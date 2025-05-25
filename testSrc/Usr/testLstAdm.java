package Usr;

import entityx.usr.NonDto;
import handler.admin.ListAdmHdr;
import handler.ivstAcc.ListInvtCmsLogHdl;
import handler.ivstAcc.dto.QueryDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static util.misc.Util2025.encodeJson;

public class testLstAdm extends BaseTest{


    @Test
    public void testHandleRequest() throws Throwable {

        QueryDto dto = new QueryDto();
        dto.uname="666";


        // Act
        // Arrange
        ListAdmHdr handler = new ListAdmHdr();
//        Object result = handler.handleRequest(new NonDto());
//
//        System.out.println(encodeJson(result));
//        // Assert
//        assertNotNull(result, "返回结果不应为 null");

        // 根据返回对象类型进行具体断言（示例假设是字符串）
        // assertTrue(result instanceof String);
        //  assertEquals("处理成功: test123", result); // 示例断言，按你的返回值修改
    }
}
