import request from '@/utils/request'

export function getRoutes() {
  return request({
    url: '/api/resources/routes',
    method: 'get'
  })
}

export function getRoles() {
  return request({
    url: '/api/roles',
    method: 'get'
  })
}

export function addRole(data) {
  return request({
    url: '/api/roles',
    method: 'post',
    data
  })
}

export function updateRole(id, data) {
  return request({
    url: `/api/roles/${id}`,
    method: 'put',
    data
  })
}

export function deleteRole(id) {
  return request({
    url: `/api/roles/${id}`,
    method: 'delete'
  })
}
