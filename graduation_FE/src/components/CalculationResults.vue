 <template>
  <div>
    <el-row type="flex" justify="start">
      <el-col>
        <el-card shadow="hover" style="background-color: #99a9bf;height: 145px">
<!--          <el-row type="flex" justify="start">-->
<!--            <span>购买实例数量/台</span>-->
<!--            <el-input-number style="margin-left: 2%" v-model="instanceNum" @change="handleChange1" :min="1" :max="10" label="实例数量"></el-input-number>-->
<!--          </el-row>-->
          <el-row style="margin-top: 5%">
            <span>购买时长/月</span>
            <el-input-number style="margin-left: 6.5%" v-model="duration" @change="handleChange2" :min="1" :max="60" label="购买时长"></el-input-number>
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
  name: "CalculationResults",
  data(){
    return {
      loading:false,
      applicationInfo:{
        instanceName:'',
        username:'',
        podName:'',
        minCPU:'',
        maxCPU:'',
        minMemory:'',
        maxMemory:'',
        minEphemeralStorage:'',
        maxEphemeralStorage:'',
        price:'',
        duration:''
      }
    }
  },
  computed:{
    // instanceNum:{
    //   get(){
    //     return this.$store.state.instanceNum
    //   },
    //   set(value){
    //     this.$store.commit('ChangeInstanceNum',value)
    //   }
    // },
    duration:{
      get() {
        return this.$store.state.duration;
      },
      set(value) {
        this.$store.commit('ChangeDuration',value)
      }
    },
    totalAmount(){
      if (this.$store.state.username === 'root'){
        return (100+0.5*this.$store.state.storageNum*this.$store.state.diskCapacity)
            *this.$store.state.duration
      }else {
        return (100+this.$store.state.minCPU+this.$store.state.maxCPU+this.$store.state.minMemory+
            this.$store.state.maxMemory+this.$store.state.minEphemeralStorage+this.$store.state.maxEphemeralStorage
        )*this.$store.state.duration
      }

    }
  },
  methods:{
    // handleChange1(value) {
    //   this.$store.commit('ChangeInstanceNum',value)
    //   console.log("改变instanceNum："+this.$store.state.instanceNum)
    // },
    handleChange2(value){
      this.$store.commit('ChangeDuration',value)
      console.log("改变duration："+this.$store.state.duration)
    },
    confirmOrder(){
      this.loading = true
      if(this.$store.state.username === 'root'){
        this.$store
            .dispatch("instanceApplication", {
              instanceId:this.$store.state.instanceId,
              username:this.$store.state.user.username,
              diskCapacity:this.$store.state.diskCapacity,
              storageNum:this.$store.state.storageNum,
              duration:this.$store.state.duration
            })//读取loginForm里面的username和password
            .then(response => {//声明response变量，类型是json
              this.loading = false;
              let code = response.data.code;//后端传给前端的值为data，code，message，传值给code
              if (code === 200) {
                alert("订单提交，订购"+this.$store.state.instanceId+"型服务器，"+
                    this.$store.state.storageNum+"台"+this.$store.state.diskCapacity+"GiB云盘，总金额为: "+this.totalAmount+" 元," +
                    "时长 "+this.$store.state.duration+" 个月")
                this.$router.back()
              } //this.$router.push根据路由将query里面的信息push给/success
              else {
                alert("实例绑定失败！！！："+response.data.message)
              }
            })
      }else {
            this.applicationInfo.instanceName = this.$store.state.nowInstance
            this.applicationInfo.username = localStorage.getItem('Username')
            this.applicationInfo.podName = this.$store.state.podName,
            this.applicationInfo.minCPU = this.$store.state.minCPU,
            this.applicationInfo.minMemory = this.$store.state.minMemory,
            this.applicationInfo.minEphemeralStorage = this.$store.state.minEphemeralStorage,
            this.applicationInfo.maxCPU = this.$store.state.maxCPU,
            this.applicationInfo.maxMemory = this.$store.state.maxMemory,
            this.applicationInfo.maxEphemeralStorage = this.$store.state.maxEphemeralStorage,
            this.applicationInfo.price = this.totalAmount;
            this.applicationInfo.duration = this.$store.state.duration
        this.$store
            .dispatch("podApplication", this.applicationInfo)//读取loginForm里面的username和password
            .then(response => {//声明response变量，类型是json
              this.loading = false;
              let code = response.data.code;//后端传给前端的值为data，code，message，传值给code
              if (code === 200) {
                alert("订单提交，"+this.applicationInfo.podName
                    )
                //充值成功，返回上一页
                this.$router.back()
              } //this.$router.push根据路由将query里面的信息push给/success
              else {
                alert("实例绑定失败！！！："+response.data.message)
              }
            })
      }
    }
  }
}
</script>

<style scoped>

</style>