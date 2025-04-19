package ztest;

import org.mariadb.jdbc.MariaDbDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MariaDBEmbeddedExample {

    public static void main(String[] args) {
        try {
            // 创建数据源对象，指定嵌入式数据库位置
            MariaDbDataSource dataSource = new MariaDbDataSource();
         //   dataSource.setUrl("jdbc:mariadb://localhost:3306/mydb");  // 或者指定嵌入式路径
            dataSource.setUrl("jdbc:mariadb:embedded://localhost/mydb2025");
            // 建立数据库连接
            try (Connection conn = dataSource.getConnection()) {
                Statement stmt = conn.createStatement();
                // 创建表格
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY, name VARCHAR(50));");
                // 插入数据
                stmt.executeUpdate("INSERT INTO users (id, name) VALUES (1, 'John Doe');");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
