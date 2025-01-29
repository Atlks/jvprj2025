package util;

import java.lang.reflect.Field;

import static util.util2026.getField2025;

public interface OrmBaseItfs {


    <T> T find(Class<T> objClass, Object id) throws Exception;

    <T> T merge(T var1) throws Exception;
    <T> T persist(T obj) throws Exception;

    String _toSqlType(Class<? extends Field> aClass);
}
