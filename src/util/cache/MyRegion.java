package util.cache;

import org.hibernate.cache.spi.Region;
import org.hibernate.cache.spi.RegionFactory;

import java.util.HashMap;
import java.util.Map;

public class MyRegion implements Region {

    private final String name;
    private final Map<Object, Object> cacheMap = new HashMap<>();

    public MyRegion(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public RegionFactory getRegionFactory() {
        return null;
    }

    public Object get(Object key) {
        return cacheMap.get(key);
    }

    public void put(Object key, Object value) {
        cacheMap.put(key, value);
    }

    public void evict(Object key) {
        cacheMap.remove(key);
    }

    public void clear() {
        cacheMap.clear();
    }


    public long getElementCountInMemory() {
        return cacheMap.size();
    }


    public long getSizeInMemory() {
        return cacheMap.size();
    }


    public long getElementCountOnDisk() {
        return 0;
    }

    @Override
    public void destroy() {
        cacheMap.clear();
    }
}
