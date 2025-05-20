package util.model.openbank;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountStatus;
import model.OpenBankingOBIE.AccountType;
import util.Oosql.Column;



import static util.algo.GetUti.getTableName;

public class ormTestsave {

    public static   void main(String[] args) {
        ObieAccFields flds = ObieAccFields.ACCOUNT_ID;


        InsertIntoStmt query =new InsertIntoStmt(getTableName(Account.class));
       query.set(Account.Fields.accountId,1);
        query.set(Account.Fields.accountType,AccountType.BUSINESS.name())    ;
        query.set(ObieAccFields.ACCOUNT_STATUS.getFieldName(),  AccountStatus.Enabled.name());

        query.setStr(ObieAccFields.ACCOUNT_TYPE.getFieldName(), "ooo");
        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);
    }



}
