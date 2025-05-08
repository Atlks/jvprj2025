package util.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import model.acc.GlbAcc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class CacheUtil implements CacheItfs {
    // 声明一个缓存（key 是字符串，value 是 GlbAcc）
 //   private static final Cache<String, GlbAcc> glbAccCache = CacheBuilder.newBuilder()
//            .expireAfterWrite(5, TimeUnit.MINUTES) // 缓存 5 分钟
//            .maximumSize(100)                      // 最多缓存 100 个条目
//            .build();
    private static final Cache<String, Object> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();

    @SuppressWarnings("unchecked")
    public static <T> T getOrLoad(String key, Supplier<T> supplier) {
        try {
            return (T) cache.get(key, (Callable<Object>) supplier::get);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load cache for key: " + key, e);
        }
    }

    public static void put(String key, Object value) {
        cache.put(key, value);
    }

    public static void invalidate(String key) {
        cache.invalidate(key);
    }

    public static void clearAll() {
        cache.invalidateAll();
    }
}
