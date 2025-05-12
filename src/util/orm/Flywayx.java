package util.orm;

import lombok.experimental.Accessors;
import util.tx.dbutil;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import static util.algo.CallUtil.callTry;
import static util.oo.FileUti.*;


@Accessors(chain = true)
public class Flywayx {
    private Connection conn;
    private String loc;

    public Flywayx dataSource(DataSource dataSource) throws SQLException {
        this.conn = dataSource.getConnection();
        return this;
    }

    public Flywayx locations(String locations) {
        this.loc = locations;
        return this;
    }

    public Flywayx load() {


        return this;
    }

    public void migrate() {

        String path = this.loc.substring(11);
        File[] files = getFilesInJarDir(path);
        for (File f : files) {
            if(exist(f,"flywayLog/logdir"))

                continue;
            String txt = readTxtFrmFile(f);
            String[] stmt = txt.split(";");
            for (String sql : stmt) {
                System.out.println(sql);
               callTry(()->{
                 dbutil.  executeUpdate(sql, conn);

               });

            }
            copyFile2Dir(f,"flywayLog/logdir");
        }
    }


}
