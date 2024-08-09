<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="闸机设备" prop="gateName">
        <el-input
          v-model="queryParams.gateName"
          placeholder="请输入闸机设备"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="闸机状态" prop="gateStatus">
        <el-select v-model="queryParams.gateStatus" placeholder="请选择闸机状态" clearable>
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
          v-hasPermi="['w2:deviceGate:add']"
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
          v-hasPermi="['w2:deviceGate:edit']"
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
          v-hasPermi="['w2:deviceGate:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:deviceGate:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="deviceGateList" @selection-change="handleSelectionChange" @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="gateId" />
      <el-table-column label="闸机设备" align="center" prop="gateName" />
      <el-table-column label="设备IP" align="center" prop="gateIp" />
      <el-table-column label="设备MAC" align="center" prop="gateMac" />
      <el-table-column label="设备备注" align="center" prop="remark" />
      <el-table-column label="设备状态" align="center" prop="gateStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_open_status" :value="scope.row.gateStatus"/>
        </template>
      </el-table-column>
    </el-table>
    <!--    鼠标右键菜单-->
    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:deviceGate:edit']"
            @click="handleUpdate(activeRow)"
        >
          修改
        </li>
        <li v-hasPermi="['w2:deviceGate:remove']"
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

    <!-- 添加或修改闸机设备对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="闸机设备" prop="gateName">
          <el-input v-model="form.gateName" placeholder="请输入闸机设备" />
        </el-form-item>
        <el-form-item label="设备IP" prop="gateIp">
          <el-input v-model="form.gateIp" placeholder="请输入闸机IP" />
        </el-form-item>
        <el-form-item label="设备MAC" prop="gateMac">
          <el-input v-model="form.gateMac" placeholder="请输入闸机MAC" />
        </el-form-item>
        <el-form-item label="设备备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入设备备注" />
        </el-form-item>
        <el-form-item label="设备状态" prop="gateStatus">
          <el-select v-model="form.gateStatus" placeholder="请选择闸机状态">
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
import { listDeviceGate, getDeviceGate, delDeviceGate, addDeviceGate, updateDeviceGate } from "@/api/w2/deviceGate";

export default {
  name: "DeviceGate",
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
      // 闸机设备表格数据
      deviceGateList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        gateId: undefined,
        gateName: undefined,
        gateIp: undefined,
        gateMac: undefined,
        gateStatus: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        gateName: [
          { required: true, message: "闸机设备不能为空", trigger: "blur" }
        ],
        gateIp: [
          { required: true, message: "设备IP不能为空", trigger: "blur" }
        ],
        gateMac: [
          { required: true, message: "设备MAC不能为空", trigger: "blur" }
        ],
        gateStatus: [
          { required: true, message: "设备状态不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询闸机设备列表 */
    getList() {
      this.loading = true;
      listDeviceGate(this.queryParams).then(response => {
        this.deviceGateList = response.rows;
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
        gateId: undefined,
        gateName: undefined,
        gateIp: undefined,
        gateMac: undefined,
        remark: undefined,
        gateStatus: "1"
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
      this.ids = selection.map(item => item.gateId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加闸机设备";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const gateId = row.gateId || this.ids
      getDeviceGate(gateId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改闸机设备";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.gateId != null) {
            updateDeviceGate(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addDeviceGate(this.form).then(response => {
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
      const gateIds = row.gateId || this.ids;
      this.$modal.confirm('是否确认删除闸机设备编号为"' + gateIds + '"的数据项？').then(() => {
        this.loading = true;
        return delDeviceGate(gateIds);
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
      this.download('system/deviceGate/export', {
        ...this.queryParams
      }, `deviceGate_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
