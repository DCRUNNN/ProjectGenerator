import request from '@/utils/request'

export function login(userForm) {
  return request({
    url: '/user/login',
    method: 'post',
    data: userForm
  })
}

export function getUserInfo() {
  return request({
    url: '/user/getUserInfo',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'get'
  })
}
