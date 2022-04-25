<template>
  <div>
    <el-container>
      <el-header  style="margin-left: 100px;margin-top: 50px">
        <span style="font-size: 30px;margin-left: 20px">您好，{{$store.state.user.username}}</span><Br/>
        <span v-if="$store.state.user.username !== 'root'" style="font-size: 12px;margin-left: 20px;color: #606266">用户</span>
        <span v-if="$store.state.user.username === 'root'" style="font-size: 12px;margin-left: 20px;color: #606266">管理员</span>
      </el-header>
      <el-main style="margin-left: 20%">
        <span style="color: dodgerblue">最近使用记录</span>
        <div>
          <el-table
              :data="userRecordList"
              style="width: 850px"
              height="250">
            <el-table-column
                prop="username"
                label="用户"
                width="140">
            </el-table-column>
            <el-table-column
                prop="time"
                label="时间"
                width="200">
            </el-table-column>
            <el-table-column
                prop="message"
                label="详情"
                width="500">
            </el-table-column>
          </el-table>
        </div>
      </el-main>

    </el-container>


  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "UserRecordPage",
  computed:{
    ...mapState({
      userRecordList:state => state.userRecordList.reverse()
    })
  },
  mounted() {
    this.$store.dispatch('getUserRecord',this.$store.state.user.username)
  }
}
</script>

<style scoped>

</style>