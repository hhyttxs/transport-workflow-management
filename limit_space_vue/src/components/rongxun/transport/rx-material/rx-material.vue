<template>
  <div class="rx-material">
    <p class="rx-material-title">
      <span>
        提醒: 1. ★为必填材料，您必须提交才能申报，☆为非必要材料,根据您实际情况选择提交。 2.附件大小不能超过50M。
      </span>
    </p>
    <div class="rx-material-body" v-for="(item,index) in material">
      <p>{{ index + 1 }}、
        <span v-if="item.required == 'true'">★</span>
        <span v-else>☆</span>
        {{ item.titel.substring(item.titel.indexOf("、") + 1, item.titel.length) }}</p>
      <p class="rx-material-annotation">{{item.message}}</p>
      <div class="rx-material-button">
        <Button v-if="material[index].url && material[index].url !== '' "
                @click="fileShow(material[index].url,material[index].titel,material[index].value)">查看上传
        </Button>
        <!--只有暂存申报和继续申报进来才能上传材料-->
        <rxUploaderFile v-if="sxlb_code==='0'||sxlb_code==='1'||sxlb_code==='3'" :uploadFileName="'上传'"
                        :fileKind="material[index].titel"
                        ref="uploaderFile" prop="path"
                        :success="uploadFile" fileShowUrl="">
        </rxUploaderFile>
      </div>
    </div>
    <Alert v-if="sxlb_code==='0'" style="text-align: center;color:#DC143C;" type="error">
      提醒：点击提交按钮数据直接到受理列表；如需要下载申请表，请点击暂存信息后前去下载。
    </Alert>
    <Alert class="button">
      <button @click="changeStepTo(2)">上一步</button>
      <button v-if="sxlb_code==='0'" @click="submitAllFileUrl(0)">暂存信息</button>
      <button v-if="sxlb_code==='1'" @click="submitAllFileUrl(1)">修改信息</button>
      <button v-if="sxlb_code==='0'||sxlb_code==='3'" @click="submitAllFileUrl(3)">提交</button>
      <!--      <button @click="updateAcceptStatus">下一步</button>-->
    </Alert>
  </div>
</template>
<script>
  import rxUploaderFile from '@/components/rongxun/rx-uploader/rx-uploaderFile'
  import {saveOrUpdateAcceptMaterial} from '@/api/material/material'
  import {responseUtil} from "@/utils/rongxunUtil";
  import {updateAcceptStatus} from "../../../../api/waterway/waterWayApprove";

  export default {
    name: "rx-material",
    components: {
      rxUploaderFile
    },
    props: {
      material: [],
      projectNo: '', sxlb_code: ''
    },
    mounted() {

      this.getFullMaterialList()
    },
    data() {
      return {
        visible: false, image_src: '',
        formValidate: {},
        newMaterialList: []
      }
    },
    methods: {
      fileShow(img_url, titel, key) {
        let ext = '';
        this.newMaterialList.forEach(r => {
          if (key === r.value) {
            ext = r.ext;
          }
        })
        let url = location.origin + "/admin/fileCheckPage?url=" + img_url + "&title=" + titel + "&extType=" + ext
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
        window.open(url, '', configuration);
      },
      // 上传文档成功
      uploadFile: function (data, param) {
        var that = this
        let indexPath = data.relativePath.lastIndexOf('.')
        let ext = data.relativePath.substring(indexPath + 1, data.relativePath.length);
        that.newMaterialList.forEach(r => {
          if (param === r.titel) {
            r.url = data.relativePath // 相对路径
            r.ext = ext;
          }
        })
      },
      changeStepTo(number) {
        this.$emit('changeStep', number)
      },
      getFullMaterialList() {
        let that = this
        console.info(that.material)
        that.material.forEach(r => {
          that.newMaterialList.push(r)
        })
      },
      submitAllFileUrl(type) {
        let that = this
        let initData = {}
        if (!that.projectNo || that.projectNo === '') {
          this.$Notice.warning({
            title: '警告',
            desc: '未生成办件编号,请返回上一步填写表单生成办件编号!'
          });
          return
        }
        initData.fileList = that.newMaterialList;
        initData.projectNo = that.projectNo;
        initData.type = type;
        /* TODO 暂存申报时材料不做必填校验 继续申报时加必填校验 */
        saveOrUpdateAcceptMaterial(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === "0") {
              if (type == 3) {
                that.updateAcceptStatus();
              } else if (type == 0) {
                that.$Message.success('暂存附件信息成功!');
              } else if (type == 1) {
                that.$Message.success('修改附件信息成功!');
              }
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      updateAcceptStatus() {
        let that = this;
        let initData = {}
        initData.projectNo = that.projectNo;
        /* TODO 暂存申报状态 STATE 改为 0 继续申报补传申请表后 状态改为 */
        updateAcceptStatus(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            that.$Modal.confirm({
              title: '提示:',
              content: '<p>数据提交成功，请前去受理</p>',
              onOk: () => {
                location.reload();
              }, onCancel: () => {
                location.reload();
              }
            })
          })
        })
      }
    }
  }
</script>
<style scoped lang="less">
  .rx-material {
    flex-grow: 1;
    min-height: ~'calc(100vh - 136px)';
    background: white;
    width: 70%;
    padding: 15px;

    .rx-material-title {
      margin-bottom: 20px;
    }

    .rx-material-body {
      border: 1px solid #E6E6E6;
      padding: 10px;

      .rx-material-annotation {
        margin-left: 10px;
        color: #B6252B;
        font-size: 12px;
      }

      .rx-material-button {
        display: flex;
        justify-content: flex-end;
        margin-top: 10px;

        .rx-material-button-mail {
          width: 95px;
          height: 32px;
          margin-left: 10px;
        }
      }
    }

    .rx-material-prompt {
      margin-top: 10px;
      display: flex;
      align-items: center;

      .rx-material-prompt-tx {
        margin-left: 20px;
      }
    }

    .button {
      margin-top: 20px;
      height: 50px;
      border: none;
      display: flex;
      align-items: center;
      justify-content: center;

      button {
        margin: 0 10px;
        background: lightgreen;
        border: none;
        width: 70px;
        height: 30px
      }

      button:hover {
        cursor: pointer;
      }
    }
  }
</style>
