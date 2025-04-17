// import Hammer from 'hammerjs'
export default async function openVConsole() {
  const {default: vConsole} = await import('vconsole');
  if (import.meta.env.VITE_APP_ENV !== 'PROD') {
    new vConsole();
  }
}
if (!['PROD'].includes(import.meta.env.VITE_APP_ENV)) {
  openVConsole();
}
// const hammer = new Hammer(document.body)

// let pressTimeout

// hammer.on('press', () => {
//   // Settings长按的时间阈值，单位为毫秒
//   const longPressThreshold = 6000
//   pressTimeout = setTimeout(() => {
//     console.log('Long press detected')
//     openVConsole()
//   }, longPressThreshold)
// })

// hammer.on('tap', () => {
//   clearTimeout(pressTimeout) // 取消长按事件
// })

export const throttle = (func, delay = 60) => {
  let timer = null;
  let start = 0;
  return function (...args) {
    const current = +new Date();
    clearTimeout(timer);
    if (current - start >= delay) {
      console.log(current - start);
      func(args);
      start = current;
    } else {
      timer = setTimeout(() => func(args), delay);
    }
  };
  // return function(...args) {
  //   const current = +new Date()
  //   clearTimeout(timer)
  //   if (current - start >= delay) {
  //     func.apply(this, args)
  //     start = current
  //   } else {
  //     timer = setTimeout(() => func(this, args), delay)
  //   }
  // }
};
