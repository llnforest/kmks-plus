<template>
  <div class="app-container">
    <el-tabs style="margin-bottom: 30px;">
      <el-tab-pane label="待考列表">
        <el-table v-loading="loading" :data="kcxxList"  @row-click="rowClick" @row-contextmenu="rightClick">
          <el-table-column label="ID" align="center" prop="id" v-if="false"/>
          <el-table-column label="考车号" align="center" prop="kcbh" min-width="50"/>
          <el-table-column label="待考人数" align="center" prop="peopleNum" min-width="65"/>
          <el-table-column label="待考考生" align="left" prop="xm" min-width="150"/>
        </el-table>
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>
import {getCarList} from "@/api/w2/kcdd";
import pubsub from "pubsub-js";
import {carQueueList} from "@/api/w2/dispatch";

export default {
  name: "carList",
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
      kcxxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        zt: "1",
      },
      carModelList:{},
      // 表单参数
      form: {},
      // 表单校验
      rules: {

      },
      carPubId:undefined,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询过程明细列表 */
    getList() {
      this.loading = true;
      carQueueList(this.queryParams).then(response => {
        this.kcxxList = response.data;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    renderCarList(msgName,data) {
      this.kcxxList = data
    }
  },
  mounted(){
    this.carPubId = pubsub.subscribe("renderCar",this.renderCarList)
  },
  beforeDestroy() {
    pubsub.unsubscribe(this.carPubId);
  }
};
</script>
