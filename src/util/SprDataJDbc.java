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


    public static List<Map<String, Object>> queryForListMap(String sql, Object... params) {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
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
}


public interface UserRepository extends JdbcRepository<Map<String, Object>, Long> {

    @Query("SELECT * FROM users WHERE ...")
    List<Map<String, Object>> findUsersBySomeCriteria(...);
}