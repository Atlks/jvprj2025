package cfg;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import orgx.uti.context.ProcessContext;

import java.io.InputStream;
import java.util.List;

import static util.oo.StrUtil.getPwdFromJdbcurl;
import static util.oo.StrUtil.getUnameFromJdbcurl;
import static util.tx.dbutil.getDvr;

public class CfgSvs {
    public static SqlSessionFactory buildSessionFactory(List<Class> mapperClzs) throws Exception {
        // 1. 配置数据源
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver(getDvr(ProcessContext.jdbcUrl)); // 适用于 MySQL"org.h2.Driver"
        dataSource.setUrl(  ProcessContext.jdbcUrl);
        dataSource.setUsername(getUnameFromJdbcurl(ProcessContext.jdbcUrl));
        dataSource.setPassword(getPwdFromJdbcurl(ProcessContext.jdbcUrl));


        // 2. 创建 MyBatis-Plus 配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true); // 开启驼峰命名转换
        configuration.setJdbcTypeForNull(null); // 处理 NULL 值问题
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);

        configuration.setDefaultScriptingLanguage(XMLLanguageDriver.class); // 设置默认 SQL 语言驱动

        configuration.setDefaultScriptingLanguage(XMLLanguageDriver.class);

// 2. 加载 Mapper XML
        //第二处 "mapper/GameKyGameRecordMapper.xml" 是 XMLMapperBuilder 的 resource 参数，目的是 告诉 MyBatis 这个 XML 文件的名称，用于 命名空间解析。

//        try{
//            String mapperPath = "mapper/GameKyGameRecordMapper.xml";
//            InputStream inputStream = Resources.getResourceAsStream(mapperPath);
//            if (inputStream == null) {
//                throw new RuntimeException("无法加载 XML 文件：" + mapperPath);
//            }
//            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(
//                    Resources.getResourceAsStream(mapperPath),
//                    configuration,
//                    mapperPath,
//                    configuration.getSqlFragments()
//            );
//            xmlMapperBuilder.parse();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }



        // load mapper cls
        for(Class clz : mapperClzs) {
            configuration.addMapper(clz);
        }
       // configuration.addMapper(mpp.class);
        // configuration.table-auto" value="update"  for spr app
        //   configuration.setMetaObjectHandler(new MyMetaObjectHandler()); // 注册 MetaObjectHandler
        // 3. 创建 GlobalConfig 并注册 MetaObjectHandler
        GlobalConfig globalConfig = new GlobalConfig();
        //  globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());


        // 3. 设置环境（**避免 NullPointerException**）
        org.apache.ibatis.mapping.Environment environment = new Environment("development", new JdbcTransactionFactory(), dataSource);
        configuration.setEnvironment(environment);


        // 3. 构建 `SqlSessionFactory`
        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(configuration);
        //   sqlSessionFactory.setMapperLocations("classpath*:mapper/*.xml");
        if (sqlSessionFactory == null) {
            throw new RuntimeException("SqlSessionFactory 初始化失败");
        }
        ProcessContext.sqlSessionFactory = sqlSessionFactory;
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
