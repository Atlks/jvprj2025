import { useRouterStore } from '@/stores/router'
export default function (to) {
  // Settings前进后退动效
  const routerStore = useRouterStore()
  if (routerStore.isIosTouchEventBack || routerStore.isIosTouchEventForward) {
    to.meta.transition = 'blank'
  } else {
    if (routerStore.isBack) {
      to.meta.transition = 'slide-right'
    } else {
      // 没有Settings任何转场动效则使用默认slide滑入动效
      if (!to.meta.transition) {
        to.meta.transition = 'slide-left'
      }
    }
  }

  // 使用过渡动效后 重置动效标识
  routerStore.setIsBackStatus(false)
  routerStore.setIsIosTouchEventBack(false)
  routerStore.setIsIosTouchEventForward(false)
}

export function listenHistory(history) {
  history.listen((path) => {
    if (path) {
      const routerStore = useRouterStore()
      routerStore.setIsBackStatus(true)
    }
  })
}
