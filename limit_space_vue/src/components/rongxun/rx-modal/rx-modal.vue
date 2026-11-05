<template>
  <div>
    <!-- @click="this.close" -->
    <div class="markShow" v-if="modalFlag" :style="{ 'z-index': zIndex }"></div>
    <Modal v-model="modalFlag" :draggable="true" :width="width" :style="{ '--width': width - 40 + 'px' }"
       :scrollable="scrollableBool" @on-visible-change="defaultVisibleChange"
      @on-cancel="(e) => onCloseConfirm(e)" :z-index="zIndex" :footer-hide="hasFooter" :fullscreen="fullscreencheck" :mask-closable="maskclosable" :closable="closable">
      <template #header>
        <div class="rx-modal-title" :style="{ width: width + 'px' }">
          <div class="rx-modal-title-text">{{ title }}</div>
        </div>
      </template>
      <div style="height: 35px;" v-if="!fullscreencheck"></div>
      <slot></slot>
      <template #footer>
        <slot name="footerSlot">
          <div >
            <rxButton v-for="btn in buttonArray" :click="btn.click">{{ btn.title }}</rxButton>
          </div>
          <div class="clear-float"></div>
        </slot>
      </template>
    </Modal>
<!--    <Modal v-model="confirmFlag" :z-index="zIndex + 5" :style="{ '&#45;&#45;width': '480px' }">-->
<!--      <template #header>-->
<!--        <div class="rx-modal-title" style="width: 520px;">-->
<!--          <div class="rx-modal-title-text">确认退出</div>-->
<!--        </div>-->
<!--      </template>-->
<!--      <div style="height: 35px;"></div>-->
<!--      <template #footer>-->
<!--        <div>-->
<!--          <div>-->
<!--            <rxButton :click="confirmCancel">确认</rxButton>-->
<!--            <rxButton :click="notCancel">取消</rxButton>-->
<!--          </div>-->
<!--          <div class="clear-float"></div>-->
<!--        </div>-->
<!--      </template>-->
<!--      确定关闭吗?-->
<!--    </Modal>-->
  </div>
</template>

<script>
import rxButton from '../rx-button/rx-button'
import { validateCancelNow } from '@/utils/rongxunUtil'
export default {
  name: 'rx-modal',
  components: {
    rxButton
  },
  props: {
    title: {
      type: String,
      default: '标题'
    },
    buttonArray: {
      type: Array
    },
    width: {
      type: Number,
      default: 800
    },
    visibleChange: {
      type: Function
    },
    needCancelConfirm: {
      type: Boolean,
      default: false
    },
    zIndex: {
      type: Number,
      default: 1300
    },
    fullscreencheck: {
      type: Boolean,
      default: false
    },
    hasFooter: {
      type: Boolean,
      default: false
    },
    maskclosable: {
      type: Boolean,
      default: true
    },
    closable: {
      type: Boolean,
      default: true
    },
    scrollableBool:{
      type: Boolean,
      default: true
    },
    validatedForm: {}
  },
  data() {
    return {
      modalFlag: false,
      confirmFlag: false,
      canClose: false,
      clickMask: false,
      type: '',
    }
  },
  methods: {
    show(type) {
      this.type = type
      this.canClose = false
      this.modalFlag = true
      this.$emit('openHardOrSoft', type)
    },
    close() {
      if (this.validatedForm) validateCancelNow(this.validatedForm)
      this.clickMask = true
      this.modalFlag = false
    },
    defaultVisibleChange(state) {
      if (this.visibleChange) {
        this.visibleChange(state)
        return
      }

      if (!this.needCancelConfirm) return
      if (this.canClose) return
      if (state) return
      if (!this.clickMask) return

      this.modalFlag = true
      this.confirmFlag = true
      this.clickMask = false

    },
    confirmCancel() {
      this.canClose = true
      this.confirmFlag = false
      this.modalFlag = false
    },
    notCancel() {
      this.confirmFlag = false
    },
    onCloseConfirm(e) {
      if (this.validatedForm) validateCancelNow(this.validatedForm)

      // 我不清楚原理是什么，但是这段代码是好用的
      // 如果去掉了，在点击右上角的x号关闭弹窗时，确认的弹窗会被原弹窗挡住
      if (this.needCancelConfirm) window.event.stopPropagation()
    }
  }
}
</script>

<style lang="less">
@import "../rx-button/rx-button.less";

.ivu-modal-close {
  z-index: 1001;
  font-size: 12px;
  position: fixed;
  overflow: hidden;
  cursor: pointer;
  right: auto;
  top: auto;
  margin-left: var(--width);
}

/*.ivu-modal-footer {*/
/*border-top: 0px;*/
/*padding: 12px 18px 12px 18px;*/
/*text-align: right;*/
/*position: sticky;*/
/*bottom: 0;*/
/*}*/
</style>
