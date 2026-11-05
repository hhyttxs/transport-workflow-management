<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
      <Divider orientation="left">新增普通货船从事国内水路运输备案申请书</Divider>
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
        <Col span="24">
          <Form-item label="水路运输证编号: " prop="waterCerNum">
            <Input v-model="formValidate.waterCerNum" placeholder="请输入水路运输证编号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="总吨: " prop="shipWeight">
            <Input v-model="formValidate.shipWeight" placeholder="请输入船舶总吨"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="参考载货量:" prop="refLoadCapa">
            <Input v-model="formValidate.refLoadCapa" placeholder="请输入参考载货量"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="净吨: " prop="deadWeight">
            <Input v-model="formValidate.deadWeight" placeholder="请输入净吨"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="主机功率:" prop="mainPower">
            <Input v-model="formValidate.mainPower" placeholder="请输入主机功率"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="船舶材料:" prop="shipMaterial">
            <Input v-model="formValidate.shipMaterial" placeholder="请输入船舶材料"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="船舶类型: " prop="shipType">
            <Input v-model="formValidate.shipType" placeholder="请输入船舶类型"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="开工日期: " prop="startWorkDate">
            <Date-picker type="date" placeholder="开工日期" value-format="yyyy-MM-dd hh:mm:ss"
                         v-model="formValidate.startWorkDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="拟建成日期:" prop="complayDate">
            <Date-picker type="date" placeholder="拟建成日期" value-format="yyyy-MM-dd hh:mm:ss"
                         v-model="formValidate.complayDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="集装箱位: ">
            <Input v-model="formValidate.container" placeholder="请输入集装箱位"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="车位:">
            <Input v-model="formValidate.stall" placeholder="请输入车位"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="制造厂:" prop="createCompany">
            <Input v-model="formValidate.createCompany" placeholder="请输入制造厂"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="船舶建造合同签订日期: " prop="shipCreateSignDate">
            <Date-picker type="date" placeholder="船舶建造合同签订日期" value-format="yyyy-MM-dd hh:mm:ss"
                         v-model="formValidate.shipCreateSignDate" class="searchItem"></Date-picker>
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
          <Form-item label="联系电话: " prop="linkPhone">
            <Input v-model="formValidate.linkPhone" placeholder="请输入联系电话"></Input>
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

<script>
  import {
    saveOrUpdateWaterShipTransportRecord,
    browseShipTransportRecordByGid
  } from '@/api/waterway/watInternalShipTransportRecord'
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
      sxbm: '',
      sxlb_code: ''
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
          id: '', state: '',
          companyName: '',//企业名称
          companyAddress: '',//地址
          waterCerNum: '',//水路运输证编号
          shipWeight: '',//总吨
          refLoadCapa: '',//参考载货量
          deadWeight: '',//净吨
          mainPower: '',//主机功率
          shipMaterial: '',//船舶材料
          shipType: '',//船舶类型
          startWorkDate: '',//开工日期
          complayDate: '',//拟建成日期
          container: '',//集装箱位
          stall: '',//车位
          createCompany: '',//制造厂
          shipCreateSignDate: '',//船舶建造合同签订日期
          linkMan: '',//联系人
          linkPhone: '',//联系电话
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
            if (!that.isSave) {
              that.isSave = false
              saveOrUpdateWaterShipTransportRecord(initData).then(function (response) {
                responseUtil.dealResponse(that, response, () => {
                  if (response.code == 0) {
                    that.$emit('setProjectNo', response.data.projectNo);
                    that.formValidate.id = response.data.projectNo
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
        initData.gid = this.formValidate.id;
        initData.sxlb = 'waterway_sx_gzl_11'
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
        if (!this.projectNo || this.projectNo === '') {
          return
        }
        var that = this
        let initData = {}
        initData.projectNo = that.projectNo
        browseShipTransportRecordByGid(initData).then(function (response) {
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
      }
    }
  }
</script>
