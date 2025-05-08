package util.cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.DomainDataRegion;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.cache.spi.access.EntityDataAccess;
import org.hibernate.metamodel.model.domain.NavigableRole;

public class MyDomainDataRegion implements DomainDataRegion {

    private final String regionName;

    public MyDomainDataRegion(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public EntityDataAccess getEntityDataAccess(NavigableRole navigableRole) {
        // 简单返回一个基础的 EntityDataAccess 实现
        return new MyEntityDataAccess(regionName);
    }

    @Override
    public String getName() {
        return regionName;
    }

    @Override
    public RegionFactory getRegionFactory() {
        return null; // 根据需求返回相应的 RegionFactory
    }

    @Override
    public void clear() {
        // 清空缓存逻辑
    }

    @Override
    public void destroy() throws CacheException {
        // 销毁缓存逻辑
    }

    @Override
    public CollectionDataAccess getCollectionDataAccess(NavigableRole navigableRole) {
        return null; // 可选：如果不需要集合缓存，返回 null
    }

    @Override
    public NaturalIdDataAccess getNaturalIdDataAccess(NavigableRole navigableRole) {
        return null; // 可选：如果不需要自然ID缓存，返回 null
    }
}
