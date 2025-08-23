import axios from "axios"
import {ElMessage} from "element-plus"

const request = axios.create({
    baseURL:'http://localhost:8080',
    timeout:3000         // 后台接口超时时间
})

// request拦截器
// 请求拦截器，在请求发送前对请求做一些处理
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    return config
}, error => {
    return Promise.reject(error);
});

// 响应拦截器
request.interceptors.response.use(
    response => {      // response：响应对象
        let res = response.data;
        // 兼容服务器返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res;
        }
        return res;
    },
    error => {
        if (error.response.status === 404) {
            ElMessage.error("未找到相关接口");
        } else if (error.response.status === 500) {
            ElMessage.error("系统异常，请查看后端控制台报错")
        } else {
            console.error(error.message);
        }
        return Promise.reject(error);
    }
);
export default request;

/**
 * 具体执行哪些事件后发送特定请求，然后在请求中设置后端访问路径，这样就能实现前后端交互了
 * */
