

ObieAccFields flds = ObieAccFields.ACCOUNT_ID;


        InsertIntoStmt query =new InsertIntoStmt(getTableName(Account.class));
       query.set(ObieAccFields.ACCOUNT_ID.getFieldName(),1);
        query.set(ObieAccFields.ACCOUNT_SUB_TYPE.getFieldName(),AccountType.BUSINESS.name())    ;
        query.set(ObieAccFields.ACCOUNT_STATUS.getFieldName(),  AccountStatus.Enabled.name());

        query.setStr(ObieAccFields.ACCOUNT_TYPE.getFieldName(), "ooo");
        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);




public class InsertIntoStmt {
public String tableName;
public Map<String, Column> linkedHashMap = new LinkedHashMap();

    public InsertIntoStmt(String tableName) {
        this.tableName = tableName;
    }

    public void set(String field, Object value) {
         if(value instanceof Integer)
        linkedHashMap.put(field, new Column(value, Integer.class));

        if(value instanceof String)
            linkedHashMap.put(field, new Column(value, String.class));
    }


