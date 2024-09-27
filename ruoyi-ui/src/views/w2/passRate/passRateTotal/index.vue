<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考试日期">
        <el-date-picker
          v-model="daterangeKsrq1"
          style="width: 215px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="考车编号" prop="kcbh">
        <el-select v-model="queryParams.kcbh" placeholder="请选择考车编号" clearable>
          <el-option
            v-for="item in carList"
            :key="item.id"
            :label="item.kch"
            :value="item.kch"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="考试车型" prop="kscx">
        <el-select v-model="queryParams.kscx" placeholder="请选择考试车型" clearable>
          <el-option
            v-for="dict in dict.type.param_car_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="考试原因" prop="ksyy">
        <el-select v-model="queryParams.ksyy" placeholder="请选择考试原因" clearable>
          <el-option
            v-for="dict in dict.type.record_ksyy"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="考试员" prop="ksy1">
        <el-input
          v-model="queryParams.ksy1"
          placeholder="请输入考试员1"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="路线" prop="line">
        <el-input
          v-model="queryParams.line"
          placeholder="请输入路线"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="培训单位" prop="jxdm">
        <el-select v-model="queryParams.jxdm" placeholder="请选择培训单位" clearable>
          <el-option
            v-for="item in schoolList"
            :key="item.nid"
            :label="item.paramdm+':'+item.paramname"
            :value="item.paramdm"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="场地设备" prop="sbbh">
        <el-select v-model="queryParams.sbbh" placeholder="请选择场地设备编号" clearable>
          <el-option
            v-for="item in cdxmbhList"
            :key="item.nid"
            :label="item.sbxh+':'+item.paramname"
            :value="item.sbxh"
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:passRate:exportTotal']"
        >导出
        </el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordsList" @selection-change="handleSelectionChange" @row-click="rowClick"
              @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" v-if="false"/>
      <el-table-column label="统计对象" align="center" prop="name" min-width="120"/>
      <el-table-column label="合计" align="center" prop="total" min-width="80"/>
      <el-table-column label="准驾A1" align="center" prop="a1" min-width="80"/>
      <el-table-column label="准驾A2" align="center" prop="a2" min-width="80"/>
      <el-table-column label="准驾A3" align="center" prop="a3" min-width="80"/>
      <el-table-column label="准驾B1" align="center" prop="b1" min-width="80"/>
      <el-table-column label="准驾B2" align="center" prop="b2" min-width="80"/>
      <el-table-column label="准驾C1" align="center" prop="c1" min-width="80"/>
      <el-table-column label="准驾C2" align="center" prop="c2" min-width="80"/>
      <el-table-column label="准驾C5" align="center" prop="c5" min-width="80"/>
      <el-table-column label="准驾C6" align="center" prop="c6" min-width="80"/>

    </el-table>


  </div>
</template>

<script>
import {getCarList} from "@/api/w2/kcdd";
import {selectSchool} from "@/api/w2/school";
import {selectCdxmbh} from "@/api/w2/cdxmbh";
import {totalListRecords} from "@/api/w2/passRate";

export default {
  name: "passRateTotal",
  dicts: ['record_ksjg', 'record_ksyy', 'record_is_print', 'param_car_type'],
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
      // 成绩管理表格数据
      recordsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openReset: false,
      // 驾校名称时间范围
      daterangeKsrq1: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        xm: undefined,
        kcbh: undefined,
        zjhm: undefined,
        kscx: undefined,
        ksyy: undefined,
        ksrq1: undefined,
        ksy1: undefined,
        sfprint: undefined,
        ksjg: undefined,
        line: undefined,
        jxdm: undefined,
        sbbh: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          {required: true, message: "ID不能为空", trigger: "blur"}
        ],
        wpyy: [
          {required: true, message: "误判原因不能为空", trigger: "blur"}
        ],
        wpxz: [
          {required: true, message: "误判选择不能为空", trigger: "change"}
        ],
      },
      carList: [],
      schoolList: [],
      cdxmbhList: [],
      wpOptions: ["第2次", "第1次", "2次全"]
    };
  },
  created() {
    this.getList();
    this.getCarList();
    this.getCdxmbh();
    this.getSchoolList();
  },
  methods: {
    getCdxmbh() {
      selectCdxmbh().then(response => {
        this.cdxmbhList = response.data
      });
    },
    /** 获取驾校列表 **/
    getSchoolList() {
      selectSchool({type: 3}).then(response => {
        this.schoolList = response.data
      });
    },
    /** 获取车辆列表 **/
    getCarList() {
      getCarList().then(response => {
        this.carList = response.data;
      });
    },
    /** 查询成绩管理列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeKsrq1 && '' != this.daterangeKsrq1) {
        this.queryParams.params["beginKsrq1"] = this.daterangeKsrq1[0];
        this.queryParams.params["endKsrq1"] = this.daterangeKsrq1[1];
      }
      totalListRecords(this.queryParams).then(response => {
        this.recordsList = response.rows;
        // .map((item)=>{
        //   if(item.type == 100){
        //     item.total = item.total/item.type + "%";
        //     item.a1 = item.a1/item.type + "%";
        //     item.a2 = item.a2/item.type + "%";
        //     item.a3 = item.a3/item.type + "%";
        //     item.b1 = item.b1/item.type + "%";
        //     item.b2 = item.b2/item.type + "%";
        //     item.c1 = item.c1/item.type + "%";
        //     item.c2 = item.c2/item.type + "%";
        //     item.c5 = item.c5/item.type + "%";
        //     item.c6 = item.c6/item.type + "%";
        //   }
        //   return item;
        // });
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.openReset = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        xm: undefined,
        kcbh: undefined,
        zjhm: undefined,
        zkzh: undefined,
        kscx: undefined,
        ksyy: undefined,
        yycs: undefined,
        ksrq1: undefined,
        kssj1: undefined,
        jssj1: undefined,
        kfxx1: undefined,
        jgfs1: undefined,
        ksy1: undefined,
        ksrq2: undefined,
        kssj2: undefined,
        jssj2: undefined,
        kfxx2: undefined,
        jgfs2: undefined,
        ksy2: undefined,
        kscs: undefined,
        sjjyw: undefined,
        sfprint: undefined,
        ksjg: undefined,
        line: undefined,
        jxmc: undefined,
        ksbh: undefined,
        kscj1: undefined,
        scf1: undefined,
        kscj2: undefined,
        scf2: undefined,
        kscc: undefined,
        czy: undefined,
        zt: undefined,
        ksxm: undefined,
        sqbs: undefined,
        sqrq: undefined,
        sqyy: undefined,
        sqr: undefined,
        spr: undefined,
        sprq: undefined,
        sqr1: undefined,
        sfqk: undefined,
        zjzp: undefined,
        jbzp: undefined,
        dycs: undefined,
        flag: undefined,
        kcxh: undefined,
        ksrq: undefined,
        kfss1: undefined,
        kfss2: undefined,
        sszp: undefined,
        sSafe: undefined,
        sSafeZjhm: undefined,
        jxdm: undefined,
        lsh: undefined,
        kchp: undefined,
        jbr: undefined,
        glbm: undefined,
        bcyykscs: undefined,
        jkSszpstatus: undefined,
        sfyk: undefined,
        sZjmc: undefined,
        sXmhz: undefined,
        ykrq: undefined,
        ykflag: undefined,
        ykdjc: undefined,
        line2: undefined,
        sCity: undefined,
        iDiff: undefined,
        dcrk: undefined,
        ksxmdown: undefined,
        kskm: undefined,
        hlSignUpload: undefined,
        cjupload: undefined,
        autojudge1: undefined,
        autojudge2: undefined,
        kszt: undefined
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
      this.daterangeKsrq1 = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('w2/passRate/exportTotal', {
        ...this.queryParams
      }, `总合格率_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
