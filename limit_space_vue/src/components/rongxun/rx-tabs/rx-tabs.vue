<template>
  <!--标签页面 开始-->
  <Tabs type="card" :animated="false" @on-click="(e)=>tabClick(e)" >
    <TabPane  v-for="(item, index) in tabsData" :key="index" :label="item.title" :name="item.slotName">
      <div class="rx-tabs-content">
        <slot :name="item.slotName" v-if="item.type=='slot'">
        </slot>
      </div>
    </TabPane>
  </Tabs>
  <!--标签页面 结束-->
</template>

<script>
export default {
    name: "rx-tabs",
    props:{
      tabsData:{
        type:Array,
      },
      clickName:{
        type:String,
      },
      click:{
        type:Function,
        default:()=>{}
      },
    },
    data(){
      return {
      }
    },
    methods:{
      /**
       * 点击标签时触发的方法
       * @param e
       */
      tabClick(e){
        if(this.click) this.click(e)
      }
    }
}
</script>

<style lang="less">
/*@import "./rx-tabs.less";*/
</style>
