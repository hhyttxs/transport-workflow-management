<template>
  <div class="workflow-queue-table">
    <Table border stripe :columns="columns" :data="tableData">
      <template #workflowAction="{ row, index }">
        <Button
          v-for="option in actionButtons"
          :key="option.name"
          size="small"
          style="margin-right: 4px"
          :disabled="option.isShowButton !== 'okButton' && row.bjzt !== option.isShowButton"
          @click="option.click({row, index})"
        >{{ option.label }}</Button>
      </template>
    </Table>
    <Page
      style="margin-top: 12px"
      :total="page.pageCount"
      :model-value="page.currentPage"
      :page-size="page.pageSize"
      :page-size-opts="[10, 20, 30, 50]"
      show-total show-elevator show-sizer
      @on-change="onChange"
      @on-page-size-change="pageChange"
    />
  </div>
</template>
<script>
export default {
  name: 'WorkflowQueueTable',
  props: {
    tableData: {type: Array, default: () => []},
    columnsInfo: {type: Array, required: true},
    page: {type: Object, required: true},
    onChange: {type: Function, required: true},
    pageChange: {type: Function, required: true}
  },
  data() { return {additionalHeight: 0}; },
  computed: {
    columns() {
      return this.columnsInfo.map(column => {
        if (!column.buttons) return column;
        const {buttons, ...definition} = column;
        return {...definition, slot: 'workflowAction'};
      });
    },
    actionButtons() {
      const action = this.columnsInfo.find(column => column.buttons);
      return action ? action.buttons : [];
    }
  }
};
</script>
