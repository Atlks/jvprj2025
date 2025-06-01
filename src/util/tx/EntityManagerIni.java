//package util.tx;
//
//import jakarta.persistence.*;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaDelete;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.CriteriaUpdate;
//import jakarta.persistence.metamodel.Metamodel;
//
//import java.util.List;
//import java.util.Map;
//
//import static util.misc.Util2025.mkdir2025;
//import static util.misc.Util2025.writeFile2501;
//import static util.misc.UtilEncode.encodeFilename;
//import static util.tx.dbutil.*;
//import static util.misc.util2026.*;
//
//public class EntityManagerIni extends EntityManagerBase implements EntityManager {
//
//    private final String saveDir;
//
//    public EntityManagerIni(String saveDir0) {
//
//        mkdir2025(saveDir0);
//        saveDir = saveDir0;
//    }
//
//    /**
//     * @param obj
//     */
//    @Override
//    public void persist(Object obj) {
//// PersistenceManager
//        String fname = (String) getField2025(obj, "id", "");
//        //todo need fname encode
//        fname = encodeFilename(fname) + ".ini";
//
//        String fnamePath = saveDir + "/" + fname;
//        System.out.println("fnamePath=" + fnamePath);
//        writeFile2501(fnamePath, encodeIni(obj));
//
//
//    }
//
//    /**
//     * @param t
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> T merge(T obj) {
//
//
//        persist(obj);
//        return obj;
//
//    }
//
//    /**
//     * @param o
//     */
//    @Override
//    public void remove(Object obj) {
//        String fname = (String) getField2025(obj, "id", "");
//        //todo need fname encode
//        fname = encodeFilename(fname) + ".ini";
//
//        String fnamePath = saveDir + "/" + fname;
//        System.out.println("fnamePath=" + fnamePath);
//        removeFile(fnamePath);
//    }
//
//
//    /**
//     * @param aClass
//     * @param o
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> T find(Class<T> aClass, Object id) {
//
//
//        String fname = encodeFilename(id.toString()) + ".ini";
//
//        String collPath = saveDir + "/" + aClass.getSimpleName();
//        String fnamePath = collPath + "/" + fname;
//        System.out.println("fnamePath=" + fnamePath);
//
//        var map = getObjIni(id.toString(), collPath);
//        try {
//            return toObj(map, aClass);
//        } catch (Exception e) {
//            throwEx(e);
//        }
//        return null;
//    }
//
//    /**
//     * @param aClass
//     * @param o
//     * @param map
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
//        return null;
//    }
//
//    /**
//     * @param aClass
//     * @param o
//     * @param lockModeType
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
//        return null;
//    }
//
//    /**
//     * @param aClass
//     * @param o
//     * @param lockModeType
//     * @param map
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
//        return null;
//    }
//
//    /**
//     * @param aClass
//     * @param o
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> T getReference(Class<T> aClass, Object o) {
//        return null;
//    }
//
//    /**
//     *
//     */
//    @Override
//    public void flush() {
//
//    }
//
//    /**
//     * @param flushModeType
//     */
//    @Override
//    public void setFlushMode(FlushModeType flushModeType) {
//
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public FlushModeType getFlushMode() {
//        return null;
//    }
//
//    /**
//     * @param o
//     * @param lockModeType
//     */
//    @Override
//    public void lock(Object o, LockModeType lockModeType) {
//
//    }
//
//    /**
//     * @param o
//     * @param lockModeType
//     * @param map
//     */
//    @Override
//    public void lock(Object o, LockModeType lockModeType, Map<String, Object> map) {
//
//    }
//
//    /**
//     * @param o
//     */
//    @Override
//    public void refresh(Object o) {
//
//    }
//
//    /**
//     * @param o
//     * @param map
//     */
//    @Override
//    public void refresh(Object o, Map<String, Object> map) {
//
//    }
//
//    /**
//     * @param o
//     * @param lockModeType
//     */
//    @Override
//    public void refresh(Object o, LockModeType lockModeType) {
//
//    }
//
//    /**
//     * @param o
//     * @param lockModeType
//     * @param map
//     */
//    @Override
//    public void refresh(Object o, LockModeType lockModeType, Map<String, Object> map) {
//
//    }
//
//    /**
//     *
//     */
//    @Override
//    public void clear() {
//
//    }
//
//    /**
//     * @param o
//     */
//    @Override
//    public void detach(Object o) {
//
//    }
//
//    /**
//     * @param o
//     * @return
//     */
//    @Override
//    public boolean contains(Object o) {
//        return false;
//    }
//
//    /**
//     * @param o
//     * @return
//     */
//    @Override
//    public LockModeType getLockMode(Object o) {
//        return null;
//    }
//
//    /**
//     * @param s
//     * @param o
//     */
//    @Override
//    public void setProperty(String s, Object o) {
//
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public Map<String, Object> getProperties() {
//        return Map.of();
//    }
//
//    /**
//     * @param s
//     * @return
//     */
//    @Override
//    public Query createQuery(String s) {
//        return null;
//    }
//
//    /**
//     * @param criteriaQuery
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
//        return null;
//    }
//
//    /**
//     * @param criteriaUpdate
//     * @return
//     */
//    @Override
//    public Query createQuery(CriteriaUpdate criteriaUpdate) {
//        return null;
//    }
//
//    /**
//     * @param criteriaDelete
//     * @return
//     */
//    @Override
//    public Query createQuery(CriteriaDelete criteriaDelete) {
//        return null;
//    }
//
//    /**
//     * @param s
//     * @param aClass
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> TypedQuery<T> createQuery(String s, Class<T> aClass) {
//        return null;
//    }
//
//    /**
//     * @param s
//     * @return
//     */
//    @Override
//    public Query createNamedQuery(String s) {
//        return null;
//    }
//
//    /**
//     * @param s
//     * @param aClass
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> TypedQuery<T> createNamedQuery(String s, Class<T> aClass) {
//        return null;
//    }
//
//    /**
//     * @param s
//     * @return
//     */
//    @Override
//    public Query createNativeQuery(String s) {
//        return null;
//    }
//
//    /**
//     * @param s
//     * @param aClass
//     * @return
//     */
//    @Override
//    public Query createNativeQuery(String s, Class aClass) {
//        return null;
//    }
//
//    /**
//     * @param s
//     * @param s1
//     * @return
//     */
//    @Override
//    public Query createNativeQuery(String s, String s1) {
//        return null;
//    }
//
//    /**
//     * @param s
//     * @return
//     */
//    @Override
//    public StoredProcedureQuery createNamedStoredProcedureQuery(String s) {
//        return null;
//    }
//
//    /**
//     * @param s
//     * @return
//     */
//    @Override
//    public StoredProcedureQuery createStoredProcedureQuery(String s) {
//        return null;
//    }
//
//    /**
//     * @param s
//     * @param classes
//     * @return
//     */
//    @Override
//    public StoredProcedureQuery createStoredProcedureQuery(String s, Class... classes) {
//        return null;
//    }
//
//    /**
//     * @param s
//     * @param strings
//     * @return
//     */
//    @Override
//    public StoredProcedureQuery createStoredProcedureQuery(String s, String... strings) {
//        return null;
//    }
//
//    /**
//     *
//     */
//    @Override
//    public void joinTransaction() {
//
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public boolean isJoinedToTransaction() {
//        return false;
//    }
//
//    /**
//     * @param aClass
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> T unwrap(Class<T> aClass) {
//        return null;
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public Object getDelegate() {
//        return null;
//    }
//
//    /**
//     *
//     */
//    @Override
//    public void close() {
//
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public boolean isOpen() {
//        return false;
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public EntityTransaction getTransaction() {
//        return null;
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public EntityManagerFactory getEntityManagerFactory() {
//        return null;
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public CriteriaBuilder getCriteriaBuilder() {
//        return null;
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public Metamodel getMetamodel() {
//        return null;
//    }
//
//    /**
//     * @param aClass
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> EntityGraph<T> createEntityGraph(Class<T> aClass) {
//        return null;
//    }
//
//    /**
//     * @param s
//     * @return
//     */
//    @Override
//    public EntityGraph<?> createEntityGraph(String s) {
//        return null;
//    }
//
//    /**
//     * @param s
//     * @return
//     */
//    @Override
//    public EntityGraph<?> getEntityGraph(String s) {
//        return null;
//    }
//
//    /**
//     * @param aClass
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> aClass) {
//        return List.of();
//    }
//}
