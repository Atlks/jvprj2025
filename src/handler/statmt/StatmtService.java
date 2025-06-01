package handler.statmt;

import api.ylwlt.BizFun;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import model.OpenBankingOBIE.Statement;
import model.OpenBankingOBIE.StatementType;
import model.OpenBankingOBIE.Transaction;
import model.OpenBankingOBIE.TransactionCode;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static handler.trx.TxDao.sumAmtWhrTxtypeNAccid;
import static util.algo.CallUtil.callTry;
import static util.oo.TimeUti.calcEndtime;
import static util.oo.TimeUti.calcStarttime;
import static util.tx.HbntUtil.mergex;
import static util.tx.HbntUtil.sessionFactory;

public class StatmtService {
    @PermitAll
    @BizFun
    @Path("/apiv1/statmt/geneStatmtCurrMth")
    public static void generStatmtCurMth() {
        String sql = "select account_id from accounts where account_Type='PERSONAL'";
        // 使用原生SQL查询
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<?> query = session.createNativeQuery(sql, String.class);
        List<String> resultList = (List<String>) query.getResultList();

        // 遍历结果
        for (Object acc : resultList) {
            if (acc != null) {
                System.out.println("Account ID: " + acc.toString());
                callTry(()->{
                    geneSttmtMonthly(geneStatmtRefCurrMthPart(), acc.toString());
                });

            }
        }


    }

    @PermitAll
    @BizFun
    @Path("/apiv1/statmt/geneStatmtLastMth")
    public static void geneStatmtLastMth() {
        String sql = "select account_id from accounts where account_Type='PERSONAL'";
        // 使用原生SQL查询
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<?> query = session.createNativeQuery(sql, String.class);
        List<String> resultList = (List<String>) query.getResultList();

        // 遍历结果
        for (Object acc : resultList) {
            if (acc != null) {
                System.out.println("Account ID: " + acc.toString());
                geneSttmtMonthly(geneStatmtRefLastMthPart(), acc.toString());
            }
        }


    }

    @PermitAll
    @BizFun
    @Path("/apiv1/statmt/geneStatmtAlltime")
    public static void geneStatmtTodateType() {
        String sql = "select owner from accounts where account_Type='PERSONAL'";
        // 使用原生SQL查询
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<?> query = session.createNativeQuery(sql, String.class);
        List<String> resultList = (List<String>) query.getResultList();

        // 遍历结果
        for (Object acc : resultList) {
            if (acc != null) {
                System.out.println("Account ID: " + acc.toString());
                callTry(()->{
                    geneSttmtTodate(acc.toString());
                });

            }
        }


    }
    public static void geneSttmtTodate(String owner) {
        Statement stt = new Statement();
        var statementId = owner + "_todate";
        stt.setStatementId(statementId);
        stt.setAccountId("allAcc");
        stt.setType(StatementType.xTodate);
        stt.setStartDateTime(calcStarttime("1900-11"));
        stt.setEndDateTime(calcEndtime("2030-11"));
        stt.setStatementReference( "todate");

        stt.setRechgAmt(sumAmtByOwner(TransactionCode.payment_rechg, owner));
        stt.setTransferExchgAmt(sumAmtByOwner(TransactionCode.InternalTransfers_exchg, owner));
        stt.setWithdrawAmt(sumAmtByOwner(TransactionCode.Payment_wthdr, owner));
        // StatementAmount a=new StatementAmount();
        //  a.set
        mergex(stt);
    }

    public static BigDecimal sumAmtByOwner(TransactionCode transactionCode, String owner) {
        String sql = "select sum(amount) from Transactions where Transaction_Code='" + transactionCode.name() + "' and "+ Transaction.Fields.owner +"='" + owner + "'";

        Session session = sessionFactory.getCurrentSession();
        NativeQuery<?> query = session.createNativeQuery(sql);
        BigDecimal result = (BigDecimal) query.getSingleResult();
        return result;

    }
    public static void geneSttmtMonthly(String geneStatmtRefCurrMth, String accid) {
        Statement stt = new Statement();
        var statementId = accid + geneStatmtRefCurrMth;
        stt.setStatementId(statementId);
        stt.setAccountId(accid);
        stt.setType(StatementType.xMonthly);
        OffsetDateTime startDateTime = calcStarttime(geneStatmtRefCurrMth);
        stt.setStartDateTime(startDateTime);

        OffsetDateTime endDateTime = calcEndtime(geneStatmtRefCurrMth);
        stt.setEndDateTime(endDateTime);
        stt.setStatementReference(geneStatmtRefCurrMth + "-monthly");

        stt.setRechgAmt(sumAmtWhrTxtypeNAccid(TransactionCode.payment_rechg, accid,startDateTime,endDateTime));
        stt.setTransferExchgAmt(sumAmtWhrTxtypeNAccid(TransactionCode.InternalTransfers_exchg, accid, startDateTime, endDateTime));
        stt.setWithdrawAmt(sumAmtWhrTxtypeNAccid(TransactionCode.Payment_wthdr, accid, startDateTime, endDateTime));
        // StatementAmount a=new StatementAmount();
        //  a.set
        mergex(stt);
    }





    /**
     * 生成上个月时间
     *
     * @return 返回时间，格式  2025-01
     */
    public static String geneStatmtRefLastMthPart() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        // 获取当前日期，减去一个月
        LocalDate lastMonth = LocalDate.now().minusMonths(1);

        // 格式化为字符串
        return lastMonth.format(formatter);
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

    }


    public static String geneStatmtRefCurrMthPart() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String monthStr = now.format(formatter);
        return monthStr;
    }


    /**
     * 生成 statmtRef,当前月份，  格式2025-11-monthly
     *
     * @return
     */
    public static String geneStatmtRefCurrMth() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String monthStr = now.format(formatter);
        return monthStr + "-monthly";
    }

}
