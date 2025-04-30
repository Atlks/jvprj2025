

JDBC 中事务是由 Connection 对象管理的
在 JDBC 中，每个数据库连接（java.sql.Connection）拥有自己的事务上下文。你通过这个连接执行的所有 SQL 操作，默认都属于这个连接的事务中。



事务管理项	JDBC 中的表现
每个连接一个事务	✅ 是的。事务绑定在 Connection 上。
默认事务行为	自动提交（每条 SQL 都是独立事务）
手动事务	通过 setAutoCommit(false) + commit() / rollback() 控制
多库/多连接事务	JDBC 不支持分布式事务（需 JTA 或中间件）


希望多个连接共享一个事务（如跨库操作），那就必须使用像 JTA 或 Spring 的事务管理器 



你说得很对：EntityManager 确实类似于 JDBC 的 Connection，它是 JPA 操作实体数据的核心接口。但为什么事务却是通过 EntityTransaction 来操作，而不是直接通过 EntityManager 呢？这其实是 JPA 设计上的一个 “关注点分离”（Separation of Concerns） 原则。



✅ 为什么 JPA 用 EntityTransaction 而不是让 EntityManager 直接控制事务？
1. 职责分离：事务管理是独立职责
   EntityManager 的职责是：管理实体的生命周期（CRUD）

EntityTransaction 的职责是：控制事务的边界（begin/commit/rollback）

这样做有利于将不同的责任模块化，避免一个接口承担过多职责（符合 SRP 单一职责原则）。



3. 与规范解耦
   JPA 是一个持久化规范，不是事务规范。

事务行为受 JTA 或 JDBC 控制。