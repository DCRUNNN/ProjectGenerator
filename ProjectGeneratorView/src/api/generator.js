import request from '@/utils/request'

export function generateProject(projectDTO) {
  return request({
    url: '/generator/generateProject',
    method: 'post',
    data: projectDTO
  })
}

export function getFormSchema(templateID) {
  return request({
    url: '/generator/getFormSchema',
    method: 'get',
    params: {
      templateID: templateID
    }
  })
}
