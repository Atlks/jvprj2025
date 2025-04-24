package util.acc;

import model.OpenBankingOBIE.AccountType;

public class AccUti {

    public static  String getAccId4ylwlt(String uname) {
        return  uname+"_"+ AccountType.YlWlt;
    }
}
