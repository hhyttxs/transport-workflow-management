<template>
<!--  <Image v-if="fileShowUrl&&fileShowUrl.length>0" :src="fileShowUrl" fit="cover" width="100%" height="100%" />-->
  <Upload
    ref="upload"
    :action="fileUploadUrl"
    :data="fileData"
    :max-size="imgMaxSize"
    :show-upload-list="false"
    :on-success="handleSuccess"
    :on-format-error="handleFormatError"
    :on-error="handleError"
    :headers="{'charset':'utf-8'}"
    :format="['jpg','jpeg','png','pdf']"
    type="drag"
    :on-preview="handlePreview"
    :on-exceeded-size="handleMaxSize"
    :on-remove="handleRemove"
    :default-file-list="defaultList">
    <a >{{uploadFileName}}</a>
  </Upload>
</template>

<script>
  //import {baseUrl} from "../../../config/env"
  import {imgMaxSize} from "../../../config/config"
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
        imgMaxSize:imgMaxSize,
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
      //图片格式错误
      handleFormatError (file) {
        this.showLoading = false
        this.$Notice.warning({
          title: '图片格式错误',
        });
      },
      //文件上传失败
      handleError(error) {
        this.showLoading = false
        this.$Notice.warning({
          title: '文件上传失败',
        });
      },
      //文件最大值
      handleMaxSize (file) {
        this.showLoading = false
        this.$Notice.warning({
          title: '文件过大',
        });
      },
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
    border: none;
    text-align: center;
  }
</style>
