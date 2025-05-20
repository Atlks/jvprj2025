package Usr;

import handler.fundDetail.QryFundDetailHdl;
import handler.fundDetail.qryFdDtl.QryFundDetailRqdto;
import handler.wlt.AdjustHdr;
import handler.wlt.dto.AdjstDto;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.TransactionCode;
import org.junit.jupiter.api.Test;

import static cfg.Containr.sessionFactory;
import static handler.acc.IniAcc.addAccEmnyIfNotExst;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static util.misc.Util2025.encodeJson;

public class testQryfdDtl  extends BaseTest{


    @Test
    public void testHandleRequest() throws Throwable {

        QryFundDetailRqdto dto = new QryFundDetailRqdto();
        dto.uname="666";


        // Act
        // Arrange
        QryFundDetailHdl handler = new QryFundDetailHdl();
        Object result = handler.handleRequest(dto);

        System.out.println(encodeJson(result));
        // Assert
        assertNotNull(result, "返回结果不应为 null");

        // 根据返回对象类型进行具体断言（示例假设是字符串）
        // assertTrue(result instanceof String);
        //  assertEquals("处理成功: test123", result); // 示例断言，按你的返回值修改
    }
}
