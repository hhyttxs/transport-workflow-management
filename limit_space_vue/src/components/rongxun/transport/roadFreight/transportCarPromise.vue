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
          <Col span="2" style="line-height: 2.5em;-webkit-line-clamp: 3;-webkit-box-orient: vertical;text-align: center;">
            <div>拟购危险货车分类数量</div>
          </Col>
          <Col span="22">
            <Row>
              <Col flex="2.275">
                <Form-item label="剧毒品专用车:" prop="">
                  <Input v-model="formValidate.contactName" placeholder="剧毒品专用车"></Input>
                </Form-item>
              </Col>
              <Col flex="2.725">
                <Form-item label="爆炸品专用车:" prop="">
                  <Input v-model="formValidate.contactName" placeholder="爆炸品专用车"></Input>
                </Form-item>
              </Col>
            </Row>
            <Row>
              <Col flex="2.275">
                <Form-item label="罐式专用车:" prop="">
                  <Input v-model="formValidate.contactName" placeholder="罐式专用车"></Input>
                </Form-item>
              </Col>
              <Col flex="2.725">
                <Form-item label="其他车辆:" prop="">
                  <Input v-model="formValidate.contactName" placeholder="其他车辆"></Input>
                </Form-item>
              </Col>
            </Row>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="承诺期起日:" prop="">
              <Date-picker type="date" placeholder="默认当前日期" v-model="formValidate.registDate"></Date-picker>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="承诺期止日:" prop="">
              <Date-picker type="date" placeholder="默认：+180日" v-model="formValidate.registDate"></Date-picker>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="车辆厂牌:" prop="">
              <Select v-model="formValidate.manageArea" placeholder="车辆厂牌">
              </Select>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="车辆型号:" prop="">
              <Input v-model="formValidate.statutoryReprese" placeholder="车辆型号"></Input>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="车辆数量:" prop="">
              <Input v-model="formValidate.statutoryReprese" placeholder="车辆数量"></Input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="核定载质量:" prop="">
              <Input v-model="formValidate.statutoryRepreseIdCard" placeholder="单位：克"></Input>
            </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <Form-item label="技术等级:" prop="">
              <Select v-model="formValidate.manageArea" filterable placeholder="技术等级">
                <Option value="1">一级</Option>
                <Option value="2">二级</Option>
              </Select>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="车辆长度:" prop="">
              <Input v-model="formValidate.statutoryRepreseIdCard" placeholder="单位：毫米"></Input>
            </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <Form-item label="车辆宽度:" prop="">
              <Input v-model="formValidate.statutoryReprese" placeholder="单位：毫米"></Input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="车辆高度:" prop="">
              <Input v-model="formValidate.statutoryRepreseIdCard" placeholder="单位：毫米"></Input>
            </Form-item>
          </Col>
        </Row>

      </Form>
    </div>
  </div>
  <div class="divInline">
    <rxHeadMiniChild :contrnt="headContent2" @changeShowState = "changeShowState"></rxHeadMiniChild>
  </div>
  <div class="divInline" v-if="isShow == true">
    <div class="permissionBasicInfo">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
        <Row>
          <Col span="12">
              <Form-item label="车辆类型:" prop="">
                <Select v-model="formValidate.manageArea" filterable placeholder="车辆类型">
                  <Option value="1">一级</Option>
                  <Option value="2">二级</Option>
                </Select>
              </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="总质量:" prop="">
              <Date-picker type="date" placeholder="默认：+180日" v-model="formValidate.registDate"></Date-picker>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="通讯工具及GPS安装:" prop="">
              <Select v-model="formValidate.manageArea" placeholder="车辆厂牌">
              </Select>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="罐车罐体容积:" prop="">
              <Input v-model="formValidate.statutoryReprese" placeholder="车辆型号"></Input>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="密度最大危货品名:" prop="">
              <Input v-model="formValidate.statutoryReprese" placeholder="车辆数量"></Input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="危险品密度:" prop="">
              <Input v-model="formValidate.statutoryRepreseIdCard" placeholder="单位：克"></Input>
            </Form-item>
          </Col>
        </Row>
      </Form>
      <Alert class="halfButton">
        <button  @click="handleSubmit('formValidate')">确定</button>
        <button  @click="handleSubmit('formValidate')">下一厂牌型号</button>
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
            title:'填报拟购运输车辆承诺书',
          },
          headContent2:{
            title:'危险品、放射性物品运输车辆附加信息',
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
