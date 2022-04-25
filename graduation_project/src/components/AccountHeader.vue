<template>
<div>
  <div>
    <span style="font-size: 30px;margin-left: 20px">您好，{{$store.state.user.username}}</span><Br/>
    <span v-if="$store.state.user.username !== 'root'" style="font-size: 12px;margin-left: 20px;color: #606266">用户</span>
    <span v-if="$store.state.user.username === 'root'" style="font-size: 12px;margin-left: 20px;color: #606266">管理员</span>
  </div>
</div>
</template>

<script>
export default {
  name: "AccountHeader",
  methods:{
    goBack(){
      this.$router.back()
    }
  }
}
</script>

<style scoped>

</style>