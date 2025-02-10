//test .kt
package test;

import apiUsr.RegHandler
import apis.BaseHdr
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

    BaseHdr.iniCfgFrmCfgfile()
    //   addObj(ord,saveUrlOrdBet,OrdBet.class);
    val he = HttpExchangeImp("http://localhost:8889/reg?uname=8&pwd=ppp", "uname=009", "output2025.txt")
    RegHandler().handle(he)
  //  RegHandler().handle2(he)  // ✅ 去掉 new 关键字
}
