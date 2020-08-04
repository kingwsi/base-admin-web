import request from '@/utils/request'

const resourceApi = {
  // get my info
  PageInfo: '/resources/page',
  Route: '/resources/routes',
  List: '/resources/list',
  Update: '/resources'
}

export function getRoutes () {
  return request({
    url: resourceApi.Route,
    method: 'get'
  })
}

export function page (parameter) {
  return request({
    url: resourceApi.PageInfo,
    method: 'get',
    params: parameter
  })
}

export function getList (parameter) {
  return request({
    url: resourceApi.List,
    method: 'get',
    params: parameter
  })
}

export function create (parameter) {
  return request({
    url: resourceApi.Update,
    method: 'post',
    data: parameter
  })
}

export function updateById (parameter) {
  return request({
    url: resourceApi.Update,
    method: 'put',
    data: parameter
  })
}

export function remove (parameter) {
  return request({
    url: resourceApi.Update,
    method: 'put',
    data: parameter
  })
}
