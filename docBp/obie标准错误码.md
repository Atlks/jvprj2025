

🧩 常见标准错误码分类（部分）
1. 验证类错误（Validation Errors）
   错误码	含义
   OBRI.Error.Field.Missing	缺少必填字段
   OBRI.Error.Field.Invalid	字段值格式非法
   OBRI.Error.Consent.Invalid	用户授权 Consent 无效或过期
   OBRI.Error.Consent.Rejected	用户拒绝授权
   OBRI.Error.Consent.Expired	授权已过期
   OBRI.Error.Field.Unexpected	请求中包含了不应有的字段

2. 授权与认证错误（Authentication & Authorization）
   错误码	含义
   OBRI.Error.Auth.InvalidToken	Token 无效或过期
   OBRI.Error.Auth.MissingToken	未提供 Token
   OBRI.Error.Auth.InsufficientScope	Token 权限不足
   OBRI.Error.Auth.InvalidClient	客户端认证失败
   OBRI.Error.Auth.ConsentMismatch	授权数据与实际请求不符

3. 资源错误（Resource Issues）
   错误码	含义
   OBRI.Error.Account.NotFound	账户不存在
   OBRI.Error.Payment.InvalidState	支付状态非法
   OBRI.Error.Resource.Duplicate	请求重复，资源已存在
   OBRI.Error.Payment.InvalidAmount	支付金额非法

4. 系统错误（Server or Bank-Side Issues）
   错误码	含义
   OBRI.Error.Server.Unavailable	银行服务不可用
   OBRI.Error.Server.Timeout	请求超时
   OBRI.Error.Server.Internal	内部服务器错误
   OBRI.Error.RateLimit.Exceeded	请求频率超限

5. 支付特定错误（PIS Errors）
   错误码	含义
   OBRI.Error.Payment.UnsupportedCurrency	货币不支持
   OBRI.Error.Payment.SchemeMismatch	银行卡类型不支持
   OBRI.Error.Payment.CutOffPassed	已过银行支付时间截止点

📘 HTTP 状态码与 OBIE 错误码的关系（常见组合）
HTTP 状态码	含义	常见 OBIE 错误码示例
400	请求非法	Field.Invalid, Consent.Invalid
401	未认证	Auth.InvalidToken
403	权限不足	Auth.InsufficientScope
404	找不到资源	Account.NotFound
409	资源冲突	Resource.Duplicate
429	请求过多	RateLimit.Exceeded
500	内部错误	Server.Internal

