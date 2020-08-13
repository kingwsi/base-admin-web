import request from '@/utils/request'
const Api = {
  // get my info
  UserInfo: '/users/info'
}

export function getInfo () {
    return request({
      url: Api.UserInfo,
      method: 'get'
    })
  }
