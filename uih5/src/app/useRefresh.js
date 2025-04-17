export function useRefresh() {
  // 刷新页面
  const isRouteAlive = ref(true)
  function reload() {
    isRouteAlive.value = false
    nextTick(() => {
      isRouteAlive.value = true
    })
  }
  provide('reload', reload)

  return {
    isRouteAlive,
    reload
  }
}
