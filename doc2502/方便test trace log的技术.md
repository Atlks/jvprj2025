
=test
aop   ioc
junit
1. 依赖注入（Dependency Injection, DI）
   作用: 使代码更加解耦，方便单元测试时用 Mock 替换依赖对象。
2. 2. Mocking（模拟对象）
      作用: 在测试时用 假的依赖对象（Mock）代替真实对象，以便控制测试环境
5. 数据库测试技术
   作用: 使数据库相关代码可测试，不依赖真实数据库。
   方法:
   嵌入式数据库（H2, SQLite, Derby）
   内存数据库（H2, Redis, Mock Data）
   数据库迁移工具（Flyway, Liquibase）
   事务回滚测试（Spring @Transactional）
7. 服务虚拟化（Service Virtualization）
   作用: 在测试时用虚拟 API 服务器代替真实外部系统，提高测试的稳定性。
   常见工具:
   WireMock（Java）
10. 并发测试  jmeter
    作用: 测试多线程/并发代码是否正确，避免死锁和竞态条件。
    方法:
    Java: JMH（性能测试）, ConcurrencyTestRule
zidonghua tet,   web driver


=trace log

sturts as code log
print mthid(prm)  n ret val
gramm clor
结构化日志（JSON / Key-Value）
作用
让日志更容易被机器解析，可以直接被日志分析系统（如 ELK、Graylog）处理。
便于查询、过滤、可视化分析。

emoji

2. 颜色高亮日志（ANSI 颜色码）
   作用
   让日志在终端中更清晰，快速分辨不同类型的日志

3. MDC（Mapped Diagnostic Context，日志上下文）
   作用
   让 每条日志自动带上请求 ID、用户 ID、线程 ID，方便定位问题。
   适合多线程 / 高并发环境。

4. 链路追踪（Tracing）
   作用
   适用于 微服务架构，可以 追踪整个请求的流转过程。
   结合 Zipkin、Jaeger 等分布式追踪工具。

6. 日志采集系统（ELK / Loki / Fluentd）
   作用
   适用于 大规模分布式系统，可以实时收集、分析、查询日志。

7. 方法级日志增强（AOP 切面日志）
   作用
   无需修改业务代码，即可自动 打印方法入参、返回值、执行时间。

总结
技术	作用	适用场景
结构化日志	让日志格式化，便于查询	大型系统，数据分析
颜色高亮	让日志在终端更清晰	本地调试，开发环境
MDC	让每条日志带上请求 ID	高并发系统，日志关联
链路追踪	记录请求的完整调用链	微服务，分布式系统
异步日志	避免日志阻塞业务逻辑	高并发场景
日志采集系统	统一存储、查询日志	分布式日志管理
AOP 日志	自动记录方法调用日志	业务代码无侵入总结
技术	作用	适用场景
结构化日志	让日志格式化，便于查询	大型系统，数据分析
颜色高亮	让日志在终端更清晰	本地调试，开发环境
MDC	让每条日志带上请求 ID	高并发系统，日志关联
链路追踪	记录请求的完整调用链	微服务，分布式系统
异步日志	避免日志阻塞业务逻辑	高并发场景
日志采集系统	统一存储、查询日志	分布式日志管理
AOP 日志	自动记录方法调用日志	业务代码无侵入