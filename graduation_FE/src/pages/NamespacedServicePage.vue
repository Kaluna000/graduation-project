<template>
  <div>
    <el-card class="box-card" shadow="hover" style="width: 30%;transform: translateY(103%)">
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
          style="width: 70%;margin-left: 30%"
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
            prop="life"
            label="到期时间"
            width="220">
          <template slot-scope="scope">
            <span>
              {{scope.row.life.split(' ')[0]}}
            </span>
            <el-alert
                v-if="goingDie(scope.row)"
                title="即将到期,请及时续费"
                type="warning"
                show-icon>
            </el-alert>
          </template>

        </el-table-column>

        <el-table-column width="140">
          <template slot-scope="scope">
            <el-button type="primary"
                       @click="goTerminal(scope.row)"
            >进入终端</el-button>
          </template>
        </el-table-column>
        <el-table-column width="140">
<!--          <template slot-scope="scope">-->
<!--            <el-button type="danger"-->
<!--                       @click="deleteIns(scope.row)"-->
<!--                       :loading="loading"-->
<!--            >释放实例</el-button>-->
<!--          </template>-->
          <template slot-scope="scope">
            <el-button type="danger"
                       @click="renew(scope.row)"
                       :loading="loading"
            >续费</el-button>
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
      },
      // podListReady:false,
      // serviceListReady:false
    }
  },
  computed:{
    ...mapState({
      namespacedServiceList: state => state.namespacedServiceList,
      namespacedPodList:state => state.namespacedPodList,
      instanceLifeList:state => state.instanceLifeList,
      instanceInfoList:state => state.instanceInfoList
    }),
  },
  watch:{
    // namespacedServiceList:{
    //   handler(){
    //     this.$store.commit('ChangeInstanceInfoList')
    //   }
    // },
    // namespacedPodList:{
    //   handler(){
    //     this.$store.commit('ChangeInstanceInfoList')
    //   }
    // }
    namespacedServiceList(){
      if (this.namespacedPodList.length !== 0 && this.instanceLifeList.length !== 0){
        this.$store.commit('ChangeInstanceInfoList')
      }
    },
    namespacedPodList(){
      if (this.namespacedServiceList.length !== 0 && this.instanceLifeList.length !== 0){
        this.$store.commit('ChangeInstanceInfoList')
      }
    },
    instanceLifeList(){
      if(this.namespacedPodList.length !== 0 && this.namespacedServiceList.length !== 0){
        this.$store.commit('ChangeInstanceInfoList')
      }
    }
  },
  methods:{
    //进入特定行实例的shell界面
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
            }
            else {
              alert("实例释放失败！！！："+response.data.message)
            }
          })
      this.loading = false
    },
    goingDie(row){
      console.log(row.life)
      var nowDateM = (new Date()).getTime(); //得到现在毫秒数
      var dieDate = row.life  //到期时间
      dieDate= dieDate.replace(new RegExp("-","gm"),"/"); //替换格式
      var dieDateM = (new Date(dieDate)).getTime()  //得到到期毫秒数
      if(dieDateM - nowDateM >= 2678400000){
        return false
      }else return true
    },
    renew(row){
      console.log(row.name)
      this.$store.commit('ChangeRenewPodName',row.name)
      this.$bus.$emit('renew')
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
    this.serviceInfo.username = this.$store.state.user.username
    this.$store.dispatch('getNamespacedService',this.serviceInfo)
    this.$store.dispatch('getNamespacedPodList',this.serviceInfo)
    this.$store.dispatch('getInstanceLife',this.$store.state.user.username)
    // if (this.$store.state.namespacedPodList.length !== 0 &&this.$store.state.namespacedServiceList.length !== 0 && this.$store.state.instanceLifeList.length !== 0){
    //   this.$store.commit('ChangeInstanceInfoList')
    // }
  }

}
</script>

<style scoped>

</style>