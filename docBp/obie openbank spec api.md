

oopenbank  obie 归定了哪些api ，列个markdown表格

<!-- TOC -->

- [1. 账户信息 API（Account Information APIs）](#1-账户信息-apiaccount-information-apis)
- [2. 支付指令 API（Payment Initiation APIs）](#2-支付指令-apipayment-initiation-apis)
- [3. 身份管理 API（SCIM APIs）](#3-身份管理-apiscim-apis)
- [4. 动态客户端注册 API（Dynamic Client Registration APIs）](#4-动态客户端注册-apidynamic-client-registration-apis)

<!-- /TOC -->



📘 OBIE 主要 API 分类与端点

# 1. 账户信息 API（Account Information APIs）
这些 API 允许第三方在用户授权下访问银行账户的详细信息。​


功能模块	关键端点示例	描述
账户信息	/accounts	获取账户列表
账户余额	/accounts/{AccountId}/balances	查询指定账户的余额
账户交易	/accounts/{AccountId}/transactions	获取账户的交易明细
账户受益人	/accounts/{AccountId}/beneficiaries	获取账户的受益人信息
直接借记	/accounts/{AccountId}/direct-debits	获取账户的直接借记信息
常设订单	/accounts/{AccountId}/standing-orders	获取账户的常设订单信息
定期付款	/accounts/{AccountId}/scheduled-payments	获取账户的定期付款信息
产品信息	/accounts/{AccountId}/product	获取账户的产品信息
账户同意	/account-access-consents	创建账户访问同意书

# 2. 支付指令 API（Payment Initiation APIs）
这些 API 允许第三方在用户授权下发起支付交易。​
truelayer.com
+1
openbankinguk.github.io
+1


功能模块	关键端点示例	描述
单次支付	/domestic-payments	发起国内单次支付
国际支付	/international-payments	发起国际支付
批量支付	/domestic-payment-consents	创建国内支付同意书
支付状态查询	/domestic-payments/{PaymentId}	查询支付状态
3. 目录服务 API（Directory APIs）
这些 API 提供了开放银行参与者的信息查询和管理功能。​


功能模块	关键端点示例	描述
银行列表	/banks	获取参与开放银行的银行列表
银行详细信息	/banks/{BankId}	获取特定银行的详细信息
证书信息	/certificates	获取用于 API 访问的安全证书信息
公钥信息	/jwks	获取用于验证 JWT 的公钥信息
使用统计	/metrics	查看目录服务的使用统计信息

# 3. 身份管理 API（SCIM APIs）
这些 API 用于管理开放银行生态系统中的身份信息，特别是用于自动化。​


功能模块	关键端点示例	描述
用户管理	/Users	管理用户信息
组织管理	/Groups	管理组织信息

# 4. 动态客户端注册 API（Dynamic Client Registration APIs）
这些 API 允许第三方提供商动态注册其客户端信息，以便与银行系统集成。​
truelayer.com
+1
openbankinguk.github.io
+1


功能模块	关键端点示例	描述
客户端注册	/register	注册新的客户端
客户端更新	/register/{ClientId}	更新已注册的客户端信息