<template>
  <div class="app-container">
    <el-tabs style="margin-bottom: 30px;">
      <el-tab-pane label="过程明细">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        </el-form>

        <el-table v-loading="loading" :data="flowList"  @row-click="rowClick" @row-contextmenu="rightClick">
          <el-table-column label="时间" align="center" prop="kcbh" min-width="70">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.kssj, '{h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="考试次数" align="center" prop="kscs" min-width="60"/>
          <el-table-column label="考试状态" align="center" prop="kszt" min-width="100"/>
          <el-table-column label="项目名称" align="left" prop="xmmc" min-width="120">
            <template slot-scope="scope">
              {{scope.row.xmmc}}-{{scope.row.ksxm}}
            </template>
          </el-table-column>
          <el-table-column label="项目扣分" align="center" prop="kskf" min-width="60"/>
          <el-table-column label="扣分明细" align="left" prop="msg" min-width="200"/>
        </el-table>
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>
import {flowList} from "@/api/w2/dispatch";
import pubsub from "pubsub-js";

export default {
  name: "flowList",
  dicts: ['param_car_type'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 过程明细表格数据
      flowList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {

      },
      flowPubId:undefined,
      flowParamPubId:undefined,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询过程明细列表 */
    getList() {
      this.loading = true;
      flowList(this.queryParams).then(response => {
        this.flowList = response.data;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    renderFlowList(msgName,data) {
      this.flowList = data
    },
    renderFlowParams(msgName,data) {
      this.queryParams = data
      this.getList();
    }
  },
  mounted(){
    this.flowPubId = pubsub.subscribe("renderFlow",this.renderFlowList)
    this.flowParamPubId = pubsub.subscribe("renderFlowParams",this.renderFlowParams)
  },
  beforeDestroy() {
    pubsub.unsubscribe(this.flowPubId);
    pubsub.unsubscribe(this.flowParamPubId);
  }
};
</script>
<style scoped>
</style>
