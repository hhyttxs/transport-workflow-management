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
        {{ item.titel }}</p>
      <div class="rx-material-button">
        <div style="float: right;width: 120px;margin-right: 5px" v-for="(item_c,index_c) in item.child_list">
          <Image style="cursor:pointer;" title="点击预览" v-if="item_c.EXT == 'jpg'||item_c.EXT == 'png'"
                 :src="item_c.VIEW_PATH"
                 :fit="contain" width="120px"
                 height="80px"
                 @click="fileShow(item_c.ATTACHMENTPATH,item_c.ATTACHMENTNAME,item_c.EXT)"/>
          <div style="height: 80px;width: 120px;font-size: 10px" v-if="item_c.EXT != 'jpg'&&item_c.EXT != 'png'">
            <div style="height: 60px">{{ item_c.ATTACHMENTNAME }}.{{ item_c.EXT }}</div>
            <Button style="position: relative;width: 120px;height: 20px;font-size: 12px" size="small" type="primary"
                    @click="downLoadFile(item_c.ATTACHMENTPATH,item_c.ATTACHMENTNAME)">
              下载
            </Button>
          </div>
          <Button style="position: relative;width: 120px;height: 20px;font-size: 12px" size="small" type="warning"
                  @click="deleteFileMethod(item.titel,item_c.ROWGUID)">删除
          </Button>
        </div>
        <!--只有暂存申报和继续申报进来才能上传材料-->
        <rxUploaderFile :uploadFileName="'上传'" :fileKind="item.titel"
                        ref="uploaderFile" prop="path" :currentFileListSize="item.child_list.length"
                        :success="uploadFile" fileShowUrl="">
        </rxUploaderFile>
      </div>
    </div>
    <Alert class="button">
      <button @click="changeStepTo(2)">上一步</button>
      <button v-if="sxlb_code!='3'" @click="submitAllFileUrl(1)">修改材料</button>
      <button @click="downLoadFileZip()">打包下载</button>
    </Alert>
  </div>
</template>
<script>
import rxUploaderFile from '@/components/rongxun/rx-uploader/rx-uploaderFile-gk'
import {saveOrUpdateAcceptMaterial_gk, downLoadFile_gk} from '@/api/material/material'
import {responseUtil, closeThisView} from '@/utils/rongxunUtil'
import {updateAcceptStatus_sl} from '../../../../api/waterTransport/xxkcjyxk'

export default {
  name: 'rx-material',
  components: {
    rxUploaderFile
  },
  props: {
    material: [],
    projectNo: '',
    sxlb_code: '',
  },
  mounted() {
  },
  data() {
    return {
      visible: false,
      image_src: '',
      formValidate: {},
      opList: ['zoomIn', 'zoomOut', 'original', 'rotateLeft', 'rotateRight', 'delete']
    }
  },
  methods: {
    fileShow(img_url, titel, ext) {
      let url = location.origin + '/portUser/fileCheckPage?url=' + img_url + '&title=' + titel + '&extType=' + ext
      let iWidth = 1000   // 弹出窗口的宽度;
      let iHeight = 500  // 弹出窗口的高度;
      let screenHeight = window.screen.height  // 获得屏幕的高;
      let screenWidth = window.screen.width   // 获得屏幕的宽;
      let iTop = (screenHeight - 30 - iHeight) / 2  //获得窗口的垂直位置;
      let iLeft = (screenWidth - 10 - iWidth) / 2   //获得窗口的水平位置;
      let configuration = `
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
      window.open(url, '', configuration)
    },
    deleteFileMethod(titel, gid) {
      this.material.forEach(r => {
        if (titel === r.titel) {
          for (let i = r.child_list.length - 1; i >= 0; i--) {
            if (r.child_list[i].ROWGUID == gid) {
              r.child_list.splice(i, 1)
            }
          }
        }
      })
      this.$Message.success('删除成功!')
    },
    createUUID() {
      return (this.S4() + this.S4() + '-' + this.S4() + '-' + this.S4() + '-' + this.S4() + '-' + this.S4() + this.S4() + this.S4())
    },
    S4() {
      let S4 = (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)
      return S4
    },
    // 上传文档成功
    uploadFile: function (data, param) {
      let that = this
      let indexPath = data.relativePath.lastIndexOf('.')
      let ext = data.relativePath.substring(indexPath + 1, data.relativePath.length)
      that.material.forEach(r => {
        if (param === r.titel) {
          r.url = data.relativePath // 相对路径
          r.ext = ext
          let new_info = {}
          new_info.VIEW_PATH = process.env.VUE_APP_ADMIN_URL + '/portImageView/selectImageUpload.json?path=' + data.relativePath
          new_info.ATTACHMENTNAME = data.fileName.substring(0, data.fileName.lastIndexOf('.'))
          new_info.ROWGUID = that.createUUID()
          new_info.ATTACHMENTPATH = data.relativePath
          new_info.EXT = ext
          r.child_list.push(new_info) // 相对路径
        }
      })
    },
    changeStepTo(number) {
      this.$emit('changeStep', number)
    },
    submitAllFileUrl(type) {
      let that = this
      let initData = {}
      if (!that.projectNo || that.projectNo === '') {
        this.$Notice.warning({
          title: '警告',
          desc: '未生成办件编号,请返回上一步填写表单生成办件编号!'
        })
        return
      }
      initData.fileList = that.material
      initData.projectNo = that.projectNo
      initData.type = 3
      /* TODO 暂存申报时材料不做必填校验 继续申报时加必填校验 */
      saveOrUpdateAcceptMaterial_gk(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          if (response.code === '0') {
            if (type == 1) {
              that.$Message.success('附件保存成功!')
            } else {
              that.updateAcceptStatus()
            }
          } else {
            responseUtil.alertErrMsg(response.msg)
          }
        })
      })
    },
    updateAcceptStatus() {

      let that = this
      let initData = {}
      initData.projectNo = that.projectNo
      /* TODO 暂存申报状态 STATE 改为 0 继续申报补传申请表后 状态改为 */
      updateAcceptStatus_sl(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          that.$Modal.confirm({
            title: '提示:',
            content: '<p>操作成功</p>',
            onOk: () => {
              that.$router.push('/slysMenu/sl_jdLook_index?routType=normal')
            }, onCancel: () => {
              that.$router.push('/slysMenu/sl_jdLook_index?routType=normal')
            }
          })
        })
      })
    },
    downLoadFile(url, fileName) {
      const fileUrl = process.env.VUE_APP_ADMIN_URL + '/portImageView/downLoadFile_gk.json?url=' + url // 文件的URL地址
      const link = document.createElement('a')
      link.href = fileUrl
      link.setAttribute('download', fileName)
      link.click()
    },
    downLoadFileZip() {
      let gid = this.projectNo;
      const fileUrl = process.env.VUE_APP_ADMIN_URL + '/portImageView/downLoadFileZip_gk.json?type=sl&gid=' + gid // 文件的URL地址
      const link = document.createElement('a')
      link.href = fileUrl
      link.setAttribute('download', 'zip')
      link.click()
    },
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
