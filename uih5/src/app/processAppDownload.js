import { isCTYWebApp, isIOSPure } from '@/utils'
import { setIsHideDownload } from '@/utils/cache'

export function processAppDownload() {
  const isShowDownload = [isCTYWebApp(), isIOSPure()].some((bool) => bool)
  setIsHideDownload(isShowDownload)
}
