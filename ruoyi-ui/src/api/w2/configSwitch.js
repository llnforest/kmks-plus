import request from '@/utils/request'

// 查询项目监控列表
export function listConfigSwitch(query) {
  return request({
    url: '/w2/configSwitch/list',
    method: 'get',
    params: query
  })
}

// 查询项目监控详细
export function getConfigSwitch(id) {
  return request({
    url: '/w2/configSwitch/' + id,
    method: 'get'
  })
}

// 新增项目监控
export function addConfigSwitch(data) {
  return request({
    url: '/w2/configSwitch',
    method: 'post',
    data: data
  })
}

// 修改项目监控
export function updateConfigSwitch(data) {
  return request({
    url: '/w2/configSwitch',
    method: 'put',
    data: data
  })
}

// 删除项目监控
export function delConfigSwitch(id) {
  return request({
    url: '/w2/configSwitch/' + id,
    method: 'delete'
  })
}
