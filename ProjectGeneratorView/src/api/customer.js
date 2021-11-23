import request from '@/utils/request'

export function getAllCustomers(params) {
  return request({
    url: '/customer/listAllCustomers',
    method: 'get',
    params
  })
}

export function addCustomer(customer) {
  return request({
    url: '/customer/insert',
    method: 'post',
    data: customer
  })
}

export function updateCustomer(customer) {
  return request({
    url: '/customer/update',
    method: 'post',
    data: customer
  })
}

export function deleteCustomer(customerId) {
  return request({
    url: '/customer/deleteCustomerById',
    method: 'get',
    params: {customerId}
  })
}
