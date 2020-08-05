import { constantRouterMap, constantRouterComponents } from '@/config/router.config'
import { getRoutes } from '@/api/resource/index'

/**
 * 过滤账户是否拥有某一个权限，并将菜单从加载列表移除
 *
 * @param permission
 * @param route
 * @returns {boolean}
 */
// function hasPermission (permission, route) {
//   if (route.meta && route.meta.permission) {
//     let flag = false
//     for (let i = 0, len = permission.length; i < len; i++) {
//       flag = route.meta.permission.includes(permission[i])
//       if (flag) {
//         return true
//       }
//     }
//     return false
//   }
//   return true
// }

/**
 * 单账户多角色时，使用该方法可过滤角色不存在的菜单
 *
 * @param roles
 * @param route
 * @returns {*}
 */
// eslint-disable-next-line
function hasRole(roles, route) {
  if (route.meta && route.meta.roles) {
    return route.meta.roles.includes(roles.id)
  } else {
    return true
  }
}

// function filterAsyncRouter (routerMap, roles) {
//   const accessedRouters = routerMap.filter(route => {
//     if (hasPermission(roles.permissionList, route)) {
//       if (route.children && route.children.length) {
//         route.children = filterAsyncRouter(route.children, roles)
//       }
//       return true
//     }
//     return false
//   })
//   return accessedRouters
// }

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    }
  },
  actions: {
    GenerateRoutes ({ commit }) {
      return new Promise(resolve => {
        // const { roles } = data
        // const accessedRouters = filterAsyncRouter(asyncRouterMap, roles)
        // commit('SET_ROUTERS', accessedRouters)
        // resolve()
        getRoutes().then(response => {
          // 组装生成路由
          const accessedRoutes = genTreeRoutes(response.data, '-1')
          console.log(accessedRoutes)
          commit('SET_ROUTERS', accessedRoutes)
          resolve()
        })
      })
    }
  }
}
/**
 * list转tree
 * @param {*} list
 * @param {*} parentId
 */
export function genTreeRoutes (list, parentId) {
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
export function resToRoute (data) {
  return {
    // path: data.uri,
    // name: data.name,
    // component: data.parentId === '-1' ? constantRouterComponents['layout'] : constantRouterComponents[data.uri],
    // meta: {
    //   title: data.name,
    //   icon: data.icon ? data.icon : 'icon'
    // },
    // 路由地址 动态拼接生成如 /dashboard/workplace
    path: data.uri,
    // 路由名称，建议唯一
    name: data.name || data.component || '',
    // 该路由对应页面的 组件
    component: data.parentId === '-1' ? constantRouterComponents['layout'] : constantRouterComponents[data.component],
    // meta: 页面标题, 菜单图标, 页面权限(供指令权限用，可去掉)
    meta: { title: data.name, icon: data.icon || undefined },
    children: []
  }
}
export default permission
