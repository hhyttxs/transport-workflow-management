<style lang="less">
@import "../../rx-label/rx-label.less";
@import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
      <Divider orientation="left">平台档案</Divider>
      <Row>
        <Col span="12">
          <Form-item label="所属区域:" prop="vcDqdm">
            <Select v-model="formValidate.vcDqdm" clearable
                    placeholder="请选择所属区域">
              <Option v-for="item in vcDqdmList" :value="item.AREAREGIONID" :key="item.AREAREGIONID">{{
                  item.AREAREGIONNAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="许可证号:" prop="xkzh">
            <Input v-model="formValidate.xkzh" placeholder="请输入许可证号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="平台简称:" prop="ptjc">
            <Input v-model="formValidate.ptjc" placeholder="请输入平台简称"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="平台名称:" prop="ptmc">
            <Input v-model="formValidate.ptmc" placeholder="请输入平台名称"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="平台地址:" prop="ptdz">
            <Input v-model="formValidate.ptdz" placeholder="请输入平台地址"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="办公地址:" prop="bgdz">
            <Input v-model="formValidate.bgdz" placeholder="请输入办公地址"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="总公司名称:" prop="zgsmc">
            <Input v-model="formValidate.zgsmc" placeholder="请输入总公司名称"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="总公司地址:" prop="ptzgsdz">
            <Input v-model="formValidate.ptzgsdz" placeholder="请输入总公司地址"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="营业执照号:" prop="yyzzh">
            <Input v-model="formValidate.yyzzh" placeholder="请输入营业执照号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="许可证有效期起:" prop="yxqks">
            <Date-picker type="date" placeholder="许可证有效期起" value-format="yyyy-MM-dd"
                         v-model="formValidate.yxqks" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="许可证有效期止: " prop="yxqjz">
            <Date-picker type="date" placeholder="许可证有效期止" value-format="yyyy-MM-dd"
                         v-model="formValidate.yxqjz" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="法定代表: " prop="frxm">
            <Input v-model="formValidate.frxm" placeholder="请输入法定代表"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="法人身份证:" prop="frsfzh">
            <Input v-model="formValidate.frsfzh" placeholder="请输入法人身份证"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="联系电话:" prop="lxdh">
            <Input v-model="formValidate.lxdh" placeholder="请输入联系电话"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="经济性质: " prop="vcJjxz">
            <Select v-model="formValidate.vcJjxz" clearable
                    placeholder="请选择经济性质">
              <Option v-for="item in VC_JJXZList" :value="item.ECONNATUREID" :key="item.ECONNATUREID">{{
                  item.ECONOMICNATURE
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="经营范围: " prop="vcJyfw">
            <CheckboxGroup v-model="vcJyfw">
              <Checkbox v-for="item in VC_JYFWList" :label="item.VC_JYFW_ID">
                <span>{{ item.VC_JYFW_NAME }}</span>
              </Checkbox>
            </CheckboxGroup>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="经营区域:" prop="jyqy">
            <Input v-model="formValidate.jyqy" placeholder="请输入经营区域"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="备注: " prop="remark">
            <Input v-model="formValidate.remark" type="textarea" placeholder="请输入备注"></Input>
          </Form-item>
        </Col>
      </Row>
      <!--      <Form-item>-->
      <!--        <Button type="primary" @click="handleSubmit('formValidate')">提交</Button>-->
      <!--        <Button type="ghost" @click="handleReset('formValidate')" style="margin-left: 8px">重置</Button>-->
      <!--      </Form-item>-->
    </Form>
    <Alert class="button">
<!--      <button @click="changeStepTo(1)">上一步</button>-->
      <button v-if="ywType==='1'" @click="handleApprove(1)">受理</button>
      <button v-if="ywType==='2'" @click="handleApprove(2)">审批</button>
      <button v-if="ywType==='2'&&formValidate.state==='2'" @click="handleApprove(0)">退回</button>
      <button v-if="ywType==='0'" @click="handleSubmit('formValidate')">提交</button>
<!--      <button @click="changeStepTo(3)">下一步</button>-->
    </Alert>
  </div>
</template>

<script>
import {saveOrUpdateAccept, browseAcceptByProjectNo} from '@/api/acceptFormCommon/acceptCommon'
import {getUserId, responseUtil} from "@/utils/rongxunUtil";
import {getUser} from "@/utils/auth"
import {
  queryTaxiArea, queryEconomicNatureList
} from '@/api/taxi/taxiCarApi'
import {queryWycCompanyInfo, saveWycCompanyInfo} from "../../../../api/taxi/taxiCarApi";
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
    this.initEconomicNature();
    this.initDataByProjectNo()
  },
  data() {
    return {
      userId: '',
      userXzqh: '',
      formValidate: {
        gid: '', state: '',
        vcDqdm: '',
        xkzh: '',
        ptjc: '',
        ptmc: '',
        ptdz: '',
        bgdz: '',
        zgsmc: '',
        ptzgsdz: '',
        yyzzh: '',
        yxqks: '',
        yxqjz: '',
        frxm: '',
        frsfzh: '',
        lxdh: '',
        vcJjxz: '',

        jyqy: '',
        remark: ''
      },
      vcJyfw: '1',
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
      VC_JJXZList: [],
      VC_JYFWList: [
        {
          'VC_JYFW_ID': '1',
          'VC_JYFW_NAME': '网络预约客运出租汽车平台许可核发'
        }
      ],
      vcDqdmList: [],
    }
  },
  methods: {
    handleSubmit(name) {
      this.$refs[name].validate((valid) => {
        var that = this
        if (valid) {
          that.formValidate.yxqks = that.changeDate(that.formValidate.yxqks)
          that.formValidate.yxqjz = that.changeDate(that.formValidate.yxqjz)
          let initData = {};
          initData['wycInfo'] = that.formValidate;
          initData.vcJyfw = that.vcJyfw;
          if (!that.isSave) {
            that.isSave = false
            saveWycCompanyInfo(JSON.stringify(initData)).then(function (response) {
              responseUtil.dealResponse(that, response, () => {
                if (response.code == 0) {
                  that.$emit('setProjectNo', response.data);
                  that.formValidate.gid = response.data;
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
      initData.sxlb = '3'
      initData.actionType = type
      initData.userCode = userInfo.code;
      approveBusinessData(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          if (response.code === "0") {
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
      queryWycCompanyInfo(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          if (response.code === "0") {
            that.formValidate = response.data;
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
    initEconomicNature() {
      let that = this;
      let initData = {}
      queryEconomicNatureList(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          if (response.code === "0") {
            that.VC_JJXZList = response.data
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
