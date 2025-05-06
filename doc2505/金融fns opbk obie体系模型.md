
Open Banking Read-Write API - Resources and Data Models
AISP    acc mdl
PISP    pay mdl
CBPII   card mdl
Event Notifications

------------------
AISP Resources and Data Models - v3.1.2
Resources accessed using the aisp PSD2 role are detailed here:

Account Access Consents
Accounts
Balances
Transactions
Beneficiaries
Direct Debits
Standing Orders
Products
BCA Product Data Model
PCA Product Data Model
Other Product Data Model
Offers
Parties
Scheduled Payments
Statements   对账单


✅ 账户相关核心模型：
名称	作用	举例
Account Access Consents	用户授权第三方访问其账户信息的许可模型。	用户授权理财 APP 读取其账户交易，生成一个有效期7天的 consent
Accounts	账户基本信息，如账户号码、类型、币种、状态。	读取用户有哪几个账户、账户ID、IBAN 等
Balances	各类余额：账面余额、可用余额、冻结余额等。	当前账户可用余额是多少
Transactions	交易明细，逐笔记录账户的收支变化。	显示“2024-05-01 Starbucks -$3.50” 这样的明细
Statements	银行生成的周期性对账单记录。	获取“2024年1月对账单”，带起始/期末余额和汇总信息




✅ 收付款人 / 关联方模型：
名称	作用	举例
Beneficiaries	用户设置的收款人或常用收款账户。	添加过的“张三银行账户”、“水电费收款人”
Parties	与账户相关联的客户或法人信息。	某账户的开户人是“John Smith Ltd.”
Offers	银行或账户提供的促销或优惠服务。	显示“申请此卡可返现5%”


✅ 定期/自动扣款模型：
名称	作用	举例
Direct Debits	自动从账户扣款的授权项目（如水电费、Netflix）。	“每月5日自动扣光纤宽带费用”
Standing Orders	用户设置的定期转账指令。	“每月1号向租房人汇款1000元”
Scheduled Payments	已排定执行的单笔付款指令。	“设定在2024-06-01支付信用卡账单”



✅ 产品模型（银行提供的账户类型说明）：
名称	作用	举例
Products	银行的所有金融产品（账号类型/信用卡/理财等）概览。	包括储蓄账户、企业账户、青少年账户等产品信息
BCA Product Data Model
(Business Current Account)	企业账户产品的详细数据模型。	显示“企业账户年费£10、每天转账限额£20k”
PCA Product Data Model
(Personal Current Account)	个人账户产品的详细数据模型。	显示“此账户无月费、可透支£200”
Other Product Data Model	非标准账户产品的说明。	银行推出的特殊理财产品、联名信用卡等


OBIE 这些模型构成了一个开放银行 API 的“账户视图 + 操作视图 + 产品信息视图”，让第三方能合法、结构化地读取或管理用户银行账户相关信息和服务。


----------------
Endpoints
The API endpoints for these resources, and their mandatory/conditional/optional status are given below.

Resource	Endpoints	Mandatory?
account-access-consents	POST /account-access-consents	Mandatory
account-access-consents	GET /account-access-consents/{ConsentId}	Mandatory
account-access-consents	DELETE /account-access-consents/{ConsentId}	Mandatory
accounts	GET /accounts	Mandatory
accounts	GET /accounts/{AccountId}	Mandatory
balances	GET /accounts/{AccountId}/balances	Mandatory
balances	GET /balances	Optional
transactions	GET /accounts/{AccountId}/transactions	Mandatory
transactions	GET /transactions	Optional
beneficiaries	GET /accounts/{AccountId}/beneficiaries	Conditional
beneficiaries	GET /beneficiaries	Optional
direct-debits	GET /accounts/{AccountId}/direct-debits	Conditional
direct-debits	GET /direct-debits	Optional
standing-orders	GET /accounts/{AccountId}/standing-orders	Conditional
standing-orders	GET /standing-orders	Optional
products	GET /accounts/{AccountId}/products	Conditional
products	GET /products	Optional
offers	GET /accounts/{AccountId}/offers	Conditional
offers	GET /offers	Optional
parties	GET /accounts/{AccountId}/parties	Conditional
parties	GET /accounts/{AccountId}/party	Conditional
parties	GET /party	Conditional
scheduled-payments	GET /accounts/{AccountId}/scheduled-payments	Conditional
scheduled-payments	GET /scheduled-payments	Optional
statements	GET /accounts/{AccountId}/statements	Conditional
statements	GET /accounts/{AccountId}/statements/{StatementId}	Conditional
statements	GET /accounts/{AccountId}/statements/{StatementId}/file	Optional
statements	GET /accounts/{AccountId}/statements/{StatementId}/transactions	Conditional
statements	GET /statements	Optional




PISP Resources and Data Models - v3.1.2
Resources accessed using the pisp PSD2 role are detailed here:

Domestic Payment Consents
Domestic Payments
Domestic Scheduled Payment Consents
Domestic Scheduled Payments
Domestic Standing Order Consents
Domestic Standing Orders
International Payment Consents
International Payments
International Scheduled Payment Consents
International Scheduled Payments
International Standing Order Consents
International Standing Orders
File Payment Consents
File Payments