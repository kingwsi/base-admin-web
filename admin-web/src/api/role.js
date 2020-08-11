import request from '@/utils/request'

const Api = {
  // get my info
  RoleList: '/role/page',
  Permission: '/role/resources'
}

export function Page (parameter) {
    return request({
      url: Api.RoleList,
      method: 'get',
      params: parameter
    })
  }

  export function GetRoleById (id) {
    return request({
      url: Api.Permission,
      method: 'get',
      params: { 'id': id }
    })
  }
