package handler.rpt;


import entityx.usr.NonDto;
import jakarta.annotation.security.PermitAll;
import model.OpenBankingOBIE.Transaction;
import model.OpenBankingOBIE.TransactionCode;
import model.rpt_dataSmry.DataSummaryVo;
import model.rpt_dataSmry.MonthRechgStats;
import util.Oosql.SlctQry;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static cfg.Containr.sessionFactory;
import static handler.trx.TransactnService.*;
import static util.Oosql.SlctQry.newSelectQuery;
import static util.Oosql.SlctQry.toValStr;
import static util.algo.GetUti.getTableName;
import static util.oo.DatetimeUti.*;
import static util.tx.HbntUtil.getSingleResult;

// rpt/DataSummray
@PermitAll
public class DataSummray {

    public Object handleRequest(NonDto param) throws Throwable {

        DataSummaryVo vo = new DataSummaryVo();
        vo.setTotalRecharge(getSumAmtByTrxtype(TransactionCode.payment_rechg.name()));
        vo.setTotalCommission(getSumAmtByTrxtype(TransactionCode.Service_Cms_rechgCms.name()));
        vo.setTotalExchange(getSumAmtByTrxtype(TransactionCode.InternalTransfers_exchgIn.name()));
        vo.setTotalProfit(getSumAmtByTrxtype(TransactionCode.invstProfit.name()));
        vo.setTotalReward(getSumAmtByTrxtype(TransactionCode.invstProfit.name()));
        vo.setTotalAdjustment(getSumAmtByTrxtype(TransactionCode.adjst_frz.name()));
        vo.setTotalWithdraw(getSumAmtByTrxtype(TransactionCode.Payment_wthdr.name()));
        vo.setCurrentMonthRechargeAmount(getMonthRchgAmt(getCurrYearMonth()));
        vo.setCurrentMonthRechargeUsers(getMonthRchgUserCnt(getCurrYearMonth()));
        for(int i=1;i<=5;i++)
        {
            String curYearMonth = lastMonth(i);
            String startTime = getMonthStartDatetime(curYearMonth);
            String endTime = getMonthEndDatetime(curYearMonth);
            MonthRechgStats mrs = new MonthRechgStats();
            mrs.setYr_month(curYearMonth);
            mrs.setMonthRechargeAmount(getSumAmtByBkDttm(startTime, endTime));
            mrs.setMonthRechargeUsersCnt( getCntByBkDttm(startTime, endTime));
            vo.getMonthRechgStatsList().add(mrs);

        }
        return vo;
    }









}
