

=一个url对应class  vs 对应fun 

对应class可能更好，简单直接，方便检索文件模式，，，每个act就是一个class，不杂乱

fun的化，查找对应的文件》方法就要时间，然后里面可能方法众多，杂乱。。

==JAX-RS
（3）创建 REST API
Quarkus 使用 JAX-RS（Jakarta RESTful Web Services） 处理 HTTP 请求，示例：


JAX-RS（Java API for RESTful Web Services）是Java平台上的一个标准API，用于构建RESTful Web服务。它定义了一套规范，用于在Java应用程序中创建、配置和处理RESTful Web服务。以下是JAX-RS的一些核心规范：

资源类：JAX-RS使用Java类作为资源类，这些类中的方法响应HTTP请求并返回数据。每个资源类方法都通过注解与特定的HTTP方法（如GET、POST、PUT、DELETE等）进行映射。

注解：

@Path: 定义资源类的URL路径。
@GET, @POST, @PUT, @DELETE: 映射HTTP方法。
@Produces: 指定响应的MIME类型。
@Consumes: 指定请求的MIME类型。
@QueryParam, @PathParam, @FormParam, @HeaderParam, @CookieParam: 获取请求中的参数。
@Context: 注入上下文信息（如UriInfo, HttpHeaders, Request等）。


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello, Quarkus!";
    }




=just use sdk web api is ok smp

=spribt too truouble
in my jdk 21 ,,alway bug...gv up

=url参数绑定dto


DTO 处理 URL 查询参数
📌 方式 1：Spring Boot 自动绑定


开发web应用，url参数绑定是什么技术
ChatGPT said:

在 Web 应用开发中，URL 参数绑定的技术主要依赖于 请求解析 和 对象映射。不同的 Web 框架和编程语言有不同的实现方式，常见的技术包括：
1. Servlet (Java EE / Spring)

技术点：@RequestParam / @PathVariable / @ModelAttribute

    @RequestParam：绑定查询参数（?key=value）
    @PathVariable：绑定路径参数（/users/{id}）
    @ModelAttribute：自动将表单数据或查询参数映射到对象

