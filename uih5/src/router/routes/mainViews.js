const HomeView = () => import('@/views/HomeView/HomeView.vue');

const site = import.meta.env.VITE_SITE;
const list = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      isShowTabbar: true,
    },
  },
];

list.forEach((item) => {
  item.meta.transition = 'fade';
});

export default list;
