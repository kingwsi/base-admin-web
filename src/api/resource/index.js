import request from '@/utils/request'

const resourceApi = {
  // get my info
  PageInfo: '/resource/page',
  Route: '/resource/routes',
  Update: '/resource',
  AllList: 'resource/list',
  Delete: 'resource'
}

export function page (parameter) {
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

export function GetAllResources () {
  return request({
    url: resourceApi.AllList,
    method: 'get'
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

export function DeleteById (id) {
  return request({
    url: `${resourceApi.Delete}/${id}`,
    method: 'delete'
  })
}
