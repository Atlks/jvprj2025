package util.orm;

import jakarta.persistence.Entity;
import jakarta.ws.rs.Path;
import util.Oosql.Column;
import util.tx.dbutil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static orgx.uti.context.ThreadContext.currDbConn;
import static util.algo.CallUtil.callTry;
import static util.algo.GetUti.getTablename;
import static util.algo.ToXX.toSnake;
import static util.misc.RestUti.getPathFromFun;
import static util.misc.util2026.scanAllClass;
import static util.orm.UkeOrmTool.buildTableModel;
import static util.orm.UkeOrmTool.mapJavaTypeToMySQL;

public class CrtTblUti {

    public static void scanClzCrtTable() {
        Consumer<Class> fun = clazz -> {
            if (clazz.getName().startsWith("entityx") || clazz.getName().startsWith("model")|| clazz.getName().startsWith("util.model")
                    || clazz.getName().startsWith("util.entty")
            ) {

                if (clazz.isAnnotationPresent(Entity.class)) {
                   String sql=generateCreateTableSQL(clazz);
                    System.out.println(sql);
                    callTry(()->{
                        Connection conn=currDbConn.get();
                        dbutil.  executeUpdate(sql, conn);

                    });
                }



            }
        };
        System.out.println("====start scanClzCrtTable");
        scanAllClass(fun);
        System.out.println("====end scanClzCrtTable");
    }

    public static   String generateCreateTableSQL(Class<?> tableClz) {
   //must to tableModel,..bcs transit fld not need
        Table tbl=  buildTableModel(tableClz);
      return  generateCreateTableSQLByTab(tbl);
        }



    public static String generateCreateTableSQLByTab(Table table) {
        if(table.getName().equals("balances")) {
            System.out.println(888);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE `").append(table.getName()).append("` (\n");

        List<String> columnDefs = new ArrayList<>();
        String primaryKey = null;

        for (Column col : table.getColumns().values()) {
            StringBuilder colDef = new StringBuilder();

            if(col.getName().trim().startsWith("`"))
                colDef.append("  ").append(col.getName()).append(" ");
            else
            colDef.append("  `").append(col.getName()).append("` ");

             if(col.getName().equals(toSnake("accSnapshot")))
                 System.out.println(888);
            // 映射 Java 类型到 MySQL 类型（简单示例）
            if(isNotBlank( col.columnDefinition))
            {
                colDef.append(col.columnDefinition);

            }else {
                String type = mapJavaTypeToMySQL(col);
                colDef.append(type);
            }


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

}
