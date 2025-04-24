package handler.ylwlt

import cfg.AppConfig
import cfg.Containr
import cfg.Containr.sessionFactory
import cfg.MyCfg
import entityx.usr.Usr
import handler.agt.AgtHdl
import handler.ylwlt.dto.QueryDto
import model.OpenBankingOBIE.CreditDebitIndicator
import model.OpenBankingOBIE.TransactionCodes
import model.OpenBankingOBIE.TransactionStatus
import model.OpenBankingOBIE.Transactions
import util.acc.AccUti.getAccId4ylwlt
import util.evtdrv.EvtUtil
import util.tx.HbntUtil
import util.tx.TransactMng
import ztoolsMy.getUuid
import java.math.BigDecimal
import java.util.function.Consumer

fun main() {


    befTest()

    var QueryDto1: QueryDto =  QueryDto("666")

println( ListBetWinLogHdl().handleRequest(QueryDto1,null))
    //addTrxDiv()

    TransactMng.commitTsact()
}

private fun addTrxDiv() {
    var accid = getAccId4ylwlt("666")

    val dto: Transactions =
        Transactions(accid, "666", CreditDebitIndicator.CREDIT, BigDecimal(1))  // 可以省略类型，Kotlin 自动推断类型
    // dto.uname = "666"
    //  dto.creditDebitIndicator= CreditDebitIndicator.CREDIT;
    //  dto.transactionId="div_"+ getUuid();
    dto.transactionCode = TransactionCodes.DIV;
    dto.transactionStatus = TransactionStatus.BOOKED;
    //   dto.amount = BigDecimal(1);
    HbntUtil.persistByHibernate(dto, sessionFactory.currentSession)
}

private fun befTest() {
    //--------ini saveurlFrm Cfg
    AppConfig().sessionFactory() //ini sessFctr

    //ini contnr 4cfg,, svrs
    MyCfg.iniContnr()
    EvtUtil.iniEvtHdrCtnr()

    Containr.evtlist4reg.add(Consumer { u: Usr? -> AgtHdl().regEvtHdl(u) })
    //============aop trans begn
    TransactMng.openSessionBgnTransact()

}