<template>
  <div>
    <el-result icon="error" title="错误提示" subTitle="请根据提示进行操作">
      <template slot="extra">
        <el-button @click="this.$router.back()" type="primary" size="medium">返回</el-button>
      </template>
    </el-result>
  </div>
</template>

<script>
export default {
  name: "LoginError",
  data(){
    return{
      msg:null
    };
  },
  created(){
    this.msg=this.$route.query.message;//login.vue里面根据路由route传过来的query里面的message
  }

}
</script>

<style scoped>

</style>