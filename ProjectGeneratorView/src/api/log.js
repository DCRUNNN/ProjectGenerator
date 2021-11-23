import request from '@/utils/request'

export function listLogsByType(page, size, logType) {
  return request({
    url: '/log/listLogsByType',
    method: 'get',
    params: {
      page: page,
      size: size,
      logType: logType
    }
  })
}

export function listByTimeAndType(page, size, logType, startTime, endTime) {
  return request({
    url: '/log/listByTimeAndType',
    method: 'get',
    params: {
      page: page,
      size: size,
      logType: logType,
      startTime: startTime,
      endTime: endTime
    }
  })
}

export function deleteById(logId) {
  return request({
    url: '/log/deleteLogById',
    method: 'get',
    params: {
      logId: logId
    }
  })
}

export function deleteByType(logType) {
  return request({
    url: '/log/deleteLogByType',
    method: 'get',
    params: {
      logType: logType
    }
  })
}
