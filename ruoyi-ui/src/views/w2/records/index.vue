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
      <el-form-item label="证件号码" prop="zjhm">
        <el-input
          v-model="queryParams.zjhm"
          placeholder="请输入证件号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考生姓名" prop="xm">
        <el-input
          v-model="queryParams.xm"
          placeholder="请输入考生姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考车编号" prop="kcbh">
        <el-select v-model="queryParams.kcbh" placeholder="请选择考车编号" clearable>
          <el-option
            v-for="item in carList"
            :key="item.id"
            :label="item.kch+'-'+item.cph"
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
      <el-form-item label="结果" prop="ksjg">
        <el-select v-model="queryParams.ksjg" placeholder="请选择结果" clearable>
          <el-option label="待考" value="0"></el-option>
          <el-option label="合格" value="1,3"></el-option>
          <el-option label="不合格" value="2,4"></el-option>
          <el-option label="考试中" value="5"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="路线" prop="line">
        <el-input
          v-model="queryParams.line"
          placeholder="请输入路线"
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
          type="primary"
          plain
          icon="el-icon-tickets"
          size="mini"
          :disabled="single"
          @click="handleDetail"
          v-hasPermi="['w2:records:detail']"
        >过程明细
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-video-play"
          size="mini"
          :disabled="single"
          @click="handleVideo"
          v-hasPermi="['w2:records:video']"
        >视频回放
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-printer"
          size="mini"
          :disabled="single"
          @click="handlePrint"
          v-hasPermi="['w2:records:print']"
        >打印
        </el-button>
      </el-col>


      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :disabled="single"
          @click="handleExportGps"
          v-hasPermi="['w2:records:exportGps']"
        >轨迹导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:records:export']"
        >页面导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-no-smoking"
          size="mini"
          :disabled="single"
          @click="handleResetExam"
          v-hasPermi="['w2:records:resetRecord']"
        >误判重考
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordsList" @selection-change="handleSelectionChange" @row-click="rowClick"
              @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="ID" align="center" prop="id" v-if="false"/>
      <el-table-column label="流水号" align="center" prop="lsh" min-width="90" v-if="isJgType(2)"/>
      <el-table-column label="证件号码" align="center" prop="zjhm" min-width="160"/>
      <el-table-column label="考生姓名" align="center" prop="xm" min-width="80"/>
      <el-table-column label="考车编号" align="center" prop="kcbh" min-width="80"/>
      <el-table-column label="考试车型" align="center" prop="kscx" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.param_car_type" :value="scope.row.kscx"/>
        </template>
      </el-table-column>
      <el-table-column label="考试原因" align="center" prop="ksyy" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.record_ksyy" :value="scope.row.ksyy"/>
        </template>
      </el-table-column>
      <el-table-column label="结果" align="center" prop="ksjg" min-width="70">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.record_ksjg" :value="scope.row.ksjg"/>
        </template>
      </el-table-column>
      <el-table-column label="预约次数" align="center" prop="yycs" min-width="80"/>
      <el-table-column label="考试日期1" align="center" prop="ksrq1" width="90">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ksrq1, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="考试时间1" align="center" prop="kssj1" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.kssj1, '{h}:{i}:{s}') }}~{{ parseTime(scope.row.jssj1, '{h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="分数1" align="center" prop="jgfs1" min-width="60"/>
      <el-table-column label="考官1" align="center" prop="ksy1" min-width="60" v-if="isJgType(2)"/>
      <el-table-column label="考试日期2" align="center" prop="ksrq2" min-width="90">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ksrq2, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="考试时间2" align="center" prop="kssj2" min-width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.kssj2, '{h}:{i}:{s}') }}~{{ parseTime(scope.row.jssj2, '{h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="分数2" align="center" prop="jgfs2" min-width="60"/>
      <el-table-column label="考官2" align="center" prop="ksy2" min-width="60" v-if="isJgType(2)"/>
      <el-table-column label="考试次数" align="center" prop="kscs" min-width="80"/>
      <!--      <el-table-column label="效验码" align="center" prop="sjjyw" />-->

      <!--      <el-table-column label="路线" align="center" prop="line" min-width="70" v-has-course="3"/>-->
      <el-table-column label="路线" align="center" prop="line" min-width="70" v-if="isCourse(3)"/>
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
        <li v-hasPermi="['w2:records:detail']"
            @click="handleDetail(activeRow)"
        >
          过程明细
        </li>
        <li v-hasPermi="['w2:records:video']"
            @click.prevent="handleVideo(activeRow)"
        >
          视频回放
        </li>
        <li v-hasPermi="['w2:records:print']"
            @click.prevent="handlePrint(activeRow)"
        >
          打印
        </li>
        <li v-hasPermi="['w2:records:exportGps']"
            @click.prevent="handleExportGps(activeRow)"
        >
          轨迹导出
        </li>
        <li v-hasPermi="['w2:records:resetRecord']"
            @click.prevent="handleResetExam(activeRow)"
        >
          误判重考
        </li>
      </ul>
    </div>

    <!-- 明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <div>
        <el-table v-loading="loading" :data="processList" height="500">
          <el-table-column label="姓名" align="center" prop="xm" min-width="80"/>
          <el-table-column label="第几次" align="center" prop="kscs" min-width="60"/>
          <el-table-column label="考试项目" align="center" prop="xmmc" min-width="200" show-overflow-tooltip>
            <template slot-scope="scope">
              {{ scope.row.ksxm == null ? '' : scope.row.ksxm + '-' }}{{ scope.row.xmmc }}
            </template>
          </el-table-column>
          <el-table-column label="图片" align="center" min-width="100">
            <template slot-scope="scope">
              <el-image
                style="width: 60px; height: 60px;"
                :src="getComponentImg(scope.row.zp)"
                :preview-src-list="[getComponentImg(scope.row.zp)]"
              />
            </template>
          </el-table-column>
          <el-table-column label="扣分" align="center" prop="kskf" min-width="80"/>
          <el-table-column label="扣分明细" align="center" prop="msg" min-width="300" show-overflow-tooltip>
            <template slot-scope="scope">
              {{ scope.row.kfdm == null ? '' : scope.row.kfdm + '-' }}{{ scope.row.msg }}
            </template>
          </el-table-column>

          <el-table-column label="考试时间" align="center" prop="kssj" min-width="150"/>
        </el-table>
      </div>
    </el-dialog>
    <!-- 视频回放对话框 -->
    <el-dialog :title="title" :visible.sync="videoOpen" width="780px" append-to-body class="video-dialog">
      <!--      <iframe :src="videoUrl" frameborder="0" width="100%" height="620px"></iframe>-->
      <back-video :video-obj="backVideoObj" :open="videoOpen" :time-range="backTime"></back-video>
    </el-dialog>
    <!-- 确认重考对话框 -->
    <el-dialog :title="title" :visible.sync="openReset" width="600px" append-to-body>
      <div class="tip-msg">
        注意请先在监管平台确认申请重考通过
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="证件号码" prop="zjhm">
          <el-input v-model="form.zjhm" placeholder="请输入证件号码" readonly/>
        </el-form-item>
        <el-form-item label="考生姓名" prop="xm">
          <el-input v-model="form.xm" placeholder="请输入考生姓名" readonly/>
        </el-form-item>
        <el-form-item label="考车编号" prop="kcbh">
          <el-input v-model="form.kcbh" placeholder="请输入考车编号" readonly/>
        </el-form-item>
        <el-form-item label="考试车型" prop="kscx">
          <el-select v-model="form.kscx" placeholder="请选择考试车型" disabled>
            <el-option
              v-for="dict in dict.type.param_car_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分数1" prop="jgfs1">
          <el-input v-model="form.jgfs1" placeholder="请输入分数1" readonly/>
        </el-form-item>
        <el-form-item label="分数2" prop="jgfs2">
          <el-input v-model="form.jgfs2" placeholder="请输入分数2" readonly/>
        </el-form-item>
        <el-form-item label="误判选择" prop="wpxz">
          <el-select v-model="form.wpxz" placeholder="请选择误判">
            <el-option
              v-for="dict in dict.type.record_wpxz"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="resetExam">确认申请</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listRecords,
  getRecords,
  delRecords,
  addRecords,
  updateRecords,
  getPrint,
  applyReset,
  getFlowList
} from "@/api/w2/records";
import {getCarList} from "@/api/w2/kcdd";
import {selectCdxmbh} from "@/api/w2/cdxmbh";
import {selectSchool} from "@/api/w2/school";
import {kcxxList} from "@/api/w2/kcxx";
import {getTodayDateRange} from "@/utils/date";
import BackVideo from "@/views/w2/video/backVideo";

export default {
  name: "Records",
  components: {BackVideo},

  dicts: ['record_ksjg', 'record_ksyy', 'record_is_print', 'param_car_type', 'record_wpxz'],
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
      videoUrl: "",
      // 是否显示弹出层
      open: false,
      openReset: false,
      videoOpen: false,
      // 驾校名称时间范围
      daterangeKsrq1: getTodayDateRange(),
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
      processList: [],
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
      backVideoObj: {},
      backTime: [],
      carList: [],
      schoolList: [],
      cdxmbhList: [],
      kcxxList: [],
    };
  },
  created() {
    this.getList();
    this.getCarList();
    this.getSchoolList();
    this.getCdxmbh();
    this.getKcxxList();
  },
  methods: {
    /**获取考车信息列表 */
    getKcxxList() {
      kcxxList().then(response => {
        this.kcxxList = response.data
      });
    },
    /**获取场地设备编号 */
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
      listRecords(this.queryParams).then(response => {
        this.recordsList = response.rows;
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
      // this.handleVideo({})
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加成绩管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getRecords(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改成绩管理";
      });
    },
    /** 明细按钮操作 **/
    handleDetail(row) {
      this.open = true;
      this.title = "考试过程明细";
      const id = row.id || this.ids
      if (!row.id) row = this.queuingList.filter(item => item.id == id)[0]
      getFlowList({bh: row.ksbh, ksrq: row.ksrq.split(" ")[0]}).then(response => {
        console.log(response.data);
        this.processList = response.data
      });
    },
    /** 视频回放操作 **/
    handleVideo(row) {
      // row = {id:1,kscs:2,kssj1:'2024-06-17 15:10:00',jssj2:'2024-06-17 16:10:00',kcbh:'05'}
      this.videoOpen = true;
      this.title = "视频回放";
      const id = row.id || this.ids
      if (!row.id) row = this.queuingList.filter(item => item.id == id)[0]
      const kcxx = this.kcxxList.filter(item => item.kch == row.kcbh)[0];
      let zx = kcxx.zxip.split(',');
      this.backVideoObj = {ip: zx[0], port: zx[1], channel: zx[2], username: kcxx.zuser, password: kcxx.zpwd}
      if (row.kscs == 1) {
        this.backTime = [row.kssj1, row.jssj1]
      } else {
        this.backTime = [row.kssj1, row.jssj2]
        console.log(this.backTime)
      }
      // if(kcxx != null){
      //   let cz = kcxx.czip.split(',');
      //   this.videoUrl = `${process.env.VUE_APP_CZ_VIDEO_PATH}/cn/WebForm.aspx?ip=${cz[0]}&user=${kcxx.cuser}&pwd=${kcxx.cpwd}&chanel=${cz[2]}&starttime=${row.kssj1}&endtime=${row.jssj1}&sfzhm=${row.zjhm}&kskm=${row.kskm}`;
      // }else{
      //   this.videoUrl = `${process.env.VUE_APP_CZ_VIDEO_PATH}/cn/WebForm.aspx?ip=172.35.72.71&user=admin&pwd=hx123456&chanel=43&starttime=${row.kssj1}&endtime=${row.jssj1}&sfzhm=${row.zjhm}&kskm=${row.kskm}`;
      // }
    },
    /** 打印按钮操作 */
    handlePrint(row) {
      const id = row.id || this.ids
      getPrint(id)
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateRecords(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addRecords(this.form).then(response => {
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
    /** 误判重考按钮操作 */
    handleResetExam(row) {
      const id = row.id || this.ids;
      this.openReset = true;
      this.title = "申请误判";
      if (!row.id) row = this.recordsList.filter(item => item.id == id)[0]
      this.form = row;
      this.$set(this.form, 'wpxz', "2");

    },
    resetExam() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.$modal.confirm(`确认申请${this.form.xm}-${this.form.wpxz}的误判吗？`).then(() => {
            this.loading = true;
            const {id, wpxz, wpyy} = this.form;
            return applyReset({id, wpxz, wpyy});
          }).then(() => {
            this.loading = false;
            this.getList();
            this.$modal.msgSuccess("申请成功");
          }).catch(() => {
          }).finally(() => {
            this.loading = false;
            this.openReset = false;
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除成绩管理编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delRecords(ids);
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
    handleExportGps(row) {
      const ids = row.id || this.ids;
      // if(!row.id) row = this.recordsList.filter(item => item.id == id)[0]

      this.download('w2/records/exportGps', {
        id: ids
      }, `records_${new Date().getTime()}.xlsx`)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('w2/records/export', {
        ...this.queryParams
      }, `records_${new Date().getTime()}.xlsx`)
    },
    getComponentImg(zp) {
      zp = 'd:\\webservice\\files\\20191021\\211021198705215828\\211021198705215828_1_03_2001.jpg';
      const xhr = new XMLHttpRequest();
      let base64Image;
      xhr.onload = () => {
        const reader = new FileReader();
        reader.onloadend = () => {
          base64Image = reader.result;
        };
        reader.readAsDataURL(xhr.response);
      };
      xhr.open('GET', zp);
      xhr.responseType = 'blob';
      xhr.send();
      return base64Image;
      // // zp = 'd:\\webservice\\files\\20191021';
      // try{
      //   return require(`${zp}`);
      //   console.log(require(zp))
      //   console.log(require('d:\\webservice\\files\\20191021\\211021198705215828\\211021198705215828_1_03_2001.jpg'))
      // }catch(e){
      //   console.log(zp);
      //   console.log(e)
      //   return '';
      // }
    }
  }
};
</script>
<style scoped lang="scss">
.app-container {
  ::v-deep #divPlugin {
    width: 100%;
    height: 380px;
  }
}

.video-dialog {
  ::v-deep .el-dialog__body {
    padding-top: 0px;
  }
}
</style>
