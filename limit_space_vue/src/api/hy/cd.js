import request from "@/utils/request";

export function getTreeMenuList (data) {
  return request({
    url: "/cd/getTreeMenuList.json",
    domed: "admin",
    method: "post",
    data: data,
  });
}
