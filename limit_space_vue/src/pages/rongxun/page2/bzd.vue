<template>
  <div class="license-page">
    <div class="top-line"></div>

    <div class="page-content">
      <Form
        ref="formValidate"
        :model="formValidate"
        :rules="ruleValidate"
        :disabled="loading || (localTest && formValidate.status === '1')"
        :label-width="180"
      >
        <!-- 基本事项信息 -->
        <Row>
          <Col span="24">
            <FormItem label="事项名称:" prop="projectName">
              <Select
                v-model="formValidate.projectName"
                placeholder="请选择事项名称"
              >
                <Option
                  v-for="item in projectNameList"
                  :key="item.value"
                  :value="item.value"
                >
                  {{ item.label }}
                </Option>
              </Select>
            </FormItem>
          </Col>
        </Row>

        <Row>
          <Col span="24">
            <FormItem label="业务类型:" prop="businessType">
              <Select
                v-model="formValidate.businessType"
                placeholder="请选择业务类型"
              >
                <Option
                  v-for="item in businessTypeList"
                  :key="item.value"
                  :value="item.value"
                >
                  {{ item.label }}
                </Option>
              </Select>
            </FormItem>
          </Col>
        </Row>

        <Row>
          <Col span="24">
            <FormItem label="经办人:" prop="operator">
              <Select
                v-model="formValidate.operator"
                placeholder="请选择经办人"
              >
                <Option
                  v-for="item in operatorList"
                  :key="item.value"
                  :value="item.value"
                >
                  {{ item.label }}
                </Option>
              </Select>
            </FormItem>
          </Col>
        </Row>

        <!-- 企业信息 -->
        <Row :gutter="16">
          <Col span="24">
            <FormItem label="企业地址:" prop="enterpriseAddress">
              <Input
                v-model.trim="formValidate.enterpriseAddress"
                maxlength="200"
                placeholder="请输入企业地址"
              />
            </FormItem>
          </Col>
        </Row>

        <Row :gutter="16">
          <Col span="12">
            <FormItem label="营业执照发证日期:" prop="licenseIssueDate">
              <DatePicker
                type="date"
                :model-value="formValidate.licenseIssueDate"
                format="yyyyMMdd"
                :editable="false"
                clearable
                style="width: 100%"
                placeholder="请选择营业执照发证日期"
                @on-change="handleLicenseIssueDateChange"
              />
            </FormItem>
          </Col>

          <Col span="12">
            <FormItem
              label="统一社会信用代码:"
              prop="socialCreditCode"
              class="focus-error-field"
            >
              <Input
                v-model.trim="formValidate.socialCreditCode"
                maxlength="18"
                placeholder="请输入18位统一社会信用代码"
              />
            </FormItem>
          </Col>
        </Row>

        <Row :gutter="16">
          <Col span="12">
            <FormItem label="法人姓名:" prop="legalPersonName">
              <Input
                v-model.trim="formValidate.legalPersonName"
                maxlength="50"
                placeholder="请输入法人姓名"
              />
            </FormItem>
          </Col>

          <Col span="12">
            <FormItem
              label="法人身份证号:"
              prop="legalPersonIdNo"
            >
              <Input
                v-model.trim="formValidate.legalPersonIdNo"
                maxlength="18"
                placeholder="请输入法人身份证号"
              />
            </FormItem>
          </Col>
        </Row>

        <Row :gutter="16">
          <Col span="12">
            <FormItem label="法人联系电话:" prop="legalPersonPhone">
              <Input
                v-model.trim="formValidate.legalPersonPhone"
                maxlength="20"
                placeholder="请输入法人联系电话"
              />
            </FormItem>
          </Col>

          <Col span="12">
            <FormItem
              label="经济类型:"
              prop="economicType"
              class="right-form-item"
            >
              <Select
                v-model="formValidate.economicType"
                clearable
                placeholder="请选择经济类型"
              >
                <Option
                  v-for="item in economicTypeList"
                  :key="item.value"
                  :value="item.value"
                >
                  {{ item.label }}
                </Option>
              </Select>
            </FormItem>
          </Col>
        </Row>

        <!-- 运输经营范围 -->
        <div class="transport-section">
          <CheckboxGroup v-model="formValidate.transportTypes">
            <div
              v-for="item in transportTypeList"
              :key="item.value"
              class="transport-row"
            >
              <Checkbox :label="item.value">
                {{ item.label }}
              </Checkbox>
            </div>
          </CheckboxGroup>
        </div>

        <!-- 车辆信息 -->
        <div class="action-row">
          <Button type="primary" icon="md-add" @click="openVehicleModal">
            添加车辆信息
          </Button>
        </div>

        <div class="data-panel">
          <div class="panel-title">货物运输车辆情况</div>

          <table class="data-table">
            <thead>
            <tr>
              <th>序号</th>
              <th>厂牌型号</th>
              <th>数量</th>
              <th>车辆类型</th>
              <th>技术等级</th>
              <th>总质量</th>
              <th>核定载质量</th>
              <th>GPS安装</th>
              <th>车辆长宽高</th>
              <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="(item, index) in vehicleList" :key="item.id">
              <td>{{ index + 1 }}</td>
              <td>{{ item.brandModel }}</td>
              <td>{{ item.quantity }}</td>
              <td>{{ item.vehicleType }}</td>
              <td>{{ item.technicalLevel }}</td>
              <td>{{ item.totalMass }}</td>
              <td>{{ item.approvedLoad }}</td>
              <td>{{ item.gpsInstalled }}</td>
              <td>{{ item.vehicleSize }}</td>
              <td class="operation-cell">
                <a @click="editVehicle(index)">修改</a>
                <a @click="deleteVehicle(index)">删除</a>
              </td>
            </tr>

            <tr v-if="vehicleList.length === 0">
              <td colspan="10" class="empty-cell">暂无车辆信息</td>
            </tr>
            </tbody>
          </table>
        </div>

        <div class="submit-row">
          <Button :disabled="loading || (localTest && formValidate.status === '1')" type="primary" @click="submitForm">提交</Button>
          <Button :disabled="loading || (localTest && formValidate.status === '1')" style="margin-left: 10px" @click="resetForm">重置</Button>
          <Button :disabled="loading || (localTest && formValidate.status === '1')" style="margin-left: 10px" @click="saveDraft">暂存</Button>
        </div>
      </Form>
    </div>

    <!-- 添加/修改车辆信息弹窗 -->
    <Modal
      v-model="vehicleModalVisible"
      :title="vehicleEditIndex === -1 ? '添加车辆信息' : '修改车辆信息'"
      width="760"
      :mask-closable="false"
      :closable="true"
      class-name="vehicle-info-modal"
      @on-cancel="closeVehicleModal"
    >
      <div style="padding-top: 24px;">
        <Form
          ref="vehicleForm"
          :model="vehicleForm"
          :rules="vehicleRules"
          :label-width="110"
        >
        <Row :gutter="16">
          <Col span="12">
            <FormItem label="厂牌型号:" prop="brandModel">
              <Input
                v-model.trim="vehicleForm.brandModel"
                placeholder="请输入厂牌型号"
              />
            </FormItem>
          </Col>

          <Col span="12">
            <FormItem label="数量:" prop="quantity">
              <InputNumber
                v-model="vehicleForm.quantity"
                :min="1"
                :precision="0"
                style="width: 100%"
              />
            </FormItem>
          </Col>
        </Row>

        <Row :gutter="16">
          <Col span="12">
            <FormItem label="车辆类型:" prop="vehicleType" >
              <Input
                v-model.trim="vehicleForm.vehicleType"
                placeholder="请输入车辆类型"
              />
            </FormItem>
          </Col>

          <Col span="12">
            <FormItem label="技术等级:" prop="technicalLevel">
              <Select
                v-model="vehicleForm.technicalLevel"
                placeholder="请选择技术等级"
              >
                <Option value="一级">一级</Option>
                <Option value="二级">二级</Option>
                <Option value="三级">三级</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>

        <Row :gutter="16">
          <Col span="12">
            <FormItem label="总质量:" prop="totalMass">
              <Input
                v-model.trim="vehicleForm.totalMass"
                placeholder="请输入总质量"
              />
            </FormItem>
          </Col>

          <Col span="12">
            <FormItem label="核定载质量:" prop="approvedLoad">
              <Input
                v-model.trim="vehicleForm.approvedLoad"
                placeholder="请输入核定载质量"
              />
            </FormItem>
          </Col>
        </Row>

        <Row :gutter="16">
          <Col span="12">
            <FormItem label="GPS安装:" prop="gpsInstalled">
              <Select
                v-model="vehicleForm.gpsInstalled"
                placeholder="请选择"
              >
                <Option value="是">是</Option>
                <Option value="否">否</Option>
                <Option value="-">-</Option>
              </Select>
            </FormItem>
          </Col>

          <Col span="12">
            <FormItem label="车辆长宽高:" prop="vehicleSize">
              <Input
                v-model.trim="vehicleForm.vehicleSize"
                placeholder="例如：1111×1111×1111"
              />
            </FormItem>
          </Col>
        </Row>
      </Form>
      </div>

      <template #footer>
        <Button @click="vehicleModalVisible = false">
          取消
        </Button>

        <Button type="primary" @click="saveVehicle">
          确定
        </Button>
      </template>
    </Modal>
  </div>
</template>



<script>
import {resultPayload} from "@/utils/resultPayload";
import {licenseDateForPicker, licenseDateForStorage} from '@/utils/licenseWorkflow';

import {closeThisView, responseUtil} from "@/utils/rongxunUtil";
import {getUser, getToken} from "@/utils/auth"
import {saveOrUpdateDlhwjyxk} from "@/api/hy/dlhwjyxk"
import {getDlhwjyxkInfoByGid} from "@/api/hy/dlhwjyxk";

export default {
  name: 'EnterpriseTransportLicense',
  data() {
    const validatePhone = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入法人联系电话'));
        return;
      }

      const phoneReg = /^(\d{3,4}-?)?\d{7,11}$/;
      if (!phoneReg.test(value)) {
        callback(new Error('请输入正确的联系电话'));
        return;
      }

      callback();
    };

    const validateIdNo = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入法人身份证号'));
        return;
      }

      const idReg = /(^\d{15}$)|(^\d{17}[\dXx]$)/;
      if (!idReg.test(value)) {
        callback(new Error('请输入正确的身份证号'));
        return;
      }

      callback();
    };

    return {
      localTest: process.env.NODE_ENV === 'development' && process.env.VUE_APP_VEHICLE_LOCAL_TEST === 'true',
      loading: false,
      formValidate: {
        gid: '',
        status: '0',
        projectName: 'roadTransportLicense',
        businessType: 'firstApply',
        operator: '',
        enterpriseAddress: '',
        licenseIssueDate: '',
        socialCreditCode: '',
        legalPersonName: '',
        legalPersonIdNo: '',
        legalPersonPhone: '',
        economicType: '',
        transportTypes: ['generalFreight', 'largeObject']
      },

      projectNameList: [
        {
          value: 'roadTransportLicense',
          label: '道路货物运输经营（含普通货运、专用运输、大件运输）许可-设立'
        }
      ],

      businessTypeList: [
        {
          value: 'firstApply',
          label: '首次申办许可证'
        }
      ],

      operatorList: [
        {
          value: '经办人员',
          label: '经办人员'
        }
      ],

      economicTypeList: [
        { value: 'stateOwned', label: '国有经济' },
        { value: 'collective', label: '集体经济' },
        { value: 'private', label: '私营经济' },
        { value: 'individual', label: '个体经济' },
        { value: 'jointStock', label: '股份制经济' },
        { value: 'foreignFunded', label: '外商投资经济' },
        { value: 'hongKongMacaoTaiwan', label: '港澳台投资经济' },
        { value: 'other', label: '其他经济类型' }
      ],

      transportTypeList: [
        { value: 'generalFreight', label: '普通货运' },
        { value: 'largeObject', label: '大型物件运输' },
        { value: 'container', label: '集装箱运输' },
        { value: 'refrigerated', label: '冷藏保鲜运输' },
        { value: 'tank', label: '罐式运输' }
      ],

      ruleValidate: {
        projectName: [
          { required: true, message: '请选择事项名称', trigger: 'change' }
        ],
        businessType: [
          { required: true, message: '请选择业务类型', trigger: 'change' }
        ],
        operator: [
          { required: true, message: '请选择经办人', trigger: 'change' }
        ],
        enterpriseAddress: [
          { required: true, message: '请输入企业地址', trigger: 'blur' }
        ],
        licenseIssueDate: [
          { required: true, type: 'date', message: '请选择营业执照发证日期', trigger: 'change' }
        ],
        socialCreditCode: [
          { required: true, message: '请输入统一社会信用代码', trigger: 'blur' },
          {
            pattern: /^[0-9A-Z]{18}$/,
            message: '统一社会信用代码应为18位数字或大写字母',
            trigger: 'blur'
          }
        ],
        legalPersonName: [
          { required: true, message: '请输入法人姓名', trigger: 'change' }
        ],
        legalPersonIdNo: [
          { required: true, validator: validateIdNo, trigger: 'change' }
        ],
        legalPersonPhone: [
          { required: true, validator: validatePhone, trigger: 'change' }
        ],
        economicType: [
          { required: true, message: '请选择经济类型', trigger: 'change' }
        ]
      },

      vehicleList: [
        {
          id: 'vehicle-1',
          brandModel: '斯堪尼亚牌-YS2G4X23',
          quantity: 1,
          vehicleType: '半挂货运列车',
          technicalLevel: '二级',
          totalMass: '11111',
          approvedLoad: '1111',
          gpsInstalled: '-',
          vehicleSize: '1111×1111×1111'
        }
      ],

      vehicleModalVisible: false,
      vehicleEditIndex: -1,

      vehicleForm: {
        brandModel: '',
        quantity: 1,
        vehicleType: '',
        technicalLevel: '',
        totalMass: '',
        approvedLoad: '',
        gpsInstalled: '-',
        vehicleSize: ''
      },

      vehicleRules: {
        brandModel: [
          { required: true, message: '请输入厂牌型号', trigger: 'change' }
        ],
        quantity: [
          {
            required: true,
            type: 'number',
            message: '请输入数量',
            trigger: 'change'
          }
        ],
        vehicleType: [
          { required: true, message: '请输入车辆类型', trigger: 'change' }
        ],
        technicalLevel: [
          { required: true, message: '请选择技术等级', trigger: 'change' }
        ]
      }
    };
  },
  mounted() {
    this.formValidate.gid = this.$route.query.gid || '';
    if(process.env.VUE_APP_VEHICLE_LOCAL_TEST==='true') { this.vehicleList=[]; this.operatorList=[{value:'LOCAL TEST OPERATOR',label:'申请经办人'}]; }
    if(this.formValidate.gid) this.loadDraftDetail();
  },

  watch: {'$route.query.gid'(gid) {this.formValidate.gid=gid || ''; if(gid) this.loadDraftDetail();}},
  methods: {

    loadDraftDetail() {
      this.loading=true;
      getDlhwjyxkInfoByGid({
        gid: this.formValidate.gid
      }).then(raw => {
        const response=resultPayload(raw);

        if (String(response.code) !== "0") {
          this.$Message.error("查询暂存详情失败");
          return;
        }

        this.formValidate.gid = response.data.gid;
        this.formValidate.projectName = response.data.sxmc;
        this.formValidate.businessType = response.data.ywlx;
        this.formValidate.operator = response.data.jbr;

        this.formValidate.enterpriseAddress = response.data.qydz;
        this.formValidate.licenseIssueDate = licenseDateForPicker(response.data.yyzzfzrq);
        this.formValidate.status = response.data.status;
        this.formValidate.socialCreditCode = response.data.tyshxydm;

        this.formValidate.legalPersonName = response.data.frxm;
        this.formValidate.legalPersonIdNo = response.data.frsfzh;
        this.formValidate.legalPersonPhone = response.data.frlxdh;
        this.formValidate.economicType = response.data.jjlx;

        this.formValidate.transportTypes =
          (response.data.ysfs || "").split(",");

        if (response.data.vehicleList) {
          this.vehicleList = response.data.vehicleList;
        }
      }).catch(error => {
        console.error("查询暂存详情失败：", error);
        this.$Message.error(error.message || "查询暂存详情失败");
      }).finally(()=>{this.loading=false;});
    },
    handleLicenseIssueDateChange(value) {
      this.formValidate.licenseIssueDate = licenseDateForPicker(value);

      this.$nextTick(() => {
        if (this.$refs.formValidate) {
          this.$refs.formValidate.validateField(
            'licenseIssueDate',
            () => {}
          );
        }
      });
    },

    createEmptyVehicle() {
      return {
        brandModel: '',
        quantity: 1,
        vehicleType: '',
        technicalLevel: '',
        totalMass: '',
        approvedLoad: '',
        gpsInstalled: '-',
        vehicleSize: ''
      };
    },

    hideFieldError(prop) {
      const form = this.$refs.formValidate;

      if (!form || !form.fields) {
        return;
      }

      const field = form.fields.find(item => item.prop === prop);

      if (field) {
        field.validateState = '';
        field.validateMessage = '';
      }
    },

    closeVehicleModal() {
      this.vehicleModalVisible = false;
    },

    openVehicleModal() {
      this.vehicleEditIndex = -1;
      this.vehicleForm = this.createEmptyVehicle();
      this.vehicleModalVisible = true;

      this.$nextTick(() => {
        if (this.$refs.vehicleForm) {
          this.$refs.vehicleForm.resetFields();
        }
      });
    },

    editVehicle(index) {
      this.vehicleEditIndex = index;
      this.vehicleForm = {
        ...this.vehicleList[index]
      };
      this.vehicleModalVisible = true;
    },

    saveVehicle() {
      this.$refs.vehicleForm.validate(valid => {
        if (!valid) {
          return;
        }

        if (this.vehicleEditIndex === -1) {
          this.vehicleList.push({
            ...this.vehicleForm,
            id: `vehicle-${Date.now()}`
          });
        } else {
          this.vehicleList[this.vehicleEditIndex] = {...this.vehicleForm};
        }

        this.vehicleModalVisible = false;
      });
    },

    deleteVehicle(index) {
      this.$Modal.confirm({
        title: '提示',
        content: '确定删除这条车辆信息吗？',
        onOk: () => {
          this.vehicleList.splice(index, 1);
        }
      });
    },

    submitForm() {
      this.$refs.formValidate.validate(valid => { if(valid) this.persistLicense('1'); });
    },
    async persistLicense(status) {
      if(this.loading)return;
      this.loading=true;
      try {
        const date=this.formValidate.licenseIssueDate;
        const yyzz=licenseDateForStorage(date);
        const result=resultPayload(await saveOrUpdateDlhwjyxk({...this.formValidate,status,licenseIssueDate:yyzz,transportTypes:Array.isArray(this.formValidate.transportTypes)?this.formValidate.transportTypes.join(','):this.formValidate.transportTypes,vehicleList:this.vehicleList}));
        if(String(result.code)!=='0') throw new Error(result.msg || '保存失败');
        this.formValidate.gid=result.data && result.data.gid || this.formValidate.gid;
        this.formValidate.status=status;
        this.$Message.success(status==='0'?'暂存成功':'提交成功');
      } catch(e) {this.$Message.error(e.message || '保存失败');} finally {this.loading=false;}
    },
    resetForm() {
      this.$refs.formValidate.resetFields();
      this.vehicleList = [];
    },

    saveDraft() { return this.persistLicense('0'); },

  }
};
</script>

<style scoped>
.license-page {
  min-height: 100%;
  background: #ffffff;
  color: #17233d;
}

.top-line {
  width: 100%;
  height: 14px;
  background: #08058d;
}

.page-content {
  padding: 14px 66px 30px;
}

.license-page /deep/ .ivu-form-item {
  margin-bottom: 2px;
}

.license-page /deep/ .ivu-form-item-label {
  padding-right: 14px;
  color: #314158;
  font-size: 14px;
}

.license-page /deep/ .ivu-select-selection,
.license-page /deep/ .ivu-input,
.license-page /deep/ .ivu-date-picker-rel .ivu-input {
  height: 36px;
  line-height: 36px;
  border-color: #d8dce3;
  border-radius: 3px;
  background: #ffffff;
}

.license-page /deep/ .ivu-select-selected-value,
.license-page /deep/ .ivu-select-placeholder {
  height: 34px;
  line-height: 34px;
}

.right-form-item {
  margin-left: -90px;
}

.transport-section {
  margin-top: 3px;
  border: 1px solid #d8dce3;
  border-bottom: 0;
  border-radius: 3px;
}

.transport-row {
  height: 34px;
  padding: 6px 10px;
  border-bottom: 1px solid #d8dce3;
  background: #fafafa;
}

.transport-row:last-child {
  border-bottom: 1px solid #d8dce3;
}

.action-row {
  padding: 8px 0;
}

.data-panel {
  width: 100%;
  border: 1px solid #e0e3e8;
  background: #ffffff;
}

.panel-title {
  height: 35px;
  line-height: 35px;
  text-align: center;
  font-weight: 600;
  border-bottom: 1px solid #e0e3e8;
  background: #fafafa;
}

.data-table {
  width: 100%;
  border-collapse: collapse;
  table-layout: fixed;
}

.data-table th,
.data-table td {
  height: 38px;
  padding: 7px 8px;
  text-align: center;
  word-break: break-all;
  border-bottom: 1px solid #e0e3e8;
}

.data-table th {
  font-weight: 600;
  background: #fafafa;
}

.data-table tbody tr:last-child td {
  border-bottom: 0;
}

.operation-cell a {
  margin: 0 7px;
  color: #2d8cf0;
  cursor: pointer;
}

.empty-cell {
  color: #999999;
}

.submit-row {
  padding-top: 18px;
  text-align: center;
}
/* 默认隐藏该字段的错误提示 */
.license-page /deep/ .focus-error-field .ivu-form-item-error-tip {
  display: none !important;
}

/* 只有身份证输入框获得焦点时才显示错误提示 */
.license-page /deep/ .focus-error-field:focus-within .ivu-form-item-error-tip {
  display: block !important;
}


@media screen and (max-width: 1200px) {
  .page-content {
    padding-left: 24px;
    padding-right: 24px;
  }

  .right-form-item {
    margin-left: 0;
  }
}
</style>

<style>
/* 保持弹窗正常定位 */
.vehicle-info-modal .ivu-modal-content {
  position: relative;
}

/* 恢复标题区域高度 */
.vehicle-info-modal .ivu-modal-header {
  padding: 14px 16px !important;
  min-height: 50px;
}

/* 让表单从标题下面开始显示 */
.vehicle-info-modal .ivu-modal-body {
  padding: 20px 16px 4px !important;
}

/* 恢复每一行表单之间的距离 */
.vehicle-info-modal .ivu-form-item {
  margin-bottom: 22px !important;
}

/* 只提高 X 的点击层级，不修改其尺寸和位置 */
.vehicle-info-modal .ivu-modal-close {
  z-index: 9999 !important;
  pointer-events: auto !important;
  cursor: pointer;
}

/* 弹窗底部按钮区域 */
.vehicle-info-modal .ivu-modal-footer {
  padding: 12px 18px !important;
}
</style>

<style>
.vehicle-info-modal .ivu-form-item-error-tip {
  display: none !important;
}

.vehicle-info-modal
.ivu-form-item-error:focus-within
.ivu-form-item-error-tip {
  display: block !important;
}
</style>
