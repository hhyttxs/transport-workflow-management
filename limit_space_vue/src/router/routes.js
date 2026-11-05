import {h} from 'vue';

import home from './modules/home'
import BasicLayout from '@/layouts/basic-layout';
import formPage from '@/router/modules/formPage'

/**
 * 在主框架内显示
 */

const frameIn = [
  {
    path: '/',
    redirect: {
      name: 'home'
    },
    component: BasicLayout,
    children: [
      {
        path: 'index',
        name: 'index',
        redirect: {
          name: 'home'
        }
      },
      {
        path: 'log',
        name: 'system-log',
        meta: {
          title: '前端日志',
          auth: true
        },
        component: () => import('@/pages/system/log')
      },
      // 刷新页面 必须保留
      {
        path: 'refresh',
        name: 'refresh',
        hidden: true,
        component: {
          beforeRouteEnter(to, from, next) {
            next(instance => instance.$router.replace(from.fullPath));
          },
          render: () => h()
        }
      },
      // 页面重定向 必须保留
      {
        path: 'redirect/:route*',
        name: 'redirect',
        hidden: true,
        component: {
          beforeRouteEnter(to, from, next) {
            next(instance => instance.$router.replace(JSON.parse(from.params.route)));
          },
          render: () => h()
        }
      },
    ]
  },
  home,
  formPage
];

/**
 * 在主框架之外显示
 */

const frameOut = [
  // 登录
  {
    path: '/login',
    name: 'login',
    meta: {
      title: '登录'
    },
    component: () => import('@/pages/login')
  },
  {
    path: '/loginSimple',
    name: 'loginSimple',
    meta: {
      title: '大众登录'
    },
    component: () => import('@/pages/login-simple')
  },
];

/**
 * 错误页面
 */

const errorPage = [
  {
    path: '/403',
    name: '403',
    meta: {
      title: '403'
    },
    component: () => import('@/pages/system/error/403')
  },
  {
    path: '/500',
    name: '500',
    meta: {
      title: '500'
    },
    component: () => import('@/pages/system/error/500')
  },
  {
    path: '/:pathMatch(.*)',
    name: '404',
    meta: {
      title: '404'
    },
    component: () => import('@/pages/system/error/404')
  }
];

// 导出需要显示菜单的
export const frameInRoutes = frameIn;

// 重新组织后导出
export default [
  ...frameIn,
  ...frameOut,
  ...errorPage
];
