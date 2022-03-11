import request from '@/utils/request'

const resourceApi = {
  // get my info
  PageInfo: '/api/resource/page',
  Route: '/api/resource/routes',
  Update: '/api/resource',
  AllList: '/api/resource/list',
  Delete: '/api/resource'
}

export function GetPage (parameter) {
  return request({
    url: resourceApi.PageInfo,
    method: 'get',
    params: parameter
  })
}

export function GetRouteList () {
  return request({
    url: resourceApi.Route,
    method: 'get'
  })
}

export function GetAllResources (param) {
  return request({
    url: resourceApi.AllList,
    method: 'get',
    params: param
  })
}

export function Create (parameter) {
  return request({
    url: resourceApi.Update,
    method: 'post',
    data: parameter
  })
}

export function UpdateById (parameter) {
  return request({
    url: resourceApi.Update,
    method: 'put',
    data: parameter
  })
}

export function DeleteById (id) {
  return request({
    url: `${resourceApi.Delete}/${id}`,
    method: 'delete'
  })
}
