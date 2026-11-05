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
            <Form-item label="事项名称:" prop="companyName">
              <Select v-model="formValidate.manageArea" placeholder="请选择事项名称">
                <Option value="beijing">道路货运</Option>
                <Option value="shanghai">危险货运</Option>
                <Option value="shenzhen">放射性物品运输</Option>
              </Select>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="业务类型:" prop="unifySocialCreditCode">
              <Select v-model="formValidate.manageArea" placeholder="请选择业务类型">
                <Option value="beijing">首次申办许可证</Option>
                <Option value="shanghai"> 跨项申办许可证</Option>
              </Select>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="统一社会信用代码:" prop="contactName">
              <Input v-model="formValidate.contactName" placeholder="统一社会信用代码"></Input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="登记日期:" prop="contactPhone">
              <Input v-model="formValidate.contactPhone" placeholder="登记日期"></Input>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="许可证号:" prop="">
              <Input v-model="formValidate.contactMail" placeholder="许可证号"></Input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="车辆购置情况:" prop="">
              <Select v-model="formValidate.manageArea" placeholder="请选择车辆购置情况">
                <Option value="beijing">拟购置车辆</Option>
                <Option value="shanghai">已购置车辆</Option>
              </Select>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="拟购车数:" prop="statutoryReprese">
              <Input v-model="formValidate.statutoryReprese" placeholder="拟购车数"></Input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="已购车数:" prop="">
              <Input v-model="formValidate.statutoryRepreseIdCard" placeholder="已购车数"></Input>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="营业执照: " prop="">
              <Upload action="//jsonplaceholder.typicode.com/posts/">
                <Button type="primary" icon="ios-cloud-upload-outline">上传文件</Button>
              </Upload>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="许可证: " prop="">
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
            title:'填报许可事项申报基础信息',
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
