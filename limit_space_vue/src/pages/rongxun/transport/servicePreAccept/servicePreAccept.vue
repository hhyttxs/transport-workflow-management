<!--
  车辆工作流列表页

-->
<template>
  <div class="main">
    <h3 class="pageTitle">预受理待办</h3>
    <DatePicker type="date" placeholder="申报起日" format="yyyy-MM-dd"
                 v-model="initParam.startTime" class="searchItem"></DatePicker>
    <DatePicker type="date" placeholder="申报止日" format="yyyy-MM-dd"
                 v-model="initParam.endTime" class="searchItem"></DatePicker>
    <Select v-model="initParam.dbjlx" clearable style="width: 200px;" placeholder="预受理待办件类型" size="large">
      <Option value="dbjlx_ysl_yslbzj">预受理补正件</Option>
      <Option value="dbjlx_ysl_yslthj">预受理退回件</Option>
      <Option value="dbjlx_ysl_yslzbzj">预受理再补正件</Option>
      <Option value="dbjlx_ysl_ylthj">受理退回件</Option>
      <Option value="dbjlx_ysl_shzbzj">受理再补正件</Option>
      <Option value="dbjlx_ysl_zbzthj">再补正退回件</Option>
      <Option value="dbjlx_ysl_dyslj">待预受理件</Option>
    </Select>
    <Select v-model="initParam.bjzt" clearable style="width: 200px;" placeholder="预受理办结状态" size="large">
      <Option value="待预受理">待预受理</Option>
      <Option value="待受理">待受理</Option>
      <Option value="待补正">待补正</Option>
      <Option value="退回申请人">退回申请人</Option>
      <Option value="待再补正">待再补正</Option>
      <Option value="再补正退回">再补正退回</Option>
      <Option value="撤回申报">撤回申报</Option>
    </Select>
    <Button type="primary" size="large" icon="ios-search" @click="search"></Button>
    <br/>
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
  import {queryAllPreAccept} from "@api/transport/transportStation/servicePreAccept/servicePreAccept";



  export default {
    name: "servicePreAccept_index",
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
        return loadWorkflowQueue(this, queryAllPreAccept);
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
          {title: '申报日期', key: 'createDate'},
          {title: '办理时限', key: 'slsx'},
          {title: '预受理待办件类型', key: 'dbjlx'},
          {title: '预受理办结状态', key: 'bjzt'},
          {
            title: '预受理操作',
            key: 'action',
            width: 140,
            fixed: 'right',
            buttons: this.authButtons([
              {
                label: '办理',
                click: (e) => this.toRoadFreightLicenceSetUp(e),
                name: 'approve',
                isShowButton: '待预受理',
                rowIndex: 0
              },{
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
        if (openSingleVehicle(this, e.row, 'pre')) return;
        let initData = {}
        let that = this
        let user = getUser()
        if (e.row.typeChild === '首次核发许可证') {
          if (e.row.source == 0) {
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceSetUpFrontDangerEcho_index',
              query: {
                id: e.row.id,
                type: 'ysl',
                step: 1,
                sqr: e.row.name,
                dbjlx: e.row.dbjCode,
                manageType: e.row.ywlx
              }
            })
          } else {
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceSetUpFrontTempEcho_index',
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
          if (e.row.source == 0 && (e.row.ywlx.indexOf('danger') != -1 || e.row.ywlx.indexOf('radiate') != -1)) {
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceChangeTempDangerEcho_index',
              query: {id: e.row.id, showType: 'ysl', step: 1, sqr: e.row.name, dbjlx: e.row.dbjCode,manageType: e.row.ywlx}
            })
          }else if(e.row.source == 0 && e.row.ywlx.indexOf('danger') == -1 && e.row.ywlx.indexOf('radiate') == -1){
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceChangeTempEcho_index',
              query: {id: e.row.id, showType: 'ysl', step: 1, sqr: e.row.name, dbjlx: e.row.dbjCode,manageType: e.row.ywlx}
            })
          } else {
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceChangeTempEcho_index',
              query: {id: e.row.id, showType: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode,manageType: e.row.ywlx}
            })
          } //许可证变更预受理
        } else if (e.row.typeChild === '许可延续') {
          if (e.row.source == 0) {
            // navigateWorkflow(that, {
            //   path: '/roadFreightLicence/roadFreightLicenceContinue_index',
            //   query: {id: e.row.id, showType: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode}
            // })
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceContinueTempDangerEcho_index',
              query: {id: e.row.id, showType: 'ysl', step: 1, sqr: e.row.name, dbjlx: e.row.dbjCode}
            })
          } else {
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceContinueTempEcho_index',
              query: {id: e.row.id, showType: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode}
            })
          } //许可证延续预受理
        } else if (e.row.typeChild === '许可证注销') {
          if (e.row.source == 0) {
            // navigateWorkflow(that, {
            //   path: '/roadFreightLicence/roadFreightLicenceLogout_index',
            //   query: {id: e.row.id, showType: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode}
            // })
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceLogoutTempEcho_index',
              query: {id: e.row.id, showType: 'ysl', step: 1, sqr: e.row.name, dbjlx: e.row.dbjCode}
            })
          } else {
            navigateWorkflow(that, {
              path: '/roadFreightLicence/roadFreightLicenceLogoutTempEcho_index',
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
              path: '/roadTransportCard/roadTransportCardFirstIssueFrontTempEcho_index',
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
              path: '/roadTransportCard/roadTransportCardChangeFrontTempEcho_index',
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
              path: '/roadTransportCard/roadTransportCardLogoutFrontTempEcho_index',
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
              path: '/roadTransportCard/roadTransportCardReissueFrontTempEcho_index',
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
              path: '/roadTransportCard/roadTransportCardRenewFrontTempEcho_index',
              query: {id: e.row.id, type: 'ysl', step: e.row.source, sqr: e.row.name, dbjlx: e.row.dbjCode,sxmc:e.row.manageType,letterGid:e.row.letterGid}
            })
          } //道路运输证换证预受理
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


