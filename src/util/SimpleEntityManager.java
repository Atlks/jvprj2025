package util;

import java.sql.*;

public class SimpleEntityManager {
    private final Connection connection;

    public SimpleEntityManager(Connection connection) {
        this.connection = connection;
    }

    // 保存实体
    public <T> void save(T entity) throws Exception {
        EntityMetadata metadata = new EntityMetadata(entity.getClass());
        String tableName = metadata.getTableName();
        var columnMappings = metadata.getColumnMappings();

        // 构造 SQL 语句
        String sql = "INSERT INTO " + tableName + " (" +
                String.join(", ", columnMappings.values()) + ") VALUES (" +
                "?,".repeat(columnMappings.size()).replaceAll(",$", "") + ")";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            for (String fieldName : columnMappings.keySet()) {
                Field field = entity.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                stmt.setObject(index++, field.get(entity));
            }
            stmt.executeUpdate();
        }
    }

    // 查找实体
    public <T> T find(Class<T> entityClass, Long id) throws Exception {
        EntityMetadata metadata = new EntityMetadata(entityClass);
        String tableName = metadata.getTableName();
        String idColumn = "id";  // 假设 ID 字段叫 "id"

        String sql = "SELECT * FROM " + tableName + " WHERE " + idColumn + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                T entity = entityClass.getDeclaredConstructor().newInstance();
                for (String fieldName : metadata.getColumnMappings().keySet()) {
                    Field field = entityClass.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(entity, rs.getObject(metadata.getColumnMappings().get(fieldName)));
                }
                return entity;
            }
        }
        return null;
    }
}
