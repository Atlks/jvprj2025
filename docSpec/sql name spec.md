


SQL 命名规范（小写加下划线）

CamelCaseToUnderscoresNamingStrategy   区别  PhysicalNamingStrategyStandardImpl


ChatGPT 说：
CamelCaseToUnderscoresNamingStrategy 和 PhysicalNamingStrategyStandardImpl 都是 Hibernate 的 物理命名策略实现（PhysicalNamingStrategy），用于控制实体字段和数据库表/列之间的映射规则，但它们的行为非常不同：

✅ 区别总结：
特性	PhysicalNamingStrategyStandardImpl	CamelCaseToUnderscoresNamingStrategy
是否自动转换字段名	❌ 不转换，保留实体类中的字段名	✅ 自动将驼峰命名转换为下划线命名
userName → user_name	❌ 不变，仍为 userName	✅ 转为 user_name
表名 UserAccount → user_account	❌ 保留 UserAccount	✅ 转为 user_account
常用于	想严格控制表名/列名时	遵循 SQL 命名规范（小写加下划线）
Hibernate 默认策略	❌ 不是	✅ 是 Spring Boot 默认策略（SpringPhysicalNamingStrategy 派生）

