import axios from 'axios';
import {assertLocalBrowser, getLocalActor} from './actor';
const client = axios.create({baseURL: '/vehicle-local-api', timeout: 30000});
client.interceptors.request.use(config => {
  assertLocalBrowser();
  if (config.baseURL !== '/vehicle-local-api' || !/^\/[A-Za-z0-9/_-]+\.json$/.test(config.url || '')) {
    return Promise.reject(new Error('当前环境未开放此服务'));
  }
  config.headers['X-Vehicle-Local-Actor'] = getLocalActor();
  return config;
});
client.interceptors.response.use(response => response.data, error => {
  const body = error.response && error.response.data;
  return Promise.reject(new Error((body && body.msg) || error.message || '请求失败'));
});
export default client;
