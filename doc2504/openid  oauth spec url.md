
# OpenID Connect 常用 API 端点说明

| API URL 路径                        | 含义说明                                      |
|------------------------------------|-----------------------------------------------|
| `/authorize`                       | 授权端点，发起登录认证流程                    |
| `/token`                           | 令牌端点，使用授权码换取 access_token 与 id_token |
| `/userinfo`                        | 用户信息端点，使用 access_token 获取用户资料   |
| `/.well-known/openid-configuration`| OpenID 提供者配置元数据发现端点                |
| `/oauth2/keys`（或 JWKS URI）      | 公钥发布端点，提供验证 id_token 的公钥         |
说明：

实际 URL 需根据具体 IdP（如 Google、Auth0）替换域名。

所有这些端点均遵循 OpenID Connect Core 1.0 标准。

/authorize 和 /token 是 OAuth 2.0 标准中的关键端点，OpenID Connect 在其基础上扩展了身份认证能力。
/authorize 和 /token 是 OAuth 2.0 标准中的关键端点，OpenID Connect 在其基础上扩展了身份认证能力。