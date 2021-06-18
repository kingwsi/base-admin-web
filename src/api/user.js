import request from '@/utils/request'
const Api = {
  User: '/api/user',
  UserInfo: '/api/user/info'
}

export function GetUserInfo () {
  return request({
    url: Api.UserInfo,
    method: 'get'
  })
}

export function CreateUser (data) {
  return request({
    url: Api.User,
    method: 'post',
    data: data
  })
}

export function GetUserPage (parameter) {
  return request({
    url: `${Api.User}/page`,
    method: 'get',
    params: parameter
  })
}

export function UpdateUserById (data) {
  return request({
    url: Api.User,
    method: 'put',
    data: data
  })
}

export function DeleteUserById (id) {
  return request({
    url: Api.User,
    method: 'get',
    params: { 'id': id }
  })
}

export function UpdateStatusById (id) {
  return request({
    url: Api.User,
    method: 'get',
    params: { 'id': id }
  })
}
