<template>
    <Card :shadow="shadow" class="infor-card-wrapper" :padding="0"  :style="{background: color}">
        <div class="content-con">
            <div class="left-area" :style="{width: leftWidth}" v-if="hasLeft">
                    <slot name="leftArear"></slot>
            </div>
            <div class="right-area" :style="{width: rightWidth}">
                    <slot></slot>
            </div>
            <div style="clear:both;"></div>
        </div>
    </Card>
</template>
<script>
    export default {
        name: 'rx-infoCard',
        props: {
            left: {
                type: [Number,String],
                default: 20
            },
            clickFunc: {
                type: Function,
                default: ()=>{}
            },
            hasLeft: {
                type: Boolean,
                default: true
            },
            color: {
                type: String,
                default: '#F0FCE5'
            },
            shadow: {
                type: Boolean,
                default: false
            }
        },
        computed: {
            leftWidth () {
                return `${this.left}%`
            },
            rightWidth () {
                if (this.hasLeft) {
                    return `${100 - this.left}%`
                }else{
                    return '100%'
                }
            }
        }
    }
</script>

<style  lang="less">
    .size{
        width: 100%;
        height: 100%;
    }
    .middle-center{
        display: table-cell;
        vertical-align: middle;
        text-align: center;
    }
    .infor-card-wrapper{
        .size;
        overflow: hidden;
        .ivu-card-body{
            .size;
        }
        .content-con{
            .size;
            display: table;
            .left-area{
                .middle-center;
                border-right: 2px solid #fff;
            }
            .right-area{
                .middle-center;
            }
        }
    }
</style>
