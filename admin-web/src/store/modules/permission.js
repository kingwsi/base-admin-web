import { getRoutes } from '@/api/resource'
import { constantRoutes, componentsMap } from '@/router/index'

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
        const accessedRoutes = genTreeRoutes(response.data, '-1')
        commit('SET_ROUTES', accessedRoutes)
        resolve(accessedRoutes)
      })
    })
  }
}

/**
 * list转tree
 * @param {*} list
 * @param {*} parentId
 */
export function genTreeRoutes(list, parentId) {
  const children = []
  list.forEach(item => {
    const res = resToRoute(item)
    // 获取子类
    if (item.parentId === parentId) {
      // 设置该元素的子元素
      res.children = genTreeRoutes(list, item.id)
      // 将当前元素放入数组
      children.push(res)
    }
  })
  return children
}

/**
 * 转换数据结构为路由格式
 * @param {} data
 */
export function resToRoute(data) {
  return {
    path: data.uri,
    name: data.name,
    component: data.parentId === '-1' ? componentsMap['layout'] : componentsMap[data.uri],
    meta: {
      title: data.name,
      icon: data.icon ? data.icon : 'icon'
    },
    children: []
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
