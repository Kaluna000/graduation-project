<template>
  <div>
    <el-row type="flex" justify="start">
      <span style="transform: translateY(30%);width: 15%">
        CPU规格(m)
      </span>
      <el-button type="primary" @click="reset">重置</el-button>
      <el-card class="el-card" shadow="hover" style="width: 90%">
        <span style="color: cornflowerblue">最小保证</span>
        <div class="block">
          <el-slider
              :min="1"
              :max="500"
              v-model="minCPU"
              show-input>
          </el-slider>
        </div>
      </el-card>
      <el-card class="el-card" shadow="hover" style="width: 90%">
        <span style="color: cornflowerblue">最大限制</span>
        <div class="block">
          <el-slider
              :min="200"
              :max="1000"
              v-model="maxCPU"
              show-input>
          </el-slider>
        </div>
      </el-card>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "PodCPU",
  data() {
    return {

    }
  },
  computed:{
    maxCPU:{
      get(){
        console.log("从store获取maxCPU")
        return this.$store.state.maxCPU
      },
      set(value){
        this.$store.commit('ChangeMaxCPU',value)
      }
    },
    minCPU:{
      get(){
        return this.$store.state.minCPU
      },
      set(value){
        this.$store.commit('ChangeMinCPU',value)
      }
    },
  },
  methods:{
    reset(){
      this.$store.commit("ChangeMaxCPU",0);
      this.$store.commit("ChangeMinCPU",0);
    },
  }
}
</script>

<style scoped>

</style>