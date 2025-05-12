package service.YLwltSvs

import cfg.Containr
import cfg.MainStart
import cfg.IocSpringCfg
import cfg.IniCfg
import entityx.wlt.TransDto
import model.usr.Usr
import util.auth.SecurityContextImp4ck
import util.tx.HbntUtil
import util.algo.Icall
import util.proxy.SprUtil.getBeanFrmSprAsObj
import util.tx.TransactMng
import java.math.BigDecimal

fun main() {


    //--------ini saveurlFrm Cfg
    //@NonNull
    MyCfg.iniContnr4cfgfile()

    IocSpringCfg.iniIocContainr4spr()
    Containr.SecurityContext1 = SecurityContextImp4ck()

    @Suppress("UNCHECKED_CAST")
   // val bean:Icall<TransDto, Any> = SprUtil.getBeanFrmSpr(AddMoneyToWltService::class.java) as Icall<TransDto, Any>

    val instance = getBeanFrmSprAsObj(AddMoney2YLWltService::class.java)

    val bean: Icall<TransDto, Any>? = if (instance is Icall<*, *>) {
        @Suppress("UNCHECKED_CAST")
        instance as Icall<TransDto, Any>
    } else {
        null
    }

    //===========rds money frm acc



    //============aop trans begn
    TransactMng.openSessionBgnTransact()


    val uname="9999"
    val objU = HbntUtil.findByHerbinateLockForUpdt<Usr>(
        Usr::class.java,
        uname ,
        AppConfig.sessionFactory.currentSession
    )
   // TransHdr.curLockAcc.set(objU)
    val dto = TransDto()
    dto.uname = uname
    dto.changeAmount = BigDecimal("888888888")
    dto.amt=dto.changeAmount;
  //  dto.lockAccObj = objU
    bean?.main(dto);


    TransactMng.commitTsact()


}