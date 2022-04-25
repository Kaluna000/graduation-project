<template>
<div>
  <el-result icon="success" title="成功提示" subTitle="请根据提示进行操作">
    <template slot="extra">
      <el-button type="primary" size="medium" @click="goIndex" >进入</el-button>
    </template>
  </el-result>
</div>
</template>

<script>
export default {
  name: "LoginSuccess",
  data(){
    return{
      msg:this.$route.query.data//login.vue里面根据路由route传过来的query里面的data
    };
  },
  methods: {
    goIndex(){
      localStorage.setItem('Username',this.msg)
      this.$router.push({
        path:'/index'
      })
    }
  }
}
</script>

<style scoped>

</style>