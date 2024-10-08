import request from '@/utils/request'


// 新增成绩管理
export function getImgData(data) {
  return request({
    url: '/util/imgData',
    method: 'post',
    data: data
  })
}

