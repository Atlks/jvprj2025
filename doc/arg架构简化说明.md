---------------项目架构说明

=编程语言 java21
=重要框架  jdk jax-rs javalin hibernate mysql
=数据存储  mysql
=使用技术 rest aop ioc orm des
=api接口
=other
= 使用的技术规范
.. rest技术方面 JSR 370	JAX-RS
.. faas规范（aws faas） ，apigateway httphandler 输入输出dto
.. 参数校验 JSR 349	 Bean Validation
.. 存储方面规范 jpa JSR 338
.. 文档规范openapi
.. aop规范 web filter,orm jpa拦截器
。。事件驱动  jpa事件驱动
.. ioc JSR 330: Dependency Injection for Java 规范
没怎么使用java的ioc，大部分模块没有使用ioc，部分模块用了faas的简化ioc
.. 权限secury 方面 Jakarta Security（JSR 375）规范
.. NoSQL & 数据处理
.. 日志使用faas aop模式拦截输出
。。jwt  oauth规范
。。openbank obie v3 账户 余额 流水 报表 充值规范
。。事务规范  transaction-api  jpa事务
。。guava cache
。。分页  spring data翻页
。。测试test junit openapi  postman测试生成
js测试脚本  

=分层结构

=安全策略
=清晰结构
=文档 openapi规范