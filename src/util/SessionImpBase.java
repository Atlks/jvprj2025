package util;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.metamodel.Metamodel;
import org.hibernate.*;
import org.hibernate.graph.RootGraph;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.query.SelectionQuery;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaInsert;
import org.hibernate.query.criteria.JpaCriteriaInsertSelect;
import org.hibernate.stat.SessionStatistics;

import java.util.List;
import java.util.Map;

public abstract   class SessionImpBase   implements Session {
    Transaction Transaction1=new TransactionImp();

    /**
     *
     */
    @Override
    public void flush() {

    }

    /**
     * @param flushModeType
     */
    @Override
    public void setFlushMode(FlushModeType flushModeType) {

    }

    /**
     * @param flushMode
     */
    @Override
    public void setHibernateFlushMode(FlushMode flushMode) {

    }

    /**
     * @return
     */
    @Override
    public FlushModeType getFlushMode() {
        return null;
    }

    /**
     * @param o
     * @param lockModeType
     */
    @Override
    public void lock(Object o, LockModeType lockModeType) {

    }

    /**
     * @param o
     * @param lockModeType
     * @param map
     */
    @Override
    public void lock(Object o, LockModeType lockModeType, Map<String, Object> map) {

    }

    /**
     * @return
     */
    @Override
    public FlushMode getHibernateFlushMode() {
        return null;
    }

    /**
     * @param cacheMode
     */
    @Override
    public void setCacheMode(CacheMode cacheMode) {

    }

    /**
     * @return
     */
    @Override
    public CacheMode getCacheMode() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public CacheStoreMode getCacheStoreMode() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public CacheRetrieveMode getCacheRetrieveMode() {
        return null;
    }

    /**
     * @param cacheStoreMode
     */
    @Override
    public void setCacheStoreMode(CacheStoreMode cacheStoreMode) {

    }

    /**
     * @param cacheRetrieveMode
     */
    @Override
    public void setCacheRetrieveMode(CacheRetrieveMode cacheRetrieveMode) {

    }

    /**
     * @return
     */
    @Override
    public int getFetchBatchSize() {
        return 0;
    }

    /**
     * @param i
     */
    @Override
    public void setFetchBatchSize(int i) {

    }

    /**
     * @return
     */
    @Override
    public boolean isSubselectFetchingEnabled() {
        return false;
    }

    /**
     * @param b
     */
    @Override
    public void setSubselectFetchingEnabled(boolean b) {

    }

    /**
     * @return
     */
    @Override
    public SessionFactory getSessionFactory() {
        return null;
    }

    /**
     *
     */
    @Override
    public void cancelQuery() {

    }

    /**
     * @return
     */
    @Override
    public boolean isDirty() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean isDefaultReadOnly() {
        return false;
    }

    /**
     * @param b
     */
    @Override
    public void setDefaultReadOnly(boolean b) {

    }

    /**
     * @param o
     * @return
     */
    @Override
    public Object getIdentifier(Object o) {
        return null;
    }

    /**
     * @param s
     * @param o
     * @return
     */
    @Override
    public boolean contains(String s, Object o) {
        return false;
    }

    /**
     * @param o
     */
    @Override
    public void detach(Object o) {

    }

    /**
     * @param o
     * @return
     */
    @Override
    public boolean contains(Object o) {
        return false;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public LockModeType getLockMode(Object o) {
        return null;
    }

    /**
     * @param s
     * @param o
     */
    @Override
    public void setProperty(String s, Object o) {

    }

    /**
     * @return
     */
    @Override
    public Map<String, Object> getProperties() {
        return Map.of();
    }

    /**
     * @param o
     */
    @Override
    public void evict(Object o) {

    }

    /**
     * @param aClass
     * @param o
     * @param lockMode
     * @deprecated
     */
    @Override
    public <T> T load(Class<T> aClass, Object o, LockMode lockMode) {
        return null;
    }

    /**
     * @param aClass
     * @param o
     * @param lockOptions
     * @deprecated
     */
    @Override
    public <T> T load(Class<T> aClass, Object o, LockOptions lockOptions) {
        return null;
    }

    /**
     * @param s
     * @param o
     * @param lockMode
     * @deprecated
     */
    @Override
    public Object load(String s, Object o, LockMode lockMode) {
        return null;
    }

    /**
     * @param s
     * @param o
     * @param lockOptions
     * @deprecated
     */
    @Override
    public Object load(String s, Object o, LockOptions lockOptions) {
        return null;
    }

    /**
     * @param aClass
     * @param o
     * @deprecated
     */
    @Override
    public <T> T load(Class<T> aClass, Object o) {
        return null;
    }

    /**
     * @param s
     * @param o
     * @deprecated
     */
    @Override
    public Object load(String s, Object o) {
        return null;
    }

    /**
     * @param o
     * @param o1
     */
    @Override
    public void load(Object o, Object o1) {

    }

    /**
     * @param o
     * @param replicationMode
     * @deprecated
     */
    @Override
    public void replicate(Object o, ReplicationMode replicationMode) {

    }

    /**
     * @param s
     * @param o
     * @param replicationMode
     * @deprecated
     */
    @Override
    public void replicate(String s, Object o, ReplicationMode replicationMode) {

    }

    /**
     * @param o
     * @deprecated
     */
    @Override
    public Object save(Object o) {
        return null;
    }

    /**
     * @param s
     * @param o
     * @deprecated
     */
    @Override
    public Object save(String s, Object o) {
        return null;
    }

    /**
     * @param o
     * @deprecated
     */
    @Override
    public void saveOrUpdate(Object o) {

    }

    /**
     * @param s
     * @param o
     * @deprecated
     */
    @Override
    public void saveOrUpdate(String s, Object o) {

    }

    /**
     * @param o
     * @deprecated
     */
    @Override
    public void update(Object o) {

    }

    /**
     * @param s
     * @param o
     * @deprecated
     */
    @Override
    public void update(String s, Object o) {

    }

    /**
     * @param t
     * @param <T>
     * @return
     */
    @Override
    public <T> T merge(T t) {
        return null;
    }

    /**
     * @param s
     * @param t
     * @param <T>
     * @return
     */
    @Override
    public <T> T merge(String s, T t) {
        return null;
    }

    /**
     * @param o
     */
    @Override
    public void persist(Object o) {

    }

    /**
     * @param s
     * @param o
     */
    @Override
    public void persist(String s, Object o) {

    }

    /**
     * @param o
     * @deprecated
     */
    @Override
    public void delete(Object o) {

    }

    /**
     * @param s
     * @param o
     * @deprecated
     */
    @Override
    public void delete(String s, Object o) {

    }

    /**
     * @param o
     * @param lockMode
     */
    @Override
    public void lock(Object o, LockMode lockMode) {

    }

    /**
     * @param o
     * @param lockOptions
     */
    @Override
    public void lock(Object o, LockOptions lockOptions) {

    }

    /**
     * @param s
     * @param o
     * @param lockMode
     * @deprecated
     */
    @Override
    public void lock(String s, Object o, LockMode lockMode) {

    }

    /**
     * @param lockOptions
     * @deprecated
     */
    @Override
    public LockRequest buildLockRequest(LockOptions lockOptions) {
        return null;
    }

    /**
     * @param o
     */
    @Override
    public void refresh(Object o) {

    }

    /**
     * @param o
     * @param map
     */
    @Override
    public void refresh(Object o, Map<String, Object> map) {

    }

    /**
     * @param o
     * @param lockModeType
     */
    @Override
    public void refresh(Object o, LockModeType lockModeType) {

    }

    /**
     * @param o
     * @param lockModeType
     * @param map
     */
    @Override
    public void refresh(Object o, LockModeType lockModeType, Map<String, Object> map) {

    }

    /**
     * @param s
     * @param o
     * @deprecated
     */
    @Override
    public void refresh(String s, Object o) {

    }

    /**
     * @param o
     * @param lockMode
     */
    @Override
    public void refresh(Object o, LockMode lockMode) {

    }

    /**
     * @param o
     * @param lockOptions
     */
    @Override
    public void refresh(Object o, LockOptions lockOptions) {

    }

    /**
     * @param s
     * @param o
     * @param lockOptions
     * @deprecated
     */
    @Override
    public void refresh(String s, Object o, LockOptions lockOptions) {

    }

    /**
     * @param o
     */
    @Override
    public void remove(Object o) {

    }

    /**
     * @param aClass
     * @param o
     * @param <T>
     * @return
     */
    @Override
    public <T> T find(Class<T> aClass, Object o) {
        return null;
    }

    /**
     * @param aClass
     * @param o
     * @param map
     * @param <T>
     * @return
     */
    @Override
    public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
        return null;
    }

    /**
     * @param aClass
     * @param o
     * @param lockModeType
     * @param <T>
     * @return
     */
    @Override
    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
        return null;
    }

    /**
     * @param aClass
     * @param o
     * @param lockModeType
     * @param map
     * @param <T>
     * @return
     */
    @Override
    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
        return null;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public LockMode getCurrentLockMode(Object o) {
        return null;
    }

    /**
     *
     */
    @Override
    public void clear() {

    }

    /**
     * @param aClass
     * @param o
     * @param <T>
     * @return
     */
    @Override
    public <T> T get(Class<T> aClass, Object o) {
        return null;
    }

    /**
     * @param aClass
     * @param o
     * @param lockMode
     * @param <T>
     * @return
     */
    @Override
    public <T> T get(Class<T> aClass, Object o, LockMode lockMode) {
        return null;
    }

    /**
     * @param aClass
     * @param o
     * @param lockOptions
     * @param <T>
     * @return
     */
    @Override
    public <T> T get(Class<T> aClass, Object o, LockOptions lockOptions) {
        return null;
    }

    /**
     * @param s
     * @param o
     * @return
     */
    @Override
    public Object get(String s, Object o) {
        return null;
    }

    /**
     * @param s
     * @param o
     * @param lockMode
     * @return
     */
    @Override
    public Object get(String s, Object o, LockMode lockMode) {
        return null;
    }

    /**
     * @param s
     * @param o
     * @param lockOptions
     * @return
     */
    @Override
    public Object get(String s, Object o, LockOptions lockOptions) {
        return null;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public String getEntityName(Object o) {
        return "";
    }

    /**
     * @param aClass
     * @param o
     * @param <T>
     * @return
     */
    @Override
    public <T> T getReference(Class<T> aClass, Object o) {
        return null;
    }

    /**
     * @param s
     * @param o
     * @return
     */
    @Override
    public Object getReference(String s, Object o) {
        return null;
    }

    /**
     * @param t
     * @param <T>
     * @return
     */
    @Override
    public <T> T getReference(T t) {
        return null;
    }

    /**
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> IdentifierLoadAccess<T> byId(Class<T> aClass) {
        return null;
    }

    /**
     * @param s
     * @param <T>
     * @return
     */
    @Override
    public <T> IdentifierLoadAccess<T> byId(String s) {
        return null;
    }

    /**
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> MultiIdentifierLoadAccess<T> byMultipleIds(Class<T> aClass) {
        return null;
    }

    /**
     * @param s
     * @param <T>
     * @return
     */
    @Override
    public <T> MultiIdentifierLoadAccess<T> byMultipleIds(String s) {
        return null;
    }

    /**
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> NaturalIdLoadAccess<T> byNaturalId(Class<T> aClass) {
        return null;
    }

    /**
     * @param s
     * @param <T>
     * @return
     */
    @Override
    public <T> NaturalIdLoadAccess<T> byNaturalId(String s) {
        return null;
    }

    /**
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> SimpleNaturalIdLoadAccess<T> bySimpleNaturalId(Class<T> aClass) {
        return null;
    }

    /**
     * @param s
     * @param <T>
     * @return
     */
    @Override
    public <T> SimpleNaturalIdLoadAccess<T> bySimpleNaturalId(String s) {
        return null;
    }

    /**
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> NaturalIdMultiLoadAccess<T> byMultipleNaturalId(Class<T> aClass) {
        return null;
    }

    /**
     * @param s
     * @param <T>
     * @return
     */
    @Override
    public <T> NaturalIdMultiLoadAccess<T> byMultipleNaturalId(String s) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Filter enableFilter(String s) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Filter getEnabledFilter(String s) {
        return null;
    }

    /**
     * @param s
     */
    @Override
    public void disableFilter(String s) {

    }

    /**
     * @return
     */
    @Override
    public SessionFactory getFactory() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public SessionStatistics getStatistics() {
        return null;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public boolean isReadOnly(Object o) {
        return false;
    }

    /**
     * @param o
     * @param b
     */
    @Override
    public void setReadOnly(Object o, boolean b) {

    }

    /**
     * @param s
     * @return
     * @throws UnknownProfileException
     */
    @Override
    public boolean isFetchProfileEnabled(String s) throws UnknownProfileException {
        return false;
    }

    /**
     * @param s
     * @throws UnknownProfileException
     */
    @Override
    public void enableFetchProfile(String s) throws UnknownProfileException {

    }

    /**
     * @param s
     * @throws UnknownProfileException
     */
    @Override
    public void disableFetchProfile(String s) throws UnknownProfileException {

    }

    /**
     * @return
     */
    @Override
    public LobHelper getLobHelper() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public SharedSessionBuilder sessionWithOptions() {
        return null;
    }

    /**
     * @param sessionEventListeners
     */
    @Override
    public void addEventListeners(SessionEventListener... sessionEventListeners) {

    }

    /**
     * @return
     */
    @Override
    public String getTenantIdentifier() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public Object getTenantIdentifierValue() {
        return null;
    }

    /**
     *
     */
    @Override
    public void close() {

    }

    /**
     * @return
     */
    @Override
    public boolean isOpen() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean isConnected() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public Transaction beginTransaction() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Transaction getTransaction() {
        return Transaction1;
    }

    /**
     *
     */
    @Override
    public void joinTransaction() {

    }

    /**
     * @return
     */
    @Override
    public boolean isJoinedToTransaction() {
        return false;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public ProcedureCall getNamedProcedureCall(String s) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public ProcedureCall createStoredProcedureCall(String s) {
        return null;
    }

    /**
     * @param s
     * @param classes
     * @return
     */
    @Override
    public ProcedureCall createStoredProcedureCall(String s, Class<?>... classes) {
        return null;
    }

    /**
     * @param s
     * @param strings
     * @return
     */
    @Override
    public ProcedureCall createStoredProcedureCall(String s, String... strings) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public ProcedureCall createNamedStoredProcedureQuery(String s) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public ProcedureCall createStoredProcedureQuery(String s) {
        return null;
    }

    /**
     * @param s
     * @param strings
     * @return
     */
    @Override
    public ProcedureCall createStoredProcedureQuery(String s, String... strings) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Integer getJdbcBatchSize() {
        return 0;
    }

    /**
     * @param integer
     */
    @Override
    public void setJdbcBatchSize(Integer integer) {

    }

    /**
     * @return
     */
    @Override
    public HibernateCriteriaBuilder getCriteriaBuilder() {
        return null;
    }

    /**
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> RootGraph<T> createEntityGraph(Class<T> aClass) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public RootGraph<?> createEntityGraph(String s) {
        return null;
    }

    /**
     * @param aClass
     * @param s
     * @param <T>
     * @return
     */
    @Override
    public <T> RootGraph<T> createEntityGraph(Class<T> aClass, String s) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public RootGraph<?> getEntityGraph(String s) {
        return null;
    }

    /**
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> aClass) {
        return List.of();
    }

    /**
     * @param s
     * @param aClass
     * @param <R>
     * @return
     */
    @Override
    public <R> Query<R> createQuery(String s, Class<R> aClass) {
        return null;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public Query createQuery(String s) {
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @param <R>
     * @return
     */
    @Override
    public <R> Query<R> createNamedQuery(String s, Class<R> aClass) {
        return null;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public SelectionQuery<?> createNamedSelectionQuery(String s) {
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @param <R>
     * @return
     */
    @Override
    public <R> SelectionQuery<R> createNamedSelectionQuery(String s, Class<R> aClass) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public MutationQuery createNamedMutationQuery(String s) {
        return null;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public Query getNamedQuery(String s) {
        return null;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public NativeQuery getNamedNativeQuery(String s) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public NativeQuery getNamedNativeQuery(String s, String s1) {
        return null;
    }

    /**

     * @return
     */











    /**
     * @param s
     * @param classes
     * @return
     */

    public ProcedureCall createStoredProcedureQuery(String s, Class... classes) {
        return null;
    }

    /**
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Object getDelegate() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Metamodel getMetamodel() {
        return null;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public Query createNamedQuery(String s) {
        return null;
    }

    /**
     * @param criteriaQuery
     * @param <R>
     * @return
     */
    @Override
    public <R> Query<R> createQuery(CriteriaQuery<R> criteriaQuery) {
        return null;
    }

    /**
     * @param criteriaDelete
     * @deprecated
     */
    @Override
    public Query createQuery(CriteriaDelete criteriaDelete) {
        return null;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public NativeQuery createNativeQuery(String s) {
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @param s1
     * @param <R>
     * @return
     */
    @Override
    public <R> NativeQuery<R> createNativeQuery(String s, Class<R> aClass, String s1) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public NativeQuery createNativeQuery(String s, String s1) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param aClass
     * @param <R>
     * @return
     */
    @Override
    public <R> NativeQuery<R> createNativeQuery(String s, String s1, Class<R> aClass) {
        return null;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public SelectionQuery<?> createSelectionQuery(String s) {
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @param <R>
     * @return
     */
    @Override
    public <R> SelectionQuery<R> createSelectionQuery(String s, Class<R> aClass) {
        return null;
    }

    /**
     * @param criteriaQuery
     * @param <R>
     * @return
     */
    @Override
    public <R> SelectionQuery<R> createSelectionQuery(CriteriaQuery<R> criteriaQuery) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public MutationQuery createMutationQuery(String s) {
        return null;
    }

    /**
     * @param criteriaUpdate
     * @return
     */
    @Override
    public MutationQuery createMutationQuery(CriteriaUpdate criteriaUpdate) {
        return null;
    }

    /**
     * @param criteriaDelete
     * @return
     */
    @Override
    public MutationQuery createMutationQuery(CriteriaDelete criteriaDelete) {
        return null;
    }

    /**
     * @param jpaCriteriaInsertSelect
     * @return
     */
    @Override
    public MutationQuery createMutationQuery(JpaCriteriaInsertSelect jpaCriteriaInsertSelect) {
        return null;
    }

    /**
     * @param jpaCriteriaInsert
     * @return
     */
    @Override
    public MutationQuery createMutationQuery(JpaCriteriaInsert jpaCriteriaInsert) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public MutationQuery createNativeMutationQuery(String s) {
        return null;
    }

    /**
     * @param criteriaUpdate
     * @deprecated
     */
    @Override
    public Query createQuery(CriteriaUpdate criteriaUpdate) {
        return null;
    }
}
