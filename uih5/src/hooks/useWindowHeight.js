// useWindowHeight.js
import {ref, onMounted, onUnmounted} from 'vue';

export function useWindowHeight() {
  const windowHeight = ref(null);
  const getWindowHeight = () => {
    windowHeight.value = window.innerHeight + 'px';
  };

  // 组件挂载时执行
  onMounted(() => {
    getWindowHeight();
    window.addEventListener('resize', getWindowHeight);
  });

  // 组件卸载时清除事件监听器
  onUnmounted(() => {
    window.removeEventListener('resize', getWindowHeight);
  });

  return {windowHeight};
}
