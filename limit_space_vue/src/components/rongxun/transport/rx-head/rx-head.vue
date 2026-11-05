<template>
  <div class="rx-head">
    <p class="rx-head-title">{{contrnt.title}}</p>
    <div class="rx-head-subtitle">
      <p class="rx-head-organ">受理机构：{{contrnt.organ}}</p>
      <p class="rx-head-code">事项编码：{{contrnt.code}}</p>
    </div>
  </div>
</template>

<script>
    export default {
      name: "rx-head",
      props:{
        contrnt:{
          default:{}
        }
      },
    }
</script>

<style scoped>
  .rx-head{
    min-height: 100px;
    background: darkblue;
    color: white;
    padding: 20px;
    display: flex;
    flex-direction: column;
  }
  .rx-head-title{
    color: inherit;
    font-size: 22px;
  }
  .rx-head-subtitle{
    display: flex;
  }
  .rx-head-subtitle > p{
    color: white;
  }
  .rx-head-code{
    margin-left: 20px;
  }
</style>
