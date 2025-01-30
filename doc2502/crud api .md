



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
