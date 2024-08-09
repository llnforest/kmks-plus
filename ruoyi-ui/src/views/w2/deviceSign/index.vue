<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="签到设备" prop="signName">
        <el-input
          v-model="queryParams.signName"
          placeholder="请输入签到设备"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备状态" prop="signStatus">
        <el-select v-model="queryParams.signStatus" placeholder="请选择设备状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_open_status"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['w2:deviceSign:add']"
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
          v-hasPermi="['w2:deviceSign:edit']"
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
          v-hasPermi="['w2:deviceSign:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:deviceSign:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="deviceSignList" @selection-change="handleSelectionChange"  @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="signId" />
      <el-table-column label="签到设备" align="center" prop="signName" />
      <el-table-column label="设备IP" align="center" prop="signIp" />
      <el-table-column label="设备MAC" align="center" prop="signMac" />
      <el-table-column label="设备备注" align="center" prop="remark" />
      <el-table-column label="设备状态" align="center" prop="signStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_open_status" :value="scope.row.signStatus"/>
        </template>
      </el-table-column>
    </el-table>
    <!--    鼠标右键菜单-->
    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:deviceSign:edit']"
            @click="handleUpdate(activeRow)"
        >
          修改
        </li>
        <li v-hasPermi="['w2:deviceSign:remove']"
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

    <!-- 添加或修改签到设备对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="签到设备" prop="signName">
          <el-input v-model="form.signName" placeholder="请输入签到设备" />
        </el-form-item>
        <el-form-item label="设备IP" prop="signIp">
          <el-input v-model="form.signIp" placeholder="请输入设备IP" />
        </el-form-item>
        <el-form-item label="设备MAC" prop="signMac">
          <el-input v-model="form.signMac" placeholder="请输入设备MAC" />
        </el-form-item>
        <el-form-item label="设备备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入设备备注" />
        </el-form-item>
        <el-form-item label="设备状态" prop="signStatus">
          <el-select v-model="form.signStatus" placeholder="请选择设备状态">
            <el-option
              v-for="dict in dict.type.sys_open_status"
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
import { listDeviceSign, getDeviceSign, delDeviceSign, addDeviceSign, updateDeviceSign } from "@/api/w2/deviceSign";

export default {
  name: "DeviceSign",
  dicts: ['sys_open_status'],
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
      // 签到设备表格数据
      deviceSignList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        signId: undefined,
        signName: undefined,
        signStatus: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        signName: [
          { required: true, message: "签到设备不能为空", trigger: "blur" }
        ],
        signIp: [
          { required: true, message: "设备IP不能为空", trigger: "blur" }
        ],
        signMac: [
          { required: true, message: "设备MAC不能为空", trigger: "blur" }
        ],
        signStatus: [
          { required: true, message: "设备状态不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询签到设备列表 */
    getList() {
      this.loading = true;
      listDeviceSign(this.queryParams).then(response => {
        this.deviceSignList = response.rows;
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
        signId: undefined,
        signName: undefined,
        signIp: undefined,
        signMac: undefined,
        remark: undefined,
        signStatus: "1"
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
      this.ids = selection.map(item => item.signId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加签到设备";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const signId = row.signId || this.ids
      getDeviceSign(signId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改签到设备";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.signId != null) {
            updateDeviceSign(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addDeviceSign(this.form).then(response => {
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
      const signIds = row.signId || this.ids;
      this.$modal.confirm('是否确认删除签到设备编号为"' + signIds + '"的数据项？').then(() => {
        this.loading = true;
        return delDeviceSign(signIds);
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
      this.download('system/deviceSign/export', {
        ...this.queryParams
      }, `deviceSign_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
