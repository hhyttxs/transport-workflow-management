const pre = '/humanResources/';

export default {
    path: '/humanResources',
    title: '人力资源',
    header: 'home',
    icon: 'ios-people',
    children: [
        {
            path: `${pre}department_index`,
            title: '部门管理'
        },
        {
            path: `${pre}staff_index`,
            title: '员工管理',
        },
        {
            path: `${pre}duty_index`,
            title: '职务管理',
        }
    ]
}
