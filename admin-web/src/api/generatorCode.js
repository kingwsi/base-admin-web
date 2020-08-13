import request from '@/utils/request'

const Api = {
  // get my info
  Info: '/generator-code/info',
  Generator: '/dictionary'
}

export function GetTableInfo (parameter) {
    return request({
      url: Api.Info,
      method: 'get',
      params: parameter
    })
  }
