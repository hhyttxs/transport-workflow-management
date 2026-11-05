import request from "@/utils/request";

export function queryYhList(data) {
  return request({
    url: '/yhcd/queryYhList.json',
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
export function saveCdgl (data) {
  return request({
    url: '/yhcd/saveCdgl.json',
    domed: 'admin',
    method: 'post',
    data: data
  });
}
