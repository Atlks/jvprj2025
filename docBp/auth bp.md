

spec   

=jsr375 vs jsr 115
在现代 Jakarta EE 中，JSR 375（Jakarta Security）更常用于简化身份认证。

JSR	名称	描述
JSR 375	Java EE Security API	Java EE 的认证和授权
JSR 196	Java Authentication Service Provider Interface for Containers (JASPIC)	认证 SPI


JSR 375 提供标准 API，简化身份认证和授权。
IdentityStore 负责用户身份验证，可以基于数据库、LDAP 等实现。
HttpAuthenticationMechanism 处理 HTTP 认证（如 BASIC、FORM、JWT）。
SecurityContext 用于检查用户权限，可用于 REST API。

@PermitAll 属于 JACC（JSR 115）和 Jakarta Security（JSR 375）规范，


5. JACC 和 Jakarta Security（JSR 375）的区别
   特性	JSR 115 (JACC)	JSR 375 (Jakarta Security)
   目标	自定义安全策略，扩展 Java EE 访问控制	提供标准的身份验证和授权 API
   适用范围	Java EE 服务器容器	任何 Jakarta EE 应用
   使用方式	需要扩展 Policy 和 PolicyConfigurationFactory	提供 IdentityStore 和 HttpAuthenticationMechanism
   典型用例	需要细粒度权限控制的企业应用	需要简化安全配置的现代 Web 应用

=javax anno secury
import jakarta.annotation.security.PermitAll;

chk url is not good hah ...chk anno more modrn

=dft need login




=PermitAll set anno ,not login 

reg,login  page not need login ,,another all need 

@PermitAll
public class RegHandler

@PermitAll
//   http://localhost:8889/login?uname=008&pwd=000
public class LoginHdr extends BaseHdr {