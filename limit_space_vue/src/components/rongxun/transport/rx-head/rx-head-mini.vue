<style lang="less">
  @import "../../rx-label/rx-label.less";
</style>
<template>
  <div class="rx-head">
    <Row style="width: 100%">
      <Col span="2">
        <button class="headBackButton" @click="goback()">  ◁-返回 </button>
      </Col>
      <Col span="18">
        <p class="rx-head-title">{{contrnt.title}}</p>
      </Col>
    </Row>
  </div>
</template>

<script>
    export default {
      name: "rx-head-mini",
      props:{
        contrnt:{
          default:{}
        }
      },
      methods:{
        goback:function () {
          this.$router.go(-1)
        }
      }
    }
</script>

<style scoped>
  .rx-head{
    min-height: 30px;
    background: darkblue;
    color: white;
    padding: 20px;
    display: flex;
    flex-direction: column;
  }
  .rx-head-title{
    color: inherit;
    font-size: 20px;
    text-align: center;
  }
  .rx-head-subtitle > p{
    color: white;
  }
  .headBackButton{
    width: 100%;
    height: 100%;
    border: 1px solid green;
    border-radius: 10px;
    background-color:green;
    color: white;
    cursor:pointer;
  }

</style>
