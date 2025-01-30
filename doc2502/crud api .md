



deletePersistent


## **JDO API 总结**

### **完整 API 表格**

| **操作**  | **方法**                                 | **说明**       | jpa | 
|-----------|----------------------------------------|--------------|------|
| **新增**  | `makePersistent(obj)`                 | 添加对象      |  persist(obj)   |
| **查询**  | `newQuery(Class)`                     | 查询所有对象  |  `createQuery("JPQL 语句")`  | 
| **查询**  | `newQuery(Class, "条件")`             | 按条件查询    | `createQuery("JPQL 语句")`    |
| **查询**  | `getObjectById(Class, id)`            | 按 ID 查询    | find(Class, id)   |
| **更新**  | `makePersistent(obj)`                 | 更新对象      |  merge(obj)  |
| **删除**  | `deletePersistent(obj)`               | 删除对象      | remove(obj |
| **删除**  | `deletePersistentAll(Collection)`     | 批量删除      |query.executeUpdate()` (JPQL 删除  |
| **SQL**   | `newQuery("SQL", "SQL语句")`          | 执行原生 SQL  | createNativeQuery |




持久化机制：

JPA：强调与关系型数据库的集成，提供实体管理器（EntityManager）用于持久化对象的生命周期管理。
JDO：提供 PersistenceManager，它不仅支持关系型数据库的持久化，还支持多种存储后端，甚至是文件系统、NoSQL 数据库等