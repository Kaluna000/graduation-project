<template>
  <div>
    <el-container>
      <el-header  style="margin-left: 100px;margin-top: 50px">
        <AccountHeader></AccountHeader>
      </el-header>
      <el-main style="margin-left: 100px">
        <el-row type="flex" justify="center" :gutter="10">
          <el-col>
            <RemainingCard></RemainingCard>
          </el-col>
          <el-col>
            <TodoCard></TodoCard>
          </el-col>
        </el-row>
        <Bill style="margin-top: 15px"></Bill>
      </el-main>
    </el-container>
    <!--      抽屉弹窗显示充值页面-->
    <el-drawer
        title="充值页面"
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose">
      <Recharge></Recharge>
    </el-drawer>

  </div>
</template>

<script>
import AccountHeader from "@/components/AccountHeader";
import RemainingCard from "@/components/RemainingCard";
import Bill from "@/components/Bill";
import TodoCard from "@/components/TodoCard";
import Recharge from "@/components/Recharge";
export default {
  name: "AccountPage",
  components: {Recharge, TodoCard, Bill, RemainingCard, AccountHeader},
  data(){
    return{
      drawer: false,
      direction: 'btt',
    }
  },
  methods: {
    handleClose(done) {
      this.$confirm('确认放弃？')
          .then(() => {
            done();
          })
          .catch(() => {});
    },
    showRecharge(){
      this.drawer = true
    },
    rechargeComplete(){
      this.drawer=false;
    }
  },
  mounted(){
    this.$bus.$on('showRecharge',this.showRecharge)
    //充值完成时关闭弹窗
    this.$bus.$on('rechargeComplete',this.rechargeComplete)
  },
  beforeDestroy() {
    this.$bus.$off('showRecharge')
    this.$bus.$off('rechargeComplete')
  },

}
</script>

<style scoped>

</style>