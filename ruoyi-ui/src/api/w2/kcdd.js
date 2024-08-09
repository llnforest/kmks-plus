import request from '@/utils/request'

// 查询过程明细列表
export function getCarList(data) {
  data = data || {}
  return request({
    url: '/w2/kcdd/carList',
    method: 'post',
    data:data
  })
}
// 查询约考列表
export function listAbout(query) {
  return request({
    url: '/w2/kcdd/aboutList',
    method: 'get',
    params: query
  })
}
// 分车
export function choice(data) {
  return request({
    url: '/w2/kcdd/choice',
    method: 'put',
    data: data
  })
}

// 查询任务调度列表
export function listTask(query) {
  return request({
    url: '/w2/kcdd/taskList',
    method: 'get',
    params: query
  })
}

// 取消考试
export function cancelExam(id) {
  return request({
    url: '/w2/kcdd/cancel/' + id,
    method: 'put'
  })
}

// 取消考试
export function changeCar(data) {
  return request({
    url: '/w2/kcdd/change',
    method: 'put',
    data
  })
}

// 取消考试
export function randomChangeCar(data) {
  return request({
    url: '/w2/kcdd/randomChange',
    method: 'put',
    data
  })
}


// 上下排序
export function upDown(params) {
  return request({
    url: '/w2/kcdd/upDown',
    method: 'put',
    params
  })
}

// 查询过程明细详细
export function getKcxx(id) {
  return request({
    url: '/w2/kcxx/' + id,
    method: 'get'
  })
}

// 新增过程明细
export function addKcxx(data) {
  return request({
    url: '/w2/kcxx',
    method: 'post',
    data: data
  })
}



// 删除过程明细
export function delKcxx(id) {
  return request({
    url: '/w2/kcxx/' + id,
    method: 'delete'
  })
}
