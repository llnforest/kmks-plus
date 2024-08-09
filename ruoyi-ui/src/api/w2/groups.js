import request from '@/utils/request'

// 查询明细分组信息列表
export function listGroups(query) {
  return request({
    url: '/w2/groups/list',
    method: 'get',
    params: query
  })
}

// 查询明细分组信息详细
export function getGroups(xh) {
  return request({
    url: '/w2/groups/' + xh,
    method: 'get'
  })
}

// 新增明细分组信息
export function addGroups(data) {
  return request({
    url: '/w2/groups',
    method: 'post',
    data: data
  })
}

// 修改明细分组信息
export function updateGroups(data) {
  return request({
    url: '/w2/groups',
    method: 'put',
    data: data
  })
}

// 下载明细分组信息
export function downloadGroups(params) {
  return request({
    url: '/w2/groups/download',
    method: 'post',
    params
  })
}

// 删除明细分组信息
export function delGroups(xh) {
  return request({
    url: '/w2/groups/' + xh,
    method: 'delete'
  })
}
