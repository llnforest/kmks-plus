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
          v-hasPermi="['w2:configSwitch:add']"
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
          v-hasPermi="['w2:configSwitch:edit']"
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
          v-hasPermi="['w2:configSwitch:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:configSwitch:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleImport"
          v-hasPermi="['w2:configSwitch:import']"
          >导入</el-button
        >
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="configSwitchList" @selection-change="handleSelectionChange"  @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="70"/>
      <el-table-column label="项目代码" align="center" prop="projectcode" min-width="80"/>
      <el-table-column label="设备IP" align="center" prop="deviceip" min-width="120"/>
      <el-table-column label="设备端口" align="center" prop="deviceport" min-width="80"/>
      <el-table-column label="设备名称" align="center" prop="deviceusername" min-width="80"/>
      <el-table-column label="设备密码" align="center" prop="devicepassword" min-width="100"/>
      <el-table-column label="通道类型" align="center" prop="channeltype" min-width="80"/>
      <el-table-column label="远程设备通道号" align="center" prop="devicechannel" min-width="80"/>
      <el-table-column label="传输协议" align="center" prop="protocol" min-width="80"/>
      <el-table-column label="厂商类型" align="center" prop="vendortype" min-width="80"/>
      <el-table-column label="码流类型" align="center" prop="bitstreamtype" min-width="80"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="false">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['w2:configSwitch:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['w2:configSwitch:remove']"
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
        <li v-hasPermi="['w2:configSwitch:edit']"
            @click="handleUpdate(activeRow)"
        >
          修改
        </li>
        <li v-hasPermi="['w2:configSwitch:remove']"
            @click.prevent="handleDelete(activeRow)"
        >
          删除
        </li>
      </ul>
    </div>
    <!-- 添加或修改项目监控对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="项目代码" prop="projectcode">
          <el-input v-model="form.projectcode" placeholder="请输入项目代码" />
        </el-form-item>
        <el-form-item label="设备IP" prop="deviceip">
          <el-input v-model="form.deviceip" placeholder="请输入设备IP" />
        </el-form-item>
        <el-form-item label="设备端口" prop="deviceport">
          <el-input v-model="form.deviceport" placeholder="请输入设备端口" />
        </el-form-item>
        <el-form-item label="设备名称" prop="deviceusername">
          <el-input v-model="form.deviceusername" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="设备密码" prop="devicepassword">
          <el-input v-model="form.devicepassword" placeholder="请输入设备密码" />
        </el-form-item>
        <el-form-item label="通道类型" prop="channeltype">
          <el-input v-model="form.channeltype" placeholder="通道类型" />
        </el-form-item>
        <el-form-item label="远程设备通道号" prop="devicechannel">
          <el-input v-model="form.devicechannel" placeholder="请输入远程设备通道号" />
        </el-form-item>
        <el-form-item label="传输协议" prop="protocol">
          <el-input v-model="form.protocol" placeholder="请输入传输协议" />
        </el-form-item>
        <el-form-item label="厂商类型" prop="vendortype">
          <el-input v-model="form.vendortype" placeholder="请输入厂商类型" />
        </el-form-item>
        <el-form-item label="码流类型" prop="bitstreamtype">
          <el-input v-model="form.bitstreamtype" placeholder="请输入码流类型" />
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
import { listConfigSwitch, getConfigSwitch, delConfigSwitch, addConfigSwitch, updateConfigSwitch } from "@/api/w2/configSwitch";
import { getToken } from "@/utils/auth";

export default {
  name: "ConfigSwitch",
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
      // 项目监控表格数据
      configSwitchList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bitstreamtype: undefined
      },
       // 导入弹框参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "项目监控导入",
        // 是否禁用上传
        isUploading: false,
        multiple: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: window.globalConfig.VUE_APP_BASE_API + "/w2/configSwitch/import",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "ID不能为空", trigger: "blur" }
        ],
        projectcode: [
          { required: true, message: "项目代码不能为空", trigger: "blur" }
        ],
        deviceip: [
          { required: true, message: "设备IP不能为空", trigger: "blur" }
        ],
        deviceport: [
          { required: true, message: "设备端口不能为空", trigger: "blur" }
        ],
        deviceusername: [
          { required: true, message: "设备名称不能为空", trigger: "blur" }
        ],
        devicepassword: [
          { required: true, message: "设备密码不能为空", trigger: "blur" }
        ],
        channeltype: [
          { required: true, message: "通道类型不能为空", trigger: "change" }
        ],
        devicechannel: [
          { required: true, message: "远程设备通道号不能为空", trigger: "blur" }
        ],
        protocol: [
          { required: true, message: "传输协议不能为空", trigger: "blur" }
        ],
        vendortype: [
          { required: true, message: "厂商类型不能为空", trigger: "change" }
        ],
        bitstreamtype: [
          { required: true, message: "码流类型不能为空", trigger: "change" }
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
      this.download('w2/downloadTpl/switch', {
      }, `项目监控导入模板.xlsx`);
    },
    /** 查询项目监控列表 */
    getList() {
      this.loading = true;
      listConfigSwitch(this.queryParams).then(response => {
        this.configSwitchList = response.rows;
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
        projectcode: undefined,
        deviceip: undefined,
        deviceport: undefined,
        deviceusername: undefined,
        devicepassword: undefined,
        channeltype: undefined,
        devicechannel: undefined,
        protocol: undefined,
        vendortype: undefined,
        bitstreamtype: undefined
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加项目监控";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getConfigSwitch(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改项目监控";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateConfigSwitch(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addConfigSwitch(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除项目监控编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delConfigSwitch(ids);
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
      this.download('w2/configSwitch/export', {
        ...this.queryParams
      }, `项目监控_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
