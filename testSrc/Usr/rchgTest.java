package Usr;

import handler.rechg.RchgHdr;
import handler.rechg.RechgDto;
import handler.wlt.AdjustHdr;
import handler.wlt.dto.AdjstDto;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.TransactionCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import orgx.uti.context.ProcessContext;
import orgx.uti.context.ThreadContext;

import java.math.BigDecimal;

import static cfg.Containr.sessionFactory;
// static handler.acc.AccService.addAmtUpdtAcc;
import static handler.acc.IniAcc.addAccEmnyIfNotExst;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static util.acc.AccUti.getAccid;
import static util.misc.Util2025.encodeJson;
import static util.tx.HbntUtil.findById;
import static util.tx.TransactMng.commitx;

public class rchgTest extends BaseTest {


    @AfterEach
    public void setupAftEach() throws Exception {
        commitx();
    }
    @Test
    public void testHandleRequest() throws Throwable {
        ProcessContext.isTestMode=true;
        String uuuu1111 = "uuuu1111";
        ThreadContext.remoteUser.set(uuuu1111);

        Account acc=findById(Account.class,getAccid(uuuu1111,AccountSubType.EMoney));
      //  addAmtUpdtAcc(acc, BigDecimal.valueOf(9999));

        RechgDto mockDto =new RechgDto();
        mockDto.owner=uuuu1111;
        mockDto.addressLine="trxxxxxx";
        mockDto.receipt_image="dsafdaf.jpg";
         mockDto.amount=new BigDecimal("100.00");





        // Act
        // Arrange
        RchgHdr handler = new RchgHdr();
        Object result = handler.rchg(mockDto);

        System.out.println(encodeJson(result));
        // Assert
        assertNotNull(result, "返回结果不应为 null");

        // 根据返回对象类型进行具体断言（示例假设是字符串）
       // assertTrue(result instanceof String);
      //  assertEquals("处理成功: test123", result); // 示例断言，按你的返回值修改
    }
}

