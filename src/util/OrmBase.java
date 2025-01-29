package util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Map;


import static util.Util2025.encodeJson;
import static util.dbutil.*;
import static util.util2026.copyProps;
import static util.util2026.getField2025;

public abstract class OrmBase implements OrmBaseItfs {
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
        var tbnm = getTableNameFromObjClass(objClass);
        var sql = "select * from " + tbnm + " where id='" + id + "'";
        try {
            return (T) toObj(qrySqlAsMap(sql, jdbcurl),objClass) ;
        } catch (Exception e) {
            if (e.getMessage().contains("no such table: tab1"))
                return objClass.newInstance();
            throw new RuntimeException(e);
        }
    }

    /**
     * add or updt
     *
     * @param var1
     * @param <T>
     * @return
     */
    @Override
    public <T> T merge(T var1) throws Exception {
        T obj = (T) find(var1.getClass(), getField2025(var1, "id"));
        copyProps(var1, obj);
        persist(obj);
        return obj;
    }

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
                    System.out.println(sql);
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
                    System.out.println(sql);
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
    public void beginTransaction() {
    }
    public void commit() {
    }
}

