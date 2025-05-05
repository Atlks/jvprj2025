package util.Oosql;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountStatus;
import model.OpenBankingOBIE.AccountType;
import util.model.openbank.InsertIntoStmt;
import util.model.openbank.ObieAccFields;

import static util.algo.GetUti.getTableName;

public class ormInsertTest {
    public static void main(String[] args) {
        InsertIntoStmt query =new InsertIntoStmt("users");
        query.set("id",1);
        query.setStr("name", "ooo");
        String sql = query.getSQL();
    }
}
