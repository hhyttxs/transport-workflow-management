// Local bootstrap uses the ORIGINAL application and layout after verifying the isolated server.
import request from './request';
import {assertLocalBrowser} from './actor';
assertLocalBrowser();
request({url: '/vehicleLocalTest/session.json', method: 'get'}).then(result => {
  if (String(result.code) !== '0' || result.data.database !== 'DISPOSABLE H2 / NOT ORACLE') throw new Error('服务配置不正确');
  return import('../main');
}).catch(error => { document.getElementById('app').textContent = '演示服务暂不可用：' + error.message; });
