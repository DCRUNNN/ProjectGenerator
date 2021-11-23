import request from '@/utils/request'

export function getAllProjects() {
  return request({
    url: '/project/getAllProjects',
    method: 'get'
  })
}

export function getProjectById(projectId) {
  return request({
    url: '/project/getProjectById',
    method: 'get',
    params: {projectId}
  })
}

export function getProjectDetailById(projectId) {
  return request({
    url: '/project/getProjectDetailById',
    method: 'get',
    params: {projectId}
  })
}

export function addProject(project) {
  return request({
    url: '/project/insert',
    method: 'post',
    data: project
  })
}

export function updateProject(project) {
  return request({
    url: '/project/update',
    method: 'post',
    data: project
  })
}

export function deleteProject(projectId) {
  return request({
    url: '/project/deleteProjectById',
    method: 'get',
    params: {projectId}
  })
}

export function deleteRateRange(rateRangeId) {
  return request({
    url: '/rateRange/deleteRateRange',
    method: 'get',
    params: {rateRangeId}
  })
}

export function getRateRange(projectId,investMoney) {
  return request({
    url: '/rateRange/getRateRange',
    method: 'get',
    params: {projectId,investMoney}
  })
}
