import request from '@/utils/request'

// 查询车辆信号列表
export function listCarsignal(query) {
  return request({
    url: '/w2/carsignal/list',
    method: 'get',
    params: query
  })
}

// 查询车辆信号详细
export function getCarsignal(id) {
  return request({
    url: '/w2/carsignal/' + id,
    method: 'get'
  })
}

// 新增车辆信号
export function addCarsignal(data) {
  return request({
    url: '/w2/carsignal',
    method: 'post',
    data: data
  })
}

// 修改车辆信号
export function updateCarsignal(data) {
  return request({
    url: '/w2/carsignal',
    method: 'put',
    data: data
  })
}

// 删除车辆信号
export function delCarsignal(id) {
  return request({
    url: '/w2/carsignal/' + id,
    method: 'delete'
  })
}
