<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
      <Divider orientation="left">船舶营运证注销申请表</Divider>
      <Row>
        <Col span="24">
          <Form-item label="申请人:" prop="suportName">
            <Input v-model="formValidate.suportName" placeholder="请输入申请人"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="船名: " prop="shipName">
            <Input v-model="formValidate.shipName" placeholder="请输入船名"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="营运证编号:" prop="shipLicenseNum">
            <Input v-model="formValidate.shipLicenseNum" placeholder="请输入营运证编号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="8">
          <Form-item label="船籍港:" prop="shipHomePort">
            <Input v-model="formValidate.shipHomePort" placeholder="请输入船籍港"></Input>
          </Form-item>
        </Col>
        <Col span="8">
          <Form-item label="船舶类型: " prop="shipType">
            <Input v-model="formValidate.shipType" placeholder="请输入船舶类型"></Input>
          </Form-item>
        </Col>
        <Col span="8">
          <Form-item label="建成日期:" prop="shipCreateDate">
            <Date-picker type="date" placeholder="建成日期" value-format="yyyy-MM-dd hh:mm:ss"
                         v-model="formValidate.shipCreateDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="船舶所有人: " prop="haveShipPeople">
            <Input v-model="formValidate.haveShipPeople" placeholder="请输入船舶所有人"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="船舶经营人: " prop="shipManagePeople">
            <Input v-model="formValidate.shipManagePeople" placeholder="请输入船舶经营人"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="营运证核定经营范围:" prop="lincenseJyfw">
            <Input v-model="formValidate.lincenseJyfw" placeholder="请输入营运证核定经营范围"
                   type="textarea"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="注销原因:" prop="logoutResion">
            <Input v-model="formValidate.logoutResion" placeholder="请输入注销原因" type="textarea"></Input>
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
    saveOrUpdateWaterShipTransportLogout,
    browseShipTransportLogoutByGid
  } from '@/api/waterway/watInternalShipTransportLogout'
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
      sxbm: '', sxlb_code: '', ship_gid: ''
    },
    mounted() {
      this.initDataByProjectNo()
    },
    data() {
      return {
        userId: '',
        userXzqh: '',
        formValidate: {
          id: '', state: '',
          suportName: '',//申请人
          shipName: '',//船名
          shipLicenseNum: '',//营运证编号
          shipHomePort: '',//船籍港
          shipType: '',//船舶类型
          shipCreateDate: '',//建成日期
          haveShipPeople: '',//船舶所有人
          shipManagePeople: '',//船舶经营人
          lincenseJyfw: '',//营运证核定经营范围
          logoutResion: '',//注销原因
          shipRegisterNumber: '',//船舶登记号
          ywType: ''
        },
        ruleValidate: {
          suportName: [
            {required: true, message: '申请人不能为空', trigger: 'blur'}
          ],
          shipName: [
            {required: true, message: '船名不能为空', trigger: 'blur'}
          ],
          shipLicenseNum: [
            {required: true, message: '营运证编号不能为空', trigger: 'blur'}
          ],
          shipHomePort: [
            {required: true, message: '船籍港不能为空', trigger: 'blur'}
          ],
          haveShipPeople: [
            {required: true, message: '船舶所有人不能为空', trigger: 'blur'}
          ],
          shipManagePeople: [
            {required: true, message: '船舶经营人不能为空', trigger: 'blur'}
          ],
          shipType: [
            {required: true, message: '船舶类型不能为空', trigger: 'blur'}
          ],
          shipCreateDate: [
            {required: true, message: '建成日期不能为空', trigger: 'blur', type: 'date'}
          ],
          logoutResion: [
            {required: true, message: '注销原因不能为空', trigger: 'blur'}
          ], lincenseJyfw: [
            {required: true, message: '营运证核定经营范围不能为空', trigger: 'blur'}
          ],
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
            initData.ship_gid = that.ship_gid
            that.isSave = true
            saveOrUpdateWaterShipTransportLogout(initData).then(function (response) {
              responseUtil.dealResponse(that, response, () => {
                if (response.code == 0) {
                  that.$emit('setProjectNo', response.data.projectNo);
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
        if (this.formValidate.ywType == '0') {
          initData.sxlb = 'waterway_sx_gzl_7'
        } else {
          initData.sxlb = 'waterway_sx_gzl_10'
        }
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
        if ((!this.projectNo || this.projectNo === '') && (!this.ship_gid || this.ship_gid === '')) {
          return
        }
        var that = this
        let initData = {}
        initData.projectNo = that.projectNo
        initData.ship_gid = that.ship_gid
        console.log(initData.projectNo)
        browseShipTransportLogoutByGid(initData).then(function (response) {
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
