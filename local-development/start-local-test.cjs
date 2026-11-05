const fs = require('fs');
const path = require('path');
const net = require('net');
const crypto = require('crypto');
const {spawn,spawnSync} = require('child_process');
const root = path.resolve(__dirname, '..');
const jar = path.join(__dirname, 'vehicle-test-server/target/vehicle-local-test-server-0.0.1-LOCAL-SNAPSHOT.jar');
const java = process.env.JAVA_HOME ? path.join(process.env.JAVA_HOME, 'bin', process.platform==='win32'?'java.exe':'java') : 'java';
const dependencies=process.env.TRANSPORT_FRONTEND_DEPENDENCIES ? path.resolve(process.env.TRANSPORT_FRONTEND_DEPENDENCIES) : path.join(root,'limit_space_vue/node_modules');
const cli = path.join(dependencies, '@vue/cli-service/bin/vue-cli-service.js');
const run = path.join(__dirname, '.run');
const children = [];
let stopping = false;
let controlTimer;
function available(port) {
  return new Promise((resolve, reject) => {
    const server = net.createServer();
    server.once('error', () => reject(new Error('Port ' + port + ' is in use; no existing process will be stopped')));
    server.listen(port, '127.0.0.1', () => server.close(resolve));
  });
}
function stop() {
  if (stopping) return; stopping = true;
  if (controlTimer) clearInterval(controlTimer);
  children.forEach(child => { try { child.kill(); } catch (_) {} });
  try {
    const file = path.join(run, 'pids.json');
    if (JSON.parse(fs.readFileSync(file, 'utf8')).launcher === process.pid) fs.unlinkSync(file);
  } catch (_) {}
  try {
    const file = path.join(run, 'stop-request.json');
    if (JSON.parse(fs.readFileSync(file, 'utf8')).launcher === process.pid) fs.unlinkSync(file);
  } catch (_) {}
}
async function main() {
  const version=spawnSync(java,['-version'],{encoding:'utf8',windowsHide:true});
  if(version.error || !/version "1\.8\./.test(version.stderr || '')) throw new Error('JDK 8 is required; configure JAVA_HOME');
  for (const file of [jar, cli]) if (!fs.existsSync(file)) throw new Error('Missing prerequisite: ' + file);
  await available(18091); await available(18092);
  fs.mkdirSync(run, {recursive: true});
  const key = crypto.randomBytes(32).toString('hex'), databasePassword = crypto.randomBytes(24).toString('hex');
  function launch(program, args, name, env, cwd) {
    const out = fs.openSync(path.join(run, name + '.log'), 'w');
    const child = spawn(program, args, {cwd, env, windowsHide: true, stdio: ['ignore', out, out]});
    fs.closeSync(out); children.push(child);
    child.on('error', error => { console.error(name + ': ' + error.message); stop(); process.exitCode = 1; });
    child.on('exit', code => { if (!stopping) { console.error(name + ' exited (' + code + '); see local-development/.run/' + name + '.log'); stop(); process.exitCode = code || 1; } });
    return child;
  }
  const backend = launch(java, ['-Dfile.encoding=UTF-8', '-jar', jar, '--spring.profiles.active=vehicle-local-test'],
    'backend', {...process.env, VEHICLE_LOCAL_TEST_ENABLED: 'true', VEHICLE_LOCAL_TEST_KEY: key, VEHICLE_LOCAL_TEST_DB_PASSWORD: databasePassword}, root);
  const frontend = launch(process.execPath, [cli, 'serve', '--mode', 'vehicle-local-test', '--host', '127.0.0.1', '--port', '18091'],
    'frontend', {...process.env, NODE_ENV: 'development', NODE_OPTIONS: '--preserve-symlinks --preserve-symlinks-main', VEHICLE_LOCAL_TEST_KEY: key, ...(process.env.TRANSPORT_FRONTEND_DEPENDENCIES ? {NODE_PATH:dependencies,VUE_CLI_SERVICE_CONFIG_PATH:path.join(__dirname,'frontend-dependencies.cjs')} : {})}, path.join(root, 'limit_space_vue'));
  fs.writeFileSync(path.join(run, 'pids.json'), JSON.stringify({root, launcher: process.pid, backend: backend.pid, frontend: frontend.pid, jar, cli}, null, 2));
  // Only this launcher's remembered children can be stopped, never a supplied PID.
  controlTimer = setInterval(() => {
    try {
      const request = JSON.parse(fs.readFileSync(path.join(run, 'stop-request.json'), 'utf8').replace(/^\uFEFF/, ''));
      if (request.launcher === process.pid && request.root === root) { stop(); process.exit(0); }
    } catch (_) {}
  }, 250);
  console.log('Transport workflow system: http://127.0.0.1:18091/#/local/home');
  console.log('Wait for compilation; logs: ' + run);
  console.log('Keep this terminal open; Ctrl+C stops both child processes. No services are installed.');
}
process.on('SIGINT', () => { stop(); process.exit(0); });
process.on('SIGTERM', () => { stop(); process.exit(0); });
process.on('exit', stop);
main().catch(error => { console.error(error.message); stop(); process.exitCode = 1; });
