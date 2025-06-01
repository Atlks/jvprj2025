package Usr;

import model.OpenBankingOBIE.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static handler.statmt.StatmtService.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static util.acc.AccUti.getAccId;
import static util.algo.GetUti.getUuid;
import static util.tx.HbntUtil.*;

public class testAddUserTxData extends BaseTest{


    @Test
    public void testHandleRequest() throws Throwable {

      //  iniTwoWlt("666");
      rechg(TransactionCode.payment_rechg, 1);
//
//        trsf();
//wthd();

generStatmtCurMth();




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
