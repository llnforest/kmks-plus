<!-- src/components/MyComponent.vue -->
<template>
  <div class="video-area">
    <div id="divPlugin">

    </div>
    <div class="back-video">
      回放时间
      <el-time-picker
        is-range
        v-model="timeSelect"
        range-separator="至"
        start-placeholder="开始时间"
        end-placeholder="结束时间"
        placeholder="选择时间范围"
        value-format="yyyy-MM-dd HH:mm:ss"
        popper-append-to-body="false"
      >
      </el-time-picker>
      <el-button type="danger" :plain="video" @click="playVideo" style="margin-left:10px;">{{video?'重放':'回放'}}</el-button>
<!--      <el-button type="primary" :plain="fastPlay ==0 && slowPlay==0" :disabled="fastPlay ==0 && slowPlay==0" @click="playResume">恢复</el-button>-->
      <el-button type="primary" :disabled="!video" @click="playFast">快放{{fastPlay>0?' X'+fastPlay:''}}</el-button>
      <el-button type="primary" :disabled="!video" @click="playSlow">慢放{{slowPlay>0?' X'+slowPlay:''}}</el-button>

    </div>
  </div>
</template>

<script>
import $ from 'jquery'
window.$ = window.jQuery = $
import '@/assets/codebase/webVideoCtrl' //注意路径
export default {
  name: 'BackVideo',
  data(){
      return {
        timeSelect:[],
        iWndIndex:0,
        fastPlay:0,
        slowPlay:0,
        video: false
      }
  },
  props: {
    videoObj: {
      type: Object,
      default: {}
    },
    timeRange:{
      type: Array,
      default:[new Date(2016, 9, 10, 8, 40), new Date(2016, 9, 10, 9, 40)]
    },
    open:{
      type:Boolean,
      default:false
    }
  },
  watch: {
    timeRange: {
      handler(newVal) {
        this.timeSelect = newVal
      },
      immediate: true,
      deep: true
    },
    open: {
      handler(newVal) {
        console.log(newVal);
        if(newVal){
          this.initVideo()
        }else{
          this.destroyVideo()
        }
      },
      immediate: true,
      // deep: true
    }
  },
  methods: {
    playVideo(){
      console.log(this.timeSelect,this.videoObj)
      const oWndInfo = WebVideoCtrl.I_GetWindowStatus(this.iWndIndex)
      if (oWndInfo != null) {// 已经在播放了，先停止
        WebVideoCtrl.I_Stop({
          success: () => {
            this.playVideo();
          }
        });
        return ;
      }

      WebVideoCtrl.I_StartPlayback(this.videoObj.ip+'_'+this.videoObj.port, {
        iRtspPort: 554,
        iStreamType: 1,
        iChannelID: this.videoObj.channel,
        szStartTime: this.timeSelect[0],
        szEndTime: this.timeSelect[1],
        success: ()=>{
          this.video = true
          this.fastPlay = 0
          this.slowPlay = 0
          console.log("回放成功")
        },
        error: (oError)=>{
          console.log("回放失败",oError)
        }
      });
    },
    // 恢复
    playResume(){
      const oWndInfo = WebVideoCtrl.I_GetWindowStatus(this.iWndIndex)
      if (oWndInfo != null) {
        WebVideoCtrl.I_Resume({
          success: () => {
            this.fastPlay = 0
            this.slowPlay = 0
            console.log("恢复成功！");
          },
          error: function (oError) {
            console.log("恢复失败！", oError);
          }
        });
      }
    },
    // 快放
    playFast(){
      const oWndInfo = WebVideoCtrl.I_GetWindowStatus(this.iWndIndex)
      if (oWndInfo != null) {
        WebVideoCtrl.I_PlayFast({
          success: ()=> {
            this.fastPlay ++
            console.log("快放成功！");
            this.$forceUpdate()
          },
          error: function (oError) {
            console.log("快放失败！", oError);
          }
        });
      }
    },
    // 快放
    playSlow(){
      const oWndInfo = WebVideoCtrl.I_GetWindowStatus(this.iWndIndex)
      if (oWndInfo != null) {
        WebVideoCtrl.I_PlaySlow({
          success:  () => {
            this.slowPlay ++
            console.log("慢放成功！");
          },
          error: function (oError) {
            console.log("慢放失败！", oError);
          }
        });
      }
    },
    // 初始化视频
    initVideo(){
      const _this = this
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
        setTimeout(() => {
          _this.hkLogin()
        },2000)
      })
    },
    destroyVideo(){
      WebVideoCtrl.I_DestroyPlugin()
      console.log("退出成功")
      this.hkLoginOut();
    },
    hkLogin(){
      console.log(this.videoObj)
      WebVideoCtrl.I_Login(this.videoObj.ip, 1, this.videoObj.port, this.videoObj.username, this.videoObj.password, {
        timeout: 3000,
        success: function (xmlDoc) {
          console.log('login success')
        },
        error: function (oError) {
          console.log('login error',oError)
        }
      });
    },
    hkLoginOut(){
      WebVideoCtrl.I_Logout(this.videoObj.ip+'_'+this.videoObj.port).then(() => {
        console.log("退出成功！");
      }, () => {
        console.log("退出失败！");
      });
    },


  }
};
</script>

<style lang="scss" scoped>
#divPlugin {
  width:100%;
  height:420px;
  background-color: #eee
}
.back-video{
  margin-top:20px;

}
::v-deep .el-picker-panel {
  top: 100% !important; /* 强制时间选择框向下弹出 */
  bottom: auto !important;
}
</style>
