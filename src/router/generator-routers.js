// eslint-disable-next-line
import * as loginService from '@/api/login'
import { BasicLayout, BlankLayout, PageView, RouteView } from '@/layouts'
import { asyncRouterMap } from '@/config/router.config'

// 前端路由表
const constantRouterComponents = {
  // 基础页面 layout 必须引入
  BasicLayout: BasicLayout,
  BlankLayout: BlankLayout,
  RouteView: RouteView,
  PageView: PageView,
  404: () => import(`@/views/404`),
  ...asyncRouterMap
}

// 前端未找到页面路由（固定不用改）
const notFoundRouter = {
  path: '*', redirect: '/404', hidden: true
}

// 根级菜单
const rootRouter = {
  key: 'index',
  name: 'index',
  path: '/',
  component: BasicLayout,
  redirect: '/dashboard/user',
  meta: {
    title: '首页'
  },
  children: []
}

/**
 * 动态生成菜单
 * @param token
 * @returns {Promise<Router>}
 */
export const generatorDynamicRouter = () => {
  return new Promise((resolve, reject) => {
    loginService.getCurrentUserNav().then(res => {
      const { data } = res
      const menuNav = []
      const childrenNav = []
      // 后端数据, 根级树数组,  根级 PID
      listToTree(data, childrenNav, -1)
      rootRouter.children = childrenNav
      menuNav.push(rootRouter)
      menuNav.push(notFoundRouter)
      resolve(menuNav)
    }).catch(err => {
      reject(err)
    })
  })
}

/**
 * 数组转树形结构
 * @param list 源数组
 * @param tree 树
 * @param parentId 父ID
 */
const listToTree = (list, tree, parentId) => {
  list.forEach(item => {
    // 判断是否为父级菜单
    if (item.parentId === parentId) {
      const child = {
        ...covertToRoute(item),
        children: []
      }
      // 迭代 list， 找到当前菜单相符合的所有子菜单
      listToTree(list, child.children, item.id)
      // 删掉不存在 children 值的属性
      if (child.children.length <= 0) {
        delete child.children
      } else {
        // 排序子菜单
        child.children.sort((o1, o2) => {
          return o1.sort - o2.sort
        })
      }
      // 加入到树中
      tree.push(child)
    }
  })
  // 排序
  tree.sort((o1, o2) => {
    return o1.sort - o2.sort
  })
}

/**
 * 菜单组件组装
 * @param resource
 * @returns {{path: (string|[{min: number, trigger: string, message: string, required: boolean}]|string), component: *, meta: {keepAlive: boolean, icon: (*|undefined), title: *}, name: (*|string), id: *, parentId: *}}
 */
const covertToRoute = (resource) => {
  const router = {
    id: resource.id,
    parentId: resource.parentId,
    sort: resource.sort,
    // 如果路由设置了 path，则作为默认 path，否则 路由地址 动态拼接生成如 /dashboard/workplace
    path: resource.uri,
    // 路由名称，建议唯一
    name: resource.name || '',
    // 该路由对应页面的 组件 :方案1
    component: resource.parentId === -1 ? constantRouterComponents['RouteView'] : constantRouterComponents[resource.uri] || constantRouterComponents[404],
    // 该路由对应页面的 组件 :方案2 (动态加载)
    // component: () => import(`@/views/${item.component}`),
    meta: {
      title: resource.name,
      icon: resource.icon || undefined,
      keepAlive: true
    }
  }
  // 是否设置了隐藏菜单
  if (resource.remark && resource.remark === 'hidden') {
    router.hidden = true
  }
  // 是否设置了隐藏子菜单
  if (resource.remark && resource.remark === 'hidden_children') {
    router.hideChildrenInMenu = true
  }
  console.log(router)
  // 重定向
  resource.redirect && (router.redirect = resource.redirect)
  return router
}
