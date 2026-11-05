<template>
  <div class="rx-slider-head">
    <p class="rx-slider-head-title">{{contrnt.title}}</p>
    <div class = "rx-slider">
      <div  class = "rx-slider-tap">
        <div class="rx-slider-img"><img src="@/assets/rxSlider/ydxz.jpg" alt=""></div>
        <div class="rx-slider-msg">{{contrnt.firstStep}}</div>
      </div>
      <div class="rx-slider-line"></div>
      <div v-if="step>=2" class = "rx-slider-tap">
          <div class="rx-slider-img"><img src="@/assets/rxSlider/sbxx.png" alt=""></div>
        <div class="rx-slider-msg">{{contrnt.secondStep}}</div>
      </div>
      <div v-else class = "rx-slider-tap-uncheck">
        <div class="rx-slider-img"><img src="@/assets/rxSlider/sbxx_unCheck.png" alt=""></div>
        <div class="rx-slider-msg">{{contrnt.secondStep}}</div>
      </div>
      <div class="rx-slider-line"></div>
      <div  v-if="step>=3" class = "rx-slider-tap">
        <div class="rx-slider-img"><img src="@/assets/rxSlider/sqcl.png" alt=""></div>
        <div class="rx-slider-msg">{{contrnt.thirdStep}}</div>
      </div>
      <div  v-else class = "rx-slider-tap-uncheck">
        <div class="rx-slider-img"><img src="@/assets/rxSlider/sqcl_unCheck.png" alt=""></div>
        <div class="rx-slider-msg">{{contrnt.thirdStep}}</div>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
      name: "rx-sliderTwo",
      props:{
        contrnt:{
          default:{}
        },
        step:{
          Number
        }
      },
    }
</script>

<style scoped lang="less">
  .rx-slider-head{
    width: inherit;
  }
  .rx-slider-head-title{
    font-size: 20px;
    width: 100%;
    text-align: center;
    height: 10.9vh;
    line-height: 12vh;
    font-weight: bold;
  }
  .rx-slider{
    min-height: 100vh;
  }
  .rx-slider-tap{
    width: 109px;
    height: 109px;
    margin-left: 42px;
    text-align: center;
    font-size: 18px;
    background-color: #5B89EB;
    color: #E4FFFF;
    border-radius: 5px;
  }
  .rx-slider-tap-uncheck{
    width: 109px;
    height: 109px;
    margin-left: 42px;
    text-align: center;
    font-size: 18px;
    background-color: #F3F3F3;
    color: #525252;
    border-radius: 5px;
  }
  .rx-slider-line{
    border: 2px solid #CBCBCB;
    width: 2px;
    height: 10px;
    margin-left: 98px;
    margin-top: 5px;
    margin-bottom: 5px;
  }
  .rx-slider-img{
    width: 100%;
    height: 72px;
    padding-top:10px;
  }
  .rx-slider-head{
    background-color: white;
  }
</style>
