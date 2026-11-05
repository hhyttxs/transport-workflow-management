import BasicLayout from '@/layouts/basic-layout/index';

const meta = {
  auth: true
};

export default {
  path: '/frontPage/',
  name: 'frontPage',
  component: BasicLayout,
  meta: {
    title: '首页',
    showAlways: true
  },
  children: [
    {
      path: 'vehicleWorkflow_index',
      name: 'single_vehicle_workflow',
      meta: {title: '单车运输证申请', auth: true},
      component: () => import('@/pages/rongxun/transport/vehicleWorkflow/VehicleWorkflow.vue')
    },
    {
      path: 'servicePreAccept_index',
      name: 'teacher_servicePreAccept_index',
      meta: {title: '预受理待办', auth: true},
      component: () => import('@/pages/rongxun/transport/servicePreAccept/servicePreAccept.vue')
    },
    {
      path: 'serviceAccept_index',
      name: 'teacher_serviceAccept_index',
      meta: {title: '受理待办', auth: true},
      component: () => import('@/pages/rongxun/transport/serviceAccept/serviceAccept.vue')
    },
    {
      path: 'dataLog_index',
      name: 'dataLog_index',
      meta: {
        title: '报名查看',
        auth: true
      },
      component: () => import('@/pages/rongxun/frontPage/bmck.vue')
    },
    {
      path: 'bzd_index',
      name: 'bzd_index',
      meta: {
        title: '道路货物运输经营许可'
      },
      component: () =>
        import('@/pages/rongxun/page2/bzd.vue')
    },
    {
      path: 'lb_index',
      name: 'lb_index',
      meta: {
        title: '许可草稿列表'
      },
      component: () =>
        import('@/pages/rongxun/lbpage/lb.vue')
    },
    {
      path: 'clb_index',
      name: 'clb_index',
      meta: {
        title: '车辆列表'
      },
      component: () =>
        import('@/pages/rongxun/clbpage/clb.vue')
    },
    {
      path: 'cd_index',
      name: 'cd_index',
      meta: {
        title: '菜单'
      },
      component: () =>
        import('@/pages/rongxun/cdpage/cd.vue')
    },
    {
      path: 'yhwhlb_index',
      name: 'yhwhlb_index',
      meta: {
        title: '用户管理'
      },
      component: () =>
        import('@/pages/rongxun/yhwhlbpage/yhwhlb.vue')
    },
    {
      path: 'yhcd_index',
      name: 'yhcd_index',
      meta: {
        title: '角色与页面权限'
      },
      component: () =>
        import('@/pages/rongxun/yhcdpage/yhcd.vue')
    },
  ]
};
