<template>
  <div>
    <el-menu
        :default-active="defaultActive"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
        >
<!--        <el-menu-item @click="consoleClicked">控制台</el-menu-item>-->
        <el-submenu index="1">
          <template slot="title">
            控制台
          </template>
          <el-menu-item index="1-1" @click="goMainPage">
            <template slot="title">
              <i class="el-icon-view"></i>
              <span>概览</span>
            </template>
          </el-menu-item>
          <el-submenu v-if="username === 'root'" index="1-2">
            <template slot="title">
              <i class="el-icon-suitcase"></i>
              <span>应用负载</span>
            </template>
            <el-menu-item index="1-2-1" @click="goPodsPage">Pods</el-menu-item>
            <el-menu-item index="1-2-2" @click="goDeploymentsPage">Deployments</el-menu-item>
            <el-menu-item index="1-2-3" @click="goServicePage">Service</el-menu-item>
          </el-submenu>
          <el-menu-item v-if="username === 'root'" index="1-5" @click="goInstancePage">
            <i class="el-icon-s-platform"></i>
            <span slot="title">实例管理</span>
          </el-menu-item>
          <el-menu-item v-if="username !=='root'" index="1-6" @click="goNamespacedServicePage">
            <i class="el-icon-s-platform"></i>
            <span slot="title">实例管理</span>
          </el-menu-item>
          <el-menu-item v-if="username !== 'root'" index="1-7" @click="goApplicationPage">
            <i class="el-icon-s-platform"></i>
            <span slot="title">实例申请</span>
          </el-menu-item>
        </el-submenu>
        <el-menu-item v-if="username !== 'root'" index="2" @click="goAccountPage">账户管理</el-menu-item>
        <el-menu-item index="4" @click="goUserRecordPage">使用记录</el-menu-item>
<!--        <div  class="block"><el-avatar icon="el-icon-user-solid" style="transform: translateY(30%)"></el-avatar></div>-->
      <el-menu-item>
        <el-select v-if="username === 'root'" v-model="value" placeholder="当前选择实例">
          <el-option
              v-for="ins in instanceList"
              :key="ins.id"
              :label="ins.name"
              :value="ins.name">
          </el-option>
        </el-select>
      </el-menu-item>
      <el-menu-item v-if="username === 'root'">
        <el-button type="info" @click="goTernimal">进入终端</el-button>
      </el-menu-item>
      <el-submenu v-if="username === 'root'"  index="5" style="transform: translateX(720px)">
        <template slot="title">
          <el-avatar>user</el-avatar>
        </template>
        <el-menu-item @click="logout">登出</el-menu-item>
      </el-submenu>
      <el-submenu v-if="username !== 'root'"  index="5" style="transform: translateX(985px)">
        <template slot="title">
          <el-avatar>user</el-avatar>
        </template>
        <el-menu-item @click="logout">登出</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>

import {mapState} from "vuex";

export default {
  name: 'Header',
  data() {
    return {
      activeIndex: '1',
      defaultActive: '1-1',
    };
  },
  computed:{
    ...mapState({
      instanceList: state => state.instanceList,
      username: state => state.user.username,
    }),
    value:{
      get(){
        if (this.$store.state.nowInstance === null || this.$store.state.nowInstance === ''){
          return localStorage.getItem('nowInstance')
        }else {
          return this.$store.state.nowInstance
        }
      },
      set(val){
        this.$store.commit('ChangeNowInstance',val)
        localStorage.setItem('nowInstance',val)
      }
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    goMainPage(){
      this.$bus.$emit('showOverview')
    },
    goPodsPage(){
      if (this.$store.state.nowInstance === null || this.$store.state.nowInstance === ''){
        alert("请先选择实例！！")
      }else {
        this.$bus.$emit('showPodsPage')
      }
    },
    goDeploymentsPage(){
      if (this.$store.state.nowInstance === null || this.$store.state.nowInstance === ''){
        alert("请先选择实例！！")
      }else {
        this.$bus.$emit('showDeploymentsPage')
      }
    },
    goInstancePage(){
      if (localStorage.getItem('Username') === 'root'){
        this.$bus.$emit('showInstancePage')
      }else this.$bus.$emit('showPodsPage')

    },
    goServicePage(){
      if (this.$store.state.nowInstance === null || this.$store.state.nowInstance === ''){
        alert("请先选择实例！！")
      }else {
        this.$bus.$emit('showServicePage')
      }
    },
    goAccountPage(){
      this.$bus.$emit('showAccountPage')
    },
    logout(){
      this.$router.replace({
        path:'/'
      })
    },
    goTernimal(){
      this.$router.push({
        path:'/webssh'
      })
    },
    goApplicationPage(){
      this.$bus.$emit('showPodApplicationPage')
    },
    goNamespacedServicePage(){
      this.$bus.$emit('showNamespacedServicePage')
    },
    goUserRecordPage(){
      this.$router.push({
        path:'/index/userRecordPage'
      })
    }
  },
  mounted() {
    this.$store.dispatch('InstanceList',this.$store.state.user.username)
  }
}
</script>

<style>

</style>