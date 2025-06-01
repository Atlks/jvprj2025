package util.oo;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static util.algo.EncodeUtil.encodeSqlPrm;
import static util.algo.GetUti.getTableName;
import static util.algo.ToXX.toSnake;

public class SqlUti {
    public static String fromWzSlct(Class<?> adminClass) {
        return  " select * from "+getTableName(adminClass);
    }

    public  static String andCondt(String fld, String op, Object val){
return  " and "+ addCondt(fld, op, val) +" ";
    }

    public static String addCondt(String fld, String op, Object val) {
        if(op.trim().toLowerCase().equals("like"))
            val="'%"+encodeSqlPrm((String) val) +"%'";
        if(val instanceof OffsetDateTime)
            val="'"+ toMysqlTimestampStr((OffsetDateTime) val)+"'";
        fld=toSnake(fld);
        return fld+" "+op+" "+val;
    }



        /**
         * 将 OffsetDateTime 转换为 MySQL 格式的时间字符串
         * @param val OffsetDateTime 对象
         * @return 格式化后的字符串，例如 "2025-05-25 14:30:00"
         */
        public static String toMysqlTimestampStr(OffsetDateTime val) {
            if (val == null) return null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return val.toLocalDateTime().format(formatter);
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
