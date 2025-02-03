


总结
情况	数据库 age 的值
游离实体的 age 是 null	可能更新为 null（如果 @Column(nullable = false) 不允许 null，可能报错）
游离实体的 age 没有被赋值	可能更新为 null
游离实体的 age 有值	age 会被新值覆盖
使用 @DynamicUpdate	age 可能不会被更新
💡 建议：

如果不希望 age 变 null，要先查询数据库的完整对象，再 merge()。
或者用 @DynamicUpdate，只更新修改过的字段。


@DynamicUpdate 的实现机制
Hibernate 动态生成 SQL 语句，只更新被修改的字段。具体流程：

查询实体：从数据库加载对象（find()）。
检测修改：Hibernate 通过 "脏检查"（Dirty Checking）判断哪些字段被修改。
生成 SQL：仅更新被修改的字段，而不是整个对象。
执行 UPDATE 语句。
🔍 @DynamicUpdate 内部工作原理

Hibernate 不会提前缓存 SQL 语句（普通 UPDATE 语句可以被预编译）。
但 @DynamicUpdate 会在运行时动态生成 UPDATE 语句，因此 可能会稍微影响性能。


如果需要部分字段更新，但不想用 @DynamicUpdate，也可以用 JPQL 或 Criteria API！