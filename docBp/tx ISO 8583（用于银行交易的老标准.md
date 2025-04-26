

金融机构规范标准 比较   iso20022,iso8583,obie



SWIFT ISO 20022 (MX)	取代 SWIFT MT 的新一代 XML 格式标准（更结构化、细节丰富）。	银行转账、证券清算
ISO 8583	定义银行卡（ATM、POS）的交易报文格式。	银行卡消费、提现、转账


SEPA (Single Euro Payments Area)	欧洲统一支付区的标准（基于 ISO 20022 XML）。	欧元区内转账、收款
ACH (Automated Clearing House)	美国自动清算中心批量交易标准。	工资发放、水电费扣款




银行账户和开放银行标准

标准/协议	简介	应用
OBIE (Open Banking UK)	英国开放银行标准。	银行 API、第三方支付
Berlin Group PSD2 API	欧洲开放银行标准（统一的账户访问接口）。	欧洲 PSD2 法规合规
FAPI (Financial-grade API)	金融级开放 API 安全框架（基于 OAuth2、OIDC 扩展）。	银行开放接口认证授权



💵 数字货币与区块链标准   标准/协议 简介 应用   ISO 24165 数字资产唯一识别码（DAMS）。 数字货币资产标识  ISO 23257 区块链和分布式账本系统参考架构。 金融区块链应用设计  TRP (Token Reference Protocol) 代币映射标准（草案阶段）。 证券型代币、稳定币标准化


如果你做支付、清算、转账，最常见的是
➔ ISO 8583、ISO 20022、SWIFT MT/MX、SEPA、ACH

如果你做开户、API 银行对接，最常见的是
➔ OBIE、Berlin PSD2、FAPI






 SEPA 主要包含的内容和标准

项目	简介	标准文档
SEPA Credit Transfer (SCT)	标准化的欧元银行转账（如工资、B2B付款）。	基于 ISO 20022 XML 格式。
SEPA Instant Credit Transfer (SCT Inst)	实时到账版 SEPA 转账（10秒内完成）。	ISO 20022，实时清算系统。
SEPA Direct Debit (SDD)	授权自动扣款（如水电费、订阅）。	ISO 20022 XML；有两种类型：
SDD Core（个人扣款）
SDD B2B（企业对企业扣款）
SEPA Cards Framework (SCF)	统一银行卡支付规范（POS刷卡，ATM取款）。	银联类似，只不过是欧盟自己的一套规范。