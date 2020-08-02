import request from '@/utils/request'

const resourceApi = {
  // get my info
  PageInfo: '/resources/page',
  Route: '/resources/routes'
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
    data: parameter
  })
}
