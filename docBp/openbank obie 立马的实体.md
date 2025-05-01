

在 Open Banking UK (OBIE) 标准中：

**Transaction** 是 账户信息接口（AIS, Account Information Services） 中的实体，表示账户中实际发生的账务交易记录。

**PaymentTransaction**（或支付相关实体，如 DomesticPayment, InternationalPayment）属于 支付发起接口（PIS, Payment Initiation Services），表示一个待发起或已发起的支付请求及其处理状态。


✅ 对比：Transaction vs PaymentTransaction
属性/字段	Transaction（AIS）	PaymentTransaction（PIS）
来源	/accounts/{AccountId}/transactions	/domestic-payments, /international-payments 等
用途	查询银行账户的历史/实时交易记录	发起一个支付请求，包含指令与处理状态
代表含义	实际发生的账务交易（已记账或待处理）	用户发起的支付请求（可能未成功）


✅ Transaction（账户交易）典型字段（简化）
json
复制
编辑
{
"TransactionId": "123456",
"AccountId": "A123",
"TransactionReference": "PAYPAL123",
"Amount": {
"Amount": "50.00",
"Currency": "GBP"
},
"CreditDebitIndicator": "Credit",
"Status": "Booked",
"BookingDateTime": "2024-10-01T10:00:00Z",
"TransactionInformation": "Payment from John"
}



✅ PaymentTransaction（支付交易）典型字段（简化）
关键字段：
PaymentId

InstructionIdentification, EndToEndIdentification（指令标识）

InstructedAmount（用户请求的支付金额）

DebtorAccount, CreditorAccount（付款人/收款人账户）

Status（支付状态，见之前的枚举）

CreationDateTime

RemittanceInformation


✅ 核心区别总结：
维度	Transaction（AIS）	PaymentTransaction（PIS）
数据来源	银行账务交易记录	第三方发起的支付请求
是否实际记账	是（如 Booked）	不一定（可能还未授权、处理中、被拒）
关联账户	账户 ID (AccountId)	付款人账户 (DebtorAccount)
金额字段	Amount	InstructedAmount
身份标识字段	TransactionId, TransactionReference	PaymentId, InstructionIdentification
主要状态字段	Status: Booked / Pending / Cancelled	Status: AcceptedXXX / Pending / Rejected



✅ OBIE v3.1+ 的标准命名是：
InstructedAmount
它是 OBIE PIS（Payment Initiation Service）接口中的标准字段名称，表示用户发起的付款金额，结构如下


❌ amount 是非标准写法
如果你看到字段名是 amount，很可能是：

某些银行的非标准实现或简化版；

是开发者为前端封装的 DTO；

属于 Transaction（账户信息接口）中的字段，而非支付发起接口。


所以你应该用 InstructedAmount 作为字段名，amount 和 currency 是它的子字段



为什么要命名为 InstructedAmount


ChatGPT 说：
OBIE 将字段命名为 InstructedAmount 而不是简单的 amount，是有明确语义目的的，原因主要有以下几点：

✅ 1. 强调“用户指令”而非实际结算金额
InstructedAmount 是用户 “指令中要求支付的金额”。

它 未必等于 实际到账金额（例如涉及手续费、货币转换、拒绝等情况）。

银行或支付系统有可能最终执行的是不同的金额（例如部分支付、退款等）。




✅ 2. 区分上下游的金额含义，防止歧义
在金融系统中，同一个“金额”可能有多种语义：

名称	语义说明
InstructedAmount	用户发起支付时的“指令金额”
DebitedAmount	实际从付款人账户扣除的金额（包含手续费等）
CreditedAmount	收款人实际收到的金额
Amount	太模糊，可能指代任何一个




✅ 3. 对齐国际标准（如 ISO 20022）
OBIE 的许多字段命名是参考国际金融通信标准 ISO 20022，在该标准中：

“InstructedAmount” 就是指付款发起方**“指示的金额”**；

实际金额、记账金额等都有各自专业字段名（比如 SettledAmount、InterbankAmount 等）。



✅ 4. 便于未来扩展和审计
如果只用 amount 字段，很难在未来加上更多种金额类型。而用 InstructedAmount，可以并列加入：

EstimatedDebitedAmount

SettledAmount

FeeAmount

等等，字段语义清晰、扩展性强。



📌 说明
枚举值	通常字段名（JSON/XML）	说明
INSTRUCTED	InstructedAmount	用户发起的请求金额
DEBITED	DebitedAmount	实际扣款金额
CREDITED	CreditedAmount	收款人实际到账金额
FEE	FeeAmount	手续费金额
ESTIMATED_DEBITED	EstimatedDebitedAmount	预估扣款金额（如用于提示）
SETTLED	SettlementAmount	清算金额
EXCHANGED	ExchangeAmount	外币交易中兑换的金额