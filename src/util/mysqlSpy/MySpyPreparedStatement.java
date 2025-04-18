package util.mysqlSpy;
import com.mysql.cj.jdbc.ConnectionWrapper;
import com.mysql.cj.jdbc.MysqlPooledConnection;
import com.mysql.cj.jdbc.PreparedStatementWrapper;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;

public class MySpyPreparedStatement extends PreparedSttmtImpltBase implements PreparedStatement{
    private final String sqlTemplate;
    private final Map<Integer, Object> parameters = new HashMap<>();

    public MySpyPreparedStatement(ConnectionWrapper connWrapper, MysqlPooledConnection pooledConn, PreparedStatement ps, String sql) {
      //  super(connWrapper, pooledConn, ps);
        this.sqlTemplate = sql;
    }

    public MySpyPreparedStatement(  PreparedStatement ps, String sql) {
        //  super(connWrapper, pooledConn, ps);
        this.sqlTemplate = sql;
    }

    public String getRawSql() {
        return sqlTemplate;
    }

    public Map<Integer, Object> getParameters() {
        return parameters;
    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        parameters.put(parameterIndex, x);
        super.setObject(parameterIndex, x);
    }

    @Override
    public boolean execute() throws SQLException {
        logFullSql();
        return super.execute();
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        logFullSql();
        return super.executeQuery();
    }

    @Override
    public int executeUpdate() throws SQLException {
        logFullSql();
        return super.executeUpdate();
    }

    private void logFullSql() {
        String fullSql = buildFullSql(sqlTemplate, parameters);
        System.out.println("SQL: " + fullSql);
    }

    private String buildFullSql(String sql, Map<Integer, Object> params) {
        List<Object> sortedParams = new ArrayList<>(params.size());
        for (int i = 1; i <= params.size(); i++) {
            sortedParams.add(params.get(i));
        }
        for (Object param : sortedParams) {
            sql = sql.replaceFirst("\\?", Matcher.quoteReplacement("'" + param + "'"));
        }
        return sql;
    }
}
