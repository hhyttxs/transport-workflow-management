export default {
  path: '/teacherQueues',
  title: '待办查询',
  header: 'home',
  icon: 'md-list',
  children: [
    {path: '/frontPage/vehicleWorkflow_index', title: '单车运输证申请'},
    {path: '/frontPage/servicePreAccept_index', title: '预受理待办'},
    {path: '/frontPage/serviceAccept_index', title: '受理待办'}
  ]
};
