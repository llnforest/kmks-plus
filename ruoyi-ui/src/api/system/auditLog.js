import request from '@/utils/request'

// 查询审计日志列表
export function listAuditLog(query) {
  return request({
    url: '/system/auditLog/list',
    method: 'get',
    params: query
  })
}

// 查询审计日志详细
export function getAuditLog(auditId) {
  return request({
    url: '/system/auditLog/' + auditId,
    method: 'get'
  })
}

// 新增审计日志
export function addAuditLog(data) {
  return request({
    url: '/system/auditLog',
    method: 'post',
    data: data
  })
}

// 修改审计日志
export function updateAuditLog(data) {
  return request({
    url: '/system/auditLog',
    method: 'put',
    data: data
  })
}

// 删除审计日志
export function delAuditLog(auditId) {
  return request({
    url: '/system/auditLog/' + auditId,
    method: 'delete'
  })
}
