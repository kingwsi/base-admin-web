import request from '@/utils/request'
const Api = {
  UploadImage: '/oss/image'
}

export function UploadAvatar (data) {
    return request({
      url: Api.UploadImage,
      method: 'post',
      data: data,
      headers: { 'Content-Type': 'multipart/form-data' }
    })
  }
