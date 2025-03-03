

ChatGPT 说：
JWT（JSON Web Token）通常由三个部分组成，每部分之间由点（.）分隔。这些部分分别是：

Header（头部）
Payload（负载）
Signature（签名）
1. Header（头部）
   头部通常包含两部分信息：

typ：令牌的类型，通常为 "JWT"。
alg：签名算法，用来签署 JWT。例如，常见的算法包括 HS256（HMAC SHA-256）和 RS256（RSA SHA-256）。
头部的一个典型示例：

json
复制
编辑
{
"typ": "JWT",
"alg": "HS256"
}
2. Payload（负载）
   负载部分包含了声明（Claims）。声明是 JWT 中的信息载体，它通常包括三个类型的数据：

注册声明（Registered Claims）：这些是 JWT 中的一些标准字段，如：

sub：Subject，主题，通常表示用户的标识。
iat：Issued At，签发时间，表示 JWT 生成的时间。
exp：Expiration Time，过期时间，表示 JWT 何时过期。
iss：Issuer，签发者，表示是谁签发了 JWT。
aud：Audience，受众，表示这个 JWT 是为谁生成的。
公共声明（Public Claims）：这些是可以自由定义的声明，但应避免与其他系统冲突。

私有声明（Private Claims）：这些是应用程序之间约定好的字段，例如用户的角色、权限等。

例如，Payload 可能包含如下内容：

json
复制
编辑
{
"sub": "1234567890",
"name": "John Doe",
"iat": 1516239022
}
3. Signature（签名）
   签名部分用于验证消息的完整性，防止数据在传输过程中被篡改。它是通过将编码后的头部和负载（以及一个密钥）传递给签名算法（如 HMAC SHA256）来生成的。

签名的生成过程通常是：

makefile
复制
编辑
Signature = HMACSHA256(
base64UrlEncode(header) + "." + base64UrlEncode(payload),
secretKey)
JWT 的签名部分确保了消息的完整性，只有持有密钥的人可以生成有效的签名。

JWT 示例
一个完整的 JWT 可能长这样：

复制
编辑
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
其中：

第一部分是 Header，经过 Base64Url 编码。
第二部分是 Payload，也经过 Base64Url 编码。
第三部分是 Signature，由前两部分和密钥生成。
JWT 常见字段解释
sub (Subject)：通常是用户的唯一标识符。
iat (Issued At)：JWT 的创建时间，用来表示该令牌生成的时间。
exp (Expiration Time)：JWT 过期时间，超过此时间后令牌失效。
aud (Audience)：令牌的受众，表示谁是这个 JWT 的目标用户。
iss (Issuer)：JWT 的签发者，通常为生成该令牌的应用或服务。
scope：定义该令牌的权限范围，常用于 OAuth2 授权中，表示允许的操作或访问的资源范围。
这些字段可以根据需要在实际应用中进行定制。