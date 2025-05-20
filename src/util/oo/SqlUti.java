package util.oo;

import static util.algo.GetUti.getTableName;
import static util.algo.ToXX.toSnake;

public class SqlUti {
    public static String fromWzSlct(Class<?> adminClass) {
        return  " select * from "+getTableName(adminClass);
    }

    public  static  String selectFrom(String cols,Class<?> adminClass) {
        return select(cols)+ from(adminClass);
    }
    private static String select(String cols) {

        return  "select "+cols ;
    }
    private static String from(Class<?> adminClass) {
        return  " from "+getTableName(adminClass);
    }

    public static String orderByDesc(String createdAt) {
        String fldName=toSnake(createdAt);
        return  " order by " + fldName + " desc";
    }
    public static String orderby(String crtTimeStmpDesc) {
        return " order by " + crtTimeStmpDesc;
    }
}
