<template>
  <div class="">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考试日期" prop="ksrq">
        <el-date-picker v-model="ksrq" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                        placeholder="请选择考试日期" :clearable="false" :editable="false"></el-date-picker>
      </el-form-item>
      <el-form-item label="考车编号" prop="kcbh">
        <el-select v-model="queryParams.kcbh" placeholder="请选择考车编号">
          <el-option
            v-for="item in carList"
            :key="item.id"
            :label="item.kch"
            :value="item.kch"
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
          @click="handleChange"
          v-hasPermi="['w2:kcdd:change']"
        >换车</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-s-promotion"
          size="mini"
          :disabled="single"
          @click="handleRandomChange"
          v-hasPermi="['w2:kcdd:randomChange']"
        >随机换车</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-circle-close"
          size="mini"
          :disabled="multiple"
          @click="handleCancel"
          v-hasPermi="['w2:kcdd:cancel']"
        >取消考试</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-tooltip content="长按行可以上下拖动排序哦" placement="right" effect="light">
          <el-button
            type="primary"
            plain
            icon="el-icon-d-caret"
            size="mini"
            v-hasPermi="['w2:kcdd:upDown']"
          >排序</el-button>
        </el-tooltip>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList">
      </right-toolbar>
    </el-row>

    <el-table ref="dragTable" v-loading="loading" :data="queuingList" row-key="id" @selection-change="handleSelectionChange" @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="bdxh" min-width="50"/>
      <el-table-column label="考车编号" align="center" prop="kcbh" min-width="70"/>
      <el-table-column label="考试编号" align="center" prop="ksbh" min-width="90"/>
      <el-table-column label="考生姓名" align="center" prop="xm" min-width="70"/>
      <el-table-column label="允考数" align="center" prop="ykcs" min-width="60"/>
      <el-table-column label="验证否" align="center" prop="sfyz" min-width="60"/>
      <el-table-column label="驾校名称" align="center" prop="jxmc" min-width="100"/>
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
        <li v-hasPermi="['w2:kcdd:change']"
            @click="handleChange(activeRow)"
        >
          换车
        </li>
        <li v-hasPermi="['w2:kcdd:randomChange']"
            @click="handleRandomChange(activeRow)"
        >
          随机换车
        </li>
        <li v-hasPermi="['w2:kcdd:cancel']"
            @click="handleCancel(activeRow)"
        >
          取消考试
        </li>
      </ul>
    </div>

    <!-- 分车信息对话框 -->
    <el-dialog :title="title" :visible.sync="updateChange" width="500px" append-to-body>
      <el-form ref="formKscx" :model="changeForm" :rules="rulesKscx" label-width="80px">
        <el-form-item label="考车编号" prop="data">
          <el-select v-model="changeForm.data" placeholder="请选择考车编号" clearable>
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
        <el-button :loading="buttonLoading" type="primary" @click="submitChange">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import pubsub from "pubsub-js"
import {selectMapKsxmdmJg} from "@/api/w2/ksxmdmJg";
import {cancelExam, changeCar, listTask, randomChangeCar, upDown} from "@/api/w2/kcdd";
import Sortable from 'sortablejs'
import {getTodayDateRange} from "@/utils/date";

export default {
  name: "TaskListTmp",
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
      changeForm:{
        id:undefined,
        data: undefined,
        xm:undefined,
        kch:undefined
      },
      // 更新
      updateForm:{
        id:undefined,
        djc:undefined,
        kszt:undefined,
        ksxm:[]
      },
      ksrq:getTodayDateRange()[0],
      updateChange:false,
      updateInfo:false,
      pubId:undefined,
      carList:{},
    };
  },
  created() {
    this.getXmdmList();

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
    rowDrop() {
      // const tbody = document.querySelector('.el-table__body-wrapper tbody')
      const tbody = this.$refs.dragTable.$el.querySelectorAll(".el-table__body-wrapper > table > tbody")[0];
      console.log(tbody);
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
    getXmdmList() {
      selectMapKsxmdmJg({kskm:2}).then(response => {
        this.xmdmList = response.data
      });
    },
    /** 查询排队信息列表 */
    getList() {
      this.loading = true;
      listTask(this.queryParams).then(response => {
        this.queuingList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 取消考试 */
    handleCancel(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认取消信息编号为"' + ids + '"的考试？').then(() => {
        this.loading = true;
        return cancelExam(ids);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("取消成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.updateChange = false;
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
    handleChange(row) {
      const id = row.id || this.ids[0]
      if(!row.id) row = this.queuingList.filter(item => item.id == id)[0]
      this.changeForm = {
        id,
        xm:row.xm,
        kch:row.kcbh,
        data:undefined
      }
      this.title = `换车—${row.xm}—${row.kcbh}车`;
      this.updateChange = true;
    },
    /** 提交换车 */
    submitChange() {
      this.$refs["formKscx"].validate(valid => {
        if (valid) {
          let arr = this.changeForm.data.split(",")
          console.log(this.changeForm)
          let kcbh = arr[0]
          let kchp = arr[1]
          if(kcbh == this.changeForm.kch){
            this.$message({
              message: '考车编号未发生更换',
              type: 'warning'
            });
            return ;
          }
          this.$confirm(`您确定将考生${this.changeForm.xm}从${this.changeForm.kch}换车到${kcbh}车`, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.buttonLoading = true;
            if (this.changeForm.id != null) {

              changeCar({id:this.changeForm.id,kcbh,kchp}).then(response => {
                this.$modal.msgSuccess("换车成功");
                this.updateChange = false;
                this.getList();
              }).finally(() => {
                this.buttonLoading = false;
              });
            }
          })
        };
      })
    },
    /** 随机换车 */
    handleRandomChange(row) {
      const id = row.id || this.ids[0]
      if (!row.id) row = this.queuingList.filter(item => item.id == id)[0]
      this.$confirm(`您确定将考生${row.xm}从${row.kcbh}车随机换车吗`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.buttonLoading = true;
        if (id != null) {
          randomChangeCar({id,kcbh:row.kcbh}).then(response => {
            this.$modal.msgSuccess("随机换车成功");
            this.updateChange = false;
            this.getList();
          }).finally(() => {
            this.buttonLoading = false;
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
      if(!this.queryParams.kcbh){
        this.$set(this.queryParams,"kcbh",data[0].kch)
        this.getList();
      }
    }
  },
  mounted(){
    this.pubId = pubsub.subscribe("renderCar",this.renderCarList)
    this.rowDrop();
  },
  beforeDestroy() {
    pubsub.unsubscribe(this.pubId);
  }
};
</script>
