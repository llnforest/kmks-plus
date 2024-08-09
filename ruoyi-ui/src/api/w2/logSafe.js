import request from '@/utils/request'

// 查询安全日志列表
export function listLogSafe(query) {
  return request({
    url: '/w2/logSafe/list',
    method: 'get',
    params: query
  })
}

// 查询安全日志详细
export function getLogSafe(id) {
  return request({
    url: '/w2/logSafe/' + id,
    method: 'get'
  })
}

// 新增安全日志
export function addLogSafe(data) {
  return request({
    url: '/w2/logSafe',
    method: 'post',
    data: data
  })
}

// 修改安全日志
export function updateLogSafe(data) {
  return request({
    url: '/w2/logSafe',
    method: 'put',
    data: data
  })
}

// 删除安全日志
export function delLogSafe(id) {
  return request({
    url: '/w2/logSafe/' + id,
    method: 'delete'
  })
}
