

sql
 insert updt del select merge replace


jpa  persist merge remove
jdo  makepersist     deletePersistent(entity)

查询	find(Entity.class, id)	getObjectById(Entity.class, id)



事务	EntityTransaction tx = em.getTransaction(); tx.begin(); ... tx.commit();	Transaction tx = pm.currentTransaction(); tx.begin(); ... tx.commit();


API 风格

JPA 使用 EntityManager，API 设计与 Hibernate 兼容度更高，易于集成 Spring。
JDO 使用 PersistenceManager，更加通用，支持 NoSQL、文件存储等。

