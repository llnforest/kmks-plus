<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="考试日期">
                <el-date-picker v-model="daterangeKsrq1" style="width: 215px" value-format="yyyy-MM-dd" type="daterange"
                    range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
            </el-form-item>
            <el-form-item label="考试项目" prop="paramdm">
                <el-input v-model="queryParams.paramdm" placeholder="请输入考试项目" clearable @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                    v-hasPermi="['w2:cdxmbh:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                    v-hasPermi="['w2:cdxmbh:export']">导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="cdxmbhList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序号" align="center" prop="nid" />
            <el-table-column label="扣分代码" align="center" prop="paramdm" />
            <el-table-column label="扣分次数" align="center" prop="kszt" />
            <el-table-column label="项目代码名称" align="center" prop="paramname" />
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
            @pagination="getList" />

        <!-- 添加或修改场地项目编号对话框 -->
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
import { listCdxmbh, getCdxmbh, delCdxmbh, addCdxmbh, updateCdxmbh } from "@/api/w2/cdxmbh";

export default {
    name: "Cdxmbh",
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
            // 场地项目编号表格数据
            cdxmbhList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                paramdm: undefined,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                nid: [
                    { required: true, message: "ID不能为空", trigger: "blur" }
                ],
                paramdm: [
                    { required: true, message: "考试项目不能为空", trigger: "blur" }
                ],
                paramname: [
                    { required: true, message: "项目名称不能为空", trigger: "blur" }
                ],
                msg: [
                    { required: true, message: "信息不能为空", trigger: "blur" }
                ],
                kszt: [
                    { required: true, message: "考试状态不能为空", trigger: "blur" }
                ],
                zxip: [
                    { required: true, message: "中心IP不能为空", trigger: "blur" }
                ],
                portms: [
                    { required: true, message: "端口不能为空", trigger: "blur" }
                ],
                mdm: [
                    { required: true, message: "内部代码不能为空", trigger: "blur" }
                ],
                sbxh: [
                    { required: true, message: "设备序号不能为空", trigger: "blur" }
                ],
                gadm: [
                    { required: true, message: "公安代码不能为空", trigger: "blur" }
                ],
                kskm: [
                    { required: true, message: "考试科目不能为空", trigger: "blur" }
                ],
                xmipaddress: [
                    { required: true, message: "项目IP地址不能为空", trigger: "blur" }
                ],
                xmipport: [
                    { required: true, message: "项目IP端口不能为空", trigger: "blur" }
                ],
                xmipuser: [
                    { required: true, message: "项目IP用户不能为空", trigger: "blur" }
                ],
                xmippwd: [
                    { required: true, message: "项目IP密码不能为空", trigger: "blur" }
                ],
                xmipchanel: [
                    { required: true, message: "项目IP通道不能为空", trigger: "blur" }
                ],
                syzt: [
                    { required: true, message: "使用状态不能为空", trigger: "blur" }
                ],
                xh: [
                    { required: true, message: "序号不能为空", trigger: "blur" }
                ],
                zzcs: [
                    { required: true, message: "zzcs不能为空", trigger: "blur" }
                ]
            }
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询场地项目编号列表 */
        getList() {
            this.loading = true;
            listCdxmbh(this.queryParams).then(response => {
                this.cdxmbhList = response.rows;
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
                nid: undefined,
                paramdm: undefined,
                paramname: undefined,
                msg: undefined,
                kszt: undefined,
                zxip: undefined,
                portms: undefined,
                mdm: undefined,
                sbxh: undefined,
                gadm: undefined,
                kskm: undefined,
                xmipaddress: undefined,
                xmipport: undefined,
                xmipuser: undefined,
                xmippwd: undefined,
                xmipchanel: undefined,
                syzt: undefined,
                xh: undefined,
                zzcs: undefined
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
            this.ids = selection.map(item => item.nid)
            this.single = selection.length !== 1
            this.multiple = !selection.length
        },
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.open = true;
            this.title = "添加场地项目编号";
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.loading = true;
            this.reset();
            const nid = row.nid || this.ids
            getCdxmbh(nid).then(response => {
                this.loading = false;
                this.form = response.data;
                this.open = true;
                this.title = "修改场地项目编号";
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    this.buttonLoading = true;
                    if (this.form.nid != null) {
                        updateCdxmbh(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        }).finally(() => {
                            this.buttonLoading = false;
                        });
                    } else {
                        addCdxmbh(this.form).then(response => {
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
            const nids = row.nid || this.ids;
            this.$modal.confirm('是否确认删除场地项目编号编号为"' + nids + '"的数据项？').then(() => {
                this.loading = true;
                return delCdxmbh(nids);
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
            this.download('w2/cdxmbh/export', {
                ...this.queryParams
            }, `cdxmbh_${new Date().getTime()}.xlsx`)
        }
    }
};
</script>
