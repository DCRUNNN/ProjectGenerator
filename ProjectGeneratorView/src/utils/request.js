import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '../store'
import { getToken } from '@/utils/token'

// 创建axios实例
// https://www.kancloud.cn/yunye/axios/234845
const service = axios.create({
  baseURL: process.env.BASE_API, // api的base_url
  timeout: 50000, // 请求超时时间 5000
  // 所有请求都以Json形式传送
  // 会有预检请求，服务端需要正常通过OPTIONS请求
  // http://www.ruanyifeng.com/blog/2016/04/cors
  headers: {
    'Content-type': 'application/json;charset=UTF-8'
  }
})

// request拦截器
service.interceptors.request.use(config => {
  if (store.getters.token) {
    // 让每个请求携带自定义token 请根据实际情况自行修改
    config.headers['Authorization'] = getToken()
  }
  return config
}, error => {
  // Do something with request error
  console.log(error) // for debug
  Promise.reject(error)
})

// response拦截器
service.interceptors.response.use(
  response => {
    if (response.status === 200 || response.data.returnCode === 200) {
      return response.data;
    } else {
      Message({
        message: response.data.message,
        type: 'error',
        duration: 5 * 1000
      });
      return Promise.reject('error')
    }
  },
  error => {
    // 401:需要认证
    console.log(error)
    if (error.response && error.response.status === 401) {
      MessageBox.confirm('Need login', 'logout', {
        confirmButtonText: 'login',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        store.dispatch('FedLogout').then(() => {
          location.reload()// 为了重新实例化vue-router对象 避免bug
        })
      })
    } else {
      if (error.response) {
        Message({
          message: error.response.data.message,
          type: 'error',
          duration: 5 * 1000
        });
      }else {
        Message({
          message: '服务器可能开小差了...',
          type: 'error',
          duration: 5 * 1000
        });
      }
    }
    return Promise.reject(error)
  }
)

export default service
