

平台	主要名称	Java 中的处理器接口或函数形式	备注
AWS Lambda	Lambda Function	RequestHandler<I, O> 接口	官方原生，支持 Spring Cloud Function
Google Cloud Functions	Function	实现 HttpFunction 接口	Java17 起支持标准 Servlet 样式
Azure Functions	Azure Functions	@FunctionName 注解 + POJO 参数	使用注解注入触发器和请求体



🧩 总结：RequestHandler 是 AWS 特有接口

    AWS 是通过 RequestHandler/RequestStreamHandler 接口定义；

    GCP、Azure 更偏向使用注解或 HttpFunction；

    Spring Cloud Function 提供跨平台兼容（Function<I,O> 等），可以写一次部署多平台；



🔷 Google Cloud Functions（Java 17+）

public class HelloFunction implements HttpFunction {
@Override
public void service(HttpRequest request, HttpResponse response) throws IOException {



🔵 Azure Functions（注解式）

public class HelloFunction {
@FunctionName("hello")
public HttpResponseMessage run(
@HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS)
HttpRequestMessage<Optional<String>> request,
final ExecutionContext context) {

        String name = request.getQueryParameters().get("name");
        return request.createResponseBuilder(HttpStatus.OK)
                      .body("Hello " + name)
                      .build();
    }
}


如果你在考虑“写一套跨平台可部署”的函数代码，Spring Cloud Function 是最推荐的做法。