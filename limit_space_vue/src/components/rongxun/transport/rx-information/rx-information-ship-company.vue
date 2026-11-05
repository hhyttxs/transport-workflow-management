<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
      <Divider style="font-weight: 500;color: #5b89eb" orientation="left">水路运输企业(船舶)开业申请书</Divider>
      <Row class="InsertLine">
        <Col span="12">
          <Form-item label="申请单位名称:" prop="sqdwmc">
            <Input v-model="formValidate.sqdwmc" placeholder="请输入申请单位名称"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="负责人姓名:" prop="fzrxm">
            <Input v-model="formValidate.fzrxm" placeholder="请输入负责人姓名"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="24">
          <Form-item label="申请单位地址:" prop="sqdwdz">
            <Input v-model="formValidate.sqdwdz" placeholder="请输入申请单位地址"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="12">
          <Form-item label="电话号码:" prop="dhhm">
            <Input v-model="formValidate.dhhm" placeholder="请输入电话号码"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="电报挂号:" prop="dbgh">
            <Input v-model="formValidate.dbgh" placeholder="请输入电报挂号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="12">
          <Form-item label="经济性质:" prop="jjxz">
            <Input v-model="formValidate.jjxz" placeholder="请输入经济性质"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="经营方式:" prop="jyfs">
            <Input v-model="formValidate.jyfs" placeholder="请输入经营方式"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="24">
          <Form-item label="主管部门名称:" prop="zgbmmc">
            <Input v-model="formValidate.zgbmmc" placeholder="请输入主管部门名称"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="12">
          <Form-item label="筹建申请书批准文号:" prop="cjsqspzwh">
            <Input v-model="formValidate.cjsqspzwh" placeholder="请输入筹建申请书批准文号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="批准日期:" prop="pzrq">
            <Date-picker type="date" placeholder="批准日期" value-format="yyyy-MM-dd"
                         v-model="formValidate.pzrq" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Divider style="font-weight: 500;color: #5b89eb" orientation="left">经营范围</Divider>
      <Row class="InsertLine">
        <Divider style="font-weight: 500;color: #5b89eb" orientation="left">主营</Divider>
        <Col span="24">
          <Form-item label="客运航线:" prop="jyfwZgKyhx">
            <Input type="textarea" v-model="formValidate.jyfwZgKyhx" placeholder="请输入客运航线"></Input>
          </Form-item>
        </Col>
        <Col span="24" class="InsertLine">
          <Form-item label="货运区域或航线:" prop="jyfwZgHyqyhhx">
            <Input type="textarea" v-model="formValidate.jyfwZgHyqyhhx" placeholder="请输入货运区域或航线"></Input>
          </Form-item>
        </Col>
        <Divider style="font-weight: 500;color: #5b89eb" orientation="left">兼营</Divider>
        <Col span="24">
          <Form-item label="兼营:" prop="jyfwJy">
            <Input type="textarea" v-model="formValidate.jyfwJy" placeholder="请输入兼营"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="8">
          <Form-item label="资金数额: " prop="zjse">
            <InputNumber :min="0" style="width: 140px" v-model="formValidate.zjse"
                         placeholder="请输入资金数额"></InputNumber>
          </Form-item>
        </Col>
        <Col span="8">
          <Form-item label="固定资产:" prop="gdzc">
            <InputNumber :min="0" style="width: 140px" v-model="formValidate.gdzc"
                         placeholder="请输入固定资产"></InputNumber>
          </Form-item>
        </Col>
        <Col span="8">
          <Form-item label="流动资产:" prop="ldzc">
            <InputNumber :min="0" style="width: 140px" v-model="formValidate.ldzc"
                         placeholder="请输入流动资产"></InputNumber>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="24">
          <Form-item label="资金来源: " prop="zjly">
            <Select v-model="formValidate.zjly" clearable
                    placeholder="请选择资金来源">
              <Option v-for="item in zjlyList" :value="item.ZJLYID" :key="item.ZJLYID">{{
                item.ZJLYNAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Divider style="font-weight: 500;color: #5b89eb" orientation="left">主要船员人数</Divider>
      <Row class="InsertLine">
        <Col span="24">
          <Form-item label="合计: " prop="zycyZsl">
            <InputNumber :min="0" style="width: 100px" v-model="formValidate.zycyZsl"></InputNumber>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="船(驾)长: " prop="zycyCjzSl">
            <InputNumber :min="0" style="width: 100px" v-model="formValidate.zycyCjzSl"></InputNumber>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="轮机长: " prop="zycyLjzSl">
            <InputNumber :min="0" style="width: 100px" v-model="formValidate.zycyLjzSl"></InputNumber>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="驾驶员: " prop="zycyJsySl">
            <InputNumber :min="0" style="width: 100px" v-model="formValidate.zycyJsySl"></InputNumber>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="轮机员: " prop="zycyLjySl">
            <InputNumber :min="0" style="width: 100px" v-model="formValidate.zycyLjySl"></InputNumber>
          </Form-item>
        </Col>
      </Row>
      <Divider style="font-weight: 500;color: #5b89eb" orientation="left">运力合计</Divider>
      <Row class="InsertLine">
        <Col span="24">
          <Form-item label="运力合计:">
            <Row class="InsertLine">
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjZS"></InputNumber>
                <span style="margin-left: 10px">艘</span>
              </Col>
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjZZz"></InputNumber>
                <span style="margin-left: 10px">载重吨</span>
              </Col>
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjZKw"></InputNumber>
                <span style="margin-left: 10px">客位</span>
              </Col>
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjZQw"></InputNumber>
                <span style="margin-left: 10px">千瓦</span>
              </Col>
            </Row>
          </Form-item>
        </Col>
      </Row>
      <Divider style="font-weight: 500;color: #5b89eb" orientation="left">其中</Divider>
      <Row class="InsertLine">
        <Col span="24">
          <Form-item label="客货轮:">
            <Row class="InsertLine">
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjKhlS"></InputNumber>
                <span style="margin-left: 10px">艘</span>
              </Col>
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjKhlZz"></InputNumber>
                <span style="margin-left: 10px">载重吨</span>
              </Col>
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjKhlKw"></InputNumber>
                <span style="margin-left: 10px">客位</span>
              </Col>
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjKhlQw"></InputNumber>
                <span style="margin-left: 10px">千瓦</span>
              </Col>
            </Row>
          </Form-item>
        </Col>
        <Col span="24">
          <Form-item label="货轮:">
            <Row class="InsertLine">
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjHlS"></InputNumber>
                <span style="margin-left: 10px">艘</span>
              </Col>
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjHlZz"></InputNumber>
                <span style="margin-left: 10px">载重吨</span>
              </Col>
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjHlQw"></InputNumber>
                <span style="margin-left: 10px">千瓦</span>
              </Col>
            </Row>
          </Form-item>
        </Col>
        <Col span="24">
          <Form-item label="拖轮:">
            <Row class="InsertLine">
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjTlS"></InputNumber>
                <span style="margin-left: 10px">艘</span>
              </Col>
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjTlQw"></InputNumber>
                <span style="margin-left: 10px">千瓦</span>
              </Col>
            </Row>
          </Form-item>
        </Col>
        <Col span="24">
          <Form-item label="驳船:">
            <Row class="InsertLine">
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjBcS"></InputNumber>
                <span style="margin-left: 10px">艘</span>
              </Col>
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjBcKw"></InputNumber>
                <span style="margin-left: 10px">客位</span>
              </Col>
              <Col span="6">
                <InputNumber :min="0" style="width: 100px" v-model="formValidate.ylhjBcZz"></InputNumber>
                <span style="margin-left: 10px">载重吨</span>
              </Col>
            </Row>
          </Form-item>
        </Col>
        <Col span="24">
          <Form-item label="其他:">
            <Row class="InsertLine">
              <Input type="textarea" v-model="formValidate.ylhjQt" placeholder="请输入其他"></Input>
            </Row>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="24">
          <Form-item label="申请理由: " prop="sqly">
            <Input v-model="formValidate.sqly" placeholder="请输入申请理由" type="textarea"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="24">
          <Form-item label="申请日期: " prop="sqrq">
            <Date-picker type="date" placeholder="日期" value-format="yyyy-MM-dd"
                         v-model="formValidate.sqrq" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="24">
          <Form-item label="申请企业主管部门意见: " prop="zgbmyj">
            <Input v-model="formValidate.zgbmyj" placeholder="请输入申请企业主管部门意见"
                   type="textarea"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="24">
          <Form-item label="日期: " prop="zgbmrq">
            <Date-picker type="date" placeholder="日期" value-format="yyyy-MM-dd hh:mm:ss"
                         v-model="formValidate.zgbmrq" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="24">
          <Form-item label="审批机关或抄报单位意见: " prop="approveReson">
            <Input v-model="formValidate.spyj" placeholder="请输入审批机关或抄报单位意见"
                   type="textarea"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="24">
          <Form-item label="日期: " prop="spsj">
            <Date-picker type="date" placeholder="日期" value-format="yyyy-MM-dd"
                         v-model="formValidate.spsj" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="24">
          <Form-item label="附件名称: " prop="fjmc">
            <Input v-model="formValidate.fjmc" placeholder="请输入附件名称" type="textarea"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Col span="12">
          <Form-item label="核发许可证号码: " prop="hfxkzh">
            <Input v-model="formValidate.hfxkzh" placeholder="请输入核发许可证号码"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="核发日期: " prop="hfqr">
            <Date-picker type="date" placeholder="日期" value-format="yyyy-MM-dd"
                         v-model="formValidate.hfqr" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
    </Form>
    <Alert v-if="sxlb_code==='0'||sxlb_code==='3'" style="text-align: center;color:#DC143C;" type="error">
      提醒：点击下一步之前请先暂存信息，否则数据会丢失。
    </Alert>
    <Alert class="button">
      <button @click="changeStepTo(1)">上一步</button>
      <button v-if="sxlb_code==='1'" @click="handleSubmit('formValidate',1)">受理</button>
      <button v-if="sxlb_code==='2'" @click="handleApprove(2)">审批</button>
      <button v-if="sxlb_code==='2'" @click="handleApprove(0)">退回</button>
      <button v-if="sxlb_code==='0'||sxlb_code==='3'" @click="handleSubmit('formValidate',0)">暂存信息</button>
      <button @click="changeStepTo(3)">下一步</button>
    </Alert>
  </div>
</template>
<style scoped>
  .InsertLine {
    margin-top: 5px;
  }
</style>
<script>
  import {closeThisView, responseUtil} from "@/utils/rongxunUtil";
  import {getUser} from "@/utils/auth"
  import {queryWaterCompanyInfo, saveWaterCompanyInfo} from "../../../../api/waterway/watCompany";
  import {approveWaterWayBusinessData} from "../../../../api/waterway/waterWayApprove";

  export default {
    name: "rx-information",
    components: {},

    props: {
      formDataContrnt: {
        default: {}
      },
      projectNo: '',
      sxbm: '',
      sxlb_code: '', companyId: '',
    },
    mounted() {
      this.initDataByProjectNo()
    },
    data() {
      return {
        userId: '',
        userXzqh: '',
        formValidate: {
          gid: '',
          ylhjKhlQw: '',
          ylhjZQw: '',
          zjse: '',
          cjsqspzwh: '',
          hfqr: '',
          zycyJsySl: '',
          ylhjKhlS: '',
          spyj: '',
          ylhjHlS: '',
          ylhjZS: '',
          ylhjBcZz: '',
          ldzc: '',
          spsj: '',
          zgbmrq: '',
          pzrq: '',
          xgr: '',
          sqdwmc: '',
          ylhjZZz: '',
          lrsj: '',
          gdzc: '',
          jjxz: '',
          ylhjKhlZz: '',
          ylhjTlS: '',
          ylhjZKw: '',
          jyfwZgKyhx: '',
          fzrxm: '',
          ylhjQt: '',
          sqrq: '',
          jyfs: '',
          auditlatestdate: '',
          zjly: '',
          ylhjBcKw: '',
          jyfwJy: '',
          ylhjHlQw: '',
          zgbmyj: '',
          sqly: '',
          dbgh: '',
          ylhjHlZz: '',
          sqdwdz: '',
          ylhjTlQw: '',
          dhhm: '',
          ylhjBcS: '',
          zycyLjySl: '',
          zycyZsl: '',
          byState: '',
          lrr: '',
          jyfwZgHyqyhhx: '',
          hfxkzh: '',
          zycyLjzSl: '',
          zycyCjzSl: '',
          ylhjKhlKw: '',
          xgsj: '',
          zgbmmc: '',
          state: '',
          fjmc: '',
        },
        ruleValidate: {
          sqdwmc: [{required: true, message: '申请单位名称不能为空', trigger: 'blur'}],
          sqdwdz: [{required: true, message: '申请单位地址不能为空', trigger: 'blur'}],
          fzrxm: [{required: true, message: '负责人姓名不能为空', trigger: 'blur'}],
          dhhm: [{required: true, message: '电话号码不能为空', trigger: 'blur'}],
          zjse: [{required: true, message: '资金数额不能为空', trigger: 'blur', type: 'number'}],
          gdzc: [{required: true, message: '固定资产不能为空', trigger: 'blur', type: 'number'}],
          ldzc: [{required: true, message: '流动资产不能为空', trigger: 'blur', type: 'number'}],
          zjly: [{required: true, message: '资金来源不能为空', trigger: 'blur', type: 'number'},
            {required: true, message: '资金来源不能为空', trigger: 'change', type: 'number'}],
          zycyZsl: [{required: true, message: '主要船员合计人数不能为空', trigger: 'blur', type: 'number'}],
          zycyCjzSl: [{required: true, message: '船(驾)长人数不能为空', trigger: 'blur', type: 'number'}],
          zycyLjzSl: [{required: true, message: '轮机长人数不能为空', trigger: 'blur', type: 'number'}],
          zycyJsySl: [{required: true, message: '驾驶员人数不能为空', trigger: 'blur', type: 'number'}],
          zycyLjySl: [{required: true, message: '轮机员人数不能为空', trigger: 'blur', type: 'number'}],
          ylhjZS: [{required: true, message: '运力合计艘不能为空', trigger: 'blur', type: 'number'}],
          ylhjZZz: [{required: true, message: '运力合计载重吨不能为空', trigger: 'blur', type: 'number'}],
          ylhjZKw: [{required: true, message: '运力合计客位不能为空', trigger: 'blur', type: 'number'}],
          ylhjZQw: [{required: true, message: '运力合计千瓦不能为空', trigger: 'blur', type: 'number'}],
          sqly: [{required: true, message: '申请理由不能为空', trigger: 'blur'}],
          sqrq: [{required: true, message: '申请日期不能为空', trigger: 'blur', type: 'date'}],
        },
        isSave: false,
        baseData: {
          registArea: [],
          enterpriseState: []
        },
        zjlyList: [{ZJLYID: 1, ZJLYNAME: '自筹'}, {ZJLYID: 2, ZJLYNAME: '资产评估'}, {ZJLYID: 3, ZJLYNAME: '融资'}],
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
        ],
      }
    },
    methods: {
      handleSubmit(name, type) {
        this.$refs[name].validate((valid) => {
          var that = this
          if (valid) {
            let userInfo = getUser();
            let initData = {};
            that.formValidate.pzrq = that.changeDate(that.formValidate.pzrq)
            that.formValidate.sqrq = that.changeDate(that.formValidate.sqrq)
            that.formValidate.zgbmrq = that.changeDate(that.formValidate.zgbmrq)
            that.formValidate.spsj = that.changeDate(that.formValidate.spsj)
            that.formValidate.hfqr = that.changeDate(that.formValidate.hfqr)
            initData['watYhxx'] = that.formValidate;
            initData.userCode = userInfo.code;
            initData.companyId = this.companyId;
            if (!that.isSave) {
              that.isSave = false
              saveWaterCompanyInfo(initData).then(function (response) {
                responseUtil.dealResponse(that, response, () => {
                  if (response.code == 0) {
                    that.$emit('setProjectNo', response.data);
                    that.formValidate.gid = response.data;
                    if (type == 0) {
                      that.$Message.success('暂存信息成功!');
                    } else {
                      that.handleApprove(1);
                    }
                  } else {
                    that.isSave = false
                  }
                })
              })
            } else {
              that.$Message.error('请勿重复提交');
            }
          } else {
            that.$Message.error('表单验证失败!');
          }
        })
      },
      handleApprove(type) {
        let that = this
        let initData = {}
        let userInfo = getUser();
        initData.gid = this.formValidate.gid;
        initData.sxlb = 'waterway_sx_gzl_1'
        initData.actionType = type
        initData.userCode = userInfo.code;
        approveWaterWayBusinessData(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.$Message.success(response.data);
              if (type == 1) {
                closeThisView(that, '/waterWayTransport/waterWayAcceptList_index')
              } else {
                closeThisView(that, '/waterWayTransport/waterWayApproveList_index')
              }
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      handleReset(name) {
        this.$refs[name].resetFields();
      },
      changeStepTo(number) {
        this.$emit('changeStep', number)
      },
      initDataByProjectNo() {
        if ((!this.projectNo || this.projectNo === '') && (!this.companyId || this.companyId === '')) {
          return
        }
        var that = this
        let initData = {}
        initData.projectNo = that.projectNo
        initData.companyId = that.companyId
        queryWaterCompanyInfo(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
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
      changeDate(dateA) {
        if (dateA == '' || dateA == null || dateA == undefined) {
          return;
        } else {
          let dateee = new Date(dateA).toJSON();
          let date = new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '');
          return date;
        }
      },
    }
  }
</script>

