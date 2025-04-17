import { parseURLParams } from '@/utils'
import { setReferCode } from '@/utils/cache'
export function processReferCode() {
  const { referCode } = parseURLParams()
  setReferCode(referCode)
}
