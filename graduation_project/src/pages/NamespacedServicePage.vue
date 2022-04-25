<template>
  <div>
    <el-card class="box-card" shadow="hover" style="width: 40%;transform: translateY(103%)">
      <el-row type="flex" justify="center">
        <el-col>
          <el-icon class="el-icon-cloudy" style="font-size: 200px"></el-icon>
        </el-col>
        <el-col>
          <div style="margin-top: 20%">
            <span class="18px large">实例状态</span><Br/>
            <span class="18px large">总数量：{{ namespacedServiceList.length }}</span><Br/>
            <el-button type="primary" @click="goApplicationPage" style="margin-top: 10%">实例申请</el-button>
          </div>
        </el-col>
      </el-row>
    </el-card>
    <!--    <div v-for="service in serviceList" :key="service.name" style="transform: translateX(50%)">-->
    <!--      <ServiceCard :service="service" style="transform: translateY(-100%)" ></ServiceCard>-->
    <!--    </div>-->
    <template>
      <el-table
          :data="instanceInfoList"
          style="width: 60%;margin-left: 40%"
          height="250"
          :cell-style="cellStyle">
        <el-table-column
            prop="name"
            label="实例名称"
            width="140">
        </el-table-column>
        <el-table-column
            prop="status"
            label="状态"
            width="140">
        </el-table-column>
        <el-table-column
            prop="ip"
            label="Pod-IP"
            width="140">
        </el-table-column>
        <el-table-column
            prop="nodePort"
            label="nodePort"
            width="140">
        </el-table-column>
        <el-table-column width="140">
          <template slot-scope="scope">
            <el-button type="primary"
                       @click="goTerminal(scope.row)"
            >进入终端</el-button>
          </template>
        </el-table-column>
        <el-table-column width="140">
          <template slot-scope="scope">
            <el-button type="danger"
                       @click="deleteIns(scope.row)"
                       :loading="loading"
            >释放实例</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>

  </div>
</template>

<script>

import {mapState} from "vuex";

export default {
  name: "NamespacedServicePage",
  data(){
    return{
      serviceInfo:{
        instanceName:'',
        username:'',
      },
      loading:false,
      deleteInfo:{
        instanceName:'',
        podName:'',
        username:''
      }
    }
  },
  computed:{
    ...mapState({
      namespacedServiceList: state => state.namespacedServiceList,
      namespacedPodList:state => state.namespacedPodList,
      instanceInfoList:state => state.instanceInfoList
    }),
  },
  watch:{
    namespacedServiceList:{
      handler(){
        this.$store.commit('ChangeInstanceInfoList')
      }
    },
    namespacedPodList:{
      handler(){
        this.$store.commit('ChangeInstanceInfoList')
      }
    }
  },
  methods:{
    goTerminal(row) {
      this.$store.commit('ChangeNowNodePort',row.nodePort)
      this.$router.push({
        path:'/webssh2'
      })
    },
    goApplicationPage(){
      this.$bus.$emit('showPodApplicationPage')
    },
    cellStyle(row){
      if (row.column.label === "状态"){
        if (row.row.status === 'Running'){
          return "color: #67C23A"
        }else if (row.row.status === 'Failed'){
          return "color: firebrick"
        }else {
          return "color: gold"
        }
      }
    },
    deleteIns(row){
      this.loading = true
      this.deleteInfo.instanceName = this.$store.state.nowInstance
      this.deleteInfo.podName = row.name
      this.deleteInfo.username = localStorage.getItem('Username')
      this.$store
          .dispatch("deleteNamespacedPod", this.deleteInfo)
          .then(response => {//声明response变量，类型是json
            this.loading = false;
            let code = response.data.code;//后端传给前端的值为data，code，message，传值给code
            if (code === 200) {
              alert("实例释放成功！！！")
              this.$store.dispatch('getNamespacedService',this.serviceInfo)
              this.$store.dispatch('getNamespacedPodList',this.serviceInfo)
            } //this.$router.push根据路由将query里面的信息push给/success
            else {
              alert("实例释放失败！！！："+response.data.message)
            }
          })
    }



    // deleteNamespacedService(row){
    //   this.loading = true
    //   this.$store
    //       .dispatch("deleteService", {
    //         instanceName:this.$store.state.nowInstance,
    //         serviceName:row.name,
    //         namespace:row.namespace
    //       })//读取loginForm里面的username和password
    //       .then(response => {//声明response变量，类型是json
    //         this.loading = false;
    //         let code = response.data.code;//后端传给前端的值为data，code，message，传值给code
    //         if (code === 200) {
    //           alert("删除成功！！！")
    //           this.$store.dispatch('getServiceList',this.$store.state.nowInstance)
    //         } //this.$router.push根据路由将query里面的信息push给/success
    //         else {
    //           alert("删除失败！！！："+response.data.message)
    //         }
    //       })
    // }
  },
  mounted() {
    this.serviceInfo.instanceName = this.$store.state.nowInstance
    this.serviceInfo.username = localStorage.getItem('Username')
    this.$store.dispatch('getNamespacedService',this.serviceInfo)
    this.$store.dispatch('getNamespacedPodList',this.serviceInfo)

  }

}
</script>

<style scoped>

</style>