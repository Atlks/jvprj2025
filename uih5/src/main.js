// 导入vant弹框样式
import 'vant/es/toast/style';
import 'vant/es/dialog/style';
import 'vant/es/notify/style';
// 导入全局样式
import './assets/styles/index.css';
import {createApp} from 'vue';
import {createPinia} from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import App from './app/App.vue';
import router from './router';
import '@/utils/plugin'; // 载入插件

import 'virtual:uno.css';
import {Lazyload} from 'vant';

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
const app = createApp(App);

// 懒加载
// app.use(Lazyload, {
//   loading: defaultGame, // 替换为你的Loading图片路径
//   error: demo_empty_pic,
//   attempt: 1,
// });
// 注册全局组件
// registerComponents(app);
// 注册全局指令
// registerDirectives(app);
// 注册状态管理器pinia
app.use(pinia);
// 注册路由
app.use(router);

app.mount('#app');

app.config.errorHandler = function (err) {
  const currentPath = router.currentRoute.value.fullPath;
};
