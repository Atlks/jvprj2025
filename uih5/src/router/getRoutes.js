// 获取routes配置
const getRoutes = () => {
  const routes = [];
  // import.meta.globEager 是 Vite 提供的一种立即导入多个模块的方式
  const context = import.meta.globEager('./routes/**/*.js');
  for (const path in context) {
    routes.push(...context[path].default);
  }
  return routes;
};
const routes = getRoutes();

export default routes.concat([
  // {
  //   path: '/404',
  //   alias: ['/500', '/403'],
  //   component: () => import('@/views/ErrorView/index.vue')
  // },
  {
    path: '/:catchAll(.*)',
    redirect: {path: '/'},
  },
]);
