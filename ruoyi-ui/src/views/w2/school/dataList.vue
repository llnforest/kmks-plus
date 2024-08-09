<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="handleDownload"
          v-hasPermi="['w2:school:download']"
        >下载</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:school:exportData']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="schoolList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" v-if="false"/>
      <el-table-column label="编号" align="center" prop="nid" />
      <el-table-column label="驾校代码" align="center" prop="paramdm" />
      <el-table-column label="驾校名称" align="center" prop="paramname" />
      <el-table-column label="驾校全称" align="center" prop="jxqc" />

    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />


  </div>
</template>

<script>
import { dataList,downloadSchool } from "@/api/w2/school";

export default {
  name: "SchoolDataList",
  dicts:['school_param_type'],
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
      // 基础编码表格数据
      schoolList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        nid: undefined,
        paramdm: undefined,
        paramname: undefined,
        bz: undefined,
        paramtype: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        nid: [
          { required: true, message: "编号不能为空", trigger: "blur" }
        ],
        paramdm: [
          { required: true, message: "驾校代码不能为空", trigger: "blur" }
        ],
        paramname: [
          { required: true, message: "驾校名称不能为空", trigger: "blur" }
        ],
        paramtype: [
          { required: true, message: "场次不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询基础编码列表 */
    getList() {
      this.loading = true;
      dataList(this.queryParams).then(response => {
        this.schoolList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        nid: undefined,
        type: undefined,
        paramdm: undefined,
        paramname: undefined,
        bz: undefined,
        paramtype: undefined,
        jxqc: undefined,
        maxdm: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.nid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 下载按钮操作 */
    handleDownload() {
      this.$modal.confirm('是否确认下载数据？').then(() => {
        this.loading = true;
        return downloadSchool();
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("下载成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('w2/school/exportData', {
        ...this.queryParams
      }, `驾校信息_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
