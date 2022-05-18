<template>
  <div>
<!--    <el-card shadow="always" style="width: 70%;margin-left: 30%;transform: translateY(150%)">-->
<!--      <el-row type="flex" justify="center" :gutter="20">-->

<!--      </el-row>-->
<!--    </el-card>-->
    <el-card class="box-card" shadow="always" style="width: 70%;margin-left: 30%;transform: translateY(100%)">
      <el-row type="flex" justify="start" :gutter="20" style="transform: translateY(-20%)">
        <el-button type="primary" @click="goApplicationPage">注册实例</el-button>
      </el-row>
      <el-row type="flex" justify="center" :gutter="20" style="margin-left: 3px">
        <el-col :span="6">实例ID</el-col>
        <el-col :span="6">实例名称</el-col>
        <el-col :span="6">实例ip</el-col>
        <el-col :span="6">配置</el-col>
        <el-col :span="6">操作</el-col>
      </el-row>
    </el-card>
    <div v-for="ins in instanceList" :key="ins.id">
      <div style="width: 70%;margin-left: 30%;transform: translateY(100%)">
        <InstanceCard  :instance="ins"></InstanceCard>
      </div>
    </div>
    <div style="transform: translateY(210%)">
      <InstanceOperation></InstanceOperation>
    </div>
  </div>
</template>

<script>
import InstanceCard from "@/components/InstanceCard";
import InstanceOperation from "@/components/InstanceOperation";
import {mapState} from "vuex";
export default {
  name: "InstancePage",
  components: {InstanceOperation, InstanceCard},
  computed:{
    ...mapState({
      instanceList: state => state.instanceList
    })
  },
  methods:{
    goApplicationPage(){
      this.$bus.$emit('showApplicationPage')
    }
  },
  mounted() {
    this.$store.dispatch('InstanceList',this.$store.state.user.username)
  }
}
</script>

<style scoped>

</style>