


在现代开发中，Serverless 模式 + ORM 确实可以实现一种极简但高效的架构：
✅ 最简单模式的核心理念：

    ❗「只写 Service，其他都交给平台 / 框架代劳



🧩 架构简化对比如下：
传统架构	Serverless 极简架构
Controller	❌ 被 API Gateway 替代
Service（业务逻辑）	✅ 自己写，仅保留业务处理
DAO（数据库访问）	❌ ORM 替代，直接在 service 中操作
Model（数据模型）	✅ ORM 提供
Web Server（如 Spring）	❌ 云函数自带运行环境



✅ 极简模式结构图：

API Gateway → Lambda Function (service logic) → ORM → Database