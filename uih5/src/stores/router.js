import { ref } from 'vue'
import { defineStore } from 'pinia'
export const useRouterStore = defineStore('router', () => {
  const isIosTouchEventBack = ref(false)
  const isIosTouchEventForward = ref(false)

  const isBack = ref(false)
  const setIsBackStatus = (bool) => {
    isBack.value = bool
  }
  const setIsIosTouchEventBack = (bool) => {
    isIosTouchEventBack.value = bool
  }
  const setIsIosTouchEventForward = (bool) => {
    isIosTouchEventForward.value = bool
  }
  return {
    isBack,
    isIosTouchEventBack,
    isIosTouchEventForward,
    setIsBackStatus,
    setIsIosTouchEventBack,
    setIsIosTouchEventForward
  }
})
