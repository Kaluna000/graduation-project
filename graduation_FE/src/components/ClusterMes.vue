<template>
  <div>
    <el-card class="box-card" shadow="hover">
      <span>节点情况</span><Br/>
      <el-row type="flex" justify="center">
        <el-col>
          <el-icon class="el-icon-s-help" style="font-size: 250px;color: cornflowerblue"></el-icon>
        </el-col>
        <el-col v-if="username === 'root'">
          <div style="margin-top: 20%">
            <span class="18px large">实例在线状态</span><Br/>
            <span class="18px large">在线实例:
            <span style="font-size: 20px;color: #409eff" >{{onlineInstance}}</span>
            </span><Br/>
            <span class="18px large">全部实例:
            <span style="font-size: 20px;color: #409eff" >{{allInstance}}</span>
            </span><Br/>
            <el-button type="primary" style = "margin-top: 10%" @click="goInstancePage">实例管理</el-button>
          </div>
        </el-col>
        <el-col v-if="username !== 'root'">
          <div style="margin-top: 20%">
            <span class="18px large">实例在线状态</span><Br/>
            <span class="18px large">在线实例:
            <span style="font-size: 20px;color: #409eff" >{{namespacedServiceList.length}}</span>
            </span><Br/>
            <span class="18px large">全部实例:
            <span style="font-size: 20px;color: #409eff" >{{namespacedServiceList.length}}</span>
            </span><Br/>
            <el-button type="primary" style = "margin-top: 10%" @click="goInstancePage">实例管理</el-button>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "ClusterMes",
  computed:{
    ...mapState({
      allInstance: state => state.instanceList.length,
      onlineInstance: state => state.instanceList.filter(item => item.running == true).length,
      username: state => state.user.username,
      namespacedServiceList:state => state.namespacedServiceList
    }),

  },
  methods:{
    goInstancePage(){
      if (this.username === 'root'){
        this.$bus.$emit('showInstancePage')
      }else {
        this.$bus.$emit('showNamespacedServicePage')
      }
    }

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