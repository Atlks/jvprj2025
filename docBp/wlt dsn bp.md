

2. API 设计
   2.1 账户管理
   创建钱包账户
   📌 用户注册后，可创建钱包账户

http
复制
编辑
POST /wallets
Content-Type: application/json
Authorization: Bearer {token}
json
复制
编辑
{
"userId": "12345",
"currency": "USD"
}
✅ 返回：

json
复制
编辑
{
"walletId": "wlt_001",
"userId": "12345",
"balance": 0.00,
"currency": "USD",
"status": "ACTIVE"
}
获取钱包信息
h
复制
编辑
GET /wallets/{walletId}
Authorization: Bearer {token}
✅ 返回：

json
复制
编辑
{
"walletId": "wlt_001",
"userId": "12345",
"balance": 100.00,
"currency": "USD",
"status": "ACTIVE"
}
冻结 / 解冻钱包
http
复制
编辑
PATCH /wallets/{walletId}
Content-Type: application/json
Authorization: Bearer {token}
json
复制
编辑
{
"status": "FROZEN"
}
✅ 返回：

json
复制
编辑
{
"walletId": "wlt_001",
"status": "FROZEN"
}
2.2 资金流动
充值（Deposit）
http
复制
编辑
POST /wallets/{walletId}/deposit
Content-Type: application/json
Authorization: Bearer {token}
json
复制
编辑
{
"amount": 50.00,
"currency": "USD",
"paymentMethod": "credit_card",
"transactionId": "txn_abc123"
}
✅ 返回：

json
复制
编辑
{
"transactionId": "txn_abc123",
"walletId": "wlt_001",
"type": "DEPOSIT",
"amount": 50.00,
"status": "COMPLETED"
}
提现（Withdraw）
http
复制
编辑
POST /wallets/{walletId}/withdraw
Content-Type: application/json
Authorization: Bearer {token}
json
复制
编辑
{
"amount": 30.00,
"currency": "USD",
"paymentMethod": "bank_transfer",
"transactionId": "txn_xyz789"
}
✅ 返回：

json
复制
编辑
{
"transactionId": "txn_xyz789",
"walletId": "wlt_001",
"type": "WITHDRAW",
"amount": 30.00,
"status": "PENDING"
}
转账（Transfer）
http
复制
编辑
POST /wallets/{walletId}/transfer
Content-Type: application/json
Authorization: Bearer {token}
json
复制
编辑
{
"toWalletId": "wlt_002",
"amount": 20.00,
"currency": "USD",
"transactionId": "txn_transfer_001"
}
✅ 返回：

json
复制
编辑
{
"transactionId": "txn_transfer_001",
"fromWalletId": "wlt_001",
"toWalletId": "wlt_002",
"amount": 20.00,
"status": "COMPLETED"
}
2.3 交易记录
查询交易历史
http
复制
编辑
GET /wallets/{walletId}/transactions?startDate=2024-01-01&endDate=2024-02-01
Authorization: Bearer {token}
✅ 返回：

json
复制
编辑
[
{
"transactionId": "txn_abc123",
"walletId": "wlt_001",
"type": "DEPOSIT",
"amount": 50.00,
"status": "COMPLETED",
"timestamp": "2024-01-10T10:00:00Z"
},
{
"transactionId": "txn_xyz789",
"walletId": "wlt_001",
"type": "WITHDRAW",
"amount": 30.00,
"status": "PENDING",
"timestamp": "2024-01-12T15:30:00Z"
}
]
查询单个交易详情
http
复制
编辑
GET /wallets/{walletId}/transactions/{transactionId}
Authorization: Bearer {token}
✅ 返回：

json
复制
编辑
{
"transactionId": "txn_abc123",
"walletId": "wlt_001",
"type": "DEPOSIT",
"amount": 50.00,
"status": "COMPLETED",
"timestamp": "2024-01-10T10:00:00Z"
}
2.4 安全性
JWT 认证
所有 API 需要 JWT 令牌（Authorization Header）。
POST /login 获取 Token，然后 所有请求需携带 Authorization: Bearer {token}。
防止重复支付
交易 ID transactionId 需唯一，避免重复请求导致的双重支付。
Idempotency Key 机制：客户端可提供 X-Idempotency-Key 头部，确保相同请求不会重复执行。
防止欺诈
KYC 认证（Know Your Customer）：绑定实名信息、银行卡验证。
风控（Risk Control）：对异常提现 / 频繁交易进行风控分析。
总结
✅ 核心 API 设计：

账户管理（创建、查询、冻结）。
资金流动（充值、提现、转账）。
交易记录（历史查询、交易详情）。
安全认证（JWT、风控、KYC）。
🚀 这些规范适用于 电子钱包、数字货币、虚拟账户 等场景，确保 安全、可靠、易扩展。