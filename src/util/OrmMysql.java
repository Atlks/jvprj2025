package util;

import apiUsr.Usr;


import static util.dbutil.*;
import static util.util2026.getField2025;

public class OrmMysql extends OrmBase {




    public static void main(String[] args) throws Exception {
        OrmMysql orm = new OrmMysql();
        orm.jdbcurl = "jdbc:mysql://localhost:3306/prjdb?user=root&password=pppppp";

        Usr u = new Usr();
        u.id = "888";
        orm.merge(u);


    }



    public   String _toSqlType(Class javatype) {
        return switch (javatype.getSimpleName()) {
            case "String" -> "VARCHAR(999)";
            case "int", "Integer" -> "INT";
            case "long", "Long" -> "BIGINT";
            case "BigDecimal" -> "DECIMAL(20, 2)";
            default -> "VARCHAR(999)";
        };
    }






}
