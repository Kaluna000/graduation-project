<template>
  <div>
    <el-card class="box-card" shadow="hover" style="width: 40%;transform: translateY(100%)">
      <el-row type="flex" justify="center">
        <el-col>
          <el-icon class="el-icon-menu" style="font-size: 200px"></el-icon>
        </el-col>
        <el-col>
          <div style="margin-top: 25%" v-if="username === 'root'">
            <span class="18px large">Pods状态</span><Br/>
            <span class="18px large">存活数量：{{this.podList.filter(item => item.status == 'Running').length}}</span><Br/>
            <span class="18px large">总数量：{{podList.length}}</span><Br/>
          </div>
          <div style="margin-top: 25%" v-if="username !== 'root'">
            <span class="18px large">Pods状态</span><Br/>
            <span class="18px large">存活数量：{{this.namespacedPodList.filter(item => item.status == 'Running').length}}</span><Br/>
            <span class="18px large">总数量：{{namespacedPodList.length}}</span><Br/>
          </div>
        </el-col>
      </el-row>
    </el-card>
<!--    <div v-for="pod in podList" :key="pod.name" style="transform: translateX(50%)">-->
<!--      <PodsCard :pod="pod" style="transform: translateY(-140%)"></PodsCard>-->
<!--    </div>-->
    <div v-if="username === 'root'">
      <el-table
          :data="podList"
          style="width: 60%;margin-left: 40%;"
          height="250"
          :cell-style="cellStyle">
        <el-table-column
            prop="name"
            label="名称"
            width="170">
        </el-table-column>
        <el-table-column
            prop="status"
            label="状态"
            width="170">
        </el-table-column>
        <el-table-column
            prop="namespace"
            label="名称空间"
            width="170">
        </el-table-column>
        <el-table-column
            prop="startTime"
            label="开始时间"
            width="170">
        </el-table-column>

        <!--      <el-table-column width="140">-->
        <!--        <el-button type="danger"-->
        <!--                   @click="deleteService"-->
        <!--                   :loading="loading"-->
        <!--        >删除</el-button>-->
        <!--      </el-table-column>-->
      </el-table>
    </div>
    <div v-if="username !== 'root'">
      <el-table
          :data="namespacedPodList"
          style="width: 50%;margin-left: 50%;"
          height="250"
          :cell-style="cellStyle">
        <el-table-column
            prop="name"
            label="名称"
            width="170">
        </el-table-column>
        <el-table-column
            prop="status"
            label="状态"
            width="170">
        </el-table-column>
        <el-table-column
            prop="namespace"
            label="名称空间"
            width="170">
        </el-table-column>
        <el-table-column
            prop="startTime"
            label="开始时间"
            width="170">
        </el-table-column>

        <!--      <el-table-column width="140">-->
        <!--        <el-button type="danger"-->
        <!--                   @click="deleteService"-->
        <!--                   :loading="loading"-->
        <!--        >删除</el-button>-->
        <!--      </el-table-column>-->
      </el-table>
    </div>


  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "PodsPage",
  data(){
    return{
      namespaceInfo:{
        instanceName:'',
        username:localStorage.getItem('Username'),
      }
    }
  },
  computed: {
    // living(){
    //   return this.pods.reduce((pre,pod) => pre+(pod.status === "running" ? 1 : 0) ,0)
    // },
    ...mapState({
      podList: state => state.podList,
      namespacedPodList:state => state.namespacedPodList,
      username:state => state.user.username
    }),
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

    }
  },
  mounted() {
    this.namespaceInfo.instanceName = this.$store.state.nowInstance
    this.$store.dispatch('getPodList',this.$store.state.nowInstance)
    this.$store.dispatch('getNamespaceList',this.$store.state.nowInstance)
    this.$store.dispatch('getNamespacedPodList',this.namespaceInfo)
  }
}
</script>

<style scoped>

</style>