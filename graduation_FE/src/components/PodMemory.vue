<template>
  <div>
    <el-row type="flex" justify="start">
      <span style="transform: translateY(30%);width: 15%">
        内存规格(Mi)
      </span>
      <el-button type="primary" @click="reset">重置</el-button>
      <el-card class="el-card" shadow="hover" style="width: 90%">
        <span style="color: cornflowerblue">最小保证</span>
        <div class="block">
          <el-slider
              :min="1"
              :max="500"
              v-model="minMemory"
              show-input>
          </el-slider>
        </div>
      </el-card>
      <el-card class="el-card" shadow="hover" style="width: 90%">
        <span style="color: cornflowerblue">最大限制</span>
        <div class="block">
          <el-slider
              :min="1024"
              :max="2048"
              v-model="maxMemory"
              show-input>
          </el-slider>
        </div>
      </el-card>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "PodMemory",
  data() {
    return {

    }
  },
  computed:{
    maxMemory:{
      get(){
        return this.$store.state.maxMemory
      },
      set(value){
        this.$store.commit('ChangeMaxMemory',value)
      }
    },
    minMemory:{
      get(){
        return this.$store.state.minMemory
      },
      set(value){
        this.$store.commit('ChangeMinMemory',value)
      }
    },
  },
  methods:{
    reset(){
      this.$store.commit("ChangeMaxMemory",0);
      this.$store.commit("ChangeMinMemory",0);
    },
  }
}
</script>

<style scoped>

</style>