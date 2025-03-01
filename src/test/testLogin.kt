//test .kt
package test;

//import MyProxyExample.MyProxyExample
//import utilDep.AOPASM.customClassLoader

import api.usr.LoginHdr
import cfg.IocSpringCfg
import cfg.MyCfg
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import util.HttpExchangeImp


fun main(){
    //  AnsiConsole.systemInstall(); // 启用 ANSI 支持
    MyCfg.iniCfgFrmCfgfile()
    cfg.IocSpringCfg.iniIocContainr4spr()
    val he: HttpExchange =
        HttpExchangeImp("http://localhost:8889/login?uname=0098&pwd=ppp0", "", "output2025.txt")
    //uname=0093
  // val container = iniIocContainr()
    val java = LoginHdr::class.java.name
    var RegHandler1= IocSpringCfg.context.getBean(java);
var hdl=RegHandler1 as HttpHandler
    hdl.handle(he);
    println("------------resp out :\n"+readFile("output2025.txt"));
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