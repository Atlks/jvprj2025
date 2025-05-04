package util.Oosql;

public class SlctQry {
    private   String sql;

    public SlctQry(String sql) {
   this.sql=sql+" where 1=1 ";
    }

    public void addConditions(String s) {
        this.sql+="   and "+s;
    }

    public void addOrderBy(String ordbyStr) {
        this.sql+=" order by "+ordbyStr;
    }

    public String getSQL() {
        return sql;
    }

    static String toValStr(String uname) {
        //  Field<String> accountOwner = DSL.field("accountOwner", String.class);
        return "'" + uname + "'";
    }

    static SlctQry newSelectQuery(String tableName) {
        var sql="select * from "+tableName;
        return new SlctQry(sql);
    }
}
