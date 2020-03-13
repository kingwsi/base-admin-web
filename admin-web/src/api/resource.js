import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/vue-element-admin/article/list',
    method: 'get',
    params: query
  })
}

export function getRouteTree() {
  return request({
    url: '/api/resources/tree/routes',
    method: 'get'
  })
}

export function getRoutes() {
  return request({
    url: '/api/resources/routes',
    method: 'get'
  })
}
