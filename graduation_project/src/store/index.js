import Vue from 'vue'
import Vuex from 'vuex'
import user from "@/store/modules/user";	// 引入Vuex
// import instance from "@/store/modules/instance";
import {instanceList} from "@/api/instanceList";
import {getPodList} from "@/api/getPodList";
import {getDeploymentList} from "@/api/getDeploymentList";
import {getServiceList} from "@/api/getServiceList";
import {getNamespaceList} from "@/api/getNamespaceList"
import {createDeployment} from "@/api/createDeployment";
import {createService} from "@/api/createService";
import {deleteDeployment} from "@/api/deleteDeployment";
import {deleteService} from "@/api/deleteService";
import {getRemainingInstance} from "@/api/getRemainingInstance";
import {instanceApplication} from "@/api/instanceApplication";
import {instanceFree} from "@/api/instanceFree";
import {getUserNum} from "@/api/getUserNum";
import {getNamespacedPodList} from "@/api/getNamespacedPodList";
import {getNamespacedService} from "@/api/getNamespacedService";
import {podApplication} from "@/api/podApplication";
import {deleteNamespacedPod} from "@/api/deleteNamespacedPod";
import {getUserRecord} from "@/api/getUserRecord";
import {getUserRemain} from "@/api/getUserRemain";
import {recharge} from "@/api/recharge";
import {getBillList} from "@/api/getBillList";
import {getBillNum} from "@/api/getBillNum";
Vue.use(Vuex)	// 应用Vuex插件
// 准备state——用于存储数据


const state = {
    instanceId:'',
    // instanceNum:0,
    instanceIdForFree:'',
    storageNum:0,
    diskCapacity:0,
    duration:1,
    remaining:0,
    userNum:'',
    nowNodePort:'',
    minCPU:10,
    maxCPU:500,
    minMemory:100,
    maxMemory:1024,
    minEphemeralStorage:1,
    maxEphemeralStorage:2,
    podName:'',
    userRemain:'',
    billNum:'',
    instanceList:[{
        id:'',
        name:'',
        ip:'',
        cpu:'',
        memory:'',
        running:''
    }],
    nowInstance: 'master1',
    podList:[],
    deploymentList:[],
    serviceList:[],
    namespaceList:[],
    remainingInstance:[],
    namespacedPodList:[],
    namespacedServiceList:[],
    instanceInfoList:[],
    userRecordList:[],
    billList:[]
}
// 准备actions——用于响应组件中的动作
const actions = {
    InstanceList({commit},username){
        return new Promise((resolve, reject) => {
            instanceList(username).then(response =>{
                resolve(response)
                commit('ChangeInstanceList',response) //提交一个 mutation，通知状态改变
            }).catch(error =>{
                reject(error)
            })
        })
    },
    getPodList({commit},instanceName){
        return new Promise((resolve, reject) => {
            getPodList(instanceName).then(response =>{
                resolve(response)
                commit('ChangePodList',response) //提交一个 mutation，通知状态改变
            }).catch(error =>{
                reject(error)
            })
        })
    },
    getNamespacedPodList({commit},namespaceInfo){
        const {instanceName,username} = namespaceInfo
        return new Promise((resolve, reject) => {
            getNamespacedPodList(instanceName,username).then(response =>{
                resolve(response)
                commit('ChangeNamespacedPodList',response) //提交一个 mutation，通知状态改变
            }).catch(error =>{
                reject(error)
            })
        })
    },
    getDeploymentList({commit},instanceName){
        return new Promise((resolve, reject) => {
            getDeploymentList(instanceName).then(response =>{
                resolve(response)
                commit('ChangeDeploymentList',response) //提交一个 mutation，通知状态改变
            }).catch(error =>{
                reject(error)
            })
        })
    },
    getServiceList({commit},instanceName){
        return new Promise((resolve, reject) => {
            getServiceList(instanceName).then(response =>{
                resolve(response)
                commit('ChangeServiceList',response) //提交一个 mutation，通知状态改变
            }).catch(error =>{
                reject(error)
            })
        })
    },
    getNamespaceList({commit},instanceName){
        return new Promise((resolve, reject) => {
            getNamespaceList(instanceName).then(response =>{
                resolve(response)
                commit('ChangeNamespaceList',response) //提交一个 mutation，通知状态改变
            }).catch(error =>{
                reject(error)
            })
        })
    },
    //创建具有名称空间的deployment
    createDeployment({ commit },form) {
        //封装一个 Promise
        const { instanceName,deploymentName,namespace,replicas,metadataLabelsApp,image,portName,containerPort } = form
        return new Promise((resolve, reject) => {
            createDeployment(instanceName,deploymentName,namespace,replicas,metadataLabelsApp,image,portName,containerPort).then(response => {
                commit('') //提交一个 mutation，通知状态改变
                resolve(response) //将结果封装进 Promise
            }).catch(error => {
                reject(error)
            })
        })
    },
    //创建具有名称空间的service
    createService({ commit },form) {
        //封装一个 Promise
        const { instanceName,serviceName,namespace,metadataLabelsApp,selectorApp,portsProtocol,portsPort,targetPort } = form
        return new Promise((resolve, reject) => {
            createService(instanceName,serviceName,namespace,metadataLabelsApp,selectorApp,portsProtocol,portsPort,targetPort).then(response => {
                commit('') //提交一个 mutation，通知状态改变
                resolve(response) //将结果封装进 Promise
            }).catch(error => {
                reject(error)
            })
        })
    },
    //删除deployment
    deleteDeployment({commit},deploymentInfo){
        //封装一个 Promise
        const { instanceName,deploymentName,namespace } = deploymentInfo
        return new Promise((resolve, reject) => {
            deleteDeployment(instanceName,deploymentName,namespace).then(response => {
                commit('') //提交一个 mutation，通知状态改变
                resolve(response) //将结果封装进 Promise
            }).catch(error => {
                reject(error)
            })
        })
    },
    //删除Service
    deleteService({commit},serviceInfo){
        //封装一个 Promise
        const { instanceName,serviceName,namespace } = serviceInfo
        return new Promise((resolve, reject) => {
            deleteService(instanceName,serviceName,namespace).then(response => {
                commit('') //提交一个 mutation，通知状态改变
                resolve(response) //将结果封装进 Promise
            }).catch(error => {
                reject(error)
            })
        })
    },
    getRemainingInstance({commit}){
        return new Promise((resolve, reject) => {
            getRemainingInstance().then(response =>{
                resolve(response)
                commit('ChangeRemainingInstance',response) //提交一个 mutation，通知状态改变
            }).catch(error =>{
                reject(error)
            })
        })
    },
    instanceApplication({commit},orderInfo){
        const {instanceId,username, diskCapacity,storageNum,duration} = orderInfo
        return new Promise((resolve, reject) => {
            instanceApplication(instanceId,username,diskCapacity,storageNum,duration).then(response =>{
                resolve(response)
                commit('') //提交一个 mutation，通知状态改变
            }).catch(error =>{
                reject(error)
            })
        })
    },
    instanceFree({commit},instanceName){
        return new Promise((resolve, reject) => {
            instanceFree(instanceName).then(response =>{
                resolve(response)
                commit('') //提交一个 mutation，通知状态改变
            }).catch(error =>{
                reject(error)
            })
        })
    },
    getUserNum({commit}){
        return new Promise((resolve, reject) => {
            getUserNum().then(response =>{
                resolve(response)
                commit('ChangeUserName',response) //提交一个 mutation，通知状态改变
            }).catch(error =>{
                reject(error)
            })
        })
    },
    getNamespacedService({commit},serviceInfo){
        const {instanceName,username} = serviceInfo
        return new Promise((resolve, reject) => {
            getNamespacedService(instanceName,username).then(response =>{
                resolve(response)
                commit('ChangeNamespacedServiceList',response) //提交一个 mutation，通知状态改变
            }).catch(error =>{
                reject(error)
            })
        })
    },
    podApplication({commit},applicationInfo){
        const {instanceName,username,podName,minCPU,maxCPU,
            minMemory,maxMemory,minEphemeralStorage,maxEphemeralStorage,price} = applicationInfo
        return new Promise((resolve, reject) => {
            podApplication(instanceName,username,podName,minCPU,maxCPU,
                minMemory,maxMemory,minEphemeralStorage,maxEphemeralStorage,price).then(response => {
                commit('') //提交一个 mutation，通知状态改变
                resolve(response) //将结果封装进 Promise
            }).catch(error => {
                reject(error)
            })
        })
    },
    deleteNamespacedPod({commit},deleteInfo){
        const {instanceName,podName,username} = deleteInfo
        //封装一个 Promise
        return new Promise((resolve, reject) => {
            deleteNamespacedPod(instanceName,podName,username).then(response => {
                commit('') //提交一个 mutation，通知状态改变
                resolve(response) //将结果封装进 Promise
            }).catch(error => {
                reject(error)
            })
        })
    },
    getUserRecord({commit},username){
        return new Promise((resolve, reject) => {
            getUserRecord(username).then(response => {
                commit('ChangeUserRecord',response) //提交一个 mutation，通知状态改变
                resolve(response) //将结果封装进 Promise
            }).catch(error => {
                reject(error)
            })
        })
    },
    getUserRemain({commit},username){
        return new Promise((resolve, reject) => {
            getUserRemain(username).then(response => {
                commit('ChangeUserRemain',response) //提交一个 mutation，通知状态改变
                resolve(response) //将结果封装进 Promise
            }).catch(error => {
                reject(error)
            })
        })
    },
    rechargeUser({commit},rechargeInfo){
        const {username,value} = rechargeInfo
        return new Promise((resolve, reject) => {
            recharge(username,value).then(response => {
                commit('') //提交一个 mutation，通知状态改变
                resolve(response) //将结果封装进 Promise
            }).catch(error => {
                reject(error)
            })
        })
    },
    getBillList({commit},username){
        return new Promise((resolve, reject) => {
            getBillList(username).then(response => {
                commit('ChangeBillList',response) //提交一个 mutation，通知状态改变
                resolve(response) //将结果封装进 Promise
            }).catch(error => {
                reject(error)
            })
        })
    },
    getBillNum({commit},username){
        return new Promise((resolve, reject) => {
            getBillNum(username).then(response => {
                commit('ChangeBillNum',response) //提交一个 mutation，通知状态改变
                resolve(response) //将结果封装进 Promise
            }).catch(error => {
                reject(error)
            })
        })
    }
}
// 准备mutations——用于操作数据（state）
const mutations = {
    ChangeOption(state,value){
        state.instanceId = value
    },
    // ChangeInstanceNum(state,value){
    //     state.instanceNum = value
    // },
    ChangeStorageNum(state,value){
        state.storageNum = value
    },
    ChangeDiskCapacity(state,value){
        state.diskCapacity = value
    },
    ChangeDuration(state,value){
        state.duration = value
    },
    ChangeInstanceList(state,response){
        state.instanceList = response.data
    },
    ChangeNowInstance(state,value){
        state.nowInstance = value
    },
    ChangePodList(state,response){
        state.podList = response.data
    },
    ChangeDeploymentList(state,response){
        state.deploymentList = response.data
    },
    ChangeServiceList(state,response){
        state.serviceList = response.data
    },
    ChangeNamespaceList(state,response){
        state.namespaceList = response.data
    },
    ChangeRemainingInstance(state,response){
        state.remainingInstance = response.data
    },
    ChangeUserName(state,response){
        state.userNum = response.data
    },
    ChangeNamespacedPodList(state,response){
        state.namespacedPodList = response.data
    },
    ChangeNamespacedServiceList(state,response){
        state.namespacedServiceList = response.data
    },
    ChangeNowNodePort(state,value){
        state.nowNodePort = value
    },
    ChangeMinCPU(state,value){
        state.minCPU = value
    },
    ChangeMinMemory(state,value){
        state.minMemory = value
    },
    ChangeMinEphemeralStorage(state,value){
        state.minEphemeralStorage = value
    },
    ChangeMaxCPU(state,value){
        state.maxCPU = value
    },
    ChangeMaxMemory(state,value){
        state.maxMemory = value
    },
    ChangeMaxEphemeralStorage(state,value){
        state.maxEphemeralStorage = value
    },
    ChangePodName(state,value){
        state.podName = value
    },
    ChangeInstanceInfoList(state){
        state.instanceInfoList = []
        for (let i=0;i<state.namespacedPodList.length;i++){
            console.log("向数组中添加元素"+i)
            state.instanceInfoList.unshift({
                name:state.namespacedPodList[i].name,
                status:state.namespacedPodList[i].status,
                ip:state.namespacedPodList[i].ip,
                nodePort:state.namespacedServiceList[i].nodePort
            })
        }
    },
    ChangeUserRecord(state,response){
        state.userRecordList = response.data
    },
    ChangeUserRemain(state,response){
        state.remaining = response.data
    },
    ChangeBillList(state,response){
        state.billList = response.data
    },
    ChangeBillNum(state,response){
        state.billNum = response.data
    }
}


// const store=new Vuex.Store(
//     {modules:{user},plugins:[]},{
//         actions,
//         mutations,
//         state,
// })
// 创建并暴露store
export default new Vuex.Store({
    actions,
    mutations,
    state,
    modules:{
        user
    }
})