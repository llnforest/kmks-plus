import request from '@/utils/request'

// 查询车辆合码器列表
export function listConfigCar(query) {
  return request({
    url: '/w2/configCar/list',
    method: 'get',
    params: query
  })
}

// 查询车辆合码器详细
export function getConfigCar(carno) {
  return request({
    url: '/w2/configCar/' + carno,
    method: 'get'
  })
}

// 新增车辆合码器
export function addConfigCar(data) {
  return request({
    url: '/w2/configCar',
    method: 'post',
    data: data
  })
}

// 修改车辆合码器
export function updateConfigCar(data) {
  return request({
    url: '/w2/configCar',
    method: 'put',
    data: data
  })
}

// 删除车辆合码器
export function delConfigCar(carno) {
  return request({
    url: '/w2/configCar/' + carno,
    method: 'delete'
  })
}
