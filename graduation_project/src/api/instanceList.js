import request from '@/utils/request' //引入封装好的 axios 请求

export function instanceList(username){
    return request({ //使用封装好的 axios 进行网络请求
        url: '/instanceList',
        method: 'get',
        params:{
            username:username
        }
    })
}