//package orgx.orm;
//
//import lombok.experimental.Accessors;
//import util.tx.dbutil;
//
//import javax.sql.DataSource;
//import java.io.File;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import static orgx.uti.context.ThreadContext.currDbConn;
//import static util.algo.CallUtil.callTry;
//import static util.oo.FileUti.*;
//
//
//@Accessors(chain = true)
//public class Flywayx {
//    private Connection conn;
//    private String loc;
//
//    public Flywayx dataSource(DataSource dataSource) throws SQLException {
//        this.conn = dataSource.getConnection();
//        currDbConn.set(conn);
//        return this;
//    }
//
//    public Flywayx locations(String locations) {
//        this.loc = locations;
//        return this;
//    }
//
//    public Flywayx load() {
//
//
//        return this;
//    }
//
//    public void migrate() {
//
//        //remv classpath:   ,only ret  sql  (Dir)
//        String path = this.loc.substring(11);
//        File[] files = getFilesInJarDir(path);
//        for (File f : files) {
//            if(exist(f.getName(),"flywayLog/logdir"))
//
//                continue;
//            String txt = readTxtFrmFile(f);
//            String[] stmt = txt.split(";");
//            for (String sql : stmt) {
//                System.out.println(sql);
//               callTry(()->{
//                 dbutil.  executeUpdate(sql, conn);
//
//               });
//
//            }
//            copyFile2Dir(f,"flywayLog/logdir");
//        }
//    }
//
//
//}
