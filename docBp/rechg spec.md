

我要给账户充值，有没有什么规范和实体说明


ChatGPT 说：
在开放银行（Open Banking）或金融系统设计中，“给账户充值” 通常属于 支付发起（PIS, Payment Initiation Service） 或 资金转入（Fund Transfer / Top-up） 类别。以下是有关账户充值的常见 规范定义 和 实体建模建议，包括标准中的说明。

🧭 所属领域：Payment Initiation（PIS）
充值属于一种由用户授权的、向其银行账户发起的资金注入行为。在 OBIE、Berlin Group、STET 等标准中，这通常等价于：

发起一笔从外部账户（如卡、电子钱包、另一个银行账户）向用户目标账户的支付


🔐 与开放银行标准的关系
OBIE（UK）定义：
/domestic-payment-consents

/domestic-payments

这些 endpoint 可用于模拟“从外部账户充值到银行账户”的流程。




3. 状态机设计
   状态	含义
   Pending	用户提交充值请求
   Processing	正在验证或第三方处理中
   Confirmed	充值到账并记账完成
   Failed	充值失败或被拒绝
   Cancelled	用户主动取消或系统超时

/domestic-payment-consents 和 /domestic-payments 是 OBIE（Open Banking Implementation Entity）标准中用于处理支付发起流程的两个关键 API，它们承担不同的职责，通常按顺序使用。

✅ 主要区别总结
API 路径	作用	发起者	说明
/domestic-payment-consents	创建并获取用户授权的支付“意图”（Consent）	第三方支付服务商（PISP）	是“我要发起一笔支付”的声明，包含金额、收款人等信息，但尚未执行
/domestic-payments	发起实际支付（执行）	第三方服务（PISP），在用户授权后	在用户授权通过后，基于 ConsentId 执行支付


GET /list_payments   列出所有支付单子
POST /payments

创建一个支付请求（初始化支付）

提供付款人账户、收款人信息、金额、货币、用途等

响应中包含 paymentId 和授权跳转链接

GET /payments?paymentId=xxx



后台会生成一个trx单子。。。



查询支付状态

用于查看支付是否授权、执行中、成功或失败

GET /payments/{paymentId}/status

获取支付当前状态（简化版）

GET /payments/{paymentId}/details（可选）

获取支付的详细信息，如交易信息等

PUT /payments/{paymentId}/authorisation/{authId}

完成授权步骤（SCA：强客户认证）

支付可能需要用户通过 2FA、动态密码等方式确认

