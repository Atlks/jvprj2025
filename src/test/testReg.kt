//test .kt
package test;

import apiUsr.RegHandler
import apis.BaseHdr
import apis.MyAspect
import com.sun.net.httpserver.HttpExchange
import org.noear.solon.Solon
import org.noear.solon.core.AppContext
import util.HttpExchangeImp
import java.lang.reflect.Method


fun main() {
    println(33)
    //C:\Users\attil\IdeaProjects\jvprj2025\target\classes\apiUsr\RegHandler.class
   // println(RegHandler::class)
    BaseHdr.iniCfgFrmCfgfile()
  //  StaticMethodAOP. enhanceClass(RegHandler::class.toString());
    val he: HttpExchange = HttpExchangeImp("http://localhost:8889/reg?uname=qq&pwd=ppp", "uname=0091", "output2025.txt")
    val Object1 = RegHandler()
   // invokeMethod2025(Object1,"handle",he)
    RegHandler().handle(he)
}


/**
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
fun main4solon() {
    //  Solon.start(RegHandler::class.java)
    //  Solon.start(RegHandler::class.java)
    // Solon.start(App::class.java, arrayOf())  // 确保 App 作为入口
    val app = Solon.start(RegHandler::class.java, arrayOf()) // `app` 是 SolonApp
    val context: AppContext = app.context() // 获取 `AppContext`
    // **手动注册 RegHandler**
//    context.beanMake(BaseHdr::class.java) // 手动注册 Bean
    //  context.beanMake(MyAspect::class.java) // 手动注册 Bean
    context.beanMake(RegHandler::class.java) // 手动注册 Bean
    context.wrapAndPut(MyAspect::class.java)


    //  context.bean(RegHandler::class.java, RegHandler()); //这里报错  Unresolved reference: bean
    // **检查 `RegHandler` 是否被正确注册**
    //   val handler = context.getBean(RegHandler::class.java)
//  手动注册
    // **手动注册 RegHandler**
    // app.bean(RegHandler::class.java, RegHandler())

    BaseHdr.iniCfgFrmCfgfile()
    //   addObj(ord,saveUrlOrdBet,OrdBet.class);
    val he = HttpExchangeImp("http://localhost:8889/reg?uname=qq&pwd=ppp", "uname=0091", "output2025.txt")
    Solon.context().getBean(RegHandler::class.java).handle(he)
    //  RegHandler().handle(he)

}


//import org.junit.jupiter.params.ParameterizedTest
//import org.junit.jupiter.params.provider.CsvSource
//import kotlin.test.assertEquals
//import io.mockk.every
//import io.mockk.mockk
//import org.junit.jupiter.api.Test
//import kotlin.test.assertEquals