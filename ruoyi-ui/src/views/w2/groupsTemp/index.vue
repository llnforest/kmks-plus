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
      <el-form-item label="证件号码" prop="zjhm">
        <el-input
          v-model="queryParams.zjhm"
          placeholder="请输入证件号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="xm">
        <el-input
          v-model="queryParams.xm"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="准考证号" prop="zkzmbh">
        <el-input
          v-model="queryParams.zkzmbh"
          placeholder="请输入准考证号"
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
          type="danger"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="handleDownload"
          v-hasPermi="['w2:groupsTemp:download']"
        >下载</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:groupsTemp:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="groupsTempList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" v-if="false"/>
      <el-table-column label="流水号" align="center" prop="lsh"  min-width="100"/>
      <el-table-column label="证件号码" align="center" prop="zjhm"  min-width="150"/>
      <el-table-column label="姓名" align="center" prop="xm"  min-width="70"/>
      <el-table-column label="性别" align="center" prop="xb"  min-width="50"/>
      <el-table-column label="准考证号" align="center" prop="zkzmbh"  min-width="90"/>
      <el-table-column label="考试原因" align="center" prop="ksyy"  min-width="80"/>
      <el-table-column label="考试日期" align="center" prop="ksrq" min-width="90">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ksrq, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="考试车型" align="center" prop="kscx" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.param_car_type" :value="scope.row.kscx"/>
        </template>
      </el-table-column>
      <el-table-column label="考试场次" align="center" prop="kscc"  min-width="80"/>
      <el-table-column label="项目名称" align="center" prop="xmmc"  min-width="80"/>
      <el-table-column label="考试员1" align="center" prop="ksy1"  min-width="80"/>
      <el-table-column label="考试员2" align="center" prop="ksy2"  min-width="80"/>
      <el-table-column label="驾校代码" align="center" prop="dlr"  min-width="80"/>
      <el-table-column label="驾校名称" align="center" prop="jxmc"  min-width="80"/>
      <el-table-column label="考试项目" align="center" prop="ksxm"  min-width="80"/>
      <el-table-column label="预约次数" align="center" prop="yycs"  min-width="80"/>
      <el-table-column label="考试原因代码" align="center" prop="ksyybh"  min-width="80"/>
      <el-table-column label="是否夜考" align="center" prop="sfyk"  min-width="80"/>
      <el-table-column label="本次预约考试次数" align="center" prop="bcyykscs"  min-width="80"/>
      <el-table-column label="管理部门" align="center" prop="glbm" min-width="80"/>
      <el-table-column label="经办人" align="center" prop="jbr"  min-width="80"/>

    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改查询下载信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="流水号" prop="lsh">
          <el-input v-model="form.lsh" placeholder="请输入流水号" />
        </el-form-item>
        <el-form-item label="证件号码" prop="zjhm">
          <el-input v-model="form.zjhm" placeholder="请输入证件号码" />
        </el-form-item>
        <el-form-item label="姓名" prop="xm">
          <el-input v-model="form.xm" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="xb">
          <el-input v-model="form.xb" placeholder="请输入性别" />
        </el-form-item>
        <el-form-item label="准考证号" prop="zkzmbh">
          <el-input v-model="form.zkzmbh" placeholder="请输入准考证号" />
        </el-form-item>
        <el-form-item label="考试原因" prop="ksyy">
          <el-input v-model="form.ksyy" placeholder="请输入考试原因" />
        </el-form-item>
        <el-form-item label="考试日期" prop="ksrq">
          <el-date-picker clearable
            v-model="form.ksrq"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择考试日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="考试车型" prop="kscx">
          <el-input v-model="form.kscx" placeholder="请输入考试车型" />
        </el-form-item>
        <el-form-item label="考试场次" prop="kscc">
          <el-input v-model="form.kscc" placeholder="请输入考试场次" />
        </el-form-item>
        <el-form-item label="项目名称" prop="xmmc">
          <el-input v-model="form.xmmc" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="考试员1" prop="ksy1">
          <el-input v-model="form.ksy1" placeholder="请输入考试员1" />
        </el-form-item>
        <el-form-item label="考试员2" prop="ksy2">
          <el-input v-model="form.ksy2" placeholder="请输入考试员2" />
        </el-form-item>
        <el-form-item label="驾校代码" prop="dlr">
          <el-input v-model="form.dlr" placeholder="请输入驾校代码" />
        </el-form-item>
        <el-form-item label="驾校名称" prop="jxmc">
          <el-input v-model="form.jxmc" placeholder="请输入驾校名称" />
        </el-form-item>
        <el-form-item label="考试项目" prop="ksxm">
          <el-input v-model="form.ksxm" placeholder="请输入考试项目" />
        </el-form-item>
        <el-form-item label="预约次数" prop="yycs">
          <el-input v-model="form.yycs" placeholder="请输入预约次数" />
        </el-form-item>
        <el-form-item label="考试原因代码" prop="ksyybh">
          <el-input v-model="form.ksyybh" placeholder="请输入考试原因代码" />
        </el-form-item>
        <el-form-item label="是否夜考" prop="sfyk">
          <el-input v-model="form.sfyk" placeholder="请输入是否夜考" />
        </el-form-item>
        <el-form-item label="本次预约考试次数" prop="bcyykscs">
          <el-input v-model="form.bcyykscs" placeholder="请输入本次预约考试次数" />
        </el-form-item>
        <el-form-item label="管理部门" prop="glbm">
          <el-input v-model="form.glbm" placeholder="请输入管理部门" />
        </el-form-item>
        <el-form-item label="经办人" prop="jbr">
          <el-input v-model="form.jbr" placeholder="请输入经办人" />
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
import { listGroupsTemp, getGroupsTemp, downloadGroups, addGroupsTemp, updateGroupsTemp } from "@/api/w2/groupsTemp";

export default {
  name: "GroupsTemp",
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
      // 查询下载信息表格数据
      groupsTempList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 经办人时间范围
      daterangeKsrq: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        zjhm: undefined,
        xm: undefined,
        zkzmbh: undefined,
        ksrq: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        lsh: [
          { required: true, message: "流水号不能为空", trigger: "blur" }
        ],
        zjhm: [
          { required: true, message: "证件号码不能为空", trigger: "blur" }
        ],
        xm: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        xb: [
          { required: true, message: "性别不能为空", trigger: "blur" }
        ],
        zkzmbh: [
          { required: true, message: "准考证号不能为空", trigger: "blur" }
        ],
        ksyy: [
          { required: true, message: "考试原因不能为空", trigger: "blur" }
        ],
        ksrq: [
          { required: true, message: "考试日期不能为空", trigger: "blur" }
        ],
        kscx: [
          { required: true, message: "考试车型不能为空", trigger: "blur" }
        ],
        kscc: [
          { required: true, message: "考试场次不能为空", trigger: "blur" }
        ],
        xmmc: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        ksy1: [
          { required: true, message: "考试员1不能为空", trigger: "blur" }
        ],
        ksy2: [
          { required: true, message: "考试员2不能为空", trigger: "blur" }
        ],
        dlr: [
          { required: true, message: "驾校代码不能为空", trigger: "blur" }
        ],
        jxmc: [
          { required: true, message: "驾校名称不能为空", trigger: "blur" }
        ],
        ksxm: [
          { required: true, message: "考试项目不能为空", trigger: "blur" }
        ],
        yycs: [
          { required: true, message: "预约次数不能为空", trigger: "blur" }
        ],
        ksyybh: [
          { required: true, message: "考试原因代码不能为空", trigger: "blur" }
        ],
        sfyk: [
          { required: true, message: "是否夜考不能为空", trigger: "blur" }
        ],
        bcyykscs: [
          { required: true, message: "本次预约考试次数不能为空", trigger: "blur" }
        ],
        glbm: [
          { required: true, message: "管理部门不能为空", trigger: "blur" }
        ],
        jbr: [
          { required: true, message: "经办人不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询查询下载信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeKsrq && '' != this.daterangeKsrq) {
        this.queryParams.params["beginKsrq"] = this.daterangeKsrq[0];
        this.queryParams.params["endKsrq"] = this.daterangeKsrq[1];
      }
      listGroupsTemp(this.queryParams).then(response => {
        this.groupsTempList = response.rows;
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
        lsh: undefined,
        zjhm: undefined,
        xm: undefined,
        xb: undefined,
        zkzmbh: undefined,
        ksyy: undefined,
        ksrq: undefined,
        kscx: undefined,
        kscc: undefined,
        xmmc: undefined,
        ksy1: undefined,
        ksy2: undefined,
        dlr: undefined,
        jxmc: undefined,
        ksxm: undefined,
        yycs: undefined,
        ksyybh: undefined,
        sfyk: undefined,
        bcyykscs: undefined,
        glbm: undefined,
        jbr: undefined
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
      this.ids = selection.map(item => item.lsh)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加查询下载信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const lsh = row.lsh || this.ids
      getGroupsTemp(lsh).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改查询下载信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.lsh != null) {
            updateGroupsTemp(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addGroupsTemp(this.form).then(response => {
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
      this.download('w2/groupsTemp/export', {
        ...this.queryParams
      }, `查询下载信息_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
