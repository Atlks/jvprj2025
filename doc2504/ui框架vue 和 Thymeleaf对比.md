注解式样路由

根据url，获取对应hdr 函数
渲染html界面
输出


2. 使用 API 的方式对比
   功能	Thymeleaf 语法	Vue.js 语法
   输出变量	th:text="${user.name}"	{{ user.name }}
   条件判断	th:if="${user.active}"	v-if="user.active"
   循环遍历	th:each="u : ${users}"	v-for="u in users"
   表单绑定	th:field="*{username}"	v-model="form.username"
   事件处理	N/A（后端处理）	@click="doSomething"
   异步请求	无（服务端同步渲染）	使用 axios.get(...) 向后端请求数据

🚦 3. 开发模型对比
特性	Thymeleaf	Vue.js
模板预览	可直接打开 .html 预览	需配合构建/运行
动态交互	页面刷新一次完成所有内容渲染	页面无需刷新，动态交互
SEO 友好	✅（服务端渲染）	❌（前端渲染默认不利于 SEO）
初学者友好	✅（和后端代码配合）	✅（但需要一定 JS 基础）



✅ 总结异同
维度	相同点	不同点
目标	都用来构建 Web UI	渲染职责不同（前端 vs 后端）
模板语法	都使用类似 {{ }} 插值	Vue 是 JS 语法，Thymeleaf 是表达式语言（OGNL）
适用场景	可用于展示数据	Vue 更适合动态交互，Thymeleaf 更适合简单后台