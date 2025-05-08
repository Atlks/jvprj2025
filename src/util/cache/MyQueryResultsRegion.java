package util.cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.QueryResultsRegion;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

public class MyQueryResultsRegion implements QueryResultsRegion {
    public MyQueryResultsRegion(String regionName) {
    }

    @Override
    public Object getFromCache(Object o, SharedSessionContractImplementor sharedSessionContractImplementor) {
        return null;
    }

    @Override
    public void putIntoCache(Object o, Object o1, SharedSessionContractImplementor sharedSessionContractImplementor) {

    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public RegionFactory getRegionFactory() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void destroy() throws CacheException {

    }
}
