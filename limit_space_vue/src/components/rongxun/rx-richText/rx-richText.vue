<template>
  <div>
    <textarea class='tinymce-textarea' :id="tinymceEditerId"></textarea>
  </div>
</template>

<script>
  import { picUpload } from '../../../getData/getData'
  import { responseUtil } from '../../../libs/rongxunUtil'
  import tinymce from 'tinymce';
export default {
    name: "rx-richText",
    props:{
      richText:{
        type:String,
        default:""
      },
      tinymceEditerId:{
        type:String,
        default:"tinymceEditer"
      },
    },
    data(){
      return {
          flag:true
      }
    },
    mounted: function () {
      tinymce.baseURL = 'tinymce'
      this.init();
    },
    methods:{
      //富文本初始化
      init() {
        var that = this
        if (tinymce.get(that.tinymceEditerId) != null) {
          tinymce.get(that.tinymceEditerId).destroy();
        }
        this.$nextTick(() => {
          let vm = this;
          let height = document.body.offsetHeight - 100;
          tinymce.init({
            selector: '#'+that.tinymceEditerId,
            branding: false,
            images_upload_url: '123',
            images_upload_handler: function (blobInfo, success, failure) {
                
              var file = blobInfo.blob()
              let data = new FormData()
              data.append("file", file);
              data.append("fileName", file.name);
              data.append("path", vm.imgUrlPrefix + "tmp");
              picUpload(data).then(function (res) {
                responseUtil.dealResponseNative(vm, res,
                  () => {
                    success(res.data.data.path);
                  },
                  () => {
                    failure(res.data.msg)
                  })
              })
            },
            elementpath: true,
            height: height,
            language: 'zh_CN.GB2312',
            // menubar: 'edit insert view format table tools',
            plugins: [
              'advlist autolink lists link image charmap print preview hr anchor pagebreak imagetools',
              'searchreplace visualblocks visualchars code fullpage',
              'insertdatetime media nonbreaking save table contextmenu directionality',
              'emoticons paste textcolor colorpicker textpattern imagetools codesample'
            ],
            toolbar1: 'newnote print preview | undo redo | insert | styleselect | forecolor backcolor bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image emoticons media codesample',
            toolbar2: 'imageSelector',
            autosave_interval: '20s',
            image_advtab: true,
            table_default_styles: {
              width: '100%',
              borderCollapse: 'collapse'
            },
            setup: function (editor) {
              editor.on('init', function (e) {
                // if (localStorage.editorContent) {
                  tinymce.get(that.tinymceEditerId).setContent(that.richText);
                // }
              });
              editor.on('input undo redo execCommand', function (e) {
                  that.flag=false;
                  // localStorage.editorContent = tinymce.get(that.tinymceEditerId).getContent();
                //   that.richText = tinymce.get(that.tinymceEditerId).getContent();
                  that.$emit('update:richText',tinymce.get(that.tinymceEditerId).getContent())
              });
            }
          });
        });
      },
    },
    watch: {
        richText(newData, oldVal) {
            if(this.flag){
                tinymce.get(this.tinymceEditerId).setContent(newData);
            }
            this.flag=true;
        },
    }
}
</script>

<style lang="less">
/*@import "./rx-tabs.less";*/
</style>
