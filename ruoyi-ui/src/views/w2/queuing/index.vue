<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
      <el-form-item label="签到状态" prop="sign">
        <el-select v-model="queryParams.sign" placeholder="请选择签到状态" clearable>
          <el-option
            v-for="dict in dict.type.queue_sign"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="分车状态" prop="zt">
        <el-select v-model="queryParams.zt" placeholder="请选择分车状态" clearable>
          <el-option
            v-for="dict in dict.type.queue_zt"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="考车信息" prop="kcbh">
        <el-select v-model="queryParams.kcbh" placeholder="请选择考车" clearable>
          <el-option
            v-for="item in carList"
            :key="item.cph"
            :label="item.kch+'-'+item.cph"
            :value="item.cph"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="考试线路" prop="rLine" v-has-course="3">
        <el-select v-model="queryParams.rLine" placeholder="请选择考试路线" clearable>
          <el-option
            v-for="item in lineList"
            :key="item.line"
            :label="item.line"
            :value="item.line"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="考试状态" prop="kszt">
        <el-select v-model="queryParams.kszt" placeholder="请选择考试状态" clearable>
          <el-option
            v-for="dict in dict.type.queue_kszt"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
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
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdateKscx"
          v-hasPermi="['w2:queuing:kscx']"
        >修改车型</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdateInfo"
          v-hasPermi="['w2:queuing:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5" v-if="false">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['w2:queuing:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:queuing:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-tooltip content="长按行可以上下拖动排序哦" placement="right" effect="light">
          <el-button
            type="primary"
            plain
            icon="el-icon-d-caret"
            size="mini"
            v-hasPermi="['w2:queuing:upDown']"
          >排序</el-button>
        </el-tooltip>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="dragTable" row-key="id"  v-loading="loading" :data="queuingList" @selection-change="handleSelectionChange" @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" v-if="false"/>
      <el-table-column label="证件号码" align="center" prop="zjhm" min-width="160"/>
      <el-table-column label="考生姓名" align="center" prop="xm" min-width="100"/>
      <el-table-column label="排队序号" align="center" prop="bdxh" min-width="80"/>
      <el-table-column label="缴费状态" align="center" prop="sqks" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.queue_sqks" :value="scope.row.sqks"/>
        </template>
      </el-table-column>
      <el-table-column label="签到状态" align="center" prop="sign" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.queue_sign" :value="scope.row.sign"/>
        </template>
      </el-table-column>
      <el-table-column label="分车状态" align="center" prop="zt" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.queue_zt" :value="scope.row.zt"/>
        </template>
      </el-table-column>
      <el-table-column label="考车编号" align="center" prop="kcbh" min-width="80"/>
      <el-table-column label="车牌号码" align="center" prop="kchp" min-width="100"/>
      <el-table-column label="考试车型" align="center" prop="kscx" min-width="80"/>
      <el-table-column label="线路" align="center" prop="rline" min-width="60"  v-if="isCourse(3)"/>
      <el-table-column label="夜考" align="center" prop="sfyk" min-width="60"  v-if="isCourse(3)">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_status" :value="scope.row.sfyk"/>
        </template>
      </el-table-column>
      <el-table-column label="考试状态" align="center" prop="kszt" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.queue_kszt" :value="scope.row.kszt"/>
        </template>
      </el-table-column>
      <el-table-column label="第几次" align="center" prop="djc" min-width="60">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.queue_djc" :value="scope.row.djc"/>
        </template>
      </el-table-column>
      <el-table-column label="成绩" align="center" prop="score" min-width="60"/>
      <el-table-column label="分配项目" align="left" prop="ksxm" min-width="600" show-overflow-tooltip>
        <template slot-scope="scope">
          {{getWcxmNames(scope.row.ksxm)}}
        </template>
      </el-table-column>
      <el-table-column label="完成项目" align="left" prop="wcxm" min-width="600" show-overflow-tooltip>
        <template slot-scope="scope">
          {{getWcxmNames(scope.row.wcxm)}}
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
    <!--    鼠标右键菜单-->
    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:queuing:kscx']"
            @click="handleUpdateKscx(activeRow)"
        >
          修改车型
        </li>
        <li v-hasPermi="['w2:queuing:edit']"
            @click.prevent="handleUpdateInfo(activeRow)"
        >
          修改
        </li>
      </ul>
    </div>

    <!-- 修改车型信息对话框 -->
    <el-dialog :title="title" :visible.sync="updateKscx" width="500px" append-to-body>
      <el-form ref="formKscx" :model="updateKscxForm" :rules="rulesKscx" label-width="80px">
        <el-form-item label="考试车型" prop="kscx">
          <el-select v-model="updateKscxForm.kscx" placeholder="请选择考试车型">
            <el-option
              v-for="dict in dict.type.param_car_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitHandleUpdateKscx">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 修改信息对话框 -->
    <el-dialog :title="title" :visible.sync="updateInfo" width="500px" append-to-body>
      <el-form ref="formInfo" :model="updateForm" :rules="rules" label-width="80px">
        <el-form-item label="考试项目" prop="ksxm">
          <el-checkbox-group v-model="updateForm.ksxm">
            <template  v-for="(item,index) in xmdmList">
              <el-checkbox :key="item.id" :label="item.custxh" v-if="item.show">{{item.name}}</el-checkbox>
              <el-divider content-position="left" v-if="item.id == 10000">科目二</el-divider>
              <el-divider content-position="left" v-if="item.id == 30000">科目三</el-divider>
            </template>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="考试状态" prop="kszt">
          <el-select v-model="updateForm.kszt" placeholder="请选择考试状态">
            <el-option
              v-for="dict in dict.type.queue_kszt"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="第几次" prop="djc">
          <el-select v-model="updateForm.djc" placeholder="请选择第几次">
            <el-option
              v-for="dict in dict.type.queue_djc"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitHandleUpdateInfo">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改排队信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考试编号" prop="ksbh">
          <el-input v-model="form.ksbh" placeholder="请输入考试编号" />
        </el-form-item>
        <el-form-item label="考生姓名" prop="xm">
          <el-input v-model="form.xm" placeholder="请输入考生姓名" />
        </el-form-item>
        <el-form-item label="证件号码" prop="zjhm">
          <el-input v-model="form.zjhm" placeholder="请输入证件号码" />
        </el-form-item>
        <el-form-item label="驾校名称" prop="jxmc">
          <el-input v-model="form.jxmc" placeholder="请输入驾校名称" />
        </el-form-item>
        <el-form-item label="考车编号" prop="kcbh">
          <el-input v-model="form.kcbh" placeholder="请输入考车编号" />
        </el-form-item>
        <el-form-item label="车牌号码" prop="kchp">
          <el-input v-model="form.kchp" placeholder="请输入车牌号码" />
        </el-form-item>
        <el-form-item label="线路" prop="rLine">
          <el-input v-model="form.rLine" placeholder="请输入线路" />
        </el-form-item>
        <el-form-item label="锁定状态" prop="iLock">
          <el-input v-model="form.iLock" placeholder="请输入锁定状态" />
        </el-form-item>
        <el-form-item label="监管分车" prop="sign">
          <el-input v-model="form.sign" placeholder="请输入监管分车" />
        </el-form-item>
        <el-form-item label="考试状态" prop="kszt">
          <el-select v-model="form.kszt" placeholder="请选择考试状态">
            <el-option
              v-for="dict in dict.type.queue_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="第几次" prop="djc">
          <el-input v-model="form.djc" placeholder="请输入第几次" />
        </el-form-item>
        <el-form-item label="缴费" prop="sqks">
          <el-input v-model="form.sqks" placeholder="请输入缴费" />
        </el-form-item>
        <el-form-item label="成绩" prop="score">
          <el-input v-model="form.score" placeholder="请输入成绩" />
        </el-form-item>
        <el-form-item label="夜考" prop="sfyk">
          <el-input v-model="form.sfyk" placeholder="请输入夜考" />
        </el-form-item>
        <el-form-item label="分配项目" prop="ksxm">
          <el-input v-model="form.ksxm" placeholder="请输入分配项目" />
        </el-form-item>
        <el-form-item label="完成项目" prop="wcxm">
          <el-input v-model="form.wcxm" placeholder="请输入完成项目" />
        </el-form-item>
        <el-form-item label="考试车型" prop="kscx">
          <el-input v-model="form.kscx" placeholder="请输入考试车型" />
        </el-form-item>
        <el-form-item label="考官姓名" prop="kgname">
          <el-input v-model="form.kgname" placeholder="请输入考官姓名" />
        </el-form-item>
        <el-form-item label="考官证件" prop="kg">
          <el-input v-model="form.kg" placeholder="请输入考官证件" />
        </el-form-item>
        <el-form-item label="安全员" prop="sSafe">
          <el-input v-model="form.sSafe" placeholder="请输入安全员" />
        </el-form-item>
        <el-form-item label="安全员证件" prop="sSafeZjhm">
          <el-input v-model="form.sSafeZjhm" placeholder="请输入安全员证件" />
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
import {listQueuing, getQueuing, delQueuing, addQueuing, updateQueuing, updateKscx, updateInfo,upDown} from "@/api/w2/queuing";
import {selectKsxmdmJg, selectMapKsxmdmJg} from "@/api/w2/ksxmdmJg";
import {selectLineconfig} from "@/api/w2/lineconfig";
import {kcxxList} from "@/api/w2/kcxx";
import Sortable from "sortablejs";

export default {
  name: "Queuing",
  dicts: ['queue_status','queue_djc','queue_is_lock','param_car_type','queue_zt','queue_sign','queue_kszt','queue_sqks','sys_status'],
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
      // 排队信息表格数据
      queuingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ksbh: undefined,
        xm: undefined,
        zjhm: undefined,
        kcbh: undefined,
        kchp: undefined,
        rLine: undefined,
        kszt: undefined,
        kscx: undefined,
        kgname: undefined,
        zt:undefined,
        sign:undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rulesKscx: {
        id: [
          { required: true, message: "id不能为空", trigger: "blur" }
        ],
        kscx: [
          { required: true, message: "考试车型不能为空", trigger: "blur" }
        ],
      },
      rules: {
        id: [
          { required: true, message: "id不能为空", trigger: "blur" }
        ],
        kscx: [
          { required: true, message: "考试车型不能为空", trigger: "blur" }
        ],
      },
      // 路线数据
      lineList: [],
      // 车辆数据
      carList: [],
      // 项目代码
      xmdmList: [],
      xmdmMap:{},
      // 考试车型
      updateKscxForm:{
        id:undefined,
        kscx: undefined
      },
      // 更新
      updateForm:{
        id:undefined,
        djc:undefined,
        kszt:undefined,
        ksxm:[]
      },
      updateKscx:false,
      updateInfo:false,
      ws:null
    };
  },
  mounted(){
    this.rowDrop();
  },
  created() {
    this.getList();
    this.getLineList();
    this.getCarList();
    this.getXmdmList();
    // websocket
    this.WebSocket_StatusCheck();
  },
  methods: {
    rowDrop() {
      // const tbody = document.querySelector('.el-table__body-wrapper tbody')
      const tbody = this.$refs.dragTable.$el.querySelectorAll(".el-table__body-wrapper > table > tbody")[0];
      const _this = this
      Sortable.create(tbody, {
        onEnd({ newIndex, oldIndex }) {
          if(newIndex == oldIndex) return;
          console.log(newIndex,oldIndex);
          const beforeRow = _this.queuingList[newIndex]
          const currRow = _this.queuingList.splice(oldIndex, 1)[0]
          _this.queuingList.splice(newIndex, 0, currRow)

          console.log(_this.queuingList);
          // let {id,kcbh} = {...currRow}
          // let obj = currRow.map(({id,kcbh}) => ({id,kcbh}));
          upDown({id:currRow.id,newIndex:beforeRow.bdxh,oldIndex:currRow.bdxh}).then(response => {
            console.log("成功");
            _this.getList();
          });
          // //   拖动后获取newIdex
          // let arr = Array.from(_this.apiObj)
          // _this.apiObjDrag = arr.map((item, index) => {
          //   return {
          //     id: item.id,
          //     dictSort: index,
          //   }
          // })
        },
      })
    },
    // 获取车辆列表
    getCarList(){
      kcxxList().then(response => {
        this.carList = response.data
      })
    },
    // 获取线路列表
    getLineList() {
      selectLineconfig().then(response => {
        this.lineList = response.data
      });
    },
    getXmdmList() {
      selectKsxmdmJg({kskm:this.$store.getters.configCourses}).then(response => {
        this.xmdmList = response.data
        this.xmdmList.forEach((item,index) =>{
          if(this.xmdmMap.hasOwnProperty(item.custxh)){
            item.show = false
          }else{
            item.show = true
          }
          this.xmdmMap[item.custxh] = item.name;
        })
      });
    },
    /** 查询排队信息列表 */
    getList() {
      this.loading = true;
      listQueuing(this.queryParams).then(response => {
        this.queuingList = response.rows;
        console.log(this.queuingList)
        this.total = response.total;
        this.loading = false;
        this.$WebSocket.Send(JSON.stringify(this.queryParams))
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.updateKscx = false;
      this.updateInfo = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        ksbh: undefined,
        xm: undefined,
        zjhm: undefined,
        jxmc: undefined,
        kcbh: undefined,
        kchp: undefined,
        rLine: undefined,
        iLock: undefined,
        sign: undefined,
        kszt: undefined,
        djc: undefined,
        sqks: undefined,
        score: undefined,
        sfyk: undefined,
        ksxm: undefined,
        wcxm: undefined,
        kscx: undefined,
        kgname: undefined,
        kg: undefined,
        sSafe: undefined,
        sSafeZjhm: undefined,
        zkzh: undefined,
        ykxms: undefined,
        zkxms: undefined,
        bdxh: undefined,
        kscs: undefined,
        ykcs: undefined,
        wcxms: undefined,
        kscj: undefined,
        kssj: undefined,
        cdxmbh: undefined,
        sfyz: undefined,
        kslx: undefined,
        jxdm: undefined,
        ksrq: undefined,
        zt: undefined,
        sqsj: undefined,
        qxlx: undefined,
        kcxh: undefined,
        flag: undefined,
        jszt: undefined,
        lsh: undefined,
        zkxmdm: undefined,
        finger: undefined,
        zsfhg: undefined,
        zcs: undefined,
        kfxm: undefined,
        kcbs: undefined,
        fieldid: undefined,
        kg2: undefined,
        kscc: undefined,
        signcontent: undefined,
        ksyy: undefined,
        jbr: undefined,
        glbm: undefined,
        yycs: undefined,
        bcyykscs: undefined,
        linecode: undefined,
        xmkssj: undefined,
        message: undefined,
        iVoiceTimes: undefined,
        dVoiceDate: undefined,
        iVoiceReady: undefined,
        cwcs: undefined,
        kskm: undefined
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
    /** 修改操作 */
    handleUpdateInfo(row) {
      const id = row.id || this.ids[0]

      if(!row.id) row = this.queuingList.filter(item => item.id == id)[0]
      this.updateForm ={
        id,
        kszt:row.kszt,
        djc:row.djc,
        ksxm:row.ksxm.split(",")
      };
      this.title = `修改信息—${row.xm}—${row.zjhm}`;
      this.updateInfo = true;
    },
    /** 提交更新按钮 */
    submitHandleUpdateInfo() {
      this.$refs["formInfo"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.updateForm.id != null) {
            updateInfo(this.updateForm).then(response => {
              this.$modal.msgSuccess("更新成功");
              this.updateInfo = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 更新考试车型按钮操作 */
    handleUpdateKscx(row) {
      const id = row.id || this.ids[0]

      if(!row.id) row = this.queuingList.filter(item => item.id == id)[0]
      this.updateKscxForm ={
        id,
        kscx:row.kscx
      };
      this.title = `修改车型—${row.xm}—${row.zjhm}`;
      this.updateKscx = true;
    },
    /** 提交更新考试车型按钮 */
    submitHandleUpdateKscx() {
      this.$confirm('您确定修改考生的排队考试车型么?改完车型需要手工分配考试项目', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs["formKscx"].validate(valid => {
          if (valid) {
            this.buttonLoading = true;
            if (this.updateKscxForm.id != null) {
              updateKscx(this.updateKscxForm).then(response => {
                this.$modal.msgSuccess("更新车型成功");
                this.updateKscx = false;
                this.getList();
              }).finally(() => {
                this.buttonLoading = false;
              });
            }
          }
        });
      })

    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加排队信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getQueuing(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改排队信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateQueuing(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addQueuing(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除排队信息编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delQueuing(ids);
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
      this.download('w2/queuing/export', {
        ...this.queryParams
      }, `queuing_${new Date().getTime()}.xlsx`)
    },
    //完成项目拼接字符喜欢
    getWcxmNames(wcxm){
      if(wcxm == null) return '';
      const wcxmArr = [];
      wcxm.split(",").forEach(id => {
        if(id != ""){
          if(this.xmdmMap[id] != null) wcxmArr.push(id+":"+this.xmdmMap[id])
        }
      });
      return wcxmArr.join(",");
    },
    // 1、WebSocket连接状态检测：
    WebSocket_StatusCheck() {
       this.WebSocketINI()
    },

    // 2、WebSocket初始化：
    WebSocketINI() {
      // 1、浏览器是否支持WebSocket检测
      if (!('WebSocket' in window)) {
        console.log('您的浏览器不支持WebSocket!')
        return
      }

      // 2、从后台提取WebScoket服务器连接地址：根据自己业务接口获取 或者直接跳过 下面直接写死
      const wsSrverAddress = window.globalConfig.VUE_APP_BASE_API +"/websocket/queue/"+10 //可以直接赋值如：ws://127.0.0.1:1234

      // 3、创建Websocket连接
      this.ws = new WebSocket(wsSrverAddress)

      // 4、全局保存WebSocket操作句柄：main.js 全局引用
      this.$WebSocket.WebsocketINI(this.ws)

      // 5、WebSocket连接成功提示
      this.ws.onopen = function(e) {
        console.log('webcoket连接成功')
      }

      //6、连接失败提示
      this.ws.onclose = function(e) {
        console.log('webcoket连接关闭：', e)
      }

      this.ws.onmessage = function(e) {
        console.log("接收到排队消息")
        const response = JSON.parse(e.data);
        console.log(response)
        this.queuingList = response.rows;
        this.total = response.total;
      }
    }
  },
  beforeDestroy(){
    if (this.ws) {
      this.ws.close();
    }
  }
};
</script>
