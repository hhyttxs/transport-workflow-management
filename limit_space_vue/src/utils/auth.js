const accessTokenKey = 'DOMED_ACCESS_TOKEN_ADMIN'
const refreshTokenKey = 'DOMED_REFRESH_TOKEN_ADMIN'
const userKey = 'DOMED_USER_ADMIN_ADMIN'

export function getToken() {
  return window.sessionStorage.getItem(accessTokenKey)
}

export function setToken(token) {
  window.sessionStorage.setItem(accessTokenKey, token)
}

export function removeToken() {
  return window.sessionStorage.removeItem(accessTokenKey)
}


export function getRefreshToken() {
  return window.sessionStorage.getItem(refreshTokenKey)
}

export function setRefreshToken(token) {
  window.sessionStorage.setItem(refreshTokenKey, token)
}

export function removeRefreshToken() {
  return window.sessionStorage.removeItem(refreshTokenKey)
}

//存储数据库中的用户信息
export function setUser(userData) {
  try {
    userData = JSON.stringify(userData)
  } catch(e) {
    userData = null
  }
  return window.sessionStorage.setItem(userKey, userData)
}

export function getUser() {
 let userData = window.sessionStorage.getItem(userKey)
  try {
    userData = JSON.parse(userData) == null ? "" : JSON.parse(userData)
  } catch(e) {
    userData = ""
  }
  return userData
}

