<template>
  <div>
    <rxModal ref="addPasswordModal" :buttonArray="addButtonPasswordArray" :title="title" :width='800' :needCancelConfirm="true" :zIndex="1000"
             :validatedForm="this.$refs['passwordValidate']">
      <Form ref="passwordValidate" :model="passwordValidate" :rules="passwordRuleValidate">

        <rxCard title="修改密码" :downBorder="false" style="width: 75%;float: left">
          <rxLabel label="密码：" :width="275" prop="password" :labelWidth="95">
            <Input v-model="passwordValidate.password" placeholder="密码" type="password" size="large" />
          </rxLabel>
          <rxLabel label="确认密码：" :width="275" prop="confirmPassword" :labelWidth="95" :lineEnd="true">
            <Input v-model="passwordValidate.confirmPassword" placeholder="确认密码" type="password"  size="large" />
          </rxLabel>
        </rxCard>
        <div class="clear-float"></div>
      </Form>
    </rxModal>
  </div>
</template>

<script>
import rxModal from '@/components/rongxun/rx-modal/rx-modal'
import rxCard from '@/components/rongxun/rx-card/rx-card'
import rxLabel from '@/components/rongxun/rx-label/rx-label'
import rxLabelText from '@/components/rongxun/rx-labelText/rx-labelText';
import {
  getUserName,
  responseUtil,
  getStaffId, getUserId
} from '@/utils/rongxunUtil'
import {getUser} from '@/utils/auth';
export default {
  name: 'changePassword',
  components: {
    rxModal,
    rxCard,
    rxLabel,
    rxLabelText
  },
  data () {
    const that = this
    var validatePass = (rule, value, callback) => {
      if (!value || value == '') {
        callback(new Error('请输入密码'))
      } else {
        that.$refs.passwordValidate.validateField('confirmPassword')
        callback()
      }
    }
    var validatePassCheck = (rule, value, callback) => {
      if (!that.passwordValidate.confirmPassword || that.passwordValidate.confirmPassword == '') {
        callback(new Error('请输入确认密码'))
      } else if (that.passwordValidate.password != that.passwordValidate.confirmPassword) {
        callback(new Error('密码不一致'))
      } else {
        callback()
      }
    }
    var checkPassWord = (rule, value, callback) => {
      let reg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[`~!@#$%^&*()_+<>?:"{},.\/\\;'[\]])[A-Za-z\d`~!@#$%^&*()_+<>?:"{},.\/\\;'[\]]{8,}$/;
      if (!reg.test(value)) {
        callback(new Error('长度不小于8位，且包含数字、大小写字母及其他特殊字符，特殊字符包含：@、!、#等。'));
      }else {
        callback()
      }
    }
    return {
      title: '',
      passwordValidate: {
        password: '',
        confirmPassword: ''
      },
      passwordRuleValidate: {
        password: [
          { required: true, validator: checkPassWord, trigger: 'blur' },
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, validator: checkPassWord, trigger: 'blur' },
          { required: true, validator: validatePassCheck, trigger: 'blur' }
        ],
      },
      addButtonPasswordArray: [{
        title: '取消',
        click: (e) => this.cancelStaffPasswordData(e)
      },
        {
          title: '保存',
          click: (e) => this.saveStaffPasswordData(e)
        }
      ],
    }
  },
  methods: {
    // 修改密码，弹出框
    editPassword: function() {
      var staffName = getUserName()
      this.title = '修改登录账号密码'
      this.initEditStaffPassword('addPasswordModal')
    },
    initEditStaffPassword: function(model) {
      this.passwordValidate.password = ''
      this.passwordValidate.confirmPassword = ''
      this.$refs['addPasswordModal'].modalFlag = true
    },
    saveStaffPasswordData: function() {
      this.$refs['passwordValidate'].validate((valid) => {
        if (valid) {
          var that = this
          let initData = that.passwordValidate
          initData.postFrom = 'admin'
          let loginUserInfo = getUser();
          initData.user_id = loginUserInfo.gid
          // updateStaffPassword(initData).then(function(response) {
          //   responseUtil.dealResponse(that, response, () => {
          //     responseUtil.alertOK(that)
          //     that.$refs['addPasswordModal'].modalFlag = false
          //   })
          // })
        }
      })
    },
    cancelStaffPasswordData: function() {
      this.$refs['addPasswordModal'].modalFlag = false
    },
  }
}
</script>
<style lang="less">
@import "../../../components/rongxun/common/rx-common.less";
@import "../../../components/rongxun/rx-tabs/rx-tabs.less";
@import "../../../components/rongxun/rx-table/rx-table.less";
@import "../../../components/rongxun/rx-modal/rx-modal.less";
@import "../../../components/rongxun/rx-card/rx-card.less";
@import "../../../components/rongxun/rx-label/rx-label.less";
@import "../../../components/rongxun/rx-labelText/rx-labelText.less";
@import "../../../components/rongxun/rx-button/rx-button.less";
</style>
