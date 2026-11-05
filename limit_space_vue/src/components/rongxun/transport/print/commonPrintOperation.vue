<template>
  <div class="main noPrint">
    <Row>
      <Col span="24" center style="text-align: center;">
        <Button type="primary" style="margin: 10px" @click="printModal = true">调整打印位置</Button>
        <Button type="primary" style="margin: 10px" @click="doPrint()">打印</Button>
        <Button type="primary" style="margin: 10px" @click="closeWin()">关闭</Button>
      </Col>
    </Row>
    <Modal v-model="printModal" draggable scrollable :mask="false" width="600" :style="{ '--width':'120px' }">
      <template #header>
        <div class="rx-modal-title" style="width:600px">
          <div class="rx-modal-title-text">设置打印位置</div>
        </div>
      </template>
      <div style="height: 35px;"></div>
      <Form>
        <Divider style="font-weight: 500;color: #5b89eb" orientation="left">整体位置调整</Divider>
        <rxLabel label="纵向调节：" width="500" :labelWidth="150">
          <InputNumber v-model="wholeY" @on-change="changeWholeY()" placeholder="纵向距离" style="width: 150px"/>
        </rxLabel>
        <rxLabel label="横向调节：" width="500" :labelWidth="150">
          <InputNumber v-model="wholeX" @on-change="changeWholeX()" placeholder="横向距离" style="width: 150px"/>
        </rxLabel>
        <Divider style="font-weight: 500;color: #5b89eb" orientation="left">单项位置调整</Divider>
        <rxLabel label="单项类别：" width="500" :labelWidth="150">
          <Select v-model="printLocation" class="selectPrintType"
                  style="width: 150px;border-radius:4px;border:1px solid #dcdee2;"
                  @on-change="changePrintTypeDate()"
                  placeholder="请选择单项类别">
            <Option v-for="item in printLocationList" :value="item.SYSPROPS" :key="item.SYSPROPS">{{
              item.PRINTNAME
              }}
            </Option>
          </Select>
        </rxLabel>
        <rxLabel label="纵向调节：" width="500" :labelWidth="150">
          <InputNumber v-model="singleY" placeholder="纵向距离" @on-change="changeSingleY()" style="width: 150px"/>
        </rxLabel>
        <rxLabel label="横向调节：" width="500" :labelWidth="150">
          <InputNumber v-model="singleX" placeholder="横向距离" @on-change="changeSingleX()" style="width: 150px"/>
        </rxLabel>
        <rxLabel label="" width="500" :labelWidth="10">
          <div style="color: red"> 注：1、一个汉字的距离大约为30个像素 <br/>
            2、以原页面打印位置为中心点，向上或向左调节时请在数字前加“-”。
          </div>
        </rxLabel>
      </Form>
      <template #footer>
        <Button type="primary" style="margin: 10px" @click="printModal = false">取消</Button>
        <Button type="primary" style="margin: 10px" @click="resetPrintData()">重置参数</Button>
        <Button type="primary" style="margin: 10px" @click="saveUserPrint()">保存</Button>
      </template>
    </Modal>
  </div>
</template>
<script>
  import rxModal from '@/components/rongxun/rx-modal/rx-modal';
  import rxLabel from '@/components/rongxun/rx-label/rx-label';
  import rxButton from '@/components/rongxun/rx-button/rx-button'
  import $ from 'jquery'
  import {getUser} from "@/utils/auth"
  import {queryPrintInfoList, saveUserPrint, resetPrintData} from "@/api/print/comonPrintApi"
  import {responseUtil} from "@/utils/rongxunUtil";

  export default {
    name: "commonPrintOperation_index",
    components: {
      rxModal, rxLabel, rxButton
    },
    props: {
      sysModel: '',
    },
    data() {
      return {
        printModal: false,
        printLocation: '',
        wholeX: 0,
        wholeY: 0,
        printLocationList: [],
        singleX: 0,
        singleY: 0,
      }
    }
    ,
    mounted: function () {
      this.initPageInfo();
    }
    ,
    methods: {
      initPageInfo() {
        let that = this;
        let userInfo = getUser();
        let initData = {}
        initData.sysModel = this.sysModel;
        initData.userCode = userInfo.code;
        queryPrintInfoList(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === '0') {
              console.log(response.data)
              response.data.forEach((item) => {
                //初始化页面位置
                let SYSPROPS = item.sysProps;
                let propsLocationX = item.propsLocationX;
                let propsLocationY = item.propsLocationY;
                $("#" + SYSPROPS).css({'position': 'relative', "left": propsLocationX + "px"});
                $("#" + SYSPROPS).css({'position': 'relative', "top": propsLocationY + "px"});
                //初始化页面参数
                if (SYSPROPS != 'wholePageSetup') {
                  $("#" + SYSPROPS).addClass('showPrintElement')
                  that.printLocationList.push(item)
                } else {
                  that.wholeX = propsLocationX
                  that.wholeY = propsLocationY
                }
              });
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      saveUserPrint() {
        let that = this;
        let userInfo = getUser();
        let initData = {}
        initData.sysModel = this.sysModel;
        initData.userCode = userInfo.code;
        initData.wholeX = this.wholeX;
        initData.wholeY = this.wholeY;
        initData.printLocationList = this.printLocationList;
        saveUserPrint(initData).then(function (response) {
          responseUtil.dealResponse(that, response, () => {
            if (response.code === '0') {
              that.$Message.success('保存成功！');
              that.initPageInfo();
            } else {
              responseUtil.alertErrMsg(response.msg)
            }
          })
        })
      },
      resetPrintData() {
        this.$Modal.confirm({
          title: '友情提示',
          content: '<p>参数重置后无法找回，确认重置？</p>',
          onOk: () => {
            let that = this
            let userInfo = getUser();
            let initData = {}
            initData.sysModel = this.sysModel;
            initData.userCode = userInfo.code;
            resetPrintData(initData).then(function (response) {
              responseUtil.dealResponse(that, response, () => {
                that.singleX = 0
                that.singleY = 0
                that.$Notice.success({
                  title: '参数重置',
                  desc: '重置成功'
                });
                that.initPageInfo();
              })
            })
          }
        })
      },
      doPrint() {
        this.$emit('print-click')
        window.print();
      },
      closeWin() {
        window.close()
      },
      cancel() {
        this.printModal = false
      },
      changeWholeY() {
        $("#wholePageSetup").css({'position': 'relative', "top": this.wholeY + "px"});
      },
      changeWholeX() {
        $("#wholePageSetup").css({'position': 'relative', "left": this.wholeX + "px"});
      },
      changeSingleX() {
        let printLocation = this.printLocation;
        if (printLocation != '' && printLocation != null && printLocation != undefined) {
          //改变页面位置
          $("#" + printLocation).css({'position': 'relative', "left": this.singleX + "px"});
          //刷新页面数据
          this.printLocationList.forEach((item) => {
            if (printLocation == item.sysProps) item.propsLocationX = this.singleX

          });
        }

      },
      changeSingleY() {
        let printLocation = this.printLocation;
        if (printLocation != '' && printLocation != null && printLocation != undefined) {
          //改变页面位置
          $("#" + printLocation).css({'position': 'relative', "top": this.singleY + "px"});
          //刷新页面数据
          this.printLocationList.forEach((item) => {
            if (printLocation == item.sysProps) item.propsLocationY = this.singleY
          });
        }

      },
      changePrintTypeDate() {
        let printLocation = this.printLocation;
        this.printLocationList.forEach((item) => {
          if (printLocation == item.sysProps) {
            this.singleX = item.propsLocationX;
            this.singleY = item.propsLocationY;
          }
        });
      }
    }
  }
</script>
<style lang="less">
  @import "../../../../components/rongxun/rx-modal/rx-modal.less";
  @import "../../../../components/rongxun/rx-label/rx-label.less";
  @import "../../../../components/rongxun/transport/rx-information/information.less";
  @import "../../../../components/rongxun/rx-button/rx-button.less";
</style>
<style>
  @media print {
    #wholePageSetup {
      visibility: hidden;
      -webkit-font-smoothing: antialiased;
      -moz-osx-font-smoothing: grayscale;
    }

    #wholePageSetup input {
      border: none;
    }

    #wholePageSetup textarea {
      border: none;
    }

    #wholePageSetup select {
      border: none;
    }

    .showPrintElement {
      visibility: visible;
      font-family: SimSun;
      font-size: 18pt;
    }
  }
</style>
