import request from '@/utils/request'

// 查询车辆信息列表
export function listCarinfo(query) {
  return request({
    url: '/w2/carinfo/list',
    method: 'get',
    params: query
  })
}

// 查询车辆信息详细
export function getCarinfo(xh) {
  return request({
    url: '/w2/carinfo/' + xh,
    method: 'get'
  })
}

// 新增车辆信息
export function addCarinfo(data) {
  return request({
    url: '/w2/carinfo',
    method: 'post',
    data: data
  })
}

// 修改车辆信息
export function updateCarinfo(data) {
  return request({
    url: '/w2/carinfo',
    method: 'put',
    data: data
  })
}

// 删除车辆信息
export function delCarinfo(xh) {
  return request({
    url: '/w2/carinfo/' + xh,
    method: 'delete'
  })
}
// 下载信息
export function downloadCarinfo(params) {
  return request({
    url: '/w2/carinfo/download',
    method: 'post',
    params
  })
}
