import axios from 'axios'
// import {MessageBox, Message} from 'element-ui'
import store from '@/store'
import { getToken, getRefreshToken } from '@/utils/auth'
import { responseUtil } from '@/utils/rongxunUtil'
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

const service = axios.create({
  // baseURL: process.env.VUE_APP_BASE_API,
  baseURL: '',
  timeout: 50000
})
service.interceptors.request.use(
  config => {
    if (process.env.NODE_ENV==="development"){
      if(config.domed&&config.domed=="auth"){
        config.baseURL = process.env.VUE_APP_OAUTH_URL;
        config.url = process.env.VUE_APP_OAUTH_API + config.url;
      }else if (config.domed&&config.domed=="admin"){
        config.baseURL = process.env.VUE_APP_ADMIN_URL;
        config.url = process.env.VUE_APP_ADMIN_API + config.url;
      }else if (config.domed&&config.domed=="pms"){
        config.baseURL = process.env.VUE_APP_PMS_URL;
        config.url = process.env.VUE_APP_PMS_API + config.url;
      }else{
        config.baseURL = '';
      }
      var url = config.url;
      url = url.substr(url.indexOf("/",2));//开发环境去掉前面的模块
      config.url = url;
    } else if (process.env.NODE_ENV=="production"){
      if(config.domed&&config.domed=="auth"){
        config.baseURL = process.env.VUE_APP_OAUTH_URL;
        config.url = process.env.VUE_APP_OAUTH_API + config.url;
      }else if (config.domed&&config.domed=="admin"){
        config.baseURL = process.env.VUE_APP_ADMIN_URL;
        config.url = process.env.VUE_APP_ADMIN_API + config.url;
      }else if (config.domed&&config.domed=="pms"){
        config.baseURL = process.env.VUE_APP_PMS_URL;
        config.url = process.env.VUE_APP_PMS_API + config.url;
      }else{
        config.baseURL = "";
      }
    }

    // var test = getToken()
    if (getToken() != "" && getToken() != null) {
      // if (store.getters.token) {
      config.headers['Authorization'] = getToken()
      var token = Math.random() + new Date().getTime()
      config.headers['X-CSRF-Token'] = 'token=' + token
    }
      // config.headers['Access-Control-Allow-Origin'] = '*'
    // }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

let refreshing = false,// 正在刷新标识，避免重复刷新
  waitQueue = [] // 请求等待队列

service.interceptors.response.use(
  ({data}) => {
    let dataValue = data == null ? "":data
    const {code, msg} = dataValue
    if (code === '0' && dataValue) {
      return data
    } else {
      return data
    }
  },
  error => {
    let config = error.config
    let data = error.response ? error.response.data : null
    let code ;
    if(data){
      code = data.code
    }else{
      let msg = error.message ? error.message : "请求出错"
      code = "-9" //请求错误
      data = {'msg': msg}
    }
    if (code === 'A0230') { // access_token过期，尝试续期token
      if (refreshing == false) {
        refreshing = true  // 切换正在刷新标识，其他请求先进请求等待队列中
        const refreshToken = getRefreshToken()
        return store.dispatch('user/refreshToken', refreshToken).then((token) => {
          config.headers['Authorization'] = token
          config.baseURL = '' // 请求重试时，url已包含baseURL
          waitQueue.forEach(callback => callback(token)) // 已成功刷新token，队列中的所有请求重试
          waitQueue = []
          return service(config)
        }).catch(() => {  // token续期失败

          MessageBox.confirm(
            '当前页面已失效，请重新登录', '确认退出', {
              confirmButtonText: '重新登录',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
            store.dispatch('user/resetToken').then(() => {
              location.reload()
            })
          })
          return Promise.reject(new Error(msg || 'Error'))
        }).finally(() => {
          refreshing = false
        })
      } else {
        return new Promise((resolve => {
          waitQueue.push((token) => {
            config.headers['Authorization'] = token
            config.baseURL = ''
            resolve(service(config))
          })
        }))
      }
    } else if (code === 'A0231') { // token被禁止访问，添加到黑名单，跳转至登录页
      Message({
        message: '很不辛，您已被管理员强制下线',
        type: 'error'
      })
      store.dispatch('user/resetToken').then(() => {
        location.reload()
      })
      return Promise.reject(new Error(msg || 'Error'))
    }else if (code === '-9') {
      return data
    }else {
      return Promise.reject(data)
    }
  }
)
export default service
