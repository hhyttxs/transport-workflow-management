<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Divider orientation="left">添加驾驶员</Divider>
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="taxiCarDriverForm" :model="taxiCarDriverForm" :rules="ruleValidate" :label-width="190">
      <Input v-show="false" v-model="taxiCarDriverForm.driverId"></Input>
      <Input v-show="false" v-model="taxiCarDriverForm.taxiArchiveId"></Input>
      <Row>
        <Col span="12">
          <Form-item label="资格证国:" prop="certificateCountry">
            <Input v-model="taxiCarDriverForm.certificateCountry" disabled placeholder="请输资格证国"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="资格证市:" prop="certifNumCity">
            <Input v-model="taxiCarDriverForm.certifNumCity" disabled placeholder="请输资格证市"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="身份证号:" prop="driverIdNum">
            <Input v-model="taxiCarDriverForm.driverIdNum" placeholder="请输身份证号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="驾驶证号:" prop="driverLicenseNum">
            <Input v-model="taxiCarDriverForm.driverLicenseNum" placeholder="请输驾驶证号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="地区:" prop="areaRegionId">
            <Row>
              <Col span="12">
                <Select v-model="taxiCarDriverForm.areaRegionId" :disabled="ywType==='1'||ywType==='2'" clearable
                        @on-change="changeCarTypeMain(1)"
                        placeholder="请选择地区">
                  <Option v-for="item in areaRegionList" :value="item.AREAREGIONID" :key="item.AREAREGIONID">{{
                    item.AREAREGIONNAME
                    }}
                  </Option>
                </Select>
              </Col>
              <Col span="12">
                <Select v-model="taxiCarDriverForm.areaDistrictId" :disabled="ywType==='1'||ywType==='2'" clearable
                        @on-change="createCertificateNum(taxiCarDriverForm.areaDistrictId)"
                        placeholder="请选择地区">
                  <Option v-for="item in areaDistricList" :value="item.AREAREGIONID" :key="item.AREAREGIONID">{{
                    item.AREAREGIONNAME
                    }}
                  </Option>
                </Select>
              </Col>
            </Row>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="性别:" prop="driverSex">
            <Select v-model="taxiCarDriverForm.driverSex" disabled clearable
                    placeholder="">
              <Option :value="'男'" :key="'男'"></Option>
              <Option :value="'女'" :key="'女'"></Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="驾驶员姓名:" prop="driverName">
            <Input v-model="taxiCarDriverForm.driverName" placeholder="请输驾驾驶员姓名"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="出生日期: " prop="driverBirth">
            <Date-picker type="date" placeholder="出生日期" disabled="" value-format="yyyy-MM-dd"
                         v-model="taxiCarDriverForm.driverBirth" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="学员证号:" prop="studentNum">
            <Input v-model="taxiCarDriverForm.studentNum"
                   placeholder="请输入学员证号" maxlength="20"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="地址: " prop="driverAddress">
            <Input v-model="taxiCarDriverForm.driverAddress"
                   placeholder="请输入地址"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="车牌号: " prop="taxiPlateNum">
            <Input v-model="taxiCarDriverForm.taxiPlateNum"
                   placeholder="请输入车牌号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="资格证号:" prop="certificateNum">
            <Input v-model="taxiCarDriverForm.certificateNum"
                   placeholder="请输入资格证号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="初次打印时间: " prop="firstPrintTime">
            <Date-picker type="date" placeholder="初次打印时间" value-format="yyyy-MM-dd"
                         v-model="taxiCarDriverForm.firstPrintTime" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="发证日期:" prop="certificateDate">
            <Date-picker type="date" placeholder="发证日期" value-format="yyyy-MM-dd"
                         v-model="taxiCarDriverForm.certificateDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="开始有效期: " prop="startDate">
            <Date-picker type="date" placeholder="开始有效期" value-format="yyyy-MM-dd"
                         v-model="taxiCarDriverForm.startDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="终止有效期:" prop="endDate">
            <Date-picker type="date" placeholder="终止有效期" value-format="yyyy-MM-dd"
                         v-model="taxiCarDriverForm.endDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="电话: " prop="driverTel">
            <Input v-model="taxiCarDriverForm.driverTel" placeholder="请输入电话"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="服务单位:" prop="serveCompany">
            <Input v-model="taxiCarDriverForm.serveCompany" placeholder="请输入服务单位"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="主替班:" prop="hostOrRepClass">
            <Select v-model="taxiCarDriverForm.hostOrRepClass" clearable
                    placeholder="主替班">
              <Option :value="'0'" :key="'0'">主班</Option>
              <Option :value="'1'" :key="'1'">替班</Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="业户名称: " prop="businessName">
            <Input v-model="taxiCarDriverForm.businessName" placeholder="请输入业户名称"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="备注:" prop="remarks">
            <Input type="textarea" v-model="taxiCarDriverForm.remarks" placeholder="请输入备注"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="驾龄:" prop="drivingYears">
            <Input v-model="taxiCarDriverForm.drivingYears" placeholder="请输入驾龄"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="考核成绩: " prop="testScore">
            <Select class="railwayApplyListSelect"
                    v-model="taxiCarDriverForm.testScore">
              <Option value=""></Option>
              <Option value="1">是</Option>
              <Option value="2">否</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="积分: " prop="integral">
            <Input v-model="taxiCarDriverForm.integral" placeholder="请输入积分" disabled></Input>
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
  import {responseUtil, closeThisView} from "@/utils/rongxunUtil";
  import {
    queryTaxiArea,
    querySecondaryLinkage
  } from '@/api/taxi/taxiCarApi'
  import {approveBusinessData} from "@/api/taxi/taxiCompanyApi";
  import {getUser} from "@/utils/auth"
  import {
    saveTaxiDriverInfo,
    checkDriverIdNum,
    createCertificateNum,
    queryTaxiDriverDetailInfo, queryHomeOrReplaceState
  } from "../../../../api/taxi/taxiDriver";

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
      this.initTaxiArea();
      this.initDataByProjectNo()
    },
    data() {
      const checkDriverTel = (rule, value, callback) => {
        let express = /^[1-9]\d*$/;
        if (value.search(express) == -1) {
          callback(new Error("电话输入有误"))
        }
        callback()
      }
      return {
        userId: '',
        userXzqh: '',
        taxiCarDriverForm: {
          driverId: '', state: '', enteringPeople: '',
          taxiArchiveId: '',
          areaDistrictId: '',
          certificateCountry: '',
          certifNumCity: '',
          driverIdNum: '',
          driverLicenseNum: '',
          driverSex: '',
          driverName: '',
          driverBirth: '',
          studentNum: '',
          driverAddress: '',
          certificateNum: '',
          taxiPlateNum: '辽B',
          firstPrintTime: new Date(),
          certificateDate: new Date(),
          startDate: new Date(),
          endDate: new Date(new Date().getFullYear() + 6 + '-' + new Date().getMonth() + '-' + new Date().getDate()),
          serveCompany: '',
          driverTel: '',
          hostOrRepClass: 0,
          businessName: '',
          remarks: '',
          drivingYears: '',
          testScore: '',
          integral: '0',
        },
        ruleValidate: {
          driverIdNum: [{required: true, message: '身份证号不能为空', trigger: 'blur'},
            {
              trigger: 'blur',
              validator: (rule, value, cb) => {
                this.checkDriverIdNum(rule, value, cb)
              }
            }],
          taxiPlateNum: [
            {
              trigger: 'blur',
              validator: (rule, value, cb) => {
                this.checkTaxiPlateNum(rule, value, cb)
              }
            }],
          driverName: [{required: true, message: '驾驶员姓名不能为空', trigger: 'blur'}],
          driverAddress: [{required: true, message: '地址不能为空', trigger: 'blur'}],
          certificateNum: [{required: true, message: '资格证号不能为空', trigger: 'blur'}],
          driverTel: [{required: true, message: '电话不能为空', trigger: 'blur'},
            {required: true, message: '电话输入有误', trigger: 'blur', validator: checkDriverTel}
          ],
          areaRegionId: [
            {required: true, message: '管辖区域不能为空', trigger: 'blur'},
            {required: true, message: '管辖区域不能为空', trigger: 'change'}
          ],
          areaDistrictId: [
            {required: true, message: '管辖区域不能为空', trigger: 'blur'},
            {required: true, message: '管辖区域不能为空', trigger: 'change'}
          ]
        },
        isSave: false,
        baseData: {
          registArea: [],
          enterpriseState: []
        },
        areaRegionList: [],
        areaDistricList: [],
      }
    },
    methods: {
      checkDriverIdNum(rule, value, callback) {
        let that = this
        let initData = {}
        initData.driverIdNum = value;
        checkDriverIdNum(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === '0') {
              let r_code = response.data.code;
              let r_msg = response.data.msg;
              let driverSex = response.data.driverSex;
              let driverBirth = response.data.driverBirth;
              if (r_code == '0') {
                that.taxiCarDriverForm.driverSex = '';
                that.taxiCarDriverForm.driverBirth = '';
                that.taxiCarDriverForm.driverLicenseNum = '';
                callback(new Error(r_msg));
              } else {
                that.taxiCarDriverForm.driverSex = driverSex;
                that.taxiCarDriverForm.driverBirth = driverBirth;
                that.taxiCarDriverForm.driverLicenseNum = value;
                callback()
              }
            } else {
              callback()
            }
          })
        })
      },
      checkTaxiPlateNum(rule, value, callback) {
        let that = this
        let initData = {}
        initData.taxiPlateNum = value;
        initData.hostOrRepClass = that.taxiCarDriverForm.hostOrRepClass;
        queryHomeOrReplaceState(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {

            if (response.code === '0') {
              let r_code = response.data.code;
              let r_msg = response.data.msg;
              if (r_code == '0') {
                callback(new Error(r_msg));
              } else {
                callback()
              }
            } else {
              callback()
            }
          })
        })
      },
      handleSubmit(name) {
        this.$refs[name].validate((valid) => {
          var that = this
          if (valid) {
            let userInfo = getUser();
            that.taxiCarDriverForm.driverBirth = that.changeDate(that.taxiCarDriverForm.driverBirth)
            that.taxiCarDriverForm.firstPrintTime = that.changeDate(that.taxiCarDriverForm.firstPrintTime)
            that.taxiCarDriverForm.certificateDate = that.changeDate(that.taxiCarDriverForm.certificateDate)
            that.taxiCarDriverForm.startDate = that.changeDate(that.taxiCarDriverForm.startDate)
            that.taxiCarDriverForm.endDate = that.changeDate(that.taxiCarDriverForm.endDate)
            that.taxiCarDriverForm.enteringPeople = userInfo.code
            let initData = that.taxiCarDriverForm
            if (!that.isSave) {
              that.isSave = false
              saveTaxiDriverInfo(initData).then(function (response) {
                responseUtil.dealResponse(that, response, () => {
                  if (response.code == 0) {
                    that.$emit('setProjectNo', response.data);
                    that.taxiCarDriverForm.driverId = response.data;
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
        initData.gid = this.taxiCarDriverForm.driverId;
        initData.sxlb = '5'
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
        queryTaxiDriverDetailInfo(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.taxiCarDriverForm = response.data;
              that.changeCarTypeMain(2);
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initTaxiArea() {
        var that = this
        let initData = {}
        queryTaxiArea(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.areaRegionList = response.data
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      changeCarTypeMain(type) {
        let that = this
        let initData = {}
        initData.type = 1;
        initData.selectClass_id = this.taxiCarDriverForm.areaRegionId;
        querySecondaryLinkage(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              if (type == 1) {
                that.areaDistricList = response.data
                that.taxiCarDriverForm.areaDistrictId = response.data[0].AREAREGIONID
                that.createCertificateNum(response.data[0].AREAREGIONID);
              } else {
                that.areaDistricList = response.data
              }
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
      createCertificateNum(areaDistrictId) {
        var that = this
        let initData = {}
        initData.areaRegionId = areaDistrictId;
        createCertificateNum(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.taxiCarDriverForm.certificateCountry = response.data.certificateCountry
              that.taxiCarDriverForm.certifNumCity = response.data.certifNumCity
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      }
    }
  }
</script>
