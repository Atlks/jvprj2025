package ztest

import MainApp
import cfg.MainStart
import cfg.Containr
import cfg.IniCfg
import entityx.usr.Usr
//import handler.agt.AgtHdl
import handler.wlt.findBalanceOverviewHdl
import handler.ylwlt.dto.QueryDto
import util.evtdrv.EvtUtil
import util.tx.TransactMng
import java.util.function.Consumer

fun main() {


    befTest()
    MainApp.iniAccInsFdPool_IfNotExist("")

    var jwt="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NjYiLCJpYXQiOjE3NDU0MTUwNzgsImlzcyI6ImF0aSIsInVwbiI6IjY2NiIsInByZWZlcnJlZF91c2VybmFtZSI6IjY2NiIsImVtYWlsIjoiYXRAdWtlLmNvbSIsInVuYW1lIjoiNjY2IiwiYXVkIjoiVVNFUiIsInJvbGUiOiJVU0VSIiwiZXhwIjoxNzU0MDU1MDc4LCJqdGkiOiJlMWIzODM4ZGQyOTk0ZWRlYjNmNTNjMzg1NzY2YzVkMSJ9.MEbTr22IB4IZdShdnUPFZoxr92StP8rBHR8Vd-hL65vU5DuXgcl-2-weO6cB5C6tB6KW14-F8zdRiqF1l9wtKQ"

    var url="http://localhost:8889/wlt/Trans?changeAmount=1"

   // println(getHttp(url,jwt))




    //   setcookie("uname", "007", exchange);//for test




    val dto: QueryDto = QueryDto("666")  // 可以省略类型，Kotlin 自动推断类型
    dto.uname = "666"

  var rt=  findBalanceOverviewHdl().handleRequest(dto);
    println(rt)
    TransactMng.commitTsact()
}

private fun befTest() {
    //--------ini saveurlFrm Cfg
    AppConfig().sessionFactory() //ini sessFctr

    //ini contnr 4cfg,, svrs
    MyCfg.iniContnr()
    EvtUtil.iniEvtHdrCtnr()

   // Containr.evtlist4reg.add(Consumer { u: Usr? -> AgtHdl().regEvtHdl(u) })
    //============aop trans begn
    TransactMng.openSessionBgnTransact()

}

