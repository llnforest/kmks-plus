<template>
  <div class="app-container">
    <div class="video-box"  id="divPlugin">

    </div>
    <div class="car-box">
      <el-tree
        ref="carTree"
        :data="videoList"
        show-checkbox
        node-key="id"
        @current-change="currentChange"
        @check="handleCheck"
        :default-checked-keys="checkedIds">
      </el-tree>
    </div>
    <div class="operate-box">
      <div class="operate-item" v-for="item in videoList" :key="item.id">
        <div v-if="checkedIds.includes(item.id)">
          <el-button type="text" class="ignore-btn" @click="closeVoice(item.id)" v-if="videoObj[item.id].voice">关闭声音</el-button>
          <el-button type="text" @click="startVoice(item.id)" v-else>打开声音</el-button>
          <el-button type="text" class="ignore-btn" @click="closeTalk()" v-if="videoObj[item.id].talk">停止对讲</el-button>
          <el-button type="text" @click="startTalk(item.id)" v-else>开启对讲</el-button>
          <el-button type="text" class="judge-btn" @click="handleJudge(item.id,item.kch)">下发评判</el-button>
        </div>

      </div>
    </div>
<!--    <el-dialog title="下发评判" :visible.sync="open" width="780px">-->
<!--    </el-dialog>-->
    <el-drawer
      title="下发评判"
      :visible.sync="open"
      direction="rtl"
      size="400px"
    >
      <div style="width:380px">
        <el-form ref="form" :model="form" :rules="rules" label-width="90px">
          <el-form-item label="考车信息" prop="kch">
            {{form.kch}}-{{form.cph}}
            <el-tag type="success" v-if="form.status" size="mini">在线</el-tag>
            <el-tag type="danger" v-else size="mini">离线</el-tag>
          </el-form-item>
          <el-form-item label="考生姓名" prop="cph">
            {{form.ksxx}}
          </el-form-item>
          <el-form-item label="当前成绩" prop="cph">
            {{form.dqcj}}
          </el-form-item>
          <el-form-item label="扣分明细" prop="kfxx">
            {{form.kfxx}}
          </el-form-item>
          <el-form-item label-width="10px">
            <el-input
              placeholder="输入扣分关键字进行过滤"
              v-model="filterText">
            </el-input>
          </el-form-item>
          <div class="kf-area">
            <el-tree
              node-key="value"
              class="filter-tree"
              :data="kfdmOptions"
              :props="defaultProps"
              highlight-current
              @node-click="handleNodeClick"
              :default-expanded-keys="explandKeys"
              ref="tree">
            </el-tree>
          </div>
          <el-row :gutter="20" type="flex" justify="center" class="judge-btn-area">
            <el-button type="primary" :loading="buttonLoading" :disabled="!form.status && !form.gakfdm" size="small" @click="sureJudge">确认下发</el-button>
            <el-button size="small" @click="open=false">取消下发</el-button>
          </el-row>
        </el-form>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import $ from 'jquery'
window.$ = window.jQuery = $
import {listVideo, videoInfo} from "@/api/w2/video";
import '@/assets/codebase/webVideoCtrl'
import {getKfdmByGroup} from "@/api/w2/kfconfig";
import {addSchool, updateSchool} from "@/api/w2/school"; //注意路径

export default {
  name: "Video",
  activated() {
    console.log("activated")
    if (this.videoList.length > 0) {
      console.log(1)
      this.initHk()
      this.showVideo()
    }
    // 在这里添加你的逻辑，例如获取数据
  },
  deactivated() {
    console.log("deactivated")
    this.unstallHk()
    // 在这里添加你的逻辑，例如清理资源
  },
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
      videoList:[],
      carObj: {},
      videoObj:{},
      checkedIds:[],
      splitWindowNum:2,
      maxShowVideo:4,
      lastWinIndex:0,
      zxObj:{},
      kfdmOptions:[],
      defaultProps: {
        children: 'children',
        label: 'label',
      },
      explandKeys:['综合'],
      filterText:'',
      form:{},
      rules: {
        carId: [
          { required: true, message: "考车不能为空", trigger: "blur" }
        ],
        gakfdm: [
          { required: true, message: "扣分明细不能为空", trigger: "blur" }
        ],
      }
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    this.kfdmGroupList();
  },
  mounted() {
    console.log("mounted")
    this.initHk()
    this.showVideo()
  },
  methods: {
    initHk(){
      $(function () {
        // 初始化插件参数及插入插件
        WebVideoCtrl.I_InitPlugin({
          bWndFull: true,     //是否支持单窗口双击全屏，默认支持 true:支持 false:不支持
          iWndowType: 1,
          szBasePath:'.',
          cbSelWnd: function (xmlDoc) {
          },
          cbDoubleClickWnd: function (iWndIndex, bFullScreen) {
          },
          cbEvent: function (iEventType, iParam1, iParam2) {
            if (2 == iEventType) {// 回放正常结束
              showCBInfo("窗口" + iParam1 + "回放结束！");
            } else if (-1 == iEventType) {
              showCBInfo("设备" + iParam1 + "网络错误！");
            } else if (3001 == iEventType) {
              clickStopRecord(g_szRecordType, iParam1);
            }
          },
          cbInitPluginComplete: function () {
            WebVideoCtrl.I_InsertOBJECTPlugin("divPlugin").then(() => {

              // 检查插件是否最新
              WebVideoCtrl.I_CheckPluginVersion().then((bFlag) => {
                if (bFlag) {
                  alert("检测到新的插件版本，双击开发包目录里的HCWebSDKPlugin.exe升级！");
                }
              });
            }, () => {
              alert("插件初始化失败，请确认是否已安装插件；如果未安装，请双击开发包目录里的HCWebSDKPlugin.exe安装！");
            });
          }
        });
      })
    },
    handleNodeClick(data,node){
      console.log(node,data)
      if (!node.isLeaf) {
        // 阻止非叶子节点的点击
        this.form.gakfdm = null
      } else {
        // 叶子节点的点击逻辑
        this.form.gakfdm = data.value
        this.$set(this.form,'kfxx',data.label)
      }
    },
    // 下发评判
    handleJudge(carId,kch){
      this.open = true
      this.form = {
        carId:carId,
        cph:this.carObj[carId].cph,
        kch:this.carObj[carId].kch,
        kfxx:null,
        gakfdm:null,
        ksxx:null,
        dqcj:null,
        dqxm:null,
        status:false
      }
      videoInfo(kch).then(response=>{
        const data = response.data
        this.form = { ...this.form, status: true,ksxx: data.ksxm+'('+data.zjhm+')',dqcj:data.ksfs,dqxm:data.dqxm}
        this.explandKeys = ['综合',data.dqxm]
      },error=>{
        this.form = { ...this.form, status: false }
      })
    },
    sureJudge(){
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          downJudge(this.form).then(response => {
            this.$modal.msgSuccess("下发成功");
            this.open = false;
          }).finally(() => {
            this.buttonLoading = false;
          });
        }
      });
    },
    renderTalk(){
      this.videoList.forEach(item=>{
        if(item.zt == 1){
          this.hkLogin(item.czArr[0],1,item.czArr[1],item.cuser,item.cpwd)
        }
      })
    },
    showVideo(){
      setTimeout(()=>{
        this.renderVideo();
        this.splitWindow();
        setTimeout(()=>{
        this.renderTalk();
          this.playVideoList()
        },500);
      },1000)
    },
    renderVideo(){
      this.hkLogin(this.zxObj.ip, 1, this.zxObj.port, this.zxObj.username, this.zxObj.password);
    },
    hkLogin(szIP, iPrototocol, iPort, szUserName, szPassword){
      console.log(szIP, iPrototocol, iPort, szUserName, szPassword);
      WebVideoCtrl.I_Login(szIP, iPrototocol, iPort, szUserName, szPassword, {
        timeout: 3000,
        success: function (xmlDoc) {
          console.log('login success')
        },
        error: function (oError) {
          console.log('login error',oError)
        }
      });
    },
    hkLoginOut(szDeviceIdentify){
      WebVideoCtrl.I_Logout(szDeviceIdentify).then(() => {
        console.log(szDeviceIdentify + " " + "退出成功！");
      }, () => {
        console.log(szDeviceIdentify + " " + "退出失败！");
      });
    },
    playVideoList(){
      // console.log('目前播放'+this.lastWndIndex+'总共播放'+this.checkedIds.length)
      console.log(this.checkedIds)
      for(var i=0;i<this.checkedIds.length;i++){
        this.stopVideo(i,true)
      }
      // 关闭多余的窗口
      // for(var i=this.checkedIds.length;i<this.lastWndIndex;i++){
      //   this.stopVideo(i)
      // }
      // this.lastWndIndex = this.checkedIds.length;
    },
    playVideo(iWndIndex,carIndex){
      const car = this.carObj[this.checkedIds[carIndex]];
      this.videoObj[car.id].index = iWndIndex;
      const szDeviceIdentify = this.zxObj.ip+'_'+this.zxObj.port
      const iStreamType = 1
      const bZeroChannel = false
      const iChannelID = car['zxArr'][2]
      WebVideoCtrl.I_StartRealPlay(szDeviceIdentify, {
        iWndIndex:iWndIndex,
        iStreamType: iStreamType,
        iChannelID: iChannelID,
        bZeroChannel: bZeroChannel,
        success: ()=>{
          console.log("播放成功"+"page"+iWndIndex)
        },
        error: (oError)=>{
          console.log("播放失败"+"page"+iWndIndex)
        }
      });
    },
    stopVideo(index,reload = false,newIndex =false){
      if(index === false) return;
      var oWndInfo = WebVideoCtrl.I_GetWindowStatus(index);
      if(oWndInfo != null){
        WebVideoCtrl.I_Stop({
          iWndIndex:index,
          success: ()=>{
            console.log("停止播放成功"+"page"+index)
            if(reload)  this.playVideo(index,newIndex === false?index:newIndex);
          },
          error: function (oError) {
            console.log("停止播放失败"+"page"+index)
          }
        });
      }else if(reload){
        this.playVideo(index,newIndex === false?index:newIndex);
      }
    },
    // 开启声音
    startVoice(id){
      // 检查是否有其他开启项，先关掉
      for(var i in this.videoObj){
        if(this.videoObj[i].voice){
          this.closeVoice(this.videoObj[i]['id'])
          this.videoObj[i].voice = false
        }
      }
      WebVideoCtrl.I_OpenSound(this.videoObj[id].index).then(() => {
        console.log("打开声音成功")
        this.$set(this.videoObj, id, { ...this.videoObj[id], voice: true });
        this.$forceUpdate();
        console.log(this.videoObj)
      }, (oError) => {
        console.log("打开声音失败",oError)

      });
    },
    // 关闭声音
    closeVoice(id){
      WebVideoCtrl.I_CloseSound(this.videoObj[id].index).then(() => {
        console.log("关闭声音成功")
        this.$set(this.videoObj, id, { ...this.videoObj[id], voice: false });
        this.$forceUpdate();

      }, (oError) => {
        console.log("关闭声音失败",oError)

      });
    },
    startTalk(id){
      // 检查是否有其他开启项，先关掉
      this.closeTalk()
      WebVideoCtrl.I_StartVoiceTalk(this.carObj[id].czArr[0]+'_'+this.carObj[id].czArr[1], this.carObj[id].czArr[2]).then(() => {
        console.log("开始对讲成功！")
        this.$set(this.videoObj, id, { ...this.videoObj[id], talk: true });
        this.$forceUpdate();
      }, (oError) => {
        console.log(" 开始对讲失败！");
      });
    },
    closeTalk(){
      WebVideoCtrl.I_StopVoiceTalk().then(() => {
        console.log("停止对讲成功！");
        for(var i in this.videoObj){
          if(this.videoObj[i].talk){
            this.videoObj[i].talk = false
          }
        }
        this.$forceUpdate();
      }, (oError) => {
        console.log(" 停止对讲失败！");
      });
    },
    getList(id) {
      this.loading = true;
      listVideo({}).then(response => {
        this.videoList = response.data.map(item=>{
          if(item.zt != "1"){
            console.log(item)
            item.disabled = true;
          }else if(this.checkedIds.length < this.maxShowVideo){
            this.checkedIds.push(item.id)
            this.videoObj[item.id] = {id:item.id,talk:false,voice:false}
          }
          item.label = item.kch+'-'+item.cph
          item.zxArr = item.zxip.split(',');
          item.czArr = item.czip.split(',');
          if(Object.keys(this.zxObj).length === 0){
            this.zxObj = {
              ip:item.zxArr[0],
              port:item.zxArr[1],
              password:item.zpwd,
              username:item.zuser,
            }
          }
          this.carObj[item.id] = item
          return item;
        })
        this.showVideo()
        console.log(this.videoList)
        this.loading = false;
      });
    },
    kfdmGroupList(){
      getKfdmByGroup().then(response=>{
        console.log(response.data)
        var sub = [];
        for(var name in response.data){
          sub = [];
          for(var item of response.data[name]){
            sub.push({
              value:item.gakfdm,
              label:item.kfmc+((item.value != "" && item.value != "0")?item.value+item.beizhu:"")+",扣"+item.kf+"分"
            })
          }
          this.kfdmOptions.push({
            value:name,
            label:name,
            children:sub
          })

        }
      })
    },
    renderCheck(){
      this.splitWindow();
      this.playVideoList()
    },
    splitWindow(){
      // const num = this.computeWindowNum()
      // if(num == this.splitWindowNum){
      //   return
      // }
      // this.splitWindowNum = num;
      WebVideoCtrl.I_ChangeWndNum(this.splitWindowNum).then(() => {
        console.log("窗口分割成功！");
      }, (oError) => {
        console.log("窗口分割失败！");
        console.log(oError);
      },2000);
    },
    computeWindowNum(){
      if(this.checkedIds.length > 9){
        return 4;
      }else if(this.checkedIds.length > 4){
        return 3
      }else if(this.checkedIds.length > 1){
        return 2
      }else{
        return 1
      }
    },
    currentChange(e){
      if(this.handleNodeIds(e.id,e.zt)){
        // this.renderCheck()
        this.$refs.carTree.setCheckedKeys(this.checkedIds)
      }
    },
    handleCheck(e){
      if(this.handleNodeIds(e.id,e.zt)){
        // this.renderCheck()
        this.$refs.carTree.setCheckedKeys(this.checkedIds)
      }
    },
    getVideoIndex(id){
      console.log(this.videoObj,id)
      if(this.videoObj[id] != undefined){
        return this.videoObj[id].index;
      }
      return false;
    },
    getPlayVideoIndex(){
      var existsIndex = [];
      for(let carId in this.videoObj){
        existsIndex.push(this.videoObj[carId].index)
      }
      console.log(existsIndex,this.videoObj)
      for(var i=0;i<this.maxShowVideo;i++){
        if(!existsIndex.includes(i)){
          return i;
        }
      }
      return 0;
    },
    handleNodeIds(id,status){
      if(status != 1) return false
      if(this.checkedIds.includes(id)){
        // 包含删除
        const indexToRemove = this.checkedIds.findIndex(item => item === id);
        if (indexToRemove > -1) {
          this.checkedIds.splice(indexToRemove, 1);
          console.log("关闭",id,this.getVideoIndex(id),this.checkedIds)
          this.stopVideo(this.getVideoIndex(id))
          delete this.videoObj[id];
        }
      }else{
        //不包含增加
        this.checkedIds.push(id)
        this.videoObj[id] = {id:id,talk:false,voice:false}
        if(this.checkedIds.length > this.maxShowVideo){
          const carId = this.checkedIds.shift();
          console.log("重启",id,this.getVideoIndex(carId),this.checkedIds)
          this.stopVideo(this.getVideoIndex(carId),true,this.checkedIds.length-1)
          delete this.videoObj[carId]
        }else{
          this.playVideo(this.getPlayVideoIndex(),this.checkedIds.length-1)
        }
      }
      return true
    },
    // 卸载
    unstallHk(){
      WebVideoCtrl.I_DestroyPlugin().then(() =>{
        console.log("退出成功")

      })
      console.log("退出成功")
      this.hkLoginOut(this.zxObj.ip+'_'+this.zxObj.port);
      this.videoList.forEach(item=>{
        this.hkLoginOut(item.czArr[0]+'_'+item.czArr[1]);
      })
    }

  },
  beforeDestroy(){
    this.unstallHk()

  }
};
</script>
<style lang="scss" scoped>
.app-container{
  padding-right:420px;
  position:relative;
  min-height: calc(100vh - 84px);
  .video-box{
    height: calc(100vh - 124px);
    .video-item{
      border: 1px solid #d3dce6;
      box-sizing: border-box;
      position:relative;
    }
    .video-car-bottom{
      position:absolute;
      background-color: #74b2fa;
      width:100%;
      height:10%;
      bottom:0;
      left:0;
    }
  }
  .car-box{
    width:160px;
    position:absolute;
    right:240px;
    top:20px;
    min-height: calc(100vh - 124px);
    border-left:3px solid #409EFF;
    ::v-deep .el-checkbox{
      margin-top:4px;
    }
  }
  .operate-box{
    width:240px;
    position:absolute;
    right:0;
    top:20px;
    min-height: calc(100vh - 124px);
    .operate-item{
      height:26px;
    }
    ::v-deep .el-button{
      height:26px;
      line-height: 0px;

    }
    ::v-deep .judge-btn{
      color: #ff200f;
    }
    ::v-deep .ignore-btn{
      color: #7abfff;
    }

  }
  ::v-deep .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content{
    background-color: rgb(64, 158, 255) !important;
    color: #fff;
  }
  .judge-btn-area{
    margin-bottom:40px;
    margin-top:40px;
  }
  /* 滚动条整体 */
  ::-webkit-scrollbar {
    width: 7px; /* 宽度 */
    height: 12px; /* 高度 */
  }

  /* 滚动条轨道 */
  ::-webkit-scrollbar-track {
    background: #f1f1f1; /* 颜色 */
    border-radius: 10px; /* 圆角 */
  }

  /* 滚动条滑块 */
  ::-webkit-scrollbar-thumb {
    background: #1da2d3; /* 颜色 */
    border-radius: 10px; /* 圆角 */
  }

  /* 滚动条滑块悬停时 */
  ::-webkit-scrollbar-thumb:hover {
    background: #555; /* 悬停颜色 */
  }
}
</style>
