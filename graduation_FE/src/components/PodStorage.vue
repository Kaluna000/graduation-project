<template>
  <div>
    <el-row type="flex" justify="start">
      <span style="transform: translateY(30%);width: 15%">
        存储规格(Gi)
      </span>
      <el-button type="primary" @click="reset">重置</el-button>
      <el-card class="el-card" shadow="hover" style="width: 90%">
        <span style="color: cornflowerblue">最小保证</span>
        <div class="block">
          <el-slider
              :min="1"
              :max="10"
              v-model="minEphemeralStorage"
              show-input>
          </el-slider>
        </div>
      </el-card>
      <el-card class="el-card" shadow="hover" style="width: 90%">
        <span style="color: cornflowerblue">最大限制</span>
        <div class="block">
          <el-slider
              :min="1"
              :max="10"
              v-model="maxEphemeralStorage"
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
    maxEphemeralStorage:{
      get(){
        return this.$store.state.maxEphemeralStorage
      },
      set(value){
        this.$store.commit('ChangeMaxEphemeralStorage',value)
      }
    },
    minEphemeralStorage:{
      get(){
        return this.$store.state.minEphemeralStorage
      },
      set(value){
        this.$store.commit('ChangeMinEphemeralStorage',value)
      }
    },
  },
  methods:{
    reset(){
      this.$store.commit("ChangeMaxEphemeralStorage",0);
      this.$store.commit("ChangeMinEphemeralStorage",0);
    },
  }
}
</script>

<style scoped>

</style>