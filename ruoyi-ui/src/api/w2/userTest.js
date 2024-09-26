import request from '@/utils/request'

// 查询测试数据列表
export function listUserTest(query) {
  return request({
    url: '/w2/userTest/list',
    method: 'get',
    params: query
  })
}

// 查询测试数据详细
export function getUserTest(id) {
  return request({
    url: '/w2/userTest/' + id,
    method: 'get'
  })
}

// 新增测试数据
export function addUserTest(data) {
  return request({
    url: '/w2/userTest',
    method: 'post',
    data: data
  })
}

// 修改测试数据
export function updateUserTest(data) {
  return request({
    url: '/w2/userTest',
    method: 'put',
    data: data
  })
}

// 删除测试数据
export function delUserTest(id) {
  return request({
    url: '/w2/userTest/' + id,
    method: 'delete'
  })
}
