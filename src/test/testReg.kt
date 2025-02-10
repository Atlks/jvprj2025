//test .kt
package test;

import apiUsr.RegHandler
import apis.BaseHdr
import util.HttpExchangeImp

fun main() {

    BaseHdr.iniCfgFrmCfgfile()
    //   addObj(ord,saveUrlOrdBet,OrdBet.class);
    val he = HttpExchangeImp("http://localhost:8889/reg?uname=8&pwd=ppp", "uname=009", "output2025.txt")
    RegHandler().handle(he)
  //  RegHandler().handle2(he)  // ✅ 去掉 new 关键字
}
