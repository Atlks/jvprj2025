


BalanceType = "ClosingBooked" 通常表示总余额。

BalanceType = "InterimAvailable" 表示可立即使用的余额（不含冻结部分）。


🟡 为什么没有叫 availableBalance？
OBIE 遵循的是 ISO 20022 + UK 扩展规范，其风格是用结构化字段（如 BalanceType + Amount）表示不同类型的余额，而不是简单字段名（如 availableBalance）。这比传统 API 更清晰、可扩展。

✅ BalanceType 字段详解（取自 OBIE 规范）：
BalanceType	含义	用途说明
ClosingBooked	📌总余额（已记账）	💡通常用于表示账户的总余额
InterimBooked	当前可用余额（已记账）	实时变化的余额
InterimAvailable	可用余额（排除未清算款项）	ATM 或在线银行常见余额
Expected	预计余额	包含预计交易影响后的余额
ForwardAvailable	未来可用余额	包含未来交易后的可用余额



ISO 20022 如何表示总余额 有效余额，冻结金额


ChatGPT 说：
在 ISO 20022 中，余额的表示是通过 Balance 类型（BalanceTypeCode） 来表达的，而不是通过字段名如 availableBalance、frozenAmount 等。不同的余额类型通过枚举代码来区分，下面是你关心的几个关键类型的标准表示方法：

✅ ISO 20022 中余额类型的标准表示
表示内容	ISO 20022 的 BalanceTypeCode	说明
总余额 (Total Balance)	CLBD (ClosingBooked)	账户的总账余额，即账面余额（包含未结算交易）
有效余额 / 可用余额 (Available Balance)	ITAV (InterimAvailable)	可立即支配的余额，排除冻结金额和挂账金额
冻结金额 (Blocked / Frozen Funds)	XPCD (Expected) + 明细说明 或 BLCK（非标准扩展）	ISO 标准中没有单独定义“冻结金额”，一般通过交易挂账/预授权形式间接表达



❗ 冻结金额的处理
ISO 20022 没有一个专门的 frozenAmount 字段或标准类型，但可以通过：

授权中交易（pending） 暂时冻结一部分余额（即 InterimBooked 或 Expected 类型的交易）。

行内系统扩展 使用自定义代码如 BLCK（Blocked）或 FRZN。



不是在obie和iso20022都没有 AvailableBalance 字段吗
ChatGPT 说：
你提得非常好，是的，在 OBIE（Open Banking UK）和 ISO 20022 标准中确实没有直接叫做 AvailableBalance 的字段，但两者都提供了等效的表达方式。


冻结金额	OBIE 暂无标准字段（可自定义扩展