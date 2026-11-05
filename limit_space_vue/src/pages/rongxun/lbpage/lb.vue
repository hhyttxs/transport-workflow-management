<template>
  <div class="draft-list-page">
  <Row></Row>
  <div style="margin-top: 25px;background-color: white;border-radius: 10px;padding-right: 20px;padding-bottom: 15px;">
    <Row></Row>
    <Row style="margin-top: 15px" justify="center">
      <Col span="6" style="text-align: right;">
        <Space>
          身份证号：
          <Input v-model="initParam.sfzh" clearable style="width: 200px"
                 size="large"/>
        </Space>
      </Col>
      <Col span="6" style="text-align: right;">
        <Space>
          姓名：
          <Input v-model="initParam.name" clearable style="width: 200px"
                 size="large"/>
        </Space>
      </Col>
      <Col span="6" style="text-align: right;"></Col>
    </Row>
  </div>

  <div style="margin-top: 25px;background-color: white;border-radius: 10px;padding: 15px;">
    <Row></Row>
    <Row style="margin-top: 15px;border-bottom: 2px solid #23202a;padding-bottom: 10px;">
      <Col span="4" style="text-align: left;">
        <h3 class="pageTitle">查询结果</h3>
      </Col>
      <Col span="12" style="text-align: left;">
        <Button style="width: 100px;margin-left: 10px" type="dashed" size="large" icon="md-search" @click="search">
          查询
        </Button>
      </Col>
    </Row>
    <Table border :columns="columns" :data="tableData" style="margin-top: 10px">
      <template #name="{ row }">
        <strong>{{ row.name }}</strong>
      </template>
      <template #action1="{ row }">
        <Button
          type="primary"
          size="small"
          @click="continueApply(row)"
        >
          继续申报
        </Button>
      </template>
    </Table>
    <Page style="margin-top: 10px" :total="page.pageCount" v-model="page.currentPage" :page-size="page.pageSize"
          placement="top"
          show-total show-elevator show-sizer @on-change="onChange" @on-page-size-change="pageChange"
          :pageSizeOpts="pageSizeOpts"/>
  </div>

  </div>
</template>


<script>
import {resultPayload} from "@/utils/resultPayload";

import {getUser} from "@/utils/auth";
import { queryDraftList } from "@/api/hy/dlhwjyxk";
import {getDlhwjyxkInfoByGid} from '@/api/hy/dlhwjyxk';

export default {
  name: 'DlhwjyxkDraftList',

  data() {
    return {
      initParam: {
        sfzh: '',
        name: ''
      },

      tableData: [],

      pageSizeOpts: [10, 20, 30, 40, 50],

      page: {
        pageSize: 10,
        pageCount: 0,
        totalPage: 0,
        currentPage: 1
      },
      columns: [
        {
          title: '事项名称',
          key: 'sxmc',
          render: (h, {row}) => h('span', row.sxmc === 'roadTransportLicense' ? '道路货物运输经营许可' : row.sxmc),
          align: 'center'
        },
        {
          title: '业务类型',
          key: 'ywlx',
          render: (h, {row}) => h('span', row.ywlx === 'firstApply' ? '首次申办许可证' : row.ywlx),
          align: 'center'
        },
        {
          title: '操作',
          slot: 'action1',
          width: 160,
          align: 'center'
        }
      ],


    };
  },

  mounted() {
    this.initData();
  },


  methods: {
    initData() {
      const user = getUser();

      const initData = {
        ...this.initParam,
        account: user && user.code ? user.code : '',
        currentPage: this.page.currentPage,
        numberPage: this.page.pageSize,
        status: '0'
      };

      queryDraftList(initData).then(response => {

          const payload=resultPayload(response);
        if (String(payload.code)!=="0") throw new Error(payload.msg);
        this.tableData = payload.data.data.result;

          this.page.pageCount = payload.data.pageCount || 0;
          this.page.totalPage = payload.data.totalPage || 0;
        }).catch(error => {
          console.error('查询暂存列表失败：', error);
          this.$Message.error('查询暂存列表失败');
        });
    },

    search: function () {
      this.page.currentPage = 1
      this.initData()
    },
    onChange: function (page) {
      this.page.currentPage = page
      this.initData()
    },
    pageChange: function (page) {
      this.page.currentPage = 1
      this.page.pageSize = page
      this.initData()
    },

    /**
     * 点击继续申报，携带暂存记录 gid 跳转。
     */
    continueApply(row) {
      this.$router.push({
        path: '/frontPage/bzd_index',
        query: {
          gid: row.gid
        }
      });
    }
  }
};
</script>
