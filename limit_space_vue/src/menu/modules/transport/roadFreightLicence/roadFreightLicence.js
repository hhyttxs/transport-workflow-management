const pre = '/roadFreightLicence/';

// 许可证办理界面-设立
export default {
	  path: '/roadFreightLicence',
    title: '许可证',
    header: 'home',
    children: [
      {
        path: `${pre}roadFreightLicenceSetUp_index`,
        title: '设立',
      },
      {
        path: `${pre}roadFreightLicenceLogout_index`,
        title: '注销',
      },
      {
        path: `${pre}roadFreightLicenceChange_index`,
        title: '变更',
      },
      {
        path: `${pre}roadFreightLicenceContinue_index`,
        title: '延续',
      }
    ]
};
