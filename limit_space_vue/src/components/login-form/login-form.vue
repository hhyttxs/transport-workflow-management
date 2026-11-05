<template>
  <Form ref="loginForm" :model="form" :rules="rules" @keydown.enter.native="handleSubmit">
    <FormItem prop="userName">
      <div class="formItem">
        <Input class="login-class" v-model="form.userName" placeholder="输入账号"></Input>
        <img src="../../assets/images/account.png"/>
      </div>
    </FormItem>
    <FormItem prop="password">
      <div class="formItem">
        <Input class="login-class" type="password" v-model="form.password" placeholder="输入密码"></Input>
        <img src="../../assets/images/password.png" alt="">
      </div>
    </FormItem>
    <FormItem>
      <Button @click="handleSubmit" type="primary" long>登 录</Button>
    </FormItem>
  </Form>
</template>
<script>
export default {
  name: 'LoginForm',
  props: {
    userNameRules: {
      type: Array,
      default: () => {
        return [
          { required: true, message: '账号不能为空', trigger: 'blur' }
        ]
      }
    },
    passwordRules: {
      type: Array,
      default: () => {
        return [
          { required: true, message: '密码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  data () {
    return {
      form: {
        userName: '',
        password: ''
      }
    }
  },
  computed: {
    rules () {
      return {
        userName: this.userNameRules,
        password: this.passwordRules
      }
    }
  },
  methods: {
    handleSubmit () {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.$emit('on-success-valid', {
            userName: this.form.userName,
            password: this.form.password
          })
        }
      })
    },
    showError(msg){
      this.$refs["password"].validateState = "error"
      this.$refs["password"].validateMessage = msg
    }
  }
}
</script>
