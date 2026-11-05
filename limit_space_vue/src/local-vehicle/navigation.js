import BasicLayout from '@/layouts/basic-layout';
export const localRoutes=[{path:'/local',component:BasicLayout,children:[
 {path:'home',meta:{title:'工作台'},component:()=>import('./Home.vue')}
]}];
