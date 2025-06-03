package util.Oosql;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.algo.GetUti.getTableName;
import static util.algo.ToXX.toSnake;

public class SqlBldr {
    //mv to sqlBlder
    public  static  String selectFrom( Class<?> adminClass) {
        return select("*")+ from(adminClass);
    }
    public static String selectFld(String jvFld) {

        return  "select "+toSnake(jvFld) ;
    }
    private static String select(String cols) {

        return  "select "+cols ;
    }
    public static String from(Class<?> adminClass) {
        return  " from "+getTableName(adminClass);
    }


    public static String and(String javafld, String op, @NotBlank @Valid String uname) {
        return " and "+toDbFldName(javafld)+op+ toSqlPrmAsStr(uname);
    }

    public static String toSqlPrmAsStr(@NotBlank @Valid String uname) {
        return  encodeSqlPrmAsStr(uname);
    }

    public static String where(String javafld, String eq, Object v) {
        String val="";
        if(v instanceof String) {
            val= toSqlPrmAsStr((String) v);
        }else
            val=v.toString();;
        return " where "+toDbFldName(javafld) + eq + val;
    }

    private static String toDbFldName(String javafld) {
        return toSnake(javafld);
    }
}
