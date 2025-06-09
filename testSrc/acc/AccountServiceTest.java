package acc;

import Usr.BaseTest;
import model.OpenBankingOBIE.Account;
import org.junit.jupiter.api.Test;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

import static handler.acc.AccService.addBls2AgtcmsAcc;
import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest extends BaseTest {

    @Test
    public void testAddBls2AgtcmsAcc() throws findByIdExptn_CantFindData {
        String owner = "uuuu66668";
        BigDecimal addAmt = new BigDecimal("800");

        Account result = addBls2AgtcmsAcc(owner, addAmt);

        System.out.println(result);

//        assertNotNull(result);
//        assertEquals(owner, result.getOwner());
//        assertEquals(new BigDecimal("150.50"), result.getBalance());
    }
}

