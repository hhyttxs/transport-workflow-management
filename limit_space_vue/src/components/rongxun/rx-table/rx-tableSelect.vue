<template >
  <div class="renter">
    <div class="renter-operation" v-show="buttonArray != undefined && buttonArray.length > 0">
      <ul class="operation-capacity" :style="{'z-index':(isSelect?200:''),height:(isSelect?height:''),'margin-top':(marginTop+'px')}"
          @mouseleave="outLineMouseLeave()">
        <template v-for="(item) in buttonArray">
          <template v-if="item.click!=undefined">
            <li @mouseover="selectItemMouseover()" @mouseout="out()" @click.stop="item.click(params)">{{item.label}}</li>
          </template>
          <template v-else>
            <li @mouseover="selectItemMouseover()" @mouseout="out()">{{item.label}}</li>
          </template>
        </template>
        <li :class="[isSelect?'daoSanJiaoWhite':'daoSanJiao']"></li>
      </ul>
    </div>
  </div>
</template>

<script>
    export default {
      name: "rx-tableSelect",
      inject: ['table'],
      props:{
        buttonArray:{
          type:Array
        },
        params:{
          type:Object
        },
        hasTab:{
          type:Boolean,
          default:true
        }
      },
      data(){
        return {
          isSelect:false,
          height:'',
          marginTop:4
        }
      },
      methods: {
        //移动出整个下拉搜索的轮廓的时候 才触发这个方法 使下拉菜单消失
        //否则会导致下拉菜单的height变化  而触发 table的高度变化 引起页面的抖动
        outLineMouseLeave(){
          this.isSelect = false
        },
        selectItemMouseover: function () {
          this.isSelect = true;
          var height = this.buttonArray.length*30
          this.height = height+'px';
            if(this.hasTab){
                if(this.needUpperSelect()){
                    this.marginTop = height * (-1)  +15
                    // this.changeButtonArraySeq()
                }
            }

        },
        out: function() {
            if (this.hasTab){
                 this.marginTop = 0
                if(this.needUpperSelect()){
                    // this.changeButtonArraySeq()
                }
            }

        },
        //判断是否需要向上飘
        needUpperSelect(){
          var height = this.buttonArray.length*30
          var iveTableRowEl = this.findUpperEl(this.$el,this.getSelectClazz())
          var ivuTableWrapper = this.findUpperEl(this.$el,this.getSelectWrapperClazz())

          return this.needUpperSelectByElement(iveTableRowEl,ivuTableWrapper,height)
        },

        //通过元素判断是否需要向上飘
        needUpperSelectByElement(row , wrapper,height){
          var offsetTop = row.offsetTop
          var wraperHeight = wrapper.scrollHeight
          if(this.table.needParent){
            var oldWraper =   this.findUpperEl(this.$el,"ivu-table-wrapper")
            wraperHeight = Math.max(wrapper.parentNode.scrollHeight,oldWraper.scrollHeight)
          }

          //如果向上顶 上面的高度也不够
          if(offsetTop < height +30 ) return false

          //下面的高度不够
          if(wraperHeight - offsetTop < height +30) return true

          return false
        },

        //通过元素和类型 找到上级的元素
        findUpperEl(el,clazzName){
          if(!el ) return null
          var className = el.className || ''
          if(className.indexOf(clazzName) != -1) return el;
          return this.findUpperEl(el.parentNode,clazzName)
        },

        getSelectClazz(){
          var clazz =  this.table.selectClazz  ||  'ivu-table-row'
          return clazz
        },
        getSelectWrapperClazz(){
          var clazz =  this.table.selectWraperClazz  ||  'ivu-table-tbody'
          return clazz
        },

        changeButtonArraySeq(){
          var temp = []
          for(var i = this.buttonArray.length - 1 ; i>=0 ; i--){
            temp.push(this.buttonArray[i])
          }
          this.buttonArray.splice(0,this.buttonArray.length)
          for(var i = 0 ; i < temp.length ; i++){
            this.buttonArray.push(temp[i])
          }
        }
      }
    }
</script>
