import request from '@/utils/request'

// 查询驾考参数列表
export function listconfig(query) {
  return request({
    url: '/w2/config/list',
    method: 'get',
    params: query
  })
}

// 查询驾考参数详细
export function getconfig(lIncode) {
  return request({
    url: '/w2/config/' + lIncode,
    method: 'get'
  })
}

// 新增驾考参数
export function addconfig(data) {
  return request({
    url: '/w2/config',
    method: 'post',
    data: data
  })
}

// 修改驾考参数
export function updateconfig(data) {
  return request({
    url: '/w2/config',
    method: 'put',
    data: data
  })
}

// 删除驾考参数
export function delconfig(lIncode) {
  return request({
    url: '/w2/config/' + lIncode,
    method: 'delete'
  })
}
