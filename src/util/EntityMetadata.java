package util;

import jakarta.persistence.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityMetadata {
    private final String tableName;
    private final Map<String, String> columnMappings = new HashMap<>();

    public EntityMetadata(Class<?> entityClass) {
        // 获取 @Table 注解
        Table table = entityClass.getAnnotation(Table.class);
        this.tableName = (table != null) ? table.name() : entityClass.getSimpleName();

        // 获取字段注解
        for (Field field : entityClass.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                columnMappings.put(field.getName(), column.name());
            }
        }
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, String> getColumnMappings() {
        return columnMappings;
    }
}
