import {useAppStore} from '@/stores/app';
import {loadScript} from '@/utils';
export async function processVerify() {
  const appStore = useAppStore();
  // const lineType = await appStore.getVerifyDomainType()
  let url = '';
  const site = import.meta.env.VITE_SITE;
  if (site === 'vi') {
    url = 'https://static.botion.com/v1/boc.js';
  } else {
    url = 'https://static.geetest.com/v4/gt4.js';
  }
  // if (lineType === 1) {
  //   url = 'https://static.botion.com/v1/boc.js'
  // } else {
  //   url = 'https://static.geetest.com/v4/gt4.js'
  // }
  if (url) {
    loadScript(url);
  }
}
