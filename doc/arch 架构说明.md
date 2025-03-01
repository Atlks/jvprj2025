
---------------项目架构说明

=编程语言 java21
=重要框架 spring hibernate mysql
=数据存储  mysql
=使用技术 rest aop ioc orm des
=api接口
=other
= 使用的 Java 技术规范
.. rest技术方面 JSR 370	JAX-RS
.. 参数校验 JSR 349	 Bean Validation
.. 存储方面规范 jpa JSR 338
.. 文档规范openapi
.. aop规范 AspectJ
.. ioc JSR 330: Dependency Injection for Java 规范
.. 权限secury 方面 Jakarta Security（JSR 375）规范
.. NoSQL & 数据处理
=安全策略
=清晰结构
=文档 openapi规范


=编程语言 java21

=重要框架 spring hibernate mysql
Spring AOP（面向切面编程，用于日志、事务、权限等）
Spring IoC（依赖注入管理 Bean 生命周期）
Hibernate（ORM 框架，与数据库交互）

=数据存储  mysql

=使用技术 rest aop ioc orm des

RESTful API（用于接口通信）
AOP（Aspect-Oriented Programming）（日志、事务、权限控制）
IoC（Inversion of Control）（依赖注入，解耦组件）
ORM（Object-Relational Mapping）（数据对象与数据库表映射）
DES 加解密（数据加密）
MD5 + Salt 密码加密（用户密码存储方案）
aop 自实现代理模式 
Cookie 加密存储（敏感信息加密）

=使用的java技术规范
..rest技术方面 JSR 370	JAX-RS
JSR 370	JAX-RS 2.1	REST API 规范（@GET、@POST）
..参数校验 JSR 349	 Bean Validation
Bean Validation 1.1	@Valid、@Pattern 等增强
Java Bean Validation（JSR 380
.. 存储方面规范 jpa JSR 338
JSR 338	JPA 2.1	Java 持久化 API (@Entity、@Table)
..文档规范 openapi
..aop规范 AspectJ
Java 本身并没有提供官方的 AOP 规范，但 AOP 主要依赖于 Spring AOP 和 AspectJ 
..ioc JSR 330: Dependency Injection for Java 规范
，这是 Java 标准的 依赖注入（DI） 规范。
权限方面的规范
..secury 方面 Jakarta Security（JSR 375）规范，  @PermitAll

..NoSQL & 数据处理
   JSR	名称	描述
   JSR 107	JCache	缓存 API
   JSR 353	Java API for JSON Processing (JSON-P)	javax.json 处理 JSON
   JSR 374	JSON-P 1.1	JSON Patch & JSON Merge Patch

=api接口
使用rest接口
RESTful 风格 API

    统一响应格式（状态码、消息、数据）
    参数校验（  Validation）
    DTO（数据传输对象）映射 

=other

rest即可，参数校验，参数映射dto
cookie加密 
密码md5+salt签名

=安全策略

    Cookie 加密存储用户信息
    密码采用 MD5+Salt 进行加密存储
    重要数据加密（如 DES）

=清晰结构

    采用分层架构（Controller -> Service -> Repository）
    业务逻辑可插拔，支持 AOP 进行事务管理和日志记录

日志管理

    采用 SLF4J + Logback 进行日志管理