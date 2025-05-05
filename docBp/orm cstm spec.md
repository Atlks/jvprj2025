


# df model  use jpa anno

规范，tablename使用复数，classname单数
要有字段名列表内部类FieldNameConstants

/**
* 实体类：代理信息
  */
  @Entity
  @Table(name=agts)
  @Data  
  @FieldNameConstants
  public class Agent {

# insert stmt


        InsertIntoStmt query =new InsertIntoStmt(getTableName(Account.class));
       query.set(Account.Fields.accountId,1);
        query.set(Account.Fields.accountType,AccountType.BUSINESS.name())    ;
        query.set(ObieAccFields.ACCOUNT_STATUS.getFieldName(),  AccountStatus.Enabled.name());

        query.setStr(ObieAccFields.ACCOUNT_TYPE.getFieldName(), "ooo");
        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串


# qry 