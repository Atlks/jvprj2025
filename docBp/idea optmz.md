
dsiab kotlin  300M

...


✅ 推荐禁用的重量级插件清单（若未使用）

插件名称	内存/资源消耗	说明
Kotlin	高	Kotlin 项目才需要，禁用后可节省数百 MB
JavaScript and TypeScript	高	如果你不开发前端，不需要 JS/TS 支持
Spring / Spring Boot	高	含 Bean 解析、依赖图、智能补全，非 Spring 项目可禁用
Database Tools and SQL	高	提供数据库浏览器、SQL 交互，未连接数据库时禁用
Docker	中-高	提供 Docker 容器管理和运行视图，不用 Docker 可禁用
Tomcat / Application Servers	中	如果不用部署到 Tomcat、JBoss 等服务器，可禁用
Groovy	中	用于 Gradle Groovy DSL 和 Groovy 脚本，不写 Groovy 可禁用
JavaFX	中	写桌面应用才需要
Scala	高	用不到 Scala 开发时应禁用
Android	高	用于移动开发，禁用后可减少插件加载和 Gradle 扫描
Cloud Services（GCP, AWS Toolkit）	高	云平台项目才需要
Markdown（如果不用写文档）	中	虽然 Markdown 本身轻量，但插件支持语法高亮、实时预览会占内存


你可以在 Help → Diagnostic Tools → Activity Monitor 查看哪些插件在消耗最多资源。