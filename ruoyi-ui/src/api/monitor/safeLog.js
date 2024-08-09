import request from '@/utils/request'

// 查询安全日志列表
export function listSafeLog(query) {
  return request({
    url: '/monitor/safeLog/list',
    method: 'get',
    params: query
  })
}

// 查询安全日志详细
export function getSafeLog(id) {
  return request({
    url: '/monitor/safeLog/' + id,
    method: 'get'
  })
}

// 新增安全日志
export function addSafeLog(data) {
  return request({
    url: '/monitor/safeLog',
    method: 'post',
    data: data
  })
}

// 修改安全日志
export function updateSafeLog(data) {
  return request({
    url: '/monitor/safeLog',
    method: 'put',
    data: data
  })
}

// 删除安全日志
export function delSafeLog(id) {
  return request({
    url: '/monitor/safeLog/' + id,
    method: 'delete'
  })
}
