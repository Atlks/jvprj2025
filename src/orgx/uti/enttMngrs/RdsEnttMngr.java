package orgx.uti.enttMngrs;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.metamodel.Metamodel;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import static orgx.uti.Uti.getField;
import static orgx.uti.Uti.toClassObj;

/**
 * 实现对redis的读写，主要是  persist  merge remove find getResultList方法的实现
 */
public class RdsEnttMngr extends EntityManagerBase implements EntityManager {


    public List getResultList(Class clazz) {

        String collectionName = clazz.getSimpleName();
        Map<String, String> resultMap = jedis.hgetAll(collectionName);

        // 将 Map 的值转换为 List
        return resultMap.values().stream().collect(Collectors.toList());
        // 创建 List，并添加三个 User 对象
//      List<User> userList = new ArrayList<>();
//      userList.add(new User(1, "Alice", 25));
//      userList.add(new User(2, "Bob", 30));
//      userList.add(new User(3, "Charlie", 28));
//       return userList;
    }


    public static final ObjectMapper objectMapper = new ObjectMapper();
    private Jedis jedis;

    public void RedisJsonStorage(String redisHost, int redisPort) {
        this.jedis = new Jedis(redisHost, redisPort);
    }

    /**
     * 将对象序列化为 JSON 并存入 Redis
     *
     * @param entity 要存储的对象
     */
    public void persist(Object entity) {
        try {
            String collectionName = entity.getClass().getSimpleName();

            String jsonValue = objectMapper.writeValueAsString(entity);
            Field field = getField(entity, jakarta.persistence.Id.class);
            field.setAccessible(true);
            String idValue = field.get(entity).toString(); // 获取 ID 值
            jedis.hset(collectionName, idValue, jsonValue);

        } catch (Exception e) {
            System.err.println("JSON 序列化失败：" + e.getMessage());
        }
    }



    //lok same wiz persist
    public <T> T merge(T entity) {
        persist(entity);
        return entity;
    }

    @Override
    public void remove(Object entity) {
        try {
            String jsonValue = objectMapper.writeValueAsString(entity);
            Field field = getField(entity, Id.class);
            field.setAccessible(true);
            String idValue = field.get(entity).toString(); // 获取 ID 值
            jedis.del(idValue);
        } catch (Exception e) {
            System.err.println("JSON 序列化失败：" + e.getMessage());
        }
    }

    /**
     * 从 Redis 读取 JSON 并反序列化为对象
     *
     * @return 反序列化后的对象
     */
    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        try {
            String collectionName = entityClass.getSimpleName();
            String objStr = jedis.hget(collectionName, (String) primaryKey);


            return toClassObj(objStr, entityClass);
        } catch (Exception e) {
            System.err.println("JSON 反序列化失败：" + e.getMessage());
            return null;
        }
    }

    ;





    @Override
    public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
        return null;
    }

    @Override
    public <T> T getReference(Class<T> aClass, Object o) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public void setFlushMode(FlushModeType flushModeType) {

    }

    @Override
    public FlushModeType getFlushMode() {
        return null;
    }

    @Override
    public void lock(Object o, LockModeType lockModeType) {

    }

    @Override
    public void lock(Object o, LockModeType lockModeType, Map<String, Object> map) {

    }

    @Override
    public void refresh(Object o) {

    }

    @Override
    public void refresh(Object o, Map<String, Object> map) {

    }

    @Override
    public void refresh(Object o, LockModeType lockModeType) {

    }

    @Override
    public void refresh(Object o, LockModeType lockModeType, Map<String, Object> map) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void detach(Object o) {

    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public LockModeType getLockMode(Object o) {
        return null;
    }

    @Override
    public void setProperty(String s, Object o) {

    }

    @Override
    public Map<String, Object> getProperties() {
        return Map.of();
    }

    @Override
    public Query createQuery(String s) {
        return null;
    }

    @Override
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return null;
    }

    @Override
    public Query createQuery(CriteriaUpdate criteriaUpdate) {
        return null;
    }

    @Override
    public Query createQuery(CriteriaDelete criteriaDelete) {
        return null;
    }

    @Override
    public <T> TypedQuery<T> createQuery(String s, Class<T> aClass) {
        return null;
    }

    @Override
    public Query createNamedQuery(String s) {
        return null;
    }

    @Override
    public <T> TypedQuery<T> createNamedQuery(String s, Class<T> aClass) {
        return null;
    }

    @Override
    public Query createNativeQuery(String s) {
        return null;
    }

    @Override
    public Query createNativeQuery(String s, Class aClass) {
        return null;
    }

    @Override
    public Query createNativeQuery(String s, String s1) {
        return null;
    }

    @Override
    public StoredProcedureQuery createNamedStoredProcedureQuery(String s) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s, Class... classes) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s, String... strings) {
        return null;
    }

    @Override
    public void joinTransaction() {

    }

    @Override
    public boolean isJoinedToTransaction() {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    @Override
    public Object getDelegate() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public EntityTransaction getTransaction() {
        return null;
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return null;
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return null;
    }

    @Override
    public Metamodel getMetamodel() {
        return null;
    }

    @Override
    public <T> EntityGraph<T> createEntityGraph(Class<T> aClass) {
        return null;
    }

    @Override
    public EntityGraph<?> createEntityGraph(String s) {
        return null;
    }

    @Override
    public EntityGraph<?> getEntityGraph(String s) {
        return null;
    }

    @Override
    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> aClass) {
        return List.of();
    }
}
