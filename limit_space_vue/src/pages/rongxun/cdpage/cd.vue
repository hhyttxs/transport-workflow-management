<template>
  <Card title="菜单目录"><Alert v-if="error" type="error">{{error}}</Alert><Tree :data="data"></Tree></Card>
</template>
<script>
import {resultPayload} from '@/utils/resultPayload';
import { getTreeMenuList } from '@/api/hy/cd'
export default {
  data () {
    return {
      error: '',
      data: []
    }
  },
  mounted () {
    this.loadTreeData()
  },

  methods: {
    loadTreeData () {
      getTreeMenuList({}).then(res => {
        const result=resultPayload(res);
        if(String(result.code)!=="0") throw new Error(result.msg);
        this.data = result.data || []
      }).catch(error => {
        this.error=error.message
      })
    }
  }
}
</script>
