<template>
  <div style="width: 70%;margin-left: 30%">
    <el-row type="flex" justify="center">

      <el-col><el-button type="primary" @click="freeHandler" :loading="loading">释放设置</el-button></el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "InstanceOperation",
  data() {
    return {
      loading: false
    }
  },

  methods: {
    freeHandler() {
      this.loading = true;
      //调用store下的Login方法
      this.$store
          .dispatch("instanceFree", this.$store.state.nowInstance)
          .then(response => {
            this.loading = false
            let code = response.data.code
            if (code === 200){
              alert("释放成功！")
              this.$store.dispatch('InstanceList',localStorage.getItem('Username'))
            }else {
              alert("释放失败:"+response.data.message)
            }
          })
    }
  }
}
</script>

<style scoped>

</style>