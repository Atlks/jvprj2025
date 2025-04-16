
web api rest项目的基础功能有哪些.md
apigate

jwt util
aes md5 secury util

aop ioc spring uti

orm hebernate util
upload

全局异常处理	exception handler	统一处理运行时异常、参数异常、权限异常等
参数校验	validation util	Spring @Valid / Hibernate Validator 自定义注解校验
log
请求日志	request log / audit log	打印请求响应日志、接口耗时、访问记录等
分页工具	page helper / pageable	分页查询封装，例如 PageRequest/PageImpl
配置管理	config util	全局配置类、读取 application.yml 等配置值
接口文档	swagger / openapi	接口自动生成文档，调试测试
定时任务调度	scheduler util	Spring Task / Quartz 定时任务支持
统一响应封装	response wrapper	接口返回统一格式（如 code/msg/data）
✅ 你已有的模块：
模块分类	模块名称	说明
安全认证	jwt util	JWT 生成、解析、验证
加密工具	aes md5 secury util	数据加密、签名、Hash 等
网关控制	apigate	通常是统一入口，用于路由、限流、鉴权等
框架工具	aop ioc spring util	AOP 实现日志/鉴权等，IoC 控制反转
数据持久化	orm hebernate util	Hibernate 或 JPA 相关 ORM 工具



📚 Web API REST 项目基础功能模块清单

| 模块分类         | 模块名称              | 说明 |
|------------------|-----------------------|------|
| 安全认证         | jwt util              | JWT 生成、解析、验证 |
| 加密工具         | aes/md5/security util | 数据加密、签名、哈希等 |
| 网关控制         | apigate               | 统一入口，处理路由、限流、鉴权等 |
| 框架工具         | aop/ioc/spring util   | AOP 实现切面、IoC 控制反转、Bean 工具 |
| 数据持久化       | orm/hibernate util    | ORM 映射工具类，数据库交互 |
| 响应处理         | response wrapper      | 接口返回结构统一，如 code/msg/data |
| 异常处理         | exception handler     | 统一处理系统、权限、参数异常等 |
| 参数校验         | validation util       | @Valid、Hibernate Validator、自定义注解等 |
| 请求日志         | request log           | 打印请求内容、响应、访问时间等 |
| 分页工具         | page helper           | 分页查询封装，PageRequest/PageImpl 等 |
| 跨域支持         | cors config           | 前后端跨域支持 |
| 接口文档         | swagger/openapi       | 自动生成 API 文档，支持测试 |
| 定时任务         | scheduler util        | Spring Task、Quartz 等定时任务支持 |
| 缓存支持         | cache util            | 如 Redis 工具类、缓存注解等 |
| 配置管理         | config util           | 项目配置读取、@ConfigurationProperties 等 |
| 国际化支持       | i18n util             | 国际化资源管理，多语言提示 |
| 文件上传下载     | file util             | 文件上传、下载、流处理工具 |
| 第三方集成       | mail/sms util         | 邮件、短信发送等集成工具 |
| 权限控制（进阶） | RBAC                  | 基于角色的权限管理系统 |
| 服务监控（进阶） | actuator/prometheus   | 健康检查、监控指标暴露 |
| 链路追踪（进阶） | trace/zipkin          | 分布式服务追踪与日志关联 |
| 接口限流（进阶） | rate limiter          | 接口频率控制，防刷保护 |
| 代码生成（进阶） | code generator        | 根据数据库表生成基础代码结构 |




前端对接 REST API 的基础功能清单（附说明）
模块分类	模块名称	说明
请求工具	API 请求封装模块	封装 axios/fetch，处理通用配置（baseURL、超时、headers）
请求拦截	请求/响应拦截器	添加 Token、统一处理错误、自动刷新 Token
接口统一管理	API 定义模块	所有 REST 接口集中定义，统一调用（如 api/user.js）
状态管理	全局状态管理	Vuex / Pinia / Redux / Zustand，用于管理用户信息、Token 等
路由管理	路由跳转控制	Vue Router / React Router，支持路由守卫
鉴权控制	权限控制	登录态检测、页面访问控制、角色菜单控制
登录模块	用户认证页面	登录、注册、找回密码等
错误处理	全局异常处理	异常弹窗提示、接口错误提示
加载状态	加载动画/按钮状态	请求过程中按钮 loading、骨架屏、进度条等
国际化（可选）	i18n 多语言支持	语言切换、多语言资源文件管理
响应数据封装	统一返回格式处理	处理 code/data/msg 格式的后端响应数据
组件库（UI）	统一 UI 组件使用	如 Element Plus、Ant Design、Naive UI
表单工具	表单验证与收集	VeeValidate、Yup、Formik、FormKit 等
通用组件	公共组件复用库	Dialog、Table、Form、Pagination 等封装组件
环境配置	环境变量管理	.env 文件区分开发/测试/生产环境 baseURL 等配置
接口文档联动	OpenAPI/Swagger 生成	可视化调试工具，甚至可自动生成接口类型定义（如 Swagger→TypeScript）
安全防护（可选）	XSS/CSRF/输入校验	防止注入攻击、点击劫持等前端防护
响应缓存（可选）	页面缓存/接口缓存	保留页面状态、避免重复请求等优化体验



前后端开发所需基础功能清单（Markdown 表格）

    一份适用于大多数 RESTful API 项目 的功能总览，分为：通用功能（前后端共用）、后端特有功能、前端特有功能



✅ 通用基础功能（前后端都需要）

| 功能模块     | 子模块/工具                         | 说明                            |
|--------------|--------------------------------|-------------------------------|
| 鉴权认证     | JWT / Token                    | 身份认证，访问控制                     |
| 安全加密     | AES / MD5 / RSA                | 数据加密、签名、验签                    |
| 请求处理     | 接口封装 / 参数验证                    | REST API 参数处理、拦截器等            |
| 错误处理     | 错误码 / 全局异常                     | 系统级错误处理，统一响应格式                |
| 配置管理     | 配置文件 / 环境变量                    | 管理环境依赖，如 API 地址、密钥等           |
| 权限控制     | RBAC / ACL                     | 角色/权限机制控制资源访问                 |
| 接口文档     | Swagger / OpenAPI              | 文档自动生成、联调工具                   |
| 日志记录     | 操作日志 / 请求日志                    | 记录访问行为，排查问题                   |
| 国际化支持   | i18n                           | 多语言提示、界面翻译                    |
| 数据格式     | 通用数据结构封装                       | 格式统一                          |
| 测试工具     | 单元测试 / 接口测试                    |                               |
| ORM 数据持久化   | LocalStorage ，mysql            | 映射数据，简化 CRUD ，前端读写web store，后端读写db |
| 事务控制         |                                | 保证原子性、一致性                     |
| 定时任务         |                                | 自动执行周期任务                      |
| 异步处理         | @Async / MQ / js线程池            | 提升响应速度，解耦流程                   |
| 文件处理         | 文件上传下载 / OSS                   | 存储图片、文档等资源                    |
| 监控告警         |                                | 应用状态监控，系统健康检查                 |
| 数据导出导入     | CSV / Excel / PDF 工具           | 提供表格/报表功能                     |
| 状态管理         |                                | 管理全局状态，如用户信息、权限等              |
| 路由控制         | Router                         | 功能导航与路由守卫                     |
| 请求封装         | Fetch                          | 封装统一请求逻辑、拦截器                  |
| 缓存管理        | redis，LocalStorage / IndexedDB | 保留用户状态、缓存数据                   |
| 表单验证         |                                | 前后端输入校验与提示                    |
| 多媒体处理       | 图片 音视频处理                       | 支持富媒体功能                       |
| 自动生成类型     | Swagger → 类型工具                 | 减少 API 接口类型定义重复工作             |
| UI 组件库        | 前端ui，后端ssR ui                  | 快速构建页面，统一样式                   |
| 动画过渡         | CSS Transition / AnimateCSS    | 增强视觉体验                        |
| 加载提示         | Loading 动画 / Skeleton          | 异步数据加载提示优化                    |
| 响应式设计       |                                | 支持 PC + 移动端自适应                |



==后端特有

| 功能模块         | 子模块/工具    | 说明 |
|------------------|-------------|------|
| 数据库连接池     | HikariCP / Druid          | 高效连接数据库 |
| 接口限流         | Guava RateLimiter / Redis | 防止接口被刷爆 |
| DevOps 支持      | Dockerfile / CI 脚本        | 自动部署、镜像化运行 |




==前端特有功能

| 功能模块         | 子模块/工具                | 说明 |
|------------------|-----------------------------|------|

