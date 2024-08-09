import request from '@/utils/request'

// 查询车辆自检列表
export function listCheckcar(query) {
  return request({
    url: '/w2/checkcar/list',
    method: 'get',
    params: query
  })
}

// 查询车辆自检详细
export function getCheckcar(sCarno) {
  return request({
    url: '/w2/checkcar/' + sCarno,
    method: 'get'
  })
}

// 新增车辆自检
export function addCheckcar(data) {
  return request({
    url: '/w2/checkcar',
    method: 'post',
    data: data
  })
}

// 修改车辆自检
export function updateCheckcar(data) {
  return request({
    url: '/w2/checkcar',
    method: 'put',
    data: data
  })
}

// 删除车辆自检
export function delCheckcar(sCarno) {
  return request({
    url: '/w2/checkcar/' + sCarno,
    method: 'delete'
  })
}
