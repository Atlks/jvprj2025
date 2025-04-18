package util.mysqlSpy;

import java.sql.*;
import java.util.Properties;

public class MySpyDriver implements Driver {
    static {
        try {
            DriverManager.registerDriver(new MySpyDriver());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register MySpyDriver", e);
        }
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        // 把原始 URL 中去掉前缀 jdbc:myspy:
        String realUrl = url.replace("jdbc:myspy:", "jdbc:");

        Driver realDriver = DriverManager.getDriver(realUrl);
        return new MySpyConnection(realDriver.connect(realUrl, info));
    }

    @Override
    public boolean acceptsURL(String url) {
        return url.startsWith("jdbc:myspy:");
    }

    // 其他方法照抄
    @Override public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) { return new DriverPropertyInfo[0]; }
    @Override public int getMajorVersion() { return 1; }
    @Override public int getMinorVersion() { return 0; }
    @Override public boolean jdbcCompliant() { return false; }
    @Override public java.util.logging.Logger getParentLogger() { return null; }
}
