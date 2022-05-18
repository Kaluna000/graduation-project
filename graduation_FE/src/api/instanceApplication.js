import request from '@/utils/request' //引入封装好的 axios 请求

export function instanceApplication(instanceId,username,diskCapacity,storageNum,duration){
    return request({ //使用封装好的 axios 进行网络请求
        url: '/instanceApplication',
        method: 'post',
        data:{
            instanceId,
            username,
            diskCapacity,
            storageNum,
            duration
        }
    })
}