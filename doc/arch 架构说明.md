
---------------项目架构说明

=编程语言 java21
=重要框架 spring hibernate mysql
=数据存储  mysql
=使用技术 rest aop ioc orm des
=api接口
=other
=安全策略
=清晰结构


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
 
Cookie 加密存储（敏感信息加密）

=api接口
使用rest接口
RESTful 风格 API

    统一响应格式（状态码、消息、数据）
    参数校验（Spring Validation）
    DTO（数据传输对象）映射（防止暴露实体对象）

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