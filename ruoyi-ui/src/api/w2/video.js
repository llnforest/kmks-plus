import request from '@/utils/request'

// 查询车载视频列表
export function listVideo(query) {
  return request({
    url: '/w2/video/list',
    method: 'get',
    params: query
  })
}

// 查询车载视频详情
export function videoInfo(kch) {
  return request({
    url: '/w2/video/'+kch,
    method: 'get',
  })
}

// 下发评判
export function downJudge(data) {
  return request({
    url: '/w2/video/downJudge',
    method: 'post',
    data: data
  })
}
