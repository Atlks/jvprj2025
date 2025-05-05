

# 自动化建库


public static void crtDatabase(String jdbcurl, String tbnm) throws SQLException {
if (jdbcurl.startsWith("jdbc:mysql")) {
var sqlCrtDb = "CREATE DATABASE IF NOT EXISTS " + tbnm + "";
//  stmt.execute(sqlCrtDb);
System.out.println(sqlCrtDb);


            //替换为sql好建立conn
            int lastSlashIndex = jdbcurl.lastIndexOf('/');
            var url_noDB = jdbcurl.substring(0, lastSlashIndex);
            String url = url_noDB + "/mysql?user=" + getUnameFromJdbcurl(jdbcurl) + "&password=" + getPwdFromJdbcurl(jdbcurl);
            System.out.println("crtDB().url=" + url);
            Connection conn = DriverManager.getConnection(url);
           executeUpdate(sqlCrtDb,conn);

        }

    }


# del col..flyway mdl


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


# updt col type 