


api 


// 获取缓存或在未命中时通过 supplier 加载（支持 Lambda）
public static <T> T getOrLoad(String key, Supplier<T> supplier);

// 手动放入缓存
public static void put(String key, Object value);

// 手动移除某个缓存项
public static void invalidate(String key);

// 清除所有缓存项
public static void clearAll()

# jcache规范


JCache（JSR 107）是 Java 缓存标准规范，其全称是：

JSR 107: Java Temporary Caching API

它是 Java 社区提出的一套统一的 缓存访问接口标准，目的是让不同厂商提供的缓存实现（如 Ehcache、Hazelcast、Infinispan、Caffeine 等）都能通过统一的 API 使用，类似 JDBC 规范于数据库。


✅ JCache 的核心组成：
CacheManager：缓存的管理器。

Cache<K, V>：表示一个键值对缓存结构。

CachingProvider：提供具体缓存实现的服务提供者。

注解支持（可选）：

@CacheResult：缓存方法结果

@CachePut：方法调用后更新缓存

@CacheRemove：移除缓存

@CacheDefaults：设置默认配置


jcache的方法
ChatGPT 说：
JCache（JSR 107）主要的 接口方法 来自 javax.cache.Cache，其设计和 Java Map 类似，适用于对缓存的基本操作。

🔧 主要方法：javax.cache.Cache<K, V>
方法名	描述
V get(K key)	获取指定 key 对应的缓存值
Map<K, V> getAll(Set<K>)	批量获取多个 key 的值
void put(K key, V value)	设置（更新）缓存项
void putAll(Map<K, V>)	批量放入缓存
boolean containsKey(K)	是否存在指定 key
boolean remove(K)	移除指定 key
boolean remove(K, V)	仅在值匹配的情况下移除（乐观并发）
void removeAll()	清空所有缓存项
void removeAll(Set<K>)	移除指定多个 key
boolean replace(K, V)	替换 key 对应的值
boolean replace(K, V, V)	仅当旧值匹配才替换为新值
void clear()	清除所有缓存（等同 removeAll）
Iterator<Entry<K, V>>	遍历缓存所有 key-value 项
void close()	关闭缓存资源