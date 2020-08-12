import request from '@/utils/request'

const Api = {
  // get my info
  RoleList: '/role/page',
  Permission: '/role/resources',
  Role: '/role'
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

export function CreateRole (data) {
    return request({
      url: Api.Role,
      method: 'post',
      data: data
    })
  }

export function UpdateById (data) {
    return request({
      url: Api.Role,
      method: 'put',
      data: data
    })
  }
