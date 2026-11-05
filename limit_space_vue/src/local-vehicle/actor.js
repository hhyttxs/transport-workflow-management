export const actorChoices = [
  {value: 'applicant', label: '申请人'},
  {value: 'preaccept', label: '预审员'},
  {value: 'accept', label: '受理员'},
  {value: 'local-admin', label: '系统管理员'}
];
const key = 'VEHICLE_LOCAL_TEST_ACTOR';
export function getLocalActor() {
  const value = window.sessionStorage.getItem(key);
  return actorChoices.some(item => item.value === value) ? value : 'applicant';
}
export function setLocalActor(value) {
  if (!actorChoices.some(item => item.value === value)) throw new Error('不允许的本地测试身份');
  window.sessionStorage.setItem(key, value);
}
export function assertLocalBrowser() {
  if (process.env.NODE_ENV !== 'development' || process.env.VUE_APP_VEHICLE_LOCAL_TEST !== 'true' ||
      window.location.hostname !== '127.0.0.1' || window.location.port !== '18091') {
    throw new Error('本地测试界面只允许通过 http://127.0.0.1:18091 的开发服务器使用');
  }
}
