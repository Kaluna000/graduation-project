import request from '@/utils/request'


export function getBillList(username){
    return request({ //使用封装好的 axios 进行网络请求
        url: '/getBill',
        method: 'get',
        params:{
            username:username
        }
    })
}