package util;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;


import static util.ColorLogger.*;
import static util.Util2025.encodeJson;
import static util.dbutil.*;
import static util.util2026.copyProps;
import static util.util2026.getField2025;

public abstract class OrmBase implements Session {
 //   String toSqlType(Class value);
 public String jdbcurl;

    /**
     * findByid  session.find(class1, id);
     *
     * @param objClass
     * @param id
     * @param <T>
     * @return
     */
    @Override
    public <T> T find(Class<T> objClass, Object id) throws Exception {
        System.out.println("\r\n\r\n");
        //   System.out.println("fun merge(");
        String runEmoji = "▶️";
        printLn("▶️fun find(", BLUE);
        printLn("objClass=" + objClass, GREEN);
        printLn("id=" + id, GREEN);
        System.out.println(")");

        var tbnm = getTableNameFromObjClass(objClass);
        var idFld= _getIdField(objClass);
        var sql = "select * from " + tbnm + " where "+idFld+"='" + id + "'";
        try {
            T obj = toObj(qrySqlAsMap(sql, jdbcurl), objClass);
            System.out.println("endfun find()");
            return obj;
        } catch (Exception e) {
            if (e.getMessage().contains("no such table: tab1"))
                return objClass.newInstance();
            throw new RuntimeException(e);
        }
    }

    /**
     * 查找objClass 中的字段，如果字段有注解 @Id ，(jakarta.persistence.Id)
     * 那么提取返回此字段的名称
     * @param objClass
     * @return
     * @param <T>
     */
    private <T> String _getIdField(Class<T> objClass) {
        for (Field field : objClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }
        throw  new RuntimeException(objClass+" dont have anno @Id");
       // return null; // 未找到 @Id 注解的字段
    }

    /**
     * add or updt
     *
     * @param obj
     * @param <T>
     * @return
     */
    @Override
    public <T> T merge(T obj) throws Exception {
        System.out.println("\r\n\r\n");
        //   System.out.println("fun merge(");
        String runEmoji = "▶️";
        printLn("▶️fun merge(", BLUE);
        printLn("obj=" + encodeJson(obj), GREEN);
        System.out.println(")");

        var idFld= _getIdField(obj.getClass());
        T objInDb = (T) find(obj.getClass(), getField2025(obj, idFld));
        copyProps(objInDb, obj);
        persist(obj);
        System.out.println("endfun merge()");
        return obj;
    }

    /**
     *   new
     *
     * @param obj
     * @param <T>
     * @return
     */
    public <T> T persist(T obj) throws Exception {

        System.out.println("\r\n\r\n");
        //   System.out.println("fun addobj(");
        String runEmoji = "▶️";
        printLn("▶️fun persist(", BLUE);
        printLn("obj=" + encodeJson(obj), GREEN);

        System.out.println(")");
        Statement stmt = Connection1.createStatement();
        //  create database db2


        //--------cret db todo
        String tbnm = (String) OrmBase.getTableNameFromObjClass(obj.getClass());
        var idFld= _getIdField(obj.getClass());
        stmt.execute("CREATE TABLE IF NOT EXISTS " + tbnm + " ("+idFld+" VARCHAR(500) PRIMARY KEY)");


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
        System.out.println("endfun persist()");
        return obj;
    }

    //循环对象属性，创建对应的字段
    public   void _CreateColumes(Object obj, Statement stmt, String tbnm, String saveDir ) throws Exception {

        if (obj instanceof Map) {
            Map<String, Object> map = (Map) obj;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                //  System.out.println(entry.getKey() + ": " + entry.getValue());
                //  System.out.println(entry.getKey() + ": " + entry.getValue());
                try {
                    Object value = entry.getValue();
                    String name = entry.getKey();
                    if (name.toLowerCase().equals("id"))
                        continue;
                    ;
                    //   System.out.println(name + ": " + value);
                    if (name.equals("amt"))
                        System.out.println("dbg");
                    String sql = "ALTER TABLE " + tbnm + " ADD COLUMN  " + name + "  " + getTypeSqlt(value) + " ";
                    //   System.out.println(sql);
                    stmt.execute(sql);


                } catch (Exception e) {
                    if (!e.getMessage().contains("duplicate column name"))
                        e.printStackTrace();
                }
            }
        } else if (obj instanceof Record) {
            // 获取 record 的字段
            var components = obj.getClass().getRecordComponents();

            for (var component : components) {
                try {
                    Object value = component.getAccessor().invoke(obj);
                    String name = component.getName();
                    if (name.toLowerCase().equals("id"))
                        continue;
                    ;
                    System.out.println(name + ": " + value);
                    String sql = "ALTER TABLE " + tbnm + " ADD COLUMN  " + name + "  " + getTypeSqlt(value) + " ";
                 //   System.out.println(sql);
                    stmt.execute(sql);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Get the class of the object
            Class<?> clazz = obj.getClass();

            // Get all fields (including private ones)
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                try {
                    field.setAccessible(true); // Allow access to private fields
                    String fieldName = field.getName();

                    var colType = _toSqlType(field.getClass());


                    String fldNameDbfmt = "\"" + fieldName + "\"";//h2 fmt
                    if (saveDir.startsWith("jdbc:mysql"))
                        fldNameDbfmt = fieldName;
                    String sql = "ALTER TABLE " + tbnm + " ADD COLUMN  " + fldNameDbfmt + "  " + colType + " ";
                    // ALTER TABLE usr ADD COLUMN c1 int;
                 //   System.out.println(sql);
                    stmt.execute(sql);
                } catch (Exception e) {
                    if (e.getMessage().contains("duplicate column name")) {

                    } else if (e.getMessage().contains("Duplicate column name")) {

                    } else
                        e.printStackTrace();
                }

            }
        }


    }
    /**
     * 获取classname....
     *
     * @param objClass
     * @param <T>
     * @return
     */
    public  static <T> String getTableNameFromObjClass(Class<T> objClass) {
        return objClass.getSimpleName();
    }
    Connection Connection1;
    public Connection beginTransaction() throws SQLException {
        System.out.println("\r\n");
        Connection conn = DriverManager.getConnection(jdbcurl);
        Connection1=conn;
        Statement stmt = conn.createStatement();
        //  create database db2


        //--------cret db todo
        conn.setAutoCommit(false);
//    var setAtCmtRzt=    stmt.execute("SET autocommit = 0;");
//        System.out.println("setAtCmtRzt="+setAtCmtRzt);
        Statement stmt2 = conn.createStatement();
        String sql = "START TRANSACTION;";
        System.out.println(sql);
        stmt2.execute(sql);
        return  conn;

    }
    public void commit() throws SQLException {
        Statement stmt2 = Connection1.createStatement();
        System.out.println("commit;");
         stmt2.execute("COMMIT;");
    }
}

