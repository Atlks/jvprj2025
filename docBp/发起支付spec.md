

PIS（Payment Initiation Service 有哪些api和实体


ChatGPT 说：
在 Open Banking（基于 PSD2 标准）的环境中，PIS（Payment Initiation Service）支付发起服务 通过一组标准化的 API 与银行（ASPSP，Account Servicing Payment Service Provider）交互，完成发起支付的流程。

以下是 PIS 的常见 API 和核心实体对象 的总结：

✅ 一、PIS 常见 API 接口（以欧盟 Berlin Group 或 UK OBIE 标准为基础）
POST /payments

创建一个支付请求（初始化支付）

提供付款人账户、收款人信息、金额、货币、用途等

响应中包含 paymentId 和授权跳转链接

GET /payments/{paymentId}

查询支付状态

用于查看支付是否授权、执行中、成功或失败

GET /payments/{paymentId}/status

获取支付当前状态（简化版）

GET /payments/{paymentId}/details（可选）

获取支付的详细信息，如交易信息等

PUT /payments/{paymentId}/authorisation/{authId}

完成授权步骤（SCA：强客户认证）

支付可能需要用户通过 2FA、动态密码等方式确认

✅ 二、PIS 涉及的主要实体对象（数据结构）
1. PaymentInitiationRequest（支付发起请求）

2. PaymentStatus（支付状态）
   状态值常见包括：

RCVD：已接收

ACTC：接受并开始执行

ACSP：已接收、待结算

ACSC：成功结算

RJCT：被拒绝

PDNG：等待授权

CANC：已取消
CANC：已取消



3. Links / Redirects
   用于引导用户跳转至银行界面进行授权：

json
复制
编辑
{
"_links": {
"scaRedirect": {
"href": "https://bank.com/authorize?paymentId=abc123"
}
}
}
✅ 三、PIS 的典型调用流程（简略）
用户在第三方平台发起支付请求

第三方（PISP）调用 POST /payments 创建支付

得到授权跳转链接，引导用户跳转到银行进行 SCA 授权

银行确认并返回授权结果

第三方查询支付状态，确认支付是否成功

