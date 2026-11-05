import request from '@/utils/request';

export function queryAllAccept(data) {
  return request({url: '/serviceAccept/queryAllAccept.json', domed: 'admin', method: 'post', data});
}

export function queryAllExamine(data) {
  return request({url: '/serviceAccept/queryAllExamine.json', domed: 'admin', method: 'post', data});
}

