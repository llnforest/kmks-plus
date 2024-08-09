import {getFrontConfigMap} from "@/api/system/config";

const config = {
  state: {
    course: '',
  },

  mutations: {
    SET_COURSE: (state, course) => {
      state.course = course
    },
  },

  actions: {
    // 登录
    Config({ commit }, configInfo) {
      return new Promise((resolve, reject) => {
        getFrontConfigMap().then(res => {
          commit('SET_COURSE', res.data.course)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
  }
}

export default config
