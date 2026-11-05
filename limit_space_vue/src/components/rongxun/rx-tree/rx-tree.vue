<template>
  <div class="rx-tree-outline">
    <Tree :data="data" @on-select-change="this.click"></Tree>
  </div>
</template>
<script>
  export default {
    props:{
      data:{},
      click:{
        type:Function
      }
    }
  }
</script>
<style lang="less">
  @import "../common/rx-common";
  .rx-tree-outline .ivu-icon-ios-arrow-forward:before {
    border:0px solid @main-color;
    content: "\F11F";
    color:@main-color;
    height: 100%;
  }

</style>
