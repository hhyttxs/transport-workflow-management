<!--
  车辆工作流列表页

-->
<template>
  <div class="main">
    <h3 class="pageTitle">受理待办</h3>
    <DatePicker type="date" placeholder="申报起日" format="yyyy-MM-dd"
                 v-model="initParam.startTime" class="searchItem"></DatePicker>
    <DatePicker type="date" placeholder="申报止日" format="yyyy-MM-dd"
                 v-model="initParam.endTime" class="searchItem"></DatePicker>
    <Select v-model="initParam.dbjlx" clearable style="width: 200px;"  placeholder="受理待办件类型" size="large">
      <Option value="待受理件">待受理件</Option>
    </Select>
    <Select v-model="initParam.bjzt" clearable style="width: 200px;" placeholder="受理办结状态" size="large">
      <Option value="待受理">待受理</Option>
      <Option value="转审批">转审批</Option>
      <Option value="退回申请人">退回申请人</Option>
    </Select>
    <Select v-model="initParam.bjxl" clearable style="width: 200px;" placeholder="办件效率" size="large">
      <Option value="0">超时</Option>
      <Option value="1">未超时</Option>
    </Select>
    <Button type="primary" size="large" icon="ios-search" @click="search"></Button><br/>
    <rxTable ref="rxTableWrap" :pageChange="pageChange" :onChange="onChange" :columnsInfo="columnsInfo()"
             :tableData="tableData"
             :page="page">
    </rxTable>
  </div>

</template>
<script>
import {openSingleVehicle} from '@/utils/singleVehicleWorkflow';
  import {loadWorkflowQueue, navigateWorkflow} from '@/utils/teacherWorkflow';
  import rxTable from '@/components/teacher-workflow/WorkflowQueueTable.vue';
  import {getUserId, responseUtil, authButtons, checkAuthList, getStaffId} from '@/utils/rongxunUtil';
  import {getUser} from '@/utils/auth';
  import {queryAllAccept} from "@api/transport/transportStation/serviceAccept/serviceAccept";


  export default {
    name: "serviceAccept_index",
    components: {
      rxTable
    },
    data() {
      return {
        tableData: [],
        page: {
          pageSize: 10,
          pageCount: 0,
          currentPage: 1
        },
        initParam: {
          startTime: '',
          endTime: '',
          bjzt: '',
          dbjlx: '',
          bjxl: ''
        },
        authButtonsList: ['approve','show']
      }
    },
    mounted: function () {
      this.$refs.rxTableWrap.additionalHeight = 53
      this.initData()
    },
    methods: {
      authButtons,
      initData: function () {
        return loadWorkflowQueue(this, queryAllAccept);
      },
      columnsInfo() {
        return [
          {
          title: '序号',
          width: 100,
          type: 'index',
          align: 'center'
          },
          {title: '申请人名称', key: 'name'},
          {title: '业务类型', key: 'manageType'},
          {title: '预受理日期', key: 'yslsj'},
          {title: '预受理意见', key: 'yslyjTwo'},
          {title: '办理时限', key: 'slsx'},
          {title: '受理待办件类型', key: 'dljlxTwo'},
          {title: '受理办结状态', key: 'bjzt'},
          {
            title: '受理操作',
            key: 'action',
            width: 140,
            fixed: 'right',
            buttons: this.authButtons([
              {label: '办理', click: (e) => this.toRoadFreightLicenceSetUp(e), name: 'approve',isShowButton:'待受理',rowIndex:0},{
                label: '查看',
                click: (e) => this.toShowRoadFreightLicenceSetUp(e),
                name: 'show',
                isShowButton: 'okButton',
                rowIndex: 1
              }
            ], this.authButtonsList)
          }
        ]
      },
      onChange: function (page) {
        this.page.currentPage = page
        this.initData()
      },
      pageChange: function (page) {
        this.page.currentPage = 1
        this.page.pageSize = page
        this.initData()
      },
      search: function () {
        this.page.currentPage = 1
        this.initData()
      },
      toRoadFreightLicenceSetUp: function (e) {
        if (openSingleVehicle(this, e.row, 'accept')) return;
        if(e.row.typeChild === '首次核发许可证'){
          if (e.row.source == 0){
            this.$router.push({
              path: '/roadFreightLicence/roadFreightLicenceSetUpFrontDangerEchoTwo_index',
              query: {id: e.row.id, type: 'sl',step:1,sqr:e.row.name, dbjlx: e.row.dbjCode,manageType:e.row.ywlx}
            })
          } else {
            this.$router.push({
              path: '/roadFreightLicence/roadFreightLicenceSetUpFrontTempEchoTwo_index',
              query: {id: e.row.id, type: 'sl',step:e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode,manageType:e.row.ywlx}
            })
          }
        }else if(e.row.typeChild === '许可证变更'){
          if (e.row.source == 0 && (e.row.ywlx.indexOf('danger') != -1 || e.row.ywlx.indexOf('radiate') != -1)){
            this.$router.push({
              path: '/roadFreightLicence/roadFreightLicenceChangeTempEchoDangerTwo_index',
              query: {id: e.row.id, showType: 'sl',step:1,sqr:e.row.name, dbjlx: e.row.dbjCode,manageType:e.row.ywlx}
            })
          }else if(e.row.source == 0 && e.row.ywlx.indexOf('danger') == -1 && e.row.ywlx.indexOf('radiate') == -1){
            this.$router.push({
              path: '/roadFreightLicence/roadFreightLicenceChangeTempEchoTwo_index',
              query: {id: e.row.id, showType: 'sl',step:1,sqr:e.row.name, dbjlx: e.row.dbjCode,manageType:e.row.ywlx}
            })
          } else {
            this.$router.push({
              path: '/roadFreightLicence/roadFreightLicenceChangeTempEchoTwo_index',
              query: {id: e.row.id, showType: 'sl',step:e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode,manageType:e.row.ywlx}
            })
          }
        }else if(e.row.typeChild === '许可延续'){
          if (e.row.source == 0){
            this.$router.push({
              path: '/roadFreightLicence/roadFreightLicenceContinueTempDangerEchoTwo_index',
              query: {id: e.row.id, showType: 'sl',step:1,sqr:e.row.name, dbjlx: e.row.dbjCode}
            })
          } else {
            this.$router.push({
              path: '/roadFreightLicence/roadFreightLicenceContinueTempEchoTwo_index',
              query: {id: e.row.id, showType: 'sl',step:e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode}
            })
          }
        }else if(e.row.typeChild === '许可证注销'){
          if (e.row.source == 0){
            // this.$router.push({
            //   path: '/roadFreightLicence/roadFreightLicenceLogoutTwo_index',
            //   query: {id: e.row.id, showType: 'sl',step:e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode}
            // })
            this.$router.push({
              path: '/roadFreightLicence/roadFreightLicenceLogoutTempEchoTwo_index',
              query: {id: e.row.id, showType: 'sl',step:1,sqr:e.row.name, dbjlx: e.row.dbjCode}
            })
          } else {
            this.$router.push({
              path: '/roadFreightLicence/roadFreightLicenceLogoutTempEchoTwo_index',
              query: {id: e.row.id, showType: 'sl',step:e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode}
            })
          }
        }else if(e.row.typeChild === '道路运输证核发'){
          if (e.row.source == 0) {
            this.$router.push({
              path: '/roadTransportCard/roadTransportCardFirstIssueTwo_index',
              query: {id: e.row.id, type: 'sl', step: e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode}
            })
          } else {
            this.$router.push({
              path: '/roadTransportCard/roadTransportCardFirstIssueFrontTempEchoTwo_index',
              query: {id: e.row.id, type: 'sl', step: e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType,letterGid:e.row.letterGid}
            })
          }
        }else if(e.row.typeChild === '道路运输证变更'){
          if (e.row.source == 0) {
            this.$router.push({
              path: '/roadTransportCard/roadTransportCardChangeTwo_index',
              query: {id: e.row.id, type: 'sl', step: e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType}
            })
          } else {
            this.$router.push({
              path: '/roadTransportCard/roadTransportCardChangeFrontTempEchoTwo_index',
              query: {id: e.row.id, type: 'sl', step: e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType,letterGid:e.row.letterGid}
            })
          }
        }else if(e.row.typeChild === '道路运输注销'){
          if (e.row.source == 0) {
            this.$router.push({
              path: '/roadTransportCard/roadTransportCardLogoutTwo_index',
              query: {id: e.row.id, type: 'sl', step: e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType}
            })
          } else {
            this.$router.push({
              path: '/roadTransportCard/roadTransportCardLogoutFrontTempEchoTwo_index',
              query: {id: e.row.id, type: 'sl', step: e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType,letterGid:e.row.letterGid}
            })
          }
        }else if(e.row.typeChild === '道路运输证补证'){
          if (e.row.source == 0) {
            this.$router.push({
              path: '/roadTransportCard/roadTransportCardReissueTwo_index',
              query: {id: e.row.id, type: 'sl', step: e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType}
            })
          } else {
            this.$router.push({
              path: '/roadTransportCard/roadTransportCardReissueFrontTempEchoTwo_index',
              query: {id: e.row.id, type: 'sl', step: e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType,letterGid:e.row.letterGid}
            })
          }
        }else if (e.row.typeChild === '道路运输证换证'){
          if (e.row.source == 0) {
            this.$router.push({
              path: '/roadTransportCard/roadTransportCardRenewTwo_index',
              query: {id: e.row.id, type: 'sl', step: e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType}
            })
          } else {
            this.$router.push({
              path: '/roadTransportCard/roadTransportCardRenewFrontTempEchoTwo_index',
              query: {id: e.row.id, type: 'sl', step: e.row.source,sqr:e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType,letterGid:e.row.letterGid}
            })
          }
        }
      },
      toShowRoadFreightLicenceSetUp: function (e) {
        if (openSingleVehicle(this, e.row, 'view')) return;
        let initData = {}
        let that = this
        let user = getUser()
        if (e.row.typeChild === '首次核发许可证') {
          if (e.row.source == 0) {
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceSetUpFrontTempEchoShow_index',
              query: {
                id: e.row.id,
                type: 'ysl',
                step: e.row.source,
                sqr: e.row.name,
                dbjlx: e.row.dbjCode,
                manageType: e.row.ywlx
              }
            })
          } else {
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceSetUpFrontTempEchoShow_index',
              query: {
                id: e.row.id,
                type: 'ysl',
                step: e.row.source,
                sqr: e.row.name,
                dbjlx: e.row.dbjCode,
                manageType: e.row.ywlx
              }
            })
          } //首次核发许可证预受理
        } else if (e.row.typeChild === '许可证变更') {
          if (e.row.source == 0) {
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceChange_index',
              query: {id: e.row.id, showType: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode,manageType: e.row.ywlx}
            })
          } else {
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceChangeTempEchoShow_index',
              query: {id: e.row.id, showType: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode,manageType: e.row.ywlx}
            })
          } //许可证变更预受理
        } else if (e.row.typeChild === '许可延续') {
          if (e.row.source == 0) {
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceContinue_index',
              query: {id: e.row.id, showType: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode}
            })
          } else {
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceContinueTempEchoShow_index',
              query: {id: e.row.id, showType: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode}
            })
          } //许可证延续预受理
        } else if (e.row.typeChild === '许可证注销') {
          if (e.row.source == 0) {
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceLogout_index',
              query: {id: e.row.id, showType: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode}
            })
          } else {
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceLogoutTempEchoShow_index',
              query: {id: e.row.id, showType: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode}
            })
          } //许可证注销预受理
        } else if (e.row.typeChild === '道路运输证核发') {
          if (e.row.source == 0) {
            navigateWorkflow(that, {
              path: '/roadTransportCard/roadTransportCardFirstIssue_index',
              query: {id: e.row.id, type: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode}
            })
          } else {
            navigateWorkflow(that, {
              path: '/roadTransportCard/roadTransportCardFirstIssueFrontTempEchoShow_index',
              query: {id: e.row.id, type: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType,letterGid:e.row.letterGid}
            })
          } //道路运输证核发预受理
        } else if (e.row.typeChild === '道路运输证变更') {
          if (e.row.source == 0) {
            navigateWorkflow(that, {
              path: '/roadTransportCard/roadTransportCardChange_index',
              query: {id: e.row.id, type: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType}
            })
          } else {
            navigateWorkflow(that, {
              path: '/roadTransportCard/roadTransportCardChangeFrontTempEchoShow_index',
              query: {id: e.row.id, type: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType,letterGid:e.row.letterGid}
            })
          } //道路运输证变更预受理
        } else if (e.row.typeChild === '道路运输注销') {
          if (e.row.source == 0) {
            navigateWorkflow(that, {
              path: '/roadTransportCard/roadTransportCardLogout_index',
              query: {id: e.row.id, type: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType}
            })
          } else {
            navigateWorkflow(that, {
              path: '/roadTransportCard/roadTransportCardLogoutFrontTempEchoShow_index',
              query: {id: e.row.id, type: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType,letterGid:e.row.letterGid}
            })
          } //道路运输证注销预受理
        } else if (e.row.typeChild === '道路运输证补证') {
          if (e.row.source == 0) {
            navigateWorkflow(that, {
              path: '/roadTransportCard/roadTransportCardReissue_index',
              query: {id: e.row.id, type: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType}
            })
          } else {
            navigateWorkflow(that, {
              path: '/roadTransportCard/roadTransportCardReissueFrontTempEchoShow_index',
              query: {id: e.row.id, type: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType,letterGid:e.row.letterGid}
            })
          } //道路运输证补证预受理
        } else if (e.row.typeChild === '道路运输证换证') {
          if (e.row.source == 0) {
            navigateWorkflow(that, {
              path: '/roadTransportCard/roadTransportCardRenew_index',
              query: {id: e.row.id, type: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType}
            })
          } else {
            navigateWorkflow(that, {
              path: '/roadTransportCard/roadTransportCardRenewFrontTempEchoShow_index',
              query: {id: e.row.id, type: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType,letterGid:e.row.letterGid}
            })
          } //道路运输证换证预受理
        }
      }
    }
  }
</script>


