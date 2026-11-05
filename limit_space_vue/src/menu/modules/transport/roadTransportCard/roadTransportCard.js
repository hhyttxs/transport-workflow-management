const pre = '/roadTransportCard/';
export default {
  path: '/roadTransportCard',
  header: 'home',
  title: '运输证',
  children: [
    {
      path: `${pre}roadTransportCardFirstIssue_index`,
      title: '首次核发',
    },
    {
      path: `${pre}roadTransportCardLogout_index`,
      title: '注销',
    },
    {
      path: `${pre}roadTransportCardChange_index`,
      title: '变更核发',
    },
    {
      path: `${pre}roadTransportCardRenew_index`,
      title: '换发',
    },
    {
      path: `${pre}roadTransportCardReissue_index`,
      title: '补发',
    }
  ]
};
