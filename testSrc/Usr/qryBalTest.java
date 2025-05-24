package Usr;

import model.OpenBankingOBIE.Balance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import util.tx.findByIdExptn_CantFindData;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static util.misc.Util2025.encodeJson;
import static util.tx.HbntUtil.findById;
import static util.tx.TransactMng.commitx;

public class qryBalTest extends BaseTest {


    @AfterEach
    public void setupAftEach() throws Exception {
        commitx();
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

