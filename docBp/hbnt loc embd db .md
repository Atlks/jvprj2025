


推荐嵌入式数据库（可与 Hibernate 配合开发测试或本地运行）：
推荐程度	数据库	理由
⭐⭐⭐⭐⭐	H2	Hibernate 官方支持，兼容性最佳，SQL 方言接近 PostgreSQL/MySQL，可用于开发、测试
⭐⭐⭐⭐	HSQLDB	稳定，开源，Hibernate 兼容性好，适合教学和嵌入式用途
⭐⭐⭐	SQLite	轻量级、跨平台、C 编写，需要自定义方言，但适合移动端或嵌入式部署
⭐⭐	Derby	原是 Oracle JavaDB，自带于 JDK 旧版本中，适合小型 Java 项目
