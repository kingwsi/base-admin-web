import { getRoutes } from '@/api/resource'
import { constantRoutes } from '@/router/index'

/* Layout */
import Layout from '@/layout'
// /**
//  * Use meta.role to determine if the current user has permission
//  * @param roles
//  * @param route
//  */
// function hasPermission(roles, route) {
//   if (route.meta && route.meta.roles) {
//     return roles.some(role => route.meta.roles.includes(role))
//   } else {
//     return true
//   }
// }

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
// export function filterAsyncRoutes(routes, roles) {
//   const res = []

//   routes.forEach(route => {
//     const tmp = { ...route }
//     if (hasPermission(roles, tmp)) {
//       if (tmp.children) {
//         tmp.children = filterAsyncRoutes(tmp.children, roles)
//       }
//       res.push(tmp)
//     }
//   })

//   return res
// }

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
  generateRoutes({ commit }) {
    return new Promise(resolve => {
      getRoutes().then(response => {
        const accessedRoutes = genTreeRoutes(toRouterList(response.data), '-1')
        commit('SET_ROUTES', accessedRoutes)
        resolve(accessedRoutes)
      })
    })
  }
}

// list转tree
// genTreeRoutes(list, "-1")
export function genTreeRoutes(list, parentId) {
  const children = []
  list.forEach(item => {
    if (item.type === 'ROUTE') {
      // 获取子类
      if (item.parentId === parentId) {
        // 设置该元素的子元素
        item.children = genTreeRoutes(list, item.id)
        // 将当前元素放入数组
        children.push(item)
      }
    }
  })
  return children
}

/**
 * list转成路由格式的list
 * @param {*} data
 */
export function toRouterList(data) {
  const res = []
  data.forEach(item => {
    const route = {}
    route.id = item.id
    route.parentId = item.parentId
    route.path = item.path
    route.redirect = null
    route.name = item.name
    route.type = item.type
    route.meta = { title: item.name, icon: item.icon, noCache: true }
    route.children = []
    if (item.parentId === '-1') {
      route.component = () => Layout
    } else {
      route.component = () => import('@/views/' + item.uri.replace(/^\/*/g, ''))
    }
    res.push(route)
  })
  return res
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
