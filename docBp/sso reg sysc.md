三、用户同步与登录
1. 单点登录（SSO）设计
用户在平台登录后，无需再次登录第三方游戏。

登录接口一般携带 user_id、token、timestamp、sign。

签名方式一般为 MD5/HMAC-SHA256，对请求参数 + 密钥进行签名。

2. 用户注册自动同步
登录时若用户不存在，自动创建账户。

有些平台提供 createIfNotExists 选项。