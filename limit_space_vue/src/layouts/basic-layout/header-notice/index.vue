<template>
    <span class="i-layout-header-trigger i-layout-header-trigger-min i-layout-header-trigger-in layout-notice-wrap">
        <!-- :wide="isMobile" -->
        <Notification :badge-props="badgeProps" class="i-layout-header-notice" :count="count"
                      :class="{ 'i-layout-header-notice-mobile': isMobile }">
            <template #icon>
                <Icon custom="i-icon i-icon-notification"/>
            </template>

        </Notification>
    </span>
</template>
<script>
import {mapState} from 'vuex';

export default {
  name: 'iHeaderNotice',
  mounted() {
  },
  data() {
    return {
      timer: null,
      refreshInterval: 60000,
      badgeProps: { offset: [20, 0] },
      count: 0,
      todoBaseList: [],
    }
  },
  methods: {
    startAutoRefresh() {
      this.timer = setInterval(() => {
        this.queryUserCountList();
      }, this.refreshInterval);
    },
    stopAutoRefresh() {
      clearInterval(this.timer);
      this.timer = null;
    },
    handleNotificationClick(e) {
      this.$router.push({
        path: '/userAccept/userAcceptData_index',
        query: { gid: e.gid }
      });
    }
  }
}
</script>

<style lang="less" scoped>
.layout-notice-wrap {
  :deep(.ivu-select-dropdown) {
    width: 300px;
  }

  :deep(.ivu-dropdown-menu) {
    width: 300px;
  }
}
</style>
