package orgx.orm;

//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.Transient;

import java.lang.reflect.Field;

import static orgx.orm.UkeOrmTool.toSnake;


public class OrmUti {

//    public static String getTableName(Class<?> jpaModelClass){
//        return  getTablename(jpaModelClass);
//    }
//    public static String getTablename(Class<?> jpaModelClass){
//        return  toSnake(getTableNameFrmAnno(jpaModelClass)) ;
//    }

//
//    /**
//     * 获取实体类 立马的 @table 表格名称。如果没有@table或者为空，则使用实体类名
//     * @param jpaModelClass
//     * @return
//     */
//    public static String getTableNameFrmAnno(Class<?> jpaModelClass) {
//        jakarta.persistence.Table tableAnnotation = jpaModelClass.getAnnotation(jakarta.persistence.Table.class);
//        if (tableAnnotation != null && tableAnnotation.name() != null && !tableAnnotation.name().isEmpty()) {
//            return tableAnnotation.name();
//        }
//        return jpaModelClass.getSimpleName();
//    }
//    /**
//     * 根据传入的对象，生产insert语句
//     *
//     *////  @param entity，实体对象 jpa规范
//     * @return
//     */
//    public static String generateInsertSql(Object entity) {
//        StringBuilder sql = new StringBuilder();
//        StringBuilder fields = new StringBuilder();
//        StringBuilder values = new StringBuilder();
//
//        try {
//            Class<?> clazz = entity.getClass();
//            Class<?> modelClass=entity.getClass();
//            String tableName=getTablename(modelClass);
//
//            System.out.println(tableName);
//
//            // 2. 遍历字段
//
//            for (Field field : modelClass.getDeclaredFields()) {
//                field.setAccessible(true);
//
//                if(field.isAnnotationPresent(Transient.class))
//                    continue;
//
//              //  System.out.println(column);
//                String fieldName = field.getName();
//                if (fieldName.equals("accountType"))
//                    System.out.println("d656");
//
//
//                Object value = field.get(entity);
//
//                // 忽略 null 值字段
//                if (value == null) {
//                    continue;
//                }
//
//                fields.append("`").append(field.getName()).append("`, ");
//
//                Class<?> colType =getColType(field);
//                values.append(formatValueForSql(value, colType)).append(", ");
//            }
//
//
//
//            // 去掉最后多余的逗号和空格
//            if (fields.length() > 0) {
//                fields.setLength(fields.length() - 2);
//                values.setLength(values.length() - 2);
//            }
//
//            sql.append("INSERT INTO `").append(tableName).append("` (")
//                    .append(fields)
//                    .append(") VALUES (")
//                    .append(values)
//                    .append(");");
//
//        } catch (Exception e) {
//            throw new RuntimeException("生成 SQL 失败: " + e.getMessage(), e);
//        }
//
//        return sql.toString();
//    }
//

    public  static String formatValueForSql(Object value, Class<?> type) {
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

    /**
     * 获取实体类 立马的 @table 表格名称。如果没有@table或者为空，则使用实体类名
     * @param jpaModelClass
     * @return
     */
//    public static String getTableName(Class<?> jpaModelClass) {
//        jakarta.persistence.Table tableAnnotation = jpaModelClass.getAnnotation(jakarta.persistence.Table.class);
//        if (tableAnnotation != null && tableAnnotation.name() != null && !tableAnnotation.name().isEmpty()) {
//            return tableAnnotation.name();
//        }
//        return jpaModelClass.getSimpleName();
//    }
//
//    private  static Class<?> getColType(Field field) {
//        Class<?> colType = field.getType();
//        if(field.isAnnotationPresent(Enumerated.class))
//            colType=(getTypeFromEnumerated(field) );
//        return colType;
//    }

    /**
     * get type from jpa anno Enumerated
     * @param field
     * @return
     */
//    private static Class<?> getTypeFromEnumerated(Field field) {
//        if (field.isAnnotationPresent(Enumerated.class)) {
//            Enumerated anno   = field.getAnnotation(Enumerated.class);
//            EnumType enumType=anno.value();
//            if(enumType==EnumType.STRING)
//                return String.class;
//            else
//                return field.getType();
//        }
//        return field.getType();
//    }
}
