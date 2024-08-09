<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="审计分类" prop="tableComment">
        <el-input
          v-model="queryParams.tableComment"
          placeholder="请输入审计分类"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据库用户名" prop="username" label-width="100px">
        <el-input
          v-model="queryParams.username"
          placeholder="请输入数据库用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="审计模块" prop="moduleName">
        <el-input
          v-model="queryParams.moduleName"
          placeholder="请输入审计模块"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作类型" prop="operationType">
        <el-select v-model="queryParams.operationType" placeholder="请选择操作类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_audit_operate_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="IP 地址" prop="ipAddress">
        <el-input
          v-model="queryParams.ipAddress"
          placeholder="请输入IP 地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作时间">
        <el-date-picker
          v-model="daterangeOperationTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:auditLog:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExportAnalyse"
          v-hasPermi="['system:auditLog:exportAnalyse']"
        >统计导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="auditLogList" @selection-change="handleSelectionChange">
      <el-table-column label="序号" align="center" prop="auditId" width="60" v-if="true"/>
      <el-table-column label="审计分类" align="center" prop="tableComment" min-width="80" />
      <el-table-column label="数据库用户名" align="center" prop="username"  min-width="100"/>
      <el-table-column label="数据表" align="center" prop="tableName"  min-width="120"/>
      <el-table-column label="IP 地址" align="center" prop="ipAddress"  min-width="150"/>
      <el-table-column label="操作类型" align="center" prop="operationType" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_audit_operate_type" :value="scope.row.operationType"/>
        </template>
      </el-table-column>
      <el-table-column label="审计模块" align="center" prop="moduleName"  min-width="130"/>
      <el-table-column label="审计内容" align="left" prop="auditContent"  min-width="300" :show-overflow-tooltip="true"/>
      <el-table-column label="操作时间" align="center" prop="operationTime" width="165">
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改审计日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAuditLog, getAuditLog, delAuditLog, addAuditLog, updateAuditLog } from "@/api/system/auditLog";

export default {
  name: "AuditLog",
  dicts: ['sys_audit_operate_type'],
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
      // 审计日志表格数据
      auditLogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 校验位时间范围
      daterangeOperationTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        username: undefined,
        tableName: undefined,
        tableComment: undefined,
        ipAddress: undefined,
        operationTime: undefined,
        operationType: undefined,
        moduleName: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询审计日志列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeOperationTime && '' != this.daterangeOperationTime) {
        this.queryParams.params["beginOperationTime"] = this.daterangeOperationTime[0];
        this.queryParams.params["endOperationTime"] = this.daterangeOperationTime[1];
      }
      listAuditLog(this.queryParams).then(response => {
        this.auditLogList = response.rows;
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
        auditId: undefined,
        username: undefined,
        tableName: undefined,
        tableComment: undefined,
        ipAddress: undefined,
        operationTime: undefined,
        operationType: undefined,
        auditContent: undefined,
        moduleName: undefined,
        validCode: undefined
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
      this.daterangeOperationTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.auditId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加审计日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const auditId = row.auditId || this.ids
      getAuditLog(auditId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改审计日志";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.auditId != null) {
            updateAuditLog(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addAuditLog(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const auditIds = row.auditId || this.ids;
      this.$modal.confirm('是否确认删除审计日志编号为"' + auditIds + '"的数据项？').then(() => {
        this.loading = true;
        return delAuditLog(auditIds);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/auditLog/export', {
        ...this.queryParams
      }, `审计日志_${new Date().getTime()}.xlsx`)
    },
    /** 审计日志统计导出操作 */
    handleExportAnalyse() {
      this.download(
        "system/auditLog/exportAnalyse",
        {
          ...this.queryParams,
        },
        `审计日志统计_${new Date().getTime()}.xlsx`
      );
    },
  }
};
</script>
