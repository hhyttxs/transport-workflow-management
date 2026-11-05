<style lang="less">
@import "../../rx-label/rx-label.less";
@import "./information.less";
</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="港口经营人:" prop="portOperator">
            <Input v-model="formValidate.portOperator" placeholder="请输入申请单位名称"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="统一社会信用代码:" prop="tyshxydm">
            <Input v-model="formValidate.tyshxydm" placeholder="请输入申请单位名称"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="法定代表人（负责人）:" prop="fddbr">
            <Input v-model="formValidate.fddbr" placeholder="请输入法定代表人（负责人）"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="联系电话:" prop="phone">
            <Input v-model="formValidate.phone" placeholder="请输入联系电话"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="法定代表人身份证号:" prop="idCardNum">
            <Input v-model="formValidate.idCardNum" placeholder="请输入法定代表人身份证号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="是否为外商独资企业:" prop="wsdjqy">
            <Input v-model="formValidate.wsdjqy" placeholder="请输入是否为外商独资企业"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="企业经济类型:" prop="jjType">
            <Input v-model="formValidate.jjType" placeholder="请输入企业经济类型"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="注册资本:" prop="zczb">
            <Input v-model="formValidate.zczb" placeholder="请输入注册资本"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="注册地址:" prop="address">
            <Input v-model="formValidate.address" placeholder="请输入注册地址"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="港口从业人员数量:" prop="peopleNum">
            <Input v-model="formValidate.peopleNum" placeholder="请输入港口从业人员数量"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="经办人:" prop="jbr">
            <Input v-model="formValidate.jbr" placeholder="请输入经办人"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="联系电话:" prop="jbrPhone">
            <Input v-model="formValidate.jbrPhone" placeholder="请输入联系电话"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="传真:" prop="conFax">
            <Input v-model="formValidate.conFax" placeholder="请输入传真"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="邮箱:" prop="conMail">
            <Input v-model="formValidate.conMail" placeholder="请输入邮箱"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="24">
          <Form-item label="办公地址:" prop="conFax">
            <Input v-model="formValidate.bgdz" placeholder="请输入办公地址"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px" v-if="sxlb=='port_sx_gzl_1'">
        <Col span="24">
          <Form-item label="码头及其他港口设施服务:" prop="proxyManageList">
            <CheckboxGroup v-model="formValidate.jyyw">
              <Checkbox label="1">
                <span>为船舶提供码头设施</span>
              </Checkbox>
              <Checkbox label="2">
                <span>提供过驳锚地</span>
              </Checkbox>
              <Checkbox label="3">
                <span>提供浮筒</span>
              </Checkbox>
            </CheckboxGroup>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px" v-if="sxlb=='port_sx_gzl_1'">
        <Col span="24">
          <Form-item label="港口旅客运输服务:" prop="proxyManageList">
            <CheckboxGroup v-model="formValidate.jyyw">
              <Checkbox label="4">
                <span>为旅客提供候船和上下船设施服务 </span>
              </Checkbox>
            </CheckboxGroup>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px" v-if="sxlb=='port_sx_gzl_1'">
        <Col span="24">
          <Form-item label="货物装卸、驳运、仓储服务:" prop="proxyManageList">
            <CheckboxGroup v-model="formValidate.jyyw">
              <Checkbox label="5">
                <span>在港区内提供货物装卸（<Checkbox label="6"><span>含过驳</span></Checkbox>）服务 </span>
              </Checkbox>
              <Checkbox label="7">
                <span>在港区内提供货物仓储服务</span>
              </Checkbox>
              <Checkbox label="8">
                <span>在港区内提供货物驳运服务</span>
              </Checkbox>
            </CheckboxGroup>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px" v-if="sxlb=='port_sx_gzl_1'">
        <Col span="24">
          <Form-item label="港口拖轮服务:" prop="proxyManageList">
            <CheckboxGroup v-model="formValidate.jyyw">
              <Checkbox label="9">
                <span>为船舶进出港、靠离码头、移泊提供顶推、拖带服务 </span>
              </Checkbox>
            </CheckboxGroup>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px" v-if="sxlb=='port_sx_gzl_2'">
        <Col span="24">
          <Form-item label="船舶港口服务:" prop="proxyManageList">
            <CheckboxGroup v-model="formValidate.jyyw">
              <Checkbox label="10" :disabled="isShowAd">
                <span>为船舶提供岸电服务</span>
              </Checkbox>
              <Checkbox label="11" :disabled="isShowQt">
                <span>提供水上船员接送服务</span>
              </Checkbox>
              <Checkbox label="12" :disabled="isShowQt">
                <span>提供物料供应服务</span>
              </Checkbox>
              <Checkbox label="13" :disabled="isShowQt">
                <span>提供淡水供应服务</span>
              </Checkbox>
              <Checkbox label="14" :disabled="isShowQt">
                <span>提供生活品供应服务（是否含酒水食品，<Checkbox label="15"
                                                                  :disabled="isShowQt"><span>是</span></Checkbox><Checkbox
                  label="16" :disabled="isShowQt"><span>否</span></Checkbox>）</span>
              </Checkbox>
              <br>
              <Checkbox label="17" :disabled="isShowQt">
                <span>提供（<Checkbox label="18" :disabled="isShowQt"><span>国内</span></Checkbox><Checkbox
                  label="19" :disabled="isShowQt"><span>国际</span></Checkbox>）船舶燃料供应服务（<Checkbox
                  label="20" :disabled="isShowQt"><span>含保税油</span></Checkbox>）</span>
              </Checkbox>
              <Checkbox label="21" :disabled="isShowQt">
                <span>提供船舶污染物（船舶生活垃圾除外）接收服务</span>
              </Checkbox>
              <Checkbox label="22" :disabled="isShowQt">
                <span>提供船舶生活垃圾接收服务</span>
              </Checkbox>
              <Checkbox label="23" :disabled="isShowQt">
                <span>提供围油栏供应服务</span>
              </Checkbox>
            </CheckboxGroup>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px" v-if="sxlb=='port_sx_gzl_2'">
        <Col span="24">
          <Form-item label="港口设施、设备和机械的租赁、维修服务:" prop="proxyManageList">
            <CheckboxGroup v-model="formValidate.jyyw">
              <Checkbox label="24" :disabled="isShowQt">
                <span>港口设施、设备和机械的租赁服务</span>
              </Checkbox>
              <Checkbox label="25" :disabled="isShowQt">
                <span>港口设施、设备和机械的维修服务</span>
              </Checkbox>
            </CheckboxGroup>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px" v-if="sxlb=='port_sx_gzl_2'">
        <Col span="24">
          <Form-item label="港口理货服务" prop="proxyManageList">
            <CheckboxGroup v-model="formValidate.jyyw">
              <Checkbox label="26" :disabled="isShowQt">
                <span>港口理货服务</span>
              </Checkbox>
            </CheckboxGroup>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="24">
          <Form-item label="从事港口危险货物作业" prop="dangerJob">
            <RadioGroup v-model="formValidate.dangerJob">
              <Radio label="1">
                <span>是</span>
              </Radio>
              <Radio label="2">
                <span>否</span>
              </Radio>
            </RadioGroup>
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
      <Row style="margin-top: 5px" v-if="sxlb_code==='2'||sxlb_code==='3'">
        <Col span="24">
          <Form-item label="经营范围:" prop="jyfw">
            <Input type="textarea" v-model="formValidate.jyfw" placeholder="请输入经营范围"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px" v-if="sxlb_code==='2'||sxlb_code==='3'">
        <Col span="24">
          <Form-item label="经营地域:" prop="jyArea">
            <Input type="textarea" v-model="formValidate.jyArea" placeholder="请输入经营地域"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px" v-if="sxlb_code==='2'||sxlb_code==='3'">
        <Col span="12">
          <Form-item label="许可证号:" prop="xkzh">
            <Input v-model="formValidate.xkzh" placeholder="请输入许可证号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="有效期截止:" prop="yxqjz">
            <Date-picker type="date" placeholder="请输入有效期截止" value-format="yyyy-MM-dd"
                         v-model="formValidate.yxqjz" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
    </Form>
    <Alert v-if="sxlb_code==='0'" style="text-align: center;color:#dc143c;margin-top: 5px" type="error">
      提醒：点击下一步之前请先暂存信息，否则数据会丢失。
    </Alert>
    <Alert class="button">
      <button @click="changeStepTo(1)">上一步</button>
      <button style="width: 110px" v-if="sxlb_code==='1'" @click="handleApprove(1)">受理通过</button>
      <button v-if="sxlb_code==='1'" @click="handleApprove(-1)">驳回</button>
      <button v-if="sxlb_code==='2'" @click="handleSubmit('formValidate',2)">审批</button>
      <button v-if="sxlb_code==='2'&&formValidate.state==='2'" @click="handleApprove(0)">退回</button>
      <button style="width: 110px" @click="changeStepTo(3)">查看材料</button>
    </Alert>
  </div>
</template>

<script>
import {closeThisView, responseUtil} from "@/utils/rongxunUtil";
import {getUser} from "@/utils/auth"
import {saveOrUpdatePortManageBook, queryPortManageBook} from "../../../../api/port/portManage";
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
    sxlb_code: '',
    sxlb: '',
  },
  mounted() {
    let userInfo = getUser();
    let xzqh = userInfo.jgxh
    if (xzqh != '210200') {
      this.isShowQt = true
    }
    this.formValidate.registArea = userInfo.xzqhCode
    this.formValidate.manageArea = userInfo.xzqhCode
    this.initDataByProjectNo()
  },
  data() {
    return {
      isShowAd: false,
      isShowQt: false,
      userId: '',
      userXzqh: '',
      formValidate: {
        gid: '',
        state: '',
        ywType: '',
        id: '',
        portOperator: '',//港口经营人
        tyshxydm: '',//统一社会信用代码
        fddbr: '',//法定代表人（负责人）
        phone: '',//联系电话
        idCardNum: '',//法定代表人身份证号
        wsdjqy: '',//是否为外商独资企业
        jjType: '',//企业经济类型
        zczb: '',//注册资本
        address: '',//地址
        peopleNum: '',//港口从业人员数量
        jbr: '',//经办人
        jbrPhone: '',//联系电话
        conFax: '',
        conMail: '',
        jyyw: [],//经营业务
        dangerJob: '',//从事港口危险货物作业
        jyArea: '',//经营地域
        jyfw: '',//经营范围
        backRemark: '', xkzh: '', yxqjz: '', bgdz: '',
      },
      ruleValidate: {
        jyArea: [
          {required: true, message: '经营地域不能为空', trigger: 'blur'}
        ], jyfw: [
          {required: true, message: '经营范围不能为空', trigger: 'blur'}
        ], xkzh: [
          {required: true, message: '许可证号不能为空', trigger: 'blur'}
        ], yxqjz: [
          {required: true, message: '有效期截止日期不能为空', trigger: 'blur', type: 'date'}
        ]
      },
      isSave: false,
      baseData: {
        registArea: [],
        enterpriseState: []
      },
      options1: {
        disabledDate(date) {
          let before = date && date.valueOf() < Date.now() - 86400000
          let weekend = date && (date.getDay() === 0 || date.getDay() === 6)
          return before || weekend
        }
      },
    }
  },
  methods: {
    handleSubmit(name, type) {
      this.$refs[name].validate((valid) => {
        let that = this
        if (valid) {
          let initData = that.formValidate
          initData.spzt = 1
          saveOrUpdatePortManageBook(initData).then(function (response) {
            responseUtil.dealResponse(that, response, () => {
              if (response.code == 0) {
                that.$emit('setProjectNo', response.data);
                that.formValidate.id = response.data;
                if (type == 2) {
                  that.handleApprove(2);
                } else {
                  that.$Message.success('保存成功!');
                }
              } else {
                that.isSave = false
              }
            })
          })
        } else {
          that.$Message.error('表单验证失败!');
        }
      })
    },
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
      initData.sxlb = this.sxlb
      initData.actionType = type
      initData.userCode = userInfo.code;
      initData.backRemark = this.formValidate.backRemark;
      approveWaterWayBusinessData(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          if (response.code === "0") {
            that.$Message.success(response.data);
            if (type == 1 || type == -1) {
              closeThisView(that, '/portManage/GKSXSL_index')
            } else {
              closeThisView(that, '/portManage/GKSXSP_index')
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
      this.$emit('getJyfw', this.formValidate.jyyw)
      this.$emit('changeStep', number)
    },
    initDataByProjectNo() {
      console.log('initDataByProjectNo')
      if (!this.projectNo || this.projectNo === '') {
        return
      }
      var that = this
      let initData = {}
      initData.projectNo = that.projectNo
      initData.sxlb_code = that.sxlb_code
      queryPortManageBook(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          if (response.code === "0") {
            console.log(response.data)
            that.formValidate = response.data
            let split = response.data.jyyw.split(',');
            let filter = split.filter(i => i != '');
            that.formValidate.jyyw = filter
            if (that.formValidate.yxqjz == null || that.formValidate.yxqjz == '') {
              that.formValidate.yxqjz = new Date();
            }
          } else {
            responseUtil.alertErrMsg(response.msg)
          }
        })
      })
    },
  }
}
</script>
