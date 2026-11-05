import {getUser} from '@/utils/auth';

// The teacher queue services expect DatePicker values serialized as ISO dates.
export function workflowDate(value, endOfDay = false) {
  if (!value) return '';
  const date = new Date(value);
  if (Number.isNaN(date.getTime())) throw new Error('请选择有效的申报日期');
  if (endOfDay) date.setHours(23, 59, 59, 999);
  return date.toISOString();
}

export async function loadWorkflowQueue(vm, query) {
  const requestId = (vm._teacherQueueRequestId || 0) + 1;
  vm._teacherQueueRequestId = requestId;
  const user = getUser();
  if (!user || !user.userType || !user.xzjgCode || !user.xzqhCode) {
    vm.tableData = [];
    vm.page.pageCount = 0;
    vm.page.totalPage = 0;
    vm.$Message.error('当前账号缺少账户类型、机构或行政区划，请联系管理员');
    return;
  }
  try {
    const payload = {
      ...vm.initParam,
      startTime: workflowDate(vm.initParam.startTime),
      endTime: workflowDate(vm.initParam.endTime, true),
      currentPage: vm.page.currentPage,
      numberPage: vm.page.pageSize,
      userType: user.userType,
      xzjg: user.xzjgCode,
      xzqh: user.xzqhCode
    };
    if (payload.startTime && payload.endTime &&
        new Date(payload.startTime) > new Date(payload.endTime)) {
      throw new Error('申报起日不能晚于申报止日');
    }
    const response = await query(payload);
    if (vm._teacherQueueRequestId !== requestId) return;
    if (!response || String(response.code) !== '0') {
      throw new Error((response && response.msg) || '待办查询失败');
    }
    const data = response.data;
    if (!data || !data.data || !Array.isArray(data.data.result)) {
      throw new Error('待办查询返回的数据格式不正确');
    }
    vm.tableData = data.data.result;
    vm.page.pageCount = Number(data.pageCount) || 0;
    vm.page.totalPage = Number(data.totalPage) || 0;
  } catch (error) {
    if (vm._teacherQueueRequestId !== requestId) return;
    vm.tableData = [];
    vm.page.pageCount = 0;
    vm.page.totalPage = 0;
    vm.$Message.error(error.message || error.msg || '待办查询失败');
  }
}

export function navigateWorkflow(vm, destination) {
  const route = vm.$router.getRoutes().find(item => item.path === destination.path);
  if (!route) {
    vm.$Message.warning('该业务的办理或详情页面尚未提供，当前只能查询待办列表');
    return false;
  }
  vm.$router.push(destination);
  return true;
}
