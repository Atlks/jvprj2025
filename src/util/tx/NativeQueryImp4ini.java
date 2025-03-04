package util.tx;

import jakarta.persistence.*;
import jakarta.persistence.metamodel.SingularAttribute;
import org.hibernate.*;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.metamodel.model.domain.BasicDomainType;
import org.hibernate.query.*;
import org.hibernate.query.Query;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.BasicTypeReference;

import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

import static test.QryLstByHbntTest.getOrderbyFromSql;
import static util.ArrUtil.getEndidx;
import static util.OrdUtil.delOrderby;
import static util.OrdUtil.orderBySqlOrderMode;
import static util.tx.Qry.*;
import static util.dbutil.*;
import static util.util2026.getSqlPrmVal;

public class NativeQueryImp4ini<T> implements NativeQuery {
    public String saveUrl;
    public Class<T> aClass;
    private String sql = "";
    private int offset=0;
    private int pagesize=-1;
    private ArrayList<Object> resultList;
    private int resultListCount;

    public NativeQueryImp4ini(String sql, Class aClass) {
        this.sql = sql;
    }

    /**
     * @param s
     * @param o
     * @return
     */
    @Override
    public NativeQuery setParameter(String s, Object o) {
        var sqlPrmVal = getSqlPrmVal(o);
        this.sql = this.sql.replaceAll(":" + s, sqlPrmVal);
        return this;
    }

    /**
     * @return
     */
    @Override
    public long getResultCount() {
        return resultListCount;
    }

    /**startPosition   offset
     * @return
     */
    @Override
    public NativeQuery setFirstResult(int offset) {
        this.offset = offset;
        return this;
    }

    /**
     * @return
     */
    @Override
    public NativeQuery setMaxResults(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }


    /**
     * @return
     */
    @Override
    public List getResultList() {
this.getResultCount();
        this.sql = delOrderby(sql);
        var spel = convertSqlToSpEL(this.sql);
        System.out.println("spel =" + spel);
        var colName = saveUrl + aClass.getName();
        List<T> list1 = null;
        try {
            list1 = findObjsAllIni(colName, this.aClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<?> fltedLst = filterWithSpEL(list1, spel);

         this.resultListCount=fltedLst.size();

        if(pagesize>0) {
            int fromIndex = offset;
            if (fromIndex + 1 > fltedLst.size()) {
                //ret
                this.resultList= new ArrayList<>();
                return new ArrayList<>();
                // return new PageResult<>(new ArrayList<>(), totalRecords, totalPages);
            }
        }

        List<?> lstOrded = orderBySqlOrderMode(fltedLst, getOrderbyFromSql(sql));


        if(pagesize==-1)
            return  lstOrded;
    //    subList2025
        if(pagesize>0)
        {
            int fromIndex = offset;
            if (fromIndex + 1 > list1.size()) {
                //ret
                return  new ArrayList<>();
                // return new PageResult<>(new ArrayList<>(), totalRecords, totalPages);
            }

            //   List<T> listPageed=new ArrayList<>();

            //last page

            int sizeList = list1.size();
            int endidx = getEndidx(pagesize, fromIndex, sizeList);
            return lstOrded.subList(fromIndex,endidx);
        }


        return List.of();
    }


    /**
     * @param s
     * @return
     */
    @Override
    public NativeQuery addScalar(String s) {
        return null;
    }

    /**
     * @param s
     * @param basicTypeReference
     * @return
     */
    @Override
    public NativeQuery addScalar(String s, BasicTypeReference basicTypeReference) {
        return null;
    }

    /**
     * @param s
     * @param basicDomainType
     * @return
     */
    @Override
    public NativeQuery addScalar(String s, BasicDomainType basicDomainType) {
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @return
     */
    @Override
    public NativeQuery addScalar(String s, Class aClass) {
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @param s1
     * @return
     */
    @Override
    public NativeQuery addAttributeResult(String s, Class aClass, String s1) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @return
     */
    @Override
    public NativeQuery addAttributeResult(String s, String s1, String s2) {
        return null;
    }

    /**
     * @param s
     * @param singularAttribute
     * @return
     */
    @Override
    public NativeQuery addAttributeResult(String s, SingularAttribute singularAttribute) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @return
     */
    @Override
    public RootReturn addRoot(String s, String s1) {
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @return
     */
    @Override
    public RootReturn addRoot(String s, Class aClass) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public NativeQuery addEntity(String s) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @return
     */
    @Override
    public NativeQuery addEntity(String s, String s1) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param lockMode
     * @return
     */
    @Override
    public NativeQuery addEntity(String s, String s1, LockMode lockMode) {
        return null;
    }

    /**
     * @param aClass
     * @return
     */
    @Override
    public NativeQuery addEntity(Class aClass) {
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @return
     */
    @Override
    public NativeQuery addEntity(String s, Class aClass) {
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @param lockMode
     * @return
     */
    @Override
    public NativeQuery addEntity(String s, Class aClass, LockMode lockMode) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @return
     */
    @Override
    public FetchReturn addFetch(String s, String s1, String s2) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @return
     */
    @Override
    public NativeQuery addJoin(String s, String s1) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @return
     */
    @Override
    public NativeQuery addJoin(String s, String s1, String s2) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param lockMode
     * @return
     */
    @Override
    public NativeQuery addJoin(String s, String s1, LockMode lockMode) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Collection<String> getSynchronizedQuerySpaces() {
        return List.of();
    }

    /**
     * @param s
     * @return
     */
    @Override
    public NativeQuery addSynchronizedQuerySpace(String s) {
        return null;
    }

    /**
     * @param s
     * @return
     * @throws MappingException
     */
    @Override
    public NativeQuery addSynchronizedEntityName(String s) throws MappingException {
        return null;
    }

    /**
     * @param aClass
     * @return
     * @throws MappingException
     */
    @Override
    public NativeQuery addSynchronizedEntityClass(Class aClass) throws MappingException {
        return null;
    }

    /**
     * @param flushMode
     * @return
     */
    @Override
    public NativeQuery setHibernateFlushMode(FlushMode flushMode) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Integer getTimeout() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public FlushModeType getFlushMode() {
        return null;
    }

    /**
     * @param flushModeType
     * @return
     */
    @Override
    public NativeQuery setFlushMode(FlushModeType flushModeType) {
        return null;
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
     * @return
     */
    @Override
    public NativeQuery setCacheMode(CacheMode cacheMode) {
        return null;
    }

    /**
     * @param cacheStoreMode
     * @return
     */
    @Override
    public NativeQuery setCacheStoreMode(CacheStoreMode cacheStoreMode) {
        return null;
    }

    /**
     * @param cacheRetrieveMode
     * @return
     */
    @Override
    public NativeQuery setCacheRetrieveMode(CacheRetrieveMode cacheRetrieveMode) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isCacheable() {
        return false;
    }

    /**
     * @param b
     * @return
     */
    @Override
    public NativeQuery setCacheable(boolean b) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isQueryPlanCacheable() {
        return false;
    }

    /**
     * @param b
     * @return
     */
    @Override
    public SelectionQuery setQueryPlanCacheable(boolean b) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String getCacheRegion() {
        return "";
    }

    /**
     * @param s
     * @return
     */
    @Override
    public NativeQuery setCacheRegion(String s) {
        return null;
    }

    /**
     * @param i
     * @return
     */
    @Override
    public NativeQuery setTimeout(int i) {
        return null;
    }

    /**
     * @param i
     * @return
     */
    @Override
    public NativeQuery setFetchSize(int i) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isReadOnly() {
        return false;
    }

    /**
     * @param b
     * @return
     */
    @Override
    public NativeQuery setReadOnly(boolean b) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public int getMaxResults() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public LockOptions getLockOptions() {
        return null;
    }

    /**
     * @param lockOptions
     * @return
     */
    @Override
    public NativeQuery setLockOptions(LockOptions lockOptions) {
        return null;
    }

    /**
     * @param s
     * @param lockMode
     * @return
     */
    @Override
    public NativeQuery setLockMode(String s, LockMode lockMode) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public List list() {
        return List.of();
    }


    /**
     * @return
     */
    @Override
    public ScrollableResults scroll() {
        return null;
    }

    /**
     * @param scrollMode
     * @return
     */
    @Override
    public ScrollableResults scroll(ScrollMode scrollMode) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Stream getResultStream() {
        return NativeQuery.super.getResultStream();
    }

    /**
     * @return
     */
    @Override
    public Stream stream() {
        return NativeQuery.super.stream();
    }

    /**
     * @return
     */
    @Override
    public Object uniqueResult() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Object getSingleResult() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Object getSingleResultOrNull() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Optional uniqueResultOptional() {
        return Optional.empty();
    }

    /**
     * @param keyedPage
     * @return
     */
    @Override
    public KeyedResultList getKeyedResultList(KeyedPage keyedPage) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public int executeUpdate() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public SharedSessionContract getSession() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String getQueryString() {
        return "";
    }

    /**
     * @param rootGraph
     * @param graphSemantic
     * @return
     */
    @Override
    public Query applyGraph(RootGraph rootGraph, GraphSemantic graphSemantic) {
        return null;
    }

    /**
     * @param graph
     * @return
     */
    @Override
    public Query applyFetchGraph(RootGraph graph) {
        return NativeQuery.super.applyFetchGraph(graph);
    }

    /**
     * @param graph
     * @return
     */
    @Override
    public Query applyLoadGraph(RootGraph graph) {
        return NativeQuery.super.applyLoadGraph(graph);
    }

    /**
     * @return
     */
    @Override
    public String getComment() {
        return "";
    }

    /**
     * @param s
     * @return
     */
    @Override
    public NativeQuery setComment(String s) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Integer getFetchSize() {
        return 0;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public NativeQuery addQueryHint(String s) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public int getFirstResult() {
        return 0;
    }


    /**
     * @param page
     * @return
     */
    @Override
    public Query setPage(Page page) {
        return NativeQuery.super.setPage(page);
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
     * @param s
     * @param o
     * @return
     */
    @Override
    public NativeQuery setHint(String s, Object o) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Map<String, Object> getHints() {
        return Map.of();
    }

    /**
     * @param entityGraph
     * @param graphSemantic
     * @return
     */
    @Override
    public Query setEntityGraph(EntityGraph entityGraph, GraphSemantic graphSemantic) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Query enableFetchProfile(String s) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Query disableFetchProfile(String s) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public LockModeType getLockMode() {
        return null;
    }

    /**
     * @param aClass
     * @return
     */
    @Override
    public T unwrap(Class aClass) {
        return null;
    }


    /**
     * @return
     */
    @Override
    public LockMode getHibernateLockMode() {
        return null;
    }

    /**
     * @param lockModeType
     * @return
     */
    @Override
    public NativeQuery setLockMode(LockModeType lockModeType) {
        return null;
    }

    /**
     * @param order
     * @return
     */
    @Override
    public Query setOrder(Order order) {
        return null;
    }

    /**
     * @param s
     * @param lockMode
     * @deprecated
     */
    @Override
    public @Remove SelectionQuery setAliasSpecificLockMode(String s, LockMode lockMode) {
        return null;
    }

    /**
     * @param b
     * @return
     */
    @Override
    public SelectionQuery setFollowOnLocking(boolean b) {
        return null;
    }

    /**
     * @param list
     * @return
     */
    @Override
    public Query setOrder(List list) {
        return null;
    }

    /**
     * @param lockMode
     * @return
     */
    @Override
    public NativeQuery setHibernateLockMode(LockMode lockMode) {
        return null;
    }

    /**
     * @param resultListTransformer
     * @return
     */
    @Override
    public NativeQuery setResultListTransformer(ResultListTransformer resultListTransformer) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public QueryOptions getQueryOptions() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public ParameterMetadata getParameterMetadata() {
        return null;
    }


    /**
     * @param s
     * @param instant
     * @param temporalType
     * @return
     */
    @Override
    public NativeQuery setParameter(String s, Instant instant, TemporalType temporalType) {
        return null;
    }

    /**
     * @param s
     * @param calendar
     * @param temporalType
     * @return
     */
    @Override
    public NativeQuery setParameter(String s, Calendar calendar, TemporalType temporalType) {
        return null;
    }

    /**
     * @param s
     * @param date
     * @param temporalType
     * @return
     */
    @Override
    public NativeQuery setParameter(String s, Date date, TemporalType temporalType) {
        return null;
    }

    /**
     * @param i
     * @param o
     * @return
     */
    @Override
    public NativeQuery setParameter(int i, Object o) {
        return null;
    }

    /**
     * @param i
     * @param instant
     * @param temporalType
     * @return
     */
    @Override
    public NativeQuery setParameter(int i, Instant instant, TemporalType temporalType) {
        return null;
    }

    /**
     * @param i
     * @param calendar
     * @param temporalType
     * @return
     */
    @Override
    public NativeQuery setParameter(int i, Calendar calendar, TemporalType temporalType) {
        return null;
    }

    /**
     * @param i
     * @param date
     * @param temporalType
     * @return
     */
    @Override
    public NativeQuery setParameter(int i, Date date, TemporalType temporalType) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Set<Parameter<?>> getParameters() {
        return Set.of();
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Parameter<?> getParameter(String s) {
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @return
     */
    @Override
    public Parameter<T> getParameter(String s, Class aClass) {
        return null;
    }

    /**
     * @param i
     * @return
     */
    @Override
    public Parameter<?> getParameter(int i) {
        return null;
    }

    /**
     * @param i
     * @param aClass
     * @return
     */

    public Parameter<T> getParameter(int i, Class aClass) {
        return null;
    }

    /**
     * @param parameter
     * @return
     */

    public boolean isBound(Parameter<?> parameter) {
        return false;
    }

    /**
     * @param parameter
     * @return
     */
    @Override
    public T getParameterValue(Parameter parameter) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Object getParameterValue(String s) {
        return null;
    }

    /**
     * @param i
     * @return
     */
    @Override
    public Object getParameterValue(int i) {
        return null;
    }


    /**
     * @param s
     * @param collection
     * @return
     */
    @Override
    public NativeQuery setParameterList(String s, Collection collection) {
        return null;
    }

    /**
     * @param s
     * @param objects
     * @return
     */
    @Override
    public NativeQuery setParameterList(String s, Object[] objects) {
        return null;
    }

    /**
     * @param i
     * @param collection
     * @return
     */
    @Override
    public NativeQuery setParameterList(int i, Collection collection) {
        return null;
    }

    /**
     * @param i
     * @param objects
     * @return
     */
    @Override
    public NativeQuery setParameterList(int i, Object[] objects) {
        return null;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public NativeQuery setProperties(Object o) {
        return null;
    }

    /**
     * @param map
     * @return
     */
    @Override
    public NativeQuery setProperties(Map map) {
        return null;
    }

    /**
     * @param queryParameter
     * @param objects
     * @param bindableType
     * @return
     */
    @Override
    public NativeQuery setParameterList(QueryParameter queryParameter, Object[] objects, BindableType bindableType) {
        return null;
    }

    /**
     * @param queryParameter
     * @param objects
     * @param aClass
     * @return
     */
    @Override
    public NativeQuery setParameterList(QueryParameter queryParameter, Object[] objects, Class aClass) {
        return null;
    }

    /**
     * @param queryParameter
     * @param objects
     * @return
     */
    @Override
    public NativeQuery setParameterList(QueryParameter queryParameter, Object[] objects) {
        return null;
    }

    /**
     * @param queryParameter
     * @param collection
     * @param bindableType
     * @return
     */
    @Override
    public NativeQuery setParameterList(QueryParameter queryParameter, Collection collection, BindableType bindableType) {
        return null;
    }

    /**
     * @param queryParameter
     * @param collection
     * @param aClass
     * @return
     */
    @Override
    public NativeQuery setParameterList(QueryParameter queryParameter, Collection collection, Class aClass) {
        return null;
    }

    /**
     * @param queryParameter
     * @param collection
     * @return
     */
    @Override
    public NativeQuery setParameterList(QueryParameter queryParameter, Collection collection) {
        return null;
    }

    /**
     * @param i
     * @param objects
     * @param bindableType
     * @return
     */
    @Override
    public NativeQuery setParameterList(int i, Object[] objects, BindableType bindableType) {
        return null;
    }

    /**
     * @param i
     * @param objects
     * @param aClass
     * @return
     */
    @Override
    public NativeQuery setParameterList(int i, Object[] objects, Class aClass) {
        return null;
    }

    /**
     * @param i
     * @param collection
     * @param bindableType
     * @return
     */
    @Override
    public NativeQuery setParameterList(int i, Collection collection, BindableType bindableType) {
        return null;
    }

    /**
     * @param i
     * @param collection
     * @param aClass
     * @return
     */
    @Override
    public NativeQuery setParameterList(int i, Collection collection, Class aClass) {
        return null;
    }

    /**
     * @param s
     * @param objects
     * @param bindableType
     * @return
     */
    @Override
    public NativeQuery setParameterList(String s, Object[] objects, BindableType bindableType) {
        return null;
    }

    /**
     * @param s
     * @param objects
     * @param aClass
     * @return
     */
    @Override
    public NativeQuery setParameterList(String s, Object[] objects, Class aClass) {
        return null;
    }

    /**
     * @param s
     * @param collection
     * @param bindableType
     * @return
     */
    @Override
    public NativeQuery setParameterList(String s, Collection collection, BindableType bindableType) {
        return null;
    }

    /**
     * @param s
     * @param collection
     * @param aClass
     * @return
     */
    @Override
    public NativeQuery setParameterList(String s, Collection collection, Class aClass) {
        return null;
    }

    /**
     * @param parameter
     * @param date
     * @param temporalType
     * @return
     */
    @Override
    public NativeQuery setParameter(Parameter parameter, Date date, TemporalType temporalType) {
        return null;
    }

    /**
     * @param parameter
     * @param calendar
     * @param temporalType
     * @return
     */
    @Override
    public NativeQuery setParameter(Parameter parameter, Calendar calendar, TemporalType temporalType) {
        return null;
    }

    /**
     * @param parameter
     * @param o
     * @return
     */
    @Override
    public NativeQuery setParameter(Parameter parameter, Object o) {
        return null;
    }

    /**
     * @param queryParameter
     * @param o
     * @param bindableType
     * @return
     */
    @Override
    public NativeQuery setParameter(QueryParameter queryParameter, Object o, BindableType bindableType) {
        return null;
    }

    /**
     * @param queryParameter
     * @param o
     * @param aClass
     * @return
     */
    @Override
    public NativeQuery setParameter(QueryParameter queryParameter, Object o, Class aClass) {
        return null;
    }

    /**
     * @param queryParameter
     * @param o
     * @return
     */
    @Override
    public NativeQuery setParameter(QueryParameter queryParameter, Object o) {
        return null;
    }

    /**
     * @param i
     * @param o
     * @param bindableType
     * @return
     */
    @Override
    public NativeQuery setParameter(int i, Object o, BindableType bindableType) {
        return null;
    }

    /**
     * @param i
     * @param o
     * @param aClass
     * @return
     */
    @Override
    public NativeQuery setParameter(int i, Object o, Class aClass) {
        return null;
    }

    /**
     * @param s
     * @param o
     * @param bindableType
     * @return
     */
    @Override
    public NativeQuery setParameter(String s, Object o, BindableType bindableType) {
        return null;
    }

    /**
     * @param s
     * @param o
     * @param aClass
     * @return
     */
    @Override
    public NativeQuery setParameter(String s, Object o, Class aClass) {
        return null;
    }

    /**
     * @param resultTransformer
     * @deprecated
     */
    @Override
    public NativeQuery setResultTransformer(ResultTransformer resultTransformer) {
        return null;
    }

    /**
     * @param tupleTransformer
     * @return
     */
    @Override
    public NativeQuery setTupleTransformer(TupleTransformer tupleTransformer) {
        return null;
    }

    /**
     * @param aClass
     * @return
     */
    @Override
    public InstantiationResultNode addInstantiation(Class aClass) {
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @param aClass1
     * @param aClass2
     * @return
     */
    @Override
    public NativeQuery addScalar(String s, Class aClass, Class aClass1, Class aClass2) {
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @param aClass1
     * @return
     */
    @Override
    public NativeQuery addScalar(String s, Class aClass, Class aClass1) {
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @param aClass1
     * @param attributeConverter
     * @return
     */
    @Override
    public NativeQuery addScalar(String s, Class aClass, Class aClass1, AttributeConverter attributeConverter) {
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @param attributeConverter
     * @return
     */
    @Override
    public NativeQuery addScalar(String s, Class aClass, AttributeConverter attributeConverter) {
        return null;
    }
}
