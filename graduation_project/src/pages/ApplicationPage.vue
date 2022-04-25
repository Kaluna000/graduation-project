<template>
  <div>
    <InstanceSpecificationsCard></InstanceSpecificationsCard>
    <el-divider></el-divider>
    <StorageSpecificationCard style="margin-top: 5%"></StorageSpecificationCard>
    <el-divider></el-divider>
    <el-row type="flex" justify="start" style="margin-top: 3%">
      <el-button @click="drawer = true" type="primary" style="margin-left: 16px;">
          计算价格
      </el-button>
<!--      抽屉弹窗显示计算结果-->
      <el-drawer
          title="计算结果"
          :visible.sync="drawer"
          :direction="direction"
          :before-close="handleClose">
        <CalculationResults></CalculationResults>
      </el-drawer>
    </el-row>

  </div>
</template>

<script>
import InstanceSpecificationsCard from "@/components/InstanceSpecificationsCard";
import StorageSpecificationCard from "@/components/StorageSpecificationCard";
import CalculationResults from "@/components/CalculationResults";

export default {
  name: "ApplicationPage",
  components: {CalculationResults, StorageSpecificationCard, InstanceSpecificationsCard},
 data() {
  return {
    drawer: false,
    direction: 'btt',
  };
},
methods: {
  handleClose(done) {
    this.$confirm('确认放弃？')
        .then(() => {
          done();
        })
        .catch(() => {});
  }
}
}
</script>

<style scoped>

</style>