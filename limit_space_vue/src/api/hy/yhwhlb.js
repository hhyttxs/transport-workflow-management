import request from "@/utils/request";

export function queryUserList(data) {
  return request({
    url: '/yhwhlb/queryUserList.json',
    domed: 'admin',
    method: 'post',
    data: data
  });
}
export function saveOrUpdateUserInfo(data) {
  return request({
    url: '/yhwhlb/saveOrUpdateUserInfo.json',
    domed: 'admin',
    method: 'post',
    data: data
  });
}
export function bindUserRole (data) {
  return request({
    url: '/yhwhlb/bindUserRole.json',
    domed: 'admin',
    method: 'post',
    data: data
  })
}
