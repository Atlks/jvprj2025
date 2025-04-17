import { parseURLParams } from '@/utils'
import { setInviteCode } from '@/utils/cache'
export function processInviteCode() {
  const { i_code } = parseURLParams()
  setInviteCode(i_code)
}
