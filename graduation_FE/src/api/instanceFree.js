import request from '@/utils/request' //引入封装好的 axios 请求

export function instanceFree(instanceName){
    return request({ //使用封装好的 axios 进行网络请求
        url: '/instanceFree',
        method: 'post',
        params:{
            instanceName:instanceName
        }
    })
}