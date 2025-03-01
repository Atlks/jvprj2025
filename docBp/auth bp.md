

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

Jakarta Security (JSR 375) 是 Jakarta EE 8 引入的一项安全性规范，提供了一种标准化的身份验证和授权机制。它简化了基于角色的访问控制（RBAC），并提供了用于身份验证、角色管理和安全上下文的 API。与旧的 Java EE 安全 API 相比，Jakarta Security 提供了更现代、灵活的方式来处理应用程序的安全性。


5. JACC 和 Jakarta Security（JSR 375）的区别
   特性	JSR 115 (JACC)	JSR 375 (Jakarta Security)
   目标	自定义安全策略，扩展 Java EE 访问控制	提供标准的身份验证和授权 API
   适用范围	Java EE 服务器容器	任何 Jakarta EE 应用
   使用方式	需要扩展 Policy 和 PolicyConfigurationFactory	提供 IdentityStore 和 HttpAuthenticationMechanism
   典型用例	需要细粒度权限控制的企业应用	需要简化安全配置的现代 Web 应用


Jakarta Security (JSR 375) 核心组件
IdentityStore：用于验证用户身份和角色。
AuthenticationMechanism：用于处理身份验证机制（如 BASIC、FORM、JWT）。
SecurityContext：用于检查当前用户的身份和角色。
AuthenticationMechanism 会调用 SecurityContext嘛
@PermitAll 和 @RolesAllowed：用于控制访问权限。
@DeclareRoles：声明应用程序角色。


AuthenticationMechanism 会调用 SecurityContext嘛
ChatGPT 说：
是的，AuthenticationMechanism 在 Java EE 或 Jakarta EE 中通常与 SecurityContext 配合使用。在 JAX-RS 中，AuthenticationMechanism 负责执行用户身份验证，而 SecurityContext 提供了获取当前认证用户信息的接口。
SecurityContext：一旦用户通过身份验证，SecurityContext 将提供关于当前用户的信息，包括用户的身份（getUserPrincipal()）和该用户是否在特定角色中（isUserInRole()）。SecurityContext 允许你在 JAX-RS 资源类中访问这些信息。
如果身份验证成功，AuthenticationMechanism 会将认证信息与用户的角色信息存储到 SecurityContext 中。
在随后的请求处理过程中，SecurityContext 会提供当前用户的身份和角色信息，供资源类（如 JAX-RS 的 @Context 注解）使用。




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

=funs
getcur usr
SecurityContext1.getCallerPrincipal().getName()