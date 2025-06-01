package handler.rpt;


import entityx.usr.NonDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import model.OpenBankingOBIE.Statement;
import model.OpenBankingOBIE.StatementType;
import model.OpenBankingOBIE.TransactionCode;
import model.rpt_dataSmry.DataSummaryVo;
import model.rpt_dataSmry.MonthRechgStats;
import util.oo.TimeUti;

import java.time.OffsetDateTime;

// static handler.statmt.StatmtService.sumAmtWhereTxtype;
import static handler.trx.TransactnService.*;
import static handler.trx.TxDao.*;
import static util.oo.DatetimeUti.*;
import static util.oo.TimeUti.calcEndtime;
import static util.tx.HbntUtil.getSingleResult;

/**
 * statmt sysuser ，alltime
 */
// rpt/DataSummray
@PermitAll
@Path("/apiv1/rpt/DataSummray")
public class DataSummray {

    public Object handleRequest(NonDto param) throws Throwable {

        DataSummaryVo vo = new DataSummaryVo();
        vo.setTotalRecharge(getSumAmtByTrxtype(TransactionCode.payment_rechg));
        vo.setTotalCommission(getSumAmtByTrxtype(TransactionCode.Service_Cms_rechgCms));
        vo.setTotalExchange(getSumAmtByTrxtype(TransactionCode.InternalTransfers_exchg));
        vo.setTotalProfit(getSumAmtByTrxtype(TransactionCode.invstProfit));
        vo.setTotalReward(getSumAmtByTrxtype(TransactionCode.invstProfit));
        vo.setTotalAdjustment(getSumAmtByTrxtype(TransactionCode.adjst_frz));
        vo.setTotalWithdraw(getSumAmtByTrxtype(TransactionCode.Payment_wthdr));


        OffsetDateTime CurMonstartTime = TimeUti.getCurrMonthStarttime();
        OffsetDateTime CurMon_endTime =TimeUti.getCurrMonthEndtime();
        vo.setCurrentMonthRechargeAmount(sumAmtWhereTxtype(TransactionCode.payment_rechg, CurMonstartTime, CurMon_endTime));
        vo.setCurrentMonthRechargeUsers(getCntByBkDttm(CurMonstartTime, CurMon_endTime));
        for(int i=1;i<=5;i++)
        {
            String curYearMonth = lastMonth(i);
            OffsetDateTime startTime = TimeUti.calcStarttime(curYearMonth);
            OffsetDateTime endTime = calcEndtime(curYearMonth);
          //  MonthRechgStats mrs = new MonthRechgStats();
            Statement mrs=new Statement();
            mrs.setStatementReference(curYearMonth);
            mrs.setType(StatementType.xMonthly);
            mrs.setOwner("sys");
           // mrs.setYr_month(curYearMonth);
            mrs.setRechgAmt(sumAmtWhereTxtype(TransactionCode.payment_rechg, startTime, endTime));
            mrs.setRechgUsersCnt( getCntByBkDttm(startTime, endTime));
            vo.getStatementList().add(mrs);

        }
        return vo;
    }









}
