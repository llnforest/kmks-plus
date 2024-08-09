import request from '@/utils/request'

// 查询路线管理列表
export function listLineconfig(query) {
  return request({
    url: '/w2/lineconfig/list',
    method: 'get',
    params: query
  })
}

// 查询路线管理详细
export function getLineconfig(line) {
  return request({
    url: '/w2/lineconfig/' + line,
    method: 'get'
  })
}

// 新增路线管理
export function addLineconfig(data) {
  return request({
    url: '/w2/lineconfig',
    method: 'post',
    data: data
  })
}

// 修改路线管理
export function updateLineconfig(data) {
  return request({
    url: '/w2/lineconfig',
    method: 'put',
    data: data
  })
}

// 删除路线管理
export function delLineconfig(line) {
  return request({
    url: '/w2/lineconfig/' + line,
    method: 'delete'
  })
}

// 查询线路
export function selectLineconfig(data) {
  return request({
    url: '/w2/lineconfig/select',
    method: 'post',
    data:data
  })
}
