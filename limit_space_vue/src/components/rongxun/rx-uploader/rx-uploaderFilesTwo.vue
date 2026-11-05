<template>
  <Upload
    ref="upload"
    :action="fileUploadUrl"
    :data="fileData"
    :show-upload-list="false"
    :on-success="handleSuccess"
    :before-upload="handleBeforeUpload"
    :on-error="handleError"
    :headers="{'charset':'utf-8'}"
    :format="['jpg','png']"
    type="drag"
    :on-preview="handlePreview"
    :on-remove="handleRemove"
    :max-size="imgMaxSize"
    :on-format-error="handleFormatError"
    :on-exceeded-size="handleMaxSize"
    :disabled = "proDisabled"
    :default-file-list="defaultList">
<!--    <Button class="btn-new" type="primary" icon="ios-cloud-upload-outline"  style="width:100%">-->
    <a :style="proDisabled?'opacity: 0.2':''">{{(uploadFileName==='更正'||uploadFileName === '上传'|| uploadFileName === '补传')?uploadFileName:("提交"+uploadFileName)}}</a>
<!--    </Button>-->
  </Upload>
</template>

<script>
  //import {baseUrl} from "../../../config/env"
  import {imgMaxSize} from "../../../config/config"
  import { responseUtil } from '@/utils/rongxunUtil'
  export default {
    name: "uploaderFile",
    data(){
      return {
        showLoading:false,
        fileUploadUrl:process.env.VUE_APP_ADMIN_URL+"/manageCommon/saveFileToLocal.json",
        fileData:{
          fileName:"",
          path:"",
          relativePath:"file"
        },
        imgUrlPrefix:"images/platform/template",
        imgMaxSize:imgMaxSize,
        uploadList:[],
        fileName:''
      }
    },
    props:{
        success:{},
        remove:{},
        proDisabled:{
          type:Boolean,
          default:false
        },
        fileKind:{
            default:String
        },
        fileShowUrl:{
            default:String
        },
        uploadFileName:{
          default:String
        },
        defaultList: [],
    },
    methods:{
      handleView (name) {
        this.imgName = name;
        this.visible = true;
      },
      handleSuccess (res, file) {
        if(res.code == "0"){
          responseUtil.alertOK(this)
          this.fileName = res.data.fileName
          let fileObj = {
            name : this.fileName
          }
          this.uploadList.push(fileObj)
        }else{
          responseUtil.alertErrMsg(this, "上传失败：" +  res.msg)
        }
        if(this.success){
          this.success(file.response.data,this.fileKind)
          this.$refs.upload.clearFiles()
        }else{
          this.$emit("uploaderFile",file.response.data,this.fileKind)
        }
        this.showLoading = false
      },
      handlePreview (select) {
        if(select.response){
          var name = select.response.data.fileName;
          var ext = select.response.data.ext;
          let url = location.origin + "/fileCheckPage?url=" + select.response.data.relativePath + "&title=" + name + "&extType=" + ext
          var iWidth = 1000;   // 弹出窗口的宽度;
          var iHeight = 500;  // 弹出窗口的高度;
          var screenHeight = window.screen.height;  // 获得屏幕的高;
          var screenWidth = window.screen.width;   // 获得屏幕的宽;
          var iTop = (screenHeight - 30 - iHeight) / 2;  //获得窗口的垂直位置;
          var iLeft = (screenWidth - 10 - iWidth) / 2;   //获得窗口的水平位置;
          var configuration = `
                              height=${iHeight},
                              innerHeight=${iHeight},
                              width=${iWidth},
                              innerWidth=${iWidth}',
                              top=${iTop},
                              left=${iLeft},
                              toolbar=no,
                              menubar=no,
                              scrollbars=auto,
                              resizeable=no,
                              location=no,
                              status=no
                              `
          let flag1 = window.open(url, '', configuration);
        }else if(select.name && select.url){
          var name = select.name;
          var ext = select.url.split(".")[1];
          let url = location.origin + "/fileCheckPage?url=" + select.url + "&title=" + name + "&extType=" + ext
          var iWidth = 1000;   // 弹出窗口的宽度;
          var iHeight = 500;  // 弹出窗口的高度;
          var screenHeight = window.screen.height;  // 获得屏幕的高;
          var screenWidth = window.screen.width;   // 获得屏幕的宽;
          var iTop = (screenHeight - 30 - iHeight) / 2;  //获得窗口的垂直位置;
          var iLeft = (screenWidth - 10 - iWidth) / 2;   //获得窗口的水平位置;
          var configuration = `
                              height=${iHeight},
                              innerHeight=${iHeight},
                              width=${iWidth},
                              innerWidth=${iWidth}',
                              top=${iTop},
                              left=${iLeft},
                              toolbar=no,
                              menubar=no,
                              scrollbars=auto,
                              resizeable=no,
                              location=no,
                              status=no
                              `
          let flag1 = window.open(url, '', configuration);
        }
      },
      controlUploadClearFile(){
        this.$refs.upload.clearFiles()
      },
      handleRemove (file,fileList) {
        this.uploadList = []
        this.uploadFiles = fileList
        let url = ''
        if(file.response){
          if(file.response.data){
            if(file.response.data.relativePath){
              url = file.response.data.relativePath
            }
          }
        }else{
          url = file.url
        }
        this.remove(this.fileKind,url)
      },
      //文件上传失败
      handleError (error) {
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
      handleBeforeUpload (file) {
        if(file) {
          this.showLoading = true
        }
      },
      handleFormatError(){
        this.$Modal.warning({
          title: '文件格式错误',
          content:'<p>请上传:jpg,png</p><p>格式</p>'
        });
      }
    }
  }
</script>
<style lang="less">
.ivu-upload .ivu-upload-drag {
  border: none;
  background-color: rgba(0,0,0,0);
  text-align: left;
}
</style>
