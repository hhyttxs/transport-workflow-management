import request from '@/utils/request'
export function login(data) {
  return request({
    url: '/oauth/token',
    method: 'post',
    domed: 'auth',
    params: data,
    headers: {
      'Authorization': 'Basic REMOVED_FROM_DEMO' // 客户端信息加密，明文：client:<REMOVED>
    }
  })
}
export function getUserInfo() {
  return request({
    url: '/users/me',
    domed: 'admin',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/oauth/logout',
    domed: 'auth',
    method: 'post'
  })
}
export function getCaptcha() {
  var test = process.env.NODE_ENV;
  return request({
    url: '/captcha/getCaptcha',
    domed: 'admin',
    method: 'get'
  })
}
export function browseAuthList(data) {
  return request({
    url: '/auth/browseAuthList.json',
    domed: 'admin',
    method: 'post',
    data: data
  })
}
