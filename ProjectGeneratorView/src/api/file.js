import request from '@/utils/request'

export function uploadSingleFile(file) {
  return request({
    url: '/file/uploadFile',
    method: 'post',
    data: file
  })
}

export function uploadMultipleFile(file) {
  return request({
    url: '/file/uploadMultipleFiles',
    method: 'post',
    data: file
  })
}

export function getAllFiles() {
  return request({
    url: '/file/getAllFileList',
    method: 'get'
  })
}

export function downloadFile(fileName) {
  return request({
    url: '/file/downloadFile/',
    method: 'get',
    params: {fileName},
    responseType: 'blob'
  })
}
export function deleteFile(fileName) {
  return request({
    url: '/file/deleteFile/',
    method: 'get',
    params: {fileName}
  })
}
