//package util;
//
//import jakarta.persistence.LockModeType;
//
//import java.lang.reflect.Field;
//
//public class SessionOrm extends OrmBase {
//    OrmBase om;
//
//    public SessionOrm(String saveDirUsrs) {
//        if (saveDirUsrs.startsWith("jdbc:mysql")) {
//            om = new OrmMysql();
//            om.jdbcurl = saveDirUsrs;
//
//        }
//
//    }
//    public static Session openSession(String saveDirUsrs) {
//        if (saveDirUsrs.startsWith("jdbc:mysql")) {
//            OrmMysql   om = new OrmMysql();
//            om.jdbcurl = saveDirUsrs;
//            return  om;
//
//        }
//
//        return null;
//    }
//
//    public static Session newSession(String saveDirUsrs) {
//        if (saveDirUsrs.startsWith("jdbc:mysql")) {
//            OrmMysql   om = new OrmMysql();
//            om.jdbcurl = saveDirUsrs;
//            return  om;
//
//        }
//
//        return null;
//    }
//
//    @Override
//    public String _toSqlType(Class<? extends Field> aClass) {
//        return om._toSqlType(aClass);
//    }
//
//    /**
//     * @param objClass
//     * @param id
//     * @param lockModeType
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> T findWzLock(Class<T> objClass, Object id, LockModeType lockModeType) {
//        return null;
//    }
//}
