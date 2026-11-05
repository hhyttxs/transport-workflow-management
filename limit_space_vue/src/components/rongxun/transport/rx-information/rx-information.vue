<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
      <Form-item label="申报标题:" prop="title">
        <Input v-model="formValidate.title" placeholder="请输入申报标题"></Input>
      </Form-item>

      <Row>
        <Col span="12">
          <Form-item label="企业名称:" prop="companyName">
            <Input v-model="formValidate.companyName" placeholder="请输入企业名称"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="统一社会信用代码:" prop="unifySocialCreditCode">
            <Input v-model="formValidate.unifySocialCreditCode" placeholder="请输入统一社会信用代码"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="联系人名称:" prop="contactName">
            <Input v-model="formValidate.contactName" placeholder="请输入联系人名称"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="联系人手机号:" prop="contactPhone">
            <Input v-model="formValidate.contactPhone" placeholder="请输入联系人手机号"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="联系人邮箱:" prop="">
            <Input v-model="formValidate.contactMail" placeholder="请输入联系人邮箱"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="联系人身份证号:" prop="">
            <Input v-model="formValidate.contactIdCard" placeholder="请输入联系人身份证号"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="法定代表:" prop="statutoryReprese">
            <Input v-model="formValidate.statutoryReprese" placeholder="请输入法定代表"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="法定代表人身份证号码:" prop="">
            <Input v-model="formValidate.statutoryRepreseIdCard" placeholder="请输入法定代表人身份证号码"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="注册资本: " prop="">
            <Input v-model="formValidate.registeCapital" placeholder="请输入请输入注册资本(万元)"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="组织机构代码: " prop="">
            <Input v-model="formValidate.organizationCode" placeholder="请输入组织机构代码"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="经营范围: " prop="">
            <Input v-model="formValidate.manageScope" placeholder="请输入经营范围"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="注册地址行政区划:" prop="">
            <Select v-model="formValidate.registArea" clearable
                    placeholder="请选择注册地址行政区划" @keyup.enter.native="search">
              <Option v-for="item in baseData.registArea" :value="item.dictionaryValue" :key="item.dictionaryValue">{{
                item.dictionaryTitle
                }}</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="注册地址: " prop="">
            <Input v-model="formValidate.registLocation" placeholder="请输入注册地址"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="经营地址行政区划:" prop="">
            <Select v-model="formValidate.manageArea" clearable
                    placeholder="请选择经营地址行政区划" @keyup.enter.native="search">
              <Option v-for="item in baseData.registArea" :value="item.dictionaryValue" :key="item.dictionaryValue">{{
                item.dictionaryTitle
                }}</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="经营地址: " prop="">
            <Input v-model="formValidate.manageLocation" placeholder="请输入经营地址"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="企业状态:" prop="">
            <Select v-model="formValidate.enterpriseState" clearable
                    placeholder="请选择企业状态" @keyup.enter.native="search">
              <Option v-for="item in baseData.enterpriseState" :value="item.dictionaryValue" :key="item.dictionaryValue">{{
                item.dictionaryTitle
                }}</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="注册日期:" prop="">
            <Date-picker type="date" placeholder="选择日期" v-model="formValidate.registDate"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="企业描述: " prop="">
            <Input v-model="formValidate.enterpriseDecription" placeholder="请输入企业描述"></Input>
          </Form-item>
        </Col>
      </Row>
<!--      <Form-item>-->
<!--        <Button type="primary" @click="handleSubmit('formValidate')">提交</Button>-->
<!--        <Button type="ghost" @click="handleReset('formValidate')" style="margin-left: 8px">重置</Button>-->
<!--      </Form-item>-->
    </Form>
    <Alert class="button">
      <button  @click="changeStepTo(1)">上一步</button>
      <button  @click="handleSubmit('formValidate')">提交</button>
      <button  @click="changeStepTo(3)">下一步</button>
    </Alert>
  </div>
</template>

<script>
  import {saveOrUpdateAccept,browseAcceptByProjectNo} from '@/api/acceptFormCommon/acceptCommon'
  import {browseDatadictionaryByDtype,browseXzqhList} from '@/api/datadictionaryCommon/datadictionaryCommon'
  import {getUserId, responseUtil} from "@/utils/rongxunUtil";
  import {getUser} from "@/utils/auth"
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
        this.initXzqh();
        this.initData();
        let userInfo = getUser();
        this.formValidate.registArea = userInfo.xzqhCode
        this.formValidate.manageArea = userInfo.xzqhCode
        this.initDataByProjectNo()
      },
      data () {
        return {
          userId:'',
          userXzqh:'',
          formValidate: {
            id:'',
            title: '',
            companyName: '',
            unifySocialCreditCode: '',
            contactName: '',
            contactPhone: '',
            contactMail: '',
            contactIdCard: '',
            statutoryReprese: '',
            statutoryRepreseIdCard: '',
            registeCapital: '',
            organizationCode: '',
            manageScope: '',
            registArea: '',
            registLocation: '',
            manageArea: '',
            manageLocation: '',
            enterpriseState: '',
            registDate: '',
            enterpriseDecription: ''
          },
          ruleValidate: {
            title:[
              { required: true, message: '申报标题不能为空', trigger: 'blur' }
            ],
            companyName: [
              { required: true, message: '企业名称不能为空', trigger: 'blur' }
            ],
            unifySocialCreditCode: [
              { required: true, message: '统一社会信用代码不能为空', trigger: 'blur' }
            ],
            contactName: [
              { required: true, message: '联系人名称不能为空', trigger: 'blur' }
            ],
            contactPhone: [
              { required: true, message: '联系人手机号不能为空', trigger: 'blur' }
            ],
            statutoryReprese: [
              { required: true, message: '法定代表不能为空', trigger: 'blur' }
            ]
            // ,
            // desc: [
            //   { required: true, message: '请输入个人介绍', trigger: 'blur' },
            //   { type: 'string', min: 20, message: '介绍不能少于20字', trigger: 'blur' }
            // ]
          },
          isSave:false,
          baseData: {
            registArea:[],
            enterpriseState:[]
          }
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
                saveOrUpdateAccept(initData).then(function (response) {
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
              that.$Message.error('表单验证失败!');
            }
          })
        },
        handleReset (name) {
          this.$refs[name].resetFields();
        },
        changeStepTo(number){
          this.$emit('changeStep',number)
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
        initDataByProjectNo(){
          if(!this.projectNo || this.projectNo === ''){
              return
          }
          var that = this
          let initData = {}
          initData.projectNo = that.projectNo
          browseAcceptByProjectNo(initData).then(function (response) {
            responseUtil.dealResponse(that, response, () => {
              if (response.code === "0") {
                that.formValidate.id = response.data.data.id
                that.formValidate.title = response.data.data.title
                that.formValidate.companyName = response.data.data.companyName
                that.formValidate.unifySocialCreditCode = response.data.data.unifySocialCreditCode
                that.formValidate.contactName = response.data.data.contactName
                that.formValidate.contactPhone = response.data.data.contactPhone
                that.formValidate.contactMail = response.data.data.contactMail
                that.formValidate.contactIdCard = response.data.data.contactIdCard
                that.formValidate.statutoryReprese = response.data.data.statutoryReprese
                that.formValidate.statutoryRepreseIdCard = response.data.data.statutoryRepreseIdCard
                that.formValidate.registeCapital = response.data.data.registeCapital
                that.formValidate.organizationCode = response.data.data.organizationCode
                that.formValidate.manageScope = response.data.data.manageScope
                that.formValidate.registArea = response.data.data.registArea
                that.formValidate.registLocation = response.data.data.registLocation
                that.formValidate.manageArea = response.data.data.manageArea
                that.formValidate.manageLocation = response.data.data.manageLocation
                that.formValidate.enterpriseState = response.data.data.enterpriseState
                that.formValidate.registDate = response.data.data.registDate
                that.formValidate.enterpriseDecription = response.data.data.enterpriseDecription
              } else {
                responseUtil.alertErrMsg(response.msg)
              }
            })
          })
        }
      }
    }
</script>
