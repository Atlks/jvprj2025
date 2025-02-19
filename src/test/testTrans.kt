//test .kt
package test;

//import MyProxyExample.MyProxyExample
//import utilDep.AOPASM.customClassLoader
//import apiAcc.ReChargeComplete.invk
import apiAcc.ReChargeComplete
import apiAcc.TransHdr
import apis.BaseHdr
import cfg.IocPicoCfg.iniIocContainr
import cfg.IocSpringCfg
import com.sun.net.httpserver.HttpExchange
import org.hibernate.Session
import org.hibernate.SessionFactory
import util.HttpExchangeImp
import util.TransactMng.commitTransaction


fun main(){
    ReChargeComplete.ovrtTEst = true
  //  AnsiConsole.systemInstall(); // 启用 ANSI 支持
    BaseHdr.iniCfgFrmCfgfile()

    cfg.IocSpringCfg.iniIocContainr4spr()





    val he: HttpExchange =
        HttpExchangeImp("http://localhost:8889/TransHdr?changeAmount=8", "uname=007", "output2025.txt")

    var obj=IocSpringCfg.context.getBean(TransHdr::class.java);
    obj.handle(he)

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