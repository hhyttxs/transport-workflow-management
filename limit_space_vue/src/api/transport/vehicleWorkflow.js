import request from '@/utils/request';

async function call(method, data = {}) {
  const response = await request({url: '/vehicleWorkflow/' + method + '.json', domed: 'admin', method: 'post', data});
  if (!response || String(response.code) !== '0' || !response.data) {
    throw new Error((response && response.msg) || '车辆业务请求失败');
  }
  return response.data;
}
export const vehicleOptions = () => call('options');
export const listVehicleDrafts = data => call('drafts', data);
export const getVehicleDraft = data => call('getAcceptCarByLetterGid', data);
export const saveVehicleDraft = data => call('holdRoadFirstIssueTemp', data);
export const submitVehicleDraft = data => call('submit', data);
export const preAcceptVehicle = data => call('updateCarForecastAccept', data);
export const acceptVehicle = data => call('updateCarAccept', data);
