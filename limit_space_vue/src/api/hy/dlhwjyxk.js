import request from "@/utils/request";

export function saveOrUpdateDlhwjyxk(data) {
  return request({
    url: "/dlhwjyxk/saveOrUpdateDlhwjyxk.json",
    domed: "admin",
    method: "post",
    data: data,
  });
}
export function queryDraftList(data) {
  return request({
    url: '/dlhwjyxk/queryDraftList.json',
    domed: 'admin',
    method: 'post',
    data: data
  });
}
export function getDlhwjyxkInfoByGid(data) {
  return request({
    url: "/dlhwjyxk/getDlhwjyxkInfoByGid.json",
    domed: "admin",
    method: "post",
    data: data
  });
}
