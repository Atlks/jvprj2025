package ztest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class H2EmbeddedExample {

    public static void main(String[] args) {
        try {
            // 加载 H2 驱动
            Class.forName("org.h2.Driver");

            // 配置数据库 URL, 使用 MySQL 协议
          //  String jdbcUrl = "jdbc:h2:mem:test;MODE=MySQL";

            //H2 数据库的默认用户名是 sa，默认密码是空字符串（""）。
            var jdbcUrl="jdbc:h2:file:c:/dbx/test.h2;MODE=MySQL";
            try (Connection conn = DriverManager.getConnection(jdbcUrl, "sa", "")) {
                Statement stmt = conn.createStatement();
                // 创建表格
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY, name VARCHAR(50));");
                // 插入数据
                stmt.executeUpdate("INSERT INTO users (id, name) VALUES (1, 'John Doe');");
                System.out.println("ok");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

