import request from '@/utils/request'

const Api = {
  // get my info
  Info: '/api/generator-code/info',
  Generator: '/api/dictionary'
}

export function GetTableInfo (parameter) {
    return request({
      url: Api.Info,
      method: 'get',
      params: parameter
    })
  }
