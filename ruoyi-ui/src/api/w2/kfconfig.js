import request from '@/utils/request'

// 查询评判参数列表
export function listKfconfig(query) {
  return request({
    url: '/w2/kfconfig/list',
    method: 'get',
    params: query
  })
}

// 查询评判参数详细
export function getKfconfig(gakey) {
  return request({
    url: '/w2/kfconfig/' + gakey,
    method: 'get'
  })
}

// 新增评判参数
export function addKfconfig(data) {
  return request({
    url: '/w2/kfconfig',
    method: 'post',
    data: data
  })
}

// 修改评判参数
export function updateKfconfig(data) {
  return request({
    url: '/w2/kfconfig',
    method: 'put',
    data: data
  })
}

// 删除评判参数
export function delKfconfig(gakey) {
  return request({
    url: '/w2/kfconfig/' + gakey,
    method: 'delete'
  })
}

// 按组获取扣分代码
export function getKfdmByGroup(query) {
  return request({
    url: '/w2/kfconfig/getKfdmByGroup',
    method: 'get',
    params: query
  })
}
