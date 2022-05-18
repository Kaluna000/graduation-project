import Vue from 'vue';
//引入Element-ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';
import VueRouter from 'vue-router';
//引入路由器
import router from './router'
//引入store
import store from './store'
//关闭Vue生产提示
Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(ElementUI);
//创建vm对象
new Vue({
  el:'#app',
  router,
  render: h => h(App),
  store,
  //创建全局事件总线
  beforeCreate() {
    Vue.prototype.$bus = this
  }
});
