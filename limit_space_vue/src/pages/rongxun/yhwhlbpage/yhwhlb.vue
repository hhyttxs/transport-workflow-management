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
            账号：
            <Input v-model="initParam.code" clearable style="width: 200px"
                   size="large"/>
          </Space>
        </Col>
        <Col span="6" style="text-align: right;">
          <Space>
            姓名：
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

          <Button type="primary" size="small" @click="openBindRoleModal(row)">绑定</Button>


        </template>

      </Table>

      <Page style="margin-top: 10px" :total="page.pageCount" v-model="page.currentPage" :page-size="page.pageSize"

            placement="top"

            show-total show-elevator show-sizer @on-change="onChange" @on-page-size-change="pageChange"

            :pageSizeOpts="pageSizeOpts"/>

    </div>
    <rxModal ref="addModal" :buttonArray="addButtonArray" :title="title" :width='550'
             :validatedForm="this.$refs['formValidate']">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate">
        <Form-item label="登录账号：" :width="400" prop="code" :labelWidth="111">
          <Input v-model="formValidate.code" placeholder="登录账号" size="large"/>
        </Form-item>
        <Form-item label="登录密码：" v-if="!formValidate.isUpdate" :width="400" prop="password" :labelWidth="111">
          <Input v-model="formValidate.password" placeholder="登录密码" type="password" size="large"/>
        </Form-item>
        <Form-item label="真实姓名：" :width="400" prop="name" :labelWidth="111">
          <Input v-model="formValidate.name" placeholder="真实姓名" size="large"/>
        </Form-item>
        <Form-item label="手机号：" :width="400" prop="phone" :labelWidth="111">
          <Input v-model="formValidate.phone" placeholder="手机号" size="large"/>
        </Form-item>
        <FormItem label="用户类型：" prop="type">
          <Select
            v-model="formValidate.type"
            placeholder="请选择用户类型"
          >
            <Option value="admin">管理员</Option>
            <Option value="user">普通用户</Option>
          </Select>
        </FormItem>
      </Form>
    </rxModal>
    <rxModal
      ref="bindRoleModal"
      :buttonArray="bindRoleButtonArray"
      :title="bindRoleTitle"
      :width="500"
    >
      <Form :model="bindForm">

        <FormItem label="当前用户：" :labelWidth="100">
          <Input
            v-model="bindForm.userName"
            disabled
            size="large"
          />
        </FormItem>

        <FormItem label="选择角色：" :labelWidth="100">
          <Select
            v-model="bindForm.yhcdGid"
            placeholder="请选择需要绑定的角色"
            size="large"
            clearable
          >
            <Option
              v-for="item in roleList"
              :key="item.gid"
              :value="item.gid"
            >
              {{ item.name }}
            </Option>
          </Select>
        </FormItem>

      </Form>
    </rxModal>

  </div>


</template>


<script>
import {resultPayload} from "@/utils/resultPayload";


import {getUser} from "@/utils/auth";
import {queryUserList} from '@/api/hy/yhwhlb'
import rxModal from '@/components/rongxun/rx-modal/rx-modal'
import rxLabel from '@/components/rongxun/rx-label/rx-label'
import rxCard from '@/components/rongxun/rx-card/rx-card'
import {saveOrUpdateUserInfo} from '@/api/hy/yhwhlb'
import {bindUserRole} from '@/api/hy/yhwhlb'
import { queryYhList } from '@/api/hy/yhcd'

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


      title: '新增用户',
      tableData: [],
      roleList: [],

      bindForm: {
        userGid: '',
        userName: '',
        yhcdGid: ''
      },
      bindRoleTitle: '角色绑定',
      bindRoleButtonArray: [
        {
          title: '保存',
          click: () => this.saveBindRole()
        }
      ],
      formValidate: {
        code: '',
        password: '',
        name: '',
        phone: '',
        type: '',
        isUpdate: false
      },
      ruleValidate: {
        code: [{required: true, message: '请输入账号', trigger: 'blur'}],
        name: [{required: true, message: '请输入真实姓名', trigger: 'blur'}],
        phone: [{required: true, message: '请输入电话', trigger: 'blur'},],
        type: [{required: true, message: '请选择用户类型', trigger: 'change'},],
        password:[{required: true, message: '请输入密码', trigger: 'blur'},]
      },
      addButtonArray: [
        {title: '保存', click: (e) => this.saveUserInfo(e)}
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
          title: '账号',
          key: 'code',
          align: 'center'
        },
        {
          title: '姓名',
          key: 'name',
          align: 'center'
        },
        {
          title: '电话',
          key: 'phone',
          align: 'center'
        },
        {
          title: '用户类型',
          key: 'type',
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
          title: '角色绑定',
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

      queryUserList(initData).then(response => {

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


    search: function () {

      this.page.currentPage = 1

      this.initData()

    },
    addUserInfo () {
      this.formValidate.code = ''
      this.formValidate.password = ''
      this.formValidate.name = ''
      this.formValidate.phone = ''
      this.formValidate.type = ''
      this.formValidate.isUpdate = false
      this.title = '添加'
      this.$refs['addModal'].show()
    },

    saveUserInfo () {
      this.$refs.formValidate.validate(valid => {
        if (!valid) {
          this.$Message.error('请完整填写用户信息')
          return
        }

        const user = getUser()

        const submitData = {
          code: this.formValidate.code,
          password: this.formValidate.password,
          name: this.formValidate.name,
          phone: this.formValidate.phone,
          type: this.formValidate.type,
          status: '1',
          newUser: ''
        }
        if (user && user.code) {
          submitData.newUser = user.code
        }

        saveOrUpdateUserInfo(submitData)
          .then(response => {

            const result = resultPayload(response)

            if (String(result.code) === '0') {
              // 关闭弹窗
              this.$refs.addModal.close()

              this.$Message.success('添加成功')

              // 重新查询数据库并刷新表格
              this.initData()
            } else {
              this.$Message.error(result.msg || '添加失败')
            }
          })
          .catch(error => {
            console.error('添加用户失败：', error)
            this.$Message.error('添加用户失败')
          })
      })
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

    openBindRoleModal (row) {

      this.bindForm.userGid = row.gid
      this.bindForm.userName = row.name
      this.bindForm.yhcdGid = ''

      if (row.yhcdGid) {
        this.bindForm.yhcdGid = row.yhcdGid
      }

      this.loadRoleList()

      this.$refs.bindRoleModal.show()
    },
    loadRoleList () {
      const queryData = {
        currentPage: 1,
        numberPage: 1000
      }

      queryYhList(queryData)
        .then(response => {

          if (
            resultPayload(response).data &&
            Array.isArray(resultPayload(response).data.data.result)
          ) {
            this.roleList = resultPayload(response).data.data.result
          } else {
            this.roleList = []
          }

        })
        .catch(error => {
          console.error('查询角色列表失败：', error)
          this.roleList = []
          this.$Message.error('查询角色列表失败')
        })
    },
    saveBindRole () {
      if (!this.bindForm.userGid) {
        this.$Message.error('没有获取到当前用户GID')
        return
      }

      if (!this.bindForm.yhcdGid) {
        this.$Message.warning('请选择需要绑定的角色')
        return
      }

      const submitData = {
        // TAB_YHWH.GID
        userGid: this.bindForm.userGid,

        // 选中的TAB_YHCD.GID
        yhcdGid: this.bindForm.yhcdGid
      }

      bindUserRole(submitData)
        .then(response => {

          let result = resultPayload(response)

          if (
            response &&
            response.data &&
            typeof response.data === 'object' &&
            response.data.code !== undefined
          ) {
            result = response.data
          }

          if (String(result.code) === '0') {
            this.$Message.success('角色绑定成功')

            this.$refs.bindRoleModal.close()

            // 刷新用户列表
            this.initData()
          } else {
            this.$Message.error(result.msg || '角色绑定失败')
          }
        })
        .catch(error => {
          console.error('角色绑定失败：', error)
          this.$Message.error('角色绑定失败')
        })
    },

  }

};

</script>
