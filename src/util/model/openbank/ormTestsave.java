package util.model.openbank;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountStatus;
import model.OpenBankingOBIE.AccountType;
import util.Oosql.Column;

import static util.Oosql.Column.newColumn;

import static util.algo.GetUti.getTableName;

public class ormTestsave {

    public static   void main(String[] args) {
        ObieAccFields flds = ObieAccFields.ACCOUNT_ID;


        InsertIntoStmt query =new InsertIntoStmt(getTableName(Account.class));
       query.set(ObieAccFields.ACCOUNT_ID.getFieldName(),new Column(1,Integer.class));
        query.set(ObieAccFields.ACCOUNT_SUB_TYPE.getFieldName(),newColumn(AccountType.BUSINESS,String.class) );
        query.set(newColumn(ObieAccFields.ACCOUNT_STATUS.getFieldName(),  AccountStatus.Enabled.name(),String.class));


        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);
    }
}
