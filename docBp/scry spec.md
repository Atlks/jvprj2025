

. javax.security
功能：与 Java 安全管理相关，提供身份验证、授权和加密等功能。
常见类/接口：
Subject：表示某个安全实体（如用户）的接口。
KeyStore：用于管理密钥和证书。



javax.validation
功能：用于 Java 对象的验证，常用于输入数据验证。
常见类/接口：
Validator：用于验证 Java 对象的接口。
ConstraintValidator：自定义约束验证器接口。


crsp安全使用jwt解决，必须手动加入head，不能一个链接自动auth了
xss使用encode编码解决
sql注入也是有encode或参数数编码
使用 deny by default 策略，默认需要auth访问，除非明确排除
aes加密，不要使用des等密级等级低的
过滤用户输入
关闭 debug 模式 ，，debug功能开关
弱密码（如 12345678） check
多因素认证（MFA）  2fa



安全日志和监控失败（Security Logging and Monitoring Failures）
🔹 问题：未记录关键安全事件，导致无法检测或响应攻击。
🔹 示例：

用户连续多次失败登录，未触发告警
SQL 注入攻击日志未记录
服务器日志被攻击者删除
🔹 防护措施：
记录所有身份验证失败的日志
使用 SIEM（安全信息与事件管理）系统
定期分析日志，发现异常行为