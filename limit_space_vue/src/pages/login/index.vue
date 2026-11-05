<template>
  <div class="login">
    <div class="container loginbox">
      <div class="logleft">
        <h1>职业培训管理考核系统</h1>
<!--        <h2>构建公路、水路、公交结合的大交通情报中心</h2>-->
      </div>
      <div class="logright">
        <h1 class="logintit">欢迎登录 <span></span></h1>
        <login-form @on-success-valid="handleLogin" ref="loginForm"></login-form>
      </div>
    </div>
  </div>
</template>
<script>
  import loginForm from '@/components/login-form'
  import {removeToken} from '@/utils/auth'
  import {responseUtil} from "@/utils/rongxunUtil"
  import {mapActions} from 'vuex';

  export default {
    name: 'page-account-login',
    components: {loginForm},
    data() {
      return {
        autoLogin: true,
        username: '',
        password: '',
        tableHeight: 0
      }
    },
    methods: {
      ...mapActions('admin/userLogin', [
        'login',
        'getUserInfo'
      ]),
      handleLogin({userName, password}) {

        removeToken()
        this.login({userName, password,type:'back'}).then(() => {
          this.getUserInfo().then(response => {
            /*this.$router.replace(this.$route.query.redirect || '/', () => {
            });*/
            this.$router.replace(this.$route.query.redirect || '/', () => {
            });
            this.$store.dispatch('admin/user/set', response.data, {root: true});          })
        }).catch(e => {
          responseUtil.alertErrMsg(this, e.msg)
        })
      }
    }
  };
</script>
<style lang="less">
  @import './login.less';
</style>
