package Usr;

import handler.cms.WthdCmsRqdto;
import handler.cms.WthdrCmsHdl;
import org.junit.jupiter.api.Test;

public class testWthdrCmsHdl  extends BaseTest {

    @Test
    public void testHandleRequest() throws Throwable {
        WthdCmsRqdto dto = new WthdCmsRqdto();
        dto.setUname("uuuu6666");
new WthdrCmsHdl().handleRequest(dto);

    }

}
