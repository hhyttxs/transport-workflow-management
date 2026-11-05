<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
      <Row v-show="ywType === -1">
        <Col>
          <Form-item label="船舶运输类型:" prop="ywType">
            <Select v-model="formValidate.ywType" placeholder="请选择船舶运输类型" style="width: 300px">
              <Option value="2">旅客运输</Option>
              <Option value="1">危险品运输</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="申请单位名称:" prop="sqrmc">
            <Input v-model="formValidate.sqrmc" placeholder="请输入申请单位名称"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="申请单位地址:" prop="sqrAddress">
            <Input v-model="formValidate.sqrAddress" placeholder="请输入申请单位地址" type="textarea"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="申请日期: " prop="sqrq">
            <Date-picker type="date" placeholder="申请日期" value-format="yyyy-MM-dd hh:mm:ss"
                         v-model="formValidate.sqrq" class="searchItem"></Date-picker>
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
          <Form-item label="曾用名: ">
            <Input v-model="formValidate.oldShipName" placeholder="请输入曾用名"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="船籍港:" prop="shipHomePort">
            <Input v-model="formValidate.shipHomePort" placeholder="请输入船籍港"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="原船籍港:">
            <Input v-model="formValidate.oldShipHomePort" placeholder="请输入原船籍港"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="船舶登记号: " prop="shipRegisterNumber">
            <Input v-model="formValidate.shipRegisterNumber" placeholder="请输入船舶登记号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="船检登记号:" prop="shipExamineNumber">
            <Input v-model="formValidate.shipExamineNumber" placeholder="请输入船检登记号"></Input>
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
          <Form-item label="经营人许可证号: " prop="manageLicenseKey">
            <Input v-model="formValidate.manageLicenseKey" placeholder="请输入经营人许可证号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="船舶管理人:">
            <Input v-model="formValidate.shipCustodian" placeholder="请输入船舶管理人"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="管理人许可证号码:">
            <Input v-model="formValidate.custodianLicenseKey" placeholder="请输入管理人许可证号码"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="8">
          <Form-item label="船舶类型: " prop="shipType">
            <Input v-model="formValidate.shipType" placeholder="请输入船舶类型"></Input>
          </Form-item>
        </Col>
        <Col span="8">
          <Form-item label="船舶材料:" prop="shipMaterials">
            <Input v-model="formValidate.shipMaterials" placeholder="请输入船舶材料"></Input>
          </Form-item>
        </Col>
        <Col span="8">
          <Form-item label="船舶总吨:" prop="shipGt">
            <Input v-model="formValidate.shipGt" placeholder="请输入船舶总吨"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="建成日期:" prop="shipCreateDate">
            <Date-picker type="date" placeholder="建成日期" value-format="yyyy-MM-dd hh:mm:ss"
                         v-model="formValidate.shipCreateDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="制造厂: " prop="manufacturer">
            <Input v-model="formValidate.manufacturer" placeholder="请输入制造厂"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="改建日期:">
            <Date-picker type="date" placeholder="改建日期" value-format="yyyy-MM-dd hh:mm:ss"
                         v-model="formValidate.shipUpdateDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="改造厂: ">
            <Input v-model="formValidate.updateManufacturer" placeholder="请输入制造厂"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="6">
          <Form-item label="船舶参考载货量（吨）:">
            <Input v-model="formValidate.shipCargoWeight" placeholder="请输入船舶参考载货量（吨）"></Input>
          </Form-item>
        </Col>
        <Col span="6">
          <Form-item label="载箱量: ">
            <Input v-model="formValidate.containerCapacity" placeholder="请输入载箱量"></Input>
          </Form-item>
        </Col>
        <Col span="6">
          <Form-item label="车位: ">
            <Input v-model="formValidate.stall" placeholder="请输入车位"></Input>
          </Form-item>
        </Col>
        <Col span="6">
          <Form-item label="客位: ">
            <Input v-model="formValidate.ratedSeat" placeholder="请输入客位"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item prop="stage">
            <template #label>
              <span>主机功率:</span>
            </template>
            <Input v-model="formValidate.stage" placeholder="请输入台">
              <template #append>
                <span>台</span>
              </template>
            </Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item prop="kilowatt">
            <Input v-model="formValidate.kilowatt" placeholder="请输入千瓦">
              <template #append>
                <span>千瓦</span>
              </template>
            </Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="经营人许可证核定经营范围:" prop="manageJyfw">
            <Input v-model="formValidate.manageJyfw" placeholder="请输入经营人许可证核定经营范围"
                   type="textarea"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="本船申请经营范围:" prop="shipJyfw">
            <Input v-model="formValidate.shipJyfw" placeholder="请输入本船申请经营范围" type="textarea"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="联系人:" prop="lxr">
            <Input v-model="formValidate.lxr" placeholder="请输入联系人"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="联系电话: " prop="lxrPhone">
            <Input v-model="formValidate.lxrPhone" placeholder="请输入联系电话"></Input>
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
  import {saveOrUpdateAccept, browseAcceptByProjectNo} from '@/api/acceptFormCommon/acceptCommon'
  import {
    saveOrUpdateWaterShipTransport,
    browseShipTransportByGidOrShipNum
  } from '@/api/waterway/watInternalShipTransport'
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
      ywType: '',
      sxlb_code: '', companyId: '',
    },
    mounted() {
      this.initDataByProjectNo()
      if (this.ywType != -1) {
        this.formValidate.ywType = this.ywType
      }
    },
    data() {
      return {
        userId: '',
        userXzqh: '',
        formValidate: {
          id: '', state: '',
          sqrmc: '',//申请单位名称
          sqrAddress: '',//申请单位地址
          sqrq: '',//申请日期
          shipName: '',//船名
          oldShipName: '',//曾用名
          shipHomePort: '',//船籍港
          oldShipHomePort: '',//原船籍港
          shipRegisterNumber: '',//船舶登记号
          shipExamineNumber: '',//船检登记号
          haveShipPeople: '',//船舶所有人
          shipManagePeople: '',//船舶经营人
          manageLicenseKey: '',//经营人许可证号
          shipCustodian: '',//船舶管理人
          custodianLicenseKey: '',//管理人许可证号码
          shipType: '',//船舶类型
          shipMaterials: '',//船舶材料
          shipGt: '',//船舶总吨
          shipCreateDate: '',//建成日期
          manufacturer: '',//制造厂
          shipUpdateDate: '',//改建日期
          updateManufacturer: '',//改造厂
          shipCargoWeight: '',//船舶参考载货量（吨）
          containerCapacity: '',//载箱量
          stall: '',//车位
          ratedSeat: '',//客位
          stage: '',//台
          kilowatt: '',//千瓦
          manageJyfw: '',//经营人许可证核定经营范围
          shipJyfw: '',//本船申请经营范围
          lxr: '',//联系人
          lxrPhone: '', //联系电话
          ywType: '', //业务类别
        },
        ruleValidate: {
          sqrmc: [
            {required: true, message: '申请单位名称不能为空', trigger: 'blur'}
          ],
          sqrAddress: [
            {required: true, message: '申请单位地址不能为空', trigger: 'blur'}
          ],
          sqrq: [
            {required: true, message: '申请日期不能为空', trigger: 'blur', type: 'date'}
          ],
          shipName: [
            {required: true, message: '船名不能为空', trigger: 'blur'}
          ],
          shipHomePort: [
            {required: true, message: '船籍港不能为空', trigger: 'blur'}
          ],
          shipRegisterNumber: [
            {required: true, message: '船舶登记号不能为空', trigger: 'blur'}
          ],
          shipExamineNumber: [
            {required: true, message: '船检登记号不能为空', trigger: 'blur'}
          ],
          haveShipPeople: [
            {required: true, message: '船舶所有人不能为空', trigger: 'blur'}
          ],
          shipManagePeople: [
            {required: true, message: '船舶经营人不能为空', trigger: 'blur'}
          ],
          manageLicenseKey: [
            {required: true, message: '经营人许可证号不能为空', trigger: 'blur'}
          ],
          shipType: [
            {required: true, message: '船舶类型不能为空', trigger: 'blur'}
          ],
          shipMaterials: [
            {required: true, message: '船舶材料不能为空', trigger: 'blur'}
          ],
          shipGt: [
            {required: true, message: '船舶总吨不能为空', trigger: 'blur'}
          ],
          shipCreateDate: [
            {required: true, message: '建成日期不能为空', trigger: 'blur', type: 'date'}
          ],
          manufacturer: [
            {required: true, message: '制造厂不能为空', trigger: 'blur'}
          ],
          stage: [
            {required: true, message: '台不能为空', trigger: 'blur'}
          ],
          kilowatt: [
            {required: true, message: '千瓦不能为空', trigger: 'blur'}
          ],
          manageJyfw: [
            {required: true, message: '经营人许可证核定经营范围不能为空', trigger: 'blur'}
          ],
          shipJyfw: [
            {required: true, message: '本船申请经营范围不能为空', trigger: 'blur'}
          ],
          lxr: [
            {required: true, message: '联系人不能为空', trigger: 'blur'}
          ],
          lxrPhone: [
            {required: true, message: '联系电话不能为空', trigger: 'blur'}
          ],
          ywType: [
            {required: true, message: '船舶运输类型不能为空', trigger: 'blur'},
            {required: true, message: '船舶运输类型不能为空', trigger: 'change'}
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
            initData.companyId = this.companyId
            saveOrUpdateWaterShipTransport(initData).then(function (response) {
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
          initData.sxlb = 'waterway_sx_gzl_5'
        } else {
          initData.sxlb = 'waterway_sx_gzl_8'
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
        browseShipTransportByGidOrShipNum(initData).then(function (response) {
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
