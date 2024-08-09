<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="安全员姓名" prop="sName" label-width="180">
        <el-input
          v-model="queryParams.sName"
          placeholder="请输入安全员姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['w2:safepeople:add']"
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
          v-hasPermi="['w2:safepeople:edit']"
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
          v-hasPermi="['w2:safepeople:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:safepeople:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="safepeopleList" @selection-change="handleSelectionChange" @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="安全员姓名" align="center" prop="sname" />
      <el-table-column label="证件号码" align="center" prop="szjhm" v-if="true"/>
      <el-table-column label="考车编号" align="center" prop="kcbh" />
      <el-table-column label="考车牌号" align="center" prop="kchp" />
    </el-table>
    <!--    鼠标右键菜单-->
    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:safepeople:edit']"
            @click.prevent="handleUpdate(activeRow)"
        >
          修改
        </li>
        <li v-hasPermi="['w2:safepeople:remove']"
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

    <!-- 添加或修改安全员管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="安全员姓名" prop="sname">
          <el-input v-model="form.sname" placeholder="请输入安全员姓名" />
        </el-form-item>
        <el-form-item label="证件号码" prop="szjhm">
          <el-input v-model="form.szjhm" placeholder="请输入证件号码" />
        </el-form-item>
        <el-form-item label="考车编号" prop="kcbh">
          <el-input v-model="form.kcbh" placeholder="请输入考车编号" />
        </el-form-item>
        <el-form-item label="考车牌号" prop="kchp">
          <el-input v-model="form.kchp" placeholder="请输入考车牌号" />
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
import { listSafepeople, getSafepeople, delSafepeople, addSafepeople, updateSafepeople } from "@/api/w2/safepeople";

export default {
  name: "Safepeople",
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
      // 安全员管理表格数据
      safepeopleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sName: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sname: [
          { required: true, message: "安全员姓名不能为空", trigger: "blur" }
        ],
        szjhm: [
          { required: true, message: "证件号码不能为空", trigger: "blur" }
        ],
        kcbh: [
          { required: true, message: "考车编号不能为空", trigger: "blur" }
        ],
        kchp: [
          { required: true, message: "考车牌号不能为空", trigger: "blur" }
        ]
      },
      add: true
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询安全员管理列表 */
    getList() {
      this.loading = true;
      listSafepeople(this.queryParams).then(response => {
        this.safepeopleList = response.rows;
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
        sname: undefined,
        szjhm: undefined,
        sZp: undefined,
        sZpUrl: undefined,
        izt: 1,
        kcbh: undefined,
        kchp: undefined
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
      this.ids = selection.map(item => item.sZjhm)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.add = true;
      this.title = "添加安全员管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      this.add = false;
      const sZjhm = row.szjhm || this.ids
      getSafepeople(sZjhm).then(response => {
        this.loading = false;
        this.form = response.data;
        console.log(this.form)
        this.open = true;
        this.title = "修改安全员管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (!this.add) {
            updateSafepeople(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addSafepeople(this.form).then(response => {
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
      const szjhms = row.szjhm || this.ids;
      console.log(row)
      this.$modal.confirm('是否确认删除安全员管理编号为"' + szjhms + '"的数据项？').then(() => {
        this.loading = true;
        return delSafepeople(szjhms);
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
      this.download('w2/safepeople/export', {
        ...this.queryParams
      }, `安全员_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
