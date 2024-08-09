import request from '@/utils/request'

// 查询过程明细列表
export function listKcxx(query) {
  return request({
    url: '/w2/kcxx/list',
    method: 'get',
    params: query
  })
}

// 查询过程明细详细
export function getKcxx(id) {
  return request({
    url: '/w2/kcxx/' + id,
    method: 'get'
  })
}

// 新增过程明细
export function addKcxx(data) {
  return request({
    url: '/w2/kcxx',
    method: 'post',
    data: data
  })
}

// 修改过程明细
export function updateKcxx(data) {
  return request({
    url: '/w2/kcxx',
    method: 'put',
    data: data
  })
}

// 删除过程明细
export function delKcxx(id) {
  return request({
    url: '/w2/kcxx/' + id,
    method: 'delete'
  })
}

// 查询过程明细列表
export function kcxxList(data) {
  data = data || {}
  return request({
    url: '/w2/kcxx/carList',
    method: 'post',
    data
  })
}
