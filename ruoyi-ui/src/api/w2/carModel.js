import request from '@/utils/request'

// 查询车辆模型列表
export function listCarModel(query) {
  return request({
    url: '/w2/carModel/list',
    method: 'get',
    params: query
  })
}

// 查询车辆模型详细
export function getCarModel(modelname) {
  return request({
    url: '/w2/carModel/' + modelname,
    method: 'get'
  })
}

// 新增车辆模型
export function addCarModel(data) {
  return request({
    url: '/w2/carModel',
    method: 'post',
    data: data
  })
}

// 修改车辆模型
export function updateCarModel(data) {
  return request({
    url: '/w2/carModel',
    method: 'put',
    data: data
  })
}

// 删除车辆模型
export function delCarModel(modelname) {
  return request({
    url: '/w2/carModel/' + modelname,
    method: 'delete'
  })
}

// 查询车辆模型列表
export function selectCarModel(query) {
  return request({
    url: '/w2/carModel/select',
    method: 'post'
  })
}
