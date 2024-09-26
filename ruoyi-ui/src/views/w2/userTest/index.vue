<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考生姓名" prop="xm">
        <el-input
          v-model="queryParams.xm"
          placeholder="请输入考生姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="证件号码" prop="zjhm">
        <el-input
          v-model="queryParams.zjhm"
          placeholder="请输入证件号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="约考日期" prop="ykrq">
        <el-date-picker clearable
                        v-model="queryParams.ykrq"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择约考日期">
        </el-date-picker>
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
          v-hasPermi="['w2:userTest:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['w2:userTest:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userTestList" @selection-change="handleSelectionChange" @row-click="rowClick"
              @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="ID" align="center" prop="id" v-if="true" min-width="80"/>
      <el-table-column label="考生姓名" align="center" prop="xm" min-width="200"/>
      <el-table-column label="证件号码" align="center" prop="zjhm" min-width="200"/>
      <el-table-column label="考试车型" align="center" prop="kscx" min-width="100"/>
      <el-table-column label="约考日期" align="center" prop="ykrq" width="180"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!--    鼠标右键菜单-->
    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:userTest:remove']"
            @click="handleDelete(activeRow)"
        >
          删除
        </li>
      </ul>
    </div>

    <!-- 添加或修改测试数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考生姓名" prop="xm">
          <el-input v-model="form.xm" placeholder="请输入考生姓名"/>
        </el-form-item>
        <el-form-item label="证件号码" prop="zjhm">
          <el-input v-model="form.zjhm" placeholder="请输入证件号码"/>
        </el-form-item>
        <el-form-item label="考试车型" prop="kscx">
          <el-select v-model="form.kscx" placeholder="请选择考试车型">
            <el-option
              v-for="dict in dict.type.param_car_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
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
import {listUserTest, getUserTest, delUserTest, addUserTest, updateUserTest} from "@/api/w2/userTest";

export default {
  name: "UserTest",
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
      // 测试数据表格数据
      userTestList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        xm: undefined,
        zjhm: undefined,
        ykrq: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          {required: true, message: "ID不能为空", trigger: "blur"}
        ],
        xm: [
          {required: true, message: "考生姓名不能为空", trigger: "blur"}
        ],
        zjhm: [
          {required: true, message: "证件号码不能为空", trigger: "blur"}
        ],
        kscx: [
          {required: true, message: "考试车型不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询测试数据列表 */
    getList() {
      this.loading = true;
      listUserTest(this.queryParams).then(response => {
        this.userTestList = response.rows;
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
        xm: undefined,
        zjhm: undefined,
        ykrq: undefined,
        kscx: 'C1'
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加测试数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getUserTest(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改测试数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateUserTest(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addUserTest(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除测试数据编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delUserTest(ids);
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
      this.download('w2/userTest/export', {
        ...this.queryParams
      }, `userTest_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
