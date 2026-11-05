import { createStore } from 'vuex';
import getters from './getters';
import admin from './modules/admin';
import notifications from './modules/notifications';

// 合并所有模块和配置
export default createStore({
  getters,
  modules: {
    admin,
    notifications // 将notifications模块添加到modules中
  }
});
