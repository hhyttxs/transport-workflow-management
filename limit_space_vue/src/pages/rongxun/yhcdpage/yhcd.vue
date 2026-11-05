<style lang="less">
@import "../../../components/rongxun/common/rx-common.less";
@import "../../../components/rongxun/rx-tabs/rx-tabs.less";
@import "../../../components/rongxun/rx-table/rx-table.less";
@import "../../../components/rongxun/rx-modal/rx-modal.less";
@import "../../../components/rongxun/rx-card/rx-card.less";
@import "../../../components/rongxun/rx-label/rx-label.less";
@import "../../../components/rongxun/rx-labelText/rx-labelText.less";
@import "../../../components/rongxun/rx-button/rx-button.less";
</style>
<template>

  <div class="draft-list-page">

    <Row></Row>

    <div style="margin-top: 25px;background-color: white;border-radius: 10px;padding-right: 20px;padding-bottom: 15px;">

      <Row></Row>

      <Row style="margin-top: 15px" justify="center">

        <Col span="6" style="text-align: right;">
          <Space>
            角色说明：
            <Input v-model="initParam.code" clearable style="width: 200px"
                   size="large"/>
          </Space>
        </Col>
        <Col span="6" style="text-align: right;">
          <Space>
            角色名称：
            <Input v-model="initParam.name" clearable style="width: 200px"
                   size="large"/>
          </Space>
        </Col>
        <Col span="6" style="text-align: right;"></Col>
        <Col span="6" style="text-align: right;"></Col>
      </Row>
      <Row>
        <Col span="24" style="text-align: center;margin-top: 10px;">
          <Button style="width: 100px;margin-left: 10px" type="dashed" size="large" icon="md-search" @click="search">查询</Button>
          <Button style="width: 100px;margin-left: 10px" type="primary" size="large" @click="addUserInfo">添加</Button>
        </Col>

      </Row>

    </div>


    <div style="margin-top: 25px;background-color: white;border-radius: 10px;padding: 15px;">

      <Row></Row>

      <Row style="margin-top: 15px;border-bottom: 2px solid #23202a;padding-bottom: 10px;">

        <Col span="4" style="text-align: left;">

          <h3 class="pageTitle">查询结果</h3>

        </Col>
      </Row>

      <Table border :columns="columns" :data="tableData" style="margin-top: 10px">

        <template #name="{ row }">

          <strong>{{ row.name }}</strong>

        </template>

        <template #action1="{ row }">

          <Button type="primary" size="small" @click="openPageMaintain(row)">页面维护</Button>


        </template>

      </Table>


      <Page style="margin-top: 10px" :total="page.pageCount" v-model="page.currentPage" :page-size="page.pageSize"

            placement="top"

            show-total show-elevator show-sizer @on-change="onChange" @on-page-size-change="pageChange"

            :pageSizeOpts="pageSizeOpts"/>

    </div>
    <Modal v-model="roleModal" title="新增角色" :mask-closable="false">
      <Form ref="roleForm" :model="roleForm" :rules="roleRules" :label-width="80">
        <FormItem label="角色名称" prop="name"><Input v-model="roleForm.name" :maxlength="100" /></FormItem>
        <FormItem label="描述"><Input v-model="roleForm.desc" :maxlength="200" /></FormItem>
      </Form>
      <template #footer><Button @click="roleModal=false">取消</Button><Button type="primary" @click="createRole">保存</Button></template>
    </Modal>
    <rxModal
      ref="pageModal"
      :buttonArray="pageButtonArray"
      :title="pageModalTitle"
      :width="600"
    >
      <div style="max-height: 450px; overflow-y: auto; padding: 10px 20px;">
        <Tree
          ref="pageTree"
          :data="treeData"
          show-checkbox
        ></Tree>
      </div>
    </rxModal>
  </div>


</template>


<script>
import localRequest from '@/local-vehicle/request';
import {resultPayload} from "@/utils/resultPayload";


import {getUser} from "@/utils/auth";
import {queryYhList} from '@/api/hy/yhcd'
import rxModal from '@/components/rongxun/rx-modal/rx-modal'
import rxLabel from '@/components/rongxun/rx-label/rx-label'
import rxCard from '@/components/rongxun/rx-card/rx-card'
import {saveOrUpdateUserInfo} from '@/api/hy/yhcd'
import { getTreeMenuList } from '@/api/hy/cd'
import {saveCdgl} from '@/api/hy/yhcd'

export default {
  components: {
    rxModal,
    rxLabel,
    rxCard
  },
  data() {

    return {

      initParam: {

        code: '',

        name: ''

      },


      roleModal:false, roleForm:{name:'',desc:''}, roleRules:{name:[{required:true,message:'请输入角色名称',trigger:'blur'}]},
      tableData: [],
      treeData: [],
      currentPageRow: null,
      pageModalTitle: '页面维护',
      pageButtonArray: [
        {
          title: '保存',
          click: () => this.savePageMaintain()
        }
      ],


      pageSizeOpts: [10, 20, 30, 40, 50],


      page: {

        pageSize: 10,

        pageCount: 0,

        totalPage: 0,

        currentPage: 1

      },

      columns: [
        {
          title: '角色说明',
          key: 'desc',
          align: 'center'
        },
        {
          title: '角色名称',
          key: 'name',
          align: 'center'
        },
        {
          title: '状态',
          key: 'status',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              String(params.row.status) === '1' ? '正常' : '删除'
            )
          }
        },
        {
          title: '创建人',
          key: 'newUser',
          align: 'center'
        },
        {
          title: '创建时间',
          key: 'newUserTime',
          align: 'center',
          width: 180
        },
        {
          title: '操作',
          slot: 'action1',
          align: 'center',
          width: 180
        },
      ],
    };
  },
  mounted() {
    this.initData();
  },
  methods: {
    initData() {
      const user = getUser();
      const initData = {
        ...this.initParam,
        account: user && user.code ? user.code : '',
        currentPage: this.page.currentPage,
        numberPage: this.page.pageSize,
      };

      queryYhList(initData).then(response => {

        const payload=resultPayload(response);
        if (String(payload.code)!=="0") throw new Error(payload.msg);
        this.tableData = payload.data.data.result;

        this.page.pageCount = payload.data.pageCount || 0;
        this.page.totalPage = payload.data.totalPage || 0;
      })
        .catch(error => {
          console.error('查询暂存列表失败：', error);
          this.$Message.error('查询暂存列表失败');
        });
    },
    savePageMaintain () {
      const checkedNodes = this.$refs.pageTree.getCheckedNodes()
      if (!this.currentPageRow || !this.currentPageRow.gid) {
        this.$Message.error('没有获取到当前角色GID')
        return
      }
      const menuCodeList = []
      checkedNodes.forEach(item => {
        let menuCode = ''
        // 根据树接口实际返回的字段大小写获取
        if (item.code) {
          menuCode = item.code
        } else if (item.CODE) {
          menuCode = item.CODE
        }
        if (menuCode && !menuCodeList.includes(menuCode)) {
          menuCodeList.push(menuCode)
        }
      })
      const submitData = {
        // 当前 TAB_YHCD 记录的 GID
        yhcdGid: this.currentPageRow.gid,

        // 选中的 TAB_MENU.CODE
        menuCodeList: menuCodeList
      }

      saveCdgl(submitData)
        .then(response => {

          const result = resultPayload(response)

          if (String(result.code) === '0') {
            this.$Message.success('页面维护保存成功')
            this.$refs.pageModal.close()
          }
        })
        .catch(error => {
          console.error('页面维护保存失败：', error)
          this.$Message.error('页面维护保存失败')
        })
    },


    search: function () {

      this.page.currentPage = 1

      this.initData()

    },
    addUserInfo() {
      if(process.env.VUE_APP_VEHICLE_LOCAL_TEST!=='true') {this.$Message.warning('此环境尚未开放角色新增服务');return;}
      this.roleForm={name:'',desc:''};this.roleModal=true;
    },
    createRole() {this.$refs.roleForm.validate(async valid=>{if(!valid)return;try{await localRequest({url:'/localSystem/saveRole.json',method:'post',data:this.roleForm});this.roleModal=false;this.$Message.success('角色已保存');this.initData();}catch(e){this.$Message.error(e.message);}});},

    onChange: function (page) {

      this.page.currentPage = page

      this.initData()

    },

    pageChange: function (page) {

      this.page.currentPage = 1

      this.page.pageSize = page

      this.initData()

    },


    /**

     * 点击继续申报，携带暂存记录 gid 跳转。

     */

    openPageMaintain (row) {
      this.currentPageRow = row
      this.treeData = []
      this.$refs.pageModal.show()
      getTreeMenuList({yhcdGid: row.gid})
        .then(response => {

          if (
            Array.isArray(resultPayload(response).data)
          ) {
            this.treeData = resultPayload(response).data
          } else if (Array.isArray(response.data)) {
            this.treeData = response.data
          } else {
            this.treeData = []
          }

        })
        .catch(error => {
          console.error('查询菜单树失败：', error)
          this.treeData = []
          this.$Message.error('查询菜单树失败')
        })
    },

  }

};

</script>
