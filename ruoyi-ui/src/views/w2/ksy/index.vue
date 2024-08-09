<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['w2:ksy:add']"
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
          v-hasPermi="['w2:ksy:edit']"
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
          v-hasPermi="['w2:ksy:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:ksy:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ksyList" @selection-change="handleSelectionChange"  @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="xh" v-if="true"/>
      <el-table-column label="所属发证机关" align="center" prop="sszd" min-width="120"/>
      <el-table-column label="管理部门" align="center" prop="glbm" min-width="100"/>
      <el-table-column label="证件名称" align="center" prop="sfzmmc" min-width="100"/>
      <el-table-column label="身份证明号码" align="center" prop="sfzmhm" min-width="160"/>
      <el-table-column label="驾驶证档案编号" align="center" prop="dabh" min-width="140"/>
      <el-table-column label="姓名" align="center" prop="xm" min-width="70"/>
      <el-table-column label="性别" align="center" prop="xb" min-width="70"/>
      <el-table-column label="出生日期" align="center" prop="csrq" min-width="90"/>
      <el-table-column label="考试准驾车型范围" align="center" prop="zkcx" min-width="130"/>
      <el-table-column label="考试员证发证日期" align="center" prop="fzrq" min-width="130"/>
      <el-table-column label="考试员证有效期止" align="center" prop="kszyxqz" min-width="130"/>
      <el-table-column label="工作单位" align="center" prop="gzdw" min-width="120"/>
      <el-table-column label="经办人" align="center" prop="jbr" min-width="80"/>
      <el-table-column label="考试员证发证单位" align="center" prop="fzjg" min-width="130"/>
      <el-table-column label="创建时间" align="center" prop="cjsj" min-width="150"/>

    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

<!--    鼠标右键菜单-->
    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:ksy:edit']"
            @click="handleUpdate(activeRow)"
        >
          修改
        </li>
        <li v-hasPermi="['w2:ksy:remove']"
            @click.prevent="handleDelete(activeRow)"
        >
          删除
        </li>
      </ul>
    </div>
    <!-- 添加或修改考官信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-form-item label="所属发证机关" prop="sszd">
          <el-input v-model="form.sszd" placeholder="请输入所属发证机关" />
        </el-form-item>
        <el-form-item label="管理部门" prop="glbm">
          <el-input v-model="form.glbm" placeholder="请输入管理部门" />
        </el-form-item>
        <el-form-item label="证件名称" prop="sfzmmc">
          <el-input v-model="form.sfzmmc" placeholder="请输入证件名称" />
        </el-form-item>
        <el-form-item label="身份证明号码" prop="sfzmhm">
          <el-input v-model="form.sfzmhm" placeholder="请输入身份证明号码" />
        </el-form-item>
        <el-form-item label="驾驶证档案编号" prop="dabh">
          <el-input v-model="form.dabh" placeholder="请输入驾驶证档案编号" />
        </el-form-item>
        <el-form-item label="姓名" prop="xm">
          <el-input v-model="form.xm" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="xb">
          <el-input v-model="form.xb" placeholder="请输入性别" />
        </el-form-item>
        <el-form-item label="出生日期" prop="csrq">
          <el-input v-model="form.csrq" placeholder="请输入出生日期" />
        </el-form-item>
        <el-form-item label="考试准驾车型范围" prop="zkcx">
          <el-input v-model="form.zkcx" placeholder="请输入考试准驾车型范围" />
        </el-form-item>
        <el-form-item label="考试员证发证日期" prop="fzrq">
          <el-input v-model="form.fzrq" placeholder="请输入考试员证发证日期" />
        </el-form-item>
        <el-form-item label="考试员证有效期止" prop="kszyxqz">
          <el-input v-model="form.kszyxqz" placeholder="请输入考试员证有效期止" />
        </el-form-item>
        <el-form-item label="工作单位" prop="gzdw">
          <el-input v-model="form.gzdw" placeholder="请输入工作单位" />
        </el-form-item>
        <el-form-item label="经办人" prop="jbr">
          <el-input v-model="form.jbr" placeholder="请输入经办人" />
        </el-form-item>
        <el-form-item label="考试员证发证单位" prop="fzjg">
          <el-input v-model="form.fzjg" placeholder="请输入考试员证发证单位" />
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
import { listKsy, getKsy, delKsy, addKsy, updateKsy,downloadKsy} from "@/api/w2/ksy";
import {delDeviceSign} from "@/api/w2/deviceSign";

export default {
  name: "Ksy",
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
      // 考官信息表格数据
      ksyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sszd: undefined,
        glbm: undefined,
        sfzmmc: undefined,
        sfzmhm: undefined,
        dabh: undefined,
        xm: undefined,
        xb: undefined,
        csrq: undefined,
        zkcx: undefined,
        fzrq: undefined,
        kszyxqz: undefined,
        ksyzt: 1,
        gzdw: undefined,
        jbr: undefined,
        fzjg: undefined,
        cjsj: undefined,
        gxsj: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sszd: [
          { required: true, message: "发证机关不能为空", trigger: "blur" }
        ],
        glbm: [
          { required: true, message: "管理部门不能为空", trigger: "blur" }
        ],
        sfzmmc: [
          { required: true, message: "证件名称不能为空", trigger: "blur" }
        ],
        sfzmhm: [
          { required: true, message: "身份证明号码不能为空", trigger: "blur" }
        ]
        ,dabh: [
          { required: true, message: "驾驶证档案编号不能为空", trigger: "blur" }
        ],
        xm: [
          { required: true, message: "考官姓名不能为空", trigger: "blur" }
        ],

      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询考官信息列表 */
    getList() {
      this.loading = true;
      listKsy(this.queryParams).then(response => {
        this.ksyList = response.rows;
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
        sszd: undefined,
        glbm: undefined,
        sfzmmc: "居民身份证",
        sfzmhm: undefined,
        dabh: undefined,
        xm: undefined,
        xb: undefined,
        csrq: undefined,
        zkcx: undefined,
        fzrq: undefined,
        kszyxqz: undefined,
        ksyzt: undefined,
        gzdw: undefined,
        jbr: undefined,
        fzjg: undefined,
        cjsj: undefined,
        gxsj: undefined
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
      this.title = "添加考官信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const xh = row.xh || this.ids
      getKsy(xh).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "更新身份证";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.xh != null) {
            updateKsy(this.form).then(response => {
              this.$modal.msgSuccess("更新身份证成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addKsy(this.form).then(response => {
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
        return downloadKsy();
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
      this.download('w2/ksy/export', {
        ...this.queryParams
      }, `考官_${new Date().getTime()}.xlsx`)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const xhs = row.xh || this.ids;
      this.$modal.confirm('是否确认删除考官序号为"' + xhs + '"的数据项？').then(() => {
        this.loading = true;
        return delKsy(xhs);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
  }
};
</script>
