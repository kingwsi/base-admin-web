import axios from 'axios'
import store from '@/store'
import storage from 'store'
import notification from 'ant-design-vue/es/notification'
import { VueAxios } from './axios'
import { ACCESS_TOKEN } from '@/store/mutation-types'

// 创建 axios 实例
const request = axios.create({
  // API 请求的默认前缀
  baseURL: process.env.VUE_APP_API_BASE_URL,
  timeout: 6000 // 请求超时时间
})

// 异常拦截处理器
const errorHandler = (error) => {
  if (error.response) {
    const data = error.response.data
    if (error.response.status === 500) {
      notification.error({
        message: '服务器错误',
        description: data.message
      })
    } else if (error.response.status === 404) {
      notification.error({
        message: '404',
        description: data.message
      })
    } else if ((error.response.status === 403)) {
      notification.error({
        message: 'Forbidden',
        description: data.message
      })
    } else if (error.response.status === 401 && !(data.result && data.result.isLogin)) {
      notification.error({
        message: 'Unauthorized',
        description: 'Authorization verification failed'
      })
      // 从 localstorage 获取 token
      const token = storage.get(ACCESS_TOKEN)
      if (token) {
        store.dispatch('Logout').then(() => {
          setTimeout(() => {
            window.location.reload()
          }, 1500)
        })
      }
    } else {
      notification.error({
        message: '请求错误',
        description: error.response.status
      })
    }
  }
  return Promise.reject(error)
}

// request interceptor
request.interceptors.request.use(config => {
  const token = storage.get(ACCESS_TOKEN)
  // 如果 token 存在
  // 让每个请求携带自定义 token 请根据实际情况自行修改
  if (token) {
    config.headers['Authorization'] = token
  }
  return config
}, errorHandler)

// response interceptor
request.interceptors.response.use((response) => {
  const data = response.data
    // 解析自定义返回参数
    // {'code':200,'data':Object}
    if (data.code && data.code !== 200) {
      notification.error({
        message: '错误',
        description: data.message || 'Error',
        duration: 1
      })
      return Promise.reject(new Error(data.message || 'Error'))
    } else {
      return data
    }
}, errorHandler)

const installer = {
  vm: {},
  install (Vue) {
    Vue.use(VueAxios, request)
  }
}

export default request

export {
  installer as VueAxios,
  request as axios
}
