package util.cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.cache.spi.TimestampsRegion;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.util.HashMap;
import java.util.Map;

public class MyTimestampsRegion implements TimestampsRegion {

    private final String regionName;
    private final Map<Object, Object> cache = new HashMap<>();  // 缓存数据的简单实现

    // 构造方法，初始化时指定区域名称
    public MyTimestampsRegion(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public Object getFromCache(Object key, SharedSessionContractImplementor session) {
        // 从缓存中获取数据
        return cache.get(key);
    }

    @Override
    public void putIntoCache(Object key, Object value, SharedSessionContractImplementor session) {
        // 将数据放入缓存
        cache.put(key, value);
    }

    @Override
    public String getName() {
        // 返回缓存区域的名称
        return regionName;
    }

    @Override
    public RegionFactory getRegionFactory() {
        // 返回区域工厂，通常是自定义的RegionFactory实例
        return null;  // 你可以根据实际需求返回具体的RegionFactory实现
    }

    @Override
    public void clear() {
        // 清空缓存区域
        cache.clear();
    }

    @Override
    public void destroy() throws CacheException {
        // 销毁缓存区域，释放资源等
        cache.clear();  // 你可以在这里处理清理资源的逻辑
    }
}
