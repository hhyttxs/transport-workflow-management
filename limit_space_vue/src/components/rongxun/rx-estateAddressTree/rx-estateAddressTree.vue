<template>
    <div>
        <Select v-model="selectEstateId" clearable filterable placeholder="选择楼盘" style="width: 25%;display: inline-block" :disabled="disabled" @on-change="selectEstate" >
            <Option :value="item.id" :key="item.id" v-for="item,index in estateList" v-if="item.id" :label="item.estateName">
                {{item.estateName}}
                <div style="display:none">{{item.estateName}}-{{item.estateAlias}}</div>
            </Option>
        </Select>
        <Select v-model="selectEstatePedestalId" clearable filterable placeholder="选择楼盘栋座" style="width: 25%;display: inline-block" :disabled="disabled" @on-change="selectEstatePedestal" >
            <Option :value="item.epId" :key="item.epId" v-for="item,index in estatePedestalList" v-if="item.epId">{{item.estatePedestalName}}</Option>
        </Select>
        <Select v-model="selectEstateUnitId" clearable filterable placeholder="选择楼盘单元" style="width: 25%;display: inline-block" :disabled="disabled" @on-change="selectEstateUnit" >
            <Option :value="item.euId" :key="item.euId" v-for="item,index in estateUnitList" v-if="item.euId">{{item.estateUnitName}}</Option>
        </Select>
        <Select ref="selectC" v-model="selectEstateRoomId" clearable filterable placeholder="选择楼盘房间" style="width: 25%;display: inline-block" :disabled="disabled" @on-change="selectData" >
            <Option :value="item.erId" :key="item.erId" v-for="item in estateRoomList" v-if="item.erId">{{item.title}}</Option>
        </Select>
    </div>
</template>
<script>
    import rxSelectTree from '../rx-select/rx-selectTree'
    import { queryEstateList,queryEstatePedestalList,queryEstateUnitList,queryEstateRoomList } from '../../../getData/getData'
    import { getUserId, responseUtil, delayValidateCancel } from '../../../libs/rongxunUtil'
    export default {
        components: {
            rxSelectTree
        },
        props: {
            vmodel: {
            },
            // selectEstateId: {
            //   type: Number,
            // },
            // selectEstatePedestalId: {
            //   type: Number,
            // },
            // selectEstateUnitId: {
            //   type: Number,
            // },
            selectOnChange: {
                type: Function,
                default:() => {}
            },
            disabled: {
                type: Boolean,
                default:false
            },
        },
        data () {
            return {
                selectEstateId:'',
                selectEstatePedestalId:'',
                selectEstateUnitId:'',
                selectEstateRoomId:'',

                estateList:[],
                estatePedestalList:[],//楼盘栋座
                estateUnitList:[],//楼盘单元
                estateRoomList:[],

                estatePedestalMap:[],//楼盘栋座
                estateUnitMap:[],//楼盘单元
                estateRoomMap:[],

                estateUnitItem:{},
                estateRoomItem:{}
                // selectEstateId:'',
                // selectEstatePedestalId:'',
                // selectEstateUnitId:''
            }
        },
        mounted: function () {
            
            this.estateList = []
            this.estatePedestalList = []
            this.estateUnitList = []
            this.estateRoomList = []

            this.estatePedestalMap = []
            this.estateUnitMap = []
            this.estateRoomMap = []
            if(this.vmodel != '-1,-1,-1,-1') {
                let str = this.vmodel.split(",")
                // this.selectEstateId = str[0] ? Number(str[0]) : -1
                // this.selectEstatePedestalId = str[1] ? Number(str[1]) : -1
                // this.selectEstateUnitId = str[2] ? Number(str[2]) : -1
                // this.selectEstateRoomId = str[3] ? Number(str[3]) : -1
                this.initEstateData(str[0] ? Number(str[0]) : -1)
                this.sleep(500)
                this.initEstatePedestalData(str[0] ? Number(str[0]) : -1,str[1] ? Number(str[1]) : -1)
                this.sleep(500)
                this.initEstateUnitData(str[1] ? Number(str[1]) : -1,str[2] ? Number(str[2]) : -1)
                this.sleep(500)
                this.initEstateRoomData(str[2] ? Number(str[2]) : -1,str[3] ? Number(str[3]) : -1)
            }
            this.initEstateData()
        },
        methods: {
            sleep(ms) {
                return new Promise(resolve =>
                    setTimeout(resolve, ms)
                )
            },
            // 获取楼盘地址联想数据
            initEstateData: function (id) {
                var that = this
                let initData = {}
                initData.user_id = getUserId()
                queryEstateList(initData).then(function (response) {
                    responseUtil.dealResponse(that, response, () => {
                        
                        that.estateList = response.data.data.roomHouseList
                        if(that.selectEstateId != id){
                            that.selectEstateId = id
                            if(!that.estateList || that.estateList.length == 0){
                                that.estatePedestalList = []
                                that.estateUnitList = []
                                that.estateRoomList = []
                                that.selectEstatePedestalId = -1
                                that.selectEstateUnitId = -1
                                that.selectEstateRoomId = -1
                            }
                        }
                    })
                })
            },
            // 获取楼盘地址联想数据
            initEstatePedestalData: function (id,id1) {
                var that = this
                let initData = {}
                initData.user_id = getUserId()
                initData.estate_id = id
                queryEstatePedestalList(initData).then(function (response) {
                    responseUtil.dealResponse(that, response, () => {
                        
                        // that.estatePedestalMap = response.data.data.estateMap
                        that.estatePedestalList = response.data.data.roomHouseList
                        if(that.selectEstatePedestalId != id1){
                            that.selectEstatePedestalId = id1
                            if(!that.estatePedestalList || that.estatePedestalList.length == 0){
                                that.estateUnitList = []
                                that.estateRoomList = []
                                that.selectEstateUnitId = -1
                                that.selectEstateRoomId = -1
                            }
                        }
                    })
                })
            },
            // 获取楼盘地址联想数据
            initEstateUnitData: function (id,id1) {
                var that = this
                let initData = {}
                initData.user_id = getUserId()
                initData.estatePedestal_id = id
                queryEstateUnitList(initData).then(function (response) {
                    responseUtil.dealResponse(that, response, () => {
                        
                        // that.estateUnitMap = response.data.data.estateMap
                        that.estateUnitList = response.data.data.roomHouseList
                        if(that.selectEstateUnitId != id1){
                            that.selectEstateUnitId = id1
                            if(!that.estateUnitList || that.estateUnitList.length == 0){
                                that.estateRoomList = []
                                that.selectEstateRoomId = -1
                            }
                            for (var index in that.estateUnitList){
                                if(that.estateUnitList[index].euId == that.selectEstateUnitId){
                                    that.estateUnitItem = that.estateUnitList[index]
                                    break
                                }else {
                                    that.estateUnitItem = {}
                                }
                            }
                        }

                    })
                })
            },
            // 获取楼盘地址联想数据
            initEstateRoomData: function (id,id1) {
                var that = this
                let initData = {}
                initData.user_id = getUserId()
                initData.estateUnit_id = id
                queryEstateRoomList(initData).then(function (response) {
                    responseUtil.dealResponse(that, response, () => {
                        
                        // that.estateRoomMap = response.data.data.estateMap
                        that.estateRoomList = response.data.data.roomHouseList
                        if(that.selectEstateRoomId != id1){
                            that.selectEstateRoomId = id1
                        }
                        // that.setEstateRoomItem()
                        // if(that.selectEstateId){
                        //   that.selectEstate(that.selectEstateId)
                        // }
                    })
                })
            },



            //选择楼盘
            selectEstate(id){
                
                if(!id){
                    id = -1
                }
                this.selectEstateId = id
                this.estatePedestalList = []
                if(id != -1){
                    this.initEstatePedestalData(id)
                }else {
                    this.estatePedestalList = []
                    this.estateUnitList = []
                    this.estateRoomList = []
                    this.selectEstatePedestalId = -1
                    this.selectEstateUnitId = -1
                    this.selectEstateRoomId = -1
                    this.estateUnitItem = {}
                    this.estateRoomItem = {}
                    this.selectOnChange(this.selectEstateId,this.selectEstatePedestalId,this.estateUnitItem,this.estateRoomItem)
                }

            },
            //选择栋座
            selectEstatePedestal(id){
                
                if(!id){
                    id = -1
                }
                this.selectEstatePedestalId = id
                this.estateUnitList = []
                if(id != -1){
                    this.initEstateUnitData(id)
                }else {
                    this.estateUnitList = []
                    this.estateRoomList = []
                    this.selectEstateUnitId = -1
                    this.selectEstateRoomId = -1
                    this.estateUnitItem = {}
                    this.estateRoomItem = {}
                    this.selectOnChange(this.selectEstateId,this.selectEstatePedestalId,this.estateUnitItem,this.estateRoomItem)
                }
            },
            //选择单元
            selectEstateUnit(id){
                
                if(!id){
                    id = -1
                }
                this.selectEstateUnitId = id
                this.estateRoomList = []
                this.initEstateRoomData(id)
                if(id != -1) {
                    this.setEstateUnitItem()
                }else{
                    this.estateRoomList = []
                    this.selectEstateRoomId = -1
                    this.estateUnitItem = {}
                    this.estateRoomItem = {}
                    this.selectOnChange(this.selectEstateId,this.selectEstatePedestalId,this.estateUnitItem,this.estateRoomItem)
                }
            },
            setEstateUnitItem(){
                
                for (var index in this.estateUnitList){
                    if(this.estateUnitList[index].euId == this.selectEstateUnitId){
                        this.estateUnitItem = this.estateUnitList[index]
                        break
                    }else {
                        this.estateUnitItem = {}
                    }
                }
                this.selectOnChange(this.selectEstateId,this.selectEstatePedestalId,this.estateUnitItem,this.estateRoomItem)
            },
            selectData(value){
                
                this.selectEstateRoomId = value
                this.setEstateRoomItem()
            },
            setEstateRoomItem(){
                
                for (var index in this.estateRoomList){
                    if(this.estateRoomList[index].erId == this.selectEstateRoomId){
                        this.estateRoomItem = this.estateRoomList[index]
                        break
                    }else {
                        this.estateRoomItem = {}
                    }
                }
                this.selectOnChange(this.selectEstateId,this.selectEstatePedestalId,this.estateUnitItem,this.estateRoomItem)
            },
        },
        watch:{
            // selectEstateId(value,oldValue){
            //   
            //   if(value != oldValue){
            //     this.selectEstateId = value
            //     this.estatePedestalList = []
            //     this.initEstatePedestalData()
            //   }
            // },
            // selectEstatePedestalId(value,oldValue){
            //   
            //   if(value != oldValue) {
            //     this.selectEstatePedestalId = value
            //     this.estateUnitList = []
            //     this.initEstateUnitData()
            //   }
            // },
            // selectEstateUnitId(value,oldValue){
            //   
            //   if(value != oldValue) {
            //     if(!value){
            //       value = -1
            //     }
            //     this.selectEstateUnitId = value
            //     this.estateRoomList = []
            //     this.initEstateRoomData()
            //   }
            // },
            vmodel(value,oldValue){
                
                if(value != oldValue) {
                    this.vmodel = value
                    let str = value.split(",")
                    // this.selectEstateId = str[0]?Number(str[0]):-1
                    // this.selectEstatePedestalId = str[1]?Number(str[1]):-1
                    // this.selectEstateUnitId = str[2]?Number(str[2]):-1
                    // this.selectEstateRoomId = str[3]?Number(str[3]):-1
                    if(value != '-1,-1,-1,-1'){
                        this.initEstateData(str[0] ? Number(str[0]) : -1)
                        this.sleep(500)
                        this.initEstatePedestalData(str[0] ? Number(str[0]) : -1,str[1] ? Number(str[1]) : -1)
                        this.sleep(500)
                        this.initEstateUnitData(str[1] ? Number(str[1]) : -1,str[2] ? Number(str[2]) : -1)
                        this.sleep(500)
                        this.initEstateRoomData(str[2] ? Number(str[2]) : -1,str[3] ? Number(str[3]) : -1)
                    }else{
                        this.selectEstateId = -1
                        this.selectEstatePedestalId = -1
                        this.selectEstateUnitId = -1
                        this.selectEstateRoomId = -1
                    }
                    // this.$emit('update:vmodel',value)
                    // this.selectData(value)
                }
            }
        }
    }
</script>
<style lang="less">
    @import "../common/rx-common";
    .rx-tree-outline .ivu-icon-ios-arrow-forward:before {
        //content: "\f330";
        //border:1px solid @main-color;
        border:0px solid @main-color;
        content: "\F11F";
        color:@main-color;
    }
    .ivu-select-input {
        display: inline-block;
        height: 32px;
        line-height: 32px;
        padding: 0 24px 0 8px;
        font-size: 14px;
        outline: none;
        border: none;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        color: #515a6e;
        background-color: transparent;
        position: relative;
        cursor: pointer;
    }
</style>
