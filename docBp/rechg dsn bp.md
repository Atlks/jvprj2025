

充值   api要如何设计，，我要充值  充值回调  给用户加钱
 
充值 API 设计需要涵盖 充值下单、支付回调、资金到账 三个主要流程。以下是 RESTful 设计规范，适用于钱包系统或第三方支付接入。

1. 充值 API 设计概述
   用户发起充值 → 生成订单 → 用户支付 → 回调通知 → 资金到账
   采用 RESTful 设计
   需要 JWT 认证，回调需支持 签名验证
   防止重复回调 & 并发处理
   适配 钱包余额管理
2. 充值 API 设计
   2.1 创建充值订单
   📌 用户发起充值，选择支付方式

h
复制
编辑
POST /recharge
Content-Type: application/json
Authorization: Bearer {token}
📌 请求体：

json 
复制
编辑
{
"userId": "12345",
"amount": 100.00,
"currency": "USD",
"paymentMethod": "alipay",
"callbackUrl": "https://example.com/payment/callback"
}
✅ 返回：

json
复制
编辑
{
"rechargeId": "recharge_001",
"userId": "12345",
"amount": 100.00,
"currency": "USD",
"paymentMethod": "alipay",
"status": "PENDING",
"paymentUrl": "https://pay.example.com/123456",
"createdAt": "2025-03-03T10:00:00Z"
}
📌 说明：

paymentUrl 用于跳转到支付页面
callbackUrl 用于接收支付成功通知
2.2 查询充值状态
📌 用户查询充值状态

http
复制
编辑
GET /recharge/{rechargeId}
Authorization: Bearer {token}
✅ 返回：

json
复制
编辑
{
"rechargeId": "recharge_001",
"userId": "12345",
"amount": 100.00,
"currency": "USD",
"status": "SUCCESS",
"paymentMethod": "alipay",
"transactionId": "txn_98765",
"completedAt": "2025-03-03T10:10:00Z"
}
📌 说明：

status：PENDING / SUCCESS / FAILED
transactionId：支付渠道返回的交易号
completedAt：支付完成时间
2.3 支付回调（异步通知）
📌 支付平台通知系统支付成功

http
复制
编辑
POST /recharge/callback
Content-Type: application/json
📌 请求体：

json
复制
编辑
{
"rechargeId": "recharge_001",
"transactionId": "txn_98765",
"amount": 100.00,
"currency": "USD",
"status": "SUCCESS",
"sign": "abcdef123456"
}
✅ 返回：

json
复制
编辑
{
"code": 200,
"message": "success"
}
📌 说明：

status:
SUCCESS 充值成功
FAILED 充值失败
sign 用于验证回调数据是否被篡改
服务器返回 200 OK 确认回调已处理




2.4 资金到账（给用户加钱）
📌 后台收到支付回调后，更新用户钱包余额

http
复制
编辑
POST /wallet/add
Content-Type: application/json
Authorization: Bearer {adminToken}
📌 请求体：

json
复制
编辑
{
"userId": "12345",
"amount": 100.00,
"currency": "USD",
"reason": "recharge",
"referenceId": "recharge_001"
}
✅ 返回：

json
复制
编辑
{
"walletId": "wallet_12345",
"userId": "12345",
"balance": 500.00,
"currency": "USD",
"updatedAt": "2025-03-03T10:12:00Z"
}
📌 说明：

referenceId 关联充值订单，防止重复加钱
防并发处理，确保 同一个 rechargeId 只处理一次
3. 防止重复充值
   ✅ 方案

幂等性检查：数据库 recharge_001 状态是否已更新为 SUCCESS
事务处理：加钱 & 订单状态更新必须在同一事务中执行
签名校验：确保回调数据未被篡改
重复回调应幂等，重复请求时返回相同结果
4. 总结
   ✅ 关键 API
   用户发起充值（POST /recharge）
   查询充值状态（GET /recharge/{id}）
   支付回调（POST /recharge/callback）
   资金到账（POST /wallet/add）
   防止重复充值（幂等性处理）
   📌 这样设计可以 保证安全、数据一致性，并支持多种支付方式 🚀