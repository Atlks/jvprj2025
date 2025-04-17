import {computed} from 'vue';
import {useRoute} from 'vue-router';
export function useRouterClass() {
  const currentRoute = useRoute();
  const isShowTabbar = computed(() => currentRoute.meta?.isShowTabbar);
  const isShowBetCart = computed(() => currentRoute.meta?.isShowBetCart);
  const getClass = computed(() => {
    if (isShowTabbar.value && betCartStore.betList.length && isShowBetCart.value) {
      return 'padding-large';
    } else if (isShowTabbar.value) {
      return 'padding-mid';
    } else if (betCartStore.betList.length && isShowBetCart.value) {
      return 'padding-sm';
    } else {
      return '';
    }
  });
  const isHomePage = computed(() => {
    return currentRoute.name === 'home';
  });

  return {
    getClass,
    isShowTabbar,
    isHomePage,
  };
}
