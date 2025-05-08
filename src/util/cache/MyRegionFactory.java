package util.cache;

import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.cfg.spi.DomainDataRegionBuildingContext;
import org.hibernate.cache.cfg.spi.DomainDataRegionConfig;
import org.hibernate.cache.spi.*;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import java.util.Map;
import java.util.Properties;

public class MyRegionFactory implements RegionFactory {

    private static final long TIMESTAMP_INCREMENT = 1L;


    public void start(Properties properties) {
        // 初始化时的逻辑，如果需要，可以使用配置项进行初始化
        System.out.println("RegionFactory started with properties: " + properties);
    }


    public void stop() {
        // 停止时的逻辑
        System.out.println("RegionFactory stopped.");
    }

    @Override
    public void start(SessionFactoryOptions sessionFactoryOptions, Map<String, Object> map) throws CacheException {
        // 这里可以做更多的初始化，例如获取配置参数等
        System.out.println("RegionFactory started with session factory options.");
    }

    @Override
    public boolean isMinimalPutsEnabledByDefault() {
        return false;  // 可以根据需求更改
    }

    @Override
    public AccessType getDefaultAccessType() {
        // 定义默认的访问类型
        return AccessType.READ_WRITE;  // 可以根据实际情况选择
    }

    @Override
    public String qualify(String s) {
        // 返回区域的 qualified name
        return "qualified." + s;
    }

    @Override
    public long nextTimestamp() {
        // 返回一个新的时间戳，用于缓存条目的版本管理
        return System.currentTimeMillis() + TIMESTAMP_INCREMENT;
    }

    @Override
    public DomainDataRegion buildDomainDataRegion(DomainDataRegionConfig domainDataRegionConfig, DomainDataRegionBuildingContext domainDataRegionBuildingContext) {
        // 返回构建的DomainDataRegion
        return new MyDomainDataRegion(domainDataRegionConfig.getRegionName());
    }

    @Override
    public QueryResultsRegion buildQueryResultsRegion(String regionName, SessionFactoryImplementor sessionFactoryImplementor) {
        // 返回构建的QueryResultsRegion
        return new MyQueryResultsRegion(regionName);
    }

    @Override
    public TimestampsRegion buildTimestampsRegion(String regionName, SessionFactoryImplementor sessionFactoryImplementor) {
        // 返回构建的TimestampsRegion
        return new MyTimestampsRegion(regionName);
    }


    public Region buildEntityRegion(String regionName) {
        // 返回自定义的实体缓存区域
        return new MyRegion(regionName);  // 返回自定义的缓存区域
    }


    public Region buildCollectionRegion(String regionName) {
        // 返回自定义的集合缓存区域
        return new MyRegion(regionName);
    }


    public Region buildQueryResultsRegion(String regionName) {
        // 返回自定义的查询缓存区域
        return new MyRegion(regionName);
    }


    public Region buildTimestampsRegion(String regionName) {
        // 返回自定义的时间戳缓存区域
        return new MyRegion(regionName);
    }


    public void destroy() {
        // 销毁时的逻辑
        System.out.println("RegionFactory destroyed.");
    }
}
