<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考试日期">
        <el-date-picker
          v-model="daterangeKsrq"
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
          icon="el-icon-upload"
          size="mini"
          @click="handleDownload"
          v-hasPermi="['w2:groups:download']"
        >下载</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:groups:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="groupsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" v-if="false"/>
      <el-table-column label="分组序号" align="center" prop="xh" v-if="true" min-width="100"/>
      <el-table-column label="姓名" align="center" prop="xm"  min-width="70"/>
      <el-table-column label="证件号码" align="center" prop="zjhm" v-if="true"  min-width="140"/>
      <el-table-column label="考试地点" align="center" prop="ksdd" min-width="80"/>
      <el-table-column label="考试场次" align="center" prop="kscc" min-width="60"/>
      <el-table-column label="考试科目" align="center" prop="kskm" min-width="60"/>
      <el-table-column label="考试车型" align="center" prop="kscx" min-width="60"/>
      <el-table-column label="考试员" align="center" prop="ksy"  min-width="70"/>
      <el-table-column label="考试日期" align="center" prop="ksrq" width="90">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ksrq, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="考试项目" align="center" prop="ksxm"  min-width="110"/>
      <el-table-column label="项目名称" align="center" prop="xmmc"  min-width="250"  show-overflow-tooltip/>

    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改明细分组信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考试地点" prop="ksdd">
          <el-input v-model="form.ksdd" placeholder="请输入考试地点" />
        </el-form-item>
        <el-form-item label="考试场次" prop="kscc">
          <el-input v-model="form.kscc" placeholder="请输入考试场次" />
        </el-form-item>
        <el-form-item label="考试科目" prop="kskm">
          <el-input v-model="form.kskm" placeholder="请输入考试科目" />
        </el-form-item>
        <el-form-item label="考试车型" prop="kscx">
          <el-input v-model="form.kscx" placeholder="请输入考试车型" />
        </el-form-item>
        <el-form-item label="考试员" prop="ksy">
          <el-input v-model="form.ksy" placeholder="请输入考试员" />
        </el-form-item>
        <el-form-item label="姓名" prop="xm">
          <el-input v-model="form.xm" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="考试日期" prop="ksrq">
          <el-date-picker clearable
            v-model="form.ksrq"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择考试日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="考试项目" prop="ksxm">
          <el-input v-model="form.ksxm" placeholder="请输入考试项目" />
        </el-form-item>
        <el-form-item label="项目名称" prop="xmmc">
          <el-input v-model="form.xmmc" placeholder="请输入项目名称" />
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
import { listGroups, getGroups, downloadGroups, addGroups, updateGroups } from "@/api/w2/groups";

export default {
  name: "Groups",
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
      // 明细分组信息表格数据
      groupsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 项目名称时间范围
      daterangeKsrq: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ksrq: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        xh: [
          { required: true, message: "分组序号不能为空", trigger: "blur" }
        ],
        ksdd: [
          { required: true, message: "考试地点不能为空", trigger: "blur" }
        ],
        kscc: [
          { required: true, message: "考试场次不能为空", trigger: "blur" }
        ],
        kskm: [
          { required: true, message: "考试科目不能为空", trigger: "blur" }
        ],
        kscx: [
          { required: true, message: "考试车型不能为空", trigger: "blur" }
        ],
        ksy: [
          { required: true, message: "考试员不能为空", trigger: "blur" }
        ],
        xm: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        zjhm: [
          { required: true, message: "证件号码不能为空", trigger: "blur" }
        ],
        ksrq: [
          { required: true, message: "考试日期不能为空", trigger: "blur" }
        ],
        ksxm: [
          { required: true, message: "考试项目不能为空", trigger: "blur" }
        ],
        xmmc: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        kchp: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        dlr: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        djrq: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        lsh: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        zkzh: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        ksyy: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        ksyybh: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        sfyk: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        yyrq: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询明细分组信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeKsrq && '' != this.daterangeKsrq) {
        this.queryParams.params["beginKsrq"] = this.daterangeKsrq[0];
        this.queryParams.params["endKsrq"] = this.daterangeKsrq[1];
      }
      listGroups(this.queryParams).then(response => {
        this.groupsList = response.rows;
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
        xh: undefined,
        ksdd: undefined,
        kscc: undefined,
        kskm: undefined,
        kscx: undefined,
        ksy: undefined,
        xm: undefined,
        zjhm: undefined,
        ksrq: undefined,
        ksxm: undefined,
        xmmc: undefined,
        kchp: undefined,
        dlr: undefined,
        djrq: undefined,
        lsh: undefined,
        zkzh: undefined,
        ksyy: undefined,
        ksyybh: undefined,
        sfyk: undefined,
        yyrq: undefined
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
      this.daterangeKsrq = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.xh)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加明细分组信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const xh = row.xh || this.ids
      getGroups(xh).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改明细分组信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.xh != null) {
            updateGroups(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addGroups(this.form).then(response => {
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
    /** 下载按钮操作 */
    handleDownload() {
      this.$modal.confirm('是否确认下载数据？').then(() => {
        this.loading = true;
        return downloadGroups();
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("下载成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('w2/groups/export', {
        ...this.queryParams
      }, `明细分组信息_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
