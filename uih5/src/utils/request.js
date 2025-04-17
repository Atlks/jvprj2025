import axios from 'axios';

let deviceId = null;
const client = getClient();
let isRefreshing = false;
let requestQueue = [];
const service = axios.create({
  timeout: 0, // Settings超时时间为0，即取消超时
});
// 请求拦截器
service.interceptors.request.use(
  async (config) => {
    // const userStore = useUserStore();
    // if (userStore.userInfo.userName) {
    //   config.headers['Username'] = userStore.userInfo.userName;
    // }
    // const token = getToken();
    // if (!config.headers.Authorization) {
    //   config.headers['Authorization'] = token;
    // }
    // if (config.data && config.data.token) {
    //   config.headers['Authorization'] = config.data.token;
    // }
    // // console.log( '----:',config.data)
    // // 添加语言标识
    // const appStore = useAppStore();
    // config.headers.Language = appStore.language;
    // config.headers['Accept-Language'] = getBackEndLanguage(appStore.language);
    // if (!deviceId) {
    //   deviceId = await getDeviceId();
    // }
    // config.headers.client = client; // 客户端
    // config.headers.deviceNo = deviceId;
    // config.headers.clientModel = navigator.userAgent; //登录型号
    // config.headers.appVersion = version; //APP版本号

    // // 添加平台
    // const platform = import.meta.env.VITE_SITE;
    // if (platform) {
    //   config.headers.platform = platform;
    // }
    // // 判断是否为表单提交
    // if (config.data && config.data instanceof FormData) {
    //   // 更改请求头为 multipart/form-data
    //   config.headers['Content-Type'] = 'multipart/form-data';
    // } else {
    //   config.headers['Content-Type'] = 'application/json';
    // }

    // if (isRefreshing && maya.sessionId) {
    //   return new Promise((resolve) => {
    //     requestQueue.push((newToken) => {
    //       config.headers.Authorization = `${newToken}`;
    //       resolve(config);
    //     });
    //   });
    // }

    return config;
  },
  (error) => {
    Toast(error.message);
    return Promise.reject(error);
  },
);

// 响应拦截器
service.interceptors.response.use(
  async (response) => {
    // const userStore = useUserStore();

    // let {data, config} = response;
    // const {code, msg} = data;
    // let reqData = '';
    // if (config.data) {
    //   // post
    //   if (config.data instanceof FormData) {
    //     reqData = 'undefined';
    //   } else {
    //     reqData = JSON.parse(config.data);
    //   }
    // } else {
    //   // get
    //   reqData = config.params;
    // }
    // // 登录状态已失效
    // if (code === ERR_EXPIRED && maya.sessionId) {
    //   await refreshToken();
    //   return;
    //   // window.location.reload();
    //   // if (!isRefreshing) {
    //   //   isRefreshing = true;
    //   //   try {
    //   //     const loading = showLoadingToast({
    //   //       message: 'loading...',
    //   //       forbidClick: true,
    //   //       duration: 0,
    //   //     });
    //   //     const newToken = await refreshToken(); // **刷新 Token**
    //   //     setToken(newToken.data.accessToken);
    //   //     userStore.token = newToken;
    //   //     isRefreshing = false;
    //   //     loading.close();
    //   //     // 重新执行所有等待中的请求
    //   //     requestQueue.forEach((callback) => callback(newToken));
    //   //     requestQueue = [];
    //   //     // **重新请求当前失败的请求**
    //   //     config.headers.Authorization = `Bearer ${newToken}`;
    //   //     return service(config);
    //   //   } catch (err) {
    //   //     isRefreshing = false;
    //   //     return Promise.reject(err);
    //   //   }
    //   // }
    //   // // **等待 Token 刷新完成后，重新执行请求**
    //   // return new Promise((resolve) => {
    //   //   requestQueue.push((newToken) => {
    //   //     config.headers.Authorization = `Bearer ${newToken}`;
    //   //     resolve(service(config));
    //   //   });
    //   // });
    // } else if (code === ERR_EXPIRED) {
    //   executeExpiredLogic();
    // } else if (code !== ERR_OK) {
    //   if (reqData && !reqData.noNeedResponseMsg) {
    //     showToast(msg);
    //   }
    //   // 上报日志
    //   // reportError(config.data, data, config.url);
    // }
    return data;
  },
  (error) => {
    // 报错信息

    return Promise.reject(error.message);
    // showToast(error.response.data.msg || '网络繁忙，请稍后重试')
  },
);
export default service;
