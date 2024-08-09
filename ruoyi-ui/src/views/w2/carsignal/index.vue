<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="信号标识" prop="signalKey">
        <el-input
          v-model="queryParams.signalKey"
          placeholder="请输入信号标识"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="信号名称" prop="signalName">
        <el-input
          v-model="queryParams.signalName"
          placeholder="请输入信号名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="信号自检" prop="signalNote">
        <el-select v-model="queryParams.signalNote" placeholder="请选择自检状态">
          <el-option
            v-for="dict in dict.type.sys_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['w2:carsignal:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['w2:carsignal:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['w2:carsignal:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:carsignal:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="carsignalList" @selection-change="handleSelectionChange" @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" v-if="true" width="55"/>
      <el-table-column label="信号标识" align="center" prop="signalKey" />
      <el-table-column label="信号数值" align="center" prop="signalValue" />
      <el-table-column label="信号名称" align="center" prop="signalName" />
      <el-table-column label="信号自检" align="center" prop="signalNote" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_status" :value="scope.row.signalNote"/>
        </template>
      </el-table-column>
    </el-table>
    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:carsignal:edit']"
            @click="handleUpdate(activeRow)"
        >
          修改
        </li>
        <li v-hasPermi="['w2:carsignal:remove']"
            @click.prevent="handleDelete(activeRow)"
        >
          删除
        </li>
      </ul>
    </div>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改车辆信号对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="信号标识" prop="signalKey">
          <el-input v-model="form.signalKey" placeholder="请输入信号标识" />
        </el-form-item>
        <el-form-item label="信号数值" prop="signalValue">
          <el-input v-model="form.signalValue" placeholder="请输入信号数值" />
        </el-form-item>
        <el-form-item label="信号名称" prop="signalName">
          <el-input v-model="form.signalName" placeholder="请输入信号名称" />
        </el-form-item>
        <el-form-item label="信号自检" prop="signalNote">
          <el-switch
            v-model="form.signalNote"
            active-value="1"
            inactive-value="0"
          ></el-switch>
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
import { listCarsignal, getCarsignal, delCarsignal, addCarsignal, updateCarsignal } from "@/api/w2/carsignal";

export default {
  name: "Carsignal",
  dicts: ['sys_status'],
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
      // 车辆信号表格数据
      carsignalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        signalKey: undefined,
        signalName: undefined,
        signalNote: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "ID不能为空", trigger: "blur" }
        ],
        signalKey: [
          { required: true, message: "信号标识不能为空", trigger: "blur" }
        ],
        signalValue: [
          { required: true, message: "信号数值不能为空", trigger: "blur" }
        ],
        signalName: [
          { required: true, message: "信号名称不能为空", trigger: "blur" }
        ],
        signalNote: [
          { required: true, message: "信号自检不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询车辆信号列表 */
    getList() {
      this.loading = true;
      listCarsignal(this.queryParams).then(response => {
        this.carsignalList = response.rows;
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
        signalKey: undefined,
        signalValue: undefined,
        signalName: undefined,
        signalNote: "1"
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加车辆信号";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getCarsignal(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改车辆信号";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateCarsignal(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addCarsignal(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除车辆信号编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delCarsignal(ids);
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
      this.download('w2/carsignal/export', {
        ...this.queryParams
      }, `carsignal_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
