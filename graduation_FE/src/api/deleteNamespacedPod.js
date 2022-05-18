import request from '@/utils/request' //引入封装好的 axios 请求

export function deleteNamespacedPod(instanceName,podName,username){
    return request({ //使用封装好的 axios 进行网络请求
        url: '/deleteNamespacedPod',
        method: 'post',
        params:{
            instanceName:instanceName,
            podName:podName,
            username:username
        },

    })
}