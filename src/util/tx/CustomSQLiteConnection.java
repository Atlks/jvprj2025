package util.tx;

// org.sqlite;

import org.sqlite.jdbc4.JDBC4Connection;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import static util.tx.UtilMysql.getDbNameSqlt;


public class CustomSQLiteConnection extends JDBC4Connection {

    // 构造函数
   // Properties: 创建一个空的 Properties 对象，你可以根据需要向其中添加连接属性。
    //timeout etc..
    public CustomSQLiteConnection(String url) throws SQLException {
     // String dbnm=;
        super(url, Objects.requireNonNull(getDbNameSqlt(url)),new Properties()); // 调用父类构造函数
    }

    // 添加自定义方法
    public void customMethod() {
        // 自定义逻辑，例如打印连接信息
        System.out.println("Custom method executed on SQLiteConnection.");
    }


    public static CustomSQLiteConnection getConnSqlt(String url) throws SQLException {
        return new CustomSQLiteConnection(url);
    }
}
