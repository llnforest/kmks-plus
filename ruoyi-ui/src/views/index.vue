<template>
  <div class="app-container home">
    <el-row :gutter="20">
    <div class="home-tip">
      欢迎您，<span class="home-item">{{userData.nickName}}</span>【<span class="home-item">{{userData.userName}}</span>】，您的账号有效期还剩<span class="home-item">{{leafUserDay}}</span>天，密码有效期还剩<span class="home-item">{{leafPassDay}}</span>天。
    </div>
    <div class="home-tip">
        您本次登录时间为：<span class="home-item">{{userData.loginDate}}</span>，IP地址是<span class="home-item">{{userData.loginIp}}</span>。
    </div>
    <div class="home-tip">
        您上次登录时间为：<span class="home-item">{{userData.lastLoginDate}}</span>，IP地址是<span class="home-item">{{userData.lastLoginIp}}</span>。
    </div>
    </el-row>
    <el-divider content-position="left">登录失败记录</el-divider>
    <el-table ref="tables" v-loading="loading" :data="list" >
      <el-table-column label="用户名称" align="center" prop="userName" :show-overflow-tooltip="true" min-width="180" />
      <el-table-column label="登录地址" align="center" prop="ipaddr" width="130" :show-overflow-tooltip="true" min-width="180" />
      <el-table-column label="浏览器" align="center" prop="browser" :show-overflow-tooltip="true"  min-width="180"/>
      <el-table-column label="操作系统" align="center" prop="os" min-width="220" v-if="false"/>
      <el-table-column label="MAC地址" align="center" prop="mac"  min-width="150"/>
      <el-table-column label="登录状态" align="center" prop="status"  min-width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_common_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作信息" align="left" prop="msg"  min-width="300"/>
      <el-table-column label="登录日期" align="center" prop="loginTime" sortable="custom" :sort-orders="['descending', 'ascending']"  min-width="165">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.loginTime) }}</span>
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


  </div>
</template>

<script>

import { list } from  '@/api/login';

export default {
  name: "Index",
  dicts: ['sys_common_status'],
  data() {
    return {
      // 用户信息
      userData: this.$store.getters.userData,// 总条数
      total: 0,
      // 遮罩层
      loading: true,
      // 表格数据
      list: [],
      // 日期范围
      dateRange: [],
      // 默认排序
      defaultSort: {prop: 'loginTime', order: 'descending'},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      }
    };
  },
  computed:{
    leafUserDay(){
      return this.getDiffDay(this.userData.validUserTime)
    },
    leafPassDay(){
      return this.getDiffDay(this.userData.validPassTime)
    }
  },
  created() {
    this.getList();
  },
  methods: {
    getDiffDay(myDate) {
      // 计算两个日期之间的差值
      let totalDays, diffDate
      myDate = Date.parse(myDate)
      // 将两个日期都转换为毫秒格式，然后做差
      diffDate = myDate - new Date() // 取相差毫秒数的绝对值
      totalDays = Math.floor(diffDate / (1000 * 3600 * 24)) // 向下取整
      // console.log(totalDays)
      return totalDays // 相差的天数
    },
    getList() {
      this.loading = true;
      list(this.queryParams).then(response => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
  },
};
</script>

<style scoped lang="scss">
.home-tip{
  border-left: 5px solid #26c2ff;
  background: #e8f6fc;
  padding: 10px 7px;
  margin-bottom: 10px;
  font-size: 13px;
  border-radius: 5px;
}
.home-item{
  color: #26c2ff;
}
</style>

