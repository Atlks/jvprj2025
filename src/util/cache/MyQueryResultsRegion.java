package util.cache;

import org.hibernate.cache.spi.QueryResultsRegion;

public class MyQueryResultsRegion implements QueryResultsRegion {
    public MyQueryResultsRegion(String regionName) {
    }
}
