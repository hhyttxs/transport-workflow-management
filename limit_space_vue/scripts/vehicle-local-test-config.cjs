const FRONTEND = '127.0.0.1:18091';
function localMode(env, command) {
  if (env.VUE_APP_VEHICLE_LOCAL_TEST !== 'true') return false;
  if (env.NODE_ENV !== 'development' || command !== 'serve') {
    throw new Error('Local vehicle authentication is allowed only by the development serve command');
  }
  if (!env.VEHICLE_LOCAL_TEST_KEY || env.VEHICLE_LOCAL_TEST_KEY.length < 32) {
    throw new Error('The local launcher must supply a local access key');
  }
  return true;
}
function localServer(key) {
  return {
    host: '127.0.0.1', port: 18091, public: FRONTEND, open: false,
    disableHostCheck: false, sockHost: '127.0.0.1', sockPort: 18091,
    headers: {
      'X-Frame-Options': 'DENY', 'Referrer-Policy': 'no-referrer',
      'Content-Security-Policy': "default-src 'self'; script-src 'self' 'unsafe-eval' 'unsafe-inline'; style-src 'self' 'unsafe-inline'; img-src 'self' data: blob:; font-src 'self' data:; connect-src 'self' ws://127.0.0.1:18091; frame-src 'none'; object-src 'none'; base-uri 'self'; form-action 'self'"
    },
    before(app) {
      app.use((req, res, next) => {
        const ip = req.socket.remoteAddress;
        const origin = req.headers.origin;
        if (!['127.0.0.1', '::1', '::ffff:127.0.0.1'].includes(ip) ||
            req.headers.host !== FRONTEND ||
            (origin && origin !== 'http://' + FRONTEND) ||
            req.headers.forwarded || req.headers['x-forwarded-for'] ||
            req.headers['x-forwarded-host'] || req.headers['x-forwarded-proto'] ||
            req.headers['sec-fetch-site'] === 'cross-site') {
          res.statusCode = 403; res.end('Local vehicle test server accepts loopback same-origin requests only'); return;
        }
        next();
      });
    },
    proxy: {
      '/vehicle-local-api': {
        target: 'http://127.0.0.1:18092', changeOrigin: true, xfwd: false,
        pathRewrite: {'^/vehicle-local-api': ''},
        onProxyReq(proxyReq) {
          proxyReq.setHeader('X-Vehicle-Local-Key', key);
          proxyReq.removeHeader('Authorization');
          ['Forwarded', 'X-Forwarded-For', 'X-Forwarded-Host', 'X-Forwarded-Proto'].forEach(h => proxyReq.removeHeader(h));
        }
      }
    }
  };
}
module.exports = {localMode, localServer};
