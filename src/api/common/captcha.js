import request from '@/utils/request'

const apis = {
  Captcha: '/api/verification/captcha'
}

export function GetCaptcha () {
  return request({
    url: apis.Captcha,
    method: 'get',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
