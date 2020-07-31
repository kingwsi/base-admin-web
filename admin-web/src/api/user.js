import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/api/auth',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/api/auth/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/api/auth/logout',
    method: 'post'
  })
}

export function getPageInfo(page) {
  return request({
    url: '/api/users/page',
    method: 'get',
    params: page
  })
}

export function update(data) {
  return request({
    url: '/api/users',
    method: 'put',
    data
  })
}

export function create(data) {
  return request({
    url: '/api/users',
    method: 'post',
    data
  })
}
