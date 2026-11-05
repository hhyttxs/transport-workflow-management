<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./permissionBasicInfo.less";
</style>
<template>
  <div class="divInline">
    <rxHeadMiniChild :contrnt="headContent" @changeShowState = "changeShowState"></rxHeadMiniChild>
  </div>
  <div class="divInline" v-if="isShow == true">
    <div class="permissionBasicInfo">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
        <Row>
          <Col span="12">
            <Form-item label="委托授权起日:" prop="">
              <Date-picker type="date" placeholder="委托授权起日" v-model="formValidate.registDate"></Date-picker>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="委托授权止日:" prop="">
              <Date-picker type="date" placeholder="默认：委托授权起日+90日" v-model="formValidate.registDate"></Date-picker>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="授权代理人:" prop="">
              <Input v-model="formValidate.contactName" placeholder="授权代理人"></Input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="代理人身份证号:" prop="">
              <Input v-model="formValidate.contactPhone" placeholder="代理人身份证号"></Input>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="代理人移动电话:" prop="">
              <Input v-model="formValidate.contactMail" placeholder="代理人移动电话"></Input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="公章印模（永久保存）:" prop="">
              <Upload action="//jsonplaceholder.typicode.com/posts/">
                <Button type="primary" icon="ios-cloud-upload-outline">上传文件</Button>
              </Upload>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="委托日期:" prop="">
              <Date-picker type="date" placeholder="默认：当前日期" v-model="formValidate.registDate"></Date-picker>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="代理人签字:" prop="">
              <Upload action="//jsonplaceholder.typicode.com/posts/">
                <Button type="primary" icon="ios-cloud-upload-outline">上传文件</Button>
              </Upload>
            </Form-item>
          </Col>
        </Row>

      </Form>
      <Alert class="button">
        <button  @click="handleSubmit('formValidate')">确定</button>
      </Alert>
    </div>
  </div>
</template>

<script>
  import rxHeadMiniChild from '@/components/rongxun/transport/rx-head/rx-head-mini-child'
  import {saveOrUpdateAccept} from '@/api/acceptFormCommon/acceptCommon'
  import {getUserId, responseUtil} from "@/utils/rongxunUtil";
    export default {
      name: "rx-information",
      props:{
        formDataContrnt:{
          default:{}
        }
      },components:{
        rxHeadMiniChild,
      },
      data () {
        return {
          formValidate: {
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
          headContent:{
            title:'填报授权委托书信息',
          },
          isShow:true
        }
      },
      methods: {
        handleSubmit (name) {
          this.$refs[name].validate((valid) => {
            if (valid) {
              var that = this
              that.formValidate.areaCode = that.formDataContrnt.areaCode
              that.formValidate.dValue = that.formDataContrnt.dValue
              that.formValidate.userId = that.formDataContrnt.userId
              that.formValidate.projectType = that.formDataContrnt.projectType
              let initData = that.formValidate
              if(!that.isSave){
                that.isSave = true
                saveOrUpdateAccept(initData).then(function (response) {
                  responseUtil.dealResponse(that, response, () => {
                    that.$Message.success('提交成功!');
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
        changeShowState(showState){
          this.isShow = showState
        }
      }
    }
</script>
