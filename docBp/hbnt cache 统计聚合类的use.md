

并使用 READ_ONLY 的缓存并发策略，即：

只读缓存：数据在缓存中一旦加载，之后不应被修改。适用于从不更新的数据（如地区、国家、分类表等）



@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)


🔁 查询缓存（可选）
用于缓存 HQL/JPQL 的查询结果：

java
复制
编辑
Query query = session.createQuery("from User where name = :name");
query.setParameter("name", "Tom");
query.setCacheable(true);
List<User> users = query.list();