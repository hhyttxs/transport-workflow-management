<template>
<!--  <Image v-if="fileShowUrl&&fileShowUrl.length>0" :src="fileShowUrl" fit="cover" width="100%" height="100%" />-->
  <Upload
    ref="upload"
    :action="fileUploadUrl"
    :data="fileData"
    :show-upload-list="false"
    :on-success="handleSuccess"
    :before-upload="handleBeforeUpload"
    :on-error="handleError"
    :headers="{'charset':'utf-8'}"
    :format="['pdf','xlsx','xls','docx','doc']"
    type="drag"
    :on-preview="handlePreview"
    :on-remove="handleRemove"
    :default-file-list="defaultList">
    <!--    :max-size="imgMaxSize"-->
    <!--    :on-exceeded-size="handleMaxSize"-->
    <!--    <div style="padding: 20px 0">-->
    <!--      <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>-->
    <!--      <p>点击或拖拽文件</p>-->
    <Button type="primary" icon="ios-cloud-upload-outline">{{uploadFileName}}</Button>
    <!--    </div>-->
  </Upload>
</template>

<script>
  //import {baseUrl} from "../../../config/env"
  // import {imgMaxSize} from "../../../config/config"
  import {responseUtil} from '@/utils/rongxunUtil'

  export default {
    name: "uploaderFile",
    data() {
      return {
        showLoading: false,
        fileUploadUrl: process.env.VUE_APP_ADMIN_URL + "/manageCommon/saveFileToLocal.json",
        fileData: {
          fileName: "",
          path: "",
          relativePath: "file"
        },
        imgUrlPrefix: "images/platform/template",
        // imgMaxSize:imgMaxSize,
        uploadList: [],
        defaultList: [],
        fileName: ''
      }
    },
    props: {
      success: {},
      fileKind: {
        default: String
      },
      uploadFileName: {
        default: String
      },
      fileShowUrl:{
        default:String
      }
    },
    methods: {
      handleSuccess(res, file) {
        if (res.code == "0") {
          responseUtil.alertOK(this)
          this.fileName = res.data.fileName
          let fileObj = {
            name: this.fileName
          }
          this.uploadList.push(fileObj)
        } else {
          responseUtil.alertErrMsg(this, "上传失败：" + res.msg)
        }
        if (this.success) {
          this.success(file.response.data, this.fileKind)
        } else {
          this.$emit("uploaderFile", file.response.data, this.fileKind)
        }
        this.showLoading = false
      },
      handlePreview(select) {
      },
      handleRemove() {
        this.uploadList = []
      },
      controlUploadClearFile(){
        this.uploadList = []
        this.$refs.upload.clearFiles()
      },
      //文件上传失败
      handleError(error) {
        this.showLoading = false
        this.$Notice.warning({
          title: '文件上传失败',
        });
      },
      //文件最大值
      // handleMaxSize (file) {
      //   this.showLoading = false
      //   this.$Notice.warning({
      //     title: '文件过大',
      //   });
      // },
      //文件上传之前校验文件个数
      handleBeforeUpload(file) {
        if (file) {
          if (this.uploadList.length >= 1) {
            responseUtil.alertMsg(this, "只能上传一个文件。")
            return false
          }
          this.showLoading = true
        }
      },
    }
  }
</script>
<style lang="less">
  .ivu-upload .ivu-upload-drag {
    margin-top: 3px;
    margin-left: 5px;
    width: 98.4%;
  }
</style>
