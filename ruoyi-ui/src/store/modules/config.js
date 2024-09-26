import {getFrontConfigMap} from "@/api/system/config";

const config = {
  state: {
    course: '',
    jgType: ''
  },

  mutations: {
    SET_COURSE: (state, course) => {
      state.course = course
    },
    SET_JGTYPE: (state, jgType) => {
      state.jgType = jgType
    },
  },

  actions: {
    // 登录
    Config({commit}, configInfo) {
      return new Promise((resolve, reject) => {
        getFrontConfigMap().then(res => {
          commit('SET_COURSE', res.data.course)
          commit('SET_JGTYPE', res.data.jgType)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
  }
}

export default config
