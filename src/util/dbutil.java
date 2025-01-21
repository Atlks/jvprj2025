package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static util.Util2025.*;
import static util.util2026.getField2025;

public class dbutil {


    /**
     *
     * @param obj
     * @param collName   usrs
     * @param saveDir   /db2026/
     *             jdbc:sqlite:/db2026/usrs.db
     *             redis
     *             jdbc:mysql
     *
     * @throws Exception
     */
    public static void addObj(Object obj, String collName,String saveDir) throws Exception {

        if(saveDir.endsWith(".db"))
        {
            addObjSqlt(obj,collName,saveDir);
        }else if( saveDir.startsWith("jdbc:mysql"))
        {
            addObjMysql(obj,collName,saveDir);
        }else {
            addObjDocdb(obj,collName,saveDir);
        }


    }

    private static void addObjDocdb(Object obj, String collName, String saveDir) {
        mkdir2025(saveDir+collName);
        String fname=getField2025(obj,"id","");
        //todo need fname encode
        fname=fname+".json";
        String fnamePath=saveDir+collName+"/"+fname;
        System.out.println("fnamePath="+fnamePath);
        writeFile2024(fnamePath,encodeJson(obj));

    }

    private static void addObjSqlt(Object obj, String collName, String saveDir) throws Exception {

        Class.forName("org.sqlite.JDBC");
        mkdir2025(saveDir);
        //    String url = "jdbc:sqlite:" + saveDir + collName + ".db";
        Connection conn = DriverManager.getConnection(saveDir);
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS tab1 (k TEXT PRIMARY KEY, v TEXT)");
        String us = encodeJson(obj);
        String sql = "INSERT INTO tab1 (k, v) VALUES ('" + getField2025(obj,"id","") + "', '" + us + "')";
        System.out.println(sql);
        stmt.execute(sql);

    }

    private static void addObjMysql(Object obj, String collName, String saveDir) throws Exception {

        Class.forName("org.sqlite.JDBC");
        mkdir2025(saveDir);
        //    String url = "jdbc:sqlite:" + saveDir + collName + ".db";
        Connection conn = DriverManager.getConnection(saveDir);
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS tab1 (k TEXT PRIMARY KEY, v TEXT)");
        String us = encodeJson(obj);
        String sql = "INSERT INTO tab1 (k, v) VALUES ('" + getField2025(obj,"id","") + "', '" + us + "')";
        System.out.println(sql);
        stmt.execute(sql);

    }
}
