const assert=require('assert'),fs=require('fs'),path=require('path'),vm=require('vm');
const project=process.env.VEHICLE_PROJECT_ROOT || path.resolve(__dirname,'../..');
const root=path.join(project,'limit_space_vue');
const modules=process.env.TRANSPORT_FRONTEND_DEPENDENCIES || path.join(root,'node_modules');
const config=require(root+'/scripts/vehicle-local-test-config.cjs');
const babel=require(modules+'/@babel/core'),plugin=require(modules+'/@babel/plugin-transform-modules-commonjs');
const compiler=require(modules+'/@vue/compiler-sfc');
let checks=0;
function check(name,fn){fn();checks++;console.log('PASS '+name);}
const valid={NODE_ENV:'development',VUE_APP_VEHICLE_LOCAL_TEST:'true',VEHICLE_LOCAL_TEST_KEY:'local-test-server-key-01234567890123456789'};
check('default and production without flag preserve the original application',()=>{assert.strictEqual(config.localMode({},'build'),false);assert.strictEqual(config.localMode({NODE_ENV:'production'},'build'),false);});
check('production flag cannot enable local authentication',()=>assert.throws(()=>config.localMode({...valid,NODE_ENV:'production'},'serve'),/only/));
check('local mode cannot be built into a deployment bundle',()=>assert.throws(()=>config.localMode(valid,'build'),/only/));
check('local serve requires its server-side key',()=>assert.throws(()=>config.localMode({...valid,VEHICLE_LOCAL_TEST_KEY:''},'serve'),/key/));
check('explicit development serve is accepted',()=>assert.strictEqual(config.localMode(valid,'serve'),true));
const settings=config.localServer(valid.VEHICLE_LOCAL_TEST_KEY);
check('frontend and backend proxy targets are pinned to loopback',()=>{assert.strictEqual(settings.host,'127.0.0.1');assert.strictEqual(settings.port,18091);assert.strictEqual(settings.disableHostCheck,false);assert.strictEqual(settings.proxy['/vehicle-local-api'].target,'http://127.0.0.1:18092');});
let middleware;
settings.before({use:fn=>{middleware=fn;}});
function accepted(ip='127.0.0.1',headers={host:'127.0.0.1:18091'}){
 let next=false,status=200;
 const res={set statusCode(v){status=v;},end(){}};
 middleware({socket:{remoteAddress:ip},headers},res,()=>{next=true;});
 return {next,status};
}
check('local same-origin browser is accepted',()=>assert.strictEqual(accepted().next,true));
check('LAN requests are rejected',()=>assert.strictEqual(accepted('192.168.1.2').status,403));
check('DNS rebinding host is rejected',()=>assert.strictEqual(accepted('127.0.0.1',{host:'evil.example:18091'}).status,403));
check('foreign origin and cross-site fetch are rejected',()=>{
 assert.strictEqual(accepted('127.0.0.1',{host:'127.0.0.1:18091',origin:'https://evil.example'}).status,403);
 assert.strictEqual(accepted('127.0.0.1',{host:'127.0.0.1:18091','sec-fetch-site':'cross-site'}).status,403);
});
check('forwarded local requests are rejected',()=>assert.strictEqual(accepted('127.0.0.1',{host:'127.0.0.1:18091','x-forwarded-for':'127.0.0.1'}).status,403));
check('proxy injects only the server-side key and strips original credentials',()=>{
 const headers={Authorization:'old-production-token','X-Vehicle-Local-Key':'client-key',Forwarded:'x'};
 settings.proxy['/vehicle-local-api'].onProxyReq({setHeader:(k,v)=>{headers[k]=v;},removeHeader:k=>{delete headers[k];}});
 assert.strictEqual(headers['X-Vehicle-Local-Key'],valid.VEHICLE_LOCAL_TEST_KEY);assert(!headers.Authorization);assert(!headers.Forwarded);
});
const session=new Map();
const browserWindow={location:{hostname:'127.0.0.1',port:'18091'},sessionStorage:{getItem:k=>session.get(k)||null,setItem:(k,v)=>session.set(k,v)}};
const env={NODE_ENV:'development',VUE_APP_VEHICLE_LOCAL_TEST:'true'};
const exported={};
vm.runInNewContext(babel.transformSync(fs.readFileSync(root+'/src/local-vehicle/actor.js','utf8'),{configFile:false,babelrc:false,plugins:[plugin]}).code,{exports:exported,window:browserWindow,process:{env},Error});
check('only pinned role names are selectable',()=>{
 exported.setLocalActor('preaccept');assert.strictEqual(exported.getLocalActor(),'preaccept');
 assert.throws(()=>exported.setLocalActor('admin'),/身份/);
 session.set('VEHICLE_LOCAL_TEST_ACTOR','admin');assert.strictEqual(exported.getLocalActor(),'applicant');
});
check('no original login/session keys are written',()=>{assert.strictEqual([...session.keys()].some(k=>k.startsWith('DOMED_')),false);});
check('browser-side production and public-host guards fail closed',()=>{
 exported.assertLocalBrowser();env.NODE_ENV='production';assert.throws(()=>exported.assertLocalBrowser(),/只允许/);
 env.NODE_ENV='development';browserWindow.location.hostname='example.com';assert.throws(()=>exported.assertLocalBrowser(),/只允许/);
});
check('local Vue 3 console template/script compile',()=>{
 const filename=root+'/src/local-vehicle/Home.vue';
 const {descriptor,errors}=compiler.parse(fs.readFileSync(filename,'utf8'),{filename});assert.strictEqual(errors.length,0);
 assert.strictEqual(compiler.compileTemplate({source:descriptor.template.content,filename,id:'local-console'}).errors.length,0);
 babel.transformSync(descriptor.script.content,{configFile:false,babelrc:false,plugins:[plugin]});
});
console.log('PASS '+checks+' local frontend guard/compile checks; browser runtime verified separately.');
