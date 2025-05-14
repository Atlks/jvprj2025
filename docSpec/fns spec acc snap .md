

账户快照表，这个有规范吗


ChatGPT 说：
账户快照表（Account Snapshot Table）虽然没有全球统一的“标准规范”，但在金融核心系统中是高度通用的设计模式，广泛应用于银行、证券、支付等领域。下面是行业通用设计规范和最佳实践，适用于核心账务系统的对账、审计、快照分析。

✅ 为什么要有账户快照表？
用于每日结息、账务核对（EOD 对账）

提供任意时点余额查询能力（如 T-1 日余额）

提升审计追踪能力（不可篡改的账务痕迹）

历史余额重建，供风控/统计使用

🏛 常见设计规范（金融行业通用结构）
字段名	类型	说明
id	bigint / UUID	主键
account_id	varchar	对应账户ID（外键）
snapshot_date	date	快照日期（通常为日终）
balance	decimal(18,2)	账户余额
available_balance	decimal(18,2)	可用余额（= 余额 - 冻结）
frozen_balance	decimal(18,2)	冻结金额
currency	varchar(3)	货币代码
status	varchar	快照状态（如 SUCCESS）
created_at	datetime	创建时间
source_txn_id	varchar	来源交易或批次号（可选）
memo	varchar	备注（可选）

🔁 一般按账户+日期做唯一索引，避免重复快照

🧠 快照生成方式
✅ 1. 按日快照（EOD）
每日凌晨由任务调度执行（如 Quartz / Spring Batch）

从账户余额表复制当前状态

✅ 2. 按交易事件实时生成（可选）
每笔大额变动后生成

适用于对账频繁的交易所、券商系统

🛠 示例 SQL（每日快照逻辑）
sql
复制
编辑
INSERT INTO account_snapshot (account_id, snapshot_date, balance, available_balance, frozen_balance, currency, created_at)
SELECT
account_id,
CURRENT_DATE,
balance,
balance - frozen_balance,
frozen_balance,
currency,
NOW()
FROM account
WHERE status = 'ACTIVE';
🧾 与账户主表的关系
表	功能
account	当前实时账户信息（主表）
account_snapshot	每日历史快照（辅助审计）

🔒 安全与合规要求
快照记录必须不可变更（insert-only）

可开启分区表按日期存储，方便归档与性能提升

配合审计系统（如 Kafka、CDC）确保数据可回溯