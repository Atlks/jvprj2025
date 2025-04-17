import Clipboard from 'clipboard';
import {showToast} from 'vant';
export default {
  beforeMount(el, binding) {
    el.clipboard = new Clipboard(el, {
      text: () => binding.value,
    });

    el.clipboard.on('success', function (e) {
      showToast('复制成功');
      e.clearSelection();
    });

    el.clipboard.on('error', function (e) {
      showToast('复制失败');
    });
  },
  beforeUnmount(el) {
    el.clipboard.destroy();
  },
};
