import request from '@/utils/request'

export function getByTemplateID(templateID) {
  return request({
    url: '/template/getByTemplateID',
    method: 'get',
    params: {
      templateID: templateID
    }
  })
}

export function listAllTemplate(page, size) {
  return request({
    url: '/template/listAllTemplates',
    method: 'get',
    params: {
      page: page,
      size: size,
    }
  })
}

export function insertTemplate(templateDTO) {
  return request({
    url: '/template/insert',
    method: 'post',
    data: templateDTO
  })
}

export function updateTemplate(templateDTO) {
  return request({
    url: '/template/update',
    method: 'post',
    data: templateDTO
  })
}
