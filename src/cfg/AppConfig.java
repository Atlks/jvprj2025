//package cfg;
//
//import com.mysql.cj.jdbc.MysqlDataSource;
//import jakarta.persistence.EntityManagerFactory;
//import org.hibernate.cfg.Environment;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import orgx.uti.context.ProcessContext;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//import static util.oo.StrUtil.getPwdFromJdbcurl;
//import static util.oo.StrUtil.getUnameFromJdbcurl;
//
//@Configuration
//@EnableJpaRepositories(basePackages = "handler")
//@EnableTransactionManagement
//public class AppConfig {
//
//    @Bean
//    public DataSource dataSource() {
//        return new MysqlDataSource() {{
//            setURL(ProcessContext.jdbcUrl);
//            setUser(getUnameFromJdbcurl(ProcessContext.jdbcUrl));
//            setPassword(getPwdFromJdbcurl(ProcessContext.jdbcUrl));
//        }};
////        dataSource.setUrl(  );
////        dataSource.setUsername(getUnameFromJdbcurl(ProcessContext.jdbcUrl));
////        dataSource.setPassword(getPwdFromJdbcurl(ProcessContext.jdbcUrl));
////        return new org.h2.jdbcx.JdbcDataSource() {{
////            setURL(ProcessContext.jdbcUrl);
////            setUser(getUnameFromJdbcurl(ProcessContext.jdbcUrl));
////            setPassword(getPwdFromJdbcurl(ProcessContext.jdbcUrl));
////        }};
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//        var em = new LocalContainerEntityManagerFactoryBean();
//        em.setEntityManagerFactoryInterface(jakarta.persistence.EntityManagerFactory.class); //
//        em.setDataSource(dataSource);
//        em.setPackagesToScan("model", "entityx", "util.entty","util.model");
//
//
//        var vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//
//        var props = new Properties();
//        props.setProperty("hibernate.hbm2ddl.auto", "update");
//       // props.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        props.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
//        em.setJpaProperties(props);
//
//        return em;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//        return new JpaTransactionManager(emf);
//    }
//}
//
