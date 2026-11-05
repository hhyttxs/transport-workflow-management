<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="90">
      <Form-item label="申报标题:" prop="">
        <Input v-model="formValidate.title" placeholder="请输入申报标题"></Input>
      </Form-item>

      <Row>
        <Col span="12">
          <Form-item label="姓名:" prop="required">
            <Input v-model="formValidate.name" placeholder="请输入姓名"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="身份证:" prop="required">
            <Input v-model="formValidate.idCard" placeholder="请输入身份证"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="性别:" prop="required">
            <Select v-model="formValidate.sex" placeholder="请选择性别">
              <Option value="0">男</Option>
              <Option value="1">女</Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="手机号:" prop="required">
            <Input v-model="formValidate.mobile" placeholder="请输入手机号"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="邮箱:" prop="">
            <Input v-model="formValidate.email" placeholder="请输入邮箱"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="出生日期:" prop="">
            <Date-picker type="date" placeholder="选择出生日期" v-model="formValidate.createDate"></Date-picker>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="民族:" prop="">
            <Select v-model="formValidate.nation" placeholder="请选择民族">
              <Option value="0">汉</Option>
              <Option value="1">等</Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="学位:" prop="">
            <Select v-model="formValidate.qualification" placeholder="请选择学位">
              <Option value="0">本科</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="所学专业: " prop="">
            <Input v-model="formValidate.major" placeholder="请输入所学专业"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="政治面貌:" prop="">
            <Select v-model="formValidate.politicsStatus" placeholder="请选择政治面貌">
              <Option value="0">群众</Option>
              <Option value="1">团员</Option>
              <Option value="2">党员</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="健康状况: " prop="">
            <Input v-model="formValidate.healthCondition" placeholder="请输入健康状况"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="婚姻状况:" prop="">
            <Select v-model="formValidate.maritalStatus" placeholder="请选择婚姻状况">
              <Option value="0">已婚</Option>
              <Option value="1">未婚</Option>
              <Option value="2">离异</Option>
              <Option value="3">丧偶</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="护照号码: " prop="">
            <Input v-model="formValidate.pastPortNum" placeholder="请输入护照号码"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="国籍:" prop="">
            <Select v-model="formValidate.nationality" placeholder="请选择国籍">
              <Option value="0">中国</Option>
              <Option value="1">美国</Option>
              <Option value="2">英国</Option>
              <Option value="3">法国</Option>
              <Option value="4">俄罗斯</Option>
              <Option value="5">其他</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="从业状态:" prop="">
            <Select v-model="formValidate.workState" placeholder="请选择从业状态">
              <Option value="0">在岗</Option>
              <Option value="1">离职</Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="工作单位: " prop="">
            <Input v-model="formValidate.workUnit" placeholder="请输入工作单位"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="职业:" prop="">
            <Select v-model="formValidate.occupation" placeholder="请选择职业">
              <Option value=""></Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="职务:" prop="">
            <Select v-model="formValidate.duty" placeholder="请选择职务">
              <Option value="0">普通员工</Option>
              <Option value="1">主管</Option>
              <Option value="2">经理</Option>
              <Option value="3">总监</Option>
              <Option value="4">董事长</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="月收入: " prop="">
            <Input v-model="formValidate.monthlyIncome" placeholder="请输入月收入"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="年收入: " prop="">
            <Input v-model="formValidate.yearlyIncome" placeholder="请输入年收入"></Input>
          </Form-item>
        </Col>
      </Row>

      <Form-item label="住址:" prop="">
        <Input v-model="formValidate.address" placeholder="请输入住址"></Input>
      </Form-item>
    </Form>
    <Alert class="button">
      <button @click="changeStepTo(1)">上一步</button>
      <button  @click="handleSubmit('formValidate')">提交</button>
      <button  @click="changeStepTo(3)">下一步</button>
    </Alert>
  </div>
</template>

<script>

import {browseAcceptByProjectNo, saveOrUpdateAccept} from '@/api/acceptFormCommon/acceptCommon'
  import {responseUtil} from "@/utils/rongxunUtil";
  import {getUser} from "@/utils/auth";
import {browseAcceptUserByProjectNo, saveOrUpdateAcceptUser} from "@api/acceptFormCommon/acceptUserCommon";

  export default {
    name: "rx-information",
    props:{
      formDataContrnt:{
        default:{}
      },
      projectNo:'',
      sxbm:''
    },
    mounted() {
      this.initDataByProjectNo()
    },
    data () {
      return {
        formValidate: {
          id:'',
          title:'',
          name:'',
          idCard:'',
          sex:'',
          mobile:'',
          email:'',
          createDate:'',
          nation:'',
          qualification:'',
          major:'',
          politicsStatus:'',
          healthCondition:'',
          maritalStatus:'',
          pastPortNum:'',
          nationality:'',
          workState:'',
          workUnit:'',
          occupation:'',
          duty:'',
          monthlyIncome:'',
          yearlyIncome:'',
          address:''
        },
        ruleValidate: {
          // required:[
          //   { required: true, message: '不能为空', trigger: 'blur' }
          // ],
          // name: [
          //   { required: true, message: '姓名不能为空', trigger: 'blur' }
          // ],
          // mail: [
          //   { required: true, message: '邮箱不能为空', trigger: 'blur' },
          //   { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
          // ],
          // city: [
          //   { required: true, message: '请选择城市', trigger: 'change' }
          // ],
          // gender: [
          //   { required: true, message: '请选择性别', trigger: 'change' }
          // ],
          // interest: [
          //   { required: true, type: 'array', min: 1, message: '至少选择一个爱好', trigger: 'change' },
          //   { type: 'array', max: 2, message: '最多选择两个爱好', trigger: 'change' }
          // ],
          // date: [
          //   { required: true, type: 'date', message: '请选择日期', trigger: 'change' }
          // ],
          // time: [
          //   { required: true, type: 'date', message: '请选择时间', trigger: 'change' }
          // ],
          // desc: [
          //   { required: true, message: '请输入个人介绍', trigger: 'blur' },
          //   { type: 'string', min: 20, message: '介绍不能少于20字', trigger: 'blur' }
          // ]

        },
        isSave:false,
      }
    },
    methods: {
      handleSubmit (name) {
        this.$refs[name].validate((valid) => {
          var that = this
          if (valid) {
            let userInfo = getUser();
            that.formValidate.areaCode = userInfo.xzqhCode
            that.formValidate.dValue = that.formDataContrnt.dValue
            that.formValidate.userId = userInfo.gid
            that.formValidate.sxbm = that.sxbm
            that.formValidate.projectType = that.formDataContrnt.projectType
            let initData = that.formValidate
            if(!that.isSave){
              that.isSave = true
              saveOrUpdateAcceptUser(initData).then(function (response) {
                responseUtil.dealResponse(that, response, () => {
                  if(response.code == 0){
                    that.$emit('setProjectNo',response.data.projectNo);
                    that.$Message.success('提交成功!');
                  }else{
                    that.isSave = false
                  }
                })
              })
            }else{
              that.$Message.error('请勿重复提交');
            }
          } else {
            this.$Message.error('表单验证失败!');
          }
        })
      },
      handleReset (name) {
        this.$refs[name].resetFields();
      },
      changeStepTo(number){
        this.$emit('changeStep',number)
      },
      initDataByProjectNo(){

        if(!this.projectNo || this.projectNo === ''){
          return
        }
        var that = this
        let initData = {}
        initData.projectNo = that.projectNo
        browseAcceptUserByProjectNo(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {

              that.formValidate.id = response.data.data.id
              that.formValidate.title = response.data.data.sbbt
              that.formValidate.name = response.data.data.xm
              that.formValidate.idCard = response.data.data.sfzh
              that.formValidate.sex = response.data.data.xb
              that.formValidate.mobile = response.data.data.mobile
              that.formValidate.email = response.data.data.mail
              that.formValidate.createDate = response.data.data.birthday
              that.formValidate.nation = response.data.data.mz
              that.formValidate.qualification = response.data.data.xw
              that.formValidate.major = response.data.data.sxzy
              that.formValidate.politicsStatus = response.data.data.zzmm
              that.formValidate.healthCondition = response.data.data.jkzk
              that.formValidate.maritalStatus = response.data.data.hyzk
              that.formValidate.pastPortNum = response.data.data.hzno
              that.formValidate.nationality = response.data.data.gj
              that.formValidate.workState = response.data.data.cyzt
              that.formValidate.workUnit = response.data.data.gzdw
              that.formValidate.occupation = response.data.data.zy
              that.formValidate.duty = response.data.data.zw
              that.formValidate.monthlyIncome = response.data.data.ysr
              that.formValidate.yearlyIncome = response.data.data.nsr
              that.formValidate.address = response.data.data.zz
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      }
    }
  }
</script>
