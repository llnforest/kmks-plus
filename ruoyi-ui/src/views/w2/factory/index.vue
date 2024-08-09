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
          v-hasPermi="['w2:factory:download']"
        >下载</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:factory:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="factoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="考场序号" align="center" prop="xh" />
      <el-table-column label="考场名称" align="center" prop="kcmc" />
      <el-table-column label="适用准驾车型范围" align="center" prop="kkcx" />
      <el-table-column label="管理部门" align="center" prop="glbm" />
      <el-table-column label="发证机关" align="center" prop="fzjg" />
      <el-table-column label="考场代码" align="center" prop="kcdddh" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改考点信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考场序号" prop="xh">
          <el-input v-model="form.xh" placeholder="请输入考场序号" />
        </el-form-item>
        <el-form-item label="考场名称" prop="kcmc">
          <el-input v-model="form.kcmc" placeholder="请输入考场名称" />
        </el-form-item>
        <el-form-item label="适用准驾车型范围" prop="kkcx">
          <el-input v-model="form.kkcx" placeholder="请输入适用准驾车型范围" />
        </el-form-item>
        <el-form-item label="管理部门" prop="glbm">
          <el-input v-model="form.glbm" placeholder="请输入管理部门" />
        </el-form-item>
        <el-form-item label="发证机关" prop="fzjg">
          <el-input v-model="form.fzjg" placeholder="请输入发证机关" />
        </el-form-item>
        <el-form-item label="考场代码" prop="kcdddh">
          <el-input v-model="form.kcdddh" placeholder="请输入考场代码" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFactory, getFactory, downloadFactory, addFactory, updateFactory } from "@/api/w2/factory";

export default {
  name: "Factory",
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
      // 考点信息表格数据
      factoryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        xh: [
          { required: true, message: "考场序号不能为空", trigger: "blur" }
        ],
        kcmc: [
          { required: true, message: "考场名称不能为空", trigger: "blur" }
        ],
        kkcx: [
          { required: true, message: "适用准驾车型范围不能为空", trigger: "blur" }
        ],
        glbm: [
          { required: true, message: "管理部门不能为空", trigger: "blur" }
        ],
        fzjg: [
          { required: true, message: "发证机关不能为空", trigger: "blur" }
        ],
        kcdddh: [
          { required: true, message: "考场代码不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询考点信息列表 */
    getList() {
      this.loading = true;
      listFactory(this.queryParams).then(response => {
        this.factoryList = response.rows;
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
        xh: undefined,
        kcmc: undefined,
        kkcx: undefined,
        glbm: undefined,
        fzjg: undefined,
        kcdddh: undefined
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
      this.ids = selection.map(item => item.xh)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加考点信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const xh = row.xh || this.ids
      getFactory(xh).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改考点信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.xh != null) {
            updateFactory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addFactory(this.form).then(response => {
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
    /** 下载按钮操作 */
    handleDownload() {
      this.$modal.confirm('是否确认下载数据？').then(() => {
        this.loading = true;
        return downloadFactory();
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
      this.download('w2/factory/export', {
        ...this.queryParams
      }, `factory_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
