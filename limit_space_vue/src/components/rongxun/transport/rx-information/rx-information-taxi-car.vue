<style lang="less">
  @import "../../rx-label/rx-label.less";
  @import "./information.less";

</style>
<template>
  <div class="rx-information">
    <Alert type="warning">提醒:带 <span class="rx-information-span">*</span> 号为必填项，请根据实际情况进行填写。</Alert>
    <Form ref="taxiCarForm" :model="taxiCarForm" :rules="ruleValidate" :label-width="190">
      <Row>
        <Col span="12">
          <Form-item label="营运号牌:" prop="taxiBrandNum">
            <Input v-model="taxiCarForm.taxiBrandNum" maxlength="7" placeholder="请输入营运号牌"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="车牌号:" prop="taxiPlateNum">
            <Input v-model="taxiCarForm.taxiPlateNum" maxlength="7" placeholder="请输入车牌号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Row>
            <Col span="18">
              <Form-item label="管辖区域:" prop="areaRegionId">
                <Select v-model="taxiCarForm.areaRegionId"
                        :disabled="ywType==='1'||ywType==='2'||taxiCarForm.companyBusinessName?true:false"
                        @on-change="changeCarTypeMain(1,0)" clearable
                        placeholder="请选择管辖区域">
                  <Option v-for="item in areaRegionList" :value="item.AREAREGIONID" :key="item.AREAREGIONID">{{
                    item.AREAREGIONNAME
                    }}
                  </Option>
                </Select>
              </Form-item>
            </Col>
            <Col span="6">
              <Form-item :label-width="0" prop="areaDistrictId">
                <Select v-model="taxiCarForm.areaDistrictId"
                        :disabled="ywType==='1'||ywType==='2'||taxiCarForm.companyBusinessName?true:false" clearable
                        @on-change="createTaxiLicense(taxiCarForm.areaDistrictId)"
                        placeholder="请选择管辖区域">
                  <Option v-for="item in areaDistricList" :value="item.AREAREGIONID" :key="item.AREAREGIONID">{{
                    item.AREAREGIONNAME
                    }}
                  </Option>
                </Select>
              </Form-item>
            </Col>
          </Row>
        </Col>
        <Col span="12">
          <Form-item label="公司代码:" prop="companyCode">
            <Input v-model="taxiCarForm.companyCode" :disabled="taxiCarForm.indBusinessName?true:false"
                   placeholder="请输入公司代码"></Input>
          </Form-item>
        </Col>

      </Row>

      <Row>
        <Col span="12">
          <Form-item label="公司工商核准名称:" prop="companyBusinessName">
            <Select v-model="taxiCarForm.companyBusinessName" clearable @on-change="handleCompanyListCreate"
                    :disabled="taxiCarForm.indBusinessName?true:false"
                    placeholder="请输入公司工商核准名称" filterable>
              <Option v-for="item in companyBusinessNameList" :value="item.COMPANYNAME" :key="item.COMPANYNAME">
                {{
                item.COMPANYNAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="公司地址: " prop="companyAddress">
            <Input v-model="taxiCarForm.companyAddress" :disabled="taxiCarForm.indBusinessName?true:false"
                   placeholder="请输入公司地址"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="个体工商核准名称: " prop="indBusinessName">
            <Input v-model="taxiCarForm.indBusinessName"
                   :disabled='taxiCarForm.companyBusinessName?true:false'
                   placeholder="请输入个体工商核准名称"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="住址:" prop="personageAddress">
            <Input v-model="taxiCarForm.personageAddress" :disabled='taxiCarForm.companyBusinessName?true:false'
                   placeholder="请输入住址"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="法定代表人: " prop="legalPersonName">
            <Input v-model="taxiCarForm.legalPersonName" placeholder="请输入法定代表人"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="法人身份证:" prop="legalPersonId">
            <Input v-model="taxiCarForm.legalPersonId" placeholder="请输入法人身份证"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="电话: " prop="tel">
            <Input v-model="taxiCarForm.tel" placeholder="请输入电话"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="手机:" prop="mobilePhone">
            <Input v-model="taxiCarForm.mobilePhone" placeholder="请输入手机"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="出生日期: " prop="birthDate">
            <Date-picker type="date" placeholder="出生日期" value-format="yyyy-MM-dd"
                         v-model="taxiCarForm.birthDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="车辆总数:" prop="taxiCount">
            <Input v-model="taxiCarForm.taxiCount" placeholder="请输入车辆总数"></Input>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="许可证号:" prop="taxiCertificateNum">
            <Input v-model="taxiCarForm.taxiCertificateNum" disabled placeholder="请输入许可证号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="营运证号: " prop="taxiLicenseNum">
            <Input v-model="taxiCarForm.taxiLicenseNum" disabled placeholder="请输入营运证号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="许可证有效起:" prop="licenseStartDate">
            <Date-picker type="date" placeholder="许可证有效起" value-format="yyyy-MM-dd"
                         v-model="taxiCarForm.licenseStartDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="许可证有效止: " prop="licenseEndDate">
            <Date-picker type="date" placeholder="许可证有效止" value-format="yyyy-MM-dd"
                         v-model="taxiCarForm.licenseEndDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="合同对应号牌编号:" prop="contractPlate">
            <Select v-model="taxiCarForm.contractPlate" clearable
                    placeholder="请选择合同对应号牌编号" filterable allow-create @on-create="handleContractPlateCreate">
              <Option v-for="item in contractPlateList" :value="item.CONTRACTNUM" :key="item.CONTRACTNUM">{{
                item.CONTRACTNUM
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="合同类型: " prop="contractTypeId">
            <Select v-model="taxiCarForm.contractTypeId" clearable
                    placeholder="请选择经营性质">
              <Option v-for="item in contractTypeList" :value="item.CONTRACTTYPEID" :key="item.CONTRACTTYPEID">{{
                item.CONTRACTNAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <Form-item label="合同号牌有效期起:" prop="contractStartDate">
            <Date-picker type="date" placeholder="许可证有效起" value-format="yyyy-MM-dd"
                         v-model="taxiCarForm.contractStartDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="合同号牌有效期止: " prop="contractEndDate">
            <Date-picker type="date" placeholder="许可证有效止" value-format="yyyy-MM-dd"
                         v-model="taxiCarForm.contractEndDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="车型号:" prop="carTypeMainId">
            <Select v-model="taxiCarForm.carTypeMainId" @on-change="changeCarTypeMain(2,0)" clearable
                    placeholder="请选择车型号" style="width: 49%">
              <Option v-for="item in carTypeaMainList" :value="item.CARTYPEMAINID" :key="item.CARTYPEMAINID">{{
                item.CARTYPEMAINNAME
                }}
              </Option>
            </Select>
            <Select v-model="taxiCarForm.carTypeViceId" clearable
                    placeholder="请选择车型号" style="width: 49%">
              <Option v-for="item in carTypeaViceList" :value="item.CARTYPEVICEID" :key="item.CARTYPEVICEID">{{
                item.CARTYPEVICENAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="燃料种类: " prop="fuelTypeId">
            <Select v-model="taxiCarForm.fuelTypeId" clearable @on-change="changeCarTypeMain(3,0)"
                    placeholder="请选择燃料种类" style="width: 49%">
              <Option v-for="item in fuelTypeList" :value="item.FUELTYPEID" :key="item.FUELTYPEID">{{
                item.FUELTYPENAME
                }}
              </Option>
            </Select>
            <Select v-model="taxiCarForm.fuelSmallTypeId" clearable
                    placeholder="请选择燃料种类" style="width: 49%">
              <Option v-for="item in fuelSmallTypeList" :value="item.FUELSMALLTYPEID" :key="item.FUELSMALLTYPEID">{{
                item.FUELSMALLTYPENAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="车身颜色:" prop="carBodyColorId">
            <Select v-model="taxiCarForm.carBodyColorId" clearable
                    placeholder="车身颜色">
              <Option v-for="item in vehicleColorList" :value="item.COLORID" :key="item.COLORID">{{
                item.COLORNAME
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="大架号:" prop="bigNumber">
            <Input v-model="taxiCarForm.bigNumber" maxlength="17" placeholder="请输入大架号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="发动机号: " prop="engineNumber">
            <Input v-model="taxiCarForm.engineNumber" placeholder="请输入发动机号"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Form-item label="经营区域: " prop="businessArea">
            <Input v-model="taxiCarForm.businessArea" placeholder="请输入经营区域" disabled></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="经济性质:" prop="economyNnatureId">
            <Select v-model="taxiCarForm.economyNnatureId" clearable
                    placeholder="请选择经济性质">
              <Option v-for="item in economicNatureList" :value="item.ECONNATUREID" :key="item.ECONNATUREID">{{
                item.ECONOMICNATURE
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="经营性质: " prop="operateNatureId">
            <Select v-model="taxiCarForm.operateNatureId" clearable
                    placeholder="请选择经营性质">
              <Option v-for="item in businessNatureList" :value="item.BUSINNATUREID" :key="item.BUSINNATUREID">{{
                item.BUSINESSNATURE
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="核定运价:" prop="vouchFareId">
            <Select v-model="taxiCarForm.vouchFareId" clearable
                    placeholder="核定运价">
              <Option v-for="item in approvedTariffList" :value="item.APPTARIFFID" :key="item.APPTARIFFID">{{
                item.APPTARIFFMONEY
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="座位数: " prop="seating">
            <Input v-model="taxiCarForm.seating" placeholder="请输入座位数"></Input>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="银行登记:" prop="banksRegister">
            <Select class="railwayApplyListSelect"
                    v-model="taxiCarForm.banksRegister">
              <Option value=""></Option>
              <Option value="1">是</Option>
              <Option value="2">否</Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="法院查封: " prop="courtSealed">
            <Select class="railwayApplyListSelect"
                    name="unitExist"
                    v-model="taxiCarForm.courtSealed">
              <Option value=""></Option>
              <Option value="1">是</Option>
              <Option value="2">否</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="核定日期:" prop="vouchDate">
            <Date-picker type="date" placeholder="核定日期" value-format="yyyy-MM-dd"
                         v-model="taxiCarForm.vouchDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="营运日期: " prop="operatingData">
            <Date-picker type="date" placeholder="营运日期" value-format="yyyy-MM-dd"
                         v-model="taxiCarForm.operatingData" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="重营运日期:" prop="reopenDate">
            <Date-picker type="date" placeholder="重营运日期" value-format="yyyy-MM-dd"
                         v-model="taxiCarForm.reopenDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="换型日期: " prop="remodelDate">
            <Date-picker type="date" placeholder="换型日期" value-format="yyyy-MM-dd"
                         v-model="taxiCarForm.remodelDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="出厂日期:" prop="factoryDate">
            <Date-picker type="date" placeholder="出厂日期" value-format="yyyy-MM-dd"
                         v-model="taxiCarForm.factoryDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="车辆使用年限: " prop="vehicleUseAgeLimit">
            <Select class="railwayApplyListSelect"
                    name="unitExist" placeholder="请选择车辆使用年限"
                    v-model="taxiCarForm.vehicleUseAgeLimit">
              <Option value="1">8</Option>
              <Option value="2">5</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="车辆使用截至日期:" prop="vehicleUseExpireDate">
            <Date-picker type="date" placeholder="车辆使用截至日期" value-format="yyyy-MM-dd"
                         v-model="taxiCarForm.vehicleUseExpireDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="停业日期: " prop="closedDate">
            <Date-picker type="date" placeholder="停业日期" value-format="yyyy-MM-dd"
                         v-model="taxiCarForm.closedDate" class="searchItem"></Date-picker>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="计价器号:" prop="meterNumber">
            <Input v-model="taxiCarForm.meterNumber" type="textarea" placeholder="请输入计价器号"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="排气量:" prop="displacement">
            <Select v-model="taxiCarForm.displacement" clearable
                    placeholder="请选择排气量" filterable allow-create @on-create="handleDisplacementCreate">
              <Option v-for="item in displacementList" :value="item.DISPLACEMENT" :key="item.DISPLACEMENT">{{
                item.DISPLACEMENT
                }}
              </Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="GPS:" prop="gps">
            <Select class="railwayApplyListSelect"
                    v-model="taxiCarForm.gps">
              <Option value=""></Option>
              <Option value="1">有</Option>
              <Option value="2">无</Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="核发: " prop="issuedType">
            <Select class="railwayApplyListSelect"
                    v-model="taxiCarForm.issuedType">
              <Option value=""></Option>
              <Option value="1">换</Option>
              <Option value="3">核</Option>
              <Option value="2">补</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="变更记录:" prop="changeRecord">
            <Input type="textarea" v-model="taxiCarForm.changeRecord" placeholder="请输入变更记录"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item label="备注: " prop="remark">
            <Input type="textarea" v-model="taxiCarForm.remark" placeholder="请输入备注"></Input>
          </Form-item>
        </Col>
      </Row>
      <!--      <Form-item>-->
      <!--        <Button type="primary" @click="handleSubmit('taxiCarForm')">提交</Button>-->
      <!--        <Button type="ghost" @click="handleReset('taxiCarForm')" style="margin-left: 8px">重置</Button>-->
      <!--      </Form-item>-->
    </Form>
    <Alert class="button">
      <!--      <button @click="changeStepTo(1)">上一步</button>-->
      <button v-if="ywType==='1'" @click="handleApprove(1)">受理</button>
      <button v-if="ywType==='2'" @click="handleApprove(2)">审批</button>
      <button v-if="ywType==='2'&&taxiCarForm.state==='2'" @click="handleApprove(0)">退回</button>
      <button style="width: 120px" v-if="ywType==='0'" @click="createLicense()">生成许可证号</button>
      <button v-if="ywType==='0'" @click="handleSubmit('taxiCarForm')">提交</button>
      <!--      <button @click="changeStepTo(3)">下一步</button>-->
    </Alert>
  </div>
</template>

<script>
  import {responseUtil, closeThisView} from "@/utils/rongxunUtil";
  import {
    queryTaxiArea,
    queryContractType,
    queryEconomicNatureList,
    querySecondaryLinkage,
    createTaxiLicense,
    queryApprovedTariffList,
    queryCarTypeaMain,
    queryFuelTypeList,
    queryVehicleColorList,
    queryContractPlateList,
    queryExhaustVolumeList,
    saveTaxiCarInfo,
    queryTaxiCarDetailInfo, checkTaxiCarNum, createLicense
  } from '../../../../api/taxi/taxiCarApi'
  import {
    approveBusinessData,
    searchCompanyInfoById,
    searchCompanyListByName
  } from "../../../../api/taxi/taxiCompanyApi";
  import {getUser} from "@/utils/auth"
  import {checkDriverIdNum} from "../../../../api/taxi/taxiDriver";

  export default {
    name: "rx-information",
    components: {},

    props: {
      formDataContrnt: {
        default: {}
      },
      projectNo: '',
      sxbm: '', ywType: ''
    },
    mounted() {
      this.initTaxiArea();
      this.initContractType();
      this.initCarTypeaMainList();
      this.initFuelTypeList();
      this.initVehicleColorList();
      this.initEconomicNature();
      this.initApprovedTariffList();
      this.initCompanyBusinessNameList();
      this.initExhaustVolumeList();
      this.initDataByProjectNo()
    },
    created() {
      this.taxiCarForm.operateNatureId = '39';
      this.taxiCarForm.seating = '5人';
      this.taxiCarForm.vehicleUseAgeLimit = '1';
    },
    data() {
      const checkTaxiBrandNum = (rule, value, callback) => {
        let express = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{7}$/;
        if (value.search(express) == -1) {
          callback(new Error("营运牌号格式不正确"))
        }
        callback()
      }
      const checkBigNumber = (rule, value, callback) => {
        let express = /(?!^\d+$)(?!^[A-Z]+$)^[A-Z0-9]+$/;
        if (value.search(express) == -1) {
          callback(new Error("车架号格式不正确,请输入大写字母和数字组合"))
        }
        if (value.length != 17) {
          callback(new Error("车架号请输入17位"))
        }
        callback()
      }
      return {
        userId: '',
        userXzqh: '',
        taxiCarForm: {
          state: '',
          taxiArchiveId: '',
          companyRecordId: '',
          areaRegionId: '',
          areaDistrictId: '',
          taxiBrandNum: '',
          taxiPlateNum: '',
          companyCode: '',
          companyBusinessName: '',
          companyAddress: '',
          indBusinessName: '',
          personageAddress: '',
          legalPersonName: '',
          legalPersonId: '',
          mobilePhone: '',
          tel: '',
          taxiCount: '',
          birthDate: '',
          taxiCertificateNum: '',
          taxiLicenseNum: '',
          licenseStartDate: '',
          licenseEndDate: '',
          contractTypeId: '',
          contractPlate: '',
          contractEndDate: '',
          contractStartDate: '',
          carTypeMainId: '',
          carTypeViceId: '',
          fuelSmallTypeId: '',
          fuelTypeId: '',
          carBodyColorId: '',
          bigNumber: '',
          engineNumber: '',
          businessArea: '',
          economyNnatureId: '',
          operateNatureId: '',
          vouchFareId: '',
          seating: '',
          banksRegister: '',
          courtSealed: '',
          vouchDate: '',
          operatingData: '',
          reopenDate: '',
          remodelDate: '',
          factoryDate: '',
          vehicleUseAgeLimit: '',
          vehicleUseExpireDate: '',
          closedDate: '',
          meterNumber: '',
          displacement: '',
          gps: '',
          issuedType: '',
          changeRecord: '',
          remark: '',
        },
        ruleValidate: {
          taxiBrandNum: [
            {required: true, message: '营运号牌不能为空', trigger: 'blur'},
            {required: true, trigger: 'blur', validator: checkTaxiBrandNum},
            {
              trigger: 'blur', validator: (rule, value, cb) => {
                this.checkTaxiCarNum(rule, value, cb, 1)
              }
            }
          ], taxiPlateNum: [
            {required: true, message: '车牌号不能为空', trigger: 'blur'},
            {
              trigger: 'blur', validator: (rule, value, cb) => {
                this.checkTaxiCarNum(rule, value, cb, 2)
              }
            }
          ],
          bigNumber: [
            {required: true, message: '大架号不能为空', trigger: 'blur'},
            {required: true, trigger: 'blur', validator: checkBigNumber}
          ],
          areaRegionId: [
            {required: true, message: '管辖区域不能为空', trigger: 'blur'},
            {required: true, message: '管辖区域不能为空', trigger: 'change'}
          ],
          areaDistrictId: [
            {required: true, message: '管辖区域不能为空', trigger: 'blur'},
            {required: true, message: '管辖区域不能为空', trigger: 'change'}
          ],
          legalPersonName: [
            {required: true, message: '法人不能为空', trigger: 'blur'}
          ],
          legalPersonId: [
            {required: true, message: '法人身份证号不能为空', trigger: 'blur'},
            {
              trigger: 'blur',
              validator: (rule, value, cb) => {
                this.checkDriverIdNum(rule, value, cb)
              }
            }
          ],
          tel: [
            {required: true, message: '电话不能为空', trigger: 'blur'}
          ],
          mobilePhone: [
            {required: true, message: '手机不能为空', trigger: 'blur'}
          ],
          birthDate: [
            {required: true, message: '出生日期不能为空', trigger: 'blur', type: 'date'}
          ],
          /*taxiCount: [
            {required: true, message: '车辆总数不能为空', trigger: 'blur', type: 'number'},
            {required: true, message: '车辆总数不能为空', trigger: 'change', type: 'number'}
          ],*/
          taxiCertificateNum: [
            {required: true, message: '许可证号不能为空', trigger: 'blur'}
          ],
          taxiLicenseNum: [
            {required: true, message: '营运证号不能为空', trigger: 'blur'}
          ],
          licenseStartDate: [
            {required: true, message: '许可证有效期起不能为空', trigger: 'blur', type: 'date'}
          ],
          licenseEndDate: [
            {required: true, message: '许可证有效期止不能为空', trigger: 'blur', type: 'date'}
          ],
          contractPlate: [
            {required: true, message: '合同对应号牌编号不能为空', trigger: 'blur'}, {
              required: true,
              message: '合同对应号牌编号不能为空',
              trigger: 'change'
            }
          ],
          contractTypeId: [
            {required: true, message: '合同类型不能为空', trigger: 'blur'},
            {required: true, message: '合同类型不能为空', trigger: 'change'}
          ],
          contractStartDate: [
            {required: true, message: '合同号牌有效期起不能为空', trigger: 'blur', type: 'date'}
          ],
          contractEndDate: [
            {required: true, message: '合同号牌有效期止不能为空', trigger: 'blur', type: 'date'}
          ],
          carTypeMainId: [
            {required: true, message: '车型号不能为空', trigger: 'blur'},
            {required: true, message: '车型号不能为空', trigger: 'change'}
          ],
          fuelTypeId: [
            {required: true, message: '燃料种类不能为空', trigger: 'blur'},
            {required: true, message: '车型号不能为空', trigger: 'change'}
          ],
          engineNumber: [
            {required: true, message: '发动机号不能为空', trigger: 'blur'}
          ],
          operatingData: [
            {required: true, message: '营运日期不能为空', trigger: 'blur', type: 'date'}
          ]
        },
        isSave: false,
        baseData: {
          registArea: [],
          enterpriseState: []
        },
        areaRegionList: [], areaDistricList: [],
        economicNatureList: [],
        businessNatureList: [
          {
            'BUSINNATUREID': '39',
            'BUSINESSNATURE': '专营'
          }, {
            'BUSINNATUREID': '40',
            'BUSINESSNATURE': '兼营'
          }
        ],
        contractTypeList: [],
        companyBusinessNameList: [],
        contractPlateList: [],
        displacementList: [],
        carTypeaMainList: [],
        carTypeaViceList: [],
        fuelTypeList: [],
        fuelSmallTypeList: [],
        vehicleColorList: [],
        approvedTariffList: []
      }
    },
    methods: {
      checkDriverIdNum(rule, value, callback) {
        let that = this
        let initData = {}
        initData.driverIdNum = value;
        initData.type = 'car';
        checkDriverIdNum(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === '0') {
              let r_code = response.data.code;
              let r_msg = response.data.msg;
              let driverBirth = response.data.driverBirth;
              if (r_code == '0') {
                that.taxiCarForm.birthDate = '';
                callback(new Error(r_msg));
              } else {
                that.taxiCarForm.birthDate = driverBirth;
                callback()
              }
            } else {
              callback()
            }
          })
        })
      },
      checkTaxiCarNum(rule, value, callback, type) {
        //type 1 号牌 2车牌
        let that = this
        let initData = {}
        initData.carNum = value;
        initData.type = type;
        checkTaxiCarNum(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === '0') {
              let r_code = response.data.code;
              let r_msg = response.data.msg;
              let driverBirth = response.data.driverBirth;
              if (r_code == '0') {
                callback(new Error(r_msg));
              } else {
                callback()
              }
            } else {
              callback()
            }
          })
        })
      },
      handleSubmit(name) {
        this.$refs[name].validate((valid) => {
          var that = this
          if (valid) {
            that.taxiCarForm.birthDate = that.changeDate(that.taxiCarForm.birthDate)
            that.taxiCarForm.licenseStartDate = that.changeDate(that.taxiCarForm.licenseStartDate)
            that.taxiCarForm.licenseEndDate = that.changeDate(that.taxiCarForm.licenseEndDate)
            that.taxiCarForm.contractStartDate = that.changeDate(that.taxiCarForm.contractStartDate)
            that.taxiCarForm.contractEndDate = that.changeDate(that.taxiCarForm.contractEndDate)
            that.taxiCarForm.vouchDate = that.changeDate(that.taxiCarForm.vouchDate)
            that.taxiCarForm.operatingData = that.changeDate(that.taxiCarForm.operatingData)
            that.taxiCarForm.reopenDate = that.changeDate(that.taxiCarForm.reopenDate)
            that.taxiCarForm.remodelDate = that.changeDate(that.taxiCarForm.remodelDate)
            that.taxiCarForm.factoryDate = that.changeDate(that.taxiCarForm.factoryDate)
            that.taxiCarForm.vehicleUseExpireDate = that.changeDate(that.taxiCarForm.vehicleUseExpireDate)
            that.taxiCarForm.closedDate = that.changeDate(that.taxiCarForm.closedDate)
            let initData = that.taxiCarForm
            if (!that.isSave) {
              that.isSave = true
              saveTaxiCarInfo(initData).then(function (response) {
                responseUtil.dealResponse(that, response, () => {
                  if (response.code == 0) {
                    that.$emit('setProjectNo', response.data);
                    that.taxiCarForm.taxiArchiveId = response.data;
                    that.$Message.success('提交成功!');
                  } else {
                    that.isSave = false
                  }
                })
              })
            } else {
              that.$Message.error('请勿重复提交');
            }
          } else {
            that.$Message.error('表单验证失败!');
          }
        })
      },
      handleApprove(type) {
        let that = this
        let initData = {}
        let userInfo = getUser();
        initData.gid = this.taxiCarForm.taxiArchiveId;
        initData.sxlb = '2'
        initData.actionType = type
        initData.userCode = userInfo.code;
        approveBusinessData(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.$Message.success(response.data);
              if (type == 1) {
                closeThisView(that, '/taxiCarAdmin/taxiWycAccept_index')
              } else {
                closeThisView(that, '/taxiCarAdmin/taxiWycApprove_index')
              }
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      handleReset(name) {
        this.$refs[name].resetFields();
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
        initData.projectNo = this.projectNo
        queryTaxiCarDetailInfo(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.taxiCarForm = response.data;
              that.changeCarTypeMain(1, 1);
              that.changeCarTypeMain(2, 1);
              that.changeCarTypeMain(3, 1);
              that.initContractPlateList(that.taxiCarForm.companyCode);
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      handleCompanyListCreate: function (val) {
        if (val != undefined && val != '' && val != null) {
          var that = this
          let initData = {}
          initData.companyApprovedid = val
          searchCompanyInfoById(initData).then(function (response) {
            responseUtil.dealResponse(that, response, () => {
              if (response.code === "0") {
                let companyInfo = response.data;
                that.taxiCarForm.companyRecordId = companyInfo.COMPANYRECORDID;
                that.taxiCarForm.areaRegionId = companyInfo.AREAREGIONID;
                that.changeCarTypeMain(1, 0);
                that.taxiCarForm.areaDistrictId = companyInfo.AREADISTRICTID;
                that.taxiCarForm.companyCode = companyInfo.COMPANYCODE;
                that.taxiCarForm.companyAddress = companyInfo.COMPANYADDRESS;
                that.taxiCarForm.legalPersonName = companyInfo.LEGALPERSONNAME;
                that.taxiCarForm.legalPersonId = companyInfo.LEGALPERSONID;
                that.taxiCarForm.tel = companyInfo.CONTACTNUMBER;
                that.taxiCarForm.mobilePhone = companyInfo.MOBILEPHONE;
                that.taxiCarForm.birthDate = companyInfo.BIRTHDAY;
                that.taxiCarForm.taxiCount = companyInfo.COUNTCARNUMBER;
                that.taxiCarForm.taxiCertificateNum = companyInfo.LICENSENUMBER;
                that.taxiCarForm.licenseStartDate = companyInfo.LICENSEDATESTART;
                that.taxiCarForm.licenseEndDate = companyInfo.LICENSEDATEEND;
                that.taxiCarForm.businessArea = companyInfo.BUSINESSAREA;
                that.taxiCarForm.economyNnatureId = companyInfo.ECONNATUREID;
                //生成车辆营运证号
                that.createTaxiLicense(companyInfo.AREADISTRICTID);
                that.initContractPlateList(companyInfo.COMPANYCODE);
              } else {
                responseUtil.alertErrMsg(response.msg)
              }
            })
          })
        }
      },
      handleContractPlateCreate: function (val) {
        this.contractPlateList.push({CONTRACTNUM: val})
      },
      handleDisplacementCreate: function (val) {
        this.displacementList.push({DISPLACEMENT: val})
      },
      initTaxiArea() {
        var that = this
        let initData = {}
        queryTaxiArea(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.areaRegionList = response.data
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initContractType() {
        let that = this;
        let initData = {}
        queryContractType(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.contractTypeList = response.data
              that.taxiCarForm.contractType = response.data[0].CONTRACTTYPEID
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initCarTypeaMainList() {
        let that = this;
        let initData = {}
        queryCarTypeaMain(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.carTypeaMainList = response.data
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initFuelTypeList() {
        let that = this;
        let initData = {}
        queryFuelTypeList(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.fuelTypeList = response.data
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initVehicleColorList() {
        let that = this;
        let initData = {}
        queryVehicleColorList(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.vehicleColorList = response.data
              that.taxiCarForm.carBodyColorId = response.data[0].COLORID
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initEconomicNature() {
        let that = this;
        let initData = {}
        initData.type = 'gt';
        queryEconomicNatureList(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.economicNatureList = response.data
              that.taxiCarForm.economyNnatureId = '1';
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initApprovedTariffList() {
        let that = this;
        let initData = {}
        queryApprovedTariffList(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.approvedTariffList = response.data
              that.taxiCarForm.vouchFareId = '180';
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initCompanyBusinessNameList() {
        let that = this;
        let initData = {}
        searchCompanyListByName(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.companyBusinessNameList = response.data
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initContractPlateList(companyCode) {
        let that = this;
        let initData = {}
        initData.companyCode = companyCode;
        queryContractPlateList(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.contractPlateList = response.data
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      changeCarTypeMain(type, flag) {
        let selectClass_id = '';
        let that = this
        let initData = {}
        initData.type = type;
        if (type == 1) {
          selectClass_id = this.taxiCarForm.areaRegionId;
        } else if (type == 2) {
          selectClass_id = this.taxiCarForm.carTypeMainId;
        } else if (type == 3) {
          selectClass_id = this.taxiCarForm.fuelTypeId;
        }
        initData.selectClass_id = selectClass_id;
        querySecondaryLinkage(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              if (type == 1) {
                that.areaDistricList = response.data
                if (flag == 0) {
                  that.taxiCarForm.areaDistrictId = response.data[0].AREAREGIONID
                  that.createTaxiLicense(response.data[0].AREAREGIONID)
                }
              } else if (type == 2) {
                if (response.data != null) {
                  that.carTypeaViceList = response.data
                  if (flag == 0) {
                    that.taxiCarForm.carTypeViceId = response.data[0].CARTYPEVICEID
                  }
                } else {
                  that.carTypeaViceList = []
                  that.taxiCarForm.carTypeViceId = ''
                }
              } else if (type == 3) {
                if (response.data != null) {
                  that.fuelSmallTypeList = response.data
                  if (flag == 0) {
                    that.taxiCarForm.fuelSmallTypeId = response.data[0].FUELSMALLTYPEID
                  }
                } else {
                  that.fuelSmallTypeList = []
                  that.taxiCarForm.fuelSmallTypeId = ''
                }
              }
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      createTaxiLicense(areaId) {
        let that = this;
        let initData = {}
        initData.select_id = areaId;
        createTaxiLicense(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.taxiCarForm.taxiLicenseNum = response.data.licenseNum;
              that.taxiCarForm.businessArea = response.data.operateArea;
              if (that.taxiCarForm.companyBusinessName == null || that.taxiCarForm.companyBusinessName == undefined
                || that.taxiCarForm.companyBusinessName == '') {
                that.taxiCarForm.taxiCertificateNum = '';
              }
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      initExhaustVolumeList() {
        let that = this;
        let initData = {}
        queryExhaustVolumeList(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              that.displacementList = response.data;
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      changeDate(dateA) {
        if (dateA == '' || dateA == null || dateA == undefined) {
          return;
        } else {
          let dateee = new Date(dateA).toJSON();
          let date = new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '');
          return date;
        }
      },
      createLicense() {
        let that = this
        let companyBusinessName = that.taxiCarForm.companyBusinessName
        let areaDistrictId = that.taxiCarForm.areaDistrictId
        if (companyBusinessName == '' || areaDistrictId == null || areaDistrictId == undefined) {
          if (areaDistrictId == '' || areaDistrictId == null || areaDistrictId == undefined) {
            that.$Message.error('请选择辖区');
          } else {
            let that = this;
            let initData = {}
            initData.select_id = that.taxiCarForm.areaDistrictId;
            createLicense(initData).then(function (response) {
              responseUtil.dealResponse(that, response, () => {
                if (response.code === "0") {
                  that.taxiCarForm.taxiCertificateNum = response.data;
                } else {
                  responseUtil.alertErrMsg(response.msg)
                }
              })
            })
          }
        }
      }
    }
  }
</script>
