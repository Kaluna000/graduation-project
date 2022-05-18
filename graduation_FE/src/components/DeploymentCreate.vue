<template>
<div>
  <el-form ref="form" :model="form" label-width="80px">
    <el-form-item label="名称" style="width: 70%">
      <el-input v-model="form.deploymentName"></el-input>
    </el-form-item>
    <el-form-item label="名称空间">
      <el-select v-model="form.namespace" placeholder="请选择名称空间">
        <el-option
            v-for="namespace in namespaceList"
            :key="namespace.index"
            :label="namespace"
            :value="namespace">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="副本数量">
      <el-input-number v-model="form.replicas" :min="1" :max="10" label="副本数量"></el-input-number>
    </el-form-item>
    <el-form-item label="LabelsApp" style="width: 70%">
      <el-input v-model="form.metadataLabelsApp"></el-input>
    </el-form-item>
    <el-form-item label="镜像" style="width: 70%">
      <el-input v-model="form.image"></el-input>
    </el-form-item>
    <el-form-item label="PortName" style="width: 70%">
      <el-input v-model="form.portName"></el-input>
    </el-form-item>
    <el-form-item label="容器端口" style="width: 70%">
      <el-input v-model="form.containerPort"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary"
                 :loading="loading"
                 @click.native.prevent="onSubmit"
      >立即创建</el-button>
    </el-form-item>
  </el-form>
</div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "DeploymentCreate",
  props:['drawerRef'],
  data() {
    return {
      loading: false,
      form: {
        instanceName:this.$store.state.nowInstance,
        deploymentName: '',
        namespace: '',
        replicas: '',
        metadataLabelsApp: '',
        image:'',
        portName: '',
        containerPort:''
      }
    }
  },
  computed:{
    ...mapState({
      namespaceList: state => state.namespaceList
    })
  },
  methods: {
    onSubmit() {
      this.$refs.form.validate(valid => {

        if (valid) {
          this.loading = true;
          this.$store
              .dispatch("createDeployment", this.form)
              .then(response => {//声明response变量，类型是json
                this.loading = false;
                let code = response.data.code;//后端传给前端的值为data，code，message，传值给code
                if (code === 200) {
                  alert("创建成功！！")
                  this.drawerRef.hide()
                  this.$store.dispatch('getDeploymentList',this.$store.state.nowInstance)
                }
                else {
                  alert("创建失败："+response.data.message)
                }
              })
              .catch(() => {
                this.loading = false;
              });
        } else {
          // eslint-disable-next-line no-console
          console.log("参数验证不合法！");
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>

</style>