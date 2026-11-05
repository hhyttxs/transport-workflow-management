<template>
    <Submenu :name="menu.path">
        <template #title>
            <i-menu-side-title :menu="menu" />
            <Badge class="i-layout-menu-side-badge" v-if="badgeData" v-bind="badgeData" />
        </template>
        <template v-for="(item, index) in menu.children" :key="index">
            <i-menu-side-item class="marginSideItem" v-if="item.children === undefined || !item.children.length" :menu="item" :key="index" />
            <i-menu-side-submenu v-else :menu="item" />
        </template>
    </Submenu>
</template>
<script>
    import iMenuSideItem from './menu-item';
    import iMenuSideTitle from './menu-title';
    import siderMenuBadge from '../mixins/sider-menu-badge';

    export default {
        name: 'iMenuSideSubmenu',
        components: { iMenuSideItem, iMenuSideTitle },
        mixins: [ siderMenuBadge ],
        props: {
            menu: {
                type: Object,
                default () {
                    return {}
                }
            }
        }
    }
</script>

<style lang="less" scoped>
.marginSideItem{
    :deep(.ivu-menu-item){
        margin-left: 0px;
    }
}
</style>
