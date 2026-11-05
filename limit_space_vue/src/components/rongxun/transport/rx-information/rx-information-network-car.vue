<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
      <Divider orientation="left">车辆档案</Divider>
      <Row>
        <Col span="12">
          <Form-item label="车牌号码:" prop="cphm">
            <Input v-model="formValidate.cphm" placeholder="请输入车牌号码"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="所属区域:" prop="vcDqdm">
            <Select v-model="formValidate.vcDqdm" clearable
                    placeholder="请选择所属区域">
              <Option></Option>
              <Option v-for="item in vcDqdmList" :value="item.AREAREGIONID" :key="item.AREAREGIONID">{{
                item.AREAREGIONNAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="所属平台:" prop="sspt">
            <Select v-model="formValidate.sspt" clearable
                    placeholder="请选择所属平台">
              <Option v-for="item in SSPTList" :value="item.GID" :key="item.GID">{{
                item.PTMC
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="营运证号:" prop="yyzh">
            <Input v-model="formValidate.yyzh" placeholder="请输入营运证号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="发证日期: " prop="fzrq">
            <Date-picker type="date" placeholder="发证日期" value-format="yyyy-MM-dd"
                         v-model="formValidate.fzrq" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="许可有效期起:" prop="xkyxqq">
            <Date-picker type="date" placeholder="许可有效期起" value-format="yyyy-MM-dd"
                         v-model="formValidate.xkyxqq" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="许可证有效止: " prop="xkyxqz">
            <Date-picker type="date" placeholder="许可证有效止" value-format="yyyy-MM-dd"
                         v-model="formValidate.xkyxqz" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="品牌型号: " prop="ppxh">
            <Input v-model="formValidate.ppxh" placeholder="请输入品牌型号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="车辆类型:" prop="cllx">
            <Select v-model="formValidate.cllx" clearable
                    placeholder="请选择车辆类型">
              <Option v-for="item in CLLXList" :value="item.CARTYPEMAINID" :key="item.CARTYPEMAINID">{{
                item.CARTYPEMAINNAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="车身颜色: " prop="csys">
            <Select v-model="formValidate.csys" clearable
                    placeholder="车身颜色">
              <Option v-for="item in CSYSList" :value="item.COLORID" :key="item.COLORID">{{
                item.COLORNAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="注册日期:" prop="zcrq">
            <Date-picker type="date" placeholder="注册日期" value-format="yyyy-MM-dd"
                         v-model="formValidate.zcrq" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="识别代码: " prop="cjh">
            <Input v-model="formValidate.cjh" placeholder="请输入识别代码"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="发动机号码:" prop="fdjh">
            <Input v-model="formValidate.fdjh" placeholder="请输入发动机号码"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="燃料种类: " prop="rllx">
            <Select v-model="formValidate.rllx" clearable
                    placeholder="燃料种类">
              <Option v-for="item in RLLXList" :value="item.FUELTYPEID" :key="item.FUELTYPEID">{{
                item.FUELTYPENAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="最大功率(KW):" prop="maxgl">
            <Input v-model="formValidate.maxgl" placeholder="请输入最大功率(KW)"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="排气量(ML):" prop="pql">
            <Input v-model="formValidate.pql" placeholder="请输入许可证号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="核载人数: " prop="hzrs">
            <Input v-model="formValidate.hzrs" placeholder="请输入营运证号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="外观尺寸(mm):" prop="licenseStartDateSpan">
            {{ '长:' }}<Input v-model="formValidate.clcd" placeholder="请输入长" style="width: 30%"></Input>
            {{ '宽:' }}<Input v-model="formValidate.clkd" placeholder="请输入宽" style="width: 30%"></Input>
            {{ '高:' }}<Input v-model="formValidate.clgd" placeholder="请输入高" style="width: 30%"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="轴距(mm):" prop="zj">
            <Input v-model="formValidate.zj" placeholder="请输入轴距(mm)"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="新能源车续航里程（公里）: " prop="xnycxhlc">
            <Input v-model="formValidate.xnycxhlc" placeholder="请输入新能源车续航里程（公里）"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="变速器类型:" prop="bsqlx">
            <Select v-model="formValidate.bsqlx" clearable
                    placeholder="请选择变速器类型">
              <Option v-for="item in BSQLXList" :value="item.BSQLXID" :key="item.BSQLXID">{{
                item.BSQLXNAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="排放标准: " prop="pfbz">
            <Select v-model="formValidate.pfbz" clearable
                    placeholder="请选择排放标准">
              <Option v-for="item in PFBZList" :value="item.PFBZID" :key="item.PFBZID">{{
                item.PFBZNAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="卫星定位装置厂牌:" prop="wxdwzzcp">
            <Input v-model="formValidate.wxdwzzcp" placeholder="请输入大架号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="卫星定位装置型号: " prop="wxdzzzxh">
            <Input v-model="formValidate.wxdzzzxh" placeholder="请输入发动机号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="审核说明: " prop="shremark">
            <Input v-model="formValidate.shremark" type="textarea" placeholder="请输入审核说明"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="备注: " prop="remark">
            <Input v-model="formValidate.remark" type="textarea" placeholder="请输入备注"></Input>
          </Form-item>
        </Col>
        <Col span="24">
          <Form-item label="" prop="">
            <Checkbox v-model="formValidate.ckbisxny">是否新能源</Checkbox>
            <Checkbox v-model="formValidate.ckbisaqbzyq">是否符合车辆技术性能运营安全相关标准要求</Checkbox>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="车辆所有人信息:" prop="clsyrlx">
            <RadioGroup v-model="formValidate.clsyrlx">
              <Radio v-for="item in CLSYRLXList" :label="item.CLSYRLXID">
                <span>{{ item.CLSYRLXNAME }}</span>
              </Radio>
            </RadioGroup>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="所有人: " prop="clsyrmc">
            <Input v-model="formValidate.clsyrmc" placeholder="请输入所有人"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="营业执照号: " prop="clsyryyzzh">
            <Input v-model="formValidate.clsyryyzzh" placeholder="请输入营业执照号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="法人代表:" prop="clsyrxm">
            <Input v-model="formValidate.clsyrxm" placeholder="请输入法人代表"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="法人身份证: " prop="CLSYRSFZH">
            <Input v-model="formValidate.CLSYRSFZH" placeholder="请输入法人身份证"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="办公地址: " prop="clsyrbgdz">
            <Input v-model="formValidate.clsyrbgdz" placeholder="请输入办公地址"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="车辆所有人联系电话: " prop="clsyrlxdh">
            <Input v-model="formValidate.clsyrlxdh" placeholder="请输入联系电话"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="车辆所有人:" prop="clsyrmc1">
            <Input v-model="formValidate.clsyrmc1" placeholder="请输入车辆所有人"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="法人身份证: " prop="clsyrsfzh1">
            <Input v-model="formValidate.clsyrsfzh1" placeholder="请输入法人身份证"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="从业资格证号或学员证号:" prop="clsyryyzzh1">
            <Input v-model="formValidate.clsyryyzzh1" placeholder="请输入法人身份证"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="注册地址: " prop="clsyrzzdz1">
            <Input v-model="formValidate.clsyrzzdz1" placeholder="请输入注册地址"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="办公地址: " prop="clsyrbgdz1">
            <Input v-model="formValidate.clsyrbgdz1" placeholder="请输入办公地址"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="联系电话: " prop="clsyrlxdh1">
            <Input v-model="formValidate.clsyrlxdh1" placeholder="请输入联系电话"></Input>
          </Form-item>
        </Col>
      </Row>
    </Form>
    <Alert class="button">
      <!--      <button @click="changeStepTo(1)">上一步</button>-->
      <button v-if="ywType==='1'" @click="handleApprove(1)">受理</button>
      <button v-if="ywType==='2'" @click="handleApprove(2)">审批</button>
      <button v-if="ywType==='2'&&formValidate.state==='2'" @click="handleApprove(0)">退回</button>
      <button v-if="ywType==='0'" @click="handleSubmit('companyForm')">提交</button>
      <!--      <button @click="changeStepTo(3)">下一步</button>-->
    </Alert>
  </div>
</template>

<script>
  import {saveOrUpdateAccept, browseAcceptByProjectNo} from '@/api/acceptFormCommon/acceptCommon'
  import {getUserId, responseUtil} from "@/utils/rongxunUtil";
  import {getUser} from "@/utils/auth"
  import {
    queryTaxiArea,
    queryCarTypeaMain,
    queryVehicleColorList,
    queryFuelTypeList
  } from '@/api/taxi/taxiCarApi'

  import {queryWycCompanyName, queryWycCarInfo, saveWycCarInfo} from '@/api/taxi/taxiWyc'
  import {approveBusinessData} from "../../../../api/taxi/taxiCompanyApi";

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
      this.initCarTypeaMainList();
      this.initVehicleColorList();
      this.initFuelTypeList();
      this.initWycCompanyNameList();
      this.initDataByProjectNo()
    },
    data() {
      return {
        userId: '',
        userXzqh: '',
        formValidate: {
          gid: '', state: '',
          cphm: '',
          vcDqdm: '',
          sspt: '',
          yyzh: '',
          fzrq: '',
          xkyxqq: '',
          xkyxqz: '',
          ppxh: '',
          cllx: '',
          csys: '',
          zcrq: '',
          cjh: '',
          fdjh: '',
          rllx: '',
          maxgl: '',
          pql: '',
          hzrs: '',
          clcd: '',
          clkd: '',
          clgd: '',
          zj: '',
          xnycxhlc: '',
          wxdwzzcp: '',
          wxdzzzxh: '',
          shremark: '',
          remark: '',
          ckbisxny: '',
          ckbisaqbzyq: '',
          clsyrlx: '',
          clsyrmc: '',
          clsyryyzzh: '',
          clsyrxm: '',
          CLSYRSFZH: '',
          clsyrbgdz: '',
          clsyrlxdh: '',
          clsyrmc1: '',
          clsyrsfzh1: '',
          clsyryyzzh1: '',
          clsyrzzdz1: '',
          clsyrbgdz1: '',
          clsyrlxdh1: '',
          bsqlx: '',
          pfbz: '',
        },
        ruleValidate: {
          title: [
            {required: true, message: '申报标题不能为空', trigger: 'blur'}
          ]
        },
        isSave: false,
        baseData: {
          registArea: [],
          enterpriseState: []
        },
        vcDqdmList: [],
        SSPTList: [],
        CLLXList: [],
        CSYSList: [],
        RLLXList: [],
        BSQLXList: [
          {
            'BSQLXID': '1',
            'BSQLXNAME': 'MT变速箱'
          }, {
            'BSQLXID': '2',
            'BSQLXNAME': 'DCT变速箱'
          }, {
            'BSQLXID': '3',
            'BSQLXNAME': 'AT变速箱'
          }, {
            'BSQLXID': '4',
            'BSQLXNAME': 'AMT变速箱'
          }, {
            'BSQLXID': '5',
            'BSQLXNAME': 'CVT变速箱'
          }, {
            'BSQLXID': '6',
            'BSQLXNAME': 'IVT变速箱'
          }],
        PFBZList: [
          {
            'PFBZID': '1',
            'PFBZNAME': '国一'
          }, {
            'PFBZID': '2',
            'PFBZNAME': '国二'
          }, {
            'PFBZID': '3',
            'PFBZNAME': '国三'
          }, {
            'PFBZID': '4',
            'PFBZNAME': '国四'
          }, {
            'PFBZID': '5',
            'PFBZNAME': '国五'
          }, {
            'PFBZID': '6',
            'PFBZNAME': '国六'
          }
        ],
        CLSYRLXList: [
          {
            'CLSYRLXID': '1',
            'CLSYRLXNAME': '车辆所有人类型一'
          }, {
            'CLSYRLXID': '2',
            'CLSYRLXNAME': '车辆所有人类型二'
          }, {
            'CLSYRLXID': '3',
            'CLSYRLXNAME': '车辆所有人类型三'
          }
        ]
      }
    },
    methods: {
      handleSubmit(name) {
        this.$refs[name].validate((valid) => {
          var that = this
          if (valid) {
            that.formValidate.fzrq = that.changeDate(that.formValidate.fzrq)
            that.formValidate.xkyxqq = that.changeDate(that.formValidate.xkyxqq)
            that.formValidate.xkyxqz = that.changeDate(that.formValidate.xkyxqz)
            that.formValidate.zcrq = that.changeDate(that.formValidate.zcrq)
            let initData = that.formValidate
            if (!that.isSave) {
              that.isSave = false
              saveWycCarInfo(initData).then(function (response) {
                responseUtil.dealResponse(that, response, () => {
                  if (response.code == 0) {
                    that.$emit('setProjectNo', response.data);
                    that.formValidate.gid = response.data
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
        initData.gid = this.formValidate.gid;
        initData.sxlb = '4'
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
        initData.projectNo = that.projectNo
        queryWycCarInfo(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.formValidate = response.data
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
              that.vcDqdmList = response.data
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initCarTypeaMainList() {
        let that = this;
        let initData = {}
        queryCarTypeaMain(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.CLLXList = response.data
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initVehicleColorList() {
        let that = this;
        let initData = {}
        queryVehicleColorList(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.CSYSList = response.data
              that.formValidate.csys = response.data[0].COLORID
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initFuelTypeList() {
        let that = this;
        let initData = {}
        queryFuelTypeList(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.RLLXList = response.data
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initWycCompanyNameList() {
        let that = this;
        let initData = {}
        queryWycCompanyName(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.SSPTList = response.data
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
