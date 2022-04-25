<template>
<div>
  <div style="margin-left: 45%">
    <el-row type="flex" justify="start">
      <el-col>
        <span style="font-weight: bold">我的余额</span>
        <span style="margin-left: 20%">充值金额</span>
      </el-col>
    </el-row>
    <el-row style="margin-top: 30px" type="flex" justify="start" :gutter="20">
      <el-col>
        <span style="color: coral;font-size: 30px">￥ {{this.$store.state.remaining}}</span>
      </el-col>
        <el-col>
          <el-input v-model="rechargeInfo.value" placeholder="请输入金额" style="width: 60%"></el-input>
        </el-col>
        <el-col>
          <el-button type="danger" @click="confirmRecharge" :loading="loading">确认充值</el-button>
        </el-col>
      <el-col>
      </el-col>
    </el-row>
  </div>
</div>
</template>

<script>
export default {
  name: "Recharge",
  data(){
    return{
      rechargeInfo:{
        username:this.$store.state.user.username,
        value:''
      },
      loading:false
    }
  },
  methods:{
    confirmRecharge(){
      this.loading = true
      if (!isNaN(this.rechargeInfo.value)){
        this.$store
            .dispatch("rechargeUser", this.rechargeInfo)
            .then(response => {//声明response变量，类型是json
              this.loading = false
              let code = response.data.code;//后端传给前端的值为data，code，message，传值给code
              if (code === 200) {
                alert("充值成功！充值金额为 "+this.rechargeInfo.value+" 元")
                this.$store.dispatch('getUserRemain',this.$store.state.user.username)
                this.$store.dispatch('getBillList',this.$store.state.user.username)
                this.$bus.$emit('rechargeComplete')
              }
              else {
                alert("充值失败："+response.data.message)
              }
            })
            .catch(() => {
              this.loading = false;
            });

      }else {
        this.loading = false
        alert('请输入正确数值！')
      }
    }
  }
}
</script>

<style scoped>

</style>