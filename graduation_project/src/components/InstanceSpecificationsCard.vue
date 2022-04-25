<template>
  <div>
   <el-row type="flex" justify="start">
     <span style="transform: translateY(45%)">
       实例规格
     </span>
     <el-button @click="clearFilter" type="primary">重置</el-button>
     <el-table
         ref="filterTable"
         :data="remainingInstance"
         style="width: 100%"
         :default-sort = "{prop: 'CPU'}">
       <el-table-column>
         <el-radio slot-scope="scope" v-model="option" :label="scope.row.id"></el-radio>
       </el-table-column>
       <el-table-column
           prop="name"
           label="名称"
           width="180"
           column-key="name"
       >
       </el-table-column>
       <el-table-column
           prop="id"
           label="编号"
           width="180">
       </el-table-column>
       <el-table-column
           prop="cpu"
           label="CPU"
           sortable
           width="180"
           column-key="CPU"
           :filters="[
            {text: '1 vCPU', value: '1 vCPU'},
            {text: '2 vCPU', value: '2 vCPU'},
            {text: '4 vCPU', value: '4 vCPU'},
            {text: '8 vCPU', value: '8 vCPU'},
            {text: '10 vCPU', value: '10 vCPU'},
            {text: '12 vCPU', value: '12 vCPU'},
            {text: '14 vCPU', value: '14 vCPU'},
            ]"
           :filter-method="filterCPU">
       </el-table-column>
       <el-table-column
           prop="memory"
           label="内存"
           sortable
           width="180"
           column-key="memory"
           :filters="[
            {text: '1 GiB', value: '1 GiB'},
            {text: '2 GiB', value: '2 GiB'},
            {text: '4 GiB', value: '4 GiB'},
            {text: '8 GiB', value: '8 GiB'},
            ]"
           :filter-method="filterMemory">
       </el-table-column>
       <el-table-column
           prop="bandwidth"
           label="带宽"
           sortable
           width="180"
           column-key="bandwidth"
       >
         <template slot-scope="scope">
           <span>
             {{scope.row.bandwidth}} Gbps
           </span>
         </template>
       </el-table-column>
       <el-table-column
       prop="price"
       label="参考价格"
       sortable
       width="180"
       column-key="price"
       >
         <template slot-scope="scope">
           <span>
             {{scope.row.price}} 元/月
           </span>
         </template>
       </el-table-column>
     </el-table>
   </el-row>
<!--    <el-row style="margin-top: 5%">-->
<!--      <span>购买实例数量</span>-->
<!--      <el-input-number style="margin-left: 2%" v-model="instanceNum" @change="handleChange" :min="1" :max="10" label="实例数量"></el-input-number>-->
<!--      <span style="margin-left: 3%">*开通-->
<!--        <template style="color: aqua">-->
<!--          {{instanceNum}}-->
<!--        </template>-->
<!--        台实例，最多开通10台-->
<!--        </span>-->
<!--    </el-row>-->

  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name:'InstanceSpecificationsCard',
  data() {
    return {

    }
  },
  computed:{
    //使用计算属性set方法实现option与store中的instanceId同步
    option:{
      get(){
        return this.$store.state.instanceId
      },
      set(value){
        this.$store.commit('ChangeOption',value)
        console.log(this.$store.state.instanceId)
      }
      },
      // instanceNum:{
      //   get(){
      //     return this.$store.state.instanceNum
      //   },
      //   set(value){
      //     this.$store.commit('ChangeInstanceNum',value)
      //   }
      // },
      ...mapState({
        remainingInstance:state => state.remainingInstance
      })
  },
  methods: {
    resetDateFilter() {
      this.$refs.filterTable.clearFilter('date');
    },
    clearFilter() {
      this.$refs.filterTable.clearFilter();
    },
    filterCPU(value, row) {
      return row.CPU === value;
    },
    filterMemory(value,row){
      return row.memory === value
    },
    //handleChange方法实现instanceNum与store中的instanceNum同步
    // handleChange(value) {
    //   this.$store.commit('ChangeInstanceNum',value)
    //   console.log(this.$store.state.instanceNum)
    // },
  },
  mounted() {
    this.$store.dispatch('getRemainingInstance')
  }
}
</script>