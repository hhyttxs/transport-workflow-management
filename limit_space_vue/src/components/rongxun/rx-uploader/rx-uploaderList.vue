<template>
  <div>
    <div id="loading" v-show="showLoading"></div>
    <img id="loadingImg" v-show="showLoading" src="./loading.gif"></img>
    <div :style="imgStyle" v-if="iconList.length > 0">
      <div style="width: 100%;background-color: gainsboro;text-align: center;">上传图片</div>
      <div class="demo-upload-list">
        <div class="default-img">
          <img :src="defaultImage">
        </div>
        <div class="demo-upload-list-cover">
          <div v-for="item in iconList" @click="onClick(item)" class="text-div" >{{item.dictionaryTitle?item.dictionaryTitle:''}}</div>
        </div>
      </div>
    </div>
    <img :src="defaultImage" :style="imgStyle" @click="this.upload" v-if="iconList.length == 0">
    <Upload
      ref="upload"
      multiple
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
      :default-file-list="defaultList"
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
  import {defaultImage} from "../../../config/config"
  import {imgMaxSize} from "../../../config/config"
  import {baseUrl} from "../../../config/env"

  export default {
    name: "rx-uploader",
    data(){
      return {
        showLoading:false,
        fileUploadUrl:baseUrl+"/photo/upload.json",
          //fileUploadUrl:"http://f2c.api.duomaide.com/manage"+"/photo/upload.json",
        fileData:{
          fileName:"",
          path:""
        },
        imgUrlPrefix:"images/platform/template",
        imgMaxSize:imgMaxSize,
        defaultImage:defaultImage,
        defaultList:[],
        srcList:[],
        passCount:0,
        errorCount:0,
        allCount:0
      }
    },
    props:{
      success:{},
      addtion:{},
      format:{
        type:Array,
        default:()=>['jpg','jpeg','png']
      },
      iconList:{
        type:Array,
        default:()=>[]
      },
      onClick:{
        type:Function,
        default:()=>{}
      },
      imgStyle:{},
    },
    methods:{
      upload(){
        this.$refs["upload"].$children[0].$el.click()
        this.defaultList = []
        this.srcList = []
        this.passCount = 0
        this.errorCount = 0
        this.allCount = 0
      },
      handleSuccess (res, file,fileList) {
        this.passCount = this.passCount + 1
        fileList.map((item) => {
          item.path = item.response && item.response.code == 0 ? item.response.data.path : ''
        })
        this.srcList = fileList
        this.checkToReturn()
      },
      //校验并返回
      checkToReturn(){
        if(this.passCount + this.errorCount == this.allCount){
          if(this.success){
            this.success(this.srcList,this.addtion)
          }else{
            this.$emit("setSrc",this.srcList[0].response.data.path)
          }
          this.showLoading = false
        }
      },
      //图片上传失败
      handError (error,file,fileList) {
        this.showLoading = false
        this.errorCount = this.errorCount + 1
        this.$Notice.warning({
          title: '图片上传失败',
        });
        this.checkToReturn()
      },
      //图片格式错误
      handleFormatError (file) {
        this.showLoading = false
        this.errorCount = this.errorCount + 1
        this.$Notice.warning({
          title: '图片格式错误',
        });
      },
      //图片最大值
      handleMaxSize (file) {
        this.showLoading = false
        this.errorCount = this.errorCount + 1
        this.$Notice.warning({
          title: '图片过大',
        });
      },
      //图片上传之前校验图片个数
      handleBeforeUpload (file) {
        if(file) {
          this.showLoading = true
          this.allCount = this.allCount + 1
          this.fileData.fileName = file.name
          this.fileData.path = this.imgUrlPrefix + "tmp"
        }
      },
    }
  }
</script>

<style scoped>
  .demo-upload-list{
    display: inline-block;
    width: 100%;
    height: 100%;
    border: 1px solid transparent;
    border-radius: 4px;
    overflow: hidden;
    background: #fff;
    position: relative;
    box-shadow: 0 1px 1px rgba(0,0,0,.2);
    margin-right: 4px;
  }
  .demo-upload-list img{
    width: 100%;
    height: 100%;
  }
  .demo-upload-list-cover{
    display: none;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0,0,0,.6);
  }
  .demo-upload-list:hover .demo-upload-list-cover{
    display: block;
  }
  .demo-upload-list-cover i{
    color: #fff;
    font-size: 20px;
    cursor: pointer;
    margin: 0 2px;
  }
  .default-img{
    width: 100%;
    height:100%;
  }
  .text-div{
    width: 33%;
    height: 33%;
    padding-top: 10%;
    color: white;
    text-align: center;
    display: inline-block;
  }
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
</style>
