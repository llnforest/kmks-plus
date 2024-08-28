<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
          v-hasPermi="['w2:configCar:add']"
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
          v-hasPermi="['w2:configCar:edit']"
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
          v-hasPermi="['w2:configCar:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:configCar:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleImport"
          v-hasPermi="['w2:configCar:import']"
          >导入</el-button
        >
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="configCarList" @selection-change="handleSelectionChange"  @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="车号" align="center" prop="carno" />
      <el-table-column label="合码器设备号" align="center" prop="deviceno" />
      <el-table-column label="合码器输出口" align="center" prop="deviceoutputno" />
      <el-table-column label="动态解码通道" align="center" prop="videochannel" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="false">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['w2:configCar:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['w2:configCar:remove']"
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

    <!-- 右键菜单 -->
    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:configCar:edit']"
            @click="handleUpdate(activeRow)"
        >
          修改
        </li>
        <li v-hasPermi="['w2:configCar:remove']"
            @click.prevent="handleDelete(activeRow)"
        >
          删除
        </li>
      </ul>
    </div>

    <!-- 添加或修改车辆合码器对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="车号" prop="carno">
          <el-input v-model="form.carno" placeholder="请输入车号" />
        </el-form-item>
        <el-form-item label="合码器设备号" prop="deviceno">
          <el-input v-model="form.deviceno" placeholder="请输入合码器设备号" />
        </el-form-item>
        <el-form-item label="合码器输出口" prop="deviceoutputno">
          <el-input v-model="form.deviceoutputno" placeholder="请输入合码器输出口" />
        </el-form-item>
        <el-form-item label="动态解码通道" prop="videochannel">
          <el-input v-model="form.videochannel" placeholder="请输入动态解码通道" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 导入对话框 -->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将Excel文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="danger" @click="downloadTpl">下载模板</el-button>
        <el-button type="primary" @click="submitFileForm">确定上传</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listConfigCar, getConfigCar, delConfigCar, addConfigCar, updateConfigCar } from "@/api/w2/configCar";
import { getToken } from "@/utils/auth";

export default {
  name: "ConfigCar",
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
      // 车辆合码器表格数据
      configCarList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
       // 导入弹框参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "车辆合码器导入",
        // 是否禁用上传
        isUploading: false,
        multiple: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: window.globalConfig.VUE_APP_BASE_API + "/w2/configCar/import",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        carno: [
          { required: true, message: "车号不能为空", trigger: "blur" }
        ],
        deviceno: [
          { required: true, message: "合码器设备号不能为空", trigger: "blur" }
        ],
        deviceoutputno: [
          { required: true, message: "合码器输出口不能为空", trigger: "blur" }
        ],
        videochannel: [
          { required: true, message: "动态解码通道不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 导入按钮操作 */
    handleImport() {
      this.upload.open = true;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    // 下载模板
    downloadTpl(){
      this.download('w2/downloadTpl/car', {
      }, `车辆合码器导入模板.xlsx`);
    },
    /** 查询车辆合码器列表 */
    getList() {
      this.loading = true;
      listConfigCar(this.queryParams).then(response => {
        this.configCarList = response.rows;
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
        carno: undefined,
        deviceno: undefined,
        deviceoutputno: undefined,
        videochannel: undefined
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
      this.ids = selection.map(item => item.carno)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加车辆合码器";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.carno || this.ids
      getConfigCar(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改车辆合码器";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.carno != null) {
            addConfigCar(this.form).then(response => {
              this.$modal.msgSuccess("操作成功");
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
      const ids = row.carno || this.ids;
      this.$modal.confirm('是否确认删除车辆合码器编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delConfigCar(ids);
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
      this.download('w2/configCar/export', {
        ...this.queryParams
      }, `车辆合码器_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
