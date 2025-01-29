


update()	对象已持久化	需在 Session 里查询后更新
merge()	对象是脱管状态	适用于离线对象更新
HQL (createQuery())	无需先查询	适用于大批量更新
EntityManager (merge())	JPA 方式	适用于 Spring Data JPA
如果你的数据量大，推荐使用 HQL，因为它直接执行 SQL 语句，无需先查询数据再更新。



merge  ...can auto qry ,then exec updt()...
\
\