<template>
  <div>
    <el-row type="flex" justify="start">
      <el-col>
        <el-card shadow="hover" style="background-color: #99a9bf;height: 145px">
          <el-row style="margin-top: 5%">
            <span>续费时长/月</span>
            <el-input-number style="margin-left: 6.5%" v-model="duration" @change="handleChange" :min="1" :max="60" label="续费时长"></el-input-number>
          </el-row>
        </el-card>
      </el-col>
      <el-col>
        <el-card shadow="hover" style="background-color: #99a9bf">
          <el-row type="flex" justify="end" style="margin-right: 5%">
            <span>
              配置费用：<span style="color: firebrick">
            ￥ {{totalAmount}}
            </span>
            </span>
          </el-row>
          <el-row  type="flex" justify="end" style="margin-right: 5%;margin-top: 45px">
            <el-button @click="confirmOrder" type="danger" :loading="loading">确认订单</el-button>
          </el-row>
        </el-card>
      </el-col>
    </el-row>


  </div>
</template>

<script>
export default {
  name: "Renew",
  data() {
    return {
      loading: false,
      renewInfo: {
        username: '',
        podName: '',
        price: '',
        duration: ''
      }
    }
  },
  computed: {
    // instanceNum:{
    //   get(){
    //     return this.$store.state.instanceNum
    //   },
    //   set(value){
    //     this.$store.commit('ChangeInstanceNum',value)
    //   }
    // },
    duration: {
      get() {
        return this.$store.state.duration;
      },
      set(value) {
        this.$store.commit('ChangeDuration', value)
      }
    },
    totalAmount() {
      return 100 * this.$store.state.duration
    },
  },
    methods: {
      handleChange(value) {
        this.$store.commit('ChangeDuration', value)
      },
      confirmOrder() {
        this.loading = true
        this.renewInfo.username = this.$store.state.user.username
        this.renewInfo.podName = this.$store.state.renewPodName
        this.renewInfo.price = this.totalAmount
        this.renewInfo.duration = this.$store.state.duration
        this.$store
            .dispatch("renew", this.renewInfo)//读取loginForm里面的username和password
            .then(response => {//声明response变量，类型是json
              this.loading = false;
              let code = response.data.code;//后端传给前端的值为data，code，message，传值给code
              if (code === 200) {
                alert("订单提交，" + this.renewInfo.podName
                )
                //充值成功，通过事件总线关闭抽屉
                this.$bus.$emit('closeRenew')
              } //this.$router.push根据路由将query里面的信息push给/success
              else {
                alert("续费失败！！！：" + response.data.message)
              }
            })
      }
    }
}
</script>

<style scoped>

</style>