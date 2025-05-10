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
import static handler.trx.TransactnService.getSumAmtByBkDttm;
import static handler.trx.TransactnService.getSumAmtByTrxtype;
import static util.Oosql.SlctQry.newSelectQuery;
import static util.Oosql.SlctQry.toValStr;
import static util.algo.GetUti.getTableName;
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

    /**
     * 功能，计算以前几个月的月份
     * 得到月份 格式  yyyy-mm
     * @param n  当前月要减去的月份  1-50
     * @return
     */
    private String lastMonth(int n) {

        LocalDate targetMonth = LocalDate.now().minusMonths(n);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return targetMonth.format(formatter);
    }

    private long getMonthRchgUserCnt(int yearMonth) {

        String startTime = getMonthStartDatetime(yearMonth);
        String endTime = getMonthEndDatetime(yearMonth);
        return getCntByBkDttm(startTime, endTime);
    }

    private long getCntByBkDttm(String startTime, String endTime) {

        SlctQry query = newSelectQuery(getTableName(Transaction.class));
        query.select("count(*)");

        query.addConditions(Transaction.Fields.transactionCode + "=" + toValStr(TransactionCode.payment_rechg.name()));
        query.addConditions(Transaction.Fields.bookingDateTime + ">=" + toValStr(startTime));
        query.addConditions(Transaction.Fields.bookingDateTime + "<=" + toValStr(endTime));

        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);
        try {
            return (long) getSingleResult(sql, sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            return 0;
        }

    }

    //格式202505  ,获取当钱年份月份
    private int getCurrYearMonth() {
        LocalDate now = LocalDate.now();
        return now.getYear() * 100 + now.getMonthValue();
    }

    /**
     * @param yearMonth 202505
     * @return
     */
    private BigDecimal getMonthRchgAmt(int yearMonth) {
        String startTime = getMonthStartDatetime(yearMonth);
        String endTime = getMonthEndDatetime(yearMonth);
        return getSumAmtByBkDttm(startTime, endTime);

    }

    /**
     * 得到本月月末日期   格式2025-05-31
     *
     * @param yearMonth 202505
     * @return
     */
    private String getMonthEndDatetime(int yearMonth) {
        int year = yearMonth / 100;
        int month = yearMonth % 100;
        YearMonth ym = YearMonth.of(year, month);
        return ym.atEndOfMonth().format(FORMATTER);
    }
    private String getMonthEndDatetime(String yearMonth) {
        YearMonth ym = YearMonth.parse(yearMonth); // 例如 "2025-05"
        return ym.atEndOfMonth().toString(); // 自动输出为 yyyy-MM-dd 格式
    }



    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    /**
     * 得到月初的字符串格式  2025-05-01
     *
     * @param yearMonth 2025-05
     * @return
     */
    private String getMonthStartDatetime(String yearMonth) {
        LocalDate date = LocalDate.parse(yearMonth + "-01");
        return date.toString(); // 自动格式为 yyyy-MM-dd

    }



    /**
     * 得到月初的字符串格式  2025-05-01
     *
     * @param yearMonth 202505
     * @return
     */
    private String getMonthStartDatetime(int yearMonth) {
        int year = yearMonth / 100;
        int month = yearMonth % 100;
        YearMonth ym = YearMonth.of(year, month);
        return ym.atDay(1).format(FORMATTER);
    }
}
