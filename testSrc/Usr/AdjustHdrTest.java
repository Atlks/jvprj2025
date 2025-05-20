package Usr;

import handler.wlt.AdjustHdr;
import handler.wlt.dto.AdjstDto;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.TransactionCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static cfg.Containr.sessionFactory;
import static handler.acc.IniAcc.addAccEmnyIfNotExst;
import static org.junit.jupiter.api.Assertions.*;
import static util.misc.Util2025.encodeJson;
import static util.tx.TransactMng.commitTsact;

public class AdjustHdrTest extends BaseTest {


    @AfterEach
    public void setupAftEach() throws Exception {
        commitTsact();
    }
    @Test
    public void testHandleRequest() throws Throwable {



        AdjstDto mockDto =new AdjstDto();
        mockDto.uname="666";
        mockDto.setTransactionCode(TransactionCode.adjst_crdt.name());
        mockDto.setAdjustAmount(BigDecimal.valueOf(8.0));
        mockDto.setAccountSubType(AccountSubType.EMoney.name());
       // when(mockDto.getId()).thenReturn("test123");
      //  when(mockDto.getValue()).thenReturn(42);

        addAccEmnyIfNotExst(mockDto.uname,sessionFactory.getCurrentSession());




        // Act
        // Arrange
        AdjustHdr handler = new AdjustHdr();
        Object result = handler.handleRequest(mockDto);

        System.out.println(encodeJson(result));
        // Assert
        assertNotNull(result, "返回结果不应为 null");

        // 根据返回对象类型进行具体断言（示例假设是字符串）
       // assertTrue(result instanceof String);
      //  assertEquals("处理成功: test123", result); // 示例断言，按你的返回值修改
    }
}

