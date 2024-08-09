import request from '@/utils/request'

// 查询闸机设备列表
export function listDeviceGate(query) {
  return request({
    url: '/system/deviceGate/list',
    method: 'get',
    params: query
  })
}

// 查询闸机设备详细
export function getDeviceGate(gateId) {
  return request({
    url: '/system/deviceGate/' + gateId,
    method: 'get'
  })
}

// 新增闸机设备
export function addDeviceGate(data) {
  return request({
    url: '/system/deviceGate',
    method: 'post',
    data: data
  })
}

// 修改闸机设备
export function updateDeviceGate(data) {
  return request({
    url: '/system/deviceGate',
    method: 'put',
    data: data
  })
}

// 删除闸机设备
export function delDeviceGate(gateId) {
  return request({
    url: '/system/deviceGate/' + gateId,
    method: 'delete'
  })
}
