//package test;
//
//import ch.vorburger.mariadb4j.DB;
//import ch.vorburger.mariadb4j.DBConfigurationBuilder;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.Statement;
//
//public class MariaDBEmbeddedExample22 {
//    public static void main(String[] args) throws Exception {
//      if("1"=="1")
//        return;
//        // 配置 MariaDB Embedded
//        DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
//        config.setPort(3307); // 设置端口
//        config.setDataDir("./data"); // 设置数据目录
//        config.setDeletingTemporaryBaseAndDataDirsOnShutdown(true); // 关闭时清理临时目录
//
//        // 启动 MariaDB Embedded
//        DB db = null;//DB.newEmbeddedDB(config.build());
//        db.start();
//
//        // 创建测试数据库
//        db.createDB("testdb");
//
//        // JDBC 连接
//        String jdbcUrl = "jdbc:mysql://localhost:3307/testdb";
//        Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
//
//        // 测试 SQL 操作
//        try (Statement statement = connection.createStatement()) {
//            statement.execute("CREATE TABLE example (id INT PRIMARY KEY, name VARCHAR(50));");
//            statement.execute("INSERT INTO example VALUES (1, 'Alice'), (2, 'Bob');");
//            System.out.println("Table created and data inserted successfully!");
//        }
//
//        // 停止 MariaDB Embedded
//        db.stop();
//    }
//}
//
