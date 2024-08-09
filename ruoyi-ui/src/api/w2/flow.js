import request from '@/utils/request'

// 统计分析扣分项
export function listAnalyseKfxm(query) {
  return request({
    url: '/w2/flow/analyseKfxm',
    method: 'get',
    params: query
  })
}
// 统计分析合格率
export function listAnalyseHgl(query) {
  return request({
    url: '/w2/flow/analyseHgl',
    method: 'get',
    params: query
  })
}
