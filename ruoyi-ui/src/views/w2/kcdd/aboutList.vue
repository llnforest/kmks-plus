<template>
  <div class="">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考试日期" prop="ksrq">
        <el-date-picker v-model="ksrq" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                        placeholder="请选择考试日期" :clearable="false" :editable="false"></el-date-picker>
      </el-form-item>

      <el-form-item label="考试车型" prop="kscx">
        <el-select v-model="queryParams.kscx" placeholder="请选择考试车型">
          <el-option
            v-for="dict in dict.type.param_car_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="准考证号" prop="zkzh">
        <el-input
          v-model="queryParams.zkzh"
          placeholder="请输入准考证号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="证件号码" prop="zjhm">
        <el-input
          v-model="queryParams.zjhm"
          placeholder="请输入证件号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="选择驾校" prop="jxdm">
        <el-select v-model="queryParams.jxdm" placeholder="请选择驾校" clearable>
          <el-option
            v-for="item in schoolList"
            :key="item.nid"
            :label="item.paramdm+':'+item.paramname"
            :value="item.paramdm"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-s-promotion"
          size="mini"
          :disabled="single"
          @click="handleChoice"
          v-hasPermi="['w2:kcdd:choice']"
        >分车</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="queuingList" @selection-change="handleSelectionChange" @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="考试编号" align="center" prop="ksbh" min-width="90"/>
      <el-table-column label="考生姓名" align="center" prop="xm" min-width="70"/>
      <el-table-column label="证件号码" align="center" prop="zjhm" min-width="130"/>
      <el-table-column label="考试车型" align="center" prop="kscx" min-width="60"/>
      <el-table-column label="考试项目" align="center" prop="ksxm" min-width="250" show-overflow-tooltip>
        <template slot-scope="scope">
          {{getWcxmNames(scope.row.ksxm)}}
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

    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:kcdd:choice']"
            @click="handleChoice(activeRow)"
        >
          分车
        </li>
      </ul>
    </div>

    <!-- 分车信息对话框 -->
    <el-dialog :title="title" :visible.sync="updateChoice" width="500px" append-to-body>
      <el-form ref="formKscx" :model="choiceForm" :rules="rulesKscx" label-width="80px">
        <el-form-item label="考车编号" prop="data">
          <el-select v-model="choiceForm.data" placeholder="请选择考车编号" clearable>
            <el-option
              v-for="item in carList"
              :key="item.id"
              :label="item.kch"
              :value="item.kch+','+item.cph"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitChoice">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import pubsub from "pubsub-js"
import {selectMapKsxmdmJg} from "@/api/w2/ksxmdmJg";
import {listAbout,choice} from "@/api/w2/kcdd";
import {selectSchool} from "@/api/w2/school";
import {getTodayDateRange} from "@/utils/date";

export default {
  name: "AboutList",
  dicts: ['queue_status','queue_djc','queue_is_lock','param_car_type'],
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
      resizeTop:-160,
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
        xm: undefined,
        ksrq: undefined,
        zjhm: undefined,
        kcbh: undefined,
        zkzh: undefined,
        kscx: undefined,
        jxdm: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rulesKscx: {
        id: [
          { required: true, message: "id不能为空", trigger: "blur" }
        ],
        data: [
          { required: true, message: "考车编号不能为空", trigger: "blur" }
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
      // 项目代码
      xmdmList: [],
      // 分车
      choiceForm:{
        id:undefined,
        data: undefined
      },
      // 更新
      updateForm:{
        id:undefined,
        djc:undefined,
        kszt:undefined,
        ksxm:[]
      },
      ksrq:getTodayDateRange()[0],
      updateChoice:false,
      updateInfo:false,
      pubId:undefined,
      carList:{},
      schoolList:{}
    };
  },
  created() {
    // this.$set(this.queryParams,"ksrq",this.getDate())
    // this.queryParams.ksrq = this.getDate();

    this.getXmdmList();
    this.getSchoolList();
    this.getList();
  },
  watch:{
    ksrq:{
      immediate:true,
      handler(newVal,oldVal){
        this.queryParams.ksrq = newVal + " 00:00:00"
      }
    }
  },
  methods: {
    getDate() {
      var now = new Date();
      var year = now.getFullYear(); //得到年份
      var month = now.getMonth(); //得到月份
      var date = now.getDate(); //得到日期
      var hour = " 00:00:00"; //默认时分秒 如果传给后台的格式为年月日时分秒，就需要加这个，如若不需要，此行可忽略
      month = month + 1;
      month = month.toString().padStart(2, "0");
      date = date.toString().padStart(2, "0");
      var defaultDate = `${year}-${month}-${date}`;//
      return defaultDate;
    },
    getXmdmList() {
      selectMapKsxmdmJg({kskm:2}).then(response => {
        this.xmdmList = response.data
      });
    },
    getSchoolList() {
      selectSchool({type:3}).then(response => {
        this.schoolList = response.data
      });
    },
    /** 查询排队信息列表 */
    getList() {
      this.loading = true;
      listAbout(this.queryParams).then(response => {
        this.queuingList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.updateChoice = false;
      this.updateInfo = false;
      this.reset();
    },
    // 表单重置
    reset() {
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
    /** 更新考试车型按钮操作 */
    handleChoice(row) {
      const id = row.id || this.ids[0]
      console.log(id,'---')
      if(!row.id) row = this.queuingList.filter(item => item.id == id)[0]
      this.choiceForm.id = id
      this.title = `分车—${row.xm}—${row.zjhm}`;
      this.updateChoice = true;
    },
    /** 提交更新考试车型按钮 */
    submitChoice() {
      this.$refs["formKscx"].validate(valid => {
        if (valid) {
          let arr = this.choiceForm.data.split(",")
          let kcbh = arr[0]
          let kchp = arr[1]
          this.$confirm(`您确定将该考生分车到${kcbh}车${kchp}`, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.buttonLoading = true;
            if (this.choiceForm.id != null) {

              choice({id:this.choiceForm.id,kcbh,kchp}).then(response => {
                this.$modal.msgSuccess("分车成功");
                this.updateChoice = false;
                this.getList();
              }).finally(() => {
                this.buttonLoading = false;
              });
            }
          });
        }
      })

    },
    //完成项目拼接字符喜欢
    getWcxmNames(wcxm){
        const wcxmArr = [];
        wcxm.split(",").forEach(id => {
          if(id != ""){
            if(this.xmdmList[id] != null) wcxmArr.push(id+":"+this.xmdmList[id])
          }
        });
        return wcxmArr.join(",");
    },
    renderCarList(msgName,data){
      this.carList = data;
    }
  },
  mounted(){
    this.pubId = pubsub.subscribe("renderCar",this.renderCarList)
  },
  beforeDestroy() {
    pubsub.unsubscribe(this.pubId);
  }
};
</script>
