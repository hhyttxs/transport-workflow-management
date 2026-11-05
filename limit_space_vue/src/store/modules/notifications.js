// store/modules/notifications.js
export default {
  namespaced: true,
  state: {
    lastApprovalTime: null // 存储最后一次审批时间戳
  },
  mutations: {
    UPDATE_APPROVAL_TIME(state) {
      state.lastApprovalTime = Date.now(); // 更新为当前时间
    }
  }
};
