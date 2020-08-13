import request from '@/utils/request'

const userApi = {
  // get my info
  UserInfo: '/users/info'
}

export function getInfo () {
    return request({
      url: userApi.UserInfo,
      method: 'get'
    })
  }
