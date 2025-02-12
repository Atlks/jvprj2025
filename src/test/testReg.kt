//test .kt
package test;

import apiUsr.RegHandler
import apis.BaseHdr
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import org.noear.solon.Solon
import org.noear.solon.core.AppContext
import util.ByteBuddyProxyExample
import util.HttpExchangeImp
fun main() {
    println(33)
    //C:\Users\attil\IdeaProjects\jvprj2025\target\classes\apiUsr\RegHandler.class
    // println(RegHandler::class)
    BaseHdr.iniCfgFrmCfgfile()
    //  StaticMethodAOP. enhanceClass(RegHandler::class.toString());
    val he: HttpExchange = HttpExchangeImp("http://localhost:8889/reg?uname=qq1&pwd=ppp", "uname=0091", "output2025.txt")
    //  val Object1 = RegHandler()


    // 创建目标对象

  //  val proxy = CGLIBProxyExample.getProxyObj(RegHandler::class.java)
    val proxy =   ByteBuddyProxyExample.createProxy(RegHandler::class.java)
    println("aopClass="+proxy.javaClass.name)
        proxy.handle(he)
  //    RegHandler().handle(he)
}

fun main22() {

    val app = Solon.start(RegHandler::class.java, arrayOf("--solon.aop=true", "--aop-debug=true"))

    val AppContext1: AppContext = app.context() // 获取 `AppContext`
  //  val bean1:RegHandler =
       AppContext1.wrapAndPut(HttpHandler::class.java, AppContext1.getBean(RegHandler::class.java))
    var bean1 = AppContext1.getBean(HttpHandler::class.java)
    val proxy =  AppContext1.wrapAndPut(RegHandler::class.java, bean1)
    println("After wrap: " + bean1.javaClass.name)  // 输出代理类名
    println("After wrap proxy:  " + proxy.javaClass.name)  // 输出代理类名



       BaseHdr.iniCfgFrmCfgfile()

    val he = HttpExchangeImp("http://localhost:8889/reg?uname=qq&pwd=ppp", "uname=0091", "output2025.txt")
   // bean1 = AppContext1.getBean(RegHandler::class.java)
    bean1.handle(he)
    //  RegHandler().handle(he)
  //  println(RegHandler::class.java.getPackage().name)
    println("aopClass="+bean1.javaClass.name)

   // 如果输出类似 RegHandler$$EnhancerBySpring，说明 AOP 代理生效。
  //  如果输出 apiUsr.RegHandler，说明 它没有被代理，AOP 不能拦截它。

}

//    context.beanMake(RegHandler::class.java)
//    context.wrapAndPut(RegHandler::class.java, context.getBean(RegHandler::class.java))
//
//
//    context.beanMake(RegHandler::class.java) // 手动注册 Bean but maybe not ok in aop ..use  beanMake
//   // context.beanWrap(RegHandler::class.java) // 确保 AOP 代理生效
//
//    context.wrapAndPut(RegHandler::class.java, context.getBean(RegHandler::class.java))

// context.wrapAndPut(MyAspect::class.java)




/**  invokeMethod2025(Object1,"handle",he)
 * 执行某个类的方法
 */
fun invokeMethod2025(Object1: Any, MethodName: String, vararg objs: Any?) {
    try {
        // 获取对象的类类型
        val clazz = Object1.javaClass

        // 获取指定方法
        var method =clazz.getMethod(MethodName, *objs.map { it?.javaClass }.toTypedArray())

        // 设置方法可访问
        method?.isAccessible = true

        // 调用方法并传递参数
        val result = method?.invoke(Object1, *objs)

        // 打印结果（如果有的话）
        println("Result: $result")
    } catch (e: Exception) {
        e.printStackTrace()  // 捕获任何异常，进行调试
    }
}

/**
 *   try {
 *            method=clazz.getMethod(MethodName, *objs.map { it?.javaClass }.toTypedArray())
 *        }catch(e:NoSuchMethodException)
 *        {
 *            // 获取父类中的 handle 方法
 *            if(method==null)
 *                method = clazz.superclass?.getDeclaredMethod(MethodName, *objs.map { it?.javaClass }.toTypedArray())
 *        }
 *
 */

//@SolonMain
//@ComponentScan("apiUsr")  // 确保 `RegHandler` 的包路径被扫描
//@Test



//import org.junit.jupiter.params.ParameterizedTest
//import org.junit.jupiter.params.provider.CsvSource
//import kotlin.test.assertEquals
//import io.mockk.every
//import io.mockk.mockk
//import org.junit.jupiter.api.Test
//import kotlin.test.assertEquals