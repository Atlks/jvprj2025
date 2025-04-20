
本项目用户实体，使用oidc openid吧标志规范，字段名参见此规范



一个符合 OpenID Connect / OAuth2 的 UserInfo 标准 的 Java 实体类示例，字段依据 OIDC Core Spec - UserInfo Claims：



常见的用户模型标准或规范
1. SCIM（System for Cross-domain Identity Management）
   🔗 标准文档：RFC 7643

SCIM 是一个 跨系统用户身份管理协议标准，由 IETF 组织定义，广泛用于 SaaS、企业身份同步（如 Azure AD、Okta、Google Identity）。

SCIM 规范中的用户模型包含：


字段	类型	说明
id	string	用户唯一标识
userName	string	用户名（登录名）
name	complex	名（givenName）和姓（familyName）
emails	array	邮箱地址（可以有多个）
phoneNumbers	array	电话号码
addresses	array	地址信息
active	boolean	是否启用
groups	array	所属组（权限控制）
meta	complex	创建时间、更新时间等
🔧 适合用于：需要和其他系统对接用户数据、OAuth / OIDC 身份服务、统一身份系统（SSO）。

2. OpenID Connect / OAuth2 的 UserInfo 标准
   OpenID Connect 规定了一组用户字段，用于 OAuth 登录完成后的 userinfo 接口返回：


字段	类型	说明
sub	string	用户唯一标识（Subject）
name	string	全名
given_name	string	名
family_name	string	姓
email	string	邮箱
phone_number	string	手机号
preferred_username	string	昵称 / 用户名
picture	string	头像
locale	string	地区语言
updated_at	int	更新时间（Unix 时间戳）
🔧 适合用于：OAuth2 / OIDC 登录用户模型，比如 Google、GitHub、Facebook 登录后返回的用户信息

3. Spring Security 的 UserDetails 接口（Java 应用通用）
