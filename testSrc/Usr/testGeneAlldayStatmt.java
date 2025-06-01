package Usr;

import org.junit.jupiter.api.Test;

import static handler.statmt.StatmtService.geneStatmtTodateType;

public class testGeneAlldayStatmt extends BaseTest{


    @Test
    public void testHandleRequest() throws Throwable {

        geneStatmtTodateType();
    }
}
