import { UserLayout } from '@/layouts'

/**
 * 静态路由映射配置
 * 根据接口返回的页面地址组装组件
 */
export const asyncRouterMap = {
  '/system/role': () => import(`@/views/system/role/List`),
  '/system/role/permission/:id': () => import(`@/views/system/role/Permission`),
  '/system/resource': () => import(`@/views/system/resource/List`),
  '/system/dictionary': () => import(`@/views/system/dictionary/List`),
  '/system/user': () => import(`@/views/system/user/List`),
  '/system/api-whitelist': () => import(`@/views/system/api-whitelist/List`),
  '/member/list': () => import(`@/views/member/List`),
  '/dashboard/workplace': () => import(`@/views/dashboard/Workplace`),
  '/dashboard/user': () => import(`@/views/dashboard/account/Info`)
}

/**
 * 基础路由
 * 公共路由 不走权限控制
 * @type { *[] }
 */
export const constantRouterMap = [
  {
    path: '/user',
    component: UserLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import(/* webpackChunkName: "user" */ '@/views/login')
      },
      {
        path: 'register',
        name: 'register',
        component: () => import(/* webpackChunkName: "user" */ '@/views/login/Register')
      },
      {
        path: 'register-result',
        name: 'registerResult',
        component: () => import(/* webpackChunkName: "user" */ '@/views/login/RegisterResult')
      },
      {
        path: 'recover',
        name: 'recover',
        component: undefined
      }
    ]
  },
  {
    path: '/404',
    component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404')
  }
]
