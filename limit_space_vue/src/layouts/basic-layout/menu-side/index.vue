<template>
    <div>
        <div class="i-layout-sider-logo layout-sider-wrap"
            :class="{ 'i-layout-sider-logo-dark': siderTheme === 'dark' }">
            <transition name="fade-quick">
                <i-link to="/" v-show="!hideLogo">
                  <div class="logoWrap">
                    <img style="width: 24px;" src="@/assets/images/logo-min.png" v-show="menuCollapse">
                    <span v-show="!menuCollapse" class="showcase-brand">交通运输业务管理</span>
                  </div>
                </i-link>
            </transition>
        </div>
        <Menu ref="menu" class="i-layout-menu-side i-scrollbar-hide"
            :class="{ 'i-layout-menu-side-collapse': menuCollapse }" :theme="siderTheme" :accordion="menuAccordion"
            :active-name="activePath" :open-names="openNames" width="auto">
            <template v-for="(item, index) in menuList" :key="index">
                <template v-if="!menuCollapse">
                    <div v-if="item.path !== '/home'">
                        <i-menu-side-item v-if="item.children === undefined || !item.children.length" :menu="item" />
                        <i-menu-side-submenu v-else :menu="item" />
                    </div>
                </template>
                <template v-else>
                    <Tooltip :content="tTitle(item.meta.title)" placement="right"
                        v-if="item.children === undefined || !item.children.length" :key="index" :theme="siderTheme">
                        <i-menu-side-item class="i-layout-menu-side-collapse-top-item" :menu="item" hide-title />
                    </Tooltip>
                    <i-menu-side-collapse v-else :menu="item" top-level />
                </template>
            </template>
        </Menu>
    </div>
</template>
<script>
import { nextTick } from 'vue';
import { mapState, mapGetters } from 'vuex';
import Setting from '@/setting';
import {getUser} from '@/utils/auth'
import {browseAuthList} from '@/api/user/user'
import {arrayContain, responseUtil, getMenuByRouter} from "@/utils/rongxunUtil"
import routers from '@/router/routes'
import {localNavigation} from '@/local-vehicle/menu'

import iMenuSideItem from './menu-item';
import iMenuSideSubmenu from './submenu';
import iMenuSideCollapse from './menu-collapse';
import tTitle from '../mixins/translate-title';

// 元素是否在可视区域
function isElementInViewport(el) {
    let rect = el.getBoundingClientRect();
    return (rect.top >= 0 && rect.left >= 0 && rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && rect.right <= (window.innerWidth || document.documentElement.clientWidth));
}

export default {
    name: 'iMenuSide',
    mixins: [tTitle],
    components: { iMenuSideItem, iMenuSideSubmenu, iMenuSideCollapse },
    props: {
        hideLogo: {
            type: Boolean,
            default: false
        }
    },
    data () {
      return {
        collapsed: false,
        isFullscreen: false,
        menuList:[],
        //后台传来的，这个人拥有的全部的菜单列表
        authList:[],
        index:0,
        count: '0',
        submitted: '1',
        otimer: null,
      }
    },
    computed: {
        ...mapState('admin/layout', [
            'siderTheme',
            'menuAccordion',
            'menuCollapse'
        ]),
        activePath() {return this.$route.path;},
        openNames() {return ['/local/business','/local/workflow','/local/admin'];},
        // ...mapState('admin/menu', [
        //     'activePath',
        //     'openNames'
        // ]),
        // ...mapGetters('admin/menu', [
        //     'filterSider'
        // ])
    },
    mounted () {
      //左侧菜单查询
      this.queryList()
    },
    watch: {
        '$route': {
            handler() {
                this.handleUpdateMenuState();
            },
            immediate: true
        },
        // 在展开/收起侧边菜单栏时，更新一次 menu 的状态
        menuCollapse() {
            this.handleUpdateMenuState();
        }
    },
    methods: {
        handleUpdateMenuState() {
            nextTick(() => {
                if (this.$refs.menu) {
                    this.$refs.menu.updateActiveName();
                    if (this.menuAccordion) this.$refs.menu.updateOpened();
                    // 聚焦当前项
                    nextTick(() => {
                        const $activeMenu = document.getElementsByClassName('ivu-menu-item ivu-menu-item-active ivu-menu-item-selected');
                        if ($activeMenu && $activeMenu.length && !isElementInViewport($activeMenu[0])) {
                            const activeMenuTop = $activeMenu[0].getBoundingClientRect().top - Setting.headerHeight;
                            const $menu = this.$refs.menu.$el;
                            setTimeout(() => {
                                this.$ScrollTop($menu, {
                                    to: activeMenuTop,
                                    time: 0
                                });
                            }, 300);
                        }
                    });
                }
            });
        },
        queryList() {
          if (process.env.NODE_ENV === 'development' && process.env.VUE_APP_VEHICLE_LOCAL_TEST === 'true') { this.menuList=localNavigation; return; }
          var that = this;
          // var menuList = this.$store.getters.menuList.concat();
          var menuList = getMenuByRouter(routers, "")
          var data = {}
          let userInfo = getUser();
          data.staff_id = userInfo.yhcdGid;
          browseAuthList(data).then(function (response) {
            responseUtil.dealResponse(that, response, () => {
              var authList = response.data.data;
              that.authList = authList;
              var authorizedList = that.getAuthorizedList(menuList)
              that.menuList = authorizedList;
            })
          })
        },
      /**
       *  获取中授权的菜单列表
       * @param item
       * @returns {*}
       */
      getAuthorizedList(list) {
        var authorizedList = [];
        for (var i = 0; i < list.length; i++) {
          var obj = list[i];

          if (obj.children) {
            obj.children = this.getAuthorizedList(obj.children)
            if (obj.children.length == 0) {
              delete obj.children
            }
          }

          if (obj.children) {
            authorizedList.push(obj)
          } else {
            if (this.checkObjectAuthorize(obj)) {
              authorizedList.push(obj)
            }
          }
        }
        return authorizedList;
      },
      /**
       * 判断一个菜单对象的名字是否在菜单列表中，如果
       * @param obj
       * @returns {*}
       */
      checkObjectAuthorize(obj) {
        var flag = false;
        if (arrayContain(this.authList, obj.header)) flag = true;
        return flag;
      }
    }
}
</script>

<style lang="less" scoped>
.showcase-brand{font-size:17px;font-weight:600;letter-spacing:1px;color:#fff;white-space:nowrap;}
.layout-sider-wrap {

    :deep(.i-layout-menu-side-title-with-collapse),
    :deep(.i-layout-menu-side-title) {
        font-size: 13px;
    }
}
</style>
