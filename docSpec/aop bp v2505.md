

use web  flter he 拦截器

✅ 与自定义拦截器（AOP方式）的对比：
特性	Filter（系统）	自定义 Interceptor（你写的 AOP）
内建支持	✅ 是 HttpServer 的一部分	❌ 需要自己设计框架结构
优先级控制	一般按添加顺序执行	可以定制复杂链执行逻辑
多路由共享	❌ 需手动添加到每个 HttpContext	✅ 可在入口通用拦截
与 Spring/SpringBoot 类比	Servlet Filter	Spring HandlerInterceptor


public class LambdaReflectDemo {


    public static void main(String[] args) throws Throwable {
        call(DelAdmHdr::m3, new handler.admin.DelAdmHdr.DeleteUserReqDto("66") );
    }

//一个函数参数的，俩个三个参数的实现。。以及supply模式
call函数以及可以读取funname ,dto 等。。。完美了，不需要使用faas模式麻烦。。。




@MethodInfo("注解描述m333")
public static Object m3(handler.admin.DelAdmHdr.DeleteUserReqDto dto ) {
System.out.println(" 执行，参数: " + dto + "  "   );
return null;
}
