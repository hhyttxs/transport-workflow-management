const item=(title,path)=>({title,path,icon:'ios-document-outline'});
export const localNavigation = [
 item('工作台','/local/home'),
 {title:'经营许可',path:'/local/business',icon:'md-car',children:[item('许可申请','/frontPage/bzd_index'),item('申请草稿','/frontPage/lb_index')]},
 {title:'运输证业务',path:'/local/workflow',icon:'md-list',children:[item('车辆申请','/frontPage/vehicleWorkflow_index'),item('预受理待办','/frontPage/servicePreAccept_index'),item('受理待办','/frontPage/serviceAccept_index')]},
 {title:'系统管理',path:'/local/admin',icon:'md-settings',children:[item('用户管理','/frontPage/yhwhlb_index'),item('角色权限','/frontPage/yhcd_index'),item('菜单目录','/frontPage/cd_index')]}
];
