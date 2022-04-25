import request from '@/utils/request' //引入封装好的 axios 请求

export function createService(instanceName,serviceName,namespace,metadataLabelsApp,selectorApp,portsProtocol,portsPort,targetPort){
    console.log('createDeployment')
    return request({ //使用封装好的 axios 进行网络请求
        url: '/createNamespacedService',
        method: 'post',
        params:{
            instanceName:instanceName
        },
        data:{
            serviceName,
            namespace,
            metadataLabelsApp,
            selectorApp,
            portsProtocol,
            portsPort,
            targetPort
        }
    })
}