<template>
  <div>
    <FormItem :label="label" :style="wrapStyles()" class="rx-lableText-wrap" :prop="prop" :labelWidth="labelWidth" v-if="type">
        <div class="rx-label-item-wraper" :style="itemWraperStyle()">
          <!--<span class="rx-lableText-label">{{label}}：</span>-->
          <slot></slot>
        </div>
    </FormItem>
    <FormItem :label="label" :style="wrapStyles()" class="rx-lableText-wrap" :prop="prop" :labelWidth="labelWidth" :rules="rules" v-if="!type">
      <div class="rx-label-item-wraper" :style="itemWraperStyle()">
        <!--<span class="rx-lableText-label">{{label}}：</span>-->
        <slot></slot>
      </div>
    </FormItem>
    <div v-if="lineEnd" class="clear-float"></div>

  </div>
</template>

<script>
export default {
    name: "rx-label-input",
    props:{
      lineEnd:{
        type:Boolean,
        defalut:false,
      },
      width:{
        default:200
      },
      label:{
        type:String,
        default:""
      },
      labelWidth: {
        type: Number,
        default:70,
      },
      prop:{},
      value:{},
      type:{
        type:Boolean,
        defalut:true,
      },
      rules:{
        type:Object,
        defalut:{},
      }
    },
    methods:{
      wrapStyles(){
        return `width:${this.width}px;float:left`
      },
      itemWraperStyle(){
        let style = {};
        // if (labelWidth || labelWidth === 0) {
        //   style.marginLeft = `${labelWidth}px`;
        // }
        style.marginLeft = "7px";
        // if(this.labelWidth) style.width = this.labelWidth + "px"
        return style;
      }
    }
}
</script>

<style lang="less">
/*@import "./rx-label.less";*/
  .rx-label-item-wraper{
    position:relative;
    line-height:32px;
    font-size:12px
  }
</style>
