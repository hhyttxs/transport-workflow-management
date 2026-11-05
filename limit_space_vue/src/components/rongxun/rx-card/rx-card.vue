<script setup>
import { ref, reactive } from "vue";

const props = defineProps({
  title: {
    type: String
  },
  downBorder: {
    type: Boolean,
    default: true,
  }
})
</script>


<template>
  <div :class="props.downBorder ? 'rx-card-wrap' : 'rx-card-wrap-no-border'" style="margin-top: 15px">
    <div v-if="props.title">
      <div class="rx-card-header-greenLine"></div>
      <div class="rx-card-header">{{ props.title }}</div>
    </div>
    <div class="clear-float"></div>
    <slot></slot>
  </div>
</template>


<style lang="less" scoped>
@import "./rx-card.less";

</style>