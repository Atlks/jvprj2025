package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

import static util.util2026.getField2025;

public interface Session extends  EntityManager {

//
//    <T> T find(Class<T> objClass, Object id) throws Exception;
//
//    <T> T merge(T var1) throws Exception;
//    <T> T persist(T obj) throws Exception;

//    JPA 没有专门的 update() 方法，因为 JPA 的 实体管理器 (EntityManager) 会自动检测对象的变化，并在事务提交时执行 UPDATE 语句。

    String _toSqlType(Class<? extends Field> aClass);

    Connection beginTransaction() throws SQLException;

    void commit() throws SQLException;

//    <T> T find(Class<T> objClass, Object id, LockModeType lockModeType) throws Exception;
}
