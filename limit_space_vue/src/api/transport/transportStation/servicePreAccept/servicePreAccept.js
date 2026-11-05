import request from '@/utils/request';

export function queryAllPreAccept(data) {
  return request({url: '/servicePreAccept/queryAllPreAccept.json', domed: 'admin', method: 'post', data});
}

