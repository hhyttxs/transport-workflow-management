<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="companyForm" :model="companyForm" :rules="ruleValidate" :label-width="190">
      <Row>
        <Col span="12">
          <Form-item label="管辖区域:" prop="areaRegionId">
            <Select v-model="companyForm.areaRegionId" :disabled="ywType==='1'||ywType==='2'"
                    @on-change="chooseAreaDistricList(0)" clearable
                    placeholder="请选择管辖区域" style="width: 49%">
              <Option v-for="item in areaRegionList" :value="item.AREAREGIONID" :key="item.AREAREGIONID">{{
                item.AREAREGIONNAME
                }}
              </Option>
            </Select>
            <Select v-model="companyForm.areaDistrictId" :disabled="ywType==='1'||ywType==='2'"
                    @on-change="changeAreaDistricList" clearable
                    placeholder="请选择管辖区域" style="width: 49%">
              <Option v-for="item in areaDistricList" :value="item.AREAREGIONID" :key="item.AREAREGIONID">{{
                item.AREAREGIONNAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="公司代码:" prop="companyCode">
            <Input v-model="companyForm.companyCode" placeholder="请输入公司代码" disabled></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="24">
          <Form-item label="公司工商核准名称:" prop="companyName">
            <Input v-model="companyForm.companyName" placeholder="请输入公司工商核准名称"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="有无法人单位:" prop="unitExist">
            <Select class="railwayApplyListSelect"
                    name="unitExist"
                    v-model="companyForm.unitExist">
              <Option value="0">无</Option>
              <Option value="1">有</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="公司地址:" prop="companyAddress">
            <Input v-model="companyForm.companyAddress" placeholder="请输入公司地址"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="许可证号:" prop="licenseNumber">
            <Input v-model="companyForm.licenseNumber" placeholder="请输入许可证号" disabled></Input>
          </Form-item>
        </Col>

      </Row>

      <Row>
        <Col span="12">
          <Form-item label="许可证有效起:" prop="licenseDateStart">
            <Date-picker type="date" placeholder="许可证有效起" value-format="yyyy-MM-dd"
                         v-model="companyForm.licenseDateStart" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="许可证有效止: " prop="licenseDateEnd">
            <Date-picker type="date" placeholder="许可证有效止" value-format="yyyy-MM-dd"
                         v-model="companyForm.licenseDateEnd" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="法定代表人: " prop="legalPersonName">
            <Input v-model="companyForm.legalPersonName" placeholder="请输入法定代表人"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="法人身份证:" prop="legalPersonId">
            <Input v-model="companyForm.legalPersonId" placeholder="请输入法人身份证"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="二级企业负责人: " prop="secondLeader">
            <Input v-model="companyForm.secondLeader" placeholder="请输入二级企业负责人"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="负责人身份证:" prop="leaderIdCard">
            <Input v-model="companyForm.leaderIdCard" placeholder="请输入负责人身份证"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="性别: " prop="sex">
            <Select v-model="companyForm.sex" clearable
                    placeholder="请选择性别">
              <Option value="1" key="1">男</Option>
              <Option value="0" key="0">女</Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="出生年月:" prop="birthday">
            <Date-picker v-model="companyForm.birthday" value-format="yyyy-MM-dd"
                         placeholder="请输入出生年月"></Date-picker>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="公司电话:" prop="contactNumber">
            <Input v-model="companyForm.contactNumber" placeholder="请输入公司电话"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="手机: " prop="mobilePhone">
            <Input v-model="companyForm.mobilePhone" placeholder="请输入手机"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="办事人:" prop="applyPerson">
            <Input v-model="companyForm.applyPerson" placeholder="请输入办事人"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="办事人身份证: " prop="applyPersonIdCard">
            <Input v-model="companyForm.applyPersonIdCard" placeholder="请输入办事人身份证"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="办事人手机:" prop="applyPersonPhone">
            <Input v-model="companyForm.applyPersonPhone" placeholder="请输入办事人手机"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="统一社会信用代码: " prop="organizationCode">
            <Input v-model="companyForm.organizationCode" placeholder="请输入统一社会信用代码"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="办理类型:" prop="bllx">
            <Select v-model="companyForm.bllx" clearable
                    placeholder="请选择管辖区域">
              <Option v-for="item in doType" :value="item.dicId" :key="item.dicId">{{
                item.dicValue
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="工商执照发证日期: " prop="gszcfzrq">
            <Date-picker v-model="companyForm.gszcfzrq" value-format="yyyy-MM-dd"
                         placeholder="请输入工商执照发证日期"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="经营范围:" prop="businessScope">
            <Checkbox v-model="companyForm.businessScope" true-value="1" false-value="0">流动</Checkbox>
            <Checkbox v-model="companyForm.businessScopeTow" true-value="1" false-value="0">接送站,旅游
            </Checkbox>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="经营区域: " prop="businessArea">
            <Input v-model="companyForm.businessArea" placeholder="请输入经营区域" disabled></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="经济性质:" prop="economicNatureId">
            <Select v-model="companyForm.economicNatureId" clearable
                    placeholder="请选择经济性质">
              <Option v-for="item in economicNatureList" :value="item.ECONNATUREID" :key="item.ECONNATUREID">{{
                item.ECONOMICNATURE
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="经营性质: " prop="businessNatureId">
            <Select v-model="companyForm.businessNatureId" clearable
                    placeholder="请选择经营性质">
              <Option v-for="item in businessNatureList" :value="item.BUSINNATUREID" :key="item.BUSINNATUREID">{{
                item.BUSINESSNATURE
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="号牌数量:" prop="brandAmount">
            <Input v-model="companyForm.brandAmount" placeholder="请输入号牌数量"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="车辆总数: " prop="taxiCount">
            <Input v-model="companyForm.taxiCount" placeholder="请输入车辆总数"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="号牌号段:" prop="brandSegment">
            <Input type="textarea" v-model="companyForm.brandSegment" placeholder="请输入号牌号段"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="受理日期:" prop="acceptanceDate">
            <Date-picker type="date" placeholder="请输入受理日期" value-format="yyyy-MM-dd"
                         v-model="companyForm.acceptanceDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="受理时限: " prop="acceptDate">
            <Date-picker type="date" placeholder="受理时限" value-format="yyyy-MM-dd"
                         v-model="companyForm.acceptDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="受理人:" prop="acceptancePerson">
            <Input v-model="companyForm.acceptancePerson" placeholder="请输入受理人"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="邮编: " prop="mail">
            <Input v-model="companyForm.mail" placeholder="请输入邮编"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="休业车辆总数:" prop="stopTaxiCount">
            <Input v-model="companyForm.stopTaxiCount" placeholder="请输入休业车辆总数"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="换型车辆总数: " prop="changeTaxiCount">
            <Input v-model="companyForm.changeTaxiCount" placeholder="请输入换型车辆总数"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="法院查封:" prop="fycf">
            <Button type="text">点击录入</Button>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="银行登记: " prop="yhdj">
            <Button type="text">点击录入</Button>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="变更记录:" prop="changeRecord">
            <Input v-model="companyForm.changeRecord" type="textarea" placeholder="请输入变更记录"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="备注:" prop="remark">
            <Input v-model="companyForm.remark" type="textarea" placeholder="请输入备注"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="合同类型:" prop="contractType">
            <Select v-model="contractForm.contractType" clearable
                    placeholder="请选择合同类型">
              <Option v-for="item in contractTypeList" :value="item.CONTRACTTYPEID" :key="item.CONTRACTTYPEID">{{
                item.CONTRACTNAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="合同对应号牌编号: " prop="contractNum">
            <Input v-model="contractForm.contractNum" placeholder="请输入合同对应号牌编号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="有效期起:" prop="contractStartDate">
            <Date-picker type="date" placeholder="有效期起" value-format="yyyy-MM-dd"
                         v-model="contractForm.contractStartDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="有效期止: " prop="contractEndDate">
            <Date-picker type="date" placeholder="有效期止" value-format="yyyy-MM-dd"
                         v-model="contractForm.contractEndDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="安全员:" prop="hon">
            <Input v-model="companyForm.hon" placeholder="请输入安全员"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="核发部门:" prop="grantDepart">
            <Input v-model="companyForm.grantDepart" placeholder="请输入核发部门"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="许可核发日期:" prop="grantDate">
            <Date-picker type="date" placeholder="许可核发日期" value-format="yyyy-MM-dd"
                         v-model="companyForm.grantDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="核发道路运输证日期: " prop="taxiLicenseDate">
            <Date-picker type="date" placeholder="核发道路运输证日期" value-format="yyyy-MM-dd"
                         v-model="companyForm.taxiLicenseDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
    </Form>
    <Alert class="button">
      <!--      <button @click="changeStepTo(1)">上一步</button>-->
      <button v-if="ywType==='1'" @click="handleApprove(1)">受理</button>
      <button v-if="ywType==='2'" @click="handleApprove(2)">审批</button>
      <button v-if="ywType==='2'&&companyForm.state==='2'" @click="handleApprove(0)">退回</button>
      <button v-if="ywType==='0'" @click="handleSubmit('companyForm')">提交</button>
      <!--      <button @click="changeStepTo(3)">下一步</button>-->
    </Alert>
  </div>
</template>

<script>
  import {saveOrUpdateAccept, browseAcceptByProjectNo} from '@/api/acceptFormCommon/acceptCommon'
  import {browseDatadictionaryByDtype, browseXzqhList} from '@/api/datadictionaryCommon/datadictionaryCommon'
  import {
    queryTaxiArea,
    queryContractType,
    queryEconomicNatureList,
    querySecondaryLinkage
  } from '@/api/taxi/taxiCarApi'
  import {getCompanyCode, saveCompanyInfo} from '@/api/taxi/taxiCompanyApi'
  import {getUser} from "@/utils/auth"
  import {closeThisView, responseUtil} from "@/utils/rongxunUtil";
  import {approveBusinessData, queryTaxiCompanyDetailInfo} from "../../../../api/taxi/taxiCompanyApi";
  import {checkDriverIdNum} from "../../../../api/taxi/taxiDriver";

  export default {
    name: "rx-information",
    components: {},

    props: {
      formDataContrnt: {
        default: {}
      },
      projectNo: '',
      sxbm: '',
      ywType: ''
    },
    mounted() {
      this.initTaxiArea();
      this.initEconomicNature();
      this.initContractType();
      this.initDataByProjectNo();
    },
    data() {
      const checkOrganizationCodel = (rule, value, callback) => {
        let express = /^([0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}|[1-9]\d{14})$/;
        if (value.search(express) == -1) {
          callback(new Error("社会统一信用代码格式不正确"))
        }
        callback()
      }
      return {
        userId: '',
        userXzqh: '',
        companyForm: {
          state: '',
          companyRecordId: '',
          areaRegionId: '',
          areaDistrictId: '',
          companyCode: '',
          companyName: '',
          unitExist: '',
          companyAddress: '',
          licenseNumber: '',
          licenseDateStart: '',
          licenseDateEnd: '',
          legalPersonName: '',
          legalPersonId: '',
          secondLeader: '',
          leaderIdCard: '',
          sex: '',
          birthday: '',
          contactNumber: '',
          mobilePhone: '',
          applyPerson: '',
          applyPersonIdCard: '',
          applyPersonPhone: '',
          organizationCode: '',
          gszcfzrq: '',
          bllx: '',
          businessScope: 0,
          businessScopeTow: 0,
          businessArea: '',
          economicNatureId: '',
          businessNatureId: '',
          brandAmount: '',
          taxiCount: '',
          brandSegment: '',
          acceptDate: '',
          acceptanceDate: '',
          acceptancePerson: '',
          mail: '',
          stopTaxiCount: '',
          changeTaxiCount: '',
          changeRecord: '',
          remark: '',
          hon: '',
          grantDepart: '',
          grantDate: '',
          taxiLicenseDate: ''
        },
        contractForm: {
          contractNum: '',
          contractType: '',
          contractStartDate: '',
          contractEndDate: ''
        },
        ruleValidate: {
          title: [
            {required: true, message: '申报标题不能为空', trigger: 'blur'}
          ],
          companyName: [
            {required: true, message: '企业名称不能为空', trigger: 'blur'}
          ],
          companyAddress: [
            {required: true, message: '公司地址不能为空', trigger: 'blur'}
          ],
          licenseDateStart: [
            {required: true, message: '许可证有效起不能为空', trigger: 'blur', type: 'date'}
          ],
          licenseDateEnd: [
            {required: true, message: '许可证有效止不能为空', trigger: 'blur', type: 'date'}
          ],
          legalPersonName: [
            {required: true, message: '法定代表人不能为空', trigger: 'blur'}
          ],
          legalPersonId: [
            {required: true, message: '法人身份证不能为空', trigger: 'blur'},
            {
              trigger: 'blur',
              validator: (rule, value, cb) => {
                this.checkDriverIdNum(rule, value, cb)
              }
            }
          ],
          contactNumber: [
            {required: true, message: '公司电话不能为空', trigger: 'blur'}
          ],
          mobilePhone: [
            {required: true, message: '手机不能为空', trigger: 'blur'}
          ],
          organizationCode: [
            {required: true, message: '统一社会信用代码不能为空', trigger: 'blur'},
            {required: true, trigger: 'blur', validator: checkOrganizationCodel}
          ],
          gszcfzrq: [
            {required: true, message: '工商执照发证日期不能为空', trigger: 'blur', type: 'date'}
          ],
          mail: [
            {required: true, message: '邮编不能为空', trigger: 'blur'}
          ],
          contactPhone: [
            {required: true, message: '联系人手机号不能为空', trigger: 'blur'}
          ],
          // ,
          // desc: [
          //   { required: true, message: '请输入个人介绍', trigger: 'blur' },
          //   { type: 'string', min: 20, message: '介绍不能少于20字', trigger: 'blur' }
          // ]
        },
        isSave: false,
        areaRegionList: [], areaDistricList: [], economicNatureList: [],
        businessNatureList: [
          {
            'BUSINNATUREID': '39',
            'BUSINESSNATURE': '专营'
          }, {
            'BUSINNATUREID': '40',
            'BUSINESSNATURE': '兼营'
          }
        ],
        contractTypeList: [],
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

      }
    },
    created() {
      this.companyForm.bllx = this.doType[0].dicId;
      this.companyForm.businessNatureId = this.businessNatureList[0].BUSINNATUREID;
    },
    methods: {
      checkDriverIdNum(rule, value, callback) {
        let that = this
        let initData = {}
        initData.driverIdNum = value;
        initData.type = 'company';
        checkDriverIdNum(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === '0') {
              let r_code = response.data.code;
              let r_msg = response.data.msg;
              let driverSex = response.data.driverSex;
              let driverBirth = response.data.driverBirth;
              if (r_code == '0') {
                that.companyForm.sex = '';
                that.companyForm.birthday = '';
                callback(new Error(r_msg));
              } else {
                if (driverSex == '男') {
                  that.companyForm.sex = '1';
                } else {
                  that.companyForm.sex = '0';
                }
                that.companyForm.birthday = driverBirth;
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
            let jsonData = {};
            that.companyForm.licenseDateStart = that.changeDate(that.companyForm.licenseDateStart)
            that.companyForm.licenseDateEnd = that.changeDate(that.companyForm.licenseDateEnd)
            that.companyForm.gszcfzrq = that.changeDate(that.companyForm.gszcfzrq)
            that.companyForm.acceptDate = that.changeDate(that.companyForm.acceptDate)
            that.companyForm.acceptanceDate = that.changeDate(that.companyForm.acceptanceDate)
            that.companyForm.grantDate = that.changeDate(that.companyForm.grantDate)
            that.companyForm.taxiLicenseDate = that.changeDate(that.companyForm.taxiLicenseDate)
            that.contractForm.contractStartDate = that.changeDate(that.contractForm.contractStartDate)
            that.contractForm.contractEndDate = that.changeDate(that.contractForm.contractEndDate)
            jsonData['companyRecord'] = that.companyForm;
            jsonData['contract'] = that.contractForm;
            jsonData.userCode = userInfo.code;
            if (!that.isSave) {
              that.isSave = false
              saveCompanyInfo(JSON.stringify(jsonData)).then(function (response) {
                responseUtil.dealResponse(that, response, () => {
                  if (response.code == 0) {
                    that.$emit('setProjectNo', response.data);
                    that.companyForm.companyRecordId = response.data;
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
        initData.gid = this.companyForm.companyRecordId;
        initData.sxlb = '1'
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
      initDataByProjectNo() {
        if (!this.projectNo || this.projectNo === '') {
          return
        }
        let that = this
        let initData = {}
        initData.projectNo = this.projectNo
        queryTaxiCompanyDetailInfo(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.companyForm = response.data.companyRecord;
              that.contractForm = response.data.contract;
              that.chooseAreaDistricList(1);
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
      handleReset(name) {
        this.$refs[name].resetFields();
      },
      changeStepTo(number) {
        this.$emit('changeStep', number)
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
      chooseAreaDistricList(type) {
        let areaRegionId = this.companyForm.areaRegionId;
        let that = this
        let initData = {}
        initData.type = 1;
        initData.selectClass_id = areaRegionId;
        querySecondaryLinkage(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.areaDistricList = response.data
              if (type == 0) {
                that.companyForm.areaDistrictId = response.data[0].AREAREGIONID
                that.getCompanyCode(response.data[0].AREAREGIONID);
              }
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      changeAreaDistricList(type) {
        let areaRegionId = this.companyForm.areaDistrictId;
        this.getCompanyCode(areaRegionId);
      },
      getCompanyCode(select_id) {
        let that = this;
        let initData = {}
        initData.select_areaRegion = select_id;
        getCompanyCode(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.companyForm.businessArea = response.data.businessArea;
              that.companyForm.companyCode = response.data.companyCode;
              that.companyForm.licenseNumber = response.data.licenseNumber;
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initEconomicNature() {
        let that = this;
        let initData = {}
        queryEconomicNatureList(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.economicNatureList = response.data
              that.companyForm.economicNatureId = response.data[0].ECONNATUREID
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initContractType() {
        let that = this;
        let initData = {}
        queryContractType(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.contractTypeList = response.data
              that.contractForm.contractType = response.data[0].CONTRACTTYPEID
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      }
    }
  }
</script>
