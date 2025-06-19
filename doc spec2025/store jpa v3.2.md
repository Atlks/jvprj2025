

jakarta.persistence-api 版本2 版本3 以及3.1 3.2的新特性
Jakarta Persistence API（前身为 JPA）在不同版本中引入了许多新特性，提升了对象关系映射（ORM）的能力。以下是各版本的主要更新：

Jakarta Persistence 2.x
仍然属于 Java EE 生态系统，未迁移至 Jakarta EE。

增强的 @Converter 机制：支持更灵活的数据转换。

改进的 Criteria API：提供更强的类型安全查询能力。

支持 StoredProcedureQuery：允许调用存储过程。

Jakarta Persistence 3.0
迁移至 Jakarta EE：javax.persistence 命名空间更改为 jakarta.persistence。

移除 EntityManagerFactory 的 Persistence.createEntityManagerFactory(String, Map) 方法。

增强 Criteria API：支持更复杂的查询表达式。

改进 JPQL 语法：增加 GROUP BY 和 HAVING 语句的优化。

Jakarta Persistence 3.1
支持 java.time.Instant 和 java.time.Year：改进时间类型映射。

增强 JPQL：新增 UNION、INTERSECT、EXCEPT 操作符。

改进 Criteria API：支持 CAST、LEFT JOIN、RIGHT JOIN。

新增 getSingleResultOrNull() 方法：避免 NoResultException。

Jakarta Persistence 3.2
新增 PersistenceConfiguration API：支持 编程式 配置 EntityManagerFactory，无需 persistence.xml。

引入 SchemaManager API：支持 创建、删除、验证、清空 数据库表。

增强事务管理：新增 runInTransaction() 和 callInTransaction() 方法。

改进 JPQL：支持 || 作为字符串拼接操作符。

支持 TypedQueryReference：增强 命名查询 的类型安全性。

这些新特性使 Jakarta Persistence 更加灵活、高效，特别是在 事务管理 和 查询优化 方面。如果你对某个特性感兴趣，我可以提供更详细的示例代码！🚀