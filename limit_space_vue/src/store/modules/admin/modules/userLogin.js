import {login, logout, getUserInfo} from '@/api/user/user'
import router from '@/router';
import {getToken, setToken, removeToken, setRefreshToken, removeRefreshToken, setUser} from '@/utils/auth'
import {closeThisView} from "@/utils/rongxunUtil";

export default {
  namespaced: true,
  state: {
    userName: '',
    avatarImgPath: '',
    token: getToken(),
    access: '',
    hasGetInfo: false,
    unreadCount: 0,
    messageUnreadList: [],
    messageReadedList: [],
    messageTrashList: [],
    messageContentStore: {}
  },
  mutations: {
    setUserName (state, name) {
      state.userName = name
    },
    setAccess (state, access) {
      state.access = access
    },
    setToken (state, token) {
      state.token = token
      setToken(token)
    },
    setHasGetInfo (state, status) {
      state.hasGetInfo = status
    },
    setMessageCount (state, count) {
      state.unreadCount = count
    },
    setMessageUnreadList (state, list) {
      state.messageUnreadList = list
    },
    setMessageReadedList (state, list) {
      state.messageReadedList = list
    },
    setMessageTrashList (state, list) {
      state.messageTrashList = list
    },
    updateMessageContentStore (state, { msg_id, content }) {
      state.messageContentStore[msg_id] = content
    },
    moveMsg (state, { from, to, msg_id }) {
      const index = state[from].findIndex(_ => _.msg_id === msg_id)
      const msgItem = state[from].splice(index, 1)[0]
      msgItem.loading = false
      state[to].unshift(msgItem)
    },
    SET_TOKEN (state, token) {
      state.token = token
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMS: (state, perms) => {
      state.perms = perms
    }
  },
  getters: {
    messageUnreadCount: state => state.messageUnreadList.length,
    messageReadedCount: state => state.messageReadedList.length,
    messageTrashCount: state => state.messageTrashList.length
  },
  actions: {
    login({dispatch}, userInfo) {
      this.state.admin.page.opened = []
      const {userName, password, validateCode, uuid,type} = userInfo
      return new Promise((resolve, reject) => {
        let data = {}
        data.username = JSON.stringify({
          'userName': userName,
          'type': type
        })
        data.password = password
        data.grant_type = 'password'
        data.uuid = uuid
        data.code = validateCode

        login(data).then(response => {
          const {access_token, refresh_token, token_type} = response.data
          const token = token_type + " " + access_token
          setToken(token)
          setRefreshToken(refresh_token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    refreshToken({commit}, refreshToken) {
      // commit('SET_TOKEN', undefined)
      return new Promise((resolve, reject) => {
        login({
          grant_type: 'refresh_token',
          refresh_token: refreshToken,
        }).then(response => {
          const {access_token, token_type} = response.data
          const token = token_type + " " + access_token
          // commit('SET_TOKEN', token)
          setToken(token)
          resolve(token)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // get user info
    getUserInfo({commit, state}) {
      return new Promise((resolve, reject) => {
        getUserInfo(state.token).then(response => {
          const {data} = response
          if (!data) {
            reject('Verification failed, please Login again.')
          }
          // const {id, name, deptID} = data
          setUser(data)
          // roles must be a non-empty array
          // if (!roles || roles.length <= 0) {
          //   reject('getUserInfo: roles must be a non-null array!')
          // }

          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // user logout
    logout({commit, state, dispatch}) {
      this.state.admin.page.opened = []
      return new Promise((resolve, reject) => {
        logout().then(response => {
          removeToken()
          removeRefreshToken()
          // reset visited views and cached views
          // to fixed https://github.com/hxrui/issues/2485
          // dispatch('tagsView/delAllViews', null, {root: true})
          router.push({
            name: 'login'
        });
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // remove token
    resetToken({commit}) {
      return new Promise(resolve => {
        // commit('SET_TOKEN', '')
        // commit('SET_ROLES', [])
        // commit('SET_PERMS', [])
        removeToken()
        removeRefreshToken()
        resolve()
      })
    },
  }
}
