



如果你想 控制 SQL 顺序，可以在 save() 或 update() 之后 调用 flush()：

ession.save(entity1);
session.flush(); // 立即执行 SQL

session.save(entity2);
session.flush(); // 立即执行 SQL
作用：防止 Hibernate 调整 SQL 顺序，确保代码执行顺序与 SQL 顺序匹配。