import request from '@/utils/request' //引入封装好的 axios 请求

export function renew(username,podName,price,duration) {

    return request({ //使用封装好的 axios 进行网络请求
        url: 'renew',
        method: 'post',
        params:{
            username,
            podName,
            price,
            duration
        }
    })
}