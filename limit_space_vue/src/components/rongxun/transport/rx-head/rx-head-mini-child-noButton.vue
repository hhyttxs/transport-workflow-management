<style lang="less">
  @import "../../rx-label/rx-label.less";
</style>
<template>
  <div class="rx-head">
    <Row style="width: 100%">
      <Col span="22">
        <p class="rx-head-title">{{contrnt.title}}</p>
      </Col>
    </Row>
  </div>
</template>

<script>
    export default {
      name: "rx-head-mini-child",
      props:{
        contrnt:{
          default:{}
        }
      },
      data () {
        return {
          isShow:true
        }
      },
      methods:{
        changeShowState:function (temp) {
          this.isShow = temp
          this.$emit('changeShowState',temp)
        }
      }
    }
</script>

<style scoped>
  .rx-head{
    min-height: 30px;
    background: darkblue;
    color: white;
    padding: 9px;
    display: flex;
    flex-direction: column;
    margin-top: 10px;
    text-align: center;
  }
  .rx-head-title{
    color: inherit;
    font-size: 20px;
    text-align: center;
  }
  .rx-head-subtitle > p{
    color: white;
  }
  .headshowButton{
    width: 100%;
    border: none;
    border-radius: 10px;
    background-color:green;
    color: white;
    height: auto;
    cursor: pointer;
    height: 30px
  }

</style>
