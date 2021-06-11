import storage from 'store'
import { login } from '@/api/login'
import { GetUserInfo } from '@/api/user'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { welcome } from '@/utils/util'

const user = {
  state: {
    token: '',
    name: '',
    welcome: '',
    avatar: '',
    roles: [],
    info: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, { name, welcome }) => {
      state.name = name
      state.welcome = welcome
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_INFO: (state, info) => {
      state.info = info
    }
  },

  actions: {
    // 登录
    Login ({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(response => {
          if (response.code !== 200) {
            reject(response.msg)
          }
          storage.set(ACCESS_TOKEN, response.data, 7 * 24 * 60 * 60 * 1000)
          commit('SET_TOKEN', response.data)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        GetUserInfo().then(response => {
          const { data } = response
          if (!data || !data.roles || data.roles < 1) {
            reject(new Error('getInfo: roles must be a non-null array !'))
          }
          const { roles, nickname, avatar } = data
          commit('SET_ROLES', roles)
          commit('SET_INFO', data)
          commit('SET_NAME', { name: nickname, welcome: welcome() })
          commit('SET_AVATAR', avatar)
          resolve(response)
          // if (result.role && result.role.permissions.length > 0) {
          //   const role = result.role
          //   role.permissions = result.role.permissions
          //   role.permissions.map(per => {
          //     if (per.actionEntitySet != null && per.actionEntitySet.length > 0) {
          //       const action = per.actionEntitySet.map(action => { return action.action })
          //       per.actionList = action
          //     }
          //   })
          //   role.permissionList = role.permissions.map(permission => { return permission.permissionId })
          //   commit('SET_ROLES', roles)
          //   commit('SET_INFO', result)
          // } else {
          //   reject(new Error('getInfo: roles must be a non-null array !'))
          // }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    Logout ({ commit, state }) {
      return new Promise((resolve) => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        storage.remove(ACCESS_TOKEN)
        resolve()
      })
    }

  }
}

export default user
