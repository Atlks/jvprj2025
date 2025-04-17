import handleRouterAnimation from './handleRouterAnimation';

import NProgress from 'nprogress';

NProgress.configure({showSpinner: false});
export default async (to, from) => {
  try {
    NProgress.start();
    // 处理路由切换动效
    handleRouterAnimation(to);
  } catch (err) {
    console.log(err, '路由守卫报错');
  }
};
