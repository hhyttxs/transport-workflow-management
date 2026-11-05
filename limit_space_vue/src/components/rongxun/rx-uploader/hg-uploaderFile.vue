<template>
  <div id="loading" v-show="showLoading"></div>
  <img id="loadingImg" v-show="showLoading" src="./loading.gif"/>
  <Upload multiple
          :action="fileUploadUrl"
          :max-size="imgMaxSize"
          :format="format"
          :show-upload-list="false"
          :on-remove="handleRemove"
          :on-exceeded-size="handleMaxSize"
          :on-success="handleSuccess"
          :before-upload="handleBeforeUpload"
          :on-error="handleError"
          :on-format-error="handleFormatError"
          :headers="{'charset':'utf-8'}">
    <Button v-if="showUploadButton" type="primary" size="small" icon="ios-cloud-upload-outline">点击上传材料</Button>
  </Upload>
  <div class="demo-upload-list">
    <template style="margin-top: 10px" v-for="item in uploadList">
      <div style="margin-top: 10px;float: left;width: 100%;">
        <div style="height:100%;width: 90%;float: left;border-bottom: 1px solid #dcdee2;cursor: pointer;"
             @click="previewFile(item.acc_id)">
          {{ item.fileName }}
        </div>
        <div class="demo-upload-list-cover">
          <Icon type="ios-trash-outline" @click="handleRemove(item.acc_id)"></Icon>
        </div>
      </div>
    </template>
  </div>
</template>

<script>
import {imgMaxSize} from "../../../config/config"
import {responseUtil} from '@/utils/rongxunUtil'

export default {
  name: "ghUploaderFile",
  data() {
    return {
      loading: true,
      showLoading: false,
      fileUploadUrl: process.env.VUE_APP_ADMIN_URL + "/uploadFile/uploadFileToServer.json",
      imgMaxSize: imgMaxSize,
      uploadList: [],
      currentFileListSize: 0,
      percent: Number(0),
      show_percent: false,
    }
  },
  props: {
    success: {},
    defaultFileList: [],
    fileListGId: '',
    showUploadButton: true,
    limitFileNum: {
      default: 0
    },
    format: {
      type: Array,
      default: () => ['jpg', 'jpeg', 'png', 'pdf', 'docx', 'xlsx']
    },
  },
  watch: {
    defaultFileList: { //监听的对象
      deep: true, //深度监听设置为 true
      handler: function (newV, oldV) {
        this.currentFileListSize = newV.length
        this.uploadList = newV
      }, immediate: true, // 立即执行一次监听函数
    },
  },
  methods: {
    previewFile(acc_id) {
      let path = '/hg/previewFileIndex'
      window.open(path + '?acc_id=' + acc_id, acc_id, 'toolbar=no,scrollbars=yes,location=no,resizable =yes')
    },
    handleSuccess(res, file) {
      let fileObj = {};
      fileObj.acc_id = res.data
      fileObj.fileName = file.name
      this.uploadList.push(fileObj)
      if (this.success) {
        this.success(this.uploadList, this.fileListGId)
      } else {
        this.$emit('ghUploaderFile', this.uploadList, this.fileListGId)
      }
      this.showLoading = false
    },
    handleRemove(acc_id) {
      for (let i = this.uploadList.length - 1; i >= 0; i--) {
        if (this.uploadList[i].acc_id == acc_id) {
          this.uploadList.splice(i, 1)
        }
      }
      this.currentFileListSize = this.currentFileListSize - 1;
      if (this.success) {
        this.success(this.uploadList, this.fileListGId)
      } else {
        this.$emit('ghUploaderFile', this.uploadList, this.fileListGId)
      }
    },
    //文件上传失败
    handleError(error) {
      this.showLoading = false
      this.$Notice.warning({
        title: '文件上传失败',
      })
    },
    //文件最大值
    handleMaxSize(file) {
      this.showLoading = false
      this.$Notice.warning({
        title: file.name + '</br>文件超过50M,无法上传',
      })
    },
    //文件上传之前校验文件个数
    handleBeforeUpload(file) {
      let limitNum = this.limitFileNum;
      if (limitNum == 0) {
        limitNum = 10;
      }
      if (file) {
        if (this.currentFileListSize >= limitNum) {
          responseUtil.alertMsg(this, '最多上传' + limitNum + '个文件!')
          return false
        }
        this.showLoading = true
        this.currentFileListSize = this.currentFileListSize + 1;

      }
    },
    //图片格式错误
    handleFormatError(file) {
      this.showLoading = false
      this.$Notice.warning({
        title: '图片格式错误。' +
          '只支持：jpg、jpeg、png、pdf、docx、xlsx',
      });
    },
  }
}
</script>
<style lang="less">
.demo-upload-list {
  display: inline-block;
  width: 100%;
  line-height: 40px;
  overflow: hidden;
  background: #fff;
  position: relative;
  margin-right: 4px;
}

.demo-upload-list-cover {
  width: 9%;
  float: right;
  text-align: center;
  background: red;
}

.demo-upload-list-cover i {
  color: #fff;
  font-size: 20px;
  cursor: pointer;
  margin: 0 2px;
}

#loading {
  z-index: 1000;
  left: 0%;
  top: 0%;
  position: fixed;
  height: 100%;
  width: 100%;
  background-color: black;
  opacity: 0.5;
}

#loadingImg {
  position: fixed;
  left: 45%;
  top: 34%;
  z-index: 10001;
}
</style>
