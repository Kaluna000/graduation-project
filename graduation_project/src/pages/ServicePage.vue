<template>
<div>
    <el-card class="box-card" shadow="hover" style="width: 40%;transform: translateY(100%)">
      <el-row type="flex" justify="center">
        <el-col>
          <el-icon class="el-icon-cloudy" style="font-size: 200px"></el-icon>
        </el-col>
        <el-col>
          <div style="margin-top: 10%">
            <span class="18px large">Service状态</span><Br/>
            <span class="18px large">总数量：{{ serviceList.length }}</span><Br/>
            <el-button type="primary" style="margin-top: 10%" @click="drawer = true">创建Service</el-button>
          </div>
        </el-col>
      </el-row>
    </el-card>
<!--    <div v-for="service in serviceList" :key="service.name" style="transform: translateX(50%)">-->
<!--      <ServiceCard :service="service" style="transform: translateY(-100%)" ></ServiceCard>-->
<!--    </div>-->
  <template>
    <el-table
        :data="serviceList"
        style="width: 60%;margin-left: 40%"
        height="250">
      <el-table-column
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
          prop="matchLabelsApp"
          label="MatchApp"
          width="140">
      </el-table-column>
      <el-table-column
          prop="type"
          label="类型"
          width="140">
      </el-table-column>
      <el-table-column
          prop="clusterIp"
          label="Cluster-IP"
          width="140">
      </el-table-column>
      <el-table-column
          prop="port"
          label="Port(s)"
          width="140">
      </el-table-column>
      <el-table-column width="140">
        <template slot-scope="scope">
          <el-button type="danger"
                     @click="deleteService(scope.row)"
                     :loading="loading"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </template>


  <el-drawer
      title="创建Service"
      :visible.sync="drawer"
      :direction="direction"
      :before-close="handleClose">
    <ServiceCreate></ServiceCreate>
  </el-drawer>
</div>
</template>

<script>

import {mapState} from "vuex";
import ServiceCreate from "@/components/ServiceCreate";
export default {
  name: "ServicePage",
  components: {ServiceCreate},
  data(){
    return {
      drawer: false,
      direction: 'ltr',
    }
  },
  computed:{
    ...mapState({
      serviceList: state => state.serviceList
    })
  },
  methods:{
    handleClose(done) {
      this.$confirm('确认放弃？')
          .then(() => {
            done();
          })
          .catch(() => {});
    },
    deleteService(row){
      this.loading = true
      this.$store
          .dispatch("deleteService", {
            instanceName:this.$store.state.nowInstance,
            serviceName:row.name,
            namespace:row.namespace
          })//读取loginForm里面的username和password
          .then(response => {//声明response变量，类型是json
            this.loading = false;
            let code = response.data.code;//后端传给前端的值为data，code，message，传值给code
            if (code === 200) {
              alert("删除成功！！！")
              this.$store.dispatch('getServiceList',this.$store.state.nowInstance)
            } //this.$router.push根据路由将query里面的信息push给/success
            else {
              alert("删除失败！！！："+response.data.message)
            }
          })
    }
  },
  mounted() {
    this.$store.dispatch('getServiceList',this.$store.state.nowInstance)
    this.$store.dispatch('getNamespaceList',this.$store.state.nowInstance)
  }

}
</script>

<style scoped>

</style>