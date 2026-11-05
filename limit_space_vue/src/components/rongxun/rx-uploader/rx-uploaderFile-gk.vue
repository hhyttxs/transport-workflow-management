<template>
  <Image v-if="fileShowUrl&&fileShowUrl.length>0" :src="fileShowUrl" fit="cover" width="100%" height="100%"
         @error="changeImgState">
    <template #error>
      <Icon type="ios-image-outline" size="24" color="#ccc"/>
    </template>
  </Image>
  <Button v-if="false" @click="checkUpPic">查看图片</Button>
  <Upload
    ref="upload"
    :action="fileUploadUrl"
    :data="fileData"
    :show-upload-list="false"
    :on-success="handleSuccess"
    :before-upload="handleBeforeUpload"
    :on-error="handleError"
    :headers="{'charset':'utf-8'}"
    type="drag"
    :format="['jpg','jpeg','png','pdf','doc','docx','xls']"
    :on-preview="handlePreview"
    :on-remove="handleRemove"
    :max-size="imgMaxSize"
    :on-exceeded-size="handleMaxSize"
    :default-file-list="defaultList">

    <!--    <div style="padding: 20px 0">-->
    <!--      <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>-->
    <!--      <p>点击或拖拽文件</p>-->
    <a>{{ (uploadFileName === '更正' || uploadFileName === '上传') ? uploadFileName : (uploadFileName)}}</a>
    <!--    </div>-->
  </Upload>
</template>

<script>
  //import {baseUrl} from "../../../config/env"
  import { imgMaxSize } from '../../../config/config'
  import { responseUtil } from '@/utils/rongxunUtil'

  export default {
    name: 'uploaderFile',
    data () {
      return {
        showLoading: false,
        fileUploadUrl: process.env.VUE_APP_ADMIN_URL + '/manageCommon/saveFileToLocal.json',
        fileData: {
          fileName: '',
          path: '',
          relativePath: 'file'
        },
        imgUrlPrefix: 'images/platform/template',
        imgMaxSize: imgMaxSize,
        uploadList: [],
        defaultList: [],
        fileName: '',
        isShowImg: true
      }
    },
    props: {
      success: {},
      fileKind: {
        default: String
      },
      fileShowUrl: {
        default: String
      },
      uploadFileName: {
        default: String
      },
      currentFileListSize: {
        default: Number
      }
    },
    methods: {
      handleView (name) {
        this.imgName = name
        this.visible = true
      },
      handleSuccess (res, file) {
        if (res.code == '0') {
          responseUtil.alertOK(this)
          this.fileName = res.data.fileName
          let fileObj = {
            name: this.fileName
          }
          this.uploadList.push(fileObj)
        } else {
          responseUtil.alertErrMsg(this, '上传失败：' + res.msg)
        }
        if (this.success) {
          this.success(file.response.data, this.fileKind)
        } else {
          this.$emit('uploaderFile', file.response.data, this.fileKind)
        }
        this.showLoading = false
      },
      handlePreview (select) {
      },
      handleRemove () {
        this.uploadList = []
      },
      controlUploadClearFile () {
        this.uploadList = []
        this.$refs.upload.clearFiles()
      },
      //文件上传失败
      handleError (error) {
        this.showLoading = false
        this.$Notice.warning({
          title: '文件上传失败',
        })
      },
      handleMaxSize (file) {
        this.showLoading = false
        this.$Notice.warning({
          title: '文件过大',
        })
      },
      //文件上传之前校验文件个数
      handleBeforeUpload (file) {
        if(file) {
          if(this.currentFileListSize >= 5) {
            responseUtil.alertMsg(this, "最多上传5个文件!")
            return false
          }
          this.showLoading = true
        }
      },
      checkUpPic () {
        let that = this
        let arr = []
        arr.push(that.fileShowUrl)
        that.$ImagePreview.show({
          previewList: arr
        })
      },
      changeImgState () {
        this.isShowImg = false
      }
    }
  }
</script>
<style lang="less">
  .ivu-upload .ivu-upload-drag {
    border: none;
    text-align: left;
    background-color: rgba(0, 0, 0, 0);
  }
</style>
