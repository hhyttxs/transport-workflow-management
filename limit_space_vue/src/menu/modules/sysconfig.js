const pre = '/sysconfig/';

export default {
    path: '/sysconfig',
    title: '系统设置',
    header: 'home',
    icon: 'ios-settings',
    children: [
        {
            path: `${pre}dicManage_index`,
            title: '字典管理'
        },
        {
            path: `${pre}roleManagement_index`,
            title: '角色管理',
        }
    ]
}
