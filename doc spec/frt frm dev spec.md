


无需打包 ， 基于 Web Components 的框架有哪些
如果你想用 Web Components 开发，又不想引入打包工具（如 Webpack、Vite、Rollup），以下这些框架和库就非常适合你 —— 它们支持原生模块加载，可以直接通过 <script type="module"> 或 CDN 使用，无需构建流程：

✅ 无需打包的 Web Components 框架推荐
名称	特点简介	是否需打包
Lit	Google 出品，语法简洁，支持模板字符串、响应式属性、Shadow DOM 等特性	❌ 可直接用 CDN
Omi	腾讯开源，融合 JSX + Web Components，支持 Shadow DOM + Virtual DOM	❌ 支持裸用
ofa.js	极简 MVVM 框架，基于 Web Components，支持模板语法、数据绑定、事件监听等	❌ 完全零构建
FAST	微软出品，适合构建设计系统，模块化强，支持主题、样式系统	❌ 可用 CDN
SkateJS	函数式风格，轻量级 Web Components 工具库，适合原生派	❌ 可裸用
Petite-Vue	虽不是 Web Components，但可嵌入组件中使用，轻量、无需构建	❌ 可直接引入



Lit + TS：Lit 天生就支持 TypeScript，非常适合组件开发。

如果你想，我可以帮你搭一个“零配置、用 TS 写 Web Components”的项目模板，甚至连 index.html 都一并生成好。想试试看吗？🧪



方式三：使用 CDN + 在线编译（适合学习）
你可以用 esm.sh 或 jsDelivr 引入 TS 支持的模块：

html
<script type="module">
  import { sum } from 'https://esm.sh/my-ts-lib';
  console.log(sum(1, 2));
</script>