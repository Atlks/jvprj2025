


我使用orm代替dao可以吗
ChatGPT said:

可以的！使用 ORM（Object-Relational Mapping）完全可以代替手写 DAO 层，尤其是在 小型到中型项目 或 开发速度优先 的场景下，非常常见且高效。


ORM（如 Hibernate / JPA / Exposed / TypeORM）本身就提供了：

    数据模型的定义（实体类）

    查询、插入、更新等通用方法

    事务控制

    懒加载、关系映射等高级特性

所以你可以直接用 ORM 提供的接口进行数据库操作，不再需要单独封装一个 DAO 层。