<template>
  <div id="app">
    <router-view />
    <theme-picker />
    <el-dialog :visible.sync="resetPass" title="账户激活后请修改密码" width="400px" :close-on-press-escape="false" :close-on-click-modal="false" :show-close="false" append-to-body>
      <resetPwd />
    </el-dialog>
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker";
import ResetPwd from "@/views/system/user/profile/resetPwd";
import {mapGetters} from "vuex";

export default {
  name: "App",
  components: { ThemePicker, ResetPwd },
  computed:{
    ...mapGetters(['resetPass']),
  },
  metaInfo() {
      return {
          title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
          titleTemplate: title => {
              return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
          }
      }
  }
};
</script>
<style>
#app .theme-picker {
  display: none;
}
.tip-msg{
  border-left: 5px solid #26c2ff;
  background: #e8f6fc;
  padding: 10px 7px;
  margin-bottom: 10px;
  font-size: 13px;
  border-radius: 5px;
}
</style>
