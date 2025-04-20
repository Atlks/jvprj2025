
ResponseEntity  这个使用，我要输出自定义的json对象呢
 
ResponseEntity 是 Spring 框架中用于构建 HTTP 响应的一个类，它可以包含响应状态码、响应头以及响应体。你可以使用它来返回自定义的 JSON 对象，通常是通过使用 @RestController 配合 @RequestMapping 或其他 HTTP 请求注解来构建 API。

返回自定义的 JSON 对象
假设你有一个自定义的 JSON 对象，首先要创建一个 Java 类来表示这个对象。然后，使用 ResponseEntity 来返回这个对象。


1. 创建自定义的 JSON 对象类
   例如，你有一个自定义的 JSON 对象 ApiResponse，它包含一个状态码、消息和数据：

java
复制
编辑
public class ApiResponse {

    private String message;
    private Object data;
    private int statusCode;



在你的 Controller 中，使用 ResponseEntity 将 ApiResponse 返回给客户端：
// 返回响应体并设置响应码 200 OK
return ResponseEntity.ok(apiResponse);

服务器错误（状态码 500）

java
复制
编辑
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);  // 500

用 ResponseEntity 可以方便地返回自定义的 JSON 对象。
你可以灵活地设置 HTTP 响应的状态码，且 Spring 会自动将 Java 对象转换为 JSON（假设使用了 @RestController 或 @ResponseBody）。
自定义的 JSON 对象（如 ApiResponse）可以包含消息、数据和状态码等字段，并将其返回给客户端。