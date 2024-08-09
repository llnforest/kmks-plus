import request from '@/utils/request'

// 查询黑名单管理列表
export function listUserBlack(query) {
  return request({
    url: '/system/userBlack/list',
    method: 'get',
    params: query
  })
}

// 查询黑名单管理详细
export function getUserBlack(id) {
  return request({
    url: '/system/userBlack/' + id,
    method: 'get'
  })
}

// 新增黑名单管理
export function addUserBlack(data) {
  return request({
    url: '/system/userBlack',
    method: 'post',
    data: data
  })
}

// 修改黑名单管理
export function updateUserBlack(data) {
  return request({
    url: '/system/userBlack',
    method: 'put',
    data: data
  })
}

// 删除黑名单管理
export function delUserBlack(id) {
  return request({
    url: '/system/userBlack/' + id,
    method: 'delete'
  })
}
// 解锁黑名单
export function unLockBlack(id) {
  return request({
    url: '/system/userBlack/unLock/' + id,
    method: 'put'
  })
}
