package util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

import static util.util2026.getField2025;

public interface Session {


    <T> T find(Class<T> objClass, Object id) throws Exception;

    <T> T merge(T var1) throws Exception;
    <T> T persist(T obj) throws Exception;

    String _toSqlType(Class<? extends Field> aClass);

    Connection beginTransaction() throws SQLException;

    void commit() throws SQLException;
}
