<template>
  <div>
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="名称" style="width: 70%">
        <el-input v-model="form.serviceName"></el-input>
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
      <el-form-item label="LabelsApp" style="width: 70%">
        <el-input v-model="form.metadataLabelsApp"></el-input>
      </el-form-item>
      <el-form-item label="SelectorApp" style="width: 70%">
        <el-input v-model="form.selectorApp"></el-input>
      </el-form-item>
      <el-form-item label="端口协议" style="width: 70%">
        <el-input v-model="form.portsProtocol"></el-input>
      </el-form-item>
      <el-form-item label="端口" style="width: 70%">
        <el-input v-model="form.portsPort"></el-input>
      </el-form-item>
      <el-form-item label="目标端口">
        <el-input v-model="form.targetPort" ></el-input>
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
  data() {
    return {
      loading: false,
      form: {
        instanceName:this.$store.state.nowInstance,
        serviceName: '',
        namespace: '',
        metadataLabelsApp: '',
        selectorApp: '',
        portsProtocol:'',
        portsPort: '',
        targetPort:''
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
              .dispatch("createService", this.form)
              .then(response => {//声明response变量，类型是json
                this.loading = false;
                let code = response.data.code;//后端传给前端的值为data，code，message，传值给code
                if (code === 200) {
                  alert("创建成功！！")
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