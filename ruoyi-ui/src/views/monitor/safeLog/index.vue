<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="用户代码" prop="userNo" v-if="false">
        <el-input
          v-model="queryParams.userNo"
          placeholder="请输入用户代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户姓名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业务类别" prop="category">
        <el-input
          v-model="queryParams.category"
          placeholder="请输入业务类别"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作类型" prop="operation">
        <el-input
          v-model="queryParams.operation"
          placeholder="请输入操作类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="mac地址" prop="mac" label-width="100">
        <el-input
          v-model="queryParams.mac"
          placeholder="请输入mac地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
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
          v-hasPermi="['monitor:safeLog:export']"
          >导出</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleState"
          v-hasPermi="['monitor:safeLog:export']"
          >统计</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="safeLogList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="主键"
        align="center"
        prop="id"
        v-if="false"
        width="170"
      />
      <el-table-column label="用户代码" align="center" prop="userNo" v-if="false" />
      <el-table-column label="用户姓名" align="center" prop="userName" min-width="100"/>
      <el-table-column label="业务类别" align="center" prop="category"  min-width="100"/>
      <el-table-column label="业务类型" align="center" prop="ywtpye"  min-width="100"/>
      <el-table-column label="操作类型" align="center" prop="operation"  min-width="100"/>
      <el-table-column label="操作结果" align="center" prop="state"  min-width="100"/>
      <el-table-column
        label="操作内容"
        align="center"
        prop="content"
        :show-overflow-tooltip="true"
        min-width="200"
      />
      <el-table-column label="操作IP" align="center" prop="userIp"  min-width="120"/>
      <el-table-column label="mac地址" align="center" prop="mac"  min-width="120"/>
      <el-table-column label="终端机器名" align="center" prop="hostName" min-width="120"/>
      <el-table-column label="操作日期" align="center" prop="createTime" min-width="160"/>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改安全日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户代码" prop="userNo">
          <el-input v-model="form.userNo" placeholder="请输入用户代码" />
        </el-form-item>
        <el-form-item label="用户姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户姓名" />
        </el-form-item>
        <el-form-item label="操作IP" prop="userIp">
          <el-input v-model="form.userIp" placeholder="请输入操作IP" />
        </el-form-item>
        <el-form-item label="业务类别" prop="category">
          <el-input v-model="form.category" placeholder="请输入业务类别" />
        </el-form-item>
        <el-form-item label="操作类型" prop="operation">
          <el-input v-model="form.operation" placeholder="请输入操作类型" />
        </el-form-item>
        <el-form-item label="操作结果" prop="state">
          <el-input v-model="form.state" placeholder="请输入操作结果" />
        </el-form-item>
        <el-form-item label="操作内容">
          <editor v-model="form.content" :min-height="192" />
        </el-form-item>
        <el-form-item label="mac地址" prop="mac">
          <el-input v-model="form.mac" placeholder="请输入mac地址" />
        </el-form-item>
        <el-form-item label="终端机器名" prop="hostName">
          <el-input v-model="form.hostName" placeholder="请输入终端机器名" />
        </el-form-item>
        <el-form-item label="业务类型" prop="ywtpye">
          <el-input v-model="form.ywtpye" placeholder="请输入业务类型" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm"
          >确 定</el-button
        >
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listSafeLog,
  getSafeLog,
  delSafeLog,
  addSafeLog,
  updateSafeLog,
} from "@/api/monitor/safeLog";

export default {
  name: "SafeLog",
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
      // 安全日志表格数据
      safeLogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userNo: undefined,
        userName: undefined,
        category: undefined,
        operation: undefined,
        mac: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [{ required: true, message: "主键不能为空", trigger: "blur" }],
        userNo: [
          { required: true, message: "用户代码不能为空", trigger: "blur" },
        ],
        userName: [
          { required: true, message: "用户姓名不能为空", trigger: "blur" },
        ],
        userIp: [
          { required: true, message: "操作IP不能为空", trigger: "blur" },
        ],
        category: [
          { required: true, message: "业务类别不能为空", trigger: "blur" },
        ],
        operation: [
          { required: true, message: "操作类型不能为空", trigger: "blur" },
        ],
        state: [
          { required: true, message: "操作结果不能为空", trigger: "blur" },
        ],
        content: [
          { required: true, message: "操作内容不能为空", trigger: "blur" },
        ],
        mac: [{ required: true, message: "mac地址不能为空", trigger: "blur" }],
        code: [{ required: true, message: "校验位不能为空", trigger: "blur" }],
        hostName: [
          { required: true, message: "终端机器名不能为空", trigger: "blur" },
        ],
        ywtpye: [
          { required: true, message: "业务类型不能为空", trigger: "blur" },
        ],
        remark: [{ required: true, message: "备注不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询安全日志列表 */
    getList() {
      this.loading = true;
      listSafeLog(this.queryParams).then((response) => {
        this.safeLogList = response.rows;
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
        id: undefined,
        userNo: undefined,
        userName: undefined,
        userIp: undefined,
        category: undefined,
        operation: undefined,
        state: undefined,
        content: undefined,
        mac: undefined,
        code: undefined,
        hostName: undefined,
        ywtpye: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        remark: undefined,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加安全日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids;
      getSafeLog(id).then((response) => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改安全日志";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateSafeLog(this.form)
              .then((response) => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              })
              .finally(() => {
                this.buttonLoading = false;
              });
          } else {
            addSafeLog(this.form)
              .then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              })
              .finally(() => {
                this.buttonLoading = false;
              });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除安全日志编号为"' + ids + '"的数据项？')
        .then(() => {
          this.loading = true;
          return delSafeLog(ids);
        })
        .then(() => {
          this.loading = false;
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {})
        .finally(() => {
          this.loading = false;
        });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "monitor/safeLog/export",
        {
          ...this.queryParams,
        },
        `safeLog_${new Date().getTime()}.xlsx`
      );
    },
    /** 导出统计操作 */
    handleState() {
      this.download(
        "monitor/safeLog/export/state",
        {
          ...this.queryParams,
        },
        `safeLog_state_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
