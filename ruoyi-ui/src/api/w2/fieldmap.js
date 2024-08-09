import request from '@/utils/request'

// 查询地图模型列表
export function listFieldmap(query) {
  return request({
    url: '/w2/fieldmap/list',
    method: 'get',
    params: query
  })
}

// 查询地图模型详细
export function getFieldmap(fieldname) {
  return request({
    url: '/w2/fieldmap/' + fieldname,
    method: 'get'
  })
}

// 新增地图模型
export function addFieldmap(data) {
  return request({
    url: '/w2/fieldmap',
    method: 'post',
    data: data
  })
}

// 修改地图模型
export function updateFieldmap(data) {
  return request({
    url: '/w2/fieldmap',
    method: 'put',
    data: data
  })
}

// 删除地图模型
export function delFieldmap(fieldname) {
  return request({
    url: '/w2/fieldmap/' + fieldname,
    method: 'delete'
  })
}
