package Usr;

import entityx.wlt.TransDto;
import handler.rechg.RchgHdr;
import handler.rechg.RechgDto;
import handler.wlt.DepositDto;
import handler.wlt.TransfHdr;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.TransactionCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import orgx.uti.context.ProcessContext;
import orgx.uti.context.ThreadContext;

import java.math.BigDecimal;

import static handler.acc.AccService.deposit;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static util.acc.AccUti.getAccid;
import static util.misc.Util2025.encodeJson;
import static util.tx.HbntUtil.findById;
import static util.tx.TransactMng.commitx;

public class TrsfT extends BaseTest {


    @AfterEach
    public void setupAftEach() throws Exception {
        commitx();
    }
    @Test
    public void testHandleRequest() throws Throwable {
        String uuu = "uuuu6666";
        DepositDto depositDto = new DepositDto();
        depositDto.accid=getAccid(uuu,AccountSubType.EMoney);
        depositDto.amt= BigDecimal.valueOf(6666);
        depositDto.type= TransactionCode.payment_rechg;
        deposit(depositDto);

        ProcessContext.isTestMode=true;

        ThreadContext.remoteUser.set(uuu);
      //  TransDto transDto = new TransDto();
        TransDto dto = new TransDto();
        dto.id = "trans123";
        dto.uname = uuu; // @NotBlank 会通过
        dto.adjustType = "incrs";
        dto.setAmount(new BigDecimal("100.00"));
        dto.changeTime = System.currentTimeMillis();
       // dto.amtBefore = new BigDecimal("1000.00");

        Account acc=findById(Account.class,getAccid(uuu,AccountSubType.EMoney));
      //  addAmtUpdtAcc(acc, BigDecimal.valueOf(9999));






        // Act
        // Arrange
        TransfHdr handler = new TransfHdr();
        Object result = handler.handleRequest(dto);

        System.out.println(encodeJson(result));
        // Assert
        assertNotNull(result, "返回结果不应为 null");

        // 根据返回对象类型进行具体断言（示例假设是字符串）
       // assertTrue(result instanceof String);
      //  assertEquals("处理成功: test123", result); // 示例断言，按你的返回值修改
    }
}

