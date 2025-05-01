package handler.wltadrs;

import entityx.usr.Usr;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.lang.Record;

import static util.algo.GetUti.getTableName;

public class testSqlBldr {
    public static void main(String[] args) {

        Table<?> USER = DSL.table(getTableName(Usr.class));
        Field<String> uname = DSL.field("uname", String.class);

        DSLContext create = DSL.using(SQLDialect.MYSQL);
        SelectQuery<?> query = create.selectQuery();

        query.addFrom(USER);
        query.addConditions(uname.eq("John"));
        String sql = query.getSQL(ParamType.INLINED);  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);
    }


}
