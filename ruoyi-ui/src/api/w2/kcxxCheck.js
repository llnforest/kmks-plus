import request from '@/utils/request'

// 查询考车自检列表
export function listKcxxCheck(query) {
  return request({
    url: '/w2/kcxxCheck/list',
    method: 'get',
    params: query
  })
}

// 查询考车自检详细
export function getKcxxCheck(kcbh) {
  return request({
    url: '/w2/kcxxCheck/' + kcbh,
    method: 'get'
  })
}

// 新增考车自检
export function addKcxxCheck(data) {
  return request({
    url: '/w2/kcxxCheck',
    method: 'post',
    data: data
  })
}

// 修改考车自检
export function updateKcxxCheck(data) {
  return request({
    url: '/w2/kcxxCheck',
    method: 'put',
    data: data
  })
}

// 删除考车自检
export function delKcxxCheck(kcbh) {
  return request({
    url: '/w2/kcxxCheck/' + kcbh,
    method: 'delete'
  })
}
