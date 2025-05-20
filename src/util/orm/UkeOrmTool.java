package util.orm;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import model.OpenBankingOBIE.Account;
import util.Oosql.Column;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static util.algo.GetUti.getUuid;
import static util.algo.ToXX.toSnake;
// static util.orm.InsertTool.generateInsertSql;
import static util.orm.InsertTool.generateUpdateSql;
import static util.orm.OrmUti.generateInsertSql;

public class UkeOrmTool {

    public static void main(String[] args) {
        Table table = buildTableModel(Account.class);
        Account account = new Account();
        account.setAccountId(getUuid());
        account.setAccountSubType("emoney");
        account.setOwner("666");

        Column c;
      String  sql=generateInsertSql(account);
     // sql=generateUpdateSql(account);
        System.out.println(sql);


       // System.out.println(table);
      //  System.out.println(generateCreateTableSQL(table));

    }



//    private static String geenrateInsertSql(Object object) {
//        Table table = buildTableModel(object.getClass());
//
//    }

    private static String mapJavaTypeToMySQL(Column col) {
        Class<?> type = col.getType();

        if (type == String.class) {
            long length = col.getLength() != null ? col.getLength() : 255;
            return "VARCHAR(" + length + ")";
        } else if (type == Integer.class || type == int.class) {
            return "INT";
        } else if (type == Long.class || type == long.class) {
            return "BIGINT";
        } else if (type == Boolean.class || type == boolean.class) {
            return "TINYINT(1)";
        } else if (type == Double.class || type == double.class) {
            return "DOUBLE";
        } else if (type == java.util.Date.class || type == java.sql.Date.class) {
            return "DATETIME";
        }
        // 默认 fallback
        return "TEXT";
    }

    public static String generateCreateTableSQL(Table table) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE `").append(table.getName()).append("` (\n");

        List<String> columnDefs = new ArrayList<>();
        String primaryKey = null;

        for (Column col : table.getColumns().values()) {
            StringBuilder colDef = new StringBuilder();
            colDef.append("  `").append(col.getName()).append("` ");

            // 映射 Java 类型到 MySQL 类型（简单示例）
            String type = mapJavaTypeToMySQL(col);
            colDef.append(type);

            // 自增
            if (col.isIdentity()) {
                colDef.append(" AUTO_INCREMENT");
            }

            // 非空
            if (!col.isNullable()) {
                colDef.append(" NOT NULL");
            }

            // 默认值
            if (col.getDefaultValue() != null) {
                colDef.append(" DEFAULT '").append(col.getDefaultValue()).append("'");
            }

            // 注释
            if (col.getComment() != null && !col.getComment().isEmpty()) {
                colDef.append(" COMMENT '").append(col.getComment()).append("'");
            }

            columnDefs.add(colDef.toString());

            // 主键（这里只支持单字段主键）
            if (col.getLabel() != null && col.getLabel().contains("主键")) {
                primaryKey = col.getName();
            }
        }

        sql.append(String.join(",\n", columnDefs));

        // 主键
        if (primaryKey != null) {
            sql.append(",\n  PRIMARY KEY (`").append(primaryKey).append("`)");
        }

        sql.append("\n) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

        // 表注释
        if (table.getComment() != null && !table.getComment().isEmpty()) {
            sql.append(" COMMENT='").append(table.getComment()).append("'");
        }

        sql.append(";");

        return sql.toString();
    }


    /**
     * 实体模型注解使用 jpa的
     * 构建表模型，语法使用mysql的
     *
     * @return
     */
    static Table buildTableModel(Class<?> modelClass) {

        Table tableModel = new Table();

        // 1. 表名
        if (modelClass.isAnnotationPresent(jakarta.persistence.Table.class)) {
            jakarta.persistence.Table tableAnno = modelClass.getAnnotation(jakarta.persistence.Table.class);
            if(tableAnno.name().equals(""))
                tableModel.setName((modelClass.getSimpleName() ) );
            else
                tableModel.setName(tableAnno.name());
        } else {
            // 默认类名小写为表名
            tableModel.setName((modelClass.getSimpleName() ) );
        }

        tableModel.setColumns(new HashMap<>()); // 初始化 columns

        // 2. 遍历字段
        for (Field field : modelClass.getDeclaredFields()) {
            field.setAccessible(true);

            if(field.isAnnotationPresent(Transient.class))
                continue;

            Column column = new Column();

            // 字段名
            column.setNameInEntity( field.getName());
            column.setName(toSnake( field.getName()));
            Class<?> colType = getColType(field);

            column.setType(colType);

            // @Column 注解处理
            if (field.isAnnotationPresent(jakarta.persistence.Column.class)) {
                jakarta.persistence.Column colAnno = field.getAnnotation(jakarta.persistence.Column.class);

                if (!colAnno.name().isEmpty()) {
                    column.setName(colAnno.name());
                }
                column.setNullable(colAnno.nullable());
                column.setUnique(colAnno.unique());
                if (colAnno.length() > 0) {
                    column.setLength((long) colAnno.length());
                }
                if (colAnno.columnDefinition() != null) {
                    column.setComment(colAnno.columnDefinition()); // 可能带注释
                }
            }

            // @Size 注解处理
//            if (field.isAnnotationPresent(Size.class)) {
//                Size sizeAnno = field.getAnnotation(Size.class);
//                column.setColumnSize(sizeAnno);
//            }
//
//            // @GeneratedValue 表示自增
//            if (field.isAnnotationPresent(GeneratedValue.class)) {
//                column.setIdentity(true);
//            }

            // @Id 注解（可加标识）
            if (field.isAnnotationPresent(Id.class)) {
                column.setLabel("主键：" + column.getName());
              tableModel.setPrimaryKey(column.getName());
            }

            // 其他字段可以从注释、默认值、注解中再补充

            tableModel.getColumns().put(column.getName(), column);
        }

        // 打印或保存 tableModel，可根据需求返回或存储
        System.out.println("构建表模型成功：" + tableModel.getName());
        tableModel.getColumns().forEach((name, col) ->
                System.out.println("字段：" + name + ", 类型：" + col.getType().getSimpleName()));

        return tableModel;
    }

    static Class<?> getColType(Field field) {
        Class<?> colType = field.getType();
        if(field.isAnnotationPresent(Enumerated.class))
            colType=(getTypeFromEnumerated(field) );
        return colType;
    }

    /**
     * get type from jpa anno Enumerated
     * @param field
     * @return
     */
    private static Class<?> getTypeFromEnumerated(Field field) {
        if (field.isAnnotationPresent(Enumerated.class)) {
            Enumerated anno   = field.getAnnotation(Enumerated.class);
            EnumType enumType=anno.value();
            if(enumType==EnumType.STRING)
                return String.class;
            else
                return field.getType();
        }
        return field.getType();
    }
}