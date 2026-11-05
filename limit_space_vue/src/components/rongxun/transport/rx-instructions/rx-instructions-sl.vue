<!--操作流程 -- 阅读须知-->
<template>
  <div class="rx-instructions-body">
    <Alert type="warning">{{ prompt }}</Alert>
    <Tabs value="tab">
      <Tab-pane label="申报须知" name="tab">
        <ul class="rx-instructions-body-ul">
          <li v-for="(item,index) in instructionsInfo" :key="index">{{ item.title }}</li>
        </ul>
      </Tab-pane>
    </Tabs>
    <Alert class="rx-instructions-body-error" type="error">{{ label }}</Alert>
    <Alert class="button">
      <button @click="changeStepTo(2)">下一步</button>
    </Alert>
  </div>
</template>

<script>
export default {
  name: 'rx-instructions',
  props: {
    prompt: {
      default: '提醒:您本次网上申报的办件，工作人员将在预受理之后联系您。'
    },
    label: {
      default: '注:本人对办理该事项所提交的申报材料实质内容的真实性负责。'
    },
    instructions: {
      default: []
    }
  },
  data () {
    return {
      instructionsInfo: [
        { title: '1、界面功能：本部分功能适用于办理：小型客船水路运输业务经营许可，相同事项不可重复申报。' },
        {
          title: '2、申报流程：请按界面导航，顺序填报（上传）信息。填写申报信息后，需要点击“草稿”按钮否则信息不会保存。' +
            '上传材料前可在菜单“模版表格下载”中选择下载模版，自查准确无误后，提交申报信息，信息提交后不可修改，' +
            '可在“进度查看”菜单中查看当前办件状态。填写申报信息后选择草稿，或者提交申报前选择草稿的，在菜单“继续申报”中可继续办理。'
        },
        { title: '3、进度查看：申报信息提交成功后，可以在左侧选择“进度查看”菜单，查看本次已申报信息进度' },
        { title: '4、继续申报：属于对暂存申报信息和退回申报信息的修改和补充,直至提交申报的功能，如不再申报可点击“撤销申报”，撤销后数据无法恢复。' },
        { title: '5、如果出现页面卡顿或操作无反应等情况,请点击左上角“刷新”按钮刷新页面' },
      ]
    }
  },
  methods: {
    changeStepTo (number) {
      this.$emit('changeStep', number)
    }
  }
}

</script>

<style scoped lang="less">
.rx-instructions-body {
  min-height: calc(100vh - 136px);
  background: white;
  flex-grow: 1;
  padding: 15px;

  .rx-instructions-body-ul {
    padding: 0 20px;
  }

  .rx-instructions-body-ul > li {
    line-height: 30px;
  }

  .rx-instructions-body-error {
    margin-top: 20px;
    text-align: center;
    color: #DC143C;
  }

  .button {
    margin-top: 20px;
    height: 50px;
    border: none;
    display: flex;
    align-items: center;
    justify-content: center;

    button {
      margin: 0 10px;
      background: lightgreen;
      border: none;
      width: 70px;
      height: 30px
    }

    button:hover {
      cursor: pointer;
    }
  }
}
</style>
