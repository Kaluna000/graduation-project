<template>
<div>
  <el-card>
    <span style="font-weight: bold">账单概览</span>
    <el-table
        :data="billList"
        height="250"
        border
        style="width: 100%;margin-top: 10px"
        :default-sort = "{prop: 'applicationDate'}">
      <el-table-column
          prop="time"
          label="日期时间"
          sortable
          width="180">
      </el-table-column>
      <el-table-column
          prop="username"
          label="用户"
          width="180">
      </el-table-column>
      <el-table-column
          prop="cost"
          label="金额">
      </el-table-column>
      <el-table-column
          prop="message"
          label="详情">
      </el-table-column>
    </el-table>
  </el-card>
</div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "Bill",
  data(){
    return{
    }
  },
  computed:{
    ...mapState({
      billList: state => state.billList.reverse()
    })
  },
  mounted() {
    this.$store.dispatch('getBillList',this.$store.state.user.username)
  }
}
</script>

<style scoped>

</style>