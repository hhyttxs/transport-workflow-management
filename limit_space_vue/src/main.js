// Vue
import { createApp, h, getCurrentInstance } from 'vue';
import App from '@/App';
// 配置
import Setting from '@/setting';
// 混合
import mixinApp from '@/mixins/app';
// 插件
import plugins from '@/plugins';
// store
import store from '@/store/index';
// ViewUIPlus
import ViewUIPlus from 'view-ui-plus';
// 菜单和路由
import router from '@/router';
import menuHeader from '@/menu/header';
import { frameInRoutes } from '@/router/routes';
// 多语言
import i18n from '@/i18n';
import { loadRemoteLocaleMessages } from '@/i18n/locale';
// 内置组件
import iLink from '@/components/link';
import iFrame from '@/components/frame';
import print from 'vue3-print-nb'
import './styles/index.less';
import 'xe-utils'
import VXETable from 'vxe-table'
import 'vxe-table/lib/style.css'
import vant from "vant";

// 第一次进入时，取菜单数据用于更新
let isFirstRouteChange = true;
import Storage from 'vue-ls'
let options = {
  namespace: 'hiPrint-',
  name: 'ls',
  storage: 'local',
};
const app = createApp({
    mixins: [ mixinApp ],
    render: () => h(App),
    created () {
        // 处理路由 得到每一级的路由设置
        this.$store.commit('admin/page/init', frameInRoutes);
        // 设置顶栏菜单
        if (!Setting.dynamicMenu) this.$store.commit('admin/menu/setHeader', menuHeader);
        // 加载用户登录的数据
        // this.$store.dispatch('admin/account/load');
        // 初始化全屏监听
        this.$store.dispatch('admin/layout/listenFullscreen');
        // 将根实例存全局，可在特殊场景下调用
        if (window) {
            window.$app = getCurrentInstance();
            window.$app.$t = i18n.global.t;
        }
        // 加载远程 i18n
        if (Setting.i18n.remote) loadRemoteLocaleMessages(i18n);
    },
    watch: {
        // 监听路由 控制侧边栏显示 标记当前顶栏菜单（如需要）
        '$route' (to, from) {
            this.$store.dispatch('admin/menu/setMenuList', to);

            if (Setting.dynamicMenu) {
                if (isFirstRouteChange) {
                    isFirstRouteChange = false;
                    // 每次进入 app 时，获取一次菜单
                    this.$store.dispatch('admin/menu/getMenuList', to);
                }
            }

            this.appRouteChange(to, from);
        }
    }
});
// app.use(Antd)
app.use(router);
app.use(store);
app.use(i18n);
app.use(ViewUIPlus, {
    i18n
});
app.use(plugins);
app.use(print)
app.use(VXETable);
app.use(vant);
app.component('i-link', iLink);
app.component('i-frame', iFrame);
app.use(Storage, options);
app.config.productionTip = false
app.mount('#app');
