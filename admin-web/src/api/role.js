import request from '@/utils/request'

const Api = {
  // get my info
  RoleList: '/role/page'
}

export function Page (parameter) {
    return request({
      url: Api.RoleList,
      method: 'get',
      params: parameter
    })
  }
