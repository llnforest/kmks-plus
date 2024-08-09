<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="配置类型" prop="iFlag"  label-width="180">
        <el-select v-model="queryParams.iFlag" placeholder="请选择配置类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_w2_config_flag"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="配置说明" prop="sName" label-width="180">
        <el-input
          v-model="queryParams.sName"
          placeholder="请输入配置说明"
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
          v-hasPermi="['w2:config:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:config:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="w2_configList" @selection-change="handleSelectionChange" @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="配置编码" align="center" prop="lincode" v-if="true"/>
      <el-table-column label="配置标识总值" align="center" prop="iflag" v-if="false"/>
      <el-table-column label="配置类型" align="center" prop="sflag" />
      <el-table-column label="配置说明" align="center" prop="sname" />
      <el-table-column label="具体配置明细数值" align="center" prop="ivalue" v-if="false"/>
      <el-table-column label="配置值" align="center" prop="svalue" />
      <el-table-column label="备注" align="center" prop="sbeizhu" />
    </el-table>
    <!--    鼠标右键菜单-->
    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:config:edit']"
            @click="handleUpdate(activeRow)"
        >
          修改
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

    <!-- 添加或修改驾考参数对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="配置标识总值" prop="iflag">
          <el-input v-model="form.iflag" placeholder="请输入配置标识总值" />
        </el-form-item>
        <el-form-item label="配置类型" prop="sflag">
          <el-input v-model="form.sflag" placeholder="请输入配置类型" />
        </el-form-item>
        <el-form-item label="配置说明" prop="sname">
          <el-input v-model="form.sname" placeholder="请输入配置说明" />
        </el-form-item>
        <el-form-item label="具体配置明细数值" prop="ivalue">
          <el-input v-model="form.ivalue" placeholder="请输入具体配置明细数值" />
        </el-form-item>
        <el-form-item label="配置值" prop="svalue">
          <el-input v-model="form.svalue" placeholder="请输入配置值" />
        </el-form-item>
        <el-form-item label="备注" prop="sbeizhu">
          <el-input v-model="form.sbeizhu" placeholder="请输入备注" />
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
import { listconfig, getconfig, delconfig, addconfig, updateconfig } from "@/api/w2/config";

export default {
  name: "W2_config",
  dicts: ['sys_w2_config_flag'],
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
      // 驾考参数表格数据
      w2_configList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        iFlag: undefined,
        sFlag: undefined,
        sName: undefined,
        iValue: undefined,
        sValue: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        lincode: [
          { required: true, message: "编码不能为空", trigger: "blur" }
        ],
        iflag: [
          { required: true, message: "标志参数不能为空", trigger: "blur" }
        ],
        sflag: [
          { required: true, message: "标志类型不能为空", trigger: "blur" }
        ],
        sname: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        ivalue: [
          { required: true, message: "标志值不能为空", trigger: "blur" }
        ],
        svalue: [
          { required: true, message: "标志值不能为空", trigger: "blur" }
        ],
        sbeizhu: [
          { required: true, message: "备注不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询驾考参数列表 */
    getList() {
      this.loading = true;
      listconfig(this.queryParams).then(response => {
        this.w2_configList = response.rows;
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
        lincode: undefined,
        iflag: undefined,
        sflag: undefined,
        sname: undefined,
        ivalue: undefined,
        svalue: undefined,
        sbeizhu: undefined
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
      this.ids = selection.map(item => item.lIncode)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加驾考参数";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const lIncode = row.lincode || this.ids
      getconfig(lIncode).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改驾考参数";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.lincode != null) {
            updateconfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addconfig(this.form).then(response => {
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
      const lIncodes = row.lincode || this.ids;
      this.$modal.confirm('是否确认删除驾考参数编号为"' + lIncodes + '"的数据项？').then(() => {
        this.loading = true;
        return delconfig(lIncodes);
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
      this.download('w2/config/export', {
        ...this.queryParams
      }, `w2_config_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
