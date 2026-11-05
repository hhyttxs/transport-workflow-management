import request from "@/utils/request";

export function saveOrUpdateClb(data) {
  return request({
    url: "/clb/saveOrUpdateClb.json",
    domed: "admin",
    method: "post",
    data: data,
  });
}
export function queryClbDraftList(data) {
  return request({
    url: '/clb/queryDraftList.json',
    domed: 'admin',
    method: 'post',
    data: data
  });
}

export function getClbInfoByGid(data) {
  return request({
    url: '/clb/getClbInfoByGid.json',
    domed: 'admin',
    method: 'post',
    data: data
  });
}
