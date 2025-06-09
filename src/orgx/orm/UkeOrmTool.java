package orgx.orm;

//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.Id;
//import jakarta.persistence.Transient;
//


import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

//import static util.algo.GetUti.getUuid;
//import static util.algo.ToXX.toSnake;
//import static util.orm.OrmUti.generateInsertSql;

public class UkeOrmTool {

    public static String toSnake(String Camel_input) {
        if (Camel_input == null || Camel_input.isEmpty()) return Camel_input;
        return Camel_input.replaceAll("([a-z])([A-Z])", "$1_$2")
                .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                .toLowerCase();
    }

    public static void main(String[] args) {
//        Table table = buildTableModel(Account.class);
//        Account account = new Account();
//        account.setAccountId(getUuid());
//        account.setAccountSubType("emoney");
//        account.setOwner("666");
//
//        Column c;
//      String  sql=generateInsertSql(account);
//     // sql=generateUpdateSql(account);
//        System.out.println(sql);


        // System.out.println(table);
        //  System.out.println(generateCreateTableSQL(table));

    }


//    private static String geenrateInsertSql(Object object) {
//        Table table = buildTableModel(object.getClass());
//
//    }

    public static String mapJavaTypeToMySQL(Column col) {
        Class<?> type = col.getType();

        return toColtypeMysqlMdl( type,col.getLength());
    }

    private static @NotNull String toColtypeMysqlMdl(Class<?> type, Long length) {
        if (type == String.class) {
            long length2 = length!= null ?   length  : 255;

            return "VARCHAR(" + length2 + ")";
        } else if (type == Integer.class || type == int.class) {
            return "INT";
        } else if (type == Long.class || type == long.class) {
            return "BIGINT";
        } else if (type == Boolean.class || type == boolean.class) {
            return "BOOLEAN";
        } else if (type == Double.class || type == double.class) {
            return "DOUBLE";
        } else if (type == java.util.Date.class || type == java.sql.Date.class) {
            return "DATETIME";


        } else if (type == BigDecimal.class) {
            return "decimal(38,2)";
        } else if (type == OffsetDateTime.class) {
            return "TIMESTAMP(6)";
        }


        // 默认 fallback
        return "VARCHAR(500)";
    }


    /**
     * 实体模型注解使用 jpa的
     * 构建表模型，语法使用mysql的
     *
     * @return
     */
//    static Table buildTableModel(Class<?> modelClass) {
//
//        Table tableModel = new Table();
//
//        // 1. 表名
//        String simpleNameClz = modelClass.getSimpleName();
//
//        if (modelClass.isAnnotationPresent(jakarta.persistence.Table.class)) {
//            jakarta.persistence.Table tableAnno = modelClass.getAnnotation(jakarta.persistence.Table.class);
//            if(tableAnno.name().equals(""))
//                tableModel.setName(toTablename(simpleNameClz) );
//            else
//                tableModel.setName(tableAnno.name());
//        } else {
//            // 默认类名小写为表名
//            tableModel.setName(toTablename(simpleNameClz));
//        }
//
//        tableModel.setColumns(new HashMap<>()); // 初始化 columns
//
//        // 2. 遍历字段
//        for (Field field : modelClass.getDeclaredFields()) {
//            field.setAccessible(true);
//
//            if(field.isAnnotationPresent(Transient.class))
//                continue;
//
//            Column column = new Column();
//
//            // 字段名
//            column.setNameInEntity( field.getName());
//            column.setName(toSnake( field.getName()));
//            Class<?> colType = getColType(field);
//
//            column.setType(colType);
//
//            // @Column 注解处理
//            if (field.isAnnotationPresent(jakarta.persistence.Column.class)) {
//                jakarta.persistence.Column colAnno = field.getAnnotation(jakarta.persistence.Column.class);
//
//                if (!colAnno.name().isEmpty()) {
//                    column.setName(colAnno.name());
//                }
//                column.setNullable(colAnno.nullable());
//                column.setUnique(colAnno.unique());
//                if (colAnno.length() > 0) {
//                    column.setLength((long) colAnno.length());
//                }
//                if (colAnno.columnDefinition() != null) {
//                    column.setColumnDefinition(colAnno.columnDefinition());
//                   // column.setComment(colAnno.columnDefinition()); // 可能带注释
//                }
//            }
//
//            // @Size 注解处理
////            if (field.isAnnotationPresent(Size.class)) {
////                Size sizeAnno = field.getAnnotation(Size.class);
////                column.setColumnSize(sizeAnno);
////            }
////
////            // @GeneratedValue 表示自增
////            if (field.isAnnotationPresent(GeneratedValue.class)) {
////                column.setIdentity(true);
////            }
//
//            // @Id 注解（可加标识）
//            if (field.isAnnotationPresent(Id.class)) {
//                column.setLabel("主键：" + column.getName());
//              tableModel.setPrimaryKey(column.getName());
//            }
//
//            // 其他字段可以从注释、默认值、注解中再补充
//
//            tableModel.getColumns().put(column.getName(), column);
//        }
//
//        // 打印或保存 tableModel，可根据需求返回或存储
//        System.out.println("构建表模型成功：" + tableModel.getName());
//        tableModel.getColumns().forEach((name, col) ->
//                System.out.println("字段：" + name + ", 类型：" + col.getType().getSimpleName()));
//
//        return tableModel;
//    }
    public static String toTablename(String simpleNameClz) {
        return toSnake(simpleNameClz);
    }

//    static Class<?> getColType(Field field) {
//        Class<?> colType = field.getType();
//        if(field.isAnnotationPresent(Enumerated.class))
//            colType=(getTypeFromEnumerated(field) );
//        return colType;
//    }
//
//    /**
//     * get type from jpa anno Enumerated
//     * @param field
//     * @return
//     */
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