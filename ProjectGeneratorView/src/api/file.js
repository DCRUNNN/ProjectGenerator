import request from '@/utils/request'

export function downloadFile(targetFileName, downloadFileName) {
  return request({
    url: '/file/downloadFile/',
    method: 'get',
    params: {
      targetFileName: targetFileName,
      downloadFileName: downloadFileName
    },
    responseType: 'blob'
  })
}
