import { createRouter, createWebHistory } from 'vue-router'
import beforEach from './hooks/beforEach'
import afterEach from './hooks/afterEach'
import { listenHistory } from './hooks/handleRouterAnimation'
import routes from './getRoutes'
// 创建路由history实例
const history = createWebHistory()
// 监听路由回退事件
listenHistory(history)
// 创建路由实例
const router = createRouter({
  history,
  routes,
  // scrollBehavior(to, from, savedPosition) {
  //   return { x: 0, y: 0 }
  //   // if (savedPosition) {
  //   //   return savedPosition
  //   // } else {
  //   //   return { x: 0, y: 0 }
  //   // }
  // },
})
// 全局路由守卫钩子
router.beforeEach(beforEach)
router.afterEach(afterEach)
export default router
