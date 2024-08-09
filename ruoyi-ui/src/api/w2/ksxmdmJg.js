import request from '@/utils/request'

// 查询项目代码列表
export function listKsxmdmJg(query) {
  return request({
    url: '/w2/ksxmdmJg/list',
    method: 'get',
    params: query
  })
}

// 查询项目代码详细
export function getKsxmdmJg(id) {
  return request({
    url: '/w2/ksxmdmJg/' + id,
    method: 'get'
  })
}

// 新增项目代码
export function addKsxmdmJg(data) {
  return request({
    url: '/w2/ksxmdmJg',
    method: 'post',
    data: data
  })
}

// 修改项目代码
export function updateKsxmdmJg(data) {
  return request({
    url: '/w2/ksxmdmJg',
    method: 'put',
    data: data
  })
}

// 删除项目代码
export function delKsxmdmJg(id) {
  return request({
    url: '/w2/ksxmdmJg/' + id,
    method: 'delete'
  })
}

// 查询项目代码
export function selectKsxmdmJg(data) {
  return request({
    url: '/w2/ksxmdmJg/select',
    method: 'post',
    data: data
  })
}

// 查询项目代码
export function selectMapKsxmdmJg(data) {
  return request({
    url: '/w2/ksxmdmJg/selectMap',
    method: 'post',
    data: data
  })
}

// 查询
export function getAllKsxmByStatistics(data) {
  return request({
    url: '/w2/ksxmdmJg/getAllKsxmByStatistics',
    method: 'post',
    data: data
  })
}

//统计
export function getTotalKfCodeReport(data){
  return request({
    url: '/w2/ksxmdmJg/getTotalKfCodeReport',
    method: 'post',
    data: data
  })
}

//统计扣分
export function listKsxmdmJgByLogNew(data) {
  return request({
    url: '/w2/ksxmdmJg/listKsxmdmJgByLogNew',
    method: 'post',
    data: data
  })
}
