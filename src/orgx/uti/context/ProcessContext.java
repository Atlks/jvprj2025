package orgx.uti.context;

import com.fasterxml.classmate.util.ConcurrentTypeCache;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.SessionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import orgx.msc.Dtoo;
import orgx.msc.FunType;
import orgx.uti.orm.FunctionX;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 全局上下文，整合 Apache Commons Configuration，支持动态加载配置
 */
public class ProcessContext<T, R> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessContext.class);
public  static HttpServer httpServer;

    public  static Javalin appJvl;
    // 线程池（异步任务调度）
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    // Hibernate SessionFactory（线程安全）
    public static SessionFactory sessionFactory = null;


    //多个租户（多库实例），那
    public static   Map<String, SessionFactory> sessionFactories = new HashMap<String, SessionFactory>();
    //或 Map<String, DataSource>

  //public  static  List<HttpContext> httpContexts=new ArrayList<>();
//    HttpContext context = httpServer.createContext(path, handler);
//    ProcessContext.httpContexts
    // Guava 本地缓存
    private static final Cache<String, Object> localCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    // Apache Commons Configuration 读取 config.properties
    private static final FileBasedConfigurationBuilder<PropertiesConfiguration> configBuilder;
    public static Map<String, Annotation[]> authMap=new ConcurrentHashMap<>();
    public static ExceptionHandler<Exception> excptnHdlrInWeb;
    public static Authenticator authenticator;
    public static ExceptionHandler<Exception> excptnHdlr;

    public static Map<FunType, FunctionX<Map,Object>> funMap4httpHdlr =new ConcurrentHashMap<>();

    // #procs ctx.java
    public static FunctionX<Dtoo, Object> fun_userAdd;
    public static FunctionX<Dtoo, Object> fun_BlsAdd;
    public static PooledDataSource dataSource;
    public static SqlSessionFactory sqlSessionFactory;
    public static Map<Class,Class> mpprMap=new ConcurrentHashMap();
    private static Configuration config;


    public static FunctionX getFun(FunType funRegName) {
        return    funMap4httpHdlr.get( funRegName);
    }

    // Redis 客户端
    private static final Jedis redisClient = null;
    private static final Jedis redisLock = null; // 用于分布式锁

    static {
        try {
            // 初始化配置管理
            configBuilder = new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                    .configure(new Parameters().properties().setFileName("config.properties"));
            config = configBuilder.getConfiguration();

            // 初始化 Redis
//            redisClient = new Jedis(config.getString("redis.host", "localhost"),
//                    config.getInt("redis.port", 6379));
//            redisLock = new Jedis(config.getString("redis.host", "localhost"),
//                    config.getInt("redis.port", 6379));

            LOGGER.info("GlobalContext initialized successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize GlobalContext", e);
        }
    }


    // **本地缓存**
    public static void putLocalCache(String key, Object value) {
        localCache.put(key, value);
    }

    public static Object getLocalCache(String key) {
        return localCache.getIfPresent(key);
    }

    // **Redis 缓存**
    public static void putRedisCache(String key, String value) {
        redisClient.set(key, value);
    }

    public static String getRedisCache(String key) {
        return redisClient.get(key);
    }

    // **分布式锁**
    public static boolean acquireLock(String key, String value) {
        return "OK".equals(redisLock.set(key, value, SetParams.setParams().nx().ex(30)));
// 30秒过期
    }

    public static void releaseLock(String key) {
        redisLock.del(key);
    }

    // **获取配置**
    public static String getConfig(String key) {
        return config.getString(key);
    }

    public static int getConfigInt(String key) {
        return config.getInt(key);
    }

    public static boolean getConfigBoolean(String key) {
        return config.getBoolean(key);
    }

    // **手动刷新配置（支持动态更新）**
    public static void refreshConfig() {
        try {
            config = configBuilder.getConfiguration();
            LOGGER.info("Configuration refreshed successfully.");
        } catch (Exception e) {
            LOGGER.error("Failed to refresh configuration", e);
        }
    }

    public static void main(String[] args) {
        // **读取配置项**
//        System.out.println("App Mode: " + GlobalContext.getConfig("app.mode"));
//        System.out.println("Max Threads: " + GlobalContext.getConfigInt("app.maxThreads"));
//
//        // **缓存测试**
//        GlobalContext.putRedisCache("user_001", "Alice");
//        System.out.println("User from Redis: " + GlobalContext.getRedisCache("user_001"));
//
//        // **刷新配置**
//        GlobalContext.refreshConfig();
//        System.out.println("Updated App Mode: " + GlobalContext.getConfig("app.mode"));
    }
}
