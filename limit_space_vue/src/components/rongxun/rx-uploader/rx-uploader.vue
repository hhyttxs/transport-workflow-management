<template>
  <div>
    <div id="loading" v-show="showLoading"></div>
    <img id="loadingImg" v-show="showLoading" src="./loading.gif" />
    <div :style="imgStyle" align="center" class="upLoadImgClass">
      <img :src="src" style="max-height: 100%;max-width: 100%;margin: auto" @click="this.upload">
    </div>
    <Upload
      ref="upload"
      :show-upload-list="false"
      :on-success="handleSuccess"
      :format="format"
      :max-size="imgMaxSize"
      :on-format-error="handleFormatError"
      :on-exceeded-size="handleMaxSize"
      :before-upload="handleBeforeUpload"
      :on-error="handError"
      :action="fileUploadUrl"
      :data="fileData"
      type="drag"
      :headers="{'charset':'utf-8'}"
      style="display: none;width:330px;margin-top: 10px;">
      <div style="width: 130px;height:80px;line-height: 180px;">
        <Icon type="ios-camera" size="20"></Icon>
      </div>
    </Upload>
  </div>
</template>

<script>
  import {baseUrl} from "../../../config/env"
  import {imgMaxSize} from "../../../config/config"

  export default {
    name: "rx-uploader",
    data(){
      return {
        showLoading:false,
        fileUploadUrl:baseUrl+"/photo/upload.json",
        fileData:{
          fileName:"",
          path:""
        },
        imgUrlPrefix:"images/platform/template",
        imgMaxSize:imgMaxSize,
      }
    },
    props:{
      success:{},
      format:{
        type:Array,
        default:()=>['jpg','jpeg','png']
      },
      imgStyle:{},
      src:"",
    },
    methods:{
      upload(){
        this.$refs["upload"].$children[0].$el.click()

      },
      handleSuccess (res, file) {
        if(this.success){
          this.success(file.response.data.path)
        }else{
          this.$emit("setSrc",file.response.data.path)
        }
        this.showLoading = false

      },
      //图片上传失败
      handError (error) {
        this.showLoading = false
        this.$Notice.warning({
          title: '图片上传失败',
        });
      },
      //图片格式错误
      handleFormatError (file) {
        this.showLoading = false
        this.$Notice.warning({
          title: '图片格式错误',
        });
      },
      //图片最大值
      handleMaxSize (file) {
        this.showLoading = false
        this.$Notice.warning({
          title: '图片过大',
        });
      },
      //图片上传之前校验图片个数
      handleBeforeUpload (file) {
        if(file) {
          this.showLoading = true
          this.fileData.fileName = file.name
          this.fileData.path = this.imgUrlPrefix + "tmp"

        }
      },
    }
  }
</script>

<style scoped>
  #loading{
    z-index:1000;
    left:0%;
    top:0%;
    position:fixed;
    height:100%;
    width: 100%;
    background-color: black;
    opacity: 0.5;
  }
  #loadingImg{
    position: fixed;
    left: 45%;
    top: 34%;
    z-index: 10001;
  }
  .upLoadImgClass{
    background-color: #E1EAEC;
    text-align: center;
    display: flex;
    align-items: center;
  }
</style>
