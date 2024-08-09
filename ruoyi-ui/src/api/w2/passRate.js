import request from '@/utils/request'

// 查询成绩管理列表
export function totalListRecords(query) {
  return request({
    url: '/w2/passRate/totalList',
    method: 'get',
    params: query
  })
}

export function detailListRecords(query) {
  return request({
    url: '/w2/passRate/detailList',
    method: 'get',
    params: query
  })
}
