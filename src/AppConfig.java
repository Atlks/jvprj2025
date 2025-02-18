import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.SimpleLoadTimeWeaver;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync  // 启用异步功能
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = false)  // 禁用类加载时的字节码增强
@ComponentScan("")  // 指定扫描包 all pkg if empty
@EnableMBeanExport
public class AppConfig {

    @Bean
    public LoadTimeWeaver loadTimeWeaver() {
        return new SimpleLoadTimeWeaver();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("defaultCache");
    }
}
