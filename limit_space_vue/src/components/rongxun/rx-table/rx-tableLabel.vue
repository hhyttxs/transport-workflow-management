<template>
  <div class="tableLable" :style="itemLableStyle()">
    <div class="tableLableText">
      <span >{{labelText}}</span>
    </div>
    <div class="tableLableRight" >
    </div>
    <div class="clear-float"></div>
  </div>
</template>

<script>
  export default {
    name: "rx-tableLable",
    props:{
      labelColor:{
        type:String
      },
      labelText:{
        type:String
      },
    },
    data(){
      return {

      }
    },
    methods: {
      itemLableStyle: function () {
        let style = {};
        style.background = this.labelColor;
        return style;
      },
    }
  }
</script>
