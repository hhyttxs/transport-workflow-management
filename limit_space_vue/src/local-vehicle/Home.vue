<template>
  <div class="workspace">
    <Card :bordered="false" class="workspace-intro">
      <h1>交通运输业务管理</h1>
      <p>围绕经营许可、车辆申报与受理审核，统一管理申请资料和办理进度。</p>
      <Space wrap><Button type="primary" @click="$router.push('/frontPage/bzd_index')">经营许可申请</Button><Button @click="$router.push('/frontPage/vehicleWorkflow_index')">车辆运输证申请</Button></Space>
    </Card>
    <Card :bordered="false" title="我的申请与待办" class="workspace-records">
      <Alert v-if="error" type="error">{{error}}</Alert>
      <Table :columns="columns" :data="rows" :loading="loading">
        <template #state="{row}"><Tag :color="row.status===2?'success':'blue'">{{stateLabel(row.status)}}</Tag></template>
        <template #action="{row}"><Button size="small" @click="open(row)">查看详情</Button></template>
      </Table>
    </Card>
  </div>
</template>
<script>
import request from './request';
export default {
  data(){return {rows:[],error:'',loading:true,columns:[{title:'车牌号码',key:'plateNumber',width:150},{title:'申请编号',key:'letterGid',minWidth:220},{title:'办理状态',slot:'state',width:150},{title:'操作',slot:'action',width:130}]};},
  async mounted(){try{const result=await request({url:'/vehicleLocalTest/inbox.json',method:'post',data:{}});this.rows=result.data.rows;}catch(e){this.error=e.message;}finally{this.loading=false;}},
  methods:{stateLabel(value){return ({'-1':'草稿','-3':'已退回','3':'待预受理','0':'待受理','2':'已受理','4':'待审批'})[value]||'待处理';},open(row){this.$router.push({path:'/frontPage/vehicleWorkflow_index',query:{id:row.id,letterGid:row.letterGid}});}}
};
</script>
<style scoped>.workspace-intro{margin-bottom:20px}.workspace-intro h1{font-size:24px;font-weight:600;margin-bottom:12px;color:#17233d}.workspace-intro p{color:#667085;line-height:1.8;margin-bottom:22px}.workspace-records{min-height:280px}</style>
