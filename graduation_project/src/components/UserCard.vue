<template>
  <div>
    <el-card class="box-card" shadow="hover">
      <span>概览</span><Br/>
      <el-row>
        <el-col :span="20" style="transform: translateX(10%)"><div class="grid-content bg-purple" style="height: 70px">
          <el-icon class="el-icon-user" style="font-size: 70px;"></el-icon>
          <div style="margin-left: 15%;margin-top: -10%">
            <span style="color: #303133;font-size: 20px">你好，{{username}}</span><Br/>
            <span style="color: #606266">{{usertype}}</span>
          </div>
        </div>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="10" v-if="username === 'root'" style="transform: translateX(20%)"><div class="grid-content bg-purple" style="height: 70px">
          <el-icon class="el-icon-monitor" style="font-size: 70px;"></el-icon>
          <div style="margin-left: 30%;margin-top: -20%">
            <span style="color: #409eff;font-size: 20px">{{allInstance}}</span><Br/>
            <span style="color: #606266">我的实例</span>
          </div>
        </div></el-col>

        <el-col :span="10" v-if="username !== 'root'" style="transform: translateX(20%)"><div class="grid-content bg-purple" style="height: 70px">
          <el-icon class="el-icon-monitor" style="font-size: 70px;"></el-icon>
          <div style="margin-left: 30%;margin-top: -20%">
            <span style="color: #409eff;font-size: 20px">{{$store.state.namespacedPodList.length}}</span><Br/>
            <span style="color: #606266">我的实例</span>
          </div>
        </div></el-col>

        <el-col v-if="username !== 'root'" :span="10" style="transform: translateX(20%)"><div class="grid-content bg-purple" style="height: 70px">
          <el-icon class="el-icon-money" style="font-size: 70px;"></el-icon>
          <div style="margin-left: 30%;margin-top: -20%">
            <span style="color: #409eff;font-size: 20px">{{ remaining }}</span><Br/>
            <span style="color: #606266">我的余额</span>
          </div>
        </div></el-col>
        <el-col v-if="username === 'root'" :span="10" style="transform: translateX(20%)"><div class="grid-content bg-purple" style="height: 70px">
          <el-icon class="el-icon-s-custom" style="font-size: 70px;"></el-icon>
          <div style="margin-left: 30%;margin-top: -20%">
            <span style="color: #409eff;font-size: 20px">{{ userNum }}</span><Br/>
            <span style="color: #606266">活跃用户</span>
          </div>
        </div></el-col>
      </el-row>
      <el-row :gutter="15">
        <el-col v-if="username === 'root'" :span="20" style="transform: translateX(10%)"><div class="grid-content bg-purple" style="height: 70px">
          <el-icon class="el-icon-suitcase-1" style="font-size: 70px;"></el-icon>
          <div style="margin-left: 15%;margin-top: -10%">
            <span style="color: #409eff;font-size: 20px">{{$store.state.deploymentList.length}}</span><Br/>
            <span style="color: #606266">我的部署</span>
          </div>
        </div></el-col>
        <el-col v-if="username !== 'root'" :span="20" style="transform: translateX(10%)"><div class="grid-content bg-purple" style="height: 70px">
          <el-icon class="el-icon-suitcase-1" style="font-size: 70px;"></el-icon>
          <div style="margin-left: 15%;margin-top: -10%">
            <span style="color: #409eff;font-size: 20px">0</span><Br/>
            <span style="color: #606266">我的部署</span>
          </div>
        </div></el-col>
      </el-row>

    </el-card>
  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: "UserCard",
  computed:{
    ...mapState({
      username: state => state.user.username,
      usertype: state => state.user.usertype,
      allInstance: state => state.instanceList.length,
      userNum: state => state.userNum,
      remaining:state => state.remaining
    })
  },
  mounted() {
    this.$store.dispatch('getUserNum')
    this.$store.dispatch('getUserRemain',this.$store.state.user.username)
    this.$store.dispatch('getNamespacedPodList',{
      instanceName:this.$store.state.nowInstance,
      username:this.$store.state.user.username
    })
  }
}
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
}
.el-col {
  border-radius: 4px;
}

.bg-purple-dark {
  background: #99a9bf;
}

.bg-purple {
  background: #d3dce6;
}

.bg-purple-light {
  background: #e5e9f2;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

</style>