package cfg;

import apiUsr.QueryUsrHdr;
import biz.BaseHdr;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.SimpleLoadTimeWeaver;
import org.springframework.scheduling.annotation.EnableAsync;

import java.sql.SQLException;
import java.util.List;

import static biz.BaseHdr.saveDirUsrs;
import static util.HbntUtil.getSessionFactory;

@EnableAsync  // 启用异步功能
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = false)  // 禁用类加载时的字节码增强
@ComponentScan("")  // 指定扫描包 all pkg if empty
//@EnableMBeanExport
@EnableMBeanExport
//启用 MBean（Managed Bean） 的导出，即 将 Spring 管理的 Bean 注册到 JMX（Java Management Extensions） 中，使其可以被 JMX 监控和管理。
public class AppConfig {

    @Bean
    public LoadTimeWeaver loadTimeWeaver() {
        return new SimpleLoadTimeWeaver();
    }

    @Bean
    public SessionFactory sessionFactory() throws SQLException {
        List<Class> li = List.of();
        BaseHdr.iniCfgFrmCfgfile();
        SessionFactory sessionFactory = getSessionFactory(saveDirUsrs, li);

        return sessionFactory;
    }


    //手动注入class
    @Bean
    public QueryUsrHdr queryUsrHdr(@Autowired SessionFactory sessionFactory) {
        // 确保 sessionFactory 被正确注入
        return new QueryUsrHdr(sessionFactory);
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("defaultCache");
    }
}
