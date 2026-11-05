const assert = require('assert');
const fs = require('fs');
const path = require('path');
const vm = require('vm');
const root = process.env.VEHICLE_PROJECT_ROOT ? path.join(process.env.VEHICLE_PROJECT_ROOT,'limit_space_vue') : path.resolve(__dirname, '../../limit_space_vue');
const modules = process.env.TRANSPORT_FRONTEND_DEPENDENCIES || path.join(root,'node_modules');
const babel = require(modules + '/@babel/core');
const compiler = require(modules + '/@vue/compiler-sfc');
const parser = require(modules + '/@babel/parser');
let user = {userType: 'admin', xzjgCode: 'ORG', xzqhCode: '210200'};
const exportsObject = {};
const source = fs.readFileSync(root + '/src/utils/teacherWorkflow.js', 'utf8');
const compiled = babel.transformSync(source, {
  configFile: false, babelrc: false,
  plugins: [require(modules + '/@babel/plugin-transform-modules-commonjs')]
}).code;
vm.runInNewContext(compiled, {
  exports: exportsObject, Date, Number, Error,
  require: name => {
    assert.strictEqual(name, '@/utils/auth');
    return {getUser: () => user};
  }
});
  const {workflowDate, loadWorkflowQueue, navigateWorkflow} = exportsObject;
let checks = 0;
function check(name, callback) { callback(); checks++; console.log('PASS ' + name); }
function context() {
  const messages=[];
  return {tableData: [], page:{currentPage:2,pageSize:10,pageCount:0,totalPage:0},initParam:{startTime:'',endTime:''},
    messages,$Message:{error:m=>messages.push(m),warning:m=>messages.push(m)}};
}
async function run() {
  check('ISO date conversion and end-of-day date handling', () => {
    const start = new Date(2026, 9, 11, 0, 0, 0);
    const end = new Date(workflowDate(start, true));
    assert.strictEqual(end.getHours(), 23);
    assert.strictEqual(end.getMinutes(), 59);
    assert.strictEqual(start.getHours(), 0);
    assert.strictEqual(workflowDate(''), '');
    assert.throws(() => workflowDate('invalid-date'));
  });
  const page=context();
  let captured;
  await loadWorkflowQueue(page, async payload => {
    captured=payload;
    return {code:'0',data:{data:{result:[{id:'teacher-row'}]},pageCount:23,totalPage:3}};
  });
  check('Real Result shape populates paged rows and account scope',()=>{
    assert.strictEqual(page.tableData[0].id,'teacher-row');
    assert.strictEqual(page.page.pageCount,23);
    assert.strictEqual(page.page.totalPage,3);
    assert.strictEqual(captured.currentPage,2);
    assert.strictEqual(captured.numberPage,10);
    assert.strictEqual(captured.xzqh,'210200');
    assert.strictEqual(page.initParam.currentPage,undefined);
  });
  await loadWorkflowQueue(page, async()=>({code:'-1',msg:'Rejected'}));
  check('Business failure is surfaced without displaying stale rows',()=>{
    assert.strictEqual(page.tableData.length,0);
    assert.strictEqual(page.messages.pop(),'Rejected');
  });
  await loadWorkflowQueue(page,async()=>({code:'0',data:{}}));
  check('Malformed responses are rejected',()=>assert.ok(page.messages.pop()));
  user=null;
  let called=false;
  await loadWorkflowQueue(page,async()=>{called=true;});
  check('Missing user scope prevents an unscoped query',()=>assert.strictEqual(called,false));
  user={userType:'admin',xzjgCode:'ORG',xzqhCode:'210200'};
  const stale=context();let finish;
  const older=loadWorkflowQueue(stale,()=>new Promise(resolve=>{finish=resolve;}));
  await loadWorkflowQueue(stale,async()=>({code:'0',data:{data:{result:[{id:'new'}]},pageCount:1,totalPage:1}}));
  finish({code:'0',data:{data:{result:[{id:'old'}]},pageCount:1,totalPage:1}});
  await older;
  check('Out-of-order replies cannot overwrite the current query',()=>assert.strictEqual(stale.tableData[0].id,'new'));
  const logout=context();let finishBeforeLogout;
  const pending=loadWorkflowQueue(logout,()=>new Promise(resolve=>{finishBeforeLogout=resolve;}));
  user=null;
  await loadWorkflowQueue(logout,async()=>{throw new Error('Unscoped request must not execute');});
  finishBeforeLogout({code:'0',data:{data:{result:[{id:'old-user'}]},pageCount:1,totalPage:1}});
  await pending;
  check('Losing account context invalidates an earlier scoped reply',()=>assert.strictEqual(logout.tableData.length,0));
  user={userType:'admin',xzjgCode:'ORG',xzqhCode:'210200'};
  const routes=context();let pushed;
  routes.$router={getRoutes:()=>[{path:'/supported'}],push:dest=>{pushed=dest;}};
  check('Unavailable workflow destination does not redirect to a false implementation',()=>{
    assert.strictEqual(navigateWorkflow(routes,{path:'/missing'}),false);
    assert.strictEqual(pushed,undefined);
  });
  check('Available workflow destination keeps its identifier payload',()=>{
    assert.strictEqual(navigateWorkflow(routes,{path:'/supported',query:{id:'1',letterGid:'L'}}),true);
    assert.strictEqual(pushed.query.letterGid,'L');
  });
  for(const name of ['src/pages/rongxun/transport/serviceAccept/serviceAccept.vue','src/pages/rongxun/transport/servicePreAccept/servicePreAccept.vue','src/components/teacher-workflow/WorkflowQueueTable.vue']){
    check('Vue 3 SFC script/template: '+name,()=>{
      const {descriptor,errors}=compiler.parse(fs.readFileSync(root+'/'+name,'utf8'),{filename:name});
      assert.strictEqual(errors.length,0);
      const template=compiler.compileTemplate({source:descriptor.template.content,filename:name,id:name});
      assert.strictEqual(template.errors.length,0);
      parser.parse(descriptor.script.content,{sourceType:'module'});
    });
  }
  check('Pagination binds the installed View UI Plus model prop',()=>{
    const pageSource=fs.readFileSync(modules+'/view-ui-plus/src/components/page/page.vue','utf8');
    const tableSource=fs.readFileSync(root+'/src/components/teacher-workflow/WorkflowQueueTable.vue','utf8');
    const match=pageSource.match(/props:\s*\{\s*(\w+):/);
    assert.ok(match, 'Installed Page prop declaration is available');
    const expected=match[1].replace(/[A-Z]/g,letter=>'-'+letter.toLowerCase());
    assert.ok(tableSource.includes(':'+expected+'="page.currentPage"'));
  });
  console.log('PASSED '+checks+' focused frontend checks');
}
run().catch(error=>{console.error(error);process.exitCode=1;});
