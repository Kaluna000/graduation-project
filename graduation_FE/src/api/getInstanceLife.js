import request from '@/utils/request' //引入封装好的 axios 请求

export function getInstanceLife(username){
    return request({ //使用封装好的 axios 进行网络请求
        url: '/getInstanceLife',
        method: 'get',
        params:{
            username
        }
    })
}