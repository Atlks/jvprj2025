import { useAppStore } from '@/stores/app'
import router from '@/router'
import { onUnmounted } from 'vue'
export function checkMaintain() {
  processMaintain()
  let checkTime = setInterval(processMaintain, 1000 * 30)
  onUnmounted(() => {
    clearInterval(checkTime)
    checkTime = null
  })
}

async function processMaintain() {
  let isMaintain = null
  try {
    // isMaintain = await getMaintainStatus()
  } catch (e) {
    console.log(e)
  }
  if (isMaintain) {
    router.push('/500')
  }
}

// async function getMaintainStatus() {
//   const appStore = useAppStore()
//   await appStore.getMaintainInfo()
//   return appStore.getMaintainStatus
// }
