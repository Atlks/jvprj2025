

ref hbnt6.6
 auto crt db,table ,col

=OrmMysql

public class OrmMysql extends OrmBase {

    public   String _toSqlType(Class javatype) {
        return switch (javatype.getSimpleName()) {
            case "String" -> "VARCHAR(999)";
            case "int", "Integer" -> "INT";
            case "long", "Long" -> "BIGINT";
            case "BigDecimal" -> "DECIMAL(20, 2)";
            default -> "VARCHAR(999)";
        };
    }


==OrmBaseItfs
public interface OrmBaseItfs {


    <T> T find(Class<T> objClass, Object id) throws Exception;

    <T> T merge(T var1) throws Exception;
    <T> T persist(T obj) throws Exception;

    String _toSqlType(Class<? extends Field> aClass);
}

== orm base


/**
* save new
*
* @param obj
* @param <T>
* @return
*/
public <T> T persist(T obj) throws Exception {
Connection conn = DriverManager.getConnection(jdbcurl);
Statement stmt = conn.createStatement();
//  create database db2


        //--------cret db todo
        String tbnm = (String) OrmBase.getTableNameFromObjClass(obj.getClass());
        stmt.execute("CREATE TABLE IF NOT EXISTS " + tbnm + " (id VARCHAR(500) PRIMARY KEY)");


        _CreateColumes(obj, stmt, tbnm, jdbcurl);


        //--------crt  sql insert
        String us = encodeJson(obj);
        String sql = "";
        String id = (String) getField2025(obj, "id", "");
        String cols = getCols(obj);
        String valss = getValsSqlFmt(obj);
        sql = "INSERT INTO " + tbnm + "  (" + cols + ") VALUES (" + valss + ")";
        System.out.println(sql);
        int i = stmt.executeUpdate(sql);
        return obj;
    }

