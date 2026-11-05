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
            <Form-item label="设施设备类型:" prop="">
              <Select v-model="formValidate.manageArea" placeholder="设施设备类型">
                <Option value="1">环境保护设备</Option>
                <Option value="2">消防设备</Option>
                <Option value="3">安全防护设备</Option>
                <Option value="4">停车场地</Option>
              </Select>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="具体配备况:" prop="">
              <TreeSelect v-model="formValidate.testTreeSelect2" :max-tag-count="1" multiple :data="treeData" placeholder="请选择具体配备况"/>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="所有权:" prop="">
              <Select v-model="formValidate.manageArea" placeholder="所有权">
                <Option value="1">自有</Option>
                <Option value="2"> 租赁</Option>
              </Select>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="租赁期限:" prop="">
              <Input v-model="formValidate.contactPhone" placeholder="1年以上"></Input>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="停车场地地址:" prop="">
              <Input v-model="formValidate.contactMail" placeholder="停车场地地址"></Input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="停车场地面积:" prop="">
              <Input v-model="formValidate.contactMail" placeholder="单位：平方米"></Input>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="产权证明或租赁协议:" prop="">
              <Upload action="//jsonplaceholder.typicode.com/posts/">
                <Button type="primary" icon="ios-cloud-upload-outline">上传文件</Button>
              </Upload>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="停车场地平面图:" prop="">
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
      name: "dangerOrRadiateEquipmentInfo_index",
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
            enterpriseDecription: '',
            testTreeSelect2:[]
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
            title:'填报危险货运、放射性物品运输设施设备配备信息',
          },
          isShow:true,
          treeData:[
            {
              title: 'parent1',
              expand: true,
              value: 'parent1',
              selected: false,
              checked: false,
              children: [
                {
                  title: 'parent 1-1',
                  expand: true,
                  value: 'parent1-1',
                  selected: false,
                  checked: false,
                  children: [
                    {
                      title: 'leaf 1-1-1',
                      value: 'leaf1',
                      selected: false,
                      checked: false,
                    },
                    {
                      title: 'leaf 1-1-2',
                      value: 'leaf2',
                      selected: false,
                      checked: false,
                    }
                  ]
                },
                {
                  title: 'parent 1-2',
                  expand: true,
                  value: 'parent1-2',
                  selected: false,
                  checked: false,
                  children: [
                    {
                      title: 'leaf 1-2-1',
                      value: 'leaf3',
                      selected: false,
                      checked: false,
                    },
                    {
                      title: 'leaf 1-2-1',
                      value: 'leaf4',
                      selected: false,
                      checked: false,
                    }
                  ]
                }
              ]
            }
          ]
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
