import BasicLayout from '@/layouts/basic-layout';

const meta = {
  auth: true
};

export default {
  path: '/home',
  name: 'home_layout',
  component: BasicLayout,
  meta: {
    auth: true,
  },
  children: [
    {
      path: 'home',
      name: 'home',
      icon: 'logo-apple',
      meta: {
        title: '首页',
        auth: true,
        closable: false,
      },
      component: () => import('@/pages/single-page/develop/index.vue')
    }
  ],
}
