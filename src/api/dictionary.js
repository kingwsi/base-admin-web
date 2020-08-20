import request from '@/utils/request'

const Api = {
  // get my info
  Page: '/dictionary/page',
  Add: '/dictionary',
  Update: '/dictionary',
  Delete: '/dictionary'
}

export function page (parameter) {
  return request({
    url: Api.Page,
    method: 'get',
    params: parameter
  })
}

export function create (data) {
  return request({
    url: Api.Add,
    method: 'post',
    data: data
  })
}

export function updateById (data) {
  return request({
    url: Api.Update,
    method: 'put',
    data: data
  })
}

export function deleteById (id) {
  return request({
    url: `${Api.Delete}/${id}`,
    method: 'delete'
  })
}
