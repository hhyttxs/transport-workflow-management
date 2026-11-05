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
            <Form-item label="行政区划:" prop="companyName" >
              <Select v-model="formValidate.manageArea" placeholder="请选择事项名称" disabled>
                <Option value="beijing">道路货运</Option>
                <Option value="shanghai">危险货运</Option>
                <Option value="shenzhen">放射性物品运输</Option>
              </Select>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="经营类型:" prop="unifySocialCreditCode" >
              <Select v-model="formValidate.manageArea" placeholder="请选择业务类型" disabled>
                <Option value="beijing">首次申办许可证</Option>
                <Option value="shanghai"> 跨项申办许可证</Option>
              </Select>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="业户名称:" prop="contactName">
              <Input v-model="formValidate.contactName" placeholder="统一社会信用代码"></Input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="业户地址:" prop="contactPhone">
              <Input v-model="formValidate.contactPhone" placeholder="登记日期"></Input>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="法人代表:" prop="">
              <Input v-model="formValidate.contactMail" placeholder="许可证号"></Input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="经济类型:" prop="">
              <Select v-model="formValidate.manageArea" placeholder="请选择车辆购置情况">
                <Option value="beijing">拟购置车辆</Option>
                <Option value="shanghai">已购置车辆</Option>
              </Select>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="身份证号:" prop="statutoryReprese">
              <Input v-model="formValidate.statutoryReprese" placeholder="拟购车数"></Input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="法人代表移动电话:" prop="">
              <Input v-model="formValidate.statutoryRepreseIdCard" placeholder="已购车数"></Input>
            </Form-item>
          </Col>
        </Row>

        <Row>
          <Col span="12">
            <Form-item label="许可证有效期: " prop="">
              <Upload action="//jsonplaceholder.typicode.com/posts/">
                <Button type="primary" icon="ios-cloud-upload-outline">上传文件</Button>
              </Upload>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="公章印模（永久保存）: " prop="">
              <Upload action="//jsonplaceholder.typicode.com/posts/">
                <Button type="primary" icon="ios-cloud-upload-outline">上传文件</Button>
              </Upload>
            </Form-item>
          </Col>
        </Row>
      </Form>
    </div>

  </div>
  <div class="divInline">
    <rxHeadMiniChildNoButton v-if="isShow == true" :contrnt="head2Content" @changeShowState = "changeShowState"></rxHeadMiniChildNoButton>
  </div>
  <div class="divInline" v-if="isShow == true">
    <div class="permissionBasicInfo-Table">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="190">
        <Row class-name="rowDiv">
          <Col span="4" style="height: 43px;line-height: 43px">
            经营范围
          </Col>
          <Col span="20">
            <Row>
              <Col span="24">
                业务类型
              </Col>
              <Col span="24">
                <Row>
                    <Col span="6">首次申报</Col>
                    <Col span="6">跨项申报</Col>
                    <Col span="6">拟扩大经营范围</Col>
                    <Col span="6">拟减少经营范围</Col>
                </Row>
              </Col>
            </Row>
          </Col>
        </Row>
        <Row class-name="rowDivChild" >
          <Col span="4">道路货运</Col>
          <Col span="5">
            <Select multiple :max-tag-count="1" v-model="formValidate.selectList" placeholder="请选择事项名称">
              <Option value="1">普通货运</Option>
              <Option value="2">专用运输（集装箱）</Option>
              <Option value="3">专用运输（冷藏保鲜）</Option>
              <Option value="4">专用运输（罐式运输）</Option>
              <Option value="5">大型物件运输</Option>
            </Select>
          </Col>
          <Col span="5">
            <Select multiple  v-model="formValidate.manageArea" placeholder="请选择事项名称">
              <Option value="beijing">普通货运</Option>
              <Option value="shanghai">专用运输（集装箱）</Option>
              <Option value="shenzhen">专用运输（冷藏保鲜）</Option>
              <Option value="shenzhen">专用运输（罐式运输）</Option>
              <Option value="shenzhen">大型物件运输</Option>
            </Select>
          </Col>
          <Col span="5">
            <Select multiple  v-model="formValidate.manageArea" placeholder="请选择事项名称">
              <Option value="beijing">普通货运</Option>
              <Option value="shanghai">专用运输（集装箱）</Option>
              <Option value="shenzhen">专用运输（冷藏保鲜）</Option>
              <Option value="shenzhen">专用运输（罐式运输）</Option>
              <Option value="shenzhen">大型物件运输</Option>
            </Select>
          </Col>
          <Col span="5">
            <Select multiple  v-model="formValidate.manageArea" placeholder="请选择事项名称">
              <Option value="beijing">普通货运</Option>
              <Option value="shanghai">专用运输（集装箱）</Option>
              <Option value="shenzhen">专用运输（冷藏保鲜）</Option>
              <Option value="shenzhen">专用运输（罐式运输）</Option>
              <Option value="shenzhen">大型物件运输</Option>
            </Select>
          </Col>
        </Row>
        <Row class-name="rowDivChild" >
          <Col span="4">危险货运</Col>
          <Col span="5">
            <TreeSelect v-model="formValidate.testTreeSelect" :max-tag-count="1" multiple :data="treeData" placeholder="请选择危险货运"/>
          </Col>
          <Col span="5">
            <Select v-model="formValidate.manageArea" placeholder="请选择事项名称">
              <Option value="beijing">道路货运</Option>
              <Option value="shanghai">危险货运</Option>
              <Option value="shenzhen">放射性物品运输</Option>
            </Select>
          </Col>
          <Col span="5">
            <Select v-model="formValidate.manageArea" placeholder="请选择事项名称">
              <Option value="beijing">道路货运</Option>
              <Option value="shanghai">危险货运</Option>
              <Option value="shenzhen">放射性物品运输</Option>
            </Select>
          </Col>
          <Col span="5">
            <Select v-model="formValidate.manageArea" placeholder="请选择事项名称">
              <Option value="beijing">道路货运</Option>
              <Option value="shanghai">危险货运</Option>
              <Option value="shenzhen">放射性物品运输</Option>
            </Select>
          </Col>
        </Row>
        <Row class-name="rowDivChild" >
          <Col span="4">放射性物品运输</Col>
          <Col span="5">
            <TreeSelect v-model="formValidate.testTreeSelect2" :max-tag-count="1" multiple :data="treeData" placeholder="请选择危险货运"/>
          </Col>
          <Col span="5">
            <Select v-model="formValidate.manageArea" placeholder="请选择事项名称">
              <Option value="beijing">道路货运</Option>
              <Option value="shanghai">危险货运</Option>
              <Option value="shenzhen">放射性物品运输</Option>
            </Select>
          </Col>
          <Col span="5">
            <Select v-model="formValidate.manageArea" placeholder="请选择事项名称">
              <Option value="beijing">道路货运</Option>
              <Option value="shanghai">危险货运</Option>
              <Option value="shenzhen">放射性物品运输</Option>
            </Select>
          </Col>
          <Col span="5">
            <Select v-model="formValidate.manageArea" placeholder="请选择事项名称">
              <Option value="beijing">道路货运</Option>
              <Option value="shanghai">危险货运</Option>
              <Option value="shenzhen">放射性物品运输</Option>
            </Select>
          </Col>
        </Row>
        <Row class-name="rowDivChild" >
          <Col span="4">剧毒品名备注</Col>
          <Col span="5">
            <Input v-model="formValidate.contactName" placeholder="统一社会信用代码"></Input>
          </Col>
          <Col span="5">
            放射性物品品名备注：
          </Col>
          <Col span="10">
            <Input v-model="formValidate.contactName" placeholder="统一社会信用代码"></Input>
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
  import rxHeadMiniChildNoButton from '@/components/rongxun/transport/rx-head/rx-head-mini-child-noButton'
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
        rxHeadMiniChildNoButton
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
            manageArea: [],
            manageLocation: '',
            enterpriseState: '',
            registDate: '',
            enterpriseDecription: '',
            selectList:[],
            testTreeSelect:[],
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
            title:'填报道路货运经营申请表-业户基本信息',
          },
          head2Content:{
            title:'填报道路货运经营申请表-业户拟申请经营范围',
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
