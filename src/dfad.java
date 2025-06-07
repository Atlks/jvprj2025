import com.google.common.math.BigIntegerMath;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class dfad {


    public static void main(String[] args) {
        BigDecimal accEmng_interim_Available_Balance= BigDecimal.valueOf(8888);
        BigDecimal sysEmnyBls= BigDecimal.valueOf(999999);
        BigDecimal rate= accEmng_interim_Available_Balance.divide(sysEmnyBls,20, RoundingMode.HALF_UP);
        System.out.println(rate);
    }
}
