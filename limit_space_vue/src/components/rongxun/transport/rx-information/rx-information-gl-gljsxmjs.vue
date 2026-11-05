<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
      <Divider orientation="left">公路建设项目竣工验收</Divider>
      <Row>
        <Col span="12">
          <Form-item label="工程名称:" prop="gcmc">
            <Input v-model="formValidate.gcmc" placeholder="请输入工程名称"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="工程地点及主要控制点:" prop="gcdd">
            <Input v-model="formValidate.gcdd" placeholder="请输入工程地点及主要控制点"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="建设依据:" prop="fddbr">
            <Input v-model="formValidate.jsyj" placeholder="请输入建设依据"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="技术标准与主要指标:" prop="phone">
            <Input v-model="formValidate.jsbz" placeholder="请输入技术标准与主要指标"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="建设规模及性质:" prop="idCardNum">
            <Input v-model="formValidate.jsgm" placeholder="请输入建设规模及性质"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="开工日期:" prop="wsdjqy">
            <Date-picker type="date" placeholder="开工日期" value-format="yyyy-MM-dd"
                         v-model="formValidate.wsdjqy" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="完工日期:" prop="jjType">
            <Date-picker type="date" placeholder="完工日期" value-format="yyyy-MM-dd"
                         v-model="formValidate.endDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="批准概算:" prop="zczb">
            <Input v-model="formValidate.pzgs" placeholder="请输入批准概算"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="实际征用土地数（亩）:" prop="peopleNum">
            <Input v-model="formValidate.zytds" placeholder="请输入实际征用土地数"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="工程建设主要内容:" prop="address">
            <Input type="textarea" v-model="formValidate.gcjszynr" placeholder="请输入工程建设主要内容"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="建设项目工程质量交工验收结论:" prop="jbr">
            <Input type="textarea" v-model="formValidate.jsxmysjl" placeholder="请输入建设项目工程质量交工验收结论"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="存在问题处理措施:" prop="jbrPhone">
            <Input type="textarea" v-model="formValidate.wtcs" placeholder="请输入存在问题处理措施"></Input>
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
  import {saveOrUpdateGljsxmjgys, queryGljsxmjgys} from '@api/roadWay/gljsxmjgys'
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
          gcmc: '',//工程名称
          gcdd: '',//工程地点及主要控制点
          jsyj: '',//建设依据
          jsbz: '',//技术标准与主要指标
          jsgm: '',//建设规模及性质
          startDate: '',//开工日期
          endDate: '',//完工日期
          pzgs: '',//批准概算
          gcjszynr: '',//工程建设主要内容
          zytds: '',//实际征用土地数
          jsxmysjl: '',//建设项目工程质量交工验收结论
          wtcs: '',//问题措施
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
        initData.sxlb = 'gl_sx_gzl_3'
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
        this.$emit('changeStep', number)
      },
      initDataByProjectNo() {
        if (!this.projectNo || this.projectNo === '') {
          return
        }
        var that = this
        let initData = {}
        initData.projectNo = that.projectNo
        queryGljsxmjgys(initData).then(function (response) {
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
