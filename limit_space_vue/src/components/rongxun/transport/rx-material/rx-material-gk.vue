<template>
  <div class="rx-material">
    <p class="rx-material-title">
      <span>
        提醒: 1. ★为必填材料，您必须提交才能申报，☆为非必要材料,根据您实际情况选择提交。 2.附件大小不能超过50M。
      </span>
    </p>
    <div class="rx-material-body" v-for="(item,index) in material.filter(i => i.isShow === 0)">
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
                    @click="downLoadFile(item_c.ATTACHMENTPATH,item_c.ATTACHMENTNAME,item_c.EXT)">
              下载
            </Button>
          </div>
          <Button v-if="sxlb_code!='3'" style="position: relative;width: 120px;height: 20px;font-size: 12px"
                  size="small" type="warning"
                  @click="deleteFileMethod(item.titel,item_c.ROWGUID)">删除
          </Button>
        </div>
        <!--只有暂存申报和继续申报进来才能上传材料-->
        <rxUploaderFile v-if="sxlb_code!='3'" :uploadFileName="'上传'" :fileKind="item.titel"
                        ref="uploaderFile" prop="path" :currentFileListSize="item.child_list.length"
                        :success="uploadFile" fileShowUrl="">
        </rxUploaderFile>
      </div>
    </div>
    <Alert class="button">
      <button @click="changeStepTo(2)">上一步</button>
      <!--<button v-if="sxlb_code==='0'" @click="submitAllFileUrl(0)">提交</button>-->
      <button v-if="sxlb_code!='3'" @click="submitAllFileUrl(1)">修改材料</button>
      <button @click="downLoadFileZip()">打包下载</button>
      <!--      <button @click="updateAcceptStatus">下一步</button>-->
    </Alert>
  </div>
</template>
<script>
import rxUploaderFile from '@/components/rongxun/rx-uploader/rx-uploaderFile-gk'
import {saveOrUpdateAcceptMaterial_gk} from '@/api/material/material'
import {responseUtil} from "@/utils/rongxunUtil";
import {updateAcceptStatus} from "../../../../api/port/portApprove";

export default {
  name: "rx-material",
  components: {
    rxUploaderFile
  },
  props: {
    material: [],
    projectNo: '',
    sxlb_code: '',
    jyfw: ''
  },
  mounted() {
    console.log(this.jyfw)
    this.getFullMaterialList()
  },
  data() {
    return {
      visible: false,
      image_src: '',
      formValidate: {},
      allNeedMaterialJyfw: ['1', '2', '3', '4', '5', '6', '7', '8', '9'],
      mtJyfw: ['1', '2', '3'],
      lkJyfw: ['4'],
      hwJyfw: ['5', '6', '7', '8'],
      tlJyfw: ['9']
    }
  },
  methods: {
    downLoadFile(url, fileName, ext) {
      const fileUrl = process.env.VUE_APP_ADMIN_URL + '/portImageView/downLoadFile_gk.json?url=' + url + '&fileName=' + fileName + '.' + ext // 文件的URL地址
      const link = document.createElement('a')
      link.href = fileUrl
      link.setAttribute('download', fileName)
      link.click()
    },
    downLoadFileZip() {
      let gid = this.projectNo;
      const fileUrl = process.env.VUE_APP_ADMIN_URL + '/portImageView/downLoadFileZip_gk.json?gid=' + gid // 文件的URL地址
      const link = document.createElement('a')
      link.href = fileUrl
      link.setAttribute('download', 'zip')
      link.click()
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
    fileShow(img_url, titel, ext) {
      let url = location.origin + '/admin/fileCheckPage?url=' + img_url + '&title=' + titel + '&extType=' + ext
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
    createUUID() {
      return (this.S4() + this.S4() + '-' + this.S4() + '-' + this.S4() + '-' + this.S4() + '-' + this.S4() + this.S4() + this.S4())
    },
    S4() {
      let S4 = (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)
      return S4
    },
    changeStepTo(number) {
      this.$emit('changeStep', number)
    },
    getFullMaterialList() {
      let that = this

      let allJyfwList = that.jyfw.filter((item) => {
        if (this.allNeedMaterialJyfw.indexOf(item) > -1) {
          return item;
        }
      })
      let mtJyfwList = that.jyfw.filter((item) => {
        if (this.mtJyfw.indexOf(item) > -1) {
          return item;
        }
      })
      let lkJyfwList = that.jyfw.filter((item) => {
        if (this.lkJyfw.indexOf(item) > -1) {
          return item;
        }
      })
      let hwJyfwList = that.jyfw.filter((item) => {
        if (this.hwJyfw.indexOf(item) > -1) {
          return item;
        }
      })
      let tlJyfwList = that.jyfw.filter((item) => {
        if (this.tlJyfw.indexOf(item) > -1) {
          return item;
        }
      })

      if (allJyfwList.length == 0) {
        console.log('all')
        for (let i of that.material) {
          i.isShow = 1
        }
      }

      console.log(that.material)

      for (let i of that.material) {
        i.isShow = 1
      }

      let flag = 'no'

      if (mtJyfwList.length != 0) {
        console.log('码头')
        for (let i of that.material) {
          if (i.temp.indexOf('mt') != -1 || i.temp.indexOf('all') != -1) {
            i.isShow = 0
          }
        }
        flag += ',mt'
      }

      if (lkJyfwList.length != 0) {
        console.log('旅客')
        for (let i of that.material) {
          if (i.temp.indexOf('lk') != -1 || i.temp.indexOf('all') != -1 || i.temp.indexOf(flag) != -1) {
            i.isShow = 0
          }
        }
        flag += ',lk'
      }

      if (hwJyfwList.length != 0) {
        console.log('货物')
        for (let i of that.material) {
          if (i.temp.indexOf('hw') != -1 || i.temp.indexOf('all') != -1 || i.temp.indexOf(flag) != -1) {
            i.isShow = 0
          }
        }
        flag += ',hw'
      }

      if (tlJyfwList.length != 0) {
        console.log('拖轮')
        for (let i of that.material) {
          if (i.temp.indexOf('tl') != -1 || i.temp.indexOf('all') != -1 || i.temp.indexOf(flag) != -1) {
            i.isShow = 0
          }
        }
      }

      if (that.jyfw.indexOf('10') != -1 || that.jyfw.indexOf('11') != -1 || that.jyfw.indexOf('12') != -1 ||
        that.jyfw.indexOf('13') != -1 || that.jyfw.indexOf('14') != -1 || that.jyfw.indexOf('17') != -1 ||
        that.jyfw.indexOf('21') != -1 || that.jyfw.indexOf('22') != -1 || that.jyfw.indexOf('23') != -1 ||
        that.jyfw.indexOf('24') != -1 || that.jyfw.indexOf('25') != -1 || that.jyfw.indexOf('26') != -1) {
        console.log('船舶港口服务')
        for (let i of that.material) {
          if (i.titel === '法人授权委托书和法人、代理人身份证复印件' || i.titel === '船舶港口服务单位、理货业务经营人备案申请表') {
            i.isShow = 0
          }
        }
      }

      if (that.jyfw.indexOf('10') != -1) {
        console.log('岸电')
        for (let i of that.material) {
          if (i.titel === '港口为船舶供电的主要设施、设备清单' || i.titel === '岸电设施主要技术参数、检测情况') {
            i.isShow = 0
          }
        }
      }

      if (that.jyfw.indexOf('13') != -1 || that.jyfw.indexOf('14') != -1 || that.jyfw.indexOf('15') != -1) {
        console.log('生活品（食品、酒水、淡水）')
        for (let i of that.material) {
          if (i.titel === '食品卫生或经营许可证' || i.titel === '国境口岸卫生许可证') {
            i.isShow = 0
          }
        }
      }

      if (that.jyfw.indexOf('18') != -1 || that.jyfw.indexOf('19') != -1 || that.jyfw.indexOf('20') != -1) {
        console.log('燃料供应服务')
        for (let i of that.material) {
          if (i.titel === '船舶、车辆或管线一览表' || i.titel === '成品油零售经营许可证' || i.titel === '国际航运船舶油料批准文件' || i.titel === '天然气供应许可证' || i.titel === '危险品经营许可证') {
            i.isShow = 0
          }
        }
      }

      if (that.jyfw.indexOf('11') != -1) {
        console.log('接送')
        for (let i of that.material) {
          if (i.titel === '船舶、车辆情况一览表' || i.titel === '泊位靠泊协议' || i.titel === '港口经营许可证') {
            i.isShow = 0
          }
        }
      }

      if (that.jyfw.indexOf('21') != -1 || that.jyfw.indexOf('22') != -1) {
        console.log('油污水')
        for (let i of that.material) {
          if (i.titel === '船舶、车辆或管线一览表' || i.titel === '船舶污染物转运、处置情况信息表' || i.titel === '船舶油污水、生活垃圾委托处理合同' || i.titel === '《城市生活垃圾　经营性清扫、收集、运办理服务许可证》') {
            i.isShow = 0
          }
        }
      }

      if (that.jyfw.indexOf('23') != -1) {
        console.log('围油栏')
        for (let i of that.material) {
          if (i.titel === '船舶、车辆情况一览表' || i.titel === '常备围油栏的数量、规格及技术说明复印件') {
            i.isShow = 0
          }
        }
      }

      if (that.jyfw.indexOf('24') != -1 || that.jyfw.indexOf('25') != -1) {
        console.log('维修')
        for (let i of that.material) {
          if (i.titel === '港口设施设备和机械清单' || i.titel === '拟租赁港口码头、设施、设备权属证明及合格证书或检测证明' || i.titel === '竣工验收合格证明') {
            i.isShow = 0
          }
        }
      }

      if (that.jyfw.indexOf('26') != -1) {
        console.log('理货')
        for (let i of that.material) {
          if (i.titel === '港航电子数据交换中心和电子口岸数据传输证明' || i.titel === '理货服务收费价格及公示情况证明材料' || i.titel === '质量管理体系认证文件') {
            i.isShow = 0
          }
        }
      }

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
      initData.fileList = that.material;
      initData.projectNo = that.projectNo;
      initData.type = 3
      /* TODO 暂存申报时材料不做必填校验 继续申报时加必填校验 */
      saveOrUpdateAcceptMaterial_gk(initData).then(function (response) {
        responseUtil.dealResponse(that, response, () => {
          if (response.code === "0") {
            if (type == 1) {
              that.$Message.success('附件图片保存成功!');
            } else {
              that.updateAcceptStatus();
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
