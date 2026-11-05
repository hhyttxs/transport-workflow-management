<style lang="less" scoped>
  @import "../../rx-label/rx-label.less";
  @import "./information.less";
</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
      <Divider orientation="left">公路工程建设项目设计文件审批</Divider>
      <Row style="margin-top: 5px">
        <Col span="24">
          <Form-item label="项目名称:" prop="xmmc">
            <Input v-model="formValidate.xmmc" placeholder="请输入项目名称"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="24">
          <Form-item label="申请人名称:" prop="sqrmc">
            <Input v-model="formValidate.sqrmc" placeholder="请输入申请人名称"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="24">
          <Form-item label="申请人地址:" prop="sqrdz">
            <Input v-model="formValidate.sqrdz" placeholder="请输入申请人地址"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row style="margin-top: 5px">
        <Col span="12">
          <Form-item label="申请人联系方式:" prop="sqrlxfs">
            <Input v-model="formValidate.sqrlxfs" placeholder="请输入申请人联系方式"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="申请时间:" prop="sqsj">
            <Date-picker type="date" placeholder="申请时间" value-format="yyyy-MM-dd"
                         v-model="formValidate.sqsj" class="searchItem"></Date-picker>
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
    </Form>
    <Alert class="button">
      <button @click="changeStepTo(1)">上一步</button>
      <button style="width: 110px" v-if="sxlb_code==='1'" @click="handleApprove(1)">受理通过</button>
      <button v-if="sxlb_code==='1'" @click="handleApprove(-1)">驳回</button>
      <button v-if="sxlb_code==='2'" @click="handleApprove(2)">审批</button>
      <button v-if="sxlb_code==='2'&&formValidate.state==='2'" @click="handleApprove(0)">退回</button>
      <button style="width: 110px" @click="changeStepTo(3)">查看材料</button>
    </Alert>
  </div>
</template>

<script>
  import {closeThisView, responseUtil} from '@/utils/rongxunUtil'
  import {getUser} from '@/utils/auth'
  import {queryGlxmsjwjsp} from '@api/roadWay/gljsxmjgys'
  import {approveRaodWayBusinessData} from '@api/roadWay/roadWayApprove'

  export default {
    name: 'rx-information',
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
      let userInfo = getUser()
      this.initDataByProjectNo()
    },
    data() {
      return {
        userId: '',
        userXzqh: '',
        formValidate: {
          backRemark: '',
          gid: '',
          sqrlxfs: '',
          sqsj: '',
          sqrmc: '',//工程名称
          sqrdz: '',//工程地点及主要控制点
          xmmc: '',//建设依据
          state: '',//技术标准与主要指标
        },
        ruleValidate: {
          xmmc: [
            {required: true, message: '项目名称不能为空', trigger: 'blur'}
          ],
          sqrmc: [
            {required: true, message: '申请人名称不能为空', trigger: 'blur'}
          ],
          sqrdz: [
            {required: true, message: '申请人地址不能为空', trigger: 'blur'}
          ],
          sqrlxfs: [
            {required: true, message: '申请人联系方式不能为空', trigger: 'blur'}
          ],
          sqsj: [
            {required: true, message: '申请时间不能为空', trigger: 'blur', type: 'date'}
          ],
        },
        isSave: false,
        baseData: {
          registArea: [],
          enterpriseState: []
        },

      }
    },
    methods: {
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
        initData.sxlb = 'gl_sx_gzl_1'
        initData.actionType = type
        initData.userCode = userInfo.code;
        initData.backRemark = this.formValidate.backRemark;
        approveRaodWayBusinessData(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.$Message.success(response.data);
              if (type == 1 || type == -1) {
                closeThisView(that, '/roadAdmin/roadAcceptList_index')
              } else {
                closeThisView(that, '/roadAdmin/roadApprovalList_index')
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
        this.$emit('changeStep', number)
      },
      initDataByProjectNo() {
        if (!this.projectNo || this.projectNo === '') {
          return
        }
        var that = this
        let initData = {}
        initData.projectNo = that.projectNo
        queryGlxmsjwjsp(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === '0') {
              console.log(response.data)
              that.formValidate = response.data
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
    }
  }
</script>
