package orgx.orm;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import orgx.transaction.Transaction;
import orgx.uti.context.ProcessContext;

import java.io.IOException;
import java.io.InputStream;

import static orgx.uti.context.ProcessContext.mpprMap;
import static orgx.uti.context.ThreadContext.sqlSessionThreadLocal;

public class MbtsUti {



    public static <T> int merge(Object tx) {

        SqlSession sqlSess = sqlSessionThreadLocal.get();
//        Class<?> aClass = tx.getClass();
//        String name = aClass.getSimpleName();
//        String mpprName = (name + "Mapper");
//        String pkgMpr = "orgx.mapper." + mpprName;
//
//        System.out.println(pkgMpr);
        Class<T> aClass = (Class<T>) tx.getClass();
        Class<?> mprClz = mpprMap.get(aClass);
        //Class.forName(pkgMpr);
        //
        BaseMapper mp = (BaseMapper) sqlSess.getMapper(mprClz);
        return mp.updateById(tx);
    }

    public static <T> T selectById(Class<T> aClass, String id) {
        SqlSession sqlSess = sqlSessionThreadLocal.get();
//        Class<?> aClass = tx.getClass();
//        String name = aClass.getSimpleName();
//        String mpprName = (name + "Mapper");
//        String pkgMpr = "orgx.mapper." + mpprName;
//
//        System.out.println(pkgMpr);
        Class<?> type = mpprMap.get(aClass);
        if(type==null){
            throw new RuntimeException("get mpr is null");
        }
        //Class.forName(pkgMpr);
        //
        BaseMapper mp = (BaseMapper) sqlSess.getMapper(type);
        return (T) mp.selectById(id);
    }
    public static <T,mpclz> T selectOne4lockForUpdt(LambdaQueryWrapper<T> qry, Class<mpclz> mprClz) {
        SqlSession sqlSess = sqlSessionThreadLocal.get();
        // Class<BalanceMapper> type = BalanceMapper.class;
//        Class mpcls=mprClz;
//        if (Transaction.class.isAssignableFrom(T))
//        {
//
//        }
        BaseMapper<T> mapper = (BaseMapper<T>) sqlSess.getMapper(mprClz);
        return mapper.selectOne(qry);
    }
    public static void persist(Object tx) throws ClassNotFoundException {

        SqlSession sqlSess = sqlSessionThreadLocal.get();
        Class<?> aClass = tx.getClass();
        String name = aClass.getSimpleName();
        String mpprName = (name + "Mapper");
        String pkgMpr = "orgx.mapper." + mpprName;

        System.out.println(pkgMpr);
        Class<?> type = mpprMap.get(aClass);
        //Class.forName(pkgMpr);
        //
        BaseMapper mp = (BaseMapper) sqlSess.getMapper(type);
        mp.insert(tx);

    }

    public static SqlSessionFactory buildSessionFactory(PooledDataSource dataSource, Class[] mappers) throws Exception {
        // 1. 配置数据源
        // PooledDataSource dataSource =ProcessContext.dataSource;


        // 2. 创建 MyBatis-Plus 配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true); // 开启驼峰命名转换
        configuration.setJdbcTypeForNull(null); // 处理 NULL 值问题
        configuration.setDefaultScriptingLanguage(XMLLanguageDriver.class); // 设置默认 SQL 语言驱动
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);


// 2. 加载 Mapper XML..
        //第二处 "mapper/GameKyGameRecordMapper.xml" 是 XMLMapperBuilder 的 resource 参数，目的是 告诉 MyBatis 这个 XML 文件的名称，用于 命名空间解析。

        String mapperPath = "mapper/GameKyGameRecordMapper.xml";
        //  cfgAddMaprXml(mapperPath, configuration);

        // load mapper cls
        for(Class mapper : mappers) {
            configuration.addMapper(mapper);
        }



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
        ProcessContext.sqlSessionFactory=sqlSessionFactory;
        return sqlSessionFactory;
    }
    private static void cfgAddMaprXml(String mapperPath, MybatisConfiguration configuration) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(mapperPath);
        if (inputStream == null) {
            throw new RuntimeException("无法加载 XML 文件：" + mapperPath);
        }
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(
                Resources.getResourceAsStream(mapperPath),
                configuration,
                mapperPath,
                configuration.getSqlFragments()
        );
        xmlMapperBuilder.parse();
    }
}
