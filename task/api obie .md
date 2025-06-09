
1. Get Accounts
   用途：列出用户账户信息

Endpoint：

bash
Copy
Edit
GET /accounts
返回内容：账户 ID、货币、账户类型、持有人信息等。

💰 2. Get Balances
用途：查询指定账户的余额

Endpoint：

bash
Copy
Edit
GET /accounts/balances？{AccountId}=xx
说明：

包括多个余额类型（如 Booked、Available、Interim、Expected）

amount 字段可能为负值（如透支）


3. Get Transactions
   用途：获取账户的交易明细（即流水）

Endpoint：

bash
Copy
Edit
GET /accounts/transactions/AccountId=xx
支持参数：

fromBookingDateTime

toBookingDateTime

creditDebitIndicator

返回内容：每笔交易的时间、金额、交易描述、对方账户等


4. Get Statements
   用途：获取账单（Statements）

Endpoint：

bash
Copy
Edit
GET /accounts/statements?{AccountId}=xxx
说明：

通常是银行月账单摘要

通常还需获取 statement 文件内容：

bash
Copy
Edit
GET /accounts/{AccountId}/statements/{StatementId}/file


模块	说明	路径示例
Accounts	用户账户列表	/accounts
Balances	查询余额	/accounts/{AccountId}/balances
Transactions	账户交易流水（账务明细）	/accounts/{AccountId}/transactions
Statements	电子账单	/accounts/{AccountId}/statements


然后用这个 AccountId 去调用：

GET /accounts/{AccountId}/balances —— 获取余额

GET /accounts/{AccountId}/transactions —— 获取流水

GET /accounts/{AccountId}/statements —— 获取账单



在 Open Banking OBIE v3 中，查询某个具体账户的详细信息并没有一个独立的 GET /accounts/{accountId} 端口，而是统一通过：

虽然 OBIE v3 的 GET /accounts 看似会返回“所有账户”，但 并不会返回银行系统中所有账户的信息 —— 只能返回用户已授权访问的账户列表。下面是详细解释。

有个登录的owner 表示筛选



✅ 1. 授权范围限定账户
当用户通过 TPP（第三方提供商）访问数据时：
返回的 access token 中包含 scope 和 account_id 列表，只有这些账户可以被访问

例如，授权后 token payload 可能是：

json
Copy
Edit
{
"sub": "user123",
"accounts": ["acc123", "acc789"],
"scope": "accounts balances transactions",
...


✅ 2. 银行后端校验 Token 权限
银行在处理 GET /accounts 时，会读取 token，仅返回该 token 授权账户的列表。

如果用户只授权了 acc123，那么无论前端请求 GET /accounts 还是 GET /accounts/acc456，银行都会拒绝访问 acc456 的数据。