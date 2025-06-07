//test .kt
package test;

//import MyProxyExample.MyProxyExample
//import utilDep.AOPASM.customClassLoader
import cfg.AppConfig
import api.usr.QueryUsrHdr
import cfg.IocPicoCfg
import cfg.MyCfg
import com.sun.net.httpserver.HttpExchange
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import util.misc.HttpExchangeImp


fun main() {
    //  AnsiConsole.systemInstall(); // 启用 ANSI 支持
    MyCfg.iniCfgFrmCfgfile()
    val he: HttpExchange =
        HttpExchangeImp(
            "http://localhost:8889/QueryUsrHdr?bettxt=龙湖和",
            "uname=008",
            "output2025.txt"
        )
    val container =IocPicoCfg. iniIocContainr()
//  //  println(    container.getComponent(SessionFactory::class.java))
//    val component = container.getComponent(AddOrdBetHdr::class.java)
//    var obj= component as AddOrdBetHdr
//    obj.handle(he);

    val context: ApplicationContext = AnnotationConfigApplicationContext(AppConfig::class.java)
    val bean: QueryUsrHdr = context.getBean<QueryUsrHdr>(
        QueryUsrHdr::class.java)
    bean.handle(he);

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