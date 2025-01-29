package util;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.Map;

import static util.dbutil.getTypeSqlt;

public class OrmUtil {
    public static Session openSession(String saveDirUsrs) {
        if (saveDirUsrs.startsWith("jdbc:mysql")) {
            OrmMysql   om = new OrmMysql();
            om.jdbcurl = saveDirUsrs;
            return  om;

        }

        return null;
    }
}
