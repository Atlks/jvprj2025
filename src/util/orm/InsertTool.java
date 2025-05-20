package util.orm;

import jakarta.persistence.Id;
import util.Oosql.Column;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static util.orm.UkeOrmTool.buildTableModel;

public class InsertTool {


    /**
     * 根据传入的对象，生产insert语句
     *
     * @param entity，实体对象 jpa规范
     * @return
     */
    public static String generateUpdateSql(Object entity) {
        StringBuilder sql = new StringBuilder();
        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();
        List<String> keyValueList = new ArrayList<String>();
        Table table = buildTableModel(entity.getClass());
        Column c;
        String where = " ";
        try {
            Class<?> clazz = entity.getClass();
            for (Column column : table.getColumns().values()) {
                if (column.isIdentity()) {
                    continue; // 跳过自增字段
                }
                System.out.println(column);
                String fieldName = column.getNameInEntity();
                if (fieldName.equals("accountType"))
                    System.out.println("d656");
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(entity);

                // 忽略 null 值字段
                if (value == null) {
                    continue;
                }
                String kv = column.getName() + "=" + formatValueForSql(value, column.getType());

                keyValueList.add(kv);
                if (field.isAnnotationPresent(Id.class))
                    where = " where " + table.getPrimaryKey() + "='" + value.toString() + "'";
            }

            String kvs = keyValueList.stream().collect(Collectors.joining(","));


            sql.append("update `").append(table.getName()).append("` set ")
                    .append(kvs).append(where)
                    .append(";");

        } catch (Exception e) {
            throw new RuntimeException("生成 SQL 失败: " + e.getMessage(), e);
        }

        return sql.toString();
    }

    /**
     * 根据传入的对象，生产insert语句
     *
     * @param entity，实体对象 jpa规范
     * @return
     */
    public static String generateInsertSqlWzTabclz
    (Object entity) {
        StringBuilder sql = new StringBuilder();
        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();
        Table table = buildTableModel(entity.getClass());
        try {
            Class<?> clazz = entity.getClass();
            for (Column column : table.getColumns().values()) {
                if (column.isIdentity()) {
                    continue; // 跳过自增字段
                }
                System.out.println(column);
                String fieldName = column.getNameInEntity();
                if (fieldName.equals("accountType"))
                    System.out.println("d656");
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(entity);

                // 忽略 null 值字段
                if (value == null) {
                    continue;
                }

                fields.append("`").append(column.getName()).append("`, ");
                values.append(formatValueForSql(value, column.getType())).append(", ");
            }

            // 去掉最后多余的逗号和空格
            if (fields.length() > 0) {
                fields.setLength(fields.length() - 2);
                values.setLength(values.length() - 2);
            }

            sql.append("INSERT INTO `").append(table.getName()).append("` (")
                    .append(fields)
                    .append(") VALUES (")
                    .append(values)
                    .append(");");

        } catch (Exception e) {
            throw new RuntimeException("生成 SQL 失败: " + e.getMessage(), e);
        }

        return sql.toString();
    }

    static String formatValueForSql(Object value, Class<?> type) {
        if (type.equals(String.class))
            return "'" + value.toString() + "'";

        if (value instanceof String || value instanceof java.util.Date || value instanceof java.sql.Date) {
            return "'" + value.toString().replace("'", "''") + "'";
        } else if (value instanceof Boolean) {
            return ((Boolean) value) ? "1" : "0";
        } else {
            return value.toString(); // 数字等类型
        }
    }

}
