<template>
  <div class="loginSimple">
    <div class="loginboxSimple">
      <div class="logrightSimple">
        <h1 class="logintitSimple">欢迎登录 <span></span></h1>
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
        this.login({userName, password,type:'front'}).then(() => {
          this.getUserInfo().then(response => {
            this.$router.replace(this.$route.query.redirect || '/', () => {
            });
            this.$store.dispatch('admin/user/set', response.data, {root: true});
          })
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
