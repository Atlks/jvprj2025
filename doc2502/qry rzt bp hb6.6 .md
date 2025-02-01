


Hibernate6.6查询 列表  ，单条记录，单个字段的api
ChatGPT said:
ChatGPT
在 Hibernate 6.6 中，Session 使用 Jakarta Persistence API (jakarta.persistence.Query) 进行查询，而不是 Hibernate 旧版的 org.hibernate.query.Query。

1. 查询列表
   使用 getResultList() 获取结果列表：


2. 查询单条记录
   如果查询结果最多只有一条数据，可以使用 getSingleResult()：


3. 查询单个字段  getSingleResult
   如果只想查询某个字段（如 count(*) 或 id），可以用 createNativeQuery 并指定返回类型：

java
Copy
Edit
String sql = "SELECT COUNT(*) FROM OrdChrg WHERE uname = :uname";

try (Session session = OrmUtilBiz.openSession(saveUrlOrdChrg)) {
Query<Long> query = session.createNativeQuery(sql, Long.class);
query.setParameter("uname", uname);

    Long count = query.getSingleResult(); // 获取单个字段值



查询类型	API 方法	说明
查询列表	getResultList()	返回 List<T>
查询单条记录	getSingleResult()	只返回 1 条记录（无结果时抛 NoResultException）
查询单个字段	getSingleResult() 或 getResultList()	适用于 



==提取单列

getResultList  vs  ColumnListHandler
new ColumnListHandler<>() 自动提取某一列（默认是第一列）。