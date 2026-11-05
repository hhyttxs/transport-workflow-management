<style lang="less">
@import "../../../components/rongxun/transport/rx-information/information.less";
</style>

<template>
  <div class="vehicle-form-page">
    <Form
      ref="formValidate"
      :model="formValidate"
      :rules="dynamicRuleValidate"
      :label-width="180"
    >
      <!-- 顶部基本信息 -->
      <Row>
        <Col span="24">
          <FormItem label="事项名称:">
            <Input
              v-model="formValidate.matterName"
              disabled
            />
          </FormItem>
        </Col>

        <Col span="24">
          <FormItem label="事项分类:">
            <Select
              v-model="formValidate.matterCategory"
              placeholder="请选择事项分类"
              @on-change="changeMatterCategory"
            >
              <Option
                v-for="item in matterCategoryList"
                :key="item.value"
                :value="item.value"
              >
                {{ item.label }}
              </Option>
            </Select>
          </FormItem>
        </Col>

        <Col span="24">
          <FormItem label="业务类型:" prop="businessType">
            <Select
              v-model="formValidate.businessType"
              placeholder="请选择业务类型"
              @on-change="changeBusinessType"
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

        <Col span="24">
          <FormItem label="经办人:" prop="handler">
            <Select
              v-model="formValidate.handler"
              clearable
              filterable
              placeholder="请选择经办人"
            >
              <Option
                v-for="item in handlerList"
                :key="item.value"
                :value="item.value"
              >
                {{ item.label }}
              </Option>
            </Select>
          </FormItem>
        </Col>
      </Row>

      <!-- 经营范围 -->
      <div class="scope-section">
        <div class="scope-title">经营范围:</div>

        <CheckboxGroup v-model="formValidate.businessScope">
          <div class="scope-item">
            <Checkbox label="普通货运">普通货运</Checkbox>
          </div>

          <div class="scope-item">
            <Checkbox label="大型物件运输">大型物件运输</Checkbox>
          </div>

          <div class="scope-item">
            <Checkbox label="集装箱运输">集装箱运输</Checkbox>
          </div>

          <div class="scope-item">
            <Checkbox label="冷藏保鲜运输">冷藏保鲜运输</Checkbox>
          </div>
        </CheckboxGroup>
      </div>

      <!-- 车辆信息 -->
      <Row>
        <Col span="12">
          <FormItem label="车辆来源:" prop="vehicleSource">
            <Select
              v-model="formValidate.vehicleSource"
              placeholder="请选择车辆来源"
            >
              <Option
                v-for="item in vehicleSourceList"
                :key="item.value"
                :value="item.value"
              >
                {{ item.label }}
              </Option>
            </Select>
          </FormItem>
        </Col>

        <Col span="12">
          <FormItem label="车辆识别代码:" prop="vin">
            <Input
              v-model.trim="formValidate.vin"
              maxlength="17"
              placeholder="输入车辆行驶证的车辆识别代码"
            />
          </FormItem>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <FormItem label="车辆类型:" prop="vehicleType">
            <div class="field-with-link">
              <Select
                v-model="formValidate.vehicleType"
                placeholder="请选择车辆类型"
              >
                <Option
                  v-for="item in vehicleTypeList"
                  :key="item.value"
                  :value="item.value"
                >
                  {{ item.label }}
                </Option>
              </Select>

              <a
                href="javascript:void(0)"
                class="add-link"
                @click="addVehicleType"
              >
                添加新车辆类型
              </a>
            </div>
          </FormItem>
        </Col>

        <Col span="12">
          <FormItem label="行驶证号:" prop="plateNumber">
            <div class="plate-number-box">
              {{'辽B-'}}

              <Input
                v-model.trim="formValidate.plateNumber"
                placeholder="输入车辆行驶证的行驶证号"
              />
            </div>
          </FormItem>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <FormItem label="总质量:" prop="totalMass">
            <Input
              v-model.trim="formValidate.totalMass"
              placeholder="请输入总质量"
            />
          </FormItem>
        </Col>

        <Col span="12">
          <FormItem label="核定载质量:" prop="approvedLoad">
            <Input
              v-model.trim="formValidate.approvedLoad"
              placeholder="请输入核定载质量"
            />
          </FormItem>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <FormItem label="车辆厂牌:" prop="brand">
            <div class="field-with-link">
              <Select
                v-model="formValidate.brand"
                filterable
                placeholder="请选择车辆厂牌"
              >
                <Option
                  v-for="item in brandList"
                  :key="item.value"
                  :value="item.value"
                >
                  {{ item.label }}
                </Option>
              </Select>

              <a
                href="javascript:void(0)"
                class="add-link"
                @click="addVehicleBrand"
              >
                添加新厂牌
              </a>
            </div>
          </FormItem>
        </Col>

        <Col span="12">
          <FormItem label="车辆型号:" prop="vehicleModel">
            <div class="field-with-link">
              <Select
                v-model="formValidate.vehicleModel"
                filterable
                placeholder="选择机动车登记证书的车辆型号"
              >
                <Option
                  v-for="item in vehicleModelList"
                  :key="item.value"
                  :value="item.value"
                >
                  {{ item.label }}
                </Option>
              </Select>

              <a
                href="javascript:void(0)"
                class="add-link"
                @click="addVehicleModel"
              >
                添加新厂牌型号
              </a>
            </div>
          </FormItem>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <FormItem label="注册日期:" prop="registerDate">
            <Date-picker
              type="date"
              v-model="formValidate.registerDate"
              format="yyyyMMdd"
              clearable
              style="width: 100%"
              placeholder="输入行驶证的注册日期，格式为8位数字如20230516"
            />
          </FormItem>
        </Col>

        <Col span="12">
          <FormItem label="报废日期:" prop="scrapDate">
            <Date-picker
              type="date"
              v-model="formValidate.scrapDate"
              format="yyyyMMdd"
              clearable
              style="width: 100%"
              placeholder="输入行驶证上的强制报废日期止日，格式为8位数字如20230516"
            />
          </FormItem>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <FormItem label="车辆长度:" prop="vehicleLength">
            <Input
              v-model.trim="formValidate.vehicleLength"
              placeholder="请输入车辆长度"
            />
          </FormItem>
        </Col>

        <Col span="12">
          <FormItem label="车辆宽度:" prop="vehicleWidth">
            <Input
              v-model.trim="formValidate.vehicleWidth"
              placeholder="请输入车辆宽度"
            />
          </FormItem>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <FormItem label="车辆高度:" prop="vehicleHeight">
            <Input
              v-model.trim="formValidate.vehicleHeight"
              placeholder="请输入车辆高度"
            />
          </FormItem>
        </Col>

        <Col span="12">
          <FormItem label="燃料类型:" prop="fuelType">
            <Select
              v-model="formValidate.fuelType"
              placeholder="请选择燃料类型"
            >
              <Option
                v-for="item in fuelTypeList"
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
        <Col span="12">
          <FormItem label="技术等级:" prop="technicalLevel">
            <Select
              v-model="formValidate.technicalLevel"
              placeholder="请选择技术等级"
            >
              <Option
                v-for="item in technicalLevelList"
                :key="item.value"
                :value="item.value"
              >
                {{ item.label }}
              </Option>
            </Select>
          </FormItem>
        </Col>

        <Col span="12">
          <FormItem label="发动机号:" prop="engineNumber">
            <Input
              v-model.trim="formValidate.engineNumber"
              placeholder="请输入行驶证上的发动机号"
            />
          </FormItem>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <FormItem label="轴数:" prop="axleCount">
            <Input
              v-model.trim="formValidate.axleCount"
              placeholder="请输入机动车登记证书的轴数"
            />
          </FormItem>
        </Col>

        <Col span="12">
          <FormItem label="出厂日期:" prop="productionDate">
            <Date-picker
              type="date"
              v-model="formValidate.productionDate"
              format="yyyyMMdd"
              clearable
              style="width: 100%"
              placeholder="请输入机动车登记证书的出厂日期"
            />
          </FormItem>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <FormItem label="车辆购买日期:" prop="purchaseDate">
            <Date-picker
                type="date"
                v-model="formValidate.purchaseDate"
                format="yyyyMMdd"
                clearable
                style="width: 100%"
                placeholder="请输入车辆购买日期"
              />
          </FormItem>
        </Col>

        <Col span="12">
          <FormItem label="发动机功率:" prop="enginePower">
            <Input
              v-model.trim="formValidate.enginePower"
              placeholder="请输入机动车登记证书的发动机功率（千瓦）"
            />
          </FormItem>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <FormItem label="车牌颜色:" prop="plateColor">
            <Select
              v-model="formValidate.plateColor"
              placeholder="请选择车牌颜色"
            >
              <Option
                v-for="item in plateColorList"
                :key="item.value"
                :value="item.value"
              >
                {{ item.label }}
              </Option>
            </Select>
          </FormItem>
        </Col>

        <Col span="12">
          <FormItem label="是否在省内检车:" prop="inspectionInProvince">
            <Select
              v-model="formValidate.inspectionInProvince"
              placeholder="请选择是否在省内检车"
            >
              <Option value="1">是</Option>
              <Option value="0">否</Option>
            </Select>
          </FormItem>
        </Col>
      </Row>

      <Row>
        <Col span="12">
          <FormItem label="排放标准:" prop="emissionStandard">
            <Select
              v-model="formValidate.emissionStandard"
              placeholder="请选择排放标准"
            >
              <Option
                v-for="item in emissionStandardList"
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
        <Col span="12">
          <FormItem label="最后转籍日期:" prop="lastTransferDate">
            <Input
              v-model.trim="formValidate.lastTransferDate"
              maxlength="8"
              placeholder="最后转籍日期"
            />
          </FormItem>
        </Col>
      </Row>

      <!-- 从业人员 -->
      <div class="staff-row">
        <Button @click="addEmployee">
          添加从业人员
        </Button>

        <div class="staff-tip">
          请确认该车从业人员信息
        </div>
      </div>

      <!-- 提交按钮，需要时可以保留 -->
      <div class="submit-row">
        <Button type="primary" @click="saveOrUpdateVehicle">提交</Button>
        <Button style="margin-left: 10px" @click="saveDraft">暂存</Button>
      </div>
    </Form>
  </div>
</template>

<script>
import { getUser } from "@/utils/auth";
import {saveOrUpdateClb, getClbInfoByGid} from "@/api/hy/clb";


export default {
  name: "gztx_gzrwfb_detail",
  components: {},
  props: {},
  // 在 bmck.vue 的 mounted 中添加
  mounted() {
    console.log(this.$route.query.gid)
    this.formValidate.gid = this.$route.query.gid
    this.initVehicleData();
  },
  data() {
    return {
      gid: "",
      draftLoading: false,
      submitLoading: false,
      detailLoading: false,
      formValidate: {
        gid:'',
        matterName: '道路货运经营许可 - 道路普通货运车辆道路运输证核发、换发、补发、审验、注销',
        matterCategory: 'transportCertificate',
        businessType: 'firstApplication',
        handler: '',

        businessScope: [],

        vehicleSource: '',
        vin: '',
        vehicleType: '',
        platePrefix: '辽B',
        plateNumber: '',

        totalMass: '0',
        approvedLoad: '0',

        brand: '',
        vehicleModel: '',

        registerDate: '',
        scrapDate: '',

        vehicleLength: '0',
        vehicleWidth: '0',
        vehicleHeight: '0',

        fuelType: '',
        technicalLevel: '',
        engineNumber: '',

        axleCount: '',
        productionDate: '',
        purchaseDate: '',
        enginePower: '',

        plateColor: '',
        inspectionInProvince: '',
        emissionStandard: '',
        lastTransferDate: ''
      },

      matterCategoryList: [
        {
          value: 'transportCertificate',
          label: '运输证核发'
        }
      ],

      businessTypeList: [
        {
          value: 'firstApplication',
          label: '首次申办运输证'
        }
      ],

      handlerList: [
        {
          value: 'currentUser',
          label: '当前经办人'
        }
      ],

      vehicleSourceList: [
        {
          value: 'new',
          label: '新购车辆'
        },
        {
          value: 'transfer',
          label: '转入车辆'
        },
        {
          value: 'existing',
          label: '已有车辆'
        }
      ],

      vehicleTypeList: [
        {
          value: 'heavyTruck',
          label: '重型普通货车'
        },
        {
          value: 'mediumTruck',
          label: '中型普通货车'
        },
        {
          value: 'lightTruck',
          label: '轻型普通货车'
        }
      ],

      brandList: [
        {
          value: 'faw',
          label: '一汽解放'
        },
        {
          value: 'dongfeng',
          label: '东风'
        },
        {
          value: 'sinotruk',
          label: '中国重汽'
        }
      ],

      vehicleModelList: [
        {
          value: 'model1',
          label: '车辆型号一'
        },
        {
          value: 'model2',
          label: '车辆型号二'
        }
      ],

      platePrefixList: [
        '辽A',
        '辽B',
        '辽C',
        '辽D',
        '辽E',
        '辽F',
        '辽G',
        '辽H',
        '辽J',
        '辽K',
        '辽L',
        '辽M',
        '辽N',
        '辽P'
      ],

      fuelTypeList: [
        {
          value: 'diesel',
          label: '柴油'
        },
        {
          value: 'gasoline',
          label: '汽油'
        },
        {
          value: 'electric',
          label: '纯电动'
        },
        {
          value: 'hybrid',
          label: '混合动力'
        },
        {
          value: 'naturalGas',
          label: '天然气'
        }
      ],

      technicalLevelList: [
        {
          value: '1',
          label: '一级'
        },
        {
          value: '2',
          label: '二级'
        },
        {
          value: '3',
          label: '三级'
        }
      ],

      plateColorList: [
        {
          value: 'yellow',
          label: '黄色'
        },
        {
          value: 'blue',
          label: '蓝色'
        },
        {
          value: 'green',
          label: '绿色'
        },
        {
          value: 'white',
          label: '白色'
        },
        {
          value: 'black',
          label: '黑色'
        }
      ],

      emissionStandardList: [
        {
          value: '国三',
          label: '国三'
        },
        {
          value: '国四',
          label: '国四'
        },
        {
          value: '国五',
          label: '国五'
        },
        {
          value: '国六',
          label: '国六'
        }
      ],

      ruleValidate: {
        businessType: [
          {
            required: true,
            message: '请选择业务类型',
            trigger: 'change'
          }
        ],

        handler: [
          {
            required: true,
            message: '请选择经办人',
            trigger: 'change'
          }
        ],

        vehicleSource: [
          {
            required: true,
            message: '请选择车辆来源',
            trigger: 'change'
          }
        ],

        vin: [
          {
            required: true,
            message: '请输入车辆识别代码',
            trigger: 'blur'
          },
          {
            len: 17,
            message: '车辆识别代码应为17位',
            trigger: 'blur'
          }
        ],

        vehicleType: [
          {
            required: true,
            message: '请选择车辆类型',
            trigger: 'change'
          }
        ],

        plateNumber: [
          {
            required: true,
            message: '请输入行驶证号',
            trigger: 'blur'
          }
        ],

        totalMass: [
          {
            required: true,
            message: '请输入总质量',
            trigger: 'blur'
          }
        ],

        approvedLoad: [
          {
            required: true,
            message: '请输入核定载质量',
            trigger: 'blur'
          }
        ],

        brand: [
          {
            required: true,
            message: '请选择车辆厂牌',
            trigger: 'change'
          }
        ],

        vehicleModel: [
          {
            required: true,
            message: '请选择车辆型号',
            trigger: 'change'
          }
        ],

        registerDate: [
          {
            required: true,
            message: '请输入注册日期',
            trigger: 'blur',
            type:'date'
          }
        ],

        scrapDate: [
          {
            required: true,
            message: '请输入报废日期',
            trigger: 'blur',
            type:'date'
          }
        ],

        vehicleLength: [
          {
            required: true,
            message: '请输入车辆长度',
            trigger: 'blur'
          }
        ],

        vehicleWidth: [
          {
            required: true,
            message: '请输入车辆宽度',
            trigger: 'blur'
          }
        ],

        vehicleHeight: [
          {
            required: true,
            message: '请输入车辆高度',
            trigger: 'blur'
          }
        ],

        fuelType: [
          {
            required: true,
            message: '请选择燃料类型',
            trigger: 'change'
          }
        ],

        technicalLevel: [
          {
            required: true,
            message: '请选择技术等级',
            trigger: 'change'
          }
        ],

        engineNumber: [
          {
            required: true,
            message: '请输入发动机号',
            trigger: 'blur'
          }
        ],

        axleCount: [
          {
            required: true,
            message: '请输入轴数',
            trigger: 'blur'
          }
        ],

        productionDate: [
          {
            required: true,
            message: '请输入出厂日期',
            trigger: 'blur',
            type:'date'
          }
        ],

        purchaseDate: [
          {
            required: true,
            message: '请输入车辆购买日期',
            trigger: 'blur',
            type:'date'
          }
        ],

        enginePower: [
          {
            required: true,
            message: '请输入发动机功率',
            trigger: 'blur'
          }
        ],

        plateColor: [
          {
            required: true,
            message: '请选择车牌颜色',
            trigger: 'change'
          }
        ],

        inspectionInProvince: [
          {
            required: true,
            message: '请选择是否在省内检车',
            trigger: 'change'
          }
        ],

        emissionStandard: [
          {
            required: true,
            message: '请选择排放标准',
            trigger: 'change'
          }
        ]
      }
    }
  },
  computed: {
    dynamicRuleValidate() {
      return this.ruleValidate;
    }
  },
  methods: {
    /**
     * 初始化页面。
     *
     * 普通进入页面时，没有 gid，表示新增申报。
     * 从暂存列表进入时，有 gid，表示继续申报。
     */
    initVehicleData() {
      this.gid =
        this.$route &&
        this.$route.query &&
        this.$route.query.gid
          ? this.$route.query.gid
          : "";

      console.log("当前暂存记录 gid：", this.gid);

      if (this.gid) {
        this.loadDraftDetail();
      }
    },

    /**
     * 兼容项目中不同的 request 返回结构。
     */
    getResponseBody(response) {
      if (response && response.code !== undefined) {
        return response;
      }

      if (response && response.data) {
        return response.data;
      }

      return {};
    },

    /**
     * 根据 gid 查询暂存记录并回显。
     */
    loadDraftDetail() {
      const user = getUser();

      const requestData = {
        gid: this.gid,

        account:
          user && user.code
            ? user.code
            : "",

        currentPage: 1,
        numberPage: 10,
        status: "0"
      };

      console.log("查询车辆暂存详情参数：", requestData);

      this.detailLoading = true;

      getClbInfoByGid(requestData).then(response => {
          if (response.code != '0'){
            this.$Message.error("查询车辆暂存详情失败");
            return
          }
        this.formValidate.handler = response.data.jbr;
        this.formValidate.businessScope = response.data.jyfw.split(",");
        this.formValidate.vehicleSource = response.data.clly;
        this.formValidate.vin = response.data.clsbdm;
        this.formValidate.vehicleType = response.data.cllx;
        this.formValidate.plateNumber = response.data.xszh;
        this.formValidate.totalMass = response.data.zzl;
        this.formValidate.approvedLoad = response.data.hdzzl;
        this.formValidate.brand = response.data.clcp;
        this.formValidate.vehicleModel = response.data.clxh;
        this.formValidate.vehicleLength = response.data.clcd;
        this.formValidate.vehicleWidth = response.data.clkd;
        this.formValidate.vehicleHeight = response.data.clgd;
        this.formValidate.fuelType = response.data.rllx;
        this.formValidate.technicalLevel = response.data.jsdj;
        this.formValidate.engineNumber = response.data.fdjh;
        this.formValidate.axleCount = response.data.zs;
        this.formValidate.enginePower = response.data.power;
        this.formValidate.plateColor = response.data.cpys;
        this.formValidate.emissionStandard = response.data.pfbz;
        this.formValidate.registerDate = this.parseDateValue(response.data.zcrq);
        this.formValidate.scrapDate = this.parseDateValue(response.data.bfrq);
        this.formValidate.productionDate = this.parseDateValue(response.data.ccrq);
        this.formValidate.purchaseDate = this.parseDateValue(response.data.clgmrq);
        }).catch(error => {
          console.error("查询车辆暂存详情失败：", error);
          this.$Message.error("查询车辆暂存详情失败");
        }).then(() => {
          this.detailLoading = false;
        });
    },

    /**
     * 将数据库返回的数据回显到表单。
     */
    fillDraftForm(draftData) {
      const dateFields = [
        "registerDate",
        "scrapDate",
        "productionDate",
        "purchaseDate"
      ];

      Object.keys(this.formValidate).forEach(key => {
        if (
          draftData[key] === undefined ||
          draftData[key] === null
        ) {
          return;
        }

        const value = draftData[key];

        // 经营范围在数据库中可能保存为逗号分隔字符串
        if (key === "businessScope") {
          if (Array.isArray(value)) {
            this.formValidate.businessScope = value;
          } else if (value) {
            this.formValidate.businessScope = String(value)
              .split(/[,，]/)
              .map(item => item.trim())
              .filter(item => item);
          } else {
            this.formValidate.businessScope = [];
          }

          return;
        }

        // DatePicker 的 v-model 需要 Date 对象
        if (dateFields.indexOf(key) !== -1) {
          this.formValidate[key] =
            this.parseDateValue(value);

          return;
        }

        this.formValidate[key] = value;
      });

      // 部分 Select 的值需要使用字符串
      if (
        draftData.inspectionInProvince !== undefined &&
        draftData.inspectionInProvince !== null
      ) {
        this.formValidate.inspectionInProvince =
          String(draftData.inspectionInProvince);
      }

      this.$nextTick(() => {
        if (
          this.$refs.formValidate &&
          this.$refs.formValidate.clearValidate
        ) {
          this.$refs.formValidate.clearValidate();
        }
      });

      console.log("暂存数据已回显：", this.formValidate);
    },

    /**
     * 将数据库日期转换成 DatePicker 可以识别的 Date。
     */
    parseDateValue(value) {
      if (!value) {
        return "";
      }

      if (value instanceof Date) {
        return value;
      }

      const text = String(value).trim();

      // 处理 yyyyMMdd
      const compactDate = text.match(
        /^(\d{4})(\d{2})(\d{2})$/
      );

      if (compactDate) {
        return new Date(
          Number(compactDate[1]),
          Number(compactDate[2]) - 1,
          Number(compactDate[3])
        );
      }

      // 处理 yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
      const normalDate = text.match(
        /^(\d{4})[-/](\d{1,2})[-/](\d{1,2})/
      );

      if (normalDate) {
        return new Date(
          Number(normalDate[1]),
          Number(normalDate[2]) - 1,
          Number(normalDate[3])
        );
      }

      const date = new Date(value);

      if (isNaN(date.getTime())) {
        return "";
      }

      return date;
    },

    /**
     * 将 DatePicker 的值转换成 yyyyMMdd。
     */
    formatDateValue(value) {
      if (!value) {
        return "";
      }

      if (
        typeof value === "string" &&
        /^\d{8}$/.test(value)
      ) {
        return value;
      }

      const date =
        value instanceof Date
          ? value
          : this.parseDateValue(value);

      if (
        !(date instanceof Date) ||
        isNaN(date.getTime())
      ) {
        return value;
      }

      const year = date.getFullYear();

      const month = String(
        date.getMonth() + 1
      ).padStart(2, "0");

      const day = String(
        date.getDate()
      ).padStart(2, "0");

      return `${year}${month}${day}`;
    },

    /**
     * 组装保存参数。
     *
     * status = 0：暂存
     * status = 1：正式提交
     */
    buildSubmitData(status) {
      const user = getUser();

      return {
        ...this.formValidate,

        gid: this.gid || "",

        account:
          user && user.code
            ? user.code
            : "",

        status: status,

        businessScope: Array.isArray(
          this.formValidate.businessScope
        )
          ? this.formValidate.businessScope.join(",")
          : this.formValidate.businessScope,

        registerDate: this.formatDateValue(
          this.formValidate.registerDate
        ),

        scrapDate: this.formatDateValue(
          this.formValidate.scrapDate
        ),

        productionDate: this.formatDateValue(
          this.formValidate.productionDate
        ),

        purchaseDate: this.formatDateValue(
          this.formValidate.purchaseDate
        )
      };
    },
    saveDraft() {
      this.saveVehicleData("0");
    },

    saveOrUpdateVehicle() {
      this.$refs.formValidate.validate(valid => {
        if (!valid) {
          this.$Message.warning("请完善必填信息");
          return;
        }

        this.saveVehicleData("1");
      });
    },

    /**
     * 调用保存接口。
     */
    saveVehicleData(status) {
      const isDraft = status === "0";

      const submitData =
        this.buildSubmitData(status);

      console.log(
        isDraft
          ? "车辆暂存参数："
          : "车辆正式提交参数：",
        submitData
      );

      if (isDraft) {
        this.draftLoading = true;
      } else {
        this.submitLoading = true;
      }

      saveOrUpdateClb(submitData)
        .then(response => {
          console.log("车辆保存接口返回：", response);

          const result = this.getResponseBody(response);

          if (String(result.code) !== "0") {
            this.$Message.error(
              result.msg ||
              (isDraft ? "暂存失败" : "提交失败")
            );

            return;
          }

          let savedGid = "";

          if (
            typeof result.data === "string" ||
            typeof result.data === "number"
          ) {
            savedGid = result.data;
          } else if (
            result.data &&
            result.data.gid
          ) {
            savedGid = result.data.gid;
          }

          if (savedGid) {
            this.gid = String(savedGid);

            if (
              !this.$route.query.gid ||
              String(this.$route.query.gid) !==
              String(this.gid)
            ) {
              this.$router.replace({
                path: this.$route.path,
                query: {
                  ...this.$route.query,
                  gid: this.gid
                }
              });
            }
          }

          this.$Message.success(
            isDraft ? "暂存成功" : "提交成功"
          );
        })
        .catch(error => {
          console.error(
            isDraft
              ? "车辆暂存失败："
              : "车辆提交失败：",
            error
          );

          this.$Message.error(
            isDraft ? "暂存失败" : "提交失败"
          );
        })
        .then(() => {
          this.draftLoading = false;
          this.submitLoading = false;
        });
    },

    changeMatterCategory(value) {
      console.log("事项分类：", value);
    },

    changeBusinessType(value) {
      console.log("业务类型：", value);
    },

    addVehicleType() {
      this.$Message.info("添加新车辆类型");
    },

    addVehicleBrand() {
      this.$Message.info("添加新厂牌");
    },

    addVehicleModel() {
      this.$Message.info("添加新厂牌型号");
    },

    addEmployee() {
      this.$Message.info("添加从业人员");
    }
  }
}
</script>


