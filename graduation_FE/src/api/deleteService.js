import request from '@/utils/request' //引入封装好的 axios 请求

export function deleteService(instanceName,serviceName,namespace){
    return request({ //使用封装好的 axios 进行网络请求
        url: '/deleteService',
        method: 'post',
        params:{
            instanceName:instanceName
        },
        data:{
            name:serviceName,
            namespace
        }
    })
}