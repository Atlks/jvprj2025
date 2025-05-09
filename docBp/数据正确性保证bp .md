

事务

使用幂等性 + 状态记录，避免重复执行

由于 Serverless 可能重试某个函数，所以你需要确保你的业务逻辑是“幂等的”。

📌 举例：

    通过 Redis/MySQL 记录处理状态：比如事务 ID、执行状态等

    重复调用不会影响结果（比如扣款不能重复）

Serverless 架构下，由于函数（如 AWS Lambda）是短暂、无状态、不可预测横向扩展的，所以传统的数据库事务机制（如 MySQL 的 BEGIN/COMMIT）并不适合直接使用在 Serverless 中的跨操作逻辑中。

但这并不代表无法处理事务，只是解决方式会 更偏向事件驱动、幂等性和补偿机制。


🔹 3. 使用 Saga 模式（推荐）

Saga 是一种将大事务拆成多个小事务，并用“补偿动作”来保证整体一致性的模式。

📌 流程：

步骤1：扣减余额成功
步骤2：扣减库存失败
--> 回滚步骤1（执行补偿逻辑，如退款）

你可以用：

    自己实现 Saga 编排函数（中控 Lambda）

    使用 AWS Step Functions 来编排步骤 + 补偿流程


✅ 总结：Serverless 中事务的替代方案
方法	场景	特点
数据库事务	单函数操作数据库	简单可靠
幂等机制	避免重复执行	使用唯一请求 ID
Saga 模式	跨服务、跨资源事务	支持失败补偿
Step Functions	流程编排和补偿事务	Serverless 原生
分布式事务（XA）	❌ 不推荐	不适配函数式架构