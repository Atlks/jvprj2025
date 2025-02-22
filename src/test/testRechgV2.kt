//test .kt
package test;

//import MyProxyExample.MyProxyExample
//import utilDep.AOPASM.customClassLoader
import apiAcc.RechargeHdr
import apis.BaseHdr
import cfg.WebSvr
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import util.HttpExchangeImp
import okhttp3.OkHttpClient


import okhttp3.Request

fun main() {


//    Thread {
//        Thread.sleep(7000) // 线程中延迟 3 秒
//       println("\n\n\n===================start req")
//        var rzt=  sendGetRequest("http://localhost:8889/rechargeHdr?amt=888", "uname=007");
//        println("responsetxt=$rzt")
//    }.start()
    //  Thread.sleep(5000) // 让主线程保持运行，防止程序退出
    WebSvr.start()


    //  AnsiConsole.systemInstall(); // 启用 ANSI 支持

}

fun sendGetRequest(url: String, cookieStr: String):

        String? {
    println("get(url=" + url)
    val client = OkHttpClient()
    val request = Request.Builder().url(url)
        .addHeader("Cookie", cookieStr)  // 添加 Cookie
        . build()

    client.newCall(request).execute().use { response ->
        println("resp coce="+response.code)
        return response.body?.string()
    }
}

//    val container = iniIocContainr()
//    val component = container.getComponent(AddOrdChargeHdr::class.java)
//    var obj= component as AddOrdChargeHdr
//            setField(instance,"session",new SessionProvider().provide());
//new SessionProvider().provide()
//  dbutil.setField<Any>(obj, Session::class.java, container.getComponent(Session::class.java))

//   obj.handle(he);


//    context.beanMake(RegHandler::class.java)
//    context.wrapAndPut(RegHandler::class.java, context.getBean(RegHandler::class.java))
//
//
//    context.beanMake(RegHandler::class.java) // 手动注册 Bean but maybe not ok in aop ..use  beanMake
//   // context.beanWrap(RegHandler::class.java) // 确保 AOP 代理生效
//
//    context.wrapAndPut(RegHandler::class.java, context.getBean(RegHandler::class.java))

// context.wrapAndPut(MyAspect::class.java)


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