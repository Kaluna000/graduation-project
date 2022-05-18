<template>
  <div>
    <PodCPU></PodCPU>
    <el-divider></el-divider>
    <PodMemory></PodMemory>
    <el-divider></el-divider>
    <PodStorage></PodStorage>
    <el-divider></el-divider>
    <el-row>

       <span style="margin-left: 100px;font-size: 25px">
      linux:
      <template>
        <span style="color: #409eff">
          centOS 7
        </span>
      </template>
    </span>
      <span style="margin-left: 70px">
      实例名称(小写):
    </span>
      <el-input style="width: 30%" v-model="podName">
      </el-input>
      <span style="margin-left: 70px">
      镜像:amadeus000/mycentosssh:v1.0
    </span>
    </el-row>

    <el-row type="flex" justify="start" style="margin-top: 3%;margin-left: 100px">
      <el-button @click="handleButton()" type="primary" style="margin-left: 16px;">
        计算价格
      </el-button>
      <!--      抽屉弹窗显示计算结果-->
      <el-drawer
          title="计算结果"
          :visible.sync="drawer"
          :direction="direction"
          :before-close="handleClose">
        <CalculationResults></CalculationResults>
      </el-drawer>
    </el-row>

  </div>
</template>

<script>

import CalculationResults from "@/components/CalculationResults";
import PodCPU from "@/components/PodCPU";
import PodMemory from "@/components/PodMemory";
import PodStorage from "@/components/PodStorage";


export default {
  name: "PodApplicationPage",
  components: {PodStorage, PodMemory, PodCPU, CalculationResults},
  data() {
    return {
      drawer: false,
      direction: 'btt',
      podName:''
    };
  },
  methods: {
    handleClose(done) {
      this.$confirm('确认放弃？')
          .then(() => {
            done();
          })
          .catch(() => {});
    },
    handleButton(){
      if (this.podName === ''){
        alert('请输入实例名称！！')
      }else {
        this.$store.commit('ChangePodName',this.podName)
        this.drawer = true
      }
    }
  },

}
</script>

<style scoped>

</style>