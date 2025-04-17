

api rest接口需要了解的规范.md


-----------TOC INDEX------------
=注解式样路由
==JAX-RS
=dto 定义以及验证规范valid   Jakarta Bean Validation 3.0
=dto 权限当前用户名 权限授权规范  jakarta.annotation.security
=apigateway  aws serverless
=内容协商与版本控制规范
=HTTP 方法与状态码规范
=分页、排序、筛选查询参数规范
=通用返回结构与错误处理规范
=OpenAPI / Swagger 接口描述标准
-----------end TOC INDEX------------


=注解式样路由
==JAX-RS

JAX-RS（Java API for RESTful Web Services）是Java平台上的一个标准API，用于构建RESTful Web服务。它定义了一套规范，用于在Java应用程序中创建、配置和处理RESTful Web服务。以下是JAX-RS的一些核心规范：
@Path: 定义资源类的URL路径。


=dto 定义以及验证规范valid   Jakarta Bean Validation 3.0
Jakarta Bean Validation（前身为 Java Bean Validation / JSR 303/349/380） 规范的一部分，用于 Java 中的数据校验（validation）。

Jakarta Bean Validation 3.0（继承自 Java Bean Validation 2.0 / 1.1 / 1.0）

属于 Jakarta EE 规范（Java EE 的继承者）

=dto 权限当前用户名 权限授权规范  jakarta.annotation.security

    @CurrentUsername
主要用于声明式权限控制：

@RolesAllowed, @PermitAll, @DenyAll

=apigateway  aws serverless
aws是实现接口
🔵 Azure Functions（注解式）

public class HelloFunction {
@FunctionName("hello")

=内容协商与版本控制规范
Content negotiation（内容协商）：

通过 Accept 头控制返回格式（JSON、XML 等）

API 版本控制方式：

URI 版本：/api/v1/users

Header 版本：Accept: application/vnd.myapp.v1+json


=HTTP 方法与状态码规范
正确使用 HTTP 动词：GET, POST, PUT, DELETE, PATCH

合理返回状态码：

200 OK

201 Created

204 No Content

400 Bad Request

401 Unauthorized

403 Forbidden

404 Not Found

500 Internal Server Error


=分页、排序、筛选查询参数规范
通用查询参数：

page, size, sort=field,asc/desc

filter[name]=John, filter[status]=active
=通用返回结构与错误处理规范
=OpenAPI / Swagger 接口描述标准