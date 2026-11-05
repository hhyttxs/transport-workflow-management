<!--操作流程 -- 阅读须知-->
<template>
  <div class="rx-instructions-body">
    <Alert type="warning">{{prompt}}</Alert>
    <Tabs value="tab">
      <Tab-pane label="所需材料" name="tab">
        <ul class="rx-instructions-body-ul">
          <li v-for="(item,index) in instructions" :key="index">{{item.titel}}</li>
        </ul>
      </Tab-pane>
    </Tabs>
    <Alert class="rx-instructions-body-error" type="error">{{label}}</Alert>
    <Alert class="button">
      <button  @click="changeStepTo(2)">下一步</button>
    </Alert>
  </div>
</template>

<script>
    export default {
      name: "rx-instructions",
      props:{
        prompt:{
          default:'提醒:您本次网上申报的办件，工作人员将在预受理之后联系您。'
        },
        label:{
          default: '注:本人对办理该事项所提交的申报材料实质内容的真实性负责。'
        },
        instructions:{
          default:[]
        }

      },
      methods:{
        changeStepTo(number){
          this.$emit('changeStep',number)
        }
      }
    }

</script>

<style scoped lang="less">
  .rx-instructions-body{
    min-height: calc(100vh - 136px);
    background: white;
    flex-grow: 1;
    padding: 15px;
    .rx-instructions-body-ul{
      padding: 0 20px;
    }
    .rx-instructions-body-ul > li{
      line-height: 30px;
    }
    .rx-instructions-body-error{
      margin-top: 20px;
      text-align: center;
      color:#DC143C;
    }
    .button {
      margin-top: 20px;
      height: 50px;
      border: none;
      display: flex;
      align-items: center;
      justify-content: center;
      button{
        margin: 0 10px;
        background:lightgreen;
        border: none;
        width: 70px;
        height: 30px
      }
      button:hover{
        cursor: pointer;
      }
    }
  }
</style>
