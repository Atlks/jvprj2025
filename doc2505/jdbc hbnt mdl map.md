

Hibernate 与 JDBC 模型对应关系
Hibernate 概念	JDBC 概念	说明
SessionFactory	数据源/连接池（DataSource）	类似一个数据库连接池工厂，是线程安全的单例对象，生命周期长、常驻内存，负责创建 Session。
Session	Connection（连接）	表示一个和数据库交互的会话，不是线程安全的，通常和一个事务绑定；每次数据库操作用一个新的 Session。
Transaction	JDBC 事务控制（conn.setAutoCommit(false) 等）	Hibernate 抽象的事务处理，实际底层调用 JDBC 的事务提交和回滚。
Query / Criteria	PreparedStatement / Statement	Hibernate 的 HQL / Criteria 查询最终会转换为 SQL 并执行。
Entity Mapping (@Entity)	SQL 表映射模型	Hibernate 会将 Java 类通过注解/配置映射为表。
