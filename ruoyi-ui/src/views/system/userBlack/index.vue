<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="对象名称" prop="blackName">
        <el-input
          v-model="queryParams.blackName"
          placeholder="请输入对象名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="对象类型" prop="blackType">
        <el-select v-model="queryParams.blackType" placeholder="请选择对象类型" clearable>
          <el-option
            v-for="dict in dict.type.black_lock_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="锁定状态" prop="isLock">
        <el-select v-model="queryParams.isLock" placeholder="请选择锁定状态" clearable>
          <el-option
            v-for="dict in dict.type.black_lock_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="锁定时间">
        <el-date-picker
          v-model="daterangeCreateTime"
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
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="multiple"
          @click="handleUnlock"
          v-hasPermi="['system:userBlack:unLock']"
        >批量解锁</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:userBlack:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="dataTable" v-loading="loading" :data="userBlackList" @selection-change="handleSelectionChange" @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" min-width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" v-if="true" min-width="80"/>
      <el-table-column label="校验" align="center" prop="validCode" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_valid_status" :value="scope.row.validCode"/>
        </template>
      </el-table-column>
      <el-table-column label="对象名称" align="center" prop="blackName"  min-width="150"/>
      <el-table-column label="对象类型" align="center" prop="blackType" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.black_lock_type" :value="scope.row.blackType"/>
        </template>
      </el-table-column>
      <el-table-column label="锁定状态" align="center" prop="isLock" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.black_lock_status" :value="scope.row.isLock"/>
        </template>
      </el-table-column>
      <el-table-column label="锁定原因" align="center" prop="remark" min-width="300"/>
      <el-table-column label="锁定时间" align="center" prop="createTime" min-width="165"/>
      <el-table-column label="解锁人" align="center" prop="updateBy" min-width="120"/>
      <el-table-column label="解锁时间" align="center" prop="updateTime" min-width="165"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="false">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click.stop="handleUnlock(scope.row)"
            v-hasPermi="['system:userBlack:unlock']"
            v-if="scope.row.isLock == '1'"
          >解锁</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click.stop="handleDelete(scope.row)"
            v-hasPermi="['system:userBlack:remove']"
            v-if="scope.row.isLock == '0'"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['system:userBlack:unLock']"
            v-if="activeRow.isLock == '1'"
            @click="handleUnlock(activeRow)"
            >
        解锁
        </li>
        <li v-hasPermi="['system:userBlack:remove']"
            v-if="activeRow.isLock == '0'"
            @click.prevent="handleDelete(activeRow)"
            >
        删除
        </li>
      </ul>
    </div>

  </div>
</template>

<script>
import { listUserBlack, getUserBlack, delUserBlack, addUserBlack, updateUserBlack, unLockBlack } from "@/api/system/userBlack";

export default {
  name: "UserBlack",
  dicts: ['black_lock_status', 'black_lock_type','sys_valid_status'],
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
      // 黑名单管理表格数据
      userBlackList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 锁定原因时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        blackName: undefined,
        blackType: undefined,
        isLock: undefined,
        createTime: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "编号不能为空", trigger: "blur" }
        ],
        validCode: [
          { required: true, message: "校验位不能为空", trigger: "blur" }
        ],
        blackName: [
          { required: true, message: "对象名称不能为空", trigger: "blur" }
        ],
        blackType: [
          { required: true, message: "对象类型不能为空", trigger: "change" }
        ],
        isLock: [
          { required: true, message: "锁定状态不能为空", trigger: "change" }
        ],
        remark: [
          { required: true, message: "锁定原因不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "锁定时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询黑名单管理列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listUserBlack(this.queryParams).then(response => {
        this.userBlackList = response.rows;
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
        validCode: undefined,
        blackName: undefined,
        blackType: undefined,
        isLock: undefined,
        remark: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        createBy: undefined
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
      this.daterangeCreateTime = [];
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
      this.title = "添加黑名单管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getUserBlack(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改黑名单管理";
      });
    },
    /** 重置密码按钮操作 */
    handleUnlock(row) {
      const ids = row.id || this.ids;
      this.$alert('请确认解锁编号为【' + ids + '】的数据项', "解锁提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: 'warning'
      }).then(() => {
        unLockBlack(ids).then(response => {
          this.getList();
          this.$modal.msgSuccess("解锁成功");
        });
      }).catch(() => {});
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateUserBlack(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addUserBlack(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除黑名单管理编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delUserBlack(ids);
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
      this.download('system/userBlack/export', {
        ...this.queryParams
      }, `userBlack_${new Date().getTime()}.xlsx`)
    },

  }
};
</script>
<style lang="scss">

</style>
