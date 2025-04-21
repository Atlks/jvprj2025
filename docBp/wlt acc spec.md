


钱包（Wallet）或账户（Account）在金融系统中，确实存在一些标准化字段的约定，尤其是在**金融合规、支付系统、电子钱包平台（如 PayPal、Stripe、央行数字货币 DC/EP）**等场景中。


ISO 20022	账户、
✅ 一、FAPI / OpenBanking 账户信息标准
1. 标准来源
   FAPI（Financial-grade API）：由 OpenID Foundation 制定，侧重于身份认证与安全访问金融数据。



1. 标准来源
   FAPI（Financial-grade API）：由 OpenID Foundation 制定，侧重于身份认证与安全访问金融数据。

Open Banking UK / Berlin Group / Australia CDR 等：是具体国家/地区的开放金融实现标准，对账户字段有明确定义。



✅ 账户与钱包区别说明
账户（Account）：更通用，包括银行账户、证券账户等，标准中使用更广泛。

钱包（Wallet）：常用于电子支付领域（e-wallet），可映射为账户的一种特殊类型（如 CACC/CASH）。



FAPI  的账户信息呢 字段规定


ChatGPT 说：
FAPI（Financial-grade API）标准本身是一个安全性和身份验证/授权的框架，并不直接规定业务字段（如账户字段），但它经常与 Open Banking（例如英国、欧洲、澳洲的实现）结合使用。这些 Open Banking 实现通常在 FAPI 安全框架下运行，并提供账户信息（Account Information Service - AIS）的结构规范。

✅ FAPI 使用场景中账户信息字段参考（基于 OpenBanking UK）
这些字段是在符合 FAPI Profile 的 API 接口中传输的账户信息。

标准字段（OpenBanking Account Info 示例）

字段	类型	描述
AccountId	string	账户唯一标识
Currency	string	币种代码（ISO 4217）
AccountType	string	账户类型（个人、企业等）
AccountSubType	string	子类型（活期、储蓄等）
Nickname	string	账户昵称
Name	string	账户持有人姓名
IBAN	string	国际银行账户号码
BBAN	string	基本银行账户号码
SortCode	string	Sort code（主要用于英国）
MaskedPAN	string	掩码账号（用于卡号类账户）
SchemeName	string	账户识别方案（如 IBAN、SortCodeAccountNumber）
Identification	string	标识符
SecondaryIdentification	string	附加识别（如公司账户附属识别码）





OpenBanking Accounts 不包含余额吗


ChatGPT 说：
很好的问题！在 OpenBanking 标准中，Accounts 实体 本身不包含余额信息，但余额信息是通过单独的 API 端点来获取的，返回的是一个叫做 Balance 的结构。

✅ OpenBanking 中余额信息的获取方式
账户基本信息：通过 /accounts 或 /accounts/{AccountId} 获取

账户余额：通过 /accounts/{AccountId}/balances 获取
