import request from '@/utils/request'

// 查询签到设备列表
export function listDeviceSign(query) {
  return request({
    url: '/system/deviceSign/list',
    method: 'get',
    params: query
  })
}

// 查询签到设备详细
export function getDeviceSign(signId) {
  return request({
    url: '/system/deviceSign/' + signId,
    method: 'get'
  })
}

// 新增签到设备
export function addDeviceSign(data) {
  return request({
    url: '/system/deviceSign',
    method: 'post',
    data: data
  })
}

// 修改签到设备
export function updateDeviceSign(data) {
  return request({
    url: '/system/deviceSign',
    method: 'put',
    data: data
  })
}

// 删除签到设备
export function delDeviceSign(signId) {
  return request({
    url: '/system/deviceSign/' + signId,
    method: 'delete'
  })
}
