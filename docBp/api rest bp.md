

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



