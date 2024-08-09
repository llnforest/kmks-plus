import request from '@/utils/request'

// 查询排队信息列表
export function listQueuing(query) {
  return request({
    url: '/w2/queuing/list',
    method: 'get',
    params: query
  })
}

// 查询排队信息详细
export function getQueuing(id) {
  return request({
    url: '/w2/queuing/' + id,
    method: 'get'
  })
}

// 新增排队信息
export function addQueuing(data) {
  return request({
    url: '/w2/queuing',
    method: 'post',
    data: data
  })
}

// 修改排队信息
export function updateQueuing(data) {
  return request({
    url: '/w2/queuing',
    method: 'put',
    data: data
  })
}

// 删除排队信息
export function delQueuing(id) {
  return request({
    url: '/w2/queuing/' + id,
    method: 'delete'
  })
}

// 修改考试车型信息
export function updateKscx(data) {
  return request({
    url: '/w2/queuing/updateKscx',
    method: 'put',
    data: data
  })
}
// 修改排队信息
export function updateInfo(data) {
  data.ksxm = data.ksxm.join(",");
  return request({
    url: '/w2/queuing/updateInfo',
    method: 'put',
    data: data
  })
}
// 上下排序
export function upDown(params) {
  return request({
    url: '/w2/queuing/upDown',
    method: 'put',
    params
  })
}
