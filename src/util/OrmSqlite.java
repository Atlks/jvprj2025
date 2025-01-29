package util;

import apiUsr.Usr;

import java.lang.reflect.Field;

public class OrmSqlite extends OrmBase{

    public static void main(String[] args) throws Exception {
        OrmMysql orm = new OrmMysql();
        orm.jdbcurl = "jdbc:sqlite:/dbx/usr22.db";

        Usr u = new Usr();
        u.id = "888";
        orm.merge(u);



    }

    @Override
    public String _toSqlType(Class javatype) {
        return switch (javatype.getSimpleName()) {
            case "String" -> "text";
            case "Integer", "int" -> "integer";
            case "Long", "long" -> "integer";
            case "BigDecimal" -> "real";
            default -> "text";
        };
    }
}
