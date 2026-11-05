<style lang="less">
  @import "../../../../components/rongxun/common/rx-common.less";
  @import "../../../../components/rongxun/rx-table/rx-table.less";
  @import "../../../../components/rongxun/rx-modal/rx-modal.less";
  @import "../../../../components/rongxun/rx-card/rx-card.less";
  @import "../../../../components/rongxun/rx-label/rx-label.less";
  @import "../../../../components/rongxun/rx-labelText/rx-labelText.less";
  @import "../../../../components/rongxun/rx-button/rx-button.less";
  @import "../../../../components/rongxun/rx-tabs/rx-tabs.less";

</style>
<template>
  <div>
    <Card class="searchCard rx-table-upper">
      <Select v-model="formValidate.jurisdiction" clearable placeholder="所属辖区" size="large">
        <Option v-for="item in isOKList" :value="item.dictionaryValue" :key="item.dictionaryValue">
          {{item.dictionaryTitle }}
        </Option>
      </Select>
      <Input v-model="initParam.licence" clearable placeholder="许可证号" @keyup.enter.native="search" size="large"
             class="searchItem" style="width: 200px"/>
      <Input v-model="initParam.name" clearable placeholder="业户名称" @keyup.enter.native="search" size="large"
             class="searchItem" style="width: 200px"/>
      <Button type="primary" size="large" icon="ios-search" @click="search"></Button>
      <rxButton :click="add" >添加</rxButton>
    </Card>
    <rxTable :pageChange="pageChange" :onChange="onChange" :columnsInfo="columnsInfo()" :data="tableData" :page="page"></rxTable>
    <rxModal ref="addModal" title="添加运输业户" :width="700" :buttonArray="addButtonArray">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate">
        <rxLabel label="业户名称：" :width="300" prop="spec" :labelWidth="95">
          <Input v-model="formValidate.name" placeholder="业户名称" clearable size="large"/>
        </rxLabel>
        <rxLabel label="经营地址：" :width="300" prop="vender" :labelWidth="95">
          <Input v-model="formValidate.address" placeholder="经营地址" clearable size="large"/>
        </rxLabel>
        <rxLabel label="身份证号：" :width="300" prop="amount" :labelWidth="95">
          <Input v-model="formValidate.idNumber" placeholder="身份证号" clearable size="large"/>
        </rxLabel>
        <rxLabel label="录入人：" :width="300" prop="place" :labelWidth="95" >
          <Input v-model="formValidate.entryUser" placeholder="录入人" clearable size="large"/>
        </rxLabel>
        <rxLabel label="所属辖区：" :width="300" prop="isExplosive" :labelWidth="95">
          <Select v-model="formValidate.jurisdiction" clearable placeholder="所属辖区" size="large">
            <Option v-for="item in isOKList" :value="item.dictionaryValue" :key="item.dictionaryValue">
              {{item.dictionaryTitle }}
            </Option>
          </Select>
        </rxLabel>
        <rxLabel label="录入日期：" :width="300" prop="place" :labelWidth="95" >
          <Date-picker type="date" placeholder="选择日期" v-model="formValidate.entryDate"></Date-picker>
        </rxLabel>
        <div class="clear-float" style="height: 150px"></div>
      </Form>

    </rxModal>




  </div>
</template>
<script>
  import rxTable from '@/components/rongxun/rx-table/rx-table'
  import rxTableSelect from '@/components/rongxun/rx-table/rx-tableSelect'
  import rxModal from '@/components/rongxun/rx-modal/rx-modal'
  import rxCard from '@/components/rongxun/rx-card/rx-card'
  import rxLabel from '@/components/rongxun/rx-label/rx-label'
  import rxTabs from '@/components/rongxun/rx-tabs/rx-tabs'
  import rxButton from '@/components/rongxun/rx-button/rx-button'
  import rxTreeSelect from '@/components/rongxun/rx-tree/rx-treeSelect'
  import { getUserId, responseUtil, authButtons, checkAuthList,getStaffId } from '@/utils/rongxunUtil'
  import { getStaffFuntionModelList } from '@/api/sys_config/role-management/roleManagement'
  import { browseTransporter,saveOrUpdateTransporter,removeTransporter } from '@/api/sys_config/transporter/transporter'
  import { queryBaseData } from '@/api/common/common'
  export default {
    name: 'tempLate',
    components: {
      rxTableSelect,
      rxTable,
      rxModal,
      rxCard,
      rxLabel,
      rxTabs,
      rxButton,
      rxTreeSelect
    },
    data () {
      return {
        addType: '',
        goodsType: '0',
        authButtonsList: ['add','edit','remove'],
        formData: {
        },
        initParam: {
          drugName: ''
        },
        page: {
          pageSize: 10,
          pageCount: 0,
          currentPage: 1
        },
        tableData: [],
        title: '',
        tabsData: [],
        addButtonArray: [
          { title: '取消', click: (e) => this.cancelData(e) },
          { title: '保存', click: (e) => this.saveOrUpdateData(e) }
        ],
        formValidate: {
          jurisdiction:'',
          licence: '',
          name: '',
          address: '',
          idNumber: '',
          entryDate: '',
          entryUser: '',
        },
        ruleValidate: {
          idNumber: [{
            required: true,
            message: '请输入身份证号',
            trigger: 'blur'
          }],
          jurisdiction: [{
            required: true,
            message: '请选择所在辖区',
            trigger: 'blur'
          }]
        },
        isOKList: [{'dictionaryValue':'210001','dictionaryTitle':'中山区'},{'dictionaryValue':'210002','dictionaryTitle':'西岗区'}],
        storeTypeList: [],
      }
    },
    mounted: function () {
      this.initStorageType() //库存分类
      this.initDictionaryData() //基础数据初始化
      this.initData()
      // //初始化权限
      this.getStaffFuntionModelList()
    },
    methods: {
      authButtons,
      checkAuthList,
      columnsInfo () {
        return [
          { title: '所属辖区', width: '200', key: 'jurisdiction' },
          { title: '许可证号', width: '150', key: 'licence' },
          { title: '业户名称', width: '150', key: 'name' },
          { title: '经营地址', width: '150', key: 'address' },
          { title: '身份证号', width: '120', key: 'idNumber' },
          { title: '录入日期', width: '150', key: 'entryDate' },
          { title: '录入人', width: '150', key: 'entryUser' },
          {
            title: '操作',
            key: 'action',
            width: 200,
            align: 'center',
            fixed: 'right',
            buttons: [
              { label: '修改', click: (e) => this.update(e), name: 'edit' },
              { label: '删除', click: (e) => this.delete(e), name: 'remove' },
            ]
          },

        ]
      },
      initData: function () {
        var that = this
        let initData = {}
        initData.staff_id = getUserId()
        initData.drugName = this.initParam.drugName
        initData.goodsType = this.goodsType
        initData.currentPage = this.page.currentPage
        initData.numberPage = this.page.pageSize
        browseTransporter(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            that.tableData = response.data.data
            that.page.pageCount = response.data.pageCount
            that.page.totalPage = response.data.totalPage
          })
        })

      },
      onChange: function (page) {
        this.page.currentPage = page
        this.initData()
      },
      search: function () {
        this.page.currentPage = 1
        this.initData()
      },
      pageChange: function (page) {
        this.page.currentPage = 1
        this.page.pageSize = page
        this.initData()
      },
      getStaffFuntionModelList () {
        var that = this
        let data = {}
        data.staff_id = getUserId()
        data.menuName = this.$options.name + '_index'
        getStaffFuntionModelList(data).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            that.authButtonsList = response.data.data
            that.addType = checkAuthList(that.authButtonsList, 'add')
          })
        })
      },
      changeType: function (e) {
        this.goodsType = e
        this.initData()
      },
      add: function() { //添加页面
        var that = this
        that.formValidate.jurisdiction  = ''
        that.formValidate.licence  = ''
        that.formValidate.name  = ''
        that.formValidate.address  = ''
        that.formValidate.idNumber  = ''
        that.formValidate.entryDate  = ''
        that.formValidate.entryUser  = ''
        this.$refs['addModal'].modalFlag = true
      },
      update: function(e) { //修改页面
        var that = this
        let initData = {}
        initData.id = e.row.id
        initData.currentPage = this.page.currentPage
        initData.numberPage = this.page.pageSize
        browseTransporter(initData).then(function (response) {
            responseUtil.dealResponse(that, response, () => {
              that.formValidate.jurisdiction  = response.data.data[0].jurisdiction
              that.formValidate.licence  = response.data.data[0].licence
              that.formValidate.name  = response.data.data[0].name
              that.formValidate.address  = response.data.data[0].address
              that.formValidate.idNumber  = response.data.data[0].idNumber
              that.formValidate.entryDate  = response.data.data[0].entryDate
              that.formValidate.entryUser  = response.data.data[0].entryUser
              that.formValidate.id = response.data.data[0].id
            })
          })
        this.$refs['addModal'].modalFlag = true
      },
      cancelData: function () {
        this.$refs['addModal'].close()
      },
      saveOrUpdateData: function () {
        this.$refs['formValidate'].validate((valid) => {
          if (valid) {
            var that = this
            let initData = that.formValidate
            initData.staffId = getStaffId()
            saveOrUpdateTransporter(initData).then(function (response) {
              responseUtil.dealResponse(that, response, () => {
                    that.initData()
                    responseUtil.alertOK(that)
                    that.$refs['addModal'].close()
              })
            })
          } else {
            delayValidateCancel(this.$refs['formValidate'])
          }
        })
      },
      initDictionaryData: function () {
        var that = this
        let initData = {}
        initData.user_id = getUserId()
        initData.dbName = ['storeType']
        initData.fdName = ['ISOKMAP']
        queryBaseData(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            that.isOKList = response.data.ISOKMAP
            that.storeTypeList = response.data.storeType
          })
        })
      },
      delete: function(e){
        let that = this
        this.$Modal.confirm({
            title: '友情提示？',
            content: '<p>确认删除？</p>',
            onOk: () => {
              let initData = {}
              var id = e.row.id
              initData.id = id
              removeTransporter(initData).then(function (response) {
                responseUtil.dealResponse(that, response, () => {
                    that.initData()
                    responseUtil.alertOK(that)
              })
              })
            }
          })
      }
    }
  }
</script>
