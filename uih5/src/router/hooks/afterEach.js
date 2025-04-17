import NProgress from 'nprogress'
export default async (/*to*/) => {
  NProgress.done()
  toTop()
  // sendToAnalytics(to.fullPath)
}

// 统计页面访问信息
// const sendToAnalytics = (fullPath) => {
//   console.log(fullPath)
// }

// 返回顶部
function toTop() {
  // const appPosition = document.querySelector('#app')
  // appPosition.style.position = 'unset'
  // document.querySelector('html').scrollTo({ left: 0, top: 0 })
  // appPosition.style.position = 'absolute'
  window.scrollTo(0, 0)
}
