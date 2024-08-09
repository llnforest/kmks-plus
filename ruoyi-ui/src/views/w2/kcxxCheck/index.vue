<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考车编号" prop="kcbh">
        <el-input
          v-model="queryParams.kcbh"
          placeholder="请输入考车编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="自检结果" prop="checkResult">
        <el-select v-model="queryParams.checkResult" placeholder="请选择自检结果" clearable>
          <el-option
            v-for="dict in dict.type.check_result"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="自检时间">
        <el-date-picker
          v-model="daterangeCheckTime"
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
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['w2:kcxxCheck:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="kcxxCheckList" @selection-change="handleSelectionChange" @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="考车编号" align="center" prop="kcbh" min-width="80"/>
      <el-table-column label="自检内容" align="center" prop="checkContent" min-width="300"/>
      <el-table-column label="自检结果" align="center" prop="checkResult" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.check_result" :value="scope.row.checkResult"/>
        </template>
      </el-table-column>
      <el-table-column label="自检时间" align="center" prop="checkTime" width="180">
      </el-table-column>
    </el-table>
    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:kcxxCheck:remove']"
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

    <!-- 添加或修改考车自检对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考车编号" prop="kcbh">
          <el-input v-model="form.kcbh" placeholder="请输入考车编号" />
        </el-form-item>
        <el-form-item label="自检结果" prop="checkResult">
          <el-select v-model="form.checkResult" placeholder="请选择自检结果">
            <el-option
              v-for="dict in dict.type.check_result"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="自检内容">
          <editor v-model="form.checkContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="自检时间" prop="checkTime">
          <el-date-picker clearable
            v-model="form.checkTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择自检时间">
          </el-date-picker>
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
import { listKcxxCheck, getKcxxCheck, delKcxxCheck, addKcxxCheck, updateKcxxCheck } from "@/api/w2/kcxxCheck";

export default {
  name: "KcxxCheck",
  dicts: ['check_result'],
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
      // 考车自检表格数据
      kcxxCheckList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 自检时间时间范围
      daterangeCheckTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        kcbh: undefined,
        checkResult: undefined,
        checkContent: undefined,
        checkTime: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        kcbh: [
          { required: true, message: "考车编号不能为空", trigger: "blur" }
        ],
        checkResult: [
          { required: true, message: "自检结果不能为空", trigger: "change" }
        ],
        checkContent: [
          { required: true, message: "自检内容不能为空", trigger: "blur" }
        ],
        checkTime: [
          { required: true, message: "自检时间不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询考车自检列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCheckTime && '' != this.daterangeCheckTime) {
        this.queryParams.params["beginCheckTime"] = this.daterangeCheckTime[0];
        this.queryParams.params["endCheckTime"] = this.daterangeCheckTime[1];
      }
      listKcxxCheck(this.queryParams).then(response => {
        this.kcxxCheckList = response.rows;
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
        id:undefined,
        kcbh: undefined,
        checkResult: undefined,
        checkContent: undefined,
        checkTime: undefined
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
      this.daterangeCheckTime = [];
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
      this.title = "添加考车自检";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getKcxxCheck(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改考车自检";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateKcxxCheck(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addKcxxCheck(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除考车自检编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delKcxxCheck(kcbhs);
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
      this.download('w2/kcxxCheck/export', {
        ...this.queryParams
      }, `kcxxCheck_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
