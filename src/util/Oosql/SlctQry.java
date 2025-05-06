package util.Oosql;

public class SlctQry {
    private   String fromFrag="";
    private String selectFrag="";
    private String sql;
    private String ordby="";

    public SlctQry(String sql) {
   this.fromFrag =sql+" where 1=1 ";
    }

    public void addConditions(String s) {
        this.fromFrag +="   and "+s;
    }

    public void addOrderBy(String ordbyStr) {
        this.ordby =" order by "+ordbyStr;
    }

    public String getSQL() {
        if(selectFrag.equals(""))
            this.selectFrag="select *";

        this.sql =selectFrag+" "+this.fromFrag+" "+this.ordby;
        return this.sql;
    }

    public static String toValStr(String uname) {
        //  Field<String> accountOwner = DSL.field("accountOwner", String.class);
        return "'" + uname + "'";
    }

    public static SlctQry newSelectQuery(String tableName) {
        var sql=" from "+tableName;
        return new SlctQry(sql);
    }

    public static SlctQry newSelectQry(String tableName) {
        var sql="select * from "+tableName;
        return new SlctQry(sql);
    }

    public void select(String slct) {
        this.selectFrag="select "+slct;

    }
}
