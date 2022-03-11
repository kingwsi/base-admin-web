import request from '@/utils/request'
const Api = {
  UploadImage: '/api/oss/image'
}

export function UploadAvatar (data) {
    return request({
      url: Api.UploadImage,
      method: 'post',
      data: data,
      headers: { 'Content-Type': 'multipart/form-data' }
    })
  }

  export function SingleUpload (data) {
    return request({
      url: Api.UploadImage,
      method: 'post',
      data: data,
      headers: { 'Content-Type': 'multipart/form-data' }
    })
  }
