import request from '@/utils/request'

export function listAllPublicParams(page, size) {
  return request({
    url: '/param/listAllPublicParams',
    method: 'get',
    params: {
      page: page,
      size: size,
    }
  })
}

export function insertPublicParam(paramForm) {
  return request({
    url: '/param/insertPublicParam',
    method: 'post',
    data: paramForm
  })
}

export function updatePublicParam(paramForm) {
  return request({
    url: '/param/updatePublicParam',
    method: 'post',
    data: paramForm
  })
}
