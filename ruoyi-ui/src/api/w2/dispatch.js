import request from '@/utils/request'

// 查询考车排队列表
export function carQueueList(query) {
  return request({
    url: '/w2/dispatch/carQueueList',
    method: 'get',
    params: query
  })
}
// 查询任务调度列表
export function centerList(query) {
  return request({
    url: '/w2/dispatch/centerList',
    method: 'get',
    params: query
  })
}

// 查询考试明细列表
export function flowList(query) {
  return request({
    url: '/w2/dispatch/flowList',
    method: 'get',
    params: query
  })
}

// 申请考试
export function applyExam(id) {
  return request({
    url: '/w2/dispatch/applyExam/'+id,
    method: 'put'
  })
}

// 取消考试
export function cancelExam(id) {
  return request({
    url: '/w2/dispatch/cancel/' + id,
    method: 'put'
  })
}

