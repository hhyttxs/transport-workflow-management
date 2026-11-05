<style lang="less">
@import "../../rx-label/rx-label.less";
@import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
      <Divider style="font-weight: 500;color: #5b89eb" orientation="center">企业基本信息</Divider>
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="企业名称:" prop="qymc">
            <Input v-model="formValidate.qymc" placeholder="企业名称" disabled></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="许可证号:" prop="xkzh">
            <Input v-model="formValidate.xkzh" placeholder="许可证号" disabled></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="有效期开始:" prop="yxqks">
            <Input v-model="formValidate.yxqks" placeholder="有效期开始" disabled></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="有效期截止:" prop="yxqjz">
            <Input v-model="formValidate.yxqjz" placeholder="有效期截止" disabled></Input>
          </Form-item>
        </Col>
      </Row>
      <Divider style="font-weight: 500;color: #5b89eb" orientation="center">船舶信息</Divider>
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="船名:" prop="cm">
            <Input v-model="formValidate.cm" placeholder="请输入船名"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="船籍港:" prop="cjg">
            <Input v-model="formValidate.cjg" placeholder="请输入船籍港"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="船舶管理人:" prop="cbglr">
            <Input v-model="formValidate.cbglr" placeholder="请输入船舶管理人"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="管理人许可证号码:" prop="glrxkzhm">
            <Input v-model="formValidate.glrxkzhm" placeholder="请输入管理人许可证号码"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="船舶总吨:" prop="cbzd">
            <Input v-model="formValidate.cbzd" placeholder="请输入船舶总吨"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="载货定额（载重吨）:" prop="zhde">
            <Input v-model="formValidate.zhde" placeholder="请输入载货定额"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="联系人:" prop="lxr">
            <Input v-model="formValidate.lxr" placeholder="请输入联系人"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="联系电话:" prop="lxdh">
            <Input v-model="formValidate.lxdh" placeholder="请输入联系电话"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="24">
          <Form-item label="本船核定的经营范围:" prop="jyfw">
            <Input type="textarea" v-model="formValidate.jyfw" placeholder="请输入本船核定的经营范围"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px" v-if="sxlb_code==='2'||sxlb_code==='3'">
        <Col span="12">
          <Form-item label="证件号码:" prop="zjhm">
            <Input v-model="formValidate.zjhm" placeholder="请输入证件号码"></Input>
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
import {closeThisView, responseUtil} from '@/utils/rongxunUtil'
import {getUser} from '@/utils/auth'
import {saveOrUpdateKyhWxpYsz, queryKchwxpShipInfo, queryYhxxByProjectNo} from '@api/waterTransport/xxkcjyxk'
import {approveWaterWayBusinessData} from "@api/waterway/waterWayApprove";

export default {
  name: 'rx-information-sl-kchwxpysz',
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
    console.log('sxlb_code:' + this.sxlb_code)
    console.log('sxlb:' + this.sxlb)
    this.initDataByProjectNo()
  },
  data() {
    return {
      formValidate: {
        gid: '',
        state: '',
        cm: '',
        cjg: '',
        cbglr: '',
        glrxkzhm: '',
        cbzd: '',
        zhde: '',
        lxr: '',
        lxdh: '',
        jyfw: '',
        qy_gid: '',
        qymc: '',
        xkzh: '',
        yxqks: '',
        yxqjz: '',
      },
      ruleValidate: {
        cm: [
          {required: true, message: '船名不能为空', trigger: 'blur'}
        ],
        cjg: [
          {required: true, message: '船籍港不能为空', trigger: 'blur'}
        ],
        cbzd: [
          {required: true, message: '船舶总吨不能为空', trigger: 'blur'}
        ],
        zhde: [
          {required: true, message: '载货定额不能为空', trigger: 'blur'}
        ],
        jyfw: [
          {required: true, message: '本船核定的经营范围不能为空', trigger: 'blur'}
        ]
      },
      isSave: false,
    }
  },
  methods: {
    handleSubmit(name, type) {
      this.$refs[name].validate((valid) => {
        var that = this
        if (valid) {
          let initData = that.formValidate
          let user = getUser()
          initData.userId = user.gid
          initData.sxlb_code = that.sxlb_code
          saveOrUpdateKyhWxpYsz(initData).then(function (response) {
            responseUtil.dealResponse(that, response, () => {
              if (response.code == 0) {

                that.$emit('setProjectNo', response.data)
                that.formValidate.gid = response.data
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
          that.$Message.error('表单验证失败!')
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
      initData.gid = this.formValidate.gid;
      initData.sxlb = this.sxlb
      initData.actionType = type
      initData.userCode = userInfo.code;
      initData.backRemark = this.formValidate.backRemark;
      approveWaterWayBusinessData(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          if (response.code === "0") {
            that.$Message.success(response.data);
            if (type == 1 || type == -1) {
              closeThisView(that, '/waterWayTransport/SL_SXSL_index')
            } else {
              closeThisView(that, '/waterWayTransport/SL_SXSP_index')
            }
          } else {
            responseUtil.alertErrMsg(response.msg)
          }
        })
      })
    },
    handleReset(name) {
      this.$refs[name].resetFields()
    },
    changeStepTo(number) {
      this.$emit('getJyfw', this.formValidate.jyyw)
      this.$emit('changeStep', number)
    },
    initDataByProjectNo() {
      if (!this.projectNo || this.projectNo === '') {
        return
      }
      var that = this
      let initData = {}
      initData.projectNo = that.projectNo
      queryKchwxpShipInfo(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          if (response.code === '0') {
            console.log(response.data)
            that.formValidate = response.data;
            that.initYhxxInfo()
          } else {
            responseUtil.alertErrMsg(response.msg)
          }
        })
      })
    },
    initYhxxInfo() {
      let that = this
      let initData = {}
      initData.projectNo = that.projectNo
      queryYhxxByProjectNo(initData).then(function (response) {
        if (response.code === '0') {
          let data = response.data
          that.formValidate.qy_gid = data.gid
          that.formValidate.qymc = data.qymc
          that.formValidate.xkzh = data.zjhm
          that.formValidate.yxqks = data.auditlatestdate
          that.formValidate.yxqjz = data.yxqjz
        }
      })
    },
  }
}
</script>
