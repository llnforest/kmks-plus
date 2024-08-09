import request from '@/utils/request'

// 查询成绩管理列表
export function listRecords(query) {
  return request({
    url: '/w2/records/list',
    method: 'get',
    params: query
  })
}

// 查询成绩管理详细
export function getRecords(id) {
  return request({
    url: '/w2/records/' + id,
    method: 'get'
  })
}

// 新增成绩管理
export function addRecords(data) {
  return request({
    url: '/w2/records',
    method: 'post',
    data: data
  })
}

// 修改成绩管理
export function updateRecords(data) {
  return request({
    url: '/w2/records',
    method: 'put',
    data: data
  })
}

// 删除成绩管理
export function delRecords(id) {
  return request({
    url: '/w2/records/' + id,
    method: 'delete'
  })
}

// 查询成绩管理详细
export function getPrint(id) {
  window.open(window.globalConfig.VUE_APP_BASE_API + `/w2/records/getReport/${id}`, '_blank');
}

//申请误判重考
export function applyReset(data) {
  return request({
    url: '/w2/records/resetRecord',
    method: 'put',
    data
  })
}

export function remote() {
  return request({
    url: '/w2/records/remote',
    method: 'get',
  })
}

// 查询成绩管理详细
export function getFlowList(params) {
  return request({
    url: '/w2/records/flowList',
    method: 'post',
    params
  })
}
export function getAnalyseHgl(params) {
  return request({
    url: '/w2/records/getAnalyseHgl',
    method: 'get',
    params: params
  })
}
