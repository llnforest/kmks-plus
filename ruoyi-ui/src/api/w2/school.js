import request from '@/utils/request'

// 查询基础编码列表
export function listSchool(query) {
  return request({
    url: '/w2/school/list',
    method: 'get',
    params: query
  })
}

// 查询基础编码详细
export function getSchool(nid) {
  return request({
    url: '/w2/school/' + nid,
    method: 'get'
  })
}

// 新增基础编码
export function addSchool(data) {
  return request({
    url: '/w2/school',
    method: 'post',
    data: data
  })
}

// 修改基础编码
export function updateSchool(data) {
  return request({
    url: '/w2/school',
    method: 'put',
    data: data
  })
}

// 删除基础编码
export function delSchool(nid) {
  return request({
    url: '/w2/school/' + nid,
    method: 'delete'
  })
}

// 查询线路
export function selectSchool(data) {
  data = data || {}
  return request({
    url: '/w2/school/select',
    method: 'post',
    data:data
  })
}

//---------------------------驾校数据下载---------------------------
// 查询驾校数据下载列表
export function dataList(query) {
  return request({
    url: '/w2/school/dataList',
    method: 'get',
    params: query
  })
}

// 下载驾校信息
export function downloadSchool(params) {
  return request({
    url: '/w2/school/download',
    method: 'post',
    params
  })
}
