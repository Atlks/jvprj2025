package Usr;

import entityx.usr.NonDto;
import handler.admin.ListAdmHdr;
import handler.ivstAcc.dto.QueryDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.*;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.Test;
import util.dbuke.EntityManagerImpltBase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static handler.acc.IniAcc.iniTwoWlt;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static util.acc.AccUti.getAccId;
import static util.algo.GetUti.getUuid;
import static util.misc.Util2025.encodeJson;
import static util.oo.TimeUti.calcEndtime;
import static util.oo.TimeUti.calcStarttime;
import static util.tx.HbntUtil.*;

public class testAddUserTxData extends BaseTest{


    @Test
    public void testHandleRequest() throws Throwable {

      //  iniTwoWlt("666");
      rechg(TransactionCode.payment_rechg, 1);
//
//        trsf();
//wthd();

generStatmt();




     // Assert
    //    assertNotNull(result, "返回结果不应为 null");

        // 根据返回对象类型进行具体断言（示例假设是字符串）
        // assertTrue(result instanceof String);
        //  assertEquals("处理成功: test123", result); // 示例断言，按你的返回值修改
    }

    private static void rechg(TransactionCode payment_rechg, int val) {
        Transaction t = new Transaction();
        t.setTransactionId(getUuid());
        t.owner = "666";
        t.setAccountId(getAccId(AccountSubType.EMoney, t.owner));
        t.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
        t.setTransactionCode(payment_rechg);
        t.setAmount(BigDecimal.valueOf(val));
        persist(t);
    }

    private void generStatmt() {
        String sql="select account_id from accounts where account_Type='PERSONAL'";
        // 使用原生SQL查询
        Session session=sessionFactory.getCurrentSession();
        NativeQuery<?> query = session.createNativeQuery(sql,String.class);
        List<String> resultList = (List<String>) query.getResultList();

        // 遍历结果
        for (Object acc : resultList) {
            if (acc != null) {
                System.out.println("Account ID: " + acc.toString());
                geneSttmtMonthly(geneStatmtRefCurrMthPart(),acc.toString());
            }
        }


    }

    private void geneSttmtMonthly(String geneStatmtRefCurrMth, String accid) {
        Statement stt=new Statement();
var statementId=accid+geneStatmtRefCurrMth;
stt.setStatementId(statementId);
        stt.setAccountId(accid);
        stt.setType(StatementType.xMonthly);
        stt.setStartDateTime(calcStarttime(geneStatmtRefCurrMth));
        stt.setEndDateTime(calcEndtime(geneStatmtRefCurrMth));
        stt.setStatementReference(geneStatmtRefCurrMth+"-monthly");

        stt.setRechgAmt(sumAmt(TransactionCode.payment_rechg,accid));
        stt.setTransferExchgAmt(sumAmt(TransactionCode.InternalTransfers_exchg,accid));
        stt.setWithdrawAmt(sumAmt(TransactionCode.Payment_wthdr,accid));
       // StatementAmount a=new StatementAmount();
      //  a.set
        mergex(stt);
    }

    private BigDecimal sumAmt(TransactionCode transactionCode, String accid) {
        String sql="select sum(amount) from Transactions where Transaction_Code='"+transactionCode.name()+"' and account_id='"+accid+"'";

        Session session=sessionFactory.getCurrentSession();
        NativeQuery<?> query = session.createNativeQuery(sql);
        BigDecimal result = (BigDecimal) query.getSingleResult();
        return result;

    }



    private String geneStatmtRefCurrMthPart() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String monthStr = now.format(formatter);
        return monthStr ;
    }


    /**
     * 生成 statmtRef,当前月份，  格式2025-11-monthly
     * @return
     */
    private String geneStatmtRefCurrMth() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String monthStr = now.format(formatter);
        return monthStr + "-monthly";
    }

    private void trsf() {
        rechg(TransactionCode.InternalTransfers_exchg, 67);

    }


    private void wthd() {
        Transaction t = new Transaction();  t.setTransactionId(getUuid());
        t.owner="666";
        t.setAccountId(getAccId(AccountSubType.GeneralInvestment,t.owner));

        t.setTransactionCode(TransactionCode.Payment_wthdr);
        t.setAmount(BigDecimal.valueOf(65));
        t.setCreditDebitIndicator(CreditDebitIndicator.DEBIT);
        persist(t);

    }


}
