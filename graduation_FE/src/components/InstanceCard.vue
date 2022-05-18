<template>
  <div>
    <el-card class="box-card" shadow="hover">
      <el-row type="flex" justify="center" :gutter="20">
        <el-col :span="6">{{instance.id}}</el-col>
        <el-col :span="6">{{instance.name}}</el-col>
        <el-col :span="6">{{instance.ip}}</el-col>
        <el-col :span="6">{{instance.cpu}}</el-col>
        <el-col :span="6">{{instance.memory}}</el-col>

      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "InstanceCard",
  props:['instance']
}
</script>

<style scoped>

</style>