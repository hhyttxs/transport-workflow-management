<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
      <Divider orientation="left">公路建设项目施工许可</Divider>
      <Row>
        <Col span="12">
          <Form-item label="申请人名称:" prop="gcmc">
            <Input v-model="formValidate.sqrmc" placeholder="请输入申请人名称"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="申请人地址:" prop="gcdd">
            <Input v-model="formValidate.sqrdz" placeholder="请输入申请人地址"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="法定代表人姓名:" prop="fddbr">
            <Input v-model="formValidate.fdrxm" placeholder="请输入法定代表人姓名"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="法定代表人电话:" prop="phone">
            <Input v-model="formValidate.fdrdh" placeholder="请输入法定代表人电话"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="法定代表人手机:" prop="idCardNum">
            <Input v-model="formValidate.fdrsj" placeholder="请输入法定代表人手机"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="法定代表人传真:" prop="peopleNum">
            <Input v-model="formValidate.fdrcz" placeholder="请输入法定代表人传真"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="法定代表人邮箱:" prop="idCardNum">
            <Input v-model="formValidate.fdryx" placeholder="请输入法定代表人邮箱"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="代理人姓名:" prop="fddbr">
            <Input v-model="formValidate.dlrxm" placeholder="请输入代理人姓名"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="代理人电话:" prop="phone">
            <Input v-model="formValidate.dlrdh" placeholder="请输入代理人电话"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="代理人手机:" prop="idCardNum">
            <Input v-model="formValidate.dlrsj" placeholder="请输入代理人手机"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="代理人传真:" prop="peopleNum">
            <Input v-model="formValidate.dlrcz" placeholder="请输入代理人传真"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="代理人邮箱:" prop="idCardNum">
            <Input v-model="formValidate.dlryx" placeholder="请输入代理人邮箱"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="申请材料目录:" prop="address">
            <Input type="textarea" v-model="formValidate.sqclml" placeholder="请输入申请材料目录"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="申请日期:" prop="jjType">
            <Date-picker type="date" placeholder="申请日期" value-format="yyyy-MM-dd"
                         v-model="formValidate.sqrq" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="法定代理人签字:" prop="peopleNum">
            <Input v-model="formValidate.fddlrqz" placeholder="请输入法定代理人签字"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="24">
          <Form-item label="驳回理由:" prop="backRemark">
            <Input type="textarea"
                   v-model="formValidate.backRemark" placeholder="驳回时驳回理由必填"></Input>
          </Form-item>
        </Col>
      </Row>
    </Form>
    <Alert class="button">
      <button @click="changeStepTo(1)">上一步</button>
      <button style="width: 110px" v-if="sxlb_code==='1'" @click="handleApprove(1)">受理通过</button>
      <button v-if="sxlb_code==='1'" @click="handleApprove(-1)">驳回</button>
      <button v-if="sxlb_code==='2'" @click="handleApprove(2)">审批</button>
      <button v-if="sxlb_code==='2'&&formValidate.state==='2'" @click="handleApprove(0)">退回</button>
      <button style="width: 110px" @click="changeStepTo(3)">查看材料</button>
    </Alert>
  </div>
</template>

<script>
  import {closeThisView, responseUtil} from '@/utils/rongxunUtil'
  import {getUser} from '@/utils/auth'
  import {queryGljsxmsgxk} from '@api/roadWay/gljsxmsgxk'
  import {approveRaodWayBusinessData} from '@api/roadWay/roadWayApprove'

  export default {
    name: 'rx-information',
    components: {},

    props: {
      formDataContrnt: {
        default: {}
      },
      projectNo: '',
      sxbm: '',
      sxlb_code: ''
    },
    mounted() {
      let userInfo = getUser()
      this.formValidate.registArea = userInfo.xzqhCode
      this.formValidate.manageArea = userInfo.xzqhCode
      this.initDataByProjectNo()
    },
    data() {
      return {
        userId: '',
        userXzqh: '',
        formValidate: {
          backRemark: '',
          gid: '',
          state: '',
          ywType: '',
          id: '',
          sqrmc: '',
          sqrdz: '',
          fdrxm: '',
          fdrdh: '',
          fdrsj: '',
          fdrcz: '',
          fdryx: '',
          dlrxm: '',
          dlrdh: '',
          dlrsj: '',
          dlrcz: '',
          dlryx: '',
          sqclml: '',
          sqrq: '',
          fddlrqz: ''
        },
        ruleValidate: {},
        isSave: false,
        baseData: {
          registArea: [],
          enterpriseState: []
        },
        ddlvcDqdmList: [
          {
            'ddlSSPT_ID': '1',
            'ddlSSPT_NAME': 'test'
          }
        ],
        ddlSSPTList: [
          {
            'ddlSSPT_ID': '1',
            'ddlSSPT_NAME': 'test'
          }
        ],
        ddlCLLXList: [
          {
            'ddlCLLXID': '1',
            'ddlCLLXNAME': 'test'
          }
        ],
        ddlCSYSList: [
          {
            'ddlCSYSID': '1',
            'ddlCSYSNAME': 'test'
          }
        ],
        doType: [
          {
            'dicId': '2',
            'dicValue': '承诺件'
          },
          {
            'dicId': '1',
            'dicValue': '即办件'
          },
          {
            'dicId': '3',
            'dicValue': '联办件'
          },
          {
            'dicId': '4',
            'dicValue': '报批件'
          }
        ],
        contractPlateList: [{'value': '1233213'}],
        displacementList: [{'value': '1233213'}],
        ddlRLLXList: [{
          'ddlRLLXID': '1',
          'ddlRLLXNAME': 'test'
        }],
        ddlBSQLXList: [{
          'ddlBSQLXID': '1',
          'ddlBSQLXNAME': 'test'
        }],
        ddlPFBZList: [
          {
            'ddlPFBZID': '1',
            'ddlPFBZNAME': 'test'
          }
        ],
        rdbCLSYRLXList: [
          {
            'rdbCLSYRLXID': '1',
            'rdbCLSYRLXNAME': 'test'
          }
        ]
      }
    },
    methods: {
      handleApprove(type) {
        if (type == -1 && (this.formValidate.backRemark == null || this.formValidate.backRemark == ''
          || this.formValidate.backRemark == undefined)) {
          this.$Message.error('驳回时驳回理由必填!');
          return
        }
        let that = this
        let initData = {}
        let userInfo = getUser();
        initData.gid = this.formValidate.id;
        initData.sxlb = 'gl_sx_gzl_2'
        initData.actionType = type
        initData.userCode = userInfo.code;
        initData.backRemark = this.formValidate.backRemark;
        approveRaodWayBusinessData(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.$Message.success(response.data);
              if (type == 1 || type == -1) {
                closeThisView(that, '/roadAdmin/roadAcceptList_index')
              } else {
                closeThisView(that, '/roadAdmin/roadApprovalList_index')
              }
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      handleReset(name) {
        this.$refs[name].resetFields()
      },
      changeStepTo(number) {
        this.$emit('getJyfw', this.formValidate.jyyw)
        this.$emit('changeStep', number)
      },
      initDataByProjectNo() {
        if (!this.projectNo || this.projectNo === '') {
          return
        }
        var that = this
        let initData = {}
        initData.projectNo = that.projectNo
        queryGljsxmsgxk(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === '0') {
              console.log(response.data)
              that.formValidate = response.data
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      handleContractPlateCreate: function (val) {
        this.contractPlateList.push({value: val})
      },
      handleDisplacementCreate: function (val) {
        this.displacementList.push({value: val})
      },
    }
  }
</script>
