
reg mapping  router  rtr...

# jdk  createContext

// 示例路由
/**
   推荐使用委托模式  fun模式
*   createContext(String path, FunctionX<T, Object> fun)

*  createContext(String path, HttpHandler handler);
   ✅ Lambda 表达式

21014

21014
*   createContext(String path, Method m)
*   createContext(String path, FaasHdler )
*
*/


# javalin  app.get()




get(path: kotlin.String, handler: io.javalin.http.Handler):

# sprboot   registerMapping

RequestMappingHandlerMapping.registerMapping 方法用于 动态注册 HTTP 请求映射，它的签名如下：

java
public void registerMapping(RequestMappingInfo mapping, Object handler, Method m


# c# miniapi  app.MapGet


📌 1. 使用 MapGet 直接映射到静态方法
✅ 适用于 ASP.NET Core Minimal API

csharp
var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

// 直接映射到静态方法
app.MapGet("/reg", () => UserService.Reg());

app.Run();


public static RouteHandlerBuilder MapGet(
this IEndpointRouteBuilder endpoints,
[StringSyntax("Route")] string pattern,
Delegate handler
)

✅ handler → Delegate，用于处理请求（可以是 Lambda、方法引用等）