<script setup>
import { useStore } from 'vuex'

const store = useStore()

const props = defineProps({
  click: {
    type: Function,
    default: () => { }
  }
})
</script>


<template>
  <div class="wrap">
    <Button type="primary" size="large" :disabled="store.state.admin.auth.buttonStatus" class="rx-button"
      @click="(e) => props.click(e)">
      <slot></slot>
    </Button>
  </div>
</template>


<style lang="less" scoped>
@import "./rx-button.less";
</style>