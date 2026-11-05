<template>
  <Select id="selectB" ref="selectB" v-model="vmodels" clearable :placeholder="placeholder" @on-change="selectOption"
    @on-clear="clearModels" size="large">
    <Input v-model="searchName" search placeholder="输入内容" @on-search="searchClick(searchName)" />
    <Tree :data="treeData" @on-select-change="treeSelectChange"
      :style="{ 'width': '50%', 'float': 'left', 'height': optionList.length > treeData.length ? optionList.length * 30 + 'px' : treeData.length * 30 + 'px' }">
    </Tree>
    <Option v-for="item in optionList" :value="item.value" :key="item.value"
      style="width:50%;display: inline-block;height: 30px">{{ item.title }}</Option>
  </Select>
</template>
<script>
export default {
  props: {
    vmodel: {
      type: String,
      default: ''
    },
    treeData: {},
    treeSelectChange: {
      type: Function
    },
    optionList: {
      type: Array,
      default: []
    },
    searchClick: {
      type: Function
    },
    selectChange: {
      type: Function
    },
    placeholder: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      searchName: '',
      vmodels: ''
    }
  },
  methods: {
    selectOption(value) {
      if (!value) {
        return
      }
      this.$refs['selectB'].visible = false
      value = value ? value.toString() : ""
      this.selectChange(value)
      this.$emit('update:vmodel', value)
    },
    clearModels() {
      this.selectChange('')
      this.$emit('update:vmodel', '')
    },
    clearAll() {
      this.$refs['selectB'].query = '';
      this.$refs['selectB'].focusIndex = -1;
      this.$refs['selectB'].unchangedQuery = true;
      this.$refs['selectB'].values = [];
      this.$refs['selectB'].filterQueryChange = false;
    }
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

#selectB .ivu-select-dropdown {
  width: 40% !important;
  min-height: 200px;
}
</style>
