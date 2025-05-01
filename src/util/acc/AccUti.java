package util.acc;

import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.AccountType;

public class AccUti {

    public static  String getAccId4ylwlt(String uname) {
        return  uname+"_"+ AccountSubType.GeneralInvestment;
    }


    public static String getAccId(String accountSubType, String uname) {
        if(accountSubType.equals(AccountSubType.EMoney.name()) )
            return  uname;
        else
            return uname+"_"+accountSubType;

    }
}
