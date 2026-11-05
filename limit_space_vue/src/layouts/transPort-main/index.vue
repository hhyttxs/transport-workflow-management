<template>
  <router-view #default="{ Component }">
    <keep-alive :include="keepAlive">
      <component :is="Component" v-if="loadRouter" style="" />
    </keep-alive>
  </router-view>
</template>
<script>
  import {mapState} from "vuex";

  export default {
    name: 'transPortMain',
    data() {
      return {
        loadRouter: true
      }
    },
    computed: {
      ...mapState('admin/page', [
        'keepAlive'
      ]),
    }
  }
</script>
