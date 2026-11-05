<template>
  <div>
    <div class="rx-labelText-wrap" :style="itemLableTextStyle()">
      <Row :gutter="5">
          <Col v-if="isRequire" class="require-style" span="1">*</Col>
        <Col :span="spanLeft" class-name="rx-labelText-label-left"><div class="rx-labelText-label">{{label}}</div></Col>
        <Col :span="spanRight" class-name="rx-labelText-label-right"><div class="rx-labelText-text">{{text}}</div></Col>
      </Row>
    </div>
    <div v-if="lineEnd" class="clear-float"></div>
  </div>

</template>

<script>
export default {
  name: "rx-label-text",
  props:{
    lineEnd:{
      type:Boolean,
      defalut:false
    },
    label:{
      type:[String, Number],
      defalut:"标题"
    },
    text:{
      type:[String,Number],
      defalut:"内容"
    },
    spanLeft: {
      type: Number,
      default:14,
    },
    spanRight: {
      type: Number,
      default:10,
    },
    LableTextWidth:{
      type: Number,
      default:185,
    },
      isRequire:{
      type: Boolean,
      default:false,
    }
  },
  methods:{
    itemLableTextStyle(){
      let style = {};
      style.width = this.LableTextWidth+"px";
      return style;
    }
  }
}
</script>

<style lang="less">
/*@import "./rx-labelText.less";*/
    .require-style {
        position: absolute;
        margin-left: -9px;
        margin-top: 3px;
        color: red;
    }
</style>
