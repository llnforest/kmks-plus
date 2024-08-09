import request from '@/utils/request'

// 查询扣分代码列表
export function listKsxmkfdmJg(query) {
  return request({
    url: '/w2/ksxmkfdmJg/list',
    method: 'get',
    params: query
  })
}

// 查询扣分代码详细
export function getKsxmkfdmJg(id) {
  return request({
    url: '/w2/ksxmkfdmJg/' + id,
    method: 'get'
  })
}

// 新增扣分代码
export function addKsxmkfdmJg(data) {
  return request({
    url: '/w2/ksxmkfdmJg',
    method: 'post',
    data: data
  })
}

// 修改扣分代码
export function updateKsxmkfdmJg(data) {
  return request({
    url: '/w2/ksxmkfdmJg',
    method: 'put',
    data: data
  })
}

// 删除扣分代码
export function delKsxmkfdmJg(id) {
  return request({
    url: '/w2/ksxmkfdmJg/' + id,
    method: 'delete'
  })
}

// 查询扣分代码
export function selectMapKsxmkfdmJg(data) {
  return request({
    url: '/w2/ksxmkfdmJg/selectMap',
    method: 'post',
    data: data
  })
}
