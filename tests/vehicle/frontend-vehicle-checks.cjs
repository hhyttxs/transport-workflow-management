const assert = require('assert');
const fs = require('fs');
const path = require('path');
const vm = require('vm');
const project = process.env.VEHICLE_PROJECT_ROOT || path.resolve(__dirname,'../..');
const root = path.join(project, 'limit_space_vue');
const modules = process.env.TRANSPORT_FRONTEND_DEPENDENCIES || path.join(root,'node_modules');
const babel = require(modules + '/@babel/core');
const compiler = require(modules + '/@vue/compiler-sfc');
const plugin = require(modules + '/@babel/plugin-transform-modules-commonjs');
let checks = 0;
function check(name, fn) { fn(); checks++; console.log('PASS ' + name); }
function load(source, imports) {
  const exports = {};
  vm.runInNewContext(babel.transformSync(source, {configFile: false, babelrc: false, plugins: [plugin]}).code,
    {exports, require: name => { assert(name in imports, name); return imports[name]; }, Date, Number, String, Boolean,
      Object, JSON, Promise, Error, console, process: {env: {NODE_ENV:'test'}}});
  return exports;
}
const helper = load(fs.readFileSync(root + '/src/utils/singleVehicleWorkflow.js', 'utf8'), {});
let calls = [];
let reply = {code:'0',data:{id:'9007199254740993',status:-1}};
const api = load(fs.readFileSync(root + '/src/api/transport/vehicleWorkflow.js', 'utf8'),
  {'@/utils/request': async request => { calls.push(request); return reply; }});
const filename = root + '/src/pages/rongxun/transport/vehicleWorkflow/VehicleWorkflow.vue';
const {descriptor, errors} = compiler.parse(fs.readFileSync(filename,'utf8'), {filename});
assert.strictEqual(errors.length, 0);
const compiledTemplate = compiler.compileTemplate({id:'vehicle-test', source:descriptor.template.content, filename});
assert.strictEqual(compiledTemplate.errors.length,0);
let records = {};
const mocks = {
  vehicleOptions: async () => ({enterprises:[{value:'enterprise',label:'Test'}],businessTypes:[]}),
  listVehicleDrafts: async () => ({rows:[],total:0}),
  getVehicleDraft: async identity => records[identity.letterGid],
  saveVehicleDraft: async payload => ({...payload,id:'9007199254740993',vehicleId:'vehicle',letterGid:'letter',revision:'new',canEdit:true}),
  submitVehicleDraft: async identity => ({...identity,status:3}),
  preAcceptVehicle: async identity => ({...identity,status:0}),
  acceptVehicle: async payload => { calls.push(payload); return {...payload,status:2}; }
};
const component = load(descriptor.script.content, {'@/api/transport/vehicleWorkflow':mocks,'@/utils/singleVehicleWorkflow':helper}).default;
function context(query={}) {
  const out = component.data(), navigation=[], messages=[];
  out.$route={query,fullPath:'test'};
  out.$router={push:d=>navigation.push(d),replace:d=>{navigation.push(d);return Promise.resolve();}};
  out.$Message={success:m=>messages.push(m)};
  out.navigation=navigation;out.messages=messages;
  Object.keys(component.methods).forEach(name=>{out[name]=component.methods[name].bind(out);});
  Object.keys(component.computed).forEach(name=>Object.defineProperty(out,name,{get:component.computed[name].bind(out)}));
  return out;
}
async function run() {
  check('Vue 3 form template compiles',()=>assert(compiledTemplate.code.includes('createElement')));
  check('identity strings preserve numeric PK beyond JS safe integer',()=>{
    const identity=helper.vehicleIdentity({id:'9007199254740993',vehicleId:'vehicle',letterGid:'letter',revision:'hash'});
    assert.strictEqual(identity.id,'9007199254740993');
    assert.strictEqual(identity.vehicleId,'vehicle');assert.strictEqual(identity.letterGid,'letter');
  });
  check('draft payload excludes role/status/owner and acceptance fields',()=>{
    const payload=helper.vehicleDraftPayload({id:'1',vehicleId:'v',letterGid:'l',revision:'r',status:2,ownerId:'attack'},
      {...helper.blankVehicleForm(),enterpriseId:'e',businessType:'b',status:2,permitNumber:'fake'});
    assert(!('status' in payload));assert(!('ownerId' in payload));assert(!('permitNumber' in payload));
  });
  check('only first-issue queue rows use the new route',()=>{
    const page=context();
    assert.strictEqual(helper.openSingleVehicle(page,{typeChild:'许可证变更',id:'7'},'pre'),false);
    assert.strictEqual(page.navigation.length,0);
    assert.strictEqual(helper.openSingleVehicle(page,{typeChild:'道路运输证核发',id:'9007199254740993',letterGid:'letter'},'accept'),true);
    assert.strictEqual(page.navigation[0].query.id,'9007199254740993');
    assert.strictEqual(page.navigation[0].query.letterGid,'letter');
    assert.strictEqual(page.navigation[0].query.mode,'accept');
  });
  await api.getVehicleDraft({letterGid:'letter'});
  check('API adapter uses existing admin request and Result convention',()=>{
    assert.strictEqual(calls[0].url,'/vehicleWorkflow/getAcceptCarByLetterGid.json');
    assert.strictEqual(calls[0].domed,'admin');assert.strictEqual(calls[0].method,'post');
  });
  reply={code:'V409',msg:'stale',data:null};
  await assert.rejects(()=>api.saveVehicleDraft({}),/stale/);checks++;console.log('PASS API business failures propagate');
  reply={code:'0',data:null};
  await assert.rejects(()=>api.listVehicleDrafts({}),/车辆业务请求失败/);checks++;console.log('PASS malformed Result rejected');
  const page=context();
  page.restore({id:'1',letterGid:'letter',vehicleId:'v',revision:'r',plateNumber:'辽B12345',registrationDate:'2026-01-01',canEdit:true,canSubmit:true});
  check('restoration resets prior form fields and keeps dates',()=>{
    assert.strictEqual(page.form.plateNumber,'辽B12345');assert.strictEqual(page.form.vin,'');
    assert.strictEqual(page.form.registrationDate,'2026-01-01');assert.strictEqual(page.dirty,false);
  });
  check('unsaved edits are detected before submission',()=>{page.form.vin='L1234567890123456';assert.strictEqual(page.dirty,true);});
  const view=context({mode:'view'});view.restore({id:'1',canEdit:true});
  check('view route stays read only even for owner',()=>assert.strictEqual(view.editable,false));
  page.form.enterpriseId='enterprise';page.form.businessType='ordinary';
  await page.save();
  check('save restores new identity and revision',()=>{
    assert.strictEqual(page.record.id,'9007199254740993');assert.strictEqual(page.record.revision,'new');assert.strictEqual(page.dirty,false);
  });
  await page.submit();
  check('submit restores submitted state',()=>assert.strictEqual(page.record.status,3));
  await page.preAccept();
  check('preaccept restores state 0',()=>assert.strictEqual(page.record.status,0));
  page.acceptForm={opinion:'Reviewed',permitNumber:'EXISTING',inspectionExpiry:'2099-01-01',inspectionEvidence:'Inspected record'};
  page.confirmed=true;
  await page.accept('slhg');
  check('manual acceptance sends reviewer confirmation and evidence',()=>{
    const body=calls[calls.length-1];assert.strictEqual(body.manualVerified,true);assert.strictEqual(body.inspectionEvidence,'Inspected record');
    assert.strictEqual(page.record.status,2);
  });
  let resolve;
  const stale=context({letterGid:'old'});
  mocks.getVehicleDraft=()=>new Promise(r=>{resolve=r;});
  const pending=stale.reload();
  stale.requestId++;stale.restore({id:'current'});
  resolve({id:'old',canEdit:false});await pending;
  check('late lookup cannot overwrite a different application',()=>assert.strictEqual(stale.record.id,'current'));
  const mutation=context();
  let finish;
  const changing=mutation.applyMutation(()=>new Promise(r=>{finish=r;}),'ok');
  mutation.requestId++;mutation.restore({id:'current'});finish({id:'old'});await changing;
  check('late mutation response cannot replace a different application',()=>assert.strictEqual(mutation.record.id,'current'));
  const failure=context();
  await failure.execute(async()=>{throw new Error('permission denied');});
  check('server rejection leaves actionable error and releases busy flag',()=>{
    assert.strictEqual(failure.error,'permission denied');assert.strictEqual(failure.busy,false);
  });
  check('both existing queues link process and view actions',()=>{
    for(const name of ['serviceAccept','servicePreAccept']) {
      const source=fs.readFileSync(root+'/src/pages/rongxun/transport/'+name+'/'+name+'.vue','utf8');
      assert(source.includes("openSingleVehicle(this, e.row, 'view')"));
      assert(source.includes("openSingleVehicle(this, e.row, '"+(name==='serviceAccept'?'accept':'pre')+"')"));
    }
  });
  console.log('PASS '+checks+' focused vehicle frontend checks. No authenticated browser/Oracle runtime claimed.');
}
run().catch(e=>{console.error(e);process.exitCode=1;});
