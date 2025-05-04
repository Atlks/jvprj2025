
qry sql spec 

# 大部分 sqlt 和 mysql是通用的sql
如果不是db，nosql的化，那就可以使用spel 或其他dsl来查询 

少部分不同，使用jooq 规范化即可。。

# spel JPQL


6. JSONata / JMESPath / JSONPath
   特点：用于 JSON 数据查询与转换，风格类似 SQL。

示例（JMESPath）：

json
复制
编辑
users[?age > `18`].name


# 函数部分必须使用jooq规范化

其他使用hbnt即可了。。。


# 如何更加oo,也可自己开发api，更加的简单

比jooq更加简单


    public static void main(String[] args) {

        QryFundDetailRqdto reqdto =new QryFundDetailRqdto();
        reqdto.setUname("test");
        reqdto.day=1;


        SlctQry query = newSelectQuery(getTableName(Transaction.class));
        query.addConditions("accountOwner="+toValStr(reqdto.uname));
        query.addConditions("timestamp>"+ beforeTmstmp(reqdto.day));
        query.addOrderBy("timestamp desc");
        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);
    }
