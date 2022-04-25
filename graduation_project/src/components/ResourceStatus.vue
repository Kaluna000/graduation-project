<template>
  <div>
    <el-card class="box-card" shadow="hover" title="资源使用情况">
      <span>资源使用情况</span><Br/>
      <el-row type="flex" justify="end" style="transform: translateY(15%)">
        <el-col>
          <div style="color: #606266">
            <el-progress type="circle" :percentage="0"></el-progress>
              <span>CPU  ??/??</span>
          </div>
        </el-col>
        <el-col>
          <div style="color: #606266">
            <el-progress type="circle" :percentage="0"></el-progress>
            <span>内存  ??/??</span>
          </div>
        </el-col>
        <el-col>
          <div style="color: #606266">
            <el-progress type="circle" :percentage="0"></el-progress>
            <span>本地存储  ??/??</span>
          </div>
        </el-col>
        <el-col>
          <div style="color: #606266">
            <el-progress type="circle" :percentage="0"></el-progress>
            <span>容器组  ??/??</span>
          </div>
        </el-col>
      </el-row>
    </el-card>



  </div>
</template>

<script>
export default {
  name: "ResourceStatus"
}
</script>

<style scoped>

</style>