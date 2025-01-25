package util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SprDataJDbc {

    /**
     * jdbc:mysql://username:password@localhost:3306/mydatabase
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> queryForListMap(String jdbcUrl,String sql, Object... params) {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(getUsernameFromJdbcUrl(jdbcUrl));
        dataSource.setPassword(getPasswordFromJdbcUrl(jdbcUrl));
        // 可以根据需要设置其他属性，例如驱动类名、连接属性等
        // dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // Properties props = new Properties();
        // props.setProperty("propertyKey", "propertyValue");
        // dataSource.setConnectionProperties(props);

        DataSource dataSource2 = dataSource;
        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource2);

        RowMapper<Map<String, Object>> rowMapper = new RowMapper<>() {
            @Override
            public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> row = new LinkedHashMap<>();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnLabel(i), rs.getObject(i));
                }
                return row;
            }
        };
        return jdbcTemplate.query(sql, rowMapper, params);
    }

    public static String getUsernameFromJdbcUrl(String jdbcUrl) {
        try {
            // Check if the URL contains "://"
            int protocolEndIndex = jdbcUrl.indexOf("://");
            if (protocolEndIndex == -1) {
                throw new IllegalArgumentException("Invalid JDBC URL: missing '://'");
            }

            // Extract the part after "://"
            String remainder = jdbcUrl.substring(protocolEndIndex + 3);

            // Check if the remainder contains "@"
            int atIndex = remainder.indexOf('@');
            if (atIndex == -1) {
                // No username/password part
                return null;
            }

            // Extract the username:password part
            String userPass = remainder.substring(0, atIndex);

            // Split on ":" to separate username and password
            int colonIndex = userPass.indexOf(':');
            if (colonIndex == -1) {
                // Only username is present
                return userPass;
            } else {
                // Both username and password are present
                return userPass.substring(0, colonIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPasswordFromJdbcUrl(String jdbcUrl) {
        try {
            // Check if the URL contains "://"
            int protocolEndIndex = jdbcUrl.indexOf("://");
            if (protocolEndIndex == -1) {
                throw new IllegalArgumentException("Invalid JDBC URL: missing '://'");
            }

            // Extract the part after "://"
            String remainder = jdbcUrl.substring(protocolEndIndex + 3);

            // Check if the remainder contains "@"
            int atIndex = remainder.indexOf('@');
            if (atIndex == -1) {
                // No username/password part
                return null;
            }

            // Extract the username:password part
            String userPass = remainder.substring(0, atIndex);

            // Split on ":" to separate username and password
            int colonIndex = userPass.indexOf(':');
            if (colonIndex == -1 || colonIndex == userPass.length() - 1) {
                // No password is present or ":" is at the end of the string
                return null;
            } else {
                // Both username and password are present
                return userPass.substring(colonIndex + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


//public interface UserRepository extends JdbcRepository<Map<String, Object>, Long> {
//
//    @Query("SELECT * FROM users WHERE ...")
//    List<Map<String, Object>> findUsersBySomeCriteria(...);
//}