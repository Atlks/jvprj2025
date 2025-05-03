


用于 彩票投资的rest url是如何
 
彩票投资的 REST API 设计需要涵盖 投资下单、查询、支付、开奖、中奖查询、兑奖 等功能。以下是基于 RESTful 设计原则的 API 规范。

1. REST 设计原则
   资源风格 URL：使用 名词 作为资源，如 /bets/{betId}。
   HTTP 方法：
   GET：查询投资记录、开奖信息等。
   POST：创建投资单、支付投资单。
   PUT / PATCH：更新投资状态（如支付、取消）。
   DELETE：取消未支付的投资单。
   状态码：
   200 OK：成功。
   201 Created：投资单创建成功。
   400 Bad Request：请求参数错误（如格式错误）。
   401 Unauthorized：未授权（如 JWT 过期）。
   403 Forbidden：权限不足（如非本人投资）。
   404 Not Found：资源不存在（如投资单 ID 错误）。
   409 Conflict：状态冲突（如已支付的单无法取消）。
   500 Internal Server Error：服务器异常。
2. API 设计
   2.1 购彩下单
   创建投资
   📌 用户选择号码并下单

http
复制
编辑
POST /bets
Content-Type: application/json
Authorization: Bearer {token}
📌 请求体：

json
复制
编辑
{
"userId": "12345",
"lotteryType": "PowerBall",
"drawDate": "2025-03-05",
"betNumbers": [
{ "numbers": [5, 12, 23, 34, 45, 9], "betAmount": 2.00 },
{ "numbers": [7, 18, 29, 38, 44, 10], "betAmount": 2.00 }
],
"totalAmount": 4.00,
"currency": "USD",
"paymentMethod": "wallet"
}
✅ 返回：

json
复制
编辑
{
"betId": "bet_001",
"userId": "12345",
"lotteryType": "PowerBall",
"drawDate": "2025-03-05",
"status": "PENDING_PAYMENT",
"totalAmount": 4.00,
"createdAt": "2025-03-03T10:00:00Z"
}
2.2 查询投资记录
获取投资详情
http
复制
编辑
GET /bets/{betId}
Authorization: Bearer {token}
✅ 返回：

json
复制
编辑
{
"betId": "bet_001",
"userId": "12345",
"lotteryType": "PowerBall",
"drawDate": "2025-03-05",
"status": "PAID",
"totalAmount": 4.00,
"betNumbers": [
{ "numbers": [5, 12, 23, 34, 45, 9], "betAmount": 2.00 },
{ "numbers": [7, 18, 29, 38, 44, 10], "betAmount": 2.00 }
],
"paymentMethod": "wallet",
"createdAt": "2025-03-03T10:00:00Z",
"updatedAt": "2025-03-03T10:30:00Z"
}
查询投资历史
http
复制
编辑
GET /bets?userId=12345&status=PAID&page=1&size=10
Authorization: Bearer {token}
✅ 返回：

json
复制
编辑
{
"bets": [
{
"betId": "bet_001",
"lotteryType": "PowerBall",
"drawDate": "2025-03-05",
"status": "PAID",
"totalAmount": 4.00,
"createdAt": "2025-03-03T10:00:00Z"
},
{
"betId": "bet_002",
"lotteryType": "MegaMillions",
"drawDate": "2025-03-07",
"status": "PENDING_PAYMENT",
"totalAmount": 6.00,
"createdAt": "2025-03-04T12:00:00Z"
}
],
"pagination": {
"currentPage": 1,
"totalPages": 3,
"totalBets": 25
}
}
2.3 支付投资单
http
复制
编辑
POST /bets/{betId}/pay
Content-Type: application/json
Authorization: Bearer {token}
📌 请求体：

json
复制
编辑
{
"paymentMethod": "wallet",
"transactionId": "txn_123456"
}
✅ 返回：

json
复制
编辑
{
"betId": "bet_001",
"status": "PAID",
"paymentMethod": "wallet",
"transactionId": "txn_123456",
"paidAt": "2025-03-03T10:15:00Z"
}
2.4 取消投资
http
复制
编辑
DELETE /bets/{betId}
Authorization: Bearer {token}
✅ 返回：

json
复制
编辑
{
"betId": "bet_001",
"status": "CANCELLED",
"cancelledAt": "2025-03-03T10:20:00Z"
}


总结
✅ 核心 API 设计：

投资管理（创建、查询、）。
 
 
🚀 这些 API 适用于 线上彩票系统，确保 结构清晰、易扩展、符合 REST 设计原则。