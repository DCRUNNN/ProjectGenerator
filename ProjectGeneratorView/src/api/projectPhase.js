import request from '@/utils/request'

export function addProjectPhase(projectPhase) {
  return request({
    url: '/projectPhase/insert',
    method: 'post',
    data: projectPhase
  })
}

export function getProjectPhaseList(projectId) {
  return request({
    url: '/projectPhase/getProjectPhaseList',
    method: 'get',
    params: {projectId}
  })
}

export function getProjectPhaseTotal(projectId) {
  return request({
    url: '/projectPhase/getProjectPhaseTotal',
    method: 'get',
    params: {projectId}
  })
}

export function updatePhase(projectPhase) {
  return request({
    url: '/projectPhase/updatePhase',
    method: 'post',
    data: projectPhase
  })
}

export function deletePhase(phaseId,projectId,phase) {
  return request({
    url: '/projectPhase/deletePhase',
    method: 'get',
    params: {phaseId,projectId,phase}
  })
}
