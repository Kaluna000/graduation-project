import request from '@/utils/request' //引入封装好的 axios 请求

export function podApplication(instanceName,username,podName,minCPU,maxCPU,
                               minMemory,maxMemory,minEphemeralStorage,maxEphemeralStorage,price){
    return request({ //使用封装好的 axios 进行网络请求
        url: '/podApplication',
        method: 'post',
        params:{
            instanceName:instanceName,
            username:username
        },
        data:{
            podName,
            minCPU,
            maxCPU,
            minMemory,
            maxMemory,
            minEphemeralStorage,
            maxEphemeralStorage,
            price:price
        }
    })
}