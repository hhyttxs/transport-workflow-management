<template>
  <div class="develop">
    <div class="container loginbox">
    </div>
  </div>
</template>
<script>
  export default {
    name: 'page-account-login',
    components: {},
    data() {
      return {
        autoLogin: true,
        username: '',
        password: '',
        tableHeight: 0
      }
    },
    methods: {
    }
  };
</script>
<style lang="less">
  @import './develop.less';
</style>
