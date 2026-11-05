<template>
  <rxSelectTree ref="rxSelectTree" :vmodel.sync="vmodels" :treeData="treeData" :treeSelectChange="treeSelectChange" :optionList="optionList" :searchClick="searchClick" :selectChange="selectChange" placeholder="人员"></rxSelectTree>
</template>
<script>
  import rxSelectTree from '../rx-select/rx-selectTree'
  import {queryDepartmentTreeList, queryStaffList} from '../../../getData/getData'
  import {getUserId, responseUtil} from '../../../libs/rongxunUtil'

  export default {
  components: {
    rxSelectTree
  },
  props: {
    vmodel: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      treeData: [],
      department_id: '',
      optionList: [],
      searchName: '',
      vmodels: ''
    }
  },
  mounted: function () {
      // // 获取部门树
      this.$nextTick(function () {
          this.initTreeData()
      })
  },
  methods: {
    // 加载树
    initTreeData: function () {
      var that = this
      let initData = {}
      initData.user_id = getUserId()
      queryDepartmentTreeList(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          // 默认展示一级部门
          for (var i = 0; i < response.data.data.departmentList.length; i++) {
            response.data.data.departmentList[i].expand = true
          }
          that.treeData = response.data.data.departmentList
        })
      })
    },
    // 获取员工数据
    initStaffListData: function () {
      var that = this
      let initData = {}
      initData.user_id = getUserId()
      initData.department_id = this.department_id
      initData.searchName = this.searchName
      queryStaffList(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          that.optionList = response.data.data
        })
      })
    },
    // 选择部门树
    treeSelectChange (selectArray, item) {
      this.department_id = item.id
      this.initStaffListData()
    },
    // 搜索
    searchClick (searchName) {
      this.searchName = searchName
      this.department_id = ''
      this.initStaffListData()
    },
    // 选择时触发
    selectChange (value) {
      this.$emit('update:vmodel', value?value.toString():"")
    },
    //修改组件的值
    setName(name){
      if(!this.vmodel){
        this.setEmptyVmodel()
        setTimeout(()=>this.changeName(name),100)
      }else{
        this.changeName(name)
      }
    },
    changeName(name){
      this.optionList = [
        {value:this.vmodel,title:name}
      ]
      this.$refs['rxSelectTree'].vmodels = this.vmodel
    },
    setEmptyVmodel(){
      this.$emit('update:vmodel', "")
    },
    getName(){
      for(var i = 0 ; i < this.optionList.length ; i++){
        var item = this.optionList[i]
        var value = item["value"]
        if(value == this.vmodel) return item['title']
      }
      return ''
    },
    clearFields(){
      this.$refs['rxSelectTree'].clearAll()
      this.optionList = []
    },
    //展开员工所在部门
    expandStaffDepartment:function (departmentList) {
        this.checkExpand(this.treeData, departmentList)
    },
    checkExpand:function (child,departmentList) {
        if (child.length > 0) {
            for (let i = 0; i < child.length; i++) {
                this.checkExpand(child[i],departmentList)
            }
        }

        let isExpand = false
        for (let j = 0; j < departmentList.length; j++) {
            if (child.id == departmentList[j].id) {
                this.$set(child,'expand',true); //重要！用set方法
                this.$set(child,'selected',true); //重要！用set方法
                isExpand = true
                break
            }
        }

        if (isExpand && child.children && child.children.length > 0) {
            this.checkExpand(child.children,departmentList)
        }
    }
  }

  }
</script>
<style lang="less">
  @import "../common/rx-common";
  .rx-tree-outline .ivu-icon-ios-arrow-forward:before {
    //content: "\f330";
    //border:1px solid @main-color;
    border:0px solid @main-color;
    content: "\F11F";
    color:@main-color;
  }

</style>
