

=标准 Java    jakarta.security.enterprise.SecurityContext
标准 Java EE/Jakarta EE 项目：
推荐使用：jakarta.security.enterprise.SecurityContext
=azure getCurUname smpl btr.. aws cfg trble
spt easy auth,,,spt oidc

var uid = request_getHeaders_get("X-MS-CLIENT-PRINCIPAL-NAME");

aws ,,,jwt chk fun just as another lamda fun..


easy auth == azure auth ,mode...

itnivk req,autho parse jwt and injkt  user info...
injkt user info( headr or cliam)
spt id provdr,,(customz oidc)


不是有easy auth吗
 
对！你说得没错，Azure 的确有一个内置的机制叫 Easy Auth（也叫 App Service Authentication），它可以自动帮你处理 JWT 验证，你就不必自己手动解析和验证 token 了。


✅ 什么是 Easy Auth？
Easy Auth 是 Azure App Service 和 Azure Functions 提供的 身份验证 / 授权集成服务，支持：

Azure Active Directory（AAD）

Facebook、Google、Twitter、Microsoft 帐户

GitHub

自定义 OpenID Connect（OIDC）身份提供者

👉 启用之后，Azure 会：

自动验证传入请求中的 token（如 JWT）

拒绝无效请求

并通过注入 HTTP 头或环境变量将用户信息提供给你的函数代码


✅ 函数中怎么获取用户信息？
启用 Easy Auth 后，Azure 会把验证后的用户信息放进 HTTP 请求头中，比如：


Header 名称	含义
X-MS-CLIENT-PRINCIPAL-ID	用户对象 ID（sub）
X-MS-CLIENT-PRINCIPAL-NAME	用户名或邮箱
X-MS-TOKEN-AAD-ID-TOKEN	Azure AD ID Token（Base64 JWT）
X-MS-TOKEN-AAD-ACCESS-TOKEN	可用于调用其他 Azure 资源的 token



X-MS-CLIENT-PRINCIPAL-NAME
来自于jwt哪个字段


 
X-MS-CLIENT-PRINCIPAL-NAME 是 Azure 应用服务（Azure App Service）中使用的一个 HTTP 请求头（Header），当你启用了 Azure AD 身份验证/授权 功能时，系统会自动注入这个 Header。它的值通常来自于 JWT Token 的以下字段：

✅ 对应的 JWT 字段：
json
复制
编辑
"upn"（User Principal Name）


个典型的 JWT Payload 如下：

json
复制
编辑
{
"aud": "...",
"iss": "...",
"iat": 1600000000,
"nbf": 1600000000,
"exp": 1600003600,
"name": "Alice Smith",
"preferred_username": "alice@example.com",
"upn": "alice@example.com",
"oid": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
...
}



📝 相关字段对比：   JWT 字段 含义 常出现在 
upn User Principal Name Azure AD 
preferred_username 优先用户名，有时等于 UPN Microsoft 账户
email 电子邮件，有时与 UPN 相同 多种身份源

X-MS-CLIENT-PRINCIPAL-NAME 通常来源于 JWT 的 upn 字段，在某些场景下也可能使用 preferred_username 或 email 字段（具体取决于身份源和配置）。

= get upn fld

String upn = claims.get("upn", String.class);
if (upn != null) return upn;

            String preferred = claims.get("preferred_username", String.class);
            if (preferred != null) return preferred;

            String email = claims.get("email", String.class);

=OIDC 的全称是 OpenID Connect
OIDC 的全称是 OpenID Connect，它是在 OAuth 2.0 基础上的一个 身份认证（Authentication）层协议。
简单理解：
OAuth 2.0 是授权协议（Authorization）——告诉应用「我允许你访问我的信息」。

OIDC 是认证协议（Authentication）——告诉应用「我是谁」。

OIDC 的核心作用：
OIDC 允许客户端应用程序通过第三方（如 Google、微软、GitHub）验证用户身份，并获取用户的基本信息（如昵称、邮箱等）。


它提供了什么？
使用 OAuth 2.0 的 Authorization Code Flow

在授权基础上增加了：

ID Token（JWT 格式，包含用户身份信息）

UserInfo Endpoint（获取用户资料的接口）


举个例子（Google 登录）：
你的网站跳转到 Google 登录

用户登录并授权

返回一个带有 code 的跳转链接

后端用 code 换取：

access_token：访问用户资源

id_token：包含用户身份（是 JWT 格式的）

后端用 id_token 来验证并识别用户是谁

=自建jwt登录系统，提供oidc cfg



