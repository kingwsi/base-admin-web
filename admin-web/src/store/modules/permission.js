import { asyncRoutes, constantRoutes } from '@/router'
import { getRoutes } from '@/api/resource'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise(resolve => {
      getRoutes().then(response => {
        console.log('tree test')
        console.log(listToTree(response.data))
      })
      let accessedRoutes
      if (roles.includes('admin')) {
        accessedRoutes = asyncRoutes || []
      } else {
        accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
      }
      commit('SET_ROUTES', accessedRoutes)
      console.log(accessedRoutes)
      resolve(accessedRoutes)
    })
  }
}

export function listToTree(oldArr) {
  oldArr.forEach(element => {
    const parentId = element.parentId
    if (parentId !== '-1') {
      oldArr.forEach(ele => {
        ele.meta = '888'
        if (ele.id === parentId) { // 当内层循环的ID== 外层循环的parendId时，（说明有children），需要往该内层id里建个children并push对应的数组；
          if (!ele.children) {
            ele.children = []
          }
          element.meta = '777'
          ele.children.push(element)
        }
      })
    }
  })
  oldArr = oldArr.filter(ele => ele.parentId === '-1') // 这一步是过滤，按树展开，将多余的数组剔除；
  return oldArr
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
