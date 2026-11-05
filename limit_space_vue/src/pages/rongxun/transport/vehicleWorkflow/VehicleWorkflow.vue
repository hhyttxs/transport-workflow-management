<template>
  <Card title="道路运输证核发 · 单车申请">
    <div class="vehicle-toolbar">
      <Button :disabled="busy" @click="newDraft">新建申请</Button>
      <Button :disabled="busy" @click="reload">刷新</Button>
      <Button :disabled="busy" @click="$router.push('/frontPage/servicePreAccept_index')">预受理待办</Button>
      <Button :disabled="busy" @click="$router.push('/frontPage/serviceAccept_index')">受理待办</Button>
    </div>
    <Alert v-if="error" type="error" show-icon>{{ error }}</Alert>
    <template v-if="!hasRecord">
      <h3>我的草稿及退回申请</h3>
      <Table :columns="columns" :data="drafts" :loading="busy">
        <template #action="{row}"><Button size="small" :disabled="busy" @click="openDraft(row)">继续申请</Button></template>
      </Table>
      <Page :model-value="page" :total="total" :page-size="10" @on-change="changePage" />
    </template>
    <Alert v-if="record.blockedReason" type="warning" show-icon>{{ record.blockedReason }}</Alert>
    <p v-if="hasRecord">申请编号：{{ record.letterGid }}　状态：{{ stateLabel(record.status) }}</p>
    <p v-if="record.formalId">正式车辆主键：{{ record.formalId }}　道路运输证号：{{ record.permitNumber }}</p>
    <Form ref="form" :model="form" :label-width="120" :disabled="busy || !editable">
      <FormItem label="业户">
        <Select v-if="editable" v-model="form.enterpriseId" filterable :disabled="hasRecord">
          <Option v-for="item in options.enterprises" :key="item.value" :value="item.value">{{ item.label }}</Option>
        </Select>
        <Input v-else :model-value="form.enterpriseId" readonly />
      </FormItem>
      <FormItem label="业务类型">
        <Select v-if="editable" v-model="form.businessType" filterable>
          <Option v-for="item in options.businessTypes" :key="item.value" :value="item.value">{{ item.label }}</Option>
        </Select>
        <Input v-else :model-value="record.businessTitle || form.businessType" readonly />
      </FormItem>
      <FormItem v-for="item in fields" :key="item.key" :label="item.label">
        <Input v-model="form[item.key]" :maxlength="item.max" :readonly="!editable" />
      </FormItem>
      <FormItem label="注册日期"><Input v-model="form.registrationDate" placeholder="yyyy-MM-dd" :maxlength="10" :readonly="!editable" /></FormItem>
      <FormItem label="备注"><Input v-model="form.remarks" type="textarea" :maxlength="4000" :readonly="!editable" /></FormItem>
    </Form>
    <div class="vehicle-toolbar">
      <Button v-if="editable" type="primary" :loading="busy" @click="save">保存草稿</Button>
      <Button v-if="record.canSubmit" type="success" :disabled="busy || dirty" @click="submit">提交预受理</Button>
      <Button v-if="record.canPreAccept && mode !== 'view'" type="primary" :loading="busy" @click="preAccept">预受理合格</Button>
    </div>
    <p v-if="dirty && record.canSubmit">表单有未保存的修改，请先保存再提交。</p>
    <Form v-if="record.canReturn && mode !== 'view'" :label-width="120">
      <FormItem label="受理意见"><Input v-model="acceptForm.opinion" :maxlength="50" /></FormItem>
      <template v-if="record.canAccept">
        <FormItem label="已签发运输证号"><Input v-model="acceptForm.permitNumber" :maxlength="20" /></FormItem>
        <FormItem label="检验有效期"><Input v-model="acceptForm.inspectionExpiry" placeholder="yyyy-MM-dd" :maxlength="10" /></FormItem>
        <FormItem label="检验核验证据"><Input v-model="acceptForm.inspectionEvidence" type="textarea" :maxlength="800" /></FormItem>
        <Checkbox v-model="confirmed">我已核验证号及车辆检验信息，并确认受理意见</Checkbox>
      </template>
      <div class="vehicle-toolbar">
        <Button v-if="record.canAccept" type="success" :disabled="busy || !confirmed" @click="accept('slhg')">受理合格并入正式车辆表</Button>
        <Button type="warning" :disabled="busy || !acceptForm.opinion.trim()" @click="accept('slth')">退回申请人</Button>
      </div>
    </Form>
  </Card>
</template>

<script>
import {vehicleOptions, listVehicleDrafts, getVehicleDraft, saveVehicleDraft,
  submitVehicleDraft, preAcceptVehicle, acceptVehicle} from '@/api/transport/vehicleWorkflow';
import {blankVehicleForm, vehicleFields, vehicleIdentity, vehicleDraftPayload} from '@/utils/singleVehicleWorkflow';

export default {
  name: 'SingleVehicleWorkflow',
  data() {
    return {localTest: process.env.NODE_ENV === 'development' && process.env.VUE_APP_VEHICLE_LOCAL_TEST === 'true', busy: false, error: '', record: {}, form: blankVehicleForm(), saved: '', options: {enterprises: [], businessTypes: []},
      drafts: [], total: 0, page: 1, requestId: 0, confirmed: false,
      acceptForm: {opinion: '', permitNumber: '', inspectionExpiry: '', inspectionEvidence: ''},
      fields: [
        {key: 'plateNumber', label: '车牌号', max: 20}, {key: 'vin', label: '车辆识别代号 VIN', max: 17},
        {key: 'plateColor', label: '号牌颜色', max: 50}, {key: 'vehicleType', label: '车辆类型', max: 50},
        {key: 'engineNumber', label: '发动机号', max: 50}, {key: 'manufacturer', label: '厂牌', max: 40},
        {key: 'model', label: '型号', max: 40}
      ],
      columns: [{title: '车牌', key: 'plateNumber'}, {title: '申请编号', key: 'letterGid'},
        {title: '状态', key: 'status'}, {title: '操作', slot: 'action', width: 130}]
    };
  },
  computed: {
    hasRecord() { return Boolean(this.record.id); },
    mode() { return this.$route.query.mode || 'apply'; },
    editable() { return this.mode !== 'view' && (!this.hasRecord || this.record.canEdit === true); },
    dirty() { return JSON.stringify(this.form) !== this.saved; }
  },
  watch: {'$route.fullPath': {immediate: true, handler() { this.reload(); }}},
  beforeUnmount() { this.requestId++; },
  methods: {

    stateLabel(value) { return ({'-1': '草稿', '3': '待预受理', '0': '待受理', '2': '受理合格', '4': '转审批/待核查', '-3': '退回申请人'})[value] || String(value); },
    restore(record) {
      this.record = record;
      const form = blankVehicleForm();
      vehicleFields.forEach(key => { form[key] = record[key] || ''; });
      this.form = form; this.saved = JSON.stringify(form); this.confirmed = false;
      this.acceptForm = {opinion: '', permitNumber: record.permitNumber || '', inspectionExpiry: record.inspectionExpiry || '', inspectionEvidence: ''};
    },
    async execute(task) {
      if (this.busy) return;
      this.busy = true; this.error = '';
      try { await task(); } catch (e) { this.error = e.message || e.msg || '车辆业务请求失败'; }
      finally { this.busy = false; }
    },
    async reload() {
      const id = ++this.requestId;
      this.busy = true; this.error = ''; this.restore({});
      try {
        const query = this.$route.query;
        if (query.letterGid || query.id) {
          const identity = {};
          if (query.letterGid) identity.letterGid = String(query.letterGid);
          if (query.id) identity.id = String(query.id);
          const record = await getVehicleDraft(identity);
          if (id !== this.requestId) return;
          this.restore(record);
          if (record.canEdit && this.mode !== 'view') {
            const options = await vehicleOptions();
            if (id === this.requestId) this.options = options;
          }
        } else {
          const [options, list] = await Promise.all([vehicleOptions(), listVehicleDrafts({page: this.page, size: 10})]);
          if (id !== this.requestId) return;
          this.options = options; this.drafts = list.rows; this.total = list.total;
        }
      } catch (e) { if (id === this.requestId) this.error = e.message || e.msg || '车辆业务加载失败'; }
      finally { if (id === this.requestId) this.busy = false; }
    },
    newDraft() { this.page = 1; if (!this.hasRecord) this.reload(); else this.$router.push('/frontPage/vehicleWorkflow_index'); },
    openDraft(row) { this.$router.push({path: '/frontPage/vehicleWorkflow_index', query: {letterGid: row.letterGid, id: row.id}}); },
    changePage(page) { this.page = page; this.reload(); },
    save() { return this.execute(async () => {
      const requestId = this.requestId;
      if (!this.form.enterpriseId || !this.form.businessType) throw new Error('请选择业户和业务类型');
      const record = await saveVehicleDraft(vehicleDraftPayload(this.record, this.form));
      if (requestId !== this.requestId) return;
      this.restore(record); this.$Message.success('草稿已保存');
      if (!this.$route.query.letterGid) await this.$router.replace({path: '/frontPage/vehicleWorkflow_index', query: {letterGid: record.letterGid, id: record.id}});
    }); },
    async applyMutation(call, message) {
      const requestId = this.requestId;
      const record = await call();
      if (requestId !== this.requestId) return;
      this.restore(record); this.$Message.success(message);
    },
    submit() { return this.execute(() => this.applyMutation(() => submitVehicleDraft(vehicleIdentity(this.record)), '已进入预受理队列')); },
    preAccept() { return this.execute(() => this.applyMutation(() => preAcceptVehicle(vehicleIdentity(this.record)), '预受理完成，已进入受理队列')); },
    accept(acceptStatus) { return this.execute(async () => {
      if (!this.acceptForm.opinion.trim()) throw new Error('请输入受理意见');
      await this.applyMutation(() => acceptVehicle({...vehicleIdentity(this.record), ...this.acceptForm, acceptStatus,
        manualVerified: acceptStatus === 'slhg' && this.confirmed}), acceptStatus === 'slhg' ? '正式受理完成' : '已退回申请人');
    }); }
  }
};
</script>

<style scoped>
.vehicle-toolbar { display: flex; flex-wrap: wrap; gap: 12px; margin: 16px 0; }
h3 { margin: 16px 0; }
</style>
