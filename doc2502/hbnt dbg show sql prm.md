

= kmplt fun log..then not need sql log

=chg drv to p6spy drv

public static String getDvr(String jdbcUrl) {
return  "com.p6spy.engine.spy.P6SpyDriver";


=chg jdbcurl
jdbcurl  jdbc:p6spy:mysql://localhost:3306/prjdb?user=root&password=pppppp

=spy.prp  cfg
==然后检查 spy.log 看 Hibernate 执行的实际 SQL 顺序。