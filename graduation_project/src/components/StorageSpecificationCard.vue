<template>
  <div>
    <el-row type="flex" justify="start">
      <span style="transform: translateY(30%);width: 5%">
        存储规格(GiB)
      </span>
      <el-button type="primary" @click="reset">重置</el-button>
      <el-card class="el-card" shadow="hover" style="width: 90%">
        <div class="block">
          <el-slider
              v-model="diskCapacity"
              show-input>
          </el-slider>
        </div>
      </el-card>
    </el-row>
    <el-row style="margin-top: 5%">
      <span>购买存储盘数量</span>
      <el-input-number style="margin-left: 5%" v-model="storageNum" @change="handleChange" :min="1" :max="10" label="GiB"></el-input-number>
      <span style="margin-left: 3%">*启用
        <template style="color: aqua">
          {{storageNum}}
        </template>
        台云盘，最多启用10台
        </span>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "StorageSpecificationCard",
  data() {
    return {
      storageNum:0
    }
  },
  computed:{
    diskCapacity:{
      get(){
        console.log("从store获取diskCapacity")
        return this.$store.state.diskCapacity
      },
      set(value){
        this.$store.commit('ChangeDiskCapacity',value)
        console.log("改变store中的DiskCapacity:"+this.$store.state.diskCapacity)
      }
    }
  },
  methods:{
    reset(){
      this.$store.commit("ChangeDiskCapacity",0);
    },
    handleChange(value) {
      this.$store.commit('ChangeStorageNum',value)
      console.log(this.$store.state.storageNum)
    }
  }
}
</script>

<style scoped>

</style>