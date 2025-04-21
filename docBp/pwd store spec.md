

哪些标准包含了  规定 密码存储的标准化字段名


还有哪些常见实体的规范，（立马包含了具体实体的字段命名）
目前在密码存储的标准化字段名方面，并没有一个像 ISO 20022 那样专门为“字段命名”做全套定义的国际标准，但以下权威标准和规范确实为密码存储的字段结构、命名方式和含义提供了间接标准或推荐实践。

✅ 涉及密码字段设计的主流标准和规范

标准/组织	是否包含字段说明	说明
OWASP – Password Storage Cheat Sheet	✅ 推荐字段和算法命名方式	实际开发中最被广泛采纳的参考。
NIST SP 800-63B	✅ 结构与存储推荐，但字段名称灵活	美国国家标准技术研究所，影响深远。
ISO/IEC 27001 & 27002	⚠️ 安全管理要求，没有字段名细节	着重管理制度和信息安全措施。
SCIM 2.0 (RFC 7643)	✅ password 字段定义	系统间用户账户同步协议，明确 password 字段（不存储哈希，仅用于创建/更新）。
OpenID Connect / OAuth2	❌ 无直接定义密码字段	通常不建议密码暴露在开放 API 中。
FIDO2 / WebAuthn	❌ 不存储密码	推广无密码认证（passkey、生物认证）。
LDAP (RFC 4519)	⚠️ 定义了 userPassword 属性	老系统兼容，建议存储哈希密码。
✅ 常见推荐字段命名（来自 OWASP / 实践经验）

字段名	含义
hashedPassword	加密后的密码值（主字段）
salt	随机加盐值
algorithm	哈希算法名称，如 bcrypt, PBKDF2
iterations	哈希迭代次数（例如 PBKDF2）
createdAt	密码设置时间
expiresAt	密码有效期（用于策略控制）
lastModifiedAt	最后更新时间
isTemporary	是否是临时密码
passwordHint	密码提示（不推荐使用）
✅ 实践建议（来自 OWASP + NIST）
字段名保持语义明确，例如 hashedPassword 优于 pwd。

密码字段永不明文存储。

每个账户使用独立的盐。

密码哈希算法建议使用：bcrypt, scrypt, Argon2, PBKDF2。

若你使用的是数据库存储，建议字段名带上前缀如 security_，例如 security_hash, security_salt。

如果你是在开发符合某种认证协议或数据合规（如 GDPR、中国等保），也可以基于这些字段来扩展元数据字段（如来源 IP、修改人等）。

需要我根据某个具体标准如 SCIM、OAuth2、LDAP 等写个实体类样例吗？