package cfg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.SessionFactory;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.*;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.SimpleLoadTimeWeaver;
import org.springframework.scheduling.annotation.EnableAsync;
import util.evtdrv.ApplicationEventPublisherImplt;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static cfg.BaseHdr.saveDirUsrs;
import static util.tx.HbntUtil.getSessionFactory;

@EnableAsync  // 启用异步功能
@Configuration
//@EnableAspectJAutoProxy(proxyTargetClass = false)  // 禁用类加载时的字节码增强
@ComponentScan("")  // 指定扫描包 all pkg if empty  ComponentScan 注解的参数为空字符串，这意味着它不会扫描任何包。Spring 无法找到
//@EnableMBeanExport
@EnableMBeanExport
//启用 MBean（Managed Bean） 的导出，即 将 Spring 管理的 Bean 注册到 JMX（Java Management Extensions） 中，使其可以被 JMX 监控和管理。
public class AppConfig {

    @Bean
    public LoadTimeWeaver loadTimeWeaver() {
        return new SimpleLoadTimeWeaver();
    }
    public static   SessionFactory sessionFactory;
    @Bean
    public SessionFactory sessionFactory() throws SQLException, FileNotFoundException {
        if(sessionFactory==null)
        {
            List<Class> li = List.of();
            MyCfg.iniContnr4cfgfile();
            SessionFactory sessionFactory2 = getSessionFactory(saveDirUsrs, li);
            sessionFactory=sessionFactory2;
            Containr.sessionFactory=sessionFactory2;
            return sessionFactory;
        }else
            return  sessionFactory;

    }


    @Bean
    public ApplicationEventPublisher applicationEventPublisher() {
        return new ApplicationEventPublisherImplt();
    }

    @Bean
    public SpringDocConfigProperties springDocConfigProperties() {
        SpringDocConfigProperties config = new SpringDocConfigProperties();
      //  config.setApiDocsPath("/v3/api-docs");
     //   config.setSwaggerUiPath("/swagger-ui.html");


        return config;
    }

    //手动注入class
//    @Bean
//    public QueryUsrHdr queryUsrHdr(@Autowired SessionFactory sessionFactory) {
//        // 确保 sessionFactory 被正确注入
//        return new QueryUsrHdr(sessionFactory);
//    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("defaultCache");
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // 让 Jackson 支持 LocalDate
        return mapper;
    }
}
