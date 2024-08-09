import request from '@/utils/request'

// 查询场地项目编号列表
export function listCdxmbh(query) {
  return request({
    url: '/w2/cdxmbh/list',
    method: 'get',
    params: query
  })
}

// 查询场地项目编号详细
export function getCdxmbh(nid) {
  return request({
    url: '/w2/cdxmbh/' + nid,
    method: 'get'
  })
}

// 新增场地项目编号
export function addCdxmbh(data) {
  return request({
    url: '/w2/cdxmbh',
    method: 'post',
    data: data
  })
}

// 修改场地项目编号
export function updateCdxmbh(data) {
  return request({
    url: '/w2/cdxmbh',
    method: 'put',
    data: data
  })
}

// 删除场地项目编号
export function delCdxmbh(nid) {
  return request({
    url: '/w2/cdxmbh/' + nid,
    method: 'delete'
  })
}

// 查询过程明细列表
export function selectCdxmbh(data) {
  data = data || {}
  return request({
    url: '/w2/cdxmbh/select',
    method: 'post',
    data
  })
}

export function listCdxmbhByLogNew(data) {
  return request({
    url: '/w2/cdxmbh/listCdxmbhByLogNew',
    method: 'post',
    data: data
  })
}

//---------------------------项目备案信息下载---------------------------
// 查询项目备案信息下载列表
export function dataList(query) {
  return request({
    url: '/w2/cdxmbh/dataList',
    method: 'get',
    params: query
  })
}

// 下载驾校信息
export function downloadCdxmbh(params) {
  return request({
    url: '/w2/cdxmbh/download',
    method: 'post',
    params
  })
}

// 修改场地项目编号
export function updateCdxmbhData(data) {
  return request({
    url: '/w2/cdxmbh/updateData',
    method: 'put',
    data: data
  })
}




