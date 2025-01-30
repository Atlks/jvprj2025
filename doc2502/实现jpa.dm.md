


如何自己实现一个jpa的实现orm
ChatGPT said:
ChatGPT
要自己实现一个 JPA 兼容的 ORM（对象关系映射）框架，需要完成以下关键组件：

1️⃣ 核心设计概览
JPA 主要包含：

实体管理（EntityManager）
查询语言（JPQL）
事务管理
持久化上下文
元数据解析（注解处理）
数据库访问（JDBC）
你可以基于 JDBC 和 反射 实现一个简化版 ORM。


5️⃣ 结论
🔹 要自己实现 JPA ORM，至少要做：

解析 @Entity, @Table, @Column 等注解。
设计 EntityManager 进行 save()、find() 操作。
使用 JDBC 进行数据库交互。
实现事务管理（begin()、commit()、rollback()）。
