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
          type="danger"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="handleDownload"
          v-hasPermi="['w2:carinfo:download']"
        >下载</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:carinfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="carinfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" v-if="false"/>
      <el-table-column label="序号" align="center" prop="xh" />
      <el-table-column label="号牌类型" align="center" prop="hpzl" />
      <el-table-column label="号牌号码" align="center" prop="hphm" />
      <el-table-column label="准驾车型" align="center" prop="syzjcx" />
      <el-table-column label="车辆类型" align="center" prop="cllx" />
      <el-table-column label="车辆品牌" align="center" prop="clpp" />
      <el-table-column label="初次登记日期" align="center" prop="ccdjrq" />
      <el-table-column label="强制报废日期" align="center" prop="qzbfqz" />
      <el-table-column label="发证机关" align="center" prop="fzjg" />
      <el-table-column label="车辆状态" align="center" prop="zt" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改车辆信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCarinfo, getCarinfo, delCarinfo, addCarinfo, updateCarinfo,downloadCarinfo } from "@/api/w2/carinfo";

export default {
  name: "Carinfo",
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
      // 车辆信息表格数据
      carinfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        xh: [
          { required: true, message: "序号不能为空", trigger: "blur" }
        ],
        hpzl: [
          { required: true, message: "号牌类型不能为空", trigger: "blur" }
        ],
        hphm: [
          { required: true, message: "号牌号码不能为空", trigger: "blur" }
        ],
        syzjcx: [
          { required: true, message: "准驾车型不能为空", trigger: "blur" }
        ],
        cllx: [
          { required: true, message: "车辆类型不能为空", trigger: "blur" }
        ],
        clpp: [
          { required: true, message: "车辆品牌不能为空", trigger: "blur" }
        ],
        ccdjrq: [
          { required: true, message: "初次登记日期不能为空", trigger: "blur" }
        ],
        qzbfqz: [
          { required: true, message: "强制报废日期不能为空", trigger: "blur" }
        ],
        fzjg: [
          { required: true, message: "发证机关不能为空", trigger: "blur" }
        ],
        zt: [
          { required: true, message: "车辆状态不能为空", trigger: "blur" }
        ],
        ksczt: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        shr: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        ipdz: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        gkjmac: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        gkjbh: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        gkjdk: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        cczdip: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        cczdmac: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        cczdbh: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        cczddk: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        sskcxh: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询车辆信息列表 */
    getList() {
      this.loading = true;
      listCarinfo(this.queryParams).then(response => {
        this.carinfoList = response.rows;
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
        hpzl: undefined,
        hphm: undefined,
        syzjcx: undefined,
        cllx: undefined,
        clpp: undefined,
        ccdjrq: undefined,
        qzbfqz: undefined,
        fzjg: undefined,
        zt: undefined,
        ksczt: undefined,
        shr: undefined,
        ipdz: undefined,
        gkjmac: undefined,
        gkjbh: undefined,
        gkjdk: undefined,
        cczdip: undefined,
        cczdmac: undefined,
        cczdbh: undefined,
        cczddk: undefined,
        sskcxh: undefined
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
      this.ids = selection.map(item => item.xh)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加车辆信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const xh = row.xh || this.ids
      getCarinfo(xh).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改车辆信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.xh != null) {
            updateCarinfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addCarinfo(this.form).then(response => {
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
        return downloadCarinfo();
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
      this.download('w2/carinfo/export', {
        ...this.queryParams
      }, `carinfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
