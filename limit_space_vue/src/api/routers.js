import axios from '@/libs/api.request'

export const getRouterReq = (access) => {
  return axios.request({
    url: 'get_router',
    params: {
      access
    },
    method: 'get'
  })
}
export function saveYhcdMenu (data) {
  return request({
    url: '/yhcd/saveYhcdMenu.json',
    domed: 'admin',
    method: 'post',
    data: data
  })
}
