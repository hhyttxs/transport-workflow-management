<style lang="less">
</style>
<template>
  <div :style="styleDiv">
    <Upload action="" :before-upload="handleBeforeUpload" accept=".xls, .xlsx">
      <rxButton type="primary" size="large" :loading="uploadLoading" @click="handleUploadFile">{{title}}</rxButton>
    </Upload>
  </div>
</template>
<script>
import excel from '@/libs/excel'
import XLSX from 'xlsx'
import rxButton from '../rx-button/rx-button'
export default {
  name: 'upload-excel',
  components: {
    rxButton
  },
  data () {
    return {
      uploadLoading: false,
      file: null
    }
  },
  props: {
    success: {
      type: Function,
      default: () => {}
    },
    title: {
      type: String,
      default: '上传文件'
    },
    styleDiv: {}
  },
  methods: {
    initUpload () {
      this.file = null
      this.uploadLoading = false
    },
    handleUploadFile () {
      this.initUpload()
    },
    handleBeforeUpload (file) {
      const fileExt = file.name.split('.').pop().toLocaleLowerCase()
      if (fileExt === 'xlsx' || fileExt === 'xls') {
        this.readFile(file)
        this.file = file
      } else {
        this.$Notice.warning({
          title: '文件类型错误',
          desc: '文件：' + file.name + '不是EXCEL文件，请选择后缀为.xlsx或者.xls的EXCEL文件。'
        })
      }
      return false
    },
    // 读取文件
    readFile (file) {
      var that = this
      that.$Spin.show()
      const reader = new FileReader()
      FileReader.prototype.readAsBinaryString = function (file) {
        var binary = ''
        var wb // 读取完成的数据
        var outdata // 你需要的数据
        var reader = new FileReader()
        reader.onload = function (e) {
          // 读取成Uint8Array，再转换为Unicode编码（Unicode占两个字节）
          var bytes = new Uint8Array(reader.result)
          var length = bytes.byteLength
          for (var i = 0; i < length; i++) {
            binary += String.fromCharCode(bytes[i])
          }
          // 接下来就是xlsx了，具体可看api
          wb = XLSX.read(binary, { type: 'binary' })
          let sheetNames = wb.SheetNames // 工作表名称集合
          let excelDataList = []
          for (var i = 0; i < sheetNames.length; i++) {
            let excelData = {}
            var cardActive = sheetNames[i]
            var worksheet = wb.Sheets[cardActive]
            var tableData = XLSX.utils.sheet_to_json(worksheet)
            excelData.sheetName = cardActive
            excelData.tableData = tableData
            excelDataList.push(excelData)
          }
          // const ws1 = XLSX.utils.sheet_to_slk(wb.Sheets[cardActive]) // 输出表格对应位置是什么值
          // const ws2 = XLSX.utils.sheet_to_html(wb.Sheets[cardActive]) // 生成HTML输出
          // const ws3 = XLSX.utils.sheet_to_csv(wb.Sheets[cardActive]) // 生成分隔符分隔值输出
          // const ws4 = XLSX.utils.sheet_to_formulae(wb.Sheets[cardActive]) // 生成公式列表（具有值回退）
          // const ws5 = XLSX.utils.sheet_to_txt(wb.Sheets[cardActive]) // 生成UTF16格式的文本
          that.success(excelDataList)
        }
        reader.readAsArrayBuffer(file)
      }
      reader.readAsBinaryString(file)
      reader.onloadstart = e => {
        this.$Spin.show()
      }
      reader.onerror = e => {
        this.$Spin.hide()
        this.$Message.error('文件读取出错')
      }
    }
  },
  created () {

  },
  mounted () {
  }
}
</script>
