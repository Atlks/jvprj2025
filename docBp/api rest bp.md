

==JAX-RS
（3）创建 REST API
Quarkus 使用 JAX-RS（Jakarta RESTful Web Services） 处理 HTTP 请求，示例：


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



