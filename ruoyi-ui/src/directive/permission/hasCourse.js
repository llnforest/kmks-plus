 /**
 * v-hasRole 角色权限处理
 * Copyright (c) 2019 ruoyi
 */

import store from '@/store'

export default {
  inserted(el, binding) {
    const { value } = binding
    const course = store.getters && store.getters.configCourses
    if(course != value){
      el.parentNode && el.parentNode.removeChild(el)
    }
  }
}
