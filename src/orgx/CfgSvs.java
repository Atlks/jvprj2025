package orgx;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import orgx.orm.MyMetaObjectHandler;
import orgx.u.User;
import orgx.u.UserMapper;
import orgx.uti.context.ProcessContext;
import orgx.uti.orm.CustomInterceptor;

public class CfgSvs {

    /// jdbc:h2:file:/data/testdb;AUTO_SERVER=TRUE
    static SessionFactory getSessionFactory() {
        Configuration config = new Configuration();
        config.addAnnotatedClass(User.class); // **绑定实体类**
        config.setProperty("hibernate.connection.url", "jdbc:h2:file:/data/testdb;AUTO_SERVER=TRUE");
        config.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        config.setProperty("hibernate.connection.username", "sa");
        config.setProperty("hibernate.connection.password", "");
        config.setProperty("hibernate.hbm2ddl.auto", "update");
        config.setInterceptor(new CustomInterceptor()); // **注册拦截器**
// **创建 SessionFactory**
        SessionFactory sessionFactory = config.buildSessionFactory();

        ProcessContext.sessionFactory=sessionFactory;
        return sessionFactory;
    }


    /**
     * 纯java 代码配置使用mybatisplus，没有spring
     * 🔹 方法 2：使用 mybatis-enhance-actable 插件
     * 如果你使用的是 Spring Boot + MyBatis-Plus，可以使用 mybatis-enhance-actable 插件，它可以在 系统启动时自动创建或更新数据库表。你可以参考 CSDN 相关教程 进行配置。
     *
     *
     * 手动实现 基于实体类自动创建表 的功能。可以使用 MyBatis-Plus 的 MetaObjectHandler
     *
     * 示例：
     * @return
     * @throws Exception
     */
    public static SqlSessionFactory buildSessionFactory() throws Exception {
        // 1. 配置数据源
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver("org.h2.Driver"); // 适用于 MySQL
        dataSource.setUrl("jdbc:h2:file:/data/testdb;AUTO_SERVER=TRUE");
        dataSource.setUsername("sa");
        dataSource.setPassword("");


        // 2. 创建 MyBatis-Plus 配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true); // 开启驼峰命名转换
        configuration.setJdbcTypeForNull(null); // 处理 NULL 值问题
        configuration.setDefaultScriptingLanguage(XMLLanguageDriver.class); // 设置默认 SQL 语言驱动
        // 3. 注册 Mapper（无需 XML）
        configuration.addMapper(UserMapper.class);
       // configuration.table-auto" value="update"  for spr app
     //   configuration.setMetaObjectHandler(new MyMetaObjectHandler()); // 注册 MetaObjectHandler
        // 3. 创建 GlobalConfig 并注册 MetaObjectHandler
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());


        // 3. 设置环境（**避免 NullPointerException**）
        org.apache.ibatis.mapping.Environment environment = new Environment("development", new JdbcTransactionFactory(), dataSource);
        configuration.setEnvironment(environment);



        // 3. 构建 `SqlSessionFactory`
        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(configuration);
        if (sqlSessionFactory == null) {
            throw new RuntimeException("SqlSessionFactory 初始化失败");
        }
        return sqlSessionFactory;

        // 2. 设置环境
//        org.apache.ibatis.session.Environment environment = new org.apache.ibatis.session.Environment(
//                "development",
//                new JdbcTransactionFactory(),
//                dataSource
//        );
//        configuration.setEnvironment(environment);
//
//        // 3. 构建 `SqlSessionFactory`
//        return new MybatisSqlSessionFactoryBuilder().build(configuration);

        // 2. 创建 MyBatis 配置
//        Configuration configuration = new Configuration();
//        configuration.setEnvironment(new org.apache.ibatis.session.Environment(
//                "development",
//                new JdbcTransactionFactory(),
//                dataSource
//        ));
//
//        // 3. 注册 Mapper（无需 XML）
//        configuration.addMapper(UserMapper.class);
//
//        // 4. 构建 `SqlSessionFactory`
//        return new SqlSessionFactoryBuilder().build(configuration);
    }
}
