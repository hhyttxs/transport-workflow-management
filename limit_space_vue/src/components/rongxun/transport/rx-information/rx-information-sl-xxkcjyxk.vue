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
            <Input v-model="formValidate.qymc" placeholder="请输入申请单位名称"></Input>
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
          <Form-item label="联系电话:" prop="lxdh">
            <Input v-model="formValidate.lxdh" placeholder="请输入联系电话"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="地址:" prop="address">
            <Input v-model="formValidate.address" placeholder="请输入申请单位地址"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="企业经济类型:" prop="jjlx">
            <Input v-model="formValidate.jjlx" placeholder="请输入企业经济类型"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row class="InsertLine">
        <Divider style="font-weight: 500;color: #5b89eb" orientation="center">经营范围-主营</Divider>
        <Col span="24">
          <Form-item label="客运航线:" prop="zyKyhx">
            <Input type="textarea" v-model="formValidate.zyKyhx" placeholder="请输入客运航线"></Input>
          </Form-item>
        </Col>
        <Col span="24" class="InsertLine" style="margin-top: 10px">
          <Form-item label="货运区域或航线:" prop="zyHyqy">
            <Input type="textarea" v-model="formValidate.zyHyqy" placeholder="请输入货运区域或航线"></Input>
          </Form-item>
        </Col>
        <Divider style="font-weight: 500;color: #5b89eb" orientation="center">经营范围-兼营</Divider>
        <Col span="24">
          <Form-item label="兼营:" prop="jy">
            <Input type="textarea" v-model="formValidate.jy" placeholder="请输入兼营"></Input>
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
        <Col span="12">
          <Form-item label="许可证号:" prop="zjhm">
            <Input v-model="formValidate.zjhm" placeholder="请输入许可证号"></Input>
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
import {saveOrUpdateXxkcJyxk, queryXxkcJyxk} from '@api/waterTransport/xxkcjyxk'
import {approveWaterWayBusinessData} from "@api/waterway/waterWayApprove";

export default {
  name: 'rx-information',
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
        qymc: '',
        tyshxydm: '',//统一社会信用代码
        fddbr: '',//法定代表人
        lxdh: '',//联系电话
        address: '',//地址
        jjlx: '',//企业经济类型
        zyKyhx: '',//客运航线
        zyHyqy: '',//货运区域或航线
        jy: '',//兼营
        backRemark: '',//兼营
      },
      ruleValidate: {
        qymc: [
          {required: true, message: '企业名称不能为空', trigger: 'blur'}
        ],
        tyshxydm: [
          {required: true, message: '统一社会信用代码不能为空', trigger: 'blur'}
        ],
        fddbr: [
          {required: true, message: '法定代表人不能为空', trigger: 'blur'}
        ],
        lxdh: [
          {required: true, message: '联系电话不能为空', trigger: 'blur'}
        ],
        address: [
          {required: true, message: '地址不能为空', trigger: 'blur'}
        ],
        jjlx: [
          {required: true, message: '企业经济类型不能为空', trigger: 'blur'}
        ]
      },
      isSave: false,
    }
  },
  methods: {
    handleSubmit(name, type) {
      this.$refs[name].validate((valid) => {
        let that = this
        if (valid) {
          let initData = that.formValidate
          initData.spzt = 1
          saveOrUpdateXxkcJyxk(initData).then(function (response) {
            responseUtil.dealResponse(that, response, () => {
              if (response.code == 0) {
                that.$emit('setProjectNo', response.data);
                that.formValidate.gid = response.data;
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
      queryXxkcJyxk(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          if (response.code === '0') {
            console.log(response.data)
            that.formValidate = response.data
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
