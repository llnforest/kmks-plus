<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考车序号" prop="kch">
        <el-input
          v-model="queryParams.kch"
          placeholder="请输入考车序号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考车名称" prop="cph">
        <el-input
          v-model="queryParams.cph"
          placeholder="请输入考车名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车型名称" prop="kcmc">
        <el-input
          v-model="queryParams.kcmc"
          placeholder="请输入车型名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
      <el-form-item label="考车状态" prop="zt">
        <el-select v-model="queryParams.zt" placeholder="请选择考车状态" clearable>
          <el-option
            v-for="dict in dict.type.param_car_status"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['w2:kcxx:add']"
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
          v-hasPermi="['w2:kcxx:edit']"
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
          v-hasPermi="['w2:kcxx:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['w2:kcxx:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="kcxxList" @selection-change="handleSelectionChange"  @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" v-if="false"/>
      <el-table-column label="考车序号" align="center" prop="kch" min-width="80"/>
      <el-table-column label="考车名称" align="center" prop="cph" min-width="80"/>
      <el-table-column label="车型名称" align="center" prop="kcmc" min-width="80"/>
      <el-table-column label="考试车型" align="center" prop="kscx" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.param_car_type" :value="scope.row.kscx"/>
        </template>
      </el-table-column>
      <el-table-column label="考车状态" align="center" prop="zt" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.param_car_status" :value="scope.row.zt"/>
        </template>
      </el-table-column>
      <el-table-column label="项目序号" align="center" prop="xmxh"  min-width="280"/>
      <el-table-column label="考试路线" align="center" prop="line"  min-width="80" v-if="false"/>
      <el-table-column label="车载视频IP" align="center" prop="czip"  min-width="180"/>
      <el-table-column label="车载用户" align="center" prop="cuser"  min-width="80"/>
      <el-table-column label="车载密码" align="center" prop="cpwd"  min-width="100"/>
      <el-table-column label="中心视频IP" align="center" prop="zxip"  min-width="180"/>
      <el-table-column label="中心用户" align="center" prop="zuser"  min-width="80"/>
      <el-table-column label="中心密码" align="center" prop="zpwd"  min-width="100"/>
      <el-table-column label="四合一视频IP" align="center" prop="fourip"  min-width="180"/>
      <el-table-column label="四合一用户" align="center" prop="fouruser"  min-width="100"/>
      <el-table-column label="四合一密码" align="center" prop="fourpwd"  min-width="100"/>
      <el-table-column label="车辆ip" align="center" prop="carIp"  min-width="110"/>
      <el-table-column label="车辆mac" align="center" prop="carMac"  min-width="180"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:kcxx:edit']"
            @click="handleUpdate(activeRow)"
        >
          修改
        </li>
        <li v-hasPermi="['w2:kcxx:remove']"
            @click.prevent="handleDelete(activeRow)"
        >
          删除
        </li>
      </ul>
    </div>
    <!-- 添加或修改过程明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="考车序号" prop="kch">
          <el-input v-model="form.kch" placeholder="请输入考车序号" />
        </el-form-item>
        <el-form-item label="考车名称" prop="cph">
          <el-input v-model="form.cph" placeholder="请输入考车名称" />
        </el-form-item>
        <el-form-item label="车型名称" prop="kcmc">
          <el-input v-model="form.kcmc" placeholder="请输入车型名称" />
        </el-form-item>
        <el-form-item label="考试车型" prop="kscx">
          <el-select v-model="form.kscx" placeholder="请选择考试车型">
            <el-option
              v-for="dict in dict.type.param_car_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考车状态" prop="zt">
          <el-select v-model="form.zt" placeholder="请选择考车状态">
            <el-option
              v-for="dict in dict.type.param_car_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目序号" prop="xmxh">
          <el-input v-model="form.xmxh" placeholder="请输入项目序号" />
        </el-form-item>
        <el-form-item label="考试路线" prop="line" v-if="false">
          <el-input v-model="form.line" placeholder="请输入考试路线" />
        </el-form-item>
        <el-form-item label="车载视频IP" prop="czHost">
          <el-input v-model="form.czHost" placeholder="请输入车载视频IP" />
        </el-form-item>
        <el-form-item label="车载视频端口" prop="czPort">
          <el-input v-model="form.czPort" placeholder="请输入车载视频端口" />
        </el-form-item>
        <el-form-item label="车载视频通道" prop="czChannel">
          <el-input v-model="form.czChannel" placeholder="请输入车载视频通道" />
        </el-form-item>
        <el-form-item label="车载用户" prop="cuser">
          <el-input v-model="form.cuser" placeholder="请输入车载用户" />
        </el-form-item>
        <el-form-item label="车载密码" prop="cpwd">
          <el-input v-model="form.cpwd" placeholder="请输入车载密码" />
        </el-form-item>
        <el-form-item label="中心视频IP" prop="zxHost">
          <el-input v-model="form.zxHost" placeholder="请输入中心视频IP" />
        </el-form-item>
        <el-form-item label="中心视频端口" prop="zxPort">
          <el-input v-model="form.zxPort" placeholder="请输入中心视频端口" />
        </el-form-item>
        <el-form-item label="中心视频通道" prop="zxChannel">
          <el-input v-model="form.zxChannel" placeholder="请输入中心视频通道" />
        </el-form-item>
        <el-form-item label="中心用户" prop="zuser">
          <el-input v-model="form.zuser" placeholder="请输入中心视频用户" />
        </el-form-item>
        <el-form-item label="中心密码" prop="zpwd">
          <el-input v-model="form.zpwd" placeholder="请输入中心视频密码" />
        </el-form-item>
        <el-form-item label="四合一视频IP" prop="fourHost">
          <el-input v-model="form.fourHost" placeholder="请输入四合一视频IP" />
        </el-form-item>
        <el-form-item label="四合一视频端口" prop="fourPort">
          <el-input v-model="form.fourPort" placeholder="请输入四合一视频端口" />
        </el-form-item>
        <el-form-item label="四合一视频通道" prop="fourChannel">
          <el-input v-model="form.fourChannel" placeholder="请输入四合一视频通道" />
        </el-form-item>
        <el-form-item label="四合一用户" prop="fouruser">
          <el-input v-model="form.fouruser" placeholder="请输入四合一视频用户" />
        </el-form-item>
        <el-form-item label="四合一密码" prop="fourpwd">
          <el-input v-model="form.fourpwd" placeholder="请输入四合一视频密码" />
        </el-form-item>
        <el-form-item label="车辆ip" prop="carIp">
          <el-input v-model="form.carIp" placeholder="请输入车辆ip" />
        </el-form-item>
        <el-form-item label="车辆mac" prop="carMac">
          <el-input v-model="form.carMac" placeholder="请输入车辆mac" />
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
import { listKcxx, getKcxx, delKcxx, addKcxx, updateKcxx } from "@/api/w2/kcxx";
import {selectCarModel} from "@/api/w2/carModel";

export default {
  name: "Kcxx",
  dicts: ['param_car_type', 'param_car_status'],
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
      // 过程明细表格数据
      kcxxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        kch: undefined,
        cph: undefined,
        kcmc: undefined,
        kscx: undefined,
        zt: undefined,
      },
      carModelList:{},
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "ID不能为空", trigger: "blur" }
        ],
        kch: [
          { required: true, message: "考车序号不能为空", trigger: "blur" }
        ],
        cph: [
          { required: true, message: "考车名称不能为空", trigger: "blur" }
        ],
        kcmc: [
          { required: true, message: "车型名称不能为空", trigger: "blur" }
        ],
        kscx: [
          { required: true, message: "考试车型不能为空", trigger: "change" }
        ],
        zt: [
          { required: true, message: "考车状态不能为空", trigger: "change" }
        ],
        xmxh: [
          { required: true, message: "项目序号不能为空", trigger: "blur" }
        ],
        line: [
          { required: true, message: "考试路线不能为空", trigger: "blur" }
        ],
        czHost: [
          { required: true, message: "车载视频IP不能为空", trigger: "blur" }
        ],czPort: [
          { required: true, message: "车载视频端口不能为空", trigger: "blur" }
        ],czChannel: [
          { required: true, message: "车载视频通道不能为空", trigger: "blur" }
        ],
        cuser: [
          { required: true, message: "车载用户不能为空", trigger: "blur" }
        ],
        cpwd: [
          { required: true, message: "车载密码不能为空", trigger: "blur" }
        ],
        zxHost: [
          { required: true, message: "中心视频IP不能为空", trigger: "blur" }
        ],zxPort: [
          { required: true, message: "中心视频端口不能为空", trigger: "blur" }
        ],zxChannel: [
          { required: true, message: "中心视频通道不能为空", trigger: "blur" }
        ],
        zuser: [
          { required: true, message: "中心视频用户不能为空", trigger: "blur" }
        ],
        zpwd: [
          { required: true, message: "中心视频密码不能为空", trigger: "blur" }
        ],
        fourHost: [
          { required: true, message: "四合一视频IP不能为空", trigger: "blur" }
        ],fourPort: [
          { required: true, message: "四合一视频端口不能为空", trigger: "blur" }
        ],fourChannel: [
          { required: true, message: "四合一视频通道不能为空", trigger: "blur" }
        ],
        fouruser: [
          { required: true, message: "四合一视频用户不能为空", trigger: "blur" }
        ],
        fourpwd: [
          { required: true, message: "四合一视频密码不能为空", trigger: "blur" }
        ],
        carMac: [
          { required: true, message: "车辆mac不能为空", trigger: "blur" }
        ],
        carVersion: [
          { required: true, message: "车载评判软件版本不能为空", trigger: "blur" }
        ],
        carIp: [
          { required: true, message: "车辆ip不能为空", trigger: "blur" }
        ],
        carModel1Name: [
          { required: true, message: "车模名称不能为空", trigger: "change" }
        ],
      }
    };
  },
  watch:{
      'form.czip':{
        handler(newVal, oldVal) {
          if(newVal !== undefined){
            let valArr = newVal.split(',');
            this.$set(this.form,'czPort',valArr[1])
            this.$set(this.form,'czHost',valArr[0])
            this.$set(this.form,'czChannel',valArr.slice(2).join(','))
          }
        },
        deep: true,
      },
    'form.zxip':{
      handler(newVal, oldVal) {
        if(newVal !== undefined){
          let valArr = newVal.split(',');
          this.$set(this.form,'zxPort',valArr[1])
          this.$set(this.form,'zxHost',valArr[0])
          this.$set(this.form,'zxChannel',valArr.slice(2).join(','))
        }
      },
      deep: true,
    },
    'form.fourip':{
      handler(newVal, oldVal) {
        if(newVal !== undefined){
          let valArr = newVal.split(',');
          this.$set(this.form,'fourPort',valArr[1])
          this.$set(this.form,'fourHost',valArr[0])
          this.$set(this.form,'fourChannel',valArr.slice(2).join(','))
        }
      },
      deep: true,
    }
  },
  created() {
    this.getList();
    this.getCarModelList();
  },
  methods: {
    /** 查询过程明细列表 */
    getList() {
      this.loading = true;
      listKcxx(this.queryParams).then(response => {
        this.kcxxList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getCarModelList(){
      selectCarModel().then(response =>{
        this.carModelList = response.data
      })
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
        kch: undefined,
        cph: undefined,
        kcmc: undefined,
        kscx: undefined,
        zt: undefined,
        xmxh: undefined,
        line: undefined,
        czip: undefined,
        czHost: undefined,
        czPort: undefined,
        czChannel: undefined,
        cuser: undefined,
        cpwd: undefined,
        zxip: undefined,
        zuser: undefined,
        zpwd: undefined,
        fourip: undefined,
        fouruser: undefined,
        fourpwd: undefined,
        carMac: undefined,
        carVersion: undefined,
        carIp: undefined,
        carModel1Name: undefined,
        ksbh: undefined,
        mdzt: undefined,
        mdrs: undefined,
        zc: undefined,
        lxbs: undefined,
        msg: undefined,
        fieldid: undefined,
        fieldname: undefined,
        fieldstatus: undefined,
        signcheck: undefined,
        allline: undefined,
        curline: undefined,
        xm: undefined,
        zkxms: undefined,
        jxmc: undefined,
        djc: undefined,
        rLine: undefined,
        kgname: undefined,
        sSafe: undefined,
        message: undefined,
        ipadddress: undefined,
        macaddress: undefined,
        checkResult1: undefined,
        checkResult2: undefined,
        checkResult3: undefined,
        carCamera1Ip: undefined,
        carCamera2Ip: undefined,
        carCamera3Ip: undefined,
        carGps1Port: undefined,
        carGps1Baud: undefined,
        carGps2Port: undefined,
        carGps2Baud: undefined,
        carSignalPort: undefined,
        carSignalBaud: undefined,
        carVoiceType: undefined,
        carVoiceSpeed: undefined,
        carModel2Name: undefined,
        gpsXOffset: undefined,
        gpsYOffset: undefined,
        checkTime: undefined,
        checkTime1: undefined,
        sStarttime: undefined,
        sEndtime: undefined,
        zjhm: undefined,
        gateway: undefined,
        ccdjrq: undefined,
        clxh: undefined,
        zdfc: undefined
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
      this.title = "添加过程明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getKcxx(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改过程明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          this.form.czip = [this.form.czHost,this.form.czPort,this.form.czChannel].join(',')
          this.form.zxip = [this.form.zxHost,this.form.zxPort,this.form.zxChannel].join(',')
          this.form.fourip = [this.form.fourHost,this.form.fourPort,this.form.fourChannel].join(',')
          if (this.form.id != null) {
            updateKcxx(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addKcxx(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除过程明细编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delKcxx(ids);
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
      this.download('w2/kcxx/export', {
        ...this.queryParams
      }, `kcxx_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
