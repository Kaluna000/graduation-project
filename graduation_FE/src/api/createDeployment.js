import request from '@/utils/request' //引入封装好的 axios 请求

export function createDeployment(instanceName,deploymentName,namespace,replicas,metadataLabelsApp,image,portName,containerPort){
    console.log('createDeployment')
    return request({ //使用封装好的 axios 进行网络请求
        url: '/createNamespacedDeployment',
        method: 'post',
        params:{
            instanceName:instanceName
        },
        data:{
            deploymentName,
            namespace,
            replicas,
            metadataLabelsApp,
            image,
            portName,
            containerPort
        }
    })
}