//test .kt
package test;

//import MyProxyExample.MyProxyExample
//import utilDep.AOPASM.customClassLoader
//import apiAcc.ReChargeComplete.invk
import apiAcc.ReChargeComplete
import cfg.IocAtiiocCfg.*
import cfg.MyCfg
import org.hibernate.Session
import org.hibernate.SessionFactory


fun main(){
    ReChargeComplete.ovrtTEst = true
  //  AnsiConsole.systemInstall(); // 启用 ANSI 支持
    MyCfg.iniCfgFrmCfgfile()

    iniIocContainr4at()



    val sessionFactory:SessionFactory =getComponent(SessionFactory::class.java)
    println("sessionFactory="+sessionFactory)
    val session: Session = sessionFactory.currentSession
    session.beginTransaction()
   // beginTransaction(session)
    println("session="+session)


    var obj= getBean2025(ReChargeComplete::class.java);
    var rc:ReChargeComplete=obj as ReChargeComplete;
    rc.updateOrdChgSetCmpltBiz("ordChrg2025-02-18T21-34-07")
  //  invokeMethod2025(obj,"updateOrdChgSetCmpltBiz","ordChrg2025-02-18T21-34-07")
 //   obj.updateOrdChgSetCmpltBiz("ordChrg2025-02-18T21-34-07")
    //            setField(instance,"session",new SessionProvider().provide());
    //new SessionProvider().provide()
  //  dbutil.setField<Any>(obj, Session::class.java, container.getComponent(Session::class.java))
  //  obj.invk("updateOrdChgSetCmpltBiz","ordChrg2025-02-18T21-34-07")
 //   obj.updateOrdChgSetCmpltBiz("ordChrg2025-02-18T21-34-07")

    session.getTransaction().commit();
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