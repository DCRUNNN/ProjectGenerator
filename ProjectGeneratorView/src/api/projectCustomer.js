import request from '@/utils/request'

export function addProjectCustomer(projectCustomer) {
  return request({
    url: '/projectCustomer/insert',
    method: 'post',
    data: projectCustomer
  })
}

export function getInvestCustomersByProjectId(projectId) {
  return request({
    url: '/projectCustomer/getInvestCustomersByProjectId',
    method: 'get',
    params: {projectId}
  })
}

export function getCustomersInProjectPhase(projectId,phase) {
  return request({
    url: '/projectCustomer/getCustomersInProjectPhase',
    method: 'get',
    params: {projectId,phase}
  })
}

export function getCustomerInvestProject(customerId) {
  return request({
    url: '/projectCustomer/getCustomerInvestProject',
    method: 'get',
    params: {customerId}
  })
}

