import request from '@/utils/request'

// 查询查询下载信息列表
export function listGroupsTemp(query) {
  return request({
    url: '/w2/groupsTemp/list',
    method: 'get',
    params: query
  })
}

// 查询查询下载信息详细
export function getGroupsTemp(lsh) {
  return request({
    url: '/w2/groupsTemp/' + lsh,
    method: 'get'
  })
}

// 新增查询下载信息
export function addGroupsTemp(data) {
  return request({
    url: '/w2/groupsTemp',
    method: 'post',
    data: data
  })
}

// 修改查询下载信息
export function updateGroupsTemp(data) {
  return request({
    url: '/w2/groupsTemp',
    method: 'put',
    data: data
  })
}

// 删除查询下载信息
export function delGroupsTemp(lsh) {
  return request({
    url: '/w2/groupsTemp/' + lsh,
    method: 'delete'
  })
}

// 下载查询下载信息
export function downloadGroupsTemp(params) {
  return request({
    url: '/w2/groupsTemp/download',
    method: 'post',
    params
  })
}
