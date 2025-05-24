package util.acc;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.AccountType;

public class AccUti {

    public  static String  sysusrName = "sys2025007";

    public static  String getAccId4ylwlt(String uname) {
        return  uname+"_"+ AccountSubType.GeneralInvestment;
    }

public  static  String getAccid(String accountSubType, String uname){
        return getAccId(accountSubType,uname);
}

    public  static  @NotNull @NotBlank String getAccid(AccountSubType accountSubType, String owner) {
        return getAccid(accountSubType.name(),owner);
    }
    public static String getAccId(String accountSubType, String uname) {

        return uname+"_"+accountSubType;

    }
}
