package util;

import java.sql.SQLException;

public class UtilSqlt {

    public static CustomSQLiteConnection getConnSqlt(String url) throws SQLException {
        return new CustomSQLiteConnection(url);
    }
}
