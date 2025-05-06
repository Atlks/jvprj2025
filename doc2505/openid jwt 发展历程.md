

🔄 OpenID & JWT 发展历程年表
时间	事件	说明
2005	OpenID 1.0 发布	最早的去中心化身份认证协议，由 Brad Fitzpatrick 发起（LiveJournal 创始人）
2007	OpenID 2.0	增强了发现机制和安全性，但复杂、不易与企业接入整合
2010	OAuth 2.0 发布	更适合授权的标准，但本身不处理“用户是谁”的身份认证问题
2010	JWT（RFC 7519 草案）开始成形	提出一种轻量级、跨语言的令牌表示机制
2012	OpenID Connect 草案	在 OAuth 2.0 基础上扩展出认证能力，引入 JWT 作为身份令牌（ID Token）
2015	OpenID Connect 1.0 正式发布	简化、现代化、移动友好，成为主流身份认证标准，使用 JWT
2015	JWT 成为正式 RFC：RFC 7519	作为 ID Token 和 Access Token 格式在 OIDC 和 OAuth 中广泛采用
2020+	OIDC 成为标准身份认证协议	Google、Microsoft、Apple、AWS 等主流平台全部采用
2023+	JWT 被广泛用于 SSO、API 授权、微服务通信	特别适用于分布式系统、无状态验