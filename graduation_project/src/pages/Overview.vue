<template>
  <div>
<!--    <UserCard class="half-left"></UserCard>-->
<!--    <cluster-mes class="half-right"></cluster-mes>-->
<!--    <UserCard class="half-left"></UserCard>-->
<!--    <cluster-mes class="half-right"></cluster-mes>-->
    <el-row type="flex" justify="end">
      <el-col>
        <UserCard></UserCard>
      </el-col>
      <el-col>
        <cluster-mes></cluster-mes>
      </el-col>
    </el-row>
    <el-card shadow="hover" v-if="username !== 'root'">
          <span>实例概览</span><Br/>
          <el-table
              :data="namespacedPodList"
              style="width: 100%;margin-left: 10%"
              height="300"
              :cell-style="cellStyle">
            <el-table-column
                prop="name"
                label="实例名称"
                width="400">
            </el-table-column>
            <el-table-column
                prop="status"
                label="状态"
                width="400">
            </el-table-column>
            <el-table-column
                prop="ip"
                label="Pod-IP"
                width="400">
            </el-table-column>

          </el-table>
        </el-card>
    <el-card shadow="hover" v-if="username === 'root'">
      <span>实例概览</span><Br/>
      <el-table
          :data="instanceList"
          style="width: 100%;margin-left: 12%"
          height="300"
          :cell-style="cellStyle">
        <el-table-column
            prop="name"
            label="实例名称"
            width="300">
        </el-table-column>
        <el-table-column
            prop="ip"
            label="实例IP"
            width="300">
        </el-table-column>
        <el-table-column
            prop="cpu"
            label="CPU"
            width="300">
        </el-table-column>
        <el-table-column
            prop="memory"
            label="内存"
            width="300">
        </el-table-column>

      </el-table>
    </el-card>



  </div>
</template>

<script>
import UserCard from "@/components/UserCard"
import ClusterMes from "@/components/ClusterMes";
import {mapState} from "vuex";


export default {
  name: 'Overview',
  components: {
    ClusterMes,
    UserCard,
  },
  data(){
    return{
      serviceInfo:{
        instanceName:'',
        username:''
      }
    }
  },
  computed:{
    ...mapState({
      namespacedPodList:state => state.namespacedPodList,
      username:state => state.user.username,
      instanceList:state => state.instanceList
    })
  },
  methods:{
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

  },
  mounted() {
    this.serviceInfo.instanceName = this.$store.state.nowInstance
    this.serviceInfo.username = this.$store.state.user.username
    this.$store.dispatch('InstanceList',this.serviceInfo.username)
    this.$store.dispatch('getNamespacedService',this.serviceInfo)
    this.$store.dispatch('getNamespacedPodList',{
      instanceName:this.$store.state.nowInstance,
      username:this.$store.state.user.username
    })
  }
}
</script>

<style scoped>
.half-left{
  width: 45%;
  height: 30%;
  margin-left: 5%;
  margin-top: 5%;
}
.half-right{
  width: 45%;
  height: 30%;
  margin-right: 5%;
  margin-top: 5%;
}


</style>