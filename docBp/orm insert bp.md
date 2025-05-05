

ObieAccFields flds = ObieAccFields.ACCOUNT_ID;


        InsertIntoStmt query =new InsertIntoStmt(getTableName(Account.class));
     
        InsertIntoStmt query =new InsertIntoStmt(getTableName(Account.class));
       query.set(Account.Fields.accountId,1);
        query.set(Account.Fields.accountType,AccountType.BUSINESS.name())    



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


