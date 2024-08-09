<template>
  <div class="app-container">
    <el-tabs>
      <el-tab-pane label="约考查询">
        <about-list class="tab-content"/>
      </el-tab-pane>
      <el-tab-pane label="任务调度">
        <task-list class="tab-content"></task-list>
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>

import aboutList from "@/views/w2/kcdd/aboutList";
import taskList from "@/views/w2/kcdd/taskList";

export default {
  name: "ddzx",
  components: {taskList,aboutList},
  data() {
    return {

    };
  },
  created() {
  },
  methods: {

  }
};
</script>
<style scoped>
.app-container{
  padding: 0 20px 20px 0 !important;
}
.tab-content{
  padding-bottom:30px;
}
</style>

