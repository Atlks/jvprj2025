package util.orm;

import cfg.Containr;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;

import java.sql.SQLException;

import static cfg.Containr.jdbcUrl;
import static util.oo.StrUtil.getPwdFromJdbcurl;
import static util.oo.StrUtil.getUnameFromJdbcurl;

public class HbntExt {

    public static void main(String[] args) {
     //   Table
    }

    public static void migrateSql() throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prjdb", "root", "pppppp");
//        System.out.println("连接成功：" + conn.getMetaData().getURL());
//        Enumeration<Driver> drivers = DriverManager.getDrivers();
//        while (drivers.hasMoreElements()) {
//            System.out.println("JDBC Driver: " + drivers.nextElement().getClass().getName());
//        }
//        System.out.println("Driver: " + java.sql.DriverManager.getDrivers());

        // 使用 BasicDataSource 来包装 Connection
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        String jdbcUrl= Containr.jdbcUrl;
        dataSource.setUsername(getUnameFromJdbcurl(jdbcUrl));
        dataSource.setPassword(getPwdFromJdbcurl(jdbcUrl));


        // Get a connection from the pool
//        try (Connection connection = dataSource.getConnection()) {
//            System.out.println("Connection Successful: " + connection.getMetaData().getURL());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        // 创建并配置 Flyway
        Flywayx flyway = Flyway_configure()
                .dataSource(dataSource)  // 直接传递 Connection 对象

                .locations("filesystem:sql") // 指向 SQL 脚本目录
                // .baselineOnMigrate(true) // If you are starting fresh with Flyway
                .load();


        // .dataSource("jdbc:mysql://localhost:3306/prjdb", getUnameFrmDburl(saveDirUsrs), getPwdFrmDburl(saveDirUsrs)) // 替换为你的实际信息

        // 执行迁移
        flyway.migrate();

        System.out.println("✅ 数据库字段删除迁移完成！");
    }



    private static Flywayx Flyway_configure() {
        return new Flywayx();
    }

}
