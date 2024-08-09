import request from '@/utils/request'

// 查询安全员管理列表
export function listSafepeople(query) {
  return request({
    url: '/w2/safepeople/list',
    method: 'get',
    params: query
  })
}

// 查询安全员管理详细
export function getSafepeople(sZjhm) {
  return request({
    url: '/w2/safepeople/' + sZjhm,
    method: 'get'
  })
}

// 新增安全员管理
export function addSafepeople(data) {
  return request({
    url: '/w2/safepeople',
    method: 'post',
    data: data
  })
}

// 修改安全员管理
export function updateSafepeople(data) {
  return request({
    url: '/w2/safepeople',
    method: 'put',
    data: data
  })
}

// 删除安全员管理
export function delSafepeople(sZjhm) {
  return request({
    url: '/w2/safepeople/' + sZjhm,
    method: 'delete'
  })
}
