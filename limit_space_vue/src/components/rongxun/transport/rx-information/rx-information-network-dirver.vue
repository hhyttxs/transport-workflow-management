<style lang="less">
@import "../../rx-label/rx-label.less";
@import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Divider orientation="left">从业资格证信息录入</Divider>
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="taxiCarDriverForm" :model="taxiCarDriverForm" :rules="ruleValidate" :label-width="190">
      <Row>
        <Col span="12">
          <Form-item label="姓名:" prop="name">
            <Input v-model="taxiCarDriverForm.name" placeholder="请输姓名"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="性别:" prop="sex">
            <Select v-model="taxiCarDriverForm.sex" clearable
                    placeholder="">
              <Option :value="'男'" :key="'男'">
              </Option>
              <Option :value="'女'" :key="'女'">
              </Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="身份证号:" prop="idcard">
            <Input v-model="taxiCarDriverForm.idcard" placeholder="请输身份证号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="国籍:" prop="nation">
            <Input v-model="taxiCarDriverForm.nation" placeholder="请输国籍"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="住址:" prop="address">
            <Input type="textarea" v-model="taxiCarDriverForm.address" placeholder="请输入住址"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="出生日期:" prop="born">
            <Date-picker type="date" placeholder="出生日期" value-format="yyyy-MM-dd"
                         v-model="taxiCarDriverForm.born" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="电话号码: " prop="phone">
            <Input v-model="taxiCarDriverForm.phone"
                   placeholder="请输入电话号码"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="资格证号: " prop="zgzh">
            <Input v-model="taxiCarDriverForm.zgzh"
                   placeholder="请输入资格证号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="准架车型:" prop="zjcx">
            <Input v-model="taxiCarDriverForm.zjcx"
                   placeholder="请输入准架车型"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="二维码区: " prop="qrcode">
            <Input v-model="taxiCarDriverForm.qrcode"
                   placeholder="请输入二维码区"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="服务单位: " prop="fwdw">
            <Input v-model="taxiCarDriverForm.fwdw"
                   placeholder="请输入服务单位"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="考核记录: " prop="khjl">
            <Input type="textarea" v-model="taxiCarDriverForm.khjl" placeholder="请输入考核记录"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="培训记录: " prop="pxjl">
            <Input type="textarea" v-model="taxiCarDriverForm.pxjl" placeholder="请输入培训记录"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="从业资格证类别: " prop="cyzglb">
            <Select v-model="taxiCarDriverForm.cyzglb" clearable
                    placeholder="">
              <Option :value="'1'" :key="'1'">{{
                  '道路旅客运输巡游、网约出租车'
                }}
              </Option>
              <Option :value="'2'" :key="'2'">{{
                  '道路旅客运输网约出租车'
                }}
              </Option>
              <Option :value="'3'" :key="'3'">{{
                  '道路旅客运输巡游出租车'
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="初次领证日期: " prop="cclzrq">
            <Date-picker type="date" placeholder="初次领证日期" value-format="yyyy-MM-dd"
                         v-model="taxiCarDriverForm.cclzrq" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="业户名称:" prop="yhmc">
            <Input v-model="taxiCarDriverForm.yhmc" placeholder="请输入业户名称"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="有效起始日期: " prop="yxqsrq">
            <Date-picker type="date" placeholder="有效起始日期" value-format="yyyy-MM-dd"
                         v-model="taxiCarDriverForm.yxqsrq" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="有效期限:" prop="yxqx">
            <Date-picker type="date" placeholder="有效期限" value-format="yyyy-MM-dd"
                         v-model="taxiCarDriverForm.yxqx" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="备注:" prop="remark">
            <Input type="textarea" v-model="taxiCarDriverForm.remark" placeholder="请输入备注"></Input>
          </Form-item>
        </Col>
      </Row>
    </Form>
    <Alert class="button">
<!--      <button @click="changeStepTo(1)">上一步</button>-->
      <button v-if="ywType==='1'" @click="handleApprove(1)">受理</button>
      <button v-if="ywType==='2'" @click="handleApprove(2)">审批</button>
      <button v-if="ywType==='2'&&taxiCarDriverForm.state==='2'" @click="handleApprove(0)">退回</button>
      <button v-if="ywType==='0'" @click="handleSubmit('taxiCarDriverForm')">提交</button>
<!--      <button @click="changeStepTo(3)">下一步</button>-->
    </Alert>
  </div>
</template>

<script>
import {responseUtil} from "@/utils/rongxunUtil";
import {approveBusinessData} from '../../../../api/taxi/taxiCompanyApi'
import {getUser} from "@/utils/auth"
import {saveTaxiCyzgzInfo, queryTaxiCyzgzDetailInfo} from "../../../../api/taxi/taxiDriver";

export default {
  name: "rx-information",
  components: {},

  props: {
    formDataContrnt: {
      default: {}
    },
    projectNo: '',
    sxbm: '', ywType: ''
  },
  mounted() {
    this.initDataByProjectNo()
  },
  created() {
    this.taxiCarDriverForm.operateNatureId = '39';
    this.taxiCarDriverForm.seating = '5人';
    this.taxiCarDriverForm.vehicleUseAgeLimit = '1';
  },
  data() {
    return {
      userId: '',
      userXzqh: '',
      taxiCarDriverForm: {
        name: '',
        sex: '',
        idcard: '',
        nation: '中国',
        address: '',
        born: '',
        phone: '',
        zgzh: '',
        zjcx: '',
        qrcode: '',
        fwdw: '',
        khjl: '',
        pxjl: '',
        cyzglb: '',
        yhmc: '',
        cclzrq: '',
        yxqsrq: '',
        yxqx: '',
        remark: '',

      },
      ruleValidate: {},
      isSave: false,
      baseData: {
        registArea: [],
        enterpriseState: []
      }
    }
  },
  methods: {
    handleSubmit(name) {
      this.$refs[name].validate((valid) => {
        var that = this
        if (valid) {
          let userInfo = getUser();
          let jsonData = {};
          that.taxiCarDriverForm.cclzrq = that.changeDate(that.taxiCarDriverForm.cclzrq)
          that.taxiCarDriverForm.born = that.changeDate(that.taxiCarDriverForm.born)
          that.taxiCarDriverForm.yxqsrq = that.changeDate(that.taxiCarDriverForm.yxqsrq)
          that.taxiCarDriverForm.yxqx = that.changeDate(that.taxiCarDriverForm.yxqx)
          jsonData['cyzgzInfo'] = that.taxiCarDriverForm;
          jsonData.userCode = userInfo.code;
          if (!that.isSave) {
            that.isSave = false
            saveTaxiCyzgzInfo(JSON.stringify(jsonData)).then(function (response) {
              responseUtil.dealResponse(that, response, () => {
                if (response.code == 0) {
                  that.$emit('setProjectNo', response.data);
                  that.taxiCarDriverForm.gid = response.data;
                  that.$Message.success('提交成功!');
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
      initData.gid = this.taxiCarDriverForm.gid;
      initData.sxlb = '6'
      initData.actionType = type
      initData.userCode = userInfo.code;
      approveBusinessData(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          if (response.code === "0") {
            that.$Message.success(response.data);
            if (type == 1) {
              closeThisView(that, '/taxiCarAdmin/taxiWycAccept_index')
            } else {
              closeThisView(that, '/taxiCarAdmin/taxiWycApprove_index')
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
      if (!this.projectNo || this.projectNo === '') {
        return
      }
      var that = this
      let initData = {}
      initData.projectNo = this.projectNo
      queryTaxiCyzgzDetailInfo(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          if (response.code === "0") {
            that.taxiCarDriverForm = response.data;
          } else {
            responseUtil.alertErrMsg(response.msg)
          }
        })
      })
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
