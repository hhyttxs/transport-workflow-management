<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
      <Divider orientation="left">国内水路运输辅助企业备案表</Divider>
      <Row>
        <Col span="24">
          <Form-item label="备案证书号:" prop="fileLceNo">
            <Input v-model="formValidate.fileLceNo" placeholder="请输入备案证书号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="企业名称:" prop="companyName">
            <Input v-model="formValidate.companyName" placeholder="请输入企业名称"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="地址:" prop="companyAddress">
            <Input v-model="formValidate.companyAddress" placeholder="请输入地址" type="textarea"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="工商登记注册日期: " prop="registDate">
            <Date-picker type="date" placeholder="工商登记注册日期" value-format="yyyy-MM-dd hh:mm:ss"
                         v-model="formValidate.registDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="工商登记注册号:" prop="registNo">
            <Input v-model="formValidate.registNo" placeholder="请输入工商登记注册号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="企业类型: " prop="companyType">
            <Input v-model="formValidate.companyType" placeholder="请输入企业类型"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="注册资金:" prop="registMoney">
            <Input v-model="formValidate.registMoney" placeholder="请输入注册资金"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Row>
            <Col span="3">{{'出资人'}}</Col>
            <Col span="21">
              <Row>
                <Col span="24"><Input v-model="formValidate.contributor" placeholder="请输入出资人"></Input></Col>
              </Row>
              <Row>
                <Col span="24"><Input v-model="formValidate.contributorOne" placeholder="请输入出资人"></Input></Col>
              </Row>
            </Col>
          </Row>
        </Col>
        <Col span="12">
          <Row>
            <Col span="3">{{'出资比例'}}</Col>
            <Col span="21">
              <Row>
                <Col span="24"><Input v-model="formValidate.contributorPercent" placeholder="请输入出资比例"></Input></Col>
              </Row>
              <Row>
                <Col span="24"><Input v-model="formValidate.contributorPercentOne" placeholder="请输入出资比例"></Input></Col>
              </Row>
            </Col>
          </Row>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="法定代表人:" prop="fddbr">
            <Input v-model="formValidate.fddbr" placeholder="请输入法定代表人"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="联系人:" prop="linkMan">
            <Input v-model="formValidate.linkMan" placeholder="请输入联系人"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Row>
            <Col span="24">
              <Form-item label="办公电话:" prop="phone">
                <Input v-model="formValidate.phone" placeholder="请输入办公电话"></Input>
              </Form-item>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <Form-item label="手机号码:" prop="mobile">
                <Input v-model="formValidate.mobile" placeholder="请输入手机号码"></Input>
              </Form-item>
            </Col>
          </Row>

        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="传真: " prop="fax">
            <Input v-model="formValidate.fax" placeholder="请输入传真"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="电子邮箱:" prop="email">
            <Input v-model="formValidate.email" placeholder="请输入电子邮箱"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="提交材料:" prop="supportMaterialList">
            <CheckboxGroup v-model="formValidate.supportMaterialList">
              <Row v-for="item in supportMaterialLists">
                <Col span="24">
                  <Checkbox :label="item.value">
                    <span>{{item.title}}</span>
                  </Checkbox>
                </Col>
              </Row>
            </CheckboxGroup>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="代理业务范围:">
            <RadioGroup v-model="formValidate.proxyManageList">
              <Radio label="船舶代理业务"></Radio>
              <Radio label="货运代理业务"></Radio>
              <Radio label="客运代理业务"></Radio>
            </RadioGroup>
          </Form-item>

        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Row>
            <Col span="24">
              {{formValidate.tempOne}}
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <Form-item label="企业法定代表人签字:" prop="">
                <Input v-model="formValidate.fddbrSignAture" placeholder="" type="textarea"></Input>
              </Form-item>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <Date-picker type="date" placeholder="签字日期" value-format="yyyy-MM-dd hh:mm:ss"
                           v-model="formValidate.fddbrSignAtureDate" class="searchItem"></Date-picker>
            </Col>
          </Row>
        </Col>
        <Col span="12">
          <Row>
            <Col span="24">
              <Form-item label="市级水路运输管理机构意见:" prop="">
                <Input v-model="formValidate.tempTwo" placeholder="" type="textarea"></Input>
              </Form-item>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <Form-item label="负责人签字:" prop="">
                <Input v-model="formValidate.fzrSignAture" placeholder="" type="textarea"></Input>
              </Form-item>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <Date-picker type="date" placeholder="签字日期" value-format="yyyy-MM-dd hh:mm:ss"
                           v-model="formValidate.fzrSignAtureDate" class="searchItem"></Date-picker>
            </Col>
          </Row>
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

<script>
  import {
    saveOrUpdateWaterShipBa,
    queryWaterShipBa
  } from '@/api/waterway/watShipBa'
  import {browseDatadictionaryByDtype, browseXzqhList} from '@/api/datadictionaryCommon/datadictionaryCommon'
  import {closeThisView, responseUtil} from "@/utils/rongxunUtil";
  import {getUser} from "@/utils/auth"
  import {approveWaterWayBusinessData} from "../../../../api/waterway/waterWayApprove";

  export default {
    name: "rx-information",
    components: {},

    props: {
      formDataContrnt: {
        default: {}
      },
      projectNo: '',
      sxbm: '', sxlb_code: ''
    },
    mounted() {
      this.initXzqh();
      this.initData();
      let userInfo = getUser();
      this.formValidate.registArea = userInfo.xzqhCode
      this.formValidate.manageArea = userInfo.xzqhCode
      this.initDataByProjectNo()
    },
    data() {
      return {
        userId: '',
        userXzqh: '',
        formValidate: {
          id: '',
          state: '',
          fileLceNo: '',//备案证书号
          companyName: '',
          companyAddress: '',
          registDate: '',
          registNo: '',
          companyType: '',
          registMoney: '',
          contributor: '',
          contributorOne: '',
          contributorPercent: '',
          contributorPercentOne: '',
          fddbr: '',
          linkMan: '',
          phone: '',
          mobile: '',
          email: '',
          fax: '',
          supportMaterialList: [],
          proxyManageList: '船舶代理业务',
          tempOne: '本公司现申请国内水路运输辅助业务备案,并郑重承诺:申请材料中所提交的文件、证件及有关材料复印件与原件一致,真实有效',
          fddbrSignAture: '',
          fddbrSignAtureDate: '',
          tempTwo: '',
          fzrSignAture: '',
          fzrSignAtureDate: '',
        },
        ruleValidate: {
          companyName: [
            {required: true, message: '企业名称不能为空', trigger: 'blur'}
          ],
          companyAddress: [
            {required: true, message: '地址不能为空', trigger: 'blur'}
          ],
          waterCerNum: [
            {required: true, message: '水路运输证编号不能为空', trigger: 'blur'}
          ],
          shipWeight: [
            {required: true, message: '总吨不能为空', trigger: 'blur'}
          ],
          refLoadCapa: [
            {required: true, message: '参考载货量不能为空', trigger: 'blur'}
          ],
          deadWeight: [
            {required: true, message: '净吨不能为空', trigger: 'blur'}
          ],
          mainPower: [
            {required: true, message: '主机功率不能为空', trigger: 'blur'}
          ],
          shipMaterial: [
            {required: true, message: '船舶材料不能为空', trigger: 'blur'}
          ],
          shipType: [
            {required: true, message: '船舶类型不能为空', trigger: 'blur'}
          ],
          startWorkDate: [
            {required: true, message: '开工日期不能为空', trigger: 'blur', type: 'date'}
          ],
          complayDate: [
            {required: true, message: '拟建成日期不能为空', trigger: 'blur', type: 'date'}
          ],
          createCompany: [
            {required: true, message: '制造厂不能为空', trigger: 'blur'}
          ],
          shipCreateSignDate: [
            {required: true, message: '船舶建造合同签订日期不能为空', trigger: 'blur', type: 'date'}
          ],
          linkMan: [
            {required: true, message: '联系人不能为空', trigger: 'blur'}
          ],
          linkPhone: [
            {required: true, message: '联系电话不能为空', trigger: 'blur'}
          ]
        },
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
        ],
        supportMaterialLists: [
          {'value': '1', 'title': '国内水路运输辅助企业备案表;'},
          {'value': '2', 'title': '营业执照复印件;'},
          {'value': '3', 'title': '企业章程复印件;'},
          {'value': '4', 'title': '法定代表人身份证复印件;'},
          {'value': '5', 'title': '办公经营场所产权证明(或者租赁证明、协议等)及其复印件;'},
          {'value': '6', 'title': '代售票协议(水路旅客运输代理业务经营者提供)'},
          {'value': '7', 'title': '法定代表人授权委托书和经办人身份证复印件'},
        ]
      }
    },
    methods: {
      handleSubmit(name, type) {
        this.$refs[name].validate((valid) => {
          var that = this
          if (valid) {
            let userInfo = getUser();
            let initData = that.formValidate
            saveOrUpdateWaterShipBa(initData).then(function (response) {
              responseUtil.dealResponse(that, response, () => {
                if (response.code == 0) {
                  that.$emit('setProjectNo', response.data);
                  that.formValidate.id = response.data;
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
            that.$Message.error('表单验证失败!');
          }
        })
      },
      handleApprove(type) {
        let that = this
        let initData = {}
        let userInfo = getUser();
        initData.gid = this.formValidate.id;
        initData.sxlb = 'waterway_sx_gzl_12'
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
      initData() {
        var that = this
        let initData = {}
        initData.dType = 'qyzt'
        browseDatadictionaryByDtype(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.baseData.enterpriseState = response.data.data
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initXzqh() {
        var that = this
        let initData = {}
        browseXzqhList(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.baseData.registArea = response.data.data
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initDataByProjectNo() {
        console.log(this.projectNo)
        if (!this.projectNo || this.projectNo === '') {
          return
        }
        var that = this
        let initData = {}
        initData.projectNo = that.projectNo
        queryWaterShipBa(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {

              that.formValidate = response.data
              that.formValidate.supportMaterialList = response.data.supportMaterialList.split(',')
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
      }
    }
  }
</script>
