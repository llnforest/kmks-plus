import request from '@/utils/request'

// 查询设备合码器列表
export function listConfigDevice(query) {
  return request({
    url: '/w2/configDevice/list',
    method: 'get',
    params: query
  })
}

// 查询设备合码器详细
export function getConfigDevice(deviceno) {
  return request({
    url: '/w2/configDevice/' + deviceno,
    method: 'get'
  })
}

// 新增设备合码器
export function addConfigDevice(data) {
  return request({
    url: '/w2/configDevice',
    method: 'post',
    data: data
  })
}

// 修改设备合码器
export function updateConfigDevice(data) {
  return request({
    url: '/w2/configDevice',
    method: 'put',
    data: data
  })
}

// 删除设备合码器
export function delConfigDevice(deviceno) {
  return request({
    url: '/w2/configDevice/' + deviceno,
    method: 'delete'
  })
}
