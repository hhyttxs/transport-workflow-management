<template>

  <Drawer v-model="drawerFlag" :width="width" :style="{ 'z-index': zIndex }">
    <!--上方 标题栏 开始-->
    <template #header>
      <div class="domed-drawer-title">
        <div class="domed-drawer-title-text">{{ title }}</div>
      </div>
    </template>
    <!--上方 标题栏 结束-->
    <slot>
      默认的内容
    </slot>

    <div v-if="hasFooter" style="height: 48px;"></div>

    <div class="rx-drawer-footer" v-if="hasFooter">
      <div class="rx-drawer-footer-right"></div>
      <slot name="footer">
        <rxButton v-for="btn in buttonArray" :click="btn.click">{{ btn.title }}</rxButton>
      </slot>
    </div>
  </Drawer>
</template>

<script>
import rxTabs from "../rx-tabs/rx-tabs"
import rxButton from "../rx-button/rx-button"

export default {
  name: "rongxun-drawer",
  components: {
    rxTabs,
    rxButton
  },
  props: {
    hasFooter: {
      type: Boolean,
      default: true,
    },
    zIndex: {
      type: Number,
      default: 2000
    },
    buttonArray: {
      type: Array
    },
    title: {
      type: String,
      default: "标题"
    },
    width: {
      type: Number,
      default: 640,
    }
  },
  data() {
    return {
      drawerFlag: false,

    }
  },
  mounted() {
  },
  methods: {
    /**
     * 打开抽屉
     */
    show() {
      this.drawerFlag = true;
    },
    close() {
      this.drawerFlag = false;
    },
  }

}
</script>

<style>
.ivu-drawer-body {
  position: static !important;
}

.rx-drawer-footer {
  width: 100% !important;
  position: absolute !important;
  bottom: 0 !important;
  left: 0 !important;
  border-top: 1px solid #e8e8e8 !important;
  padding: 10px 16px !important;
  text-align: right !important;
  background: #fff !important;
  margin-left: 0px !important;
  z-index: 1000;
}

/*@import "./rx-drawer.less";*/
</style>
