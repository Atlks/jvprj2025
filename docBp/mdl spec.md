

哪些常用的实体,有标准规范
ChatGPT 说：
很多常用实体在业界都有一定的标准化规范或行业推荐结构，尤其是在身份认证、支付、电商、日志、安全等领域。下面列出一些常见实体及它们所依据的标准或推荐规范：



✅ 1. 用户（User / UserInfo）
标准：
OpenID Connect (OIDC) UserInfo

SCIM（System for Cross-domain Identity Management）


✅ 2. 地址（Address）
标准：
vCard

OIDC 中也包含标准地址格式

常用字段：
java
复制
编辑
country, region, city, street, postalCode, recipientName, phone


✅ 3. 密码 / 凭证（Password / Credential）
标准：
OWASP 密码存储最佳实践

NIST SP 800-63B

常用字段：
java
复制
编辑
passwordHash, salt, hashAlgorithm, iterations, updatedAt, forceChange, locked


✅ 4. 订单（Order）
标准：
电商平台常用设计（Amazon、Shopify 参考）

ISO 8583（金融交易）

常用字段：
java
复制
编辑
orderId, userId, productList, totalAmount, currency,
status, createdAt, paymentMethod, deliveryInfo


✅ 5. 支付（Payment）
标准：
ISO 20022 金融支付消息标准

PCI DSS（安全要求）

常用字段：
java
复制
编辑
paymentId, amount, currency, payerId, payeeId,
status, method (credit, alipay, wechat), transactionId


✅ 6. 日志（AuditLog / Activity）
标准：
Common Event Format (CEF)

OpenTelemetry / OpenTracing

常用字段：
java
复制
编辑
logId, timestamp, userId, action, ip, result, errorMessage, targetEntity


✅ 7. JWT Claims（Token Payload）
标准：
RFC 7519 JWT

OIDC ID Token Claims

常用字段：
json
复制
编辑
{
"sub": "user_id",
"aud": "client_id",
"iss": "https://issuer.com",
"exp": 1689020000,
"iat": 1689010000,
"email": "user@example.com",
"roles": ["admin", "user"]
}


✅ 8. 产品（Product）
推荐结构：
多数电商平台通用模型

常用字段：
java
复制
编辑
productId, name, category, price, currency, description,
images, stock, tags, specs, createdAt


✅ 9. 客户（Customer）
推荐结构：
Salesforce / Hubspot 等通用 CRM 模型

常用字段：
java
复制
编辑
customerId, fullName, phone, email, company, industry, tags, createdAt


✅ 10. 文件 / 附件（File / Attachment）
推荐结构：
java
复制
编辑
fileId, fileName, size, mimeType, url, uploadedBy, uploadedAt