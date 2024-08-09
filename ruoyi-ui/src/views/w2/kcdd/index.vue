<template>
  <div class="app-container" id="app-container">
    <el-row class="row-top">
      <el-col :span="24" class="main-box">
        <task-list/>
      </el-col>
    </el-row>
    <el-row class="row-bottom">
      <el-col :span="17" class="col-left">
        <flow-list class="flow-box"/>
      </el-col>
      <el-col :span="7">
        <car-list class="car-box"/>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import carList from "@/views/w2/kcdd/carList";
import ddzx from "@/views/w2/kcdd/ddzx";
import TaskList from "@/views/w2/kcdd/taskList";
import FlowList from "@/views/w2/kcdd/flowList";

export default {
  name: "Kcdd",
  components: {FlowList, TaskList, carList},
  data() {
    return {}
  },
  created() {
  },
  methods: {}
};
</script>
<style lang="scss" scoped>
#app-container{
  ::v-deep .row-red {
   background-color: #ffd3d3 !important;
  }
  ::v-deep .row-blue {
    background-color: #B9DBF5FF !important;
  }
  ::v-deep .row-red-deep {
    background-color: #f67c7c !important;
  }
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

#app-container{
  min-height: calc(100vh - 125px)
}
.row-top{
  min-height: calc((100vh - 125px)*0.6);
  border-bottom: 3px solid #409EFF;
}
.row-bottom{
  height: calc((100vh - 125px)*0.4);
  overflow-y: auto;
}
.col-left{
  border-right: 3px solid #409EFF;
}
.car-box{
  padding:0 0 0 20px !important;
}
.flow-box{
  padding:0 20px 0 0 !important;
}
/*.divider-class{*/
/*  float:left;*/
/*}*/
/*.car-box{*/
/*  float:left;*/
/*  width:360px;*/
/*}*/
</style>
