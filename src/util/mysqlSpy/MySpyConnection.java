package util.mysqlSpy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySpyConnection implements Connection {
    private final Connection conn;

    public MySpyConnection(Connection conn) {
        this.conn = conn;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return new MySpyPreparedStatement(conn.prepareStatement(sql), sql);
    }

    // 把其他 Connection 方法都代理转发出去（可以用 IDE 快捷键实现）
}

