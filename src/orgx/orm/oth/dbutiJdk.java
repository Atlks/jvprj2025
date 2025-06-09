package orgx.orm.oth;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class dbutiJdk {


        public static int executeUpdate(String sql, Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            int affectedRows = stmt.executeUpdate(sql);
            System.out.println("Affected rows: " + affectedRows);
            return affectedRows;
        } catch (SQLException e) {
            System.out.println("---catch exeupdt sql: " + sql);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
