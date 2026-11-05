<template>
  <div class="homePage">
    <div class="homePage-shelterLayer">
      <div class="homePageTitle">
        <div class="homePageTitleImg" ></div>
      </div>

      <div class="homePageButton">
        <Button type="button" to="/legalPersonPage" class="homePageButtons">注册</Button>
        <Button type="button" to="/loginSimple" class="homePageButtons">登录</Button>
      </div>
    </div>
    <rxModal
      ref="addModal"
      title="提示"
      :buttonArray="buttonArray">
      <p>{{message}}</p>
    </rxModal>
  </div>
</template>
<script>
  import {getToken} from "@/utils/auth";
  import rxModal from '@/components/rongxun/rx-modal/rx-modal'

  export default {
    name: 'homepage',
    components: { rxModal },
    data() {
      return {
        message:'',
        buttonArray:[
          { title: '注册', click: (e) => this.goRegister(e) },
          { title: '登录', click: (e) => this.goSimpleLogin(e) }
        ]
      }
    },
    methods: {
      onlineDeclaration(){
        var token = getToken();

        if (!token || token == ''){
          this.message = '请先登陆，没有账号请先注册，再进行登陆'
          this.$refs['addModal'].modalFlag = true
        }else{
          this.$router.push({path:'/home'})
        }
      },
      goRegister(){
        this.$router.push({path:'/legalPersonPage'})
      },
      goSimpleLogin(){
        this.$router.push({path:'/loginSimple'})
      }
    }
  };
</script>
<style lang="less">
  @import './homePage.less';
  @import "../../../components/rongxun/rx-modal/rx-modal.less";
</style>
