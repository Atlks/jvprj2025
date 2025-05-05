

❌ Hibernate update 的问题：
不可控： 没有版本号历史，谁改了什么表无法追踪

不安全： 在生产环境很可能造成结构错乱或数据丢失

不支持删除字段/索引等复杂操作

CI/CD 过程无法做迁移验证



✅ Flyway 的优势
特性	Flyway	Hibernate DDL auto
版本控制	✅ 明确 V1、V2 等版本	❌ 无版本概念
可追踪性	✅ 谁写的脚本一目了然	❌ 自动处理不透明
回滚能力	✅ 手动控制版本回退	❌ 无法回滚
生产环境安全性	✅ 安全、可审核	❌ 高风险
自动化测试	✅ 可集成到 CI/CD 流程	❌ 不适用于测试校验
灵活性	✅ 支持 SQL/Java 方式	⚠️ 仅基于实体类映射


✅ 推荐做法
环境	Hibernate DDL auto	Flyway 用法
开发环境	update 或 create	用 Flyway 编写脚本辅助开发
测试/生产	validate	✅ 仅使用 Flyway 执行迁移


Hibernate 自动建表适合开发用，Flyway 适合团队协作和生产上线，是“可控、安全、有版本”的数据库迁移方式。



是的，Flyway 默认就是为了防止你重复执行相同的迁移脚本，所以它会记录已执行过的脚本，并确保每个迁移只跑一次。

✅ Flyway 如何防止重复执行？
Flyway 在数据库中自动创建一个表（默认叫做 flyway_schema_history），记录已执行的迁移脚本，例如：


