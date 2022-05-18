import axios from 'axios';
import baseUrl from "@/api/baseUrl";
//import baseUrl from '../api/baseUrl'使用环境变量 + 模式的方式定义基础URL

const service = axios.create({
    baseURL:baseUrl,
    timeout:15000,
})

export default service
