import request from '@/utils/request'

// 查询考点信息列表
export function listFactory(query) {
  return request({
    url: '/w2/factory/list',
    method: 'get',
    params: query
  })
}

// 查询考点信息详细
export function getFactory(xh) {
  return request({
    url: '/w2/factory/' + xh,
    method: 'get'
  })
}

// 新增考点信息
export function addFactory(data) {
  return request({
    url: '/w2/factory',
    method: 'post',
    data: data
  })
}

// 修改考点信息
export function updateFactory(data) {
  return request({
    url: '/w2/factory',
    method: 'put',
    data: data
  })
}

// 删除考点信息
export function delFactory(xh) {
  return request({
    url: '/w2/factory/' + xh,
    method: 'delete'
  })
}

// 下载信息
export function downloadFactory(params) {
  return request({
    url: '/w2/factory/download',
    method: 'post',
    params
  })
}
