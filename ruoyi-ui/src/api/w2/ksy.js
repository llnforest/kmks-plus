import request from '@/utils/request'

// 查询考官信息列表
export function listKsy(query) {
  return request({
    url: '/w2/ksy/list',
    method: 'get',
    params: query
  })
}

// 查询考官信息详细
export function getKsy(xh) {
  return request({
    url: '/w2/ksy/' + xh,
    method: 'get'
  })
}

// 新增考官信息
export function addKsy(data) {
  return request({
    url: '/w2/ksy',
    method: 'post',
    data: data
  })
}

// 修改考官信息
export function updateKsy(data) {
  return request({
    url: '/w2/ksy',
    method: 'put',
    data: data
  })
}

// 删除考官信息
export function delKsy(xh) {
  return request({
    url: '/w2/ksy/' + xh,
    method: 'delete'
  })
}

// 下载信息
export function downloadKsy(params) {
  return request({
    url: '/w2/ksy/download',
    method: 'post',
    params
  })
}
