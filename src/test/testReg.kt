//test .kt
package test;

import apiUsr.RegHandler
import apis.BaseHdr
import apis.MyAspect
import com.sun.tools.javac.tree.TreeInfo.args
import org.noear.solon.Solon
import org.noear.solon.annotation.SolonMain
import org.noear.solon.core.AppContext
import util.HttpExchangeImp


//@SolonMain
//@ComponentScan("apiUsr")  // 确保 `RegHandler` 的包路径被扫描
//@Test
fun main() {
  //  Solon.start(RegHandler::class.java)
  //  Solon.start(RegHandler::class.java)
   // Solon.start(App::class.java, arrayOf())  // 确保 App 作为入口
    val app = Solon.start(RegHandler::class.java, arrayOf()) // `app` 是 SolonApp
    val context: AppContext = app.context() // 获取 `AppContext`
    // **手动注册 RegHandler**
//    context.beanMake(BaseHdr::class.java) // 手动注册 Bean
  //  context.beanMake(MyAspect::class.java) // 手动注册 Bean
    context.beanMake(RegHandler::class.java) // 手动注册 Bean
    context.  wrapAndPut(MyAspect::class.java)



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