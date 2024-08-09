<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目名称" prop="gakfmc">
        <el-input
          v-model="queryParams.gakfmc"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="扣分项目说明" prop="kfmc">
        <el-input
          v-model="queryParams.kfmc"
          placeholder="请输入扣分项目说明"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评判方式" prop="autoflag">
        <el-select v-model="queryParams.autoflag" placeholder="请选择评判方式" clearable>
          <el-option
            v-for="dict in dict.type.param_judge_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['w2:kfconfigk2common:edit']"
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
          v-hasPermi="['w2:kfconfigk2common:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:kfconfigk2common:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="kfconfigList" @selection-change="handleSelectionChange" @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="考试科目" align="center" prop="kskm" />
      <el-table-column label="项目名称" align="center" prop="gakfmc" min-width="200"/>
      <el-table-column label="扣分项目说明" align="center" prop="kfmc" min-width="300"/>
      <el-table-column label="扣分值" align="center" prop="kf" min-width="150"/>
      <el-table-column label="评判方式" align="center" prop="autoflag">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.param_judge_type" :value="scope.row.autoflag"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="false">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['w2:kfconfigk2common:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['w2:kfconfigk2common:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:kfconfigk2common:edit']"
            @click="handleUpdate(activeRow)"
        >
          修改
        </li>
        <li v-hasPermi="['w2:kfconfigk2common:remove']"
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

    <!-- 添加或修改评判参数对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="考试科目" prop="kskm">
          {{form.kskm}}
        </el-form-item>
        <el-form-item label="项目名称" prop="gakfmc">
          {{form.gakfmc}}
        </el-form-item>
        <el-form-item label="扣分项目说明" prop="kfmc">
          {{form.kfmc}}
        </el-form-item>
        <el-form-item label="扣分值" prop="kf">
          <el-input v-model="form.kf" placeholder="请输入扣分值" />
        </el-form-item>
        <el-form-item label="评判方式" prop="autoflag">
          <el-select v-model="form.autoflag" placeholder="请选择评判方式">
            <el-option
              v-for="dict in dict.type.param_judge_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
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
import { listKfconfig, getKfconfig, delKfconfig, addKfconfig, updateKfconfig } from "@/api/w2/kfconfig";

export default {
  name: "Kfconfigk2common",
  dicts: ['param_judge_type'],
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
      // 评判参数表格数据
      kfconfigList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        gakey: undefined,
        gakfmc: undefined,
        kfmc: undefined,
        gakfdm: undefined,
        paramtype:2,
        autoflag: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        kf: [
          { required: true, message: "扣分值不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询评判参数列表 */
    getList() {
      this.loading = true;
      listKfconfig(this.queryParams).then(response => {
        this.kfconfigList = response.rows;
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
        gakey: undefined,
        gakfmc: undefined,
        xmxh: undefined,
        kfmc: undefined,
        value: undefined,
        beizhu: undefined,
        kskm: undefined,
        gakfdm: undefined,
        autoflag: undefined,
        ksxm: undefined,
        ksxmdm: undefined,
        kf: undefined,
        id: undefined,
        paramtype: 2,
        autoflag1: undefined,
        xmtype: undefined
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
      this.ids = selection.map(item => item.gakey)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加评判参数";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const gakey = row.gakey || this.ids
      getKfconfig(gakey).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改评判参数";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.gakey != null) {
            updateKfconfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addKfconfig(this.form).then(response => {
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
      const gakeys = row.gakey || this.ids;
      this.$modal.confirm('是否确认删除评判参数编号为"' + gakeys + '"的数据项？').then(() => {
        this.loading = true;
        return delKfconfig(gakeys);
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
      this.download('w2/kfconfig/export', {
        ...this.queryParams
      }, `kfconfig_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
