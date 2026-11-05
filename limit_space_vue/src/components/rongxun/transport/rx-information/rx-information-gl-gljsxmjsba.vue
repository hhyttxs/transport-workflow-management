<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
      <Divider orientation="left">公路工程交工验收向交通主管部门备案</Divider>
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
          <Form-item label="建设规模及性质:" prop="idCardNum">
            <Input v-model="formValidate.jsgm" placeholder="请输入建设规模及性质"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="开工日期:" prop="wsdjqy">
            <Date-picker type="date" placeholder="开工日期" value-format="yyyy-MM-dd"
                         v-model="formValidate.wsdjqy" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="完工日期:" prop="jjType">
            <Date-picker type="date" placeholder="完工日期" value-format="yyyy-MM-dd"
                         v-model="formValidate.endDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="实际征用土地数（亩）:" prop="peopleNum">
            <Input v-model="formValidate.zytds" placeholder="请输入实际征用土地数"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="原批准概算:" prop="peopleNum">
            <Input v-model="formValidate.ypzgs" placeholder="请输入原批准概算"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="调整概算:" prop="peopleNum">
            <Input v-model="formValidate.tzgs" placeholder="请输入调整概算"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="竣工概算:" prop="peopleNum">
            <Input v-model="formValidate.jggs" placeholder="请输入竣工概算"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="建筑安装工程投资:" prop="peopleNum">
            <Input v-model="formValidate.jzazgctz" placeholder="请输入建筑安装工程投资"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="设备及工程器具购置费用:" prop="peopleNum">
            <Input v-model="formValidate.sbjgjqjgzfy" placeholder="请输入设备及工程器具购置费用"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="其他基本建设费:" prop="peopleNum">
            <Input v-model="formValidate.qtjbjsf" placeholder="请输入其他基本建设费"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="建设单位综合评价:" prop="peopleNum">
            <Input v-model="formValidate.jsdwzhpj" placeholder="请输入建设单位综合评价"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="设计单位综合评价:" prop="peopleNum">
            <Input v-model="formValidate.sjdwzhpj" placeholder="请输入设计单位综合评价"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="施工单位综合评价:" prop="peopleNum">
            <Input v-model="formValidate.sgdwzhpj" placeholder="请输入施工单位综合评价"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="监理单位综合评价:" prop="peopleNum">
            <Input v-model="formValidate.jldwzhpj" placeholder="请输入监理单位综合评价"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="建设管理综合评分:" prop="peopleNum">
            <Input v-model="formValidate.jsglzhpf" placeholder="请输入建设管理综合评分"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="设计工作综合评分:" prop="peopleNum">
            <Input v-model="formValidate.sjgzzhpf" placeholder="请输入设计工作综合评分"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="监理工作综合评分:" prop="peopleNum">
            <Input v-model="formValidate.jlgzzhpf" placeholder="请输入监理工作综合评分"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="施工管理综合评分:" prop="peopleNum">
            <Input v-model="formValidate.sgglzhpf" placeholder="请输入施工管理综合评分"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="建设项目综合评分:" prop="peopleNum">
            <Input v-model="formValidate.jsxmzhpf" placeholder="请输入建设项目综合评分"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="建设项目综合评分:" prop="peopleNum">
            <Input v-model="formValidate.jsxmzhpf" placeholder="请输入建设项目综合评分"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="主要材料实际消耗:" prop="phone">
            <Input type="textarea" v-model="formValidate.zyclsjxh" placeholder="请输入主要材料实际消耗"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="技术标准与主要指标:" prop="phone">
            <Input type="textarea" v-model="formValidate.jsbz" placeholder="请输入技术标准与主要指标"></Input>
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
          <Form-item label="建设项目工程质量鉴定结论及质量评价:" prop="jbr">
            <Input type="textarea" v-model="formValidate.jsxmysjl" placeholder="请输入建设项目工程质量鉴定结论及质量评价"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="有关问题的决定和建议:" prop="jbr">
            <Input type="textarea" v-model="formValidate.wtjdjy" placeholder="请输入有关问题的决定和建议"></Input>
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
  import {queryGlgcjgysba} from '@api/roadWay/glgcjgysba'
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
          ypzgs: '',//原批准概算
          gcjszynr: '',//工程建设主要内容
          zytds: '',//实际征用土地数
          jsxmysjl: '',//建设项目工程质量鉴定结论及质量评价
          tzgs: '',//调整概算
          jggs: '',//竣工概算
          jzazgctz: '',//建筑安装工程投资
          sbjgjqjgzfy: '',//设备及工程器具购置费用
          qtjbjsf: '',//其他基本建设费
          jsdwzhpj: '',//建设单位综合评价
          sjdwzhpj: '',//设计单位综合评价
          sgdwzhpj: '',//施工单位综合评价
          jldwzhpj: '',//监理单位综合评价
          jsglzhpf: '',//建设管理综合评分
          sjgzzhpf: '',//设计工作综合评分
          jlgzzhpf: '',//监理工作综合评分
          sgglzhpf: '',//施工管理综合评分
          jsxmzhpf: '',//建设项目综合评分
          jsxmzhpj: '',//工程建设项目综合评价
          wtjdjy: '',//有关问题的决定和建议
          zyclsjxh: '',//主要材料实际消耗
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
        initData.sxlb = 'gl_sx_gzl_4'
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
        queryGlgcjgysba(initData).then(function (response) {
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
