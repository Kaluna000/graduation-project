import request from '@/utils/request' //引入封装好的 axios 请求

export function deleteDeployment(instanceName,deploymentName,namespace){
    return request({ //使用封装好的 axios 进行网络请求
        url: '/deleteDeployment',
        method: 'post',
        params:{
            instanceName:instanceName
        },
        data:{
            name:deploymentName,
            namespace
        }
    })
}