import { login } from '@/api/login'
import {signUp} from "@/api/signUp";

const user = {
    state:{
        username: localStorage.getItem('Username'),
        usertype: localStorage.getItem('Username') === 'root' ? 'admin' : 'user'
    },
    actions: {
        //登录
        //定义Login方法，在组件中使用语法this.$store.dispatch("Login")
        Login({ commit }, userInfo) {
            //const username=userInfo.username.trim()//处理传递过来的数据
            console.log(userInfo)
            const { username, password } = userInfo
            //封装一个 Promise
            return new Promise((resolve, reject) => {
                //使用 login 接口进行网络请求
                login(username, password).then(response => {
                    commit('') //提交一个 mutation，通知状态改变
                    resolve(response) //将结果封装进 Promise
                }).catch(error => {
                    reject(error)
                })
            })
        },
        signUp({commit},userInfo){
            //const username=userInfo.username.trim()//处理传递过来的数据
            console.log(userInfo)
            const { username, password } = userInfo
            //封装一个 Promise
            return new Promise((resolve, reject) => {
                //使用 login 接口进行网络请求
                signUp(username, password).then(response => {
                    commit('') //提交一个 mutation，通知状态改变
                    resolve(response) //将结果封装进 Promise
                }).catch(error => {
                    reject(error)
                })
            })
        },
    },
    mutations:{
        loginUsername(state,username){
            state.username = username
        },
        loginUsertype(state,usertype){
            state.usertype = usertype
        }
    }
}
export default user

