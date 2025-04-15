


apigateway模式

只需要关注业务即可

一张图总结区别：
维度	普通开发模式（传统服务）	Serverless 模式
基础设施	开发者部署、维护服务器或容器	云平台托管，函数即服务
运行模式	常驻服务，持续运行	请求触发，按需启动
资源利用率	低流量时资源闲置	零负载不计费
部署方式	一次部署整套系统	部署每个函数（功能）
状态管理	可使用内存/全局变量	无状态（必须使用外部存储）
事务处理	通过数据库连接、长事务管理	需用补偿机制、Saga 模式
调用方式	REST API、消息队列等	HTTP API、事件触发（S3、队列等）
监控日志	自建日志系统（如 ELK）	云平台集成日志系统（如 CloudWatch）
适合场景	高并发长连接、需要控制资源	瞬时请求、弹性处理任务


⚡ Serverless 开发

    你写一个函数（比如 handleUserLogin(Request r)），部署到 AWS Lambda

    云平台帮你：

        启动和销毁函数运行时

        自动扩容（同时 1万个请求都能并发触发 1万个函数）

        自动接入日志（CloudWatch）、监控、报警

    项目结构：

        每个业务操作可拆成独立函数

        可以被 S3 上传、消息队列、HTTP 请求等事件触发



🛠 开发者角度的实际区别
项目方面	普通开发	Serverless 开发
开发 IDE	任意 IDE	同样 IDE
框架支持	Spring Boot, Express 等	Spring Cloud Function、Quarkus、Go、Node.js、Python等
构建方式	Maven/Gradle 构建 WAR/JAR	构建函数 ZIP 或容器镜像
本地调试	直接运行项目	使用 SAM, serverless, faas-cli 等工具模拟执行
测试方式	REST 接口测试	模拟事件请求、函数输出
部署工具	Jenkins, Docker, SSH	SAM CLI、Serverless Framework、Terraform、CDK



🔐 安全、权限方面差异

    普通开发： 你需要配置服务器防火墙、Nginx、SSL、登录认证等

    Serverless： 权限使用细粒度的 IAM，函数执行角色可以精确控制“只能访问某个 S3、某个数据库”


🧠 思维上的转变
思维点	普通开发	Serverless
服务是长驻	是	否（按需启动）
保持状态	可以（内存/全局变量）	否（每次是新函数）
日志	写入文件	打印日志即上传
数据库连接	可以复用	每次新建或用连接池
并发控制	自行管理	云平台自动扩缩容


✅ 总结
Serverless 优势	Serverless 劣势
不用运维服务器	无状态，开发复杂度高
自动扩容、弹性强	单次运行时间有限（如 AWS Lambda 默认 15 分钟）
成本低（用多少算多少）	调试不如本地服务方便
集成日志、监控	调用链复杂时较难排查
更适合事件驱动架构	不适合长连接、WebSocket