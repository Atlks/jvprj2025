



JPA 的 UPDATE 接口
JPA 没有专门的 update() 方法，因为 JPA 的 实体管理器 (EntityManager) 会自动检测对象的变化，并在事务提交时执行 UPDATE 语句。

🔹 1️⃣ 使用 merge() 进行更新
在 JPA 中，merge() 用于更新游离状态的对象：

java
Copy
Edit
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

// 先查询出对象
User user = em.find(User.class, 1L);
user.setName("Alice Updated"); // 修改属性

// 使用 merge() 进行更新
User updatedUser = em.merge(user);

em.getTransaction().commit();
em.close();
🔹 merge() 适用于：

更新一个**游离（detached）**对象（不在 EntityManager 作用域内）。
适用于 REST API 接收的 DTO 对象 需要合并到数据库中。




在 JPA 中，em.find(User.class, 1L) 获取的对象是 托管（Managed）状态，而不是游离（Detached）状态。
当 EntityManager 关闭后，这个对象才会变成 游离态