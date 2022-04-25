<template>
  <div>
    <el-card class="box-card" shadow="hover" style="width: 40%;transform: translateY(100%)">
      <el-row type="flex" justify="center">
        <el-col>
          <el-icon class="el-icon-s-grid" style="font-size: 200px"></el-icon>
        </el-col>
        <el-col>
          <div style="margin-top: 10%">
            <span class="18px large">Deployments状态</span><Br/>
            <span class="18px large">总数量：{{ deploymentList.length }}</span><Br/>
            <el-button type="primary" style="margin-top: 10%" @click="drawer = true">创建Deployment</el-button>
          </div>
        </el-col>
      </el-row>
    </el-card>
<!--    <div v-for="deployment in deploymentList" :key="deployment.name" style="transform: translateX(50%)">-->
<!--      <DeploymentsCard :deployment="deployment" style="transform: translateY(-100%)"></DeploymentsCard>-->
<!--    </div>-->
    <template>
      <el-table
          :data="deploymentList"
          style="width: 60%;margin-left: 40%"
          height="250">
        <el-table-column
            fixed
            prop="name"
            label="名称"
            width="140">
        </el-table-column>
        <el-table-column
            prop="namespace"
            label="名称空间"
            width="140">
        </el-table-column>
        <el-table-column
            prop="readyReplicas"
            label="副本情况"
            width="140">
          <template slot-scope="scope">
            <span style="color: #409eff">
              {{scope.row.readyReplicas}}/{{scope.row.replicas}}
            </span>
          </template>
        </el-table-column>
        <el-table-column
            prop="matchLabelsApp"
            label="MatchApp"
            width="140">
        </el-table-column>
        <el-table-column width="140">
          <template slot-scope="scope">
            <el-button type="danger"
                       @click="deleteDeployment(scope.row)"
                       :loading="loading"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>


    <el-drawer
        title="创建Deployment"
        ref="drawerRef"
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose">
      <DeploymentCreate :drawerRef="this.$refs.drawerRef"></DeploymentCreate>
    </el-drawer>
  </div>
</template>

<script>


import {mapState} from "vuex";
import DeploymentCreate from "@/components/DeploymentCreate";
export default {
  name: "DeploymentsPage",
  components: {DeploymentCreate},
  data(){
    return {
      drawer: false,
      direction: 'ltr',
      loading: false,
    }
  },
  computed:{
    // living(){
    //   return this.pods.reduce((pre,pod) => pre+(pod.status === "running" ? 1 : 0) ,0)
    // },
    ...mapState({
      deploymentList: state => state.deploymentList
    }),
  },
  methods:{
    handleClose(done) {
      this.$confirm('确认放弃？')
          .then(() => {
            done();
          })
          .catch(() => {});
    },
    deleteDeployment(row){
      this.loading = true
      this.$store
          .dispatch("deleteDeployment", {
            instanceName:this.$store.state.nowInstance,
            deploymentName:row.name,
            namespace:row.namespace
          })//读取loginForm里面的username和password
          .then(response => {//声明response变量，类型是json
            this.loading = false;
            let code = response.data.code;//后端传给前端的值为data，code，message，传值给code
            if (code === 200) {
              alert("删除成功！！！")
              this.$store.dispatch('getDeploymentList',this.$store.state.nowInstance)
            } //this.$router.push根据路由将query里面的信息push给/success
            else {
              alert("删除失败！！！："+response.data.message)
            }
          })
    }
  },
  mounted() {
    this.$store.dispatch('getDeploymentList',this.$store.state.nowInstance)
    this.$store.dispatch('getNamespaceList',this.$store.state.nowInstance)
  }
}
</script>

<style scoped>

</style>