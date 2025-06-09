package orgx.orm.mbts;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import org.jetbrains.annotations.Nullable;
import orgx.orm.Column;
import orgx.orm.Table;

import java.lang.reflect.Field;
import java.util.HashMap;

import static cn.hutool.core.text.CharSequenceUtil.isBlank;
import static orgx.orm.UkeOrmTool.*;
import static orgx.orm.mbts.CrtTblUti.dbType;

public class Mbts2Table {

    private static Class<?> getColType(Field field) {
        Class<?> colType = field.getType();
//        if(field.isAnnotationPresent(Enumerated.class))
//            colType=(getTypeFromEnumerated(field) );
        return colType;
    }

    /**
     * 实体模型注解使用 jpa的
     * 构建表模型，语法使用mysql的
     *
     * @return
     */
    public static Table buildTableModel(Class<?> modelClass) {

        Table tableModel = new Table();

        // 1. 表名


        String tablename = getTablename(modelClass);

        tableModel.setName(tablename);

        tableModel.setColumns(new HashMap<>()); // 初始化 columns

        // 2. 遍历字段
        for (Field field : modelClass.getDeclaredFields()) {
            field.setAccessible(true);

//            if(field.isAnnotationPresent(Transient.class))
//                continue;

            Column column = new Column();

            // 字段名
            column.setNameInEntity(field.getName());


            String colName = getColName(field);
            if(colName.equals(""))
                System.out.println("d1120");
            column.setName(colName);


            Class<?> colType = getColType(field);

            column.setType(colType);

            // @Column 注解处理
            if (field.isAnnotationPresent(TableField.class)) {


//                column.setNullable(colAnno.nullable());
//                column.setUnique(colAnno.unique());
//                if (colAnno.length() > 0) {
//                    column.setLength((long) colAnno.length());
//                }
//                if (colAnno.columnDefinition() != null) {
//                    column.setColumnDefinition(colAnno.columnDefinition());
//                    // column.setComment(colAnno.columnDefinition()); // 可能带注释
//                }
            }

            // @Size 注解处理
//            if (field.isAnnotationPresent(Size.class)) {
//                Size sizeAnno = field.getAnnotation(Size.class);
//                column.setColumnSize(sizeAnno);
//            }
//
            setPkNautoIcrmt(field, column, tableModel);

            // 其他字段可以从注释、默认值、注解中再补充

            tableModel.getColumns().put(column.getName(), column);
        }

        // 打印或保存 tableModel，可根据需求返回或存储
        System.out.println("构建表模型成功：" + tableModel.getName());
        tableModel.getColumns().forEach((name, col) ->
                System.out.println("字段：" + name + ", 类型：" + col.getType().getSimpleName()));

        return tableModel;
    }

    private static String getColName(Field field) {

        if(field.getName().equals("ruid"))
            System.out.println("d1121");
        String colName = (toSnake(field.getName()));  //deflt
        if (field.isAnnotationPresent(TableField.class)) {
            TableField colAnno = field.getAnnotation(TableField.class);
           if(! isBlank(colAnno.value()) )
               colName = colAnno.value();
            if (dbType.equals("H2"))
                colName = trimMysqlColNameWarp(colName);
        }
        return colName;
    }

    private static void setPkNautoIcrmt(Field field, Column column, Table tableModel) {
        // 获取 `@TableId` 注解
        TableId tableId = field.getAnnotation(TableId.class);

        if (tableId != null) {           // 读取 `type` 值
            column.setLabel("主键：" + column.getName());
            tableModel.setPrimaryKey(column.getName());
            column.setIdentity(true);
            IdType idType = tableId.type();
            if (idType == IdType.AUTO) {
                column.AUTO_INCREMENT = true;
            }
        }


    }

    /**
     * 去除俩边的 波浪号
     *
     * @param colName
     * @return
     */
    private static String trimMysqlColNameWarp(String colName) {
        return colName.replaceAll("^`|`$", "");
    }

    private static @Nullable String getTablename(Class<?> modelClass) {

        String simpleNameClz = modelClass.getSimpleName();
        String tablename = toTablename(simpleNameClz);
        if (modelClass.isAnnotationPresent(TableName.class)) {
            TableName tableAnno = modelClass.getAnnotation(TableName.class);
            if (tableAnno.value().equals(""))
                tablename = toTablename(simpleNameClz);
            else
                tablename = (tableAnno.value());
        } else {
            // 默认类名小写为表名
            tablename = toTablename(simpleNameClz);
        }
        return tablename;
    }

}
