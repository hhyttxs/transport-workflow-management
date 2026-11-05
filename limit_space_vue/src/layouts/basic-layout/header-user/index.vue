<template>
    <Dropdown :trigger="isMobile ? 'click' : 'hover'" class="i-layout-header-user"
        :class="{ 'i-layout-header-user-mobile': isMobile }" @on-click="handleClick">
        <span class="i-layout-header-trigger i-layout-header-trigger-min">
<!--            <Avatar size="small" src="http://oss.f2c.duomaide.com/images/platform/admin.png" />-->
            <span class="i-layout-header-user-name" v-if="!isMobile">{{ userName }}</span>
        </span>
        <template #list>
            <DropdownMenu>
                <template v-if="showcaseLocal">
                    <DropdownItem disabled>体验身份</DropdownItem>
                    <DropdownItem v-for="role in showcaseActors" :key="role.value" :name="'role:'+role.value">{{role.label}}</DropdownItem>
                </template>
            <template v-else>
                <DropdownItem>
                    <Icon type="ios-settings-outline" />
                    <span>{{ $t('basicLayout.user.setting') }}</span>
                </DropdownItem>
                <DropdownItem divided name="logout">
                    <Icon type="ios-log-out" />
                    <span>{{ $t('basicLayout.user.logOut') }}</span>
                </DropdownItem>
            </template>            </DropdownMenu>
        </template>
    </Dropdown>
    <changePassword ref="changePassword"></changePassword>
</template>
<script>
import { mapState, mapActions } from 'vuex';
import changePassword from './changePassword.vue'
import { getUser } from '@/utils/auth'
import {actorChoices,setLocalActor} from '@/local-vehicle/actor';

export default {
    name: 'iHeaderUser',
    components: { changePassword },
    data () {
      return {
        userName: '',
        showcaseLocal: process.env.NODE_ENV === 'development' && process.env.VUE_APP_VEHICLE_LOCAL_TEST === 'true',
        showcaseActors: actorChoices
      }
    },
    mounted: function () {
      this.userName = getUser().name
    },
    computed: {
        // ...mapState('admin/userLogin', [
        //     'userName'
        // ]),
        ...mapState('admin/layout', [
            'isMobile',
            'logoutConfirm'
        ])
    },
    methods: {
        ...mapActions('admin/userLogin', [
            'logout'
        ]),
        handleClick(name) {
            if (this.showcaseLocal) {
                if (!String(name).startsWith('role:')) return;
                const role=String(name).slice(5);setLocalActor(role);
                window.location.hash=({'local-admin':'#/frontPage/yhwhlb_index',preaccept:'#/frontPage/servicePreAccept_index',accept:'#/frontPage/serviceAccept_index',applicant:'#/local/home'})[role];
                window.location.reload();return;
            }
            if (name === 'logout') {
                this.logout();
            } else {
                this.$refs.changePassword.editPassword()
            }
        }
    }
}
</script>

<style lang="less" scoped>
:deep(.ivu-select-dropdown) {
    width: 160px;
}
:deep(.ivu-dropdown-menu) {
    width: 160px;
}
</style>
