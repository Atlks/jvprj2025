package Usr;

import handler.wlt.AdjustHdr;
import handler.wlt.dto.AdjstDto;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.Balance;
import model.OpenBankingOBIE.TransactionCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import util.tx.findByIdExptn_CantFindData;

import static cfg.Containr.sessionFactory;
import static handler.acc.IniAcc.addAccEmnyIfNotExst;
import static handler.acc.IniAcc.iniTwoWlt;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static util.misc.Util2025.encodeJson;
import static util.tx.HbntUtil.findById;
import static util.tx.TransactMng.commitTsact;

public class qryBalTest extends BaseTest {


    @AfterEach
    public void setupAftEach() throws Exception {
        commitTsact();
    }
    @Test
    public void testHandleRequest() throws Throwable {
       // iniTwoWlt("6789");

        try{
            Balance b=findById(Balance.class,"6789_EMoney_interimAvailable");
            System.out.println(encodeJson(b));
        } catch (findByIdExptn_CantFindData e) {
            e.printStackTrace();
        }







        // Assert
      //  assertNotNull(b, "返回结果不应为 null");

        // 根据返回对象类型进行具体断言（示例假设是字符串）
       // assertTrue(result instanceof String);
      //  assertEquals("处理成功: test123", result); // 示例断言，按你的返回值修改
    }
}

