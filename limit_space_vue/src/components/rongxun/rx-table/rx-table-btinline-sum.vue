<template>
  <div class="tableBoxWrapWrap" ref="tableWrap" :style="c_showpage ? '' : `border-bottom: 1px solid #dcdee2;`">
    <div class="leftTreeLineBox" style="height: calc(100% - 50px);z-index: 9;"></div>
    <div class="leftTreeBox" :style="`height: ${isFull ? (tableHeight - 1) + 'px' : undefined}`">
      <slot name="leftTree"></slot>
    </div>
    <Table border row-key="id" :height="isFull ? tableHeight : undefined" :max-height="tableHeight"
      :no-filtered-data-text="noFilter" ref="table" stripe :columns="getColumnsData()" :data="tableDataList"
      @on-row-click="onRowClick" @on-selection-change="selectCheck" :load-data="expandRow" class="rx-table-flag" :show-summary="showSum" :summary-method="handleSummary">
      <template #label="params">
        <rxTableLabel :labelColor=params.row.labelColor :labelText=params.row.labelText></rxTableLabel>
      </template>
      <template #action="{ row, index }">
        <div class="tableFlagSlot">
<!--          <Dropdown :placement="tableDataList.length > 8 && (tableDataList.length - 3) < index ? 'top' : 'bottom'"-->
<!--            @on-click="clickOptionFn($event, row, index)" transfer>-->
<!--            <Button @click.stop="clickOptionFn($event, row, index)" class="dropdownBtn">-->
<!--              {{ optionList[0].label }}-->
<!--              <Icon style="font-size: 20px;" type="md-arrow-dropdown"></Icon>-->
<!--            </Button>-->
<!--            <template #list>-->
<!--              <DropdownMenu class="dropdownMenuWrap">-->
<!--                <DropdownItem class="mainButton" v-for="option in optionList.filter(i => i.name !== optionList[0].name)"-->
<!--                  :key="option.label" :name="option.name">-->
<!--                  {{ option.label }}-->
<!--                </DropdownItem>-->
<!--              </DropdownMenu>-->
<!--            </template>-->
<!--          </Dropdown>-->
            <Button class="actButton" @click="clickOptionFn($event, row, index, option.rowIndex)" v-for="option in optionList.filter(i => row.bjzt === i.isShowButton)" >{{ option.label }}</Button>
        </div>
      </template>
    </Table>

    <div class="rx-pagebody" v-if="pageShow">
      <Button type="primary" v-if="billAdmin == 'billAdmin' && batchAuditType" @click="batchReceipt()" size="large"
        class="rx-button" style="float:left;position:relative;top:50%;transform: translateY(20%);">批量收款</Button>
      <Button type="primary" v-if="billAdmin == 'dealAudit' && batchAuditType" @click="batchApprove()" size="large"
        class="rx-button" style="float:left;position:relative;top:50%;transform: translateY(20%);">批量审批</Button>
      <Button type="primary" v-if="billAdmin == 'payment' && batchAuditType" @click="batchPayment()" size="large"
        class="rx-button" style="float:left;position:relative;top:50%;transform: translateY(20%);">批量支付</Button>
      <div class="content_wrapper" :style="getstyle()" v-if="content">{{ content }}</div>
      <div class="rx-page" v-if="c_showpage">
        <div class="leftLineBox"></div>
        <Page :total="page.pageCount" :current.sync="page.currentPage" :page-size="page.pageSize" placement="top"
          show-total show-elevator show-sizer @on-change="onChange" @on-page-size-change="pageChange"
          :pageSizeOpts="pageSizeOpts" />
      </div>
    </div>
    <div class="clear-float"></div>
  </div>
</template>
<script>
import rxTableSelect from '@/components/rongxun/rx-table/rx-tableSelect'
import rxTableLabel from '@/components/rongxun/rx-table/rx-tableLabel'
import { authButtons, responseUtil } from '@/utils/rongxunUtil'
import {Button} from "view-ui-plus";
export default {
  name: 'rx-table',
  components: {
    Button,
    rxTableSelect,
    rxTableLabel
  },
  props: {
    c_showpage: {
      type: Boolean,
      default: true
    },
    c_index: {
      type: Boolean,
      default: true
    },
    isLabel: {
      type: Boolean,
      default: false
    },
    isFull: {
      type: Boolean,
      default: false
    },
    needParent: {
      type: Boolean,
      default: true
    },
    selectClazz: {

    },
    selectWraperClazz: {

    },
    tableData: {
      type: Array
    },
    columnsInfo: {
      type: Array
    },
    onChange: {
      type: Function
    },
    pageChange: {
      type: Function
    },
    page: {
      type: Object
    },
    onRowClick: {
      type: Function,
      default: () => { }
    },
    selectCheck: {
      type: Function,
      default: () => { }
    },
    type: {
      type: String,
      default: '1'
    },
    pageShow: {
      type: Boolean,
      default: true
    },
    expandCellClick: {
      type: Function,
      default: () => { }
    },
    typeAuthMap: {},
    typeAuthField: {},
    content: {
      type: String,
      default: ''
    },
    typeAuthFunction: {},
    inCard: {
      type: Boolean,
      default: false
    },
    inTabs: {
      type: Boolean,
      default: false
    },
    needCaclHeight: {
      type: Boolean,
      default: true
    },
    isTableTree: {
      type: Boolean,
      default: false
    },
    noFilter: {
      type: String,
      default: ''
    },
    billAdmin: {
      type: String,
      default: ''
    },
    batchAuditType: {
      type: Boolean,
      default: false
    },
    showSum: {
      type: Boolean,
      default: false
    },
    batchList: {
      type: Array
    },
    getChildren: {
      type: Function,
      default: (item, callback) => { }
    }
  },
  data() {
    return {
      additionalHeight: 0,
      tableDataList: [],
      tableHeight: 0,
      tableWidth: 0,
      heightLock: false,
      isLoading: false,
      height: 0,
      pageSizeOpts: [10, 30, 50],
      optionList: [],
      columnsInfoData: []
    }
  },
  provide() {
    return {
      table: this
    }
  },
  computed: {
    // add by wangjiaqi 2022/6/7 -- begin
    isCollapsed() {
      return this.$store.getters.collapsed
    }
    // add by wangjiaqi 2022/6/7 -- end
  },
  watch: {
    // add by wangjiaqi 2022/6/7
    columnsInfo(val) {
      this.columnsInfoData = !this.c_index ? val.filter(item => item.key !== 'orderNumber') : val
    },
    tableData(val) {
      this.tableDataList = val.map((item, index) => {
        item.orderNumber = parseInt(this.page.currentPage === 1 ? 0 : this.page.currentPage + '0') + (index + 1)
        return item
      })
    }
    // add by wangjiaqi 2022/6/7 -- end
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = this.getClientHeight() - (this.c_showpage ? (275 + this.additionalHeight) : (225 + this.additionalHeight))
      if (!window.onresize) {
        window.onresize = () => {
          this.tableHeight = this.getClientHeight() - (this.c_showpage ? (275 + this.additionalHeight) : (225 + this.additionalHeight))
        }
      }
    })
  },
  destroyed() {
    window.onresize = null;
  },
  methods: {
    getClientHeight() {
      let clientHeight = 0;
      if (document.body.clientHeight && document.documentElement.clientHeight) {
        clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
      }
      else {
        clientHeight = (document.body.clientHeight > document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
      }
      return clientHeight;
    },
    // add by wangjiaqi 2022/6/7
    clickOptionFn(e, row, index,rowIndex) {
      const nameStr = typeof e === 'string' ? e : this.optionList[rowIndex].name
      const findData = this.optionList.find(item => item.name === nameStr)
      const data = this.isTableTree ? row : { row, index }
      if (findData) {
        findData.click(data)
      }
    },
    // add by wangjiaqi 2022/6/7
    getColumnsData() {
      if (this.columnsInfoData.length > 0) {
        return this.columnsInfoData.map((column, index) => {
          if (index === 0) {
            if (this.isTableTree) {
              column.tree = true
            }
          }
          if (column.key === 'action') {
            if (column.buttons && column.buttons.length > 0) {
              column.slot = 'action'
              this.optionList = column.buttons
            }
          }
          return column
        })
      }
    },
    // 批量收款
    batchReceipt() {
      if (!this.batchList || this.batchList.length == 0) {
        responseUtil.alertMsg(this, '请选择数据')
        return
      }
      for (let i = 0; i < this.batchList.length; i++) {
        if (this.batchList[i].labelText.substring(0, 2) != '应收') {
          responseUtil.alertMsg(this, '选中的数据不是应收数据')
          return
        }
      }
      for (let i = 0; i < this.batchList.length - 1; i++) {
        for (let j = i + 1; j < this.batchList.length; j++) {
          if (this.batchList[i].pruserName != this.batchList[j].pruserName) {
            responseUtil.alertMsg(this, '选中的数据不是同一个人')
            return
          }
        }
      }
      this.$emit('batchList', this.batchList)
    },
    // 批量审批
    batchApprove() {
      if (!this.batchList || this.batchList.length == 0) {
        responseUtil.alertMsg(this, '请选择数据')
        return
      }
      this.$emit('batchApprove', this.batchList)
    },
    // 批量支付
    batchPayment() {
      if (!this.batchList || this.batchList.length == 0) {
        responseUtil.alertMsg(this, '请选择数据')
        return
      }
      this.$emit('batchPayment', this.batchList)
    },
    getstyle() {
      if (this.content.length > 40) {
        return {
          'padding': '10px 16px 10px 16px',
          'float': 'left',
          'width': '50%',
          'font-size': 'xx-small'
        }
      } else {
        return {
          'padding': '10px 16px 10px 16px',
          'float': 'left',
          'width': '50%',
          'font-size': 'small'
        }
      }
    },
    authButtons,
    expandRow(item, callback){
      this.getChildren(item,callback)
    },
    handleSummary ({ columns, data }) {
      const sums = {};
      columns.forEach((column, index) => {
        const key = column.key;
        if (index === 0) {
          sums[key] = {
            key,
            value: '总计'
          };
          return;
        }
        const values = data.map(item => Number(item[key]));
        if (values.every(value => !isNaN(value))) {
          const v = values.reduce((prev, curr) => {
            const value = Number(curr);
            if (!isNaN(value)) {
              return prev + curr;
            } else {
              return prev;
            }
          }, 0);
          sums[key] = {
            key,
            value: v + ''
          };
        } else {
          sums[key] = {
            key,
            value: ''
          };
        }
      });
      return sums;
    }
  }
}
</script>
<style lang="less">
@import "../common/rx-common";
@import "./rx-table.less";

.tableBoxWrapWrap {
  width: 100%;
  height: 100% !important;
  border-radius: 5px;
  position: relative;
  background-color: #fff;

  .leftTreeLineBox {
    position: absolute;
    left: 0;
    top: 0;
    background-color: #dcdee2;
    width: 1px;
  }

  .leftTreeBox {
    float: left;
    display: flex;
    border-top: 1px solid #dcdee2;
  }
}

.rx-table-flag {
  .tableFlagSlot {
    width: 100%;
    font-size: 20px;
  }

  .dropdownBtn {
    width: 100px;
    height: 30px;
    text-align: left;
    font-size: 14px !important;
    border-radius: 2px;
    box-sizing: border-box;
  }

  .dropdownBtn:hover {
    color: #FFFFFF;
    background: @button-hover;
    border-radius: 0 0 2px 2px !important;
  }
}

.mainButton {
  height: 32px;
  width: 100px !important;
  border-radius: 2px !important;
  border: none;
  margin-left: 0px;
  font-size: 14px !important;
  text-indent: 0.5em;
  padding: 7px 16px 7px 3px;
  cursor: pointer;
  -webkit-font-smoothing: antialiased;
  box-sizing: border-box;
}

.mainButton:hover {
  color: #FFFFFF;
  background: @button-hover;
}

.ivu-dropdown-menu {
  width: 100px;
  border-radius: 0 0 2px 2px !important;
  background: #fff;
  margin-top: 0px;
  overflow: hidden;
}

.ivu-select-dropdown {
  margin: 0;
  padding: 0;
  font-size: 13px;
  width: 100px;
  border-radius: 0 0 2px 2px !important;
}

.rx-tree-outline .ivu-icon-ios-arrow-forward:before {
  border: 0px solid @main-color;
  content: "\F11F";
  color: @main-color;
}

.zk-table__body-cell {
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  text-align: left;
  vertical-align: middle;
  word-break: break-all;
  overflow: visible !important;
}

.zk-table__body-row {
  height: 45px !important;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}

.zk-table__cell-inner {
  overflow: visible !important;
  padding: 6px 12px;
  text-align: left !important;
}

.zk-table--row-hover td {
  background-color: #E6E6E6;
}
</style>
<style lang="less" scoped>
.tableBoxWrapWrap {
  .rx-pagebody {
    .rx-page {

      :deep(.ivu-page-options),
      :deep(.ivu-page-options-sizer),
      :deep(.ivu-select-default),
      :deep(.ivu-select-selection) {
        height: 32px !important;
        min-height: 32px !important;
      }
    }
  }

  .tableFlagSlot {
    :deep(.ivu-select-selection) {
      min-height: 32px !important;
    }

    :deep(.ivu-btn) {
      position: relative;
      padding: 0 8px;
    }

    :deep(.ivu-icon) {
      position: absolute;
      right: 5px;
      top: 50%;
      transform: translateY(-50%);
    }
  }

  :deep(.ivu-dropdown-menu),
  .dropdownMenuWrap {
    width: 100px;
    box-sizing: border-box;
  }
}
  .actButton:hover{ background: #3c6cfe; color:#fff;}
</style>
