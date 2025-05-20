


完成函数，传入实体生产sql


调用语句

Table table = buildTableModel(Account.class);
Account account = new Account();
account.setAccountId(getUuid());
account.setAccountSubType("emoney");
account.setOwner("666");


      String  sql=geenrateInsertSql(account);
        System.out.println(sql);


我要如何生成geenrateInsertSql 函数

其他函数以及实现了。。
buildTableModel函数以及实现了。。。
其他依赖的代码如下

public class Table {
private List<Column> EMPTY_COLUMN_ARRAY = new ArrayList<>();
private String name="";
private   Map<String, Column> columns=new HashMap<>();
//  private PrimaryKey primaryKey;
private String comment="";
}


public class Column {
public  String nameInEntity;   //在实体中的名字
private String name;       // 字段名（如 "user_id"）
private String label;      // 字段中文名或注释（如 "用户ID"）
private Object value="";      // 字段的值（如 123）
private Class<?> type;     // 字段类型（如 Integer.class）
private boolean nullable = true;
private boolean unique=false;
private Size columnSize;
int DecimalDigits;
private Long length;
private boolean identity=false;
private String comment;
private Object defaultValue;