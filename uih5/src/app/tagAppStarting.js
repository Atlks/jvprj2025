/*
处理登录过期问题
最大网络接口响应时长为10s, timeoutSettings为10s
*/
export const tagAppStarting = () => {
  // 应用运行10s之后，标识应用一直处于运行状态，否则应用处于重启运行状态
  let timer = setTimeout(() => {
    window.isAppLongRunStatus = true
    clearTimeout(timer)
  }, 10 * 1000)
}
