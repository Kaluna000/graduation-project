<template>
  <div>
    <el-card shadow="hover" style="height: 110px">
      <div>
        <span style="font-weight: bold">待办账单</span>
        <el-row style="margin-top: 20px" type="flex" justify="start" :gutter="20">
          <el-col>
            <span>总账单：<template>
              {{ billNum }}
            </template></span>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "TodoCard",
  computed:{
    ...mapState({
      billNum:state => state.billNum
    })
  },
  mounted() {
    this.$store.dispatch('getBillNum',this.$store.state.user.username)
  }
}
</script>

<style scoped>

</style>