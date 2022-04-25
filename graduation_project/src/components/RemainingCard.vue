<template>
<div>
  <el-card shadow="hover" style="height: 110px">
    <div>
      <span style="font-weight: bold">我的余额</span>
      <el-row style="margin-top: 10px" type="flex" justify="start" :gutter="20">
        <el-col>
          <span style="color: coral;font-size: 20px">￥ {{remaining}}</span>
          <el-button
              style="margin-left: 40px" type="primary" icon="el-icon-edit" circle @click="goRecharge">充值</el-button>
        </el-col>
        <el-col>
        </el-col>
      </el-row>
    </div>
  </el-card>
</div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "RemainingCard",
  methods:{
    goRecharge(){
      this.$bus.$emit('showRecharge')
    }
  },
  computed:{
    ...mapState({
      remaining: state => state.remaining
    })
  },
  mounted() {
    this.$store.dispatch('getUserRemain',this.$store.state.user.username)
  }
}
</script>

<style scoped>

</style>