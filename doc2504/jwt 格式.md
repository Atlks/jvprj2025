


JWT 分为三部分：

Header. Payload. Signature
↑
Claims





✅ 常见标准 Claims 字段（按规范定义）：
字段	含义
iss	Issuer（签发者）
sub	Subject（主题，一般是用户 ID）
aud	Audience（接收方）
exp	Expiration（过期时间）
nbf	Not Before（生效时间）
iat	Issued At（签发时间）
jti	JWT ID（唯一标识）



JWT 的 Header（头部） 是一个 JSON 对象，用来说明这个 JWT 的类型（typ）和签名所使用的算法（alg）。
✅ 标准 JWT Header 示例：

{
"alg": "HS256",
"typ": "JWT"
}

✅ 常见字段说明：
字段名	含义	示例
alg	签名算法，如 HS256、RS256 等	"HS256"
typ	类型，通常就是 "JWT"	"JWT"
kid	Key ID，指定使用哪个密钥（可选）	"abc123"
cty	Content Type，例如 "JWT"（嵌套 JWT）	"JWT"（不常用）



✅ Java 示例：构造 Header

在 Java（如 jjwt）中构建 Token 时，一般 Header 是自动添加的，但你也可以手动设置：


✅ 最终编码结构（Base64 编码）

JWT 是 3 段字符串拼接而成：

Header.Payload.Signature