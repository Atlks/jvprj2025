

openbank obie  Account实体 ，交易实体，还有哪些实体


ChatGPT 说：
在 Open Banking Implementation Entity（OBIE）的 Read/Write Data API 规范中，除了您提到的 Account（账户） 和 Transaction（交易） 实体外，还定义了多个关键实体，用于支持账户信息服务（AIS）、支付发起服务（PIS）和资金确认服务（COF）等功能。

以下是 OBIE 规范中主要的实体及其简要说明：

📘 主要实体列表
Account（账户）

描述银行账户的详细信息，如账户类型、币种、账户状态等。

用于账户信息服务（AIS）。

Transaction（交易）

记录账户的交易明细，包括金额、交易日期、交易描述等。

与账户实体关联，用于展示账户的交易历史。

Statement（对账单）

提供账户在特定期间的对账单信息。

包括开始日期、结束日期、创建日期等字段。
openbankinguk.github.io

Balance（余额）

表示账户的当前余额信息。

包括可用余额、账面余额等。
openbankinguk.github.io

Beneficiary（受益人）

存储用户保存的受益人信息。

用于快速发起支付。

Direct Debit（直接借记）

描述账户的直接借记授权信息。

包括授权状态、收款人信息等。

Standing Order（定期付款指令）

定义定期自动支付的指令。

包括支付频率、下次支付日期等。

Scheduled Payment（预定支付）

表示已安排但尚未执行的支付。

包括预定支付日期、金额等。

Consent（授权）

记录用户对第三方提供者（TPP）的授权信息。

用于控制数据访问权限。

Payment Initiation（支付发起）

处理支付请求的实体。

包括支付金额、收款人账户信息等。

Funds Confirmation（资金确认）

用于确认账户中是否有足够资金进行支付。

包括付款人账户信息、确认结果等。

Event Notification（事件通知）

用于通知 TPP 有关账户或支付的事件。

包括事件类型、相关资源等。

这些实体共同构成了 OBIE 的数据模型，支持开放银行的各项功能。开发者可以根据具体业务需求，选择相应的实体进行集成和开发。

如果您需要某个特定实