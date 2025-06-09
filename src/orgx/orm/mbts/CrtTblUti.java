package orgx.orm.mbts;

//import jakarta.persistence.Entity;
//import orgx.orm.Column;
//import util.Oosql.Column;
//import util.tx.dbutil;

import com.baomidou.mybatisplus.annotation.TableName;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import orgx.orm.Column;
import orgx.orm.Table;
import orgx.orm.oth.ProcessContext;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static orgx.orm.mbts.Mbts2Table.buildTableModel;

import static orgx.orm.UkeOrmTool.mapJavaTypeToMySQL;
import static orgx.orm.UkeOrmTool.toSnake;
import static orgx.orm.oth.CallUtil.callTry;
import static orgx.orm.oth.dbutiJdk.executeUpdate;
import static orgx.orm.oth.util2026.scanAllClass;
import static orgx.orm.oth.util2026.scanAllClassByClsesDir;
//import static orgx.uti.context.ThreadContext.currDbConn;
//import static util.algo.CallUtil.callTry;
//import static util.algo.ToXX.toSnake;
//import static util.misc.util2026.scanAllClass;
//import static util.orm.UkeOrmTool.buildTableModel;
//import static util.orm.UkeOrmTool.mapJavaTypeToMySQL;

public class CrtTblUti {
    public static  String dbType = "H2"; // 或 "MySQL"

    public static void scanClzCrtTable(String fltpath) {

        SqlSessionFactory sqlSessionFactory = ProcessContext.sqlSessionFactory;

        SqlSession sqlSession = sqlSessionFactory.openSession();
        Connection connection = sqlSession.getConnection();
        Consumer<Class> fun = clazz -> {
            String[] paths = fltpath.split(",");
            for (String path : paths) {
                if (clazz.getName().startsWith(path)         ) {
                    System.out.println(clazz.getName());
                   // if (clazz.isAnnotationPresent(TableName.class)) {
                        String sql=generateCreateTableSQL(clazz);
                        System.out.println(sql);
                        callTry(()->{


                              executeUpdate(sql, connection);

                        });
                  //  }



                }
            }

        };
        System.out.println("====start scanClzCrtTable");
        scanAllClassByClsesDir(true,fun);
        System.out.println("====end scanClzCrtTable");
    }





    public static String generateCreateTableSQL(Class<?> tableClz) {
        //must to tableModel,..bcs transit fld not need

        Table tbl = buildTableModel(tableClz);


        String s = generateCreateTableSQL(tbl);
        return s;

    }


    public static String generateCreateTableSQL(Table table) {
        if (table.getName().equals("balances")) {
            System.out.println(888);
        }

        String tableNamePrtktChar = dbType.equals("H2") ? "\"" : "`";

//CREATE TABLE IF NOT EXISTS
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS "+tableNamePrtktChar).append(table.getName()).append(tableNamePrtktChar+" (\n");

        List<String> columnDefs = new ArrayList<>();
        String primaryKey = null;

        for (Column col : table.getColumns().values()) {
            StringBuilder colDef = new StringBuilder();

            if (col.getName().trim().startsWith(tableNamePrtktChar))
                colDef.append("  ").append(col.getName()).append(" ");
            else
                colDef.append("  "+tableNamePrtktChar).append(col.getName()).append(tableNamePrtktChar+" ");

            if (col.getName().equals(toSnake("accSnapshot")))
                System.out.println(888);
            // 映射 Java 类型到 MySQL 类型（简单示例）
            if (isNotBlank(col.columnDefinition)) {
                colDef.append(col.columnDefinition);

            } else {
                String type = mapJavaTypeToMySQL(col);
                colDef.append(type);
            }


            // 自增
            if (col.isIdentity() || col.AUTO_INCREMENT) {
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
            sql.append(",\n  PRIMARY KEY ("+tableNamePrtktChar).append(primaryKey).append(tableNamePrtktChar+")");
        }

        sql.append("\n)");   // DEFAULT CHARSET=utf8mb4

        // 表注释
        if (table.getComment() != null && !table.getComment().isEmpty()) {
            sql.append(" COMMENT='").append(table.getComment()).append("'");
        }

        sql.append(";");

        return sql.toString();
    }

}
