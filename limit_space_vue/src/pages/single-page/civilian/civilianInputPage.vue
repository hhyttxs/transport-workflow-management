<template>
  <div class="mainPage">
    <div class="pageTitles">服务选项</div>
    <div class="halfPage">
      <Button type="button" to="/roadFreightLicenceNoBlock/roadFreightLicenceSetUpNoBlock_index" class="homePageButtonscivilian">首次核发许可证</Button>
      <Button type="button" to="/roadFreightLicenceNoBlock/roadFreightLicenceSetUpTempList_index" class="homePageButtonscivilian">进度查看</Button>
      <Button type="button" to="/roadFreightLicenceNoBlock/roadFreightLicenceChangeNoBlock_index" class="homePageButtonscivilian">许可证变更</Button>
      <Button type="button" to="/roadFreightLicenceNoBlock/roadFreightLicenceChangeTempList_index" class="homePageButtonscivilian">进度查看</Button>
      <Button type="button" to="/roadFreightLicenceNoBlock/roadFreightLicenceContinueNoBlock_index" class="homePageButtonscivilian">许可证延续</Button>
      <Button type="button" to="/roadFreightLicenceNoBlock/roadFreightLicenceContinueTempList_index" class="homePageButtonscivilian">进度查看</Button>
      <Button type="button" to="/roadFreightLicenceNoBlock/roadFreightLicenceLogoutNoBlock_index" class="homePageButtonscivilian">许可证注销</Button>
      <Button type="button" to="/roadFreightLicenceNoBlock/roadFreightLicenceLogoutTempList_index" class="homePageButtonscivilian">进度查看</Button>
    </div>
    <div class="halfPage">
      <Button type="button" to="/roadTransportCardNoBlock/roadTransportCardFirstIssueNoBlock_index" class="homePageButtonscivilian">首次道路运输证核发</Button>
      <Button type="button" to="/roadTransportCardNoBlock/roadTransportCardFirstIssueTemp_index" class="homePageButtonscivilian">进度查看</Button>
      <Button type="button" to="/roadTransportCardNoBlock/roadTransportCardLogoutNoBlock_index" class="homePageButtonscivilian">道路运输证注销</Button>
      <Button type="button" to="/roadTransportCardNoBlock/roadTransportCardLogoutTempList_index" class="homePageButtonscivilian">进度查看</Button>
      <Button type="button" to="/roadTransportCardNoBlock/roadTransportCardChangeNoBlock_index" class="homePageButtonscivilian">道路运输证变更核发</Button>
      <Button type="button" to="/roadTransportCardNoBlock/roadTransportCardChangeTempList_index" class="homePageButtonscivilian">进度查看</Button>
      <Button type="button" to="/roadTransportCardNoBlock/roadTransportCardRenewNoBlock_index" class="homePageButtonscivilian">道路运输证换发</Button>
      <Button type="button" to="/roadTransportCardNoBlock/roadTransportCardRenewTempList_index" class="homePageButtonscivilian">进度查看</Button>
      <Button type="button" to="/roadTransportCardNoBlock/roadTransportCardReissueNoBlock_index" class="homePageButtonscivilian">道路运输证补发</Button>
      <Button type="button" to="/roadTransportCardNoBlock/roadTransportCardReissueTempList_index" class="homePageButtonscivilian">进度查看</Button>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'civilianInputPage_index',
    components: {},
    data() {
      return {
        isCheck:false,
        formValidate: {
        },
        ruleValidate: {
        }
      }
    },
    methods: {
      alertOk:function(){
      }
    }
  };
</script>
<style lang="less">
  @import '../../../pages/single-page/civilian/civilianInputPage.less';
  @import "../../../components/rongxun/transport/rx-information/information.less";
  @import "../../../components/rongxun/rx-label/rx-label.less";
</style>
