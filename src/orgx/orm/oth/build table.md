完成函数

/**
* 实体模型注解使用 jpa的
*构建表模型，语法使用mysql的
* @param accountClass
* @return
*/
private static boolean buildTableModel(Class<?> modelClass) {


    }


依赖的代码如下


public class Table {
private List<Column> EMPTY_COLUMN_ARRAY = new ArrayList<>();
private String name;
private   Map<String, Column> columns;
//  private PrimaryKey primaryKey;
private String comment;
}


@Data
public class Column {
private String name;       // 字段名（如 "user_id"）
private String label;      // 字段中文名或注释（如 "用户ID"）
private Object value;      // 字段的值（如 123）
private Class<?> type;     // 字段类型（如 Integer.class）
private boolean nullable = true;
private boolean unique=false;
private Size columnSize;
int DecimalDigits;
private Long length;
private boolean identity=false;
private String comment;
private Object defaultValue;