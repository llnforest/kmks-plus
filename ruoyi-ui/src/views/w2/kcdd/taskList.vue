<template>
  <div class="">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-circle-close"
          size="mini"
          :disabled="single"
          @click="handleApply"
          v-hasPermi="['w2:dispatch:apply']"
        >申请考试</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-circle-close"
          size="mini"
          :disabled="single"
          @click="handleCancel"
          v-hasPermi="['w2:dispatch:cancel']"
        >取消考试</el-button>
      </el-col>
      <right-toolbar :search="false" @queryTable="getList">
      </right-toolbar>
    </el-row>

    <el-table ref="dragTable" v-loading="loading" :data="queuingList" :row-class-name="tableRowClassName"
              row-key="zjhm" @selection-change="handleSelectionChange" @row-click="rowClick" @row-contextmenu="rightClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="考车编号" align="center" prop="kcbh" min-width="80"/>
      <el-table-column label="考车信息" align="center" prop="kcxx" min-width="90"/>
      <el-table-column label="考试状态" align="center" prop="kszt" min-width="120"/>
      <el-table-column label="考试科目" align="center" prop="kskm" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_kskm" :value="scope.row.kskm"/>
        </template>
      </el-table-column>
      <el-table-column label="考生姓名" align="center" prop="ksxm" min-width="80"/>
      <el-table-column label="证件号码" align="center" prop="zjhm" min-width="160"/>
      <el-table-column label="当前项目" align="center" prop="dqxm" min-width="80"/>
      <el-table-column label="上传状态" align="center" prop="sczt" min-width="100"/>
      <el-table-column label="考试分数" align="center" prop="ksfs" min-width="80"/>
      <el-table-column label="考试结果" align="center" prop="kscj" min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.record_ksjg" :value="scope.row.kscj"/>
        </template>
      </el-table-column>
      <el-table-column label="次数" align="center" prop="kscs" min-width="50"/>
      <el-table-column label="里程" align="center" prop="lc" min-width="60"/>
      <el-table-column label="车速" align="center" prop="cs" min-width="60"/>
    </el-table>

    <div id="menu" class="menuDiv" v-show="rightMenuVisible" :style="{top:rightMenuTop,left:rightMenuLeft}">
      <ul class="menuUl">
        <li v-hasPermi="['w2:dispatch:apply']"
            @click="handleApply(activeRow)"
        >
          申请考试
        </li>
        <li v-hasPermi="['w2:dispatch:cancel']"
            @click="handleCancel(activeRow)"
        >
          取消考试
        </li>
      </ul>
    </div>

  </div>
</template>

<script>
import pubsub from "pubsub-js"
import {applyExam, cancelExam, centerList, flowList} from "@/api/w2/dispatch";


export default {
  name: "TaskList",
  dicts: ['record_ksjg','sys_kskm'],
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
        zjhm: undefined,
      },
      // 表单参数
      form: {},
      // 路线数据
      lineList: [],
      carList:{},
      currentRow:undefined,
      ws:null,
    };
  },
  created() {
    this.getList();
    // websocket
    this.WebSocket_StatusCheck();
  },
  methods: {
    /** 查询排队信息列表 */
    getList() {
      this.loading = true;
      centerList().then(response => {
        this.queuingList = response.data;
        this.loading = false;
      });
    },
    /** 申请考试 */
    handleApply(row) {
      const ids = row.zjhm+"_"+row.kskm || this.ids;
      if(ids == "null_null"){
        this.$modal.msgError("该考车尚未查找下一个待考考生");
        return ;
      }
      this.$modal.confirm('是否确认证件号码为"' + ids + '"的考生申请考试？').then(() => {
        this.loading = true;
        return applyExam(ids);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("申请考试已下发车载");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 取消考试 */
    handleCancel(row) {
      const ids = row.zjhm+"_"+row.kskm || this.ids;
      if(ids == "null_null"){
        this.$modal.msgError("该考车尚未查找下一个待考考生");
        return ;
      }
      this.$modal.confirm('是否确认取消证件号码为"' + ids + '"的考试？').then(() => {
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      console.log(selection);
      this.ids = selection.map(item => item.zjhm+"_"+item.kskm)
      console.log(this.ids)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    rowClick(row, column, event){
      console.log(row, column, event)
      if(row.zt == 1){
        this.resetRow()
        row.zt = "11"
        this.currentRow = row
        this.sendFlowParams()
      }else if(row.zt == 0){
        this.resetRow()
        row.zt = "00"
        this.currentRow = row
        this.sendFlowParams()
      }
    },
    sendFlowParams(){
      this.$WebSocket.Send(JSON.stringify({zjhm: this.currentRow.zjhm}))
      pubsub.publish("renderFlowParams", {zjhm: this.currentRow.zjhm})
    },
    resetRow(){
      if(this.currentRow == undefined) return ;
      this.queuingList.forEach(item=>{
        if(item.zjhm == this.currentRow.zjhm){
          if(item.zt == "11"){
            item.zt = "1"
          }else if(item.zt == "00"){
            item.zt = "0"
          }
        }
      })
    },
    renderDispatchList(msgName,data) {
      if(this.currentRow != undefined){
        data.forEach(item => {
          if(item.zjhm == this.currentRow.zjhm){
            if(item.zt == 1){
              item.zt = "11"
            }else if(item.zt == 0){
              item.zt = "00"
            }
          }
        })
      }
      const selectedKeys = this.ids.map(row => row);
      this.queuingList = data

      this.$nextTick(() => {
        console.log(selectedKeys)
        // 恢复选中状态
        selectedKeys.forEach(key => {
          const row = this.queuingList.find(item => item.zjhm === key);
          if (row) {
            this.$refs.dragTable.toggleRowSelection(row, true);
          }
        });
      });
    },
    tableRowClassName({ row, rowIndex }) {
      if (row.zt == '0') {
        return 'row-red';
      }else if (row.zt == '00') {
        return 'row-red-deep';
      }else if (row.zt == '11') {
        return 'row-blue';
      }
      return '';
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
      const wsSrverAddress = window.globalConfig.VUE_APP_BASE_API +"/websocket/dispatch/"+10 //可以直接赋值如：ws://127.0.0.1:1234

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
        console.log("接收调度中心到消息")
        const response = JSON.parse(e.data);
        console.log(response)
        if(response.code == 1){
          pubsub.publish("renderDispatch",response.data)
        }else if(response.code == 2){
          pubsub.publish("renderFlow",response.data)
        }else if(response.code == 3){
          pubsub.publish("renderCar",response.data)
        }else if(response.code == 4){

        }
      }
    }
  },
  mounted(){
    this.pubId = pubsub.subscribe("renderDispatch",this.renderDispatchList)
  },
  beforeDestroy() {
    if (this.ws) {
      this.ws.close();
    }
    pubsub.unsubscribe(this.pubId);
  }
};
</script>
