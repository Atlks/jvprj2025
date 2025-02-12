//test .kt
package test;

import apiUsr.RegHandler
import apis.BaseHdr
import com.sun.tools.javac.tree.TreeInfo.args
import org.noear.solon.Solon
import util.HttpExchangeImp


//import org.junit.jupiter.params.ParameterizedTest
//import org.junit.jupiter.params.provider.CsvSource
//import kotlin.test.assertEquals
//import io.mockk.every
//import io.mockk.mockk
//import org.junit.jupiter.api.Test
//import kotlin.test.assertEquals

//@Test
fun main() {
  //  Solon.start(RegHandler::class.java)
  //  Solon.start(RegHandler::class.java)
    Solon.start(RegHandler::class.java, arrayOf())
    BaseHdr.iniCfgFrmCfgfile()
    //   addObj(ord,saveUrlOrdBet,OrdBet.class);
    val he = HttpExchangeImp("http://localhost:8889/reg?uname=qq&pwd=ppp", "uname=0091", "output2025.txt")
    RegHandler().handle(he)

}
