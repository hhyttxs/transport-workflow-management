<template>
  <!--<Select ref="selectA" v-if="!vmultiple" v-model="inner_select_model" clearable :filterable="filterable" :remote="filterable" :remote-method="queryChange" :placeholder="title" :size="vsize" :disabled="disabled" @on-change="onChange" >-->
  <Select class="rxTreeSelectBox" not-found-text="" id="selectA" ref="selectA" v-if="!vmultiple" v-model="vmodel"
    clearable :filterable="filterable" :remote="filterable" :remote-method="queryChange" :placeholder="title"
    :size="vsize" :disabled="disabled" @on-change="onChange">
    <Option :value="item[selectName]" :key="item[selectName]" :label="item[selectShowName]" v-for="item in optData"
      v-show="showcheck && filterable">
      <span>{{ item[selectShowName] }}</span>
    </Option>
    <Option :value="vmodel" :key="vmodelName" style="display: none" v-if="!filterable">
      {{ vmodelName }}
    </Option>
    <rxTree :data="data" :click="selectData" v-show="!showcheck"></rxTree>
  </Select>
  <Select class="rxTreeSelectBox" not-found-text="" ref="selectA" v-else v-model="modelIdList" :multiple="true"
    clearable :size="vsize" :placeholder="title" size="large" :disabled="disabled" @on-change="onChange">
    <Option :value="item.department_id" :key="item.department_id" v-for="item in modelList" style="display: none">
      {{ item.departmentName }}
    </Option>
    <rxTree :data="data" :click="selectData"></rxTree>
  </Select>
</template>
<script>
import rxTree from "./rx-tree"
export default {
  components: {
    rxTree
  },
  data() {
    return {
      modelIdList: this.vmodelIdList,
      modelList: this.vmodelList,
      optData: [],
      showcheck: false,
      inner_select_model: ""
    }
  },
  props: {
    data: {},
    disabled: {
      type: Boolean,
      default: false
    },
    vmodel: {
      type: String,
      default: ""
    },
    vmodelLevel: {
      type: String,
      default: ""
    },
    vmodelName: {
      type: String,
      default: ""
    },
    title: {
      type: String,
      default: ""
    },
    selectName: {
      type: String,
      default: ""
    },
    selectShowName: {
      type: String,
      default: ""
    },
    onChange: {
      type: Function,
      default: () => { }
    },
    vmultiple: {
      type: Boolean,
      default: false
    },
    vmodelList: {
      type: Array,
      default: () => []
    },
    vmodelIdList: {
      type: Array,
      default: () => []
    },
    vsize: {
      type: String,
      default: "large"
    },
    returnLevel: {
      type: Number,
      default: 6
    },
    filterable: {
      type: Boolean,
      default: false
    },
  },
  mounted() {
    var that = this

    console.log("--------mounted---------")
    console.log(this.vmodel)
    console.log(this.inner_select_model)
    this.inner_select_model = this.vmodel
    this.$refs["selectA"].updateFocusIndex = function () {
      if (this.filterable) {
        if (!this.query) that.showcheck = false
        this.focusIndex = this.flatOptions.findIndex((opt) => {
          if (!opt || !opt.componentOptions) return false;
          return opt.componentOptions.propsData.value === this.publicValue;
        });
      }
    }
  },
  methods: {
    flatten: function (array) {
      var that = this
      return array.reduce(function (list, v) {
        if (v[that.selectName]) {
          v[that.selectName] = v[that.selectName].toString()
        }
        return list.concat(Array.isArray(v.children) ? that.flatten(v.children) : v);
      }, []);
    },
    queryChange: function (query) {
      if (this.filterable) {
        if (query) this.showcheck = true
      }
    },
    selectData: function (selectArray, item) {
      if (!this.vmultiple) {
        if ((item[this.selectName] && item.level && parseInt(item.level) < this.returnLevel) || (item[this.selectName] && !item.level)) {
          this.$refs['selectA'].visible = false
          this.$emit('update:vmodelName', item[this.selectShowName] ? item[this.selectShowName] : '')
          this.$emit('update:vmodelLevel', item[this.selectShowName] ? item.level : '')
          this.inner_select_model = item[this.selectName] ? item[this.selectName].toString() : ''
          this.$emit('update:vmodel', item[this.selectName] ? item[this.selectName].toString() : '')
          if (this.onChange) {
            this.onChange(item[this.selectName])
          }
        }
      } else {
        if (this.modelIdList.indexOf(item[this.selectName]) < 0) {
          this.modelIdList.push(item[this.selectName])
          let map = {
            department_id: item[this.selectName],
            departmentName: item[this.selectShowName]
          }
          this.modelList.push(map)
          this.$emit('update:vmodelIdList', this.modelIdList)
          this.$emit('update:vmodelList', this.modelList)
        }
        if (this.onChange) {
          this.onChange(this.modelList)
        }
      }

    }
  },
  watch: {
    modelIdList: { //监听的对象
      deep: true, //深度监听设置为 true
      handler: function (newV, oldV) {
        this.$emit('update:vmodelIdList', newV)
      }
    },
    modelList: { //监听的对象
      deep: true, //深度监听设置为 true
      handler: function (newV, oldV) {
        this.$emit('update:vmodelList', newV)
      }
    },
    vmodel: { //监听的对象
      handler: function (newV, oldV) {
        this.$emit('update:vmodel', newV ? newV.toString() : '')
      }
    },
    vmodelName: { //监听的对象
      handler: function (newV, oldV) {
        console.log("--------watch  vmodelName---------")
        console.log(this.vmodel)
        console.log(this.inner_select_model)
        this.$emit('update:vmodelName', newV)
      }
    },
    data: { //监听的对象
      handler: function (newV, oldV) {
        if (this.filterable) {
          this.optData = this.flatten(newV)
        }
      }
    },
    inner_select_model: { //监听的对象
      handler: function (newV, oldV) {
        var newV_str = newV ? newV.toString() : ''
        this.$emit('update:vmodel', newV_str)
      }
    },
  }
}
</script>
<style lang="less">
@import "../common/rx-common";

.rx-tree-outline .ivu-icon-ios-arrow-forward:before {
  //content: "\f330";
  //border:1px solid @main-color;
  border: 0px solid @main-color;
  content: "\F11F";
  color: @main-color;
}

#selectA .ivu-select-dropdown {
  width: 20% !important;
  min-height: 200px;
}

.ivu-tree-empty {
  padding: 7px 16px 8px;
  font-size: 14px !important;
}
</style>
<style lang="less" scoped>
.rxTreeSelectBox {
  :deep(.ivu-tag) {
    height: 28px !important;
    line-height: 28px !important;
    font-size: 14px !important;
  }

  :deep(.ivu-icon-ios-close) {
    margin-top: 6px;
  }

  :deep(.ivu-select-selection) {
    min-height: 34px !important;
    height: auto !important;
    box-sizing: border-box;
  }

  :deep(.ivu-select-placeholder) {
    height: 34px !important;
    line-height: 34px !important;
    box-sizing: border-box;
  }
}
</style>
