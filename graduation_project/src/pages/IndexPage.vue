<template>
  <div>
    <Header></Header>
    <!--    <MainPage></MainPage>-->
    <router-view></router-view>

  </div>
</template>

<script>
import Header from '@/components/Header'


export default {
  name: 'IndexPage',
  components: {
    // MainPage,
    Header,
  },
  // data(){
  //   return{
  //     isShowingLeft: false
  //   }
  // },
  methods:{
    showOverview(){
      this.$router.push({
        path:'/index/overview'
      })
    },
    showPodsPage(){
      this.$router.push({
        path:'/index/podsPage'
      })
    },
    showDeploymentsPage(){
      this.$router.push({
        path:'/index/deploymentsPage'
      })
    },
    showInstancePage(){
      this.$router.push({
        path:'/index/instancePage'
      })
    },
    showApplicationPage(){
      this.$router.push({
        path:'/index/applicationPage'
      })
    },
    showServicePage(){
      this.$router.push({
        path:'/index/servicePage'
      })
    },
    showAccountPage(){
      this.$router.push({
        path:'/index/accountPage'
      })
    },
    showNamespacedServicePage(){
      this.$router.push({
        path:'/index/namespacedServicePage'
      })
    },
    showPodApplicationPage(){
      this.$router.push({
        path:'/index/podApplicationPage'
      })
    },
  },
  //向全局事件总线注册事件
  mounted() {
    this.$bus.$on('showOverview',this.showOverview)
    this.$bus.$on('showPodsPage',this.showPodsPage)
    this.$bus.$on('showDeploymentsPage',this.showDeploymentsPage)
    this.$bus.$on('showServicePage',this.showServicePage)
    this.$bus.$on('showInstancePage',this.showInstancePage)
    this.$bus.$on('showApplicationPage',this.showApplicationPage)
    this.$bus.$on('showAccountPage',this.showAccountPage)
    this.$bus.$on('showNamespacedServicePage',this.showNamespacedServicePage)
    this.$bus.$on('showPodApplicationPage',this.showPodApplicationPage)
  },
  beforeDestroy() {
    this.$bus.$off('showOverview')
    this.$bus.$off('showPodsPage')
    this.$bus.$off('showDeploymentsPage')
    this.$bus.$off('showServicePage')
    this.$bus.$off('showInstancePage')
    this.$bus.$off('showApplicationPage')
    this.$bus.$off('showAccountPage')
    this.$bus.$off('showNamespacedServicePage')
    this.$bus.$off('showPodApplicationPage')
  }
}
</script>

<style>


</style>