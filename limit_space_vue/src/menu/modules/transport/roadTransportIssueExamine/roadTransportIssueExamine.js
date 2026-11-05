const pre = '/roadExamine/';

export default {
  path: '/roadExamine',
  title: '审核',
  header: 'home',
  children: [
    {
      path: `${pre}roadTransportIssueExamine_index`,
      title: '车辆审核',
    },{
      path: `${pre}roadTransportFirstExamine_index`,
      title: '业户审核',
    }
  ]
};
