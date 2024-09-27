<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="驾校代码" prop="paramdm">
        <el-input
          v-model="queryParams.paramdm"
          placeholder="请输入驾校代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="驾校名称" prop="paramname">
        <el-input
          v-model="queryParams.paramname"
          placeholder="请输入驾校名称"
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
          v-hasPermi="['w2:school:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['w2:school:edit']"
        >修改
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
          v-hasPermi="['w2:school:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:school:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="handleDownload"
          v-hasPermi="['w2:school:download']"
          v-if="isJgType(2)"
        >驾校备案信息下载
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="schoolList" @selection-change="handleSelectionChange" @row-click="rowClick"
              @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="nid" min-width="70"/>
      <el-table-column label="驾校代码" align="center" prop="paramdm"/>
      <el-table-column label="驾校名称" align="center" prop="paramname"/>
      <el-table-column label="场次" align="center" prop="paramtype">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.school_param_type" :value="scope.row.paramtype"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="bz"/>
    </el-table>
    <!--    鼠标右键菜单-->
    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:school:edit']"
            @click.prevent="handleUpdate(activeRow)"
        >
          修改
        </li>
        <li v-hasPermi="['w2:school:remove']"
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

    <!-- 添加或修改基础编码对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="驾校代码" prop="paramdm">
          <el-input v-model="form.paramdm" placeholder="请输入驾校代码"/>
        </el-form-item>
        <el-form-item label="驾校名称" prop="paramname">
          <el-input v-model="form.paramname" placeholder="请输入驾校名称"/>
        </el-form-item>
        <el-form-item label="场次选择" prop="paramtype">
          <el-select v-model="form.paramtype" placeholder="请选择场次 ">
            <el-option
              v-for="dict in dict.type.school_param_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="bz">
          <el-input v-model="form.bz" placeholder="请输入备注"/>
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
import {listSchool, getSchool, delSchool, addSchool, updateSchool, downloadSchool} from "@/api/w2/school";

export default {
  name: "School",
  dicts: ['school_param_type'],
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
          {required: true, message: "编号不能为空", trigger: "blur"}
        ],
        paramdm: [
          {required: true, message: "驾校代码不能为空", trigger: "blur"}
        ],
        paramname: [
          {required: true, message: "驾校名称不能为空", trigger: "blur"}
        ],
        paramtype: [
          {required: true, message: "场次不能为空", trigger: "change"}
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
      listSchool(this.queryParams).then(response => {
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加基础编码";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const nid = row.nid || this.ids
      getSchool(nid).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改基础编码";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.nid != null) {
            updateSchool(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addSchool(this.form).then(response => {
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
      const nids = row.nid || this.ids;
      this.$modal.confirm('是否确认删除基础编码编号为"' + nids + '"的数据项？').then(() => {
        this.loading = true;
        return delSchool(nids);
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
      this.download('w2/school/export', {
        ...this.queryParams
      }, `驾校信息管理_${new Date().getTime()}.xlsx`)
    },
    /** 下载按钮操作 */
    handleDownload() {
      this.$modal.confirm('是否确认下载驾校备案数据？').then(() => {
        this.download('w2/school/download', {
          ...this.queryParams
        }, `驾校备案信息_${new Date().getTime()}.xlsx`)
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
  }
};
</script>
