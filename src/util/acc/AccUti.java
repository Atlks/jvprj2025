package util.acc;

import model.OpenBankingOBIE.AccountType;

public class AccUti {

    public static  String getYlAccId(String uname) {
        return  uname+"_"+ AccountType.YlWlt;
    }
}
