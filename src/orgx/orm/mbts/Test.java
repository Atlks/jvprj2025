package orgx.orm.mbts;

import orgx.orm.Column;
import orgx.orm.Table;

import static orgx.orm.mbts.CrtTblUti.generateCreateTableSQL;
import static orgx.orm.mbts.Mbts2Table.buildTableModel;

public class Test {


    public static void main(String[] args) {
       


        Column c;
        //  String  sql=generateInsertSql(account);
        // sql=generateUpdateSql(account);
        //  System.out.println(sql);


        Table table = null;
        System.out.println(table);
        System.out.println(generateCreateTableSQL(table));

    }
}
