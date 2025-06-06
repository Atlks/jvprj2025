package util.tx;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import model.admin.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import util.model.CrudRzt;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static util.algo.IsXXX.ifIsBlank;
import static util.algo.JarClassScanner.getClassesList;
import static util.log.ColorLogger.*;
//import static cfg.IocPicoCfg.scanClasses;
import static util.oo.StrUtil.getPwdFromJdbcurl;
import static util.oo.StrUtil.getUnameFromJdbcurl;
import static util.misc.Util2025.*;
import static util.tx.dbutil.*;

//ormUtilHbnt
public class HbntUtil {
    public static String lastMsg="";
    public static ThreadLocal<CrudRzt> crudRztThreadLocal=new ThreadLocal<>();
    public static void deletex(Class<?> aClass, String objid) {
        Session session=sessionFactory.getCurrentSession();


        Object user = session.get(aClass, objid); // 根据主键获取实体
        if (user != null) {
            session.delete(user); // 删除实体
           // return 1;
            lastMsg="dlt 1";
            crudRztThreadLocal.set(new CrudRzt(1));
        }else
        {
            lastMsg="dlt 0";
            crudRztThreadLocal.set(new CrudRzt(0));
        }
          //  return 0;
    }
    public static Session openSession(String jdbcUrl, List<Class> li) throws SQLException {
        String mthClr = colorStr("openSession", YELLOW_bright);
        String urlClr = colorStr(jdbcUrl, GREEN);
        String liClr = colorStr(encodeJsonObj(li), GREEN);
        System.out.println("▶\uFE0F fun " + mthClr + "(url=" + urlClr + ",listClass=" + liClr);


        SessionFactory sessionFactory = getSessionFactory(jdbcUrl, li);

        // 获取 Session
        Session session = sessionFactory.openSession();
        System.out.println("✅endfun openSession()");
        return session;


        // Create the Configuration object
//        Configuration configuration = new Configuration();
//
//        // Set Hibernate properties programmatically
//        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
//        configuration.setProperty("hibernate.hbm2ddl.auto", "update"); // Or "create", "validate"
//        configuration.setProperty("hibernate.show_sql", "true");
//        configuration.setProperty("hibernate.format_sql", "true");
//        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
//        configuration.setProperty("hibernate.connection.url", saveDirUsrs);
////        configuration.setProperty("hibernate.connection.username",  getU);
//        configuration.setProperty("hibernate.connection.password", "your_password");
//        configuration.setProperty("hibernate.c3p0.min_size", "5");
//        configuration.setProperty("hibernate.c3p0.max_size", "20");


        //  session.beginTransaction();

        // 示例操作

        //  session.save(obj);

//        session.getTransaction().commit();
//        session.close();
//
//        // 关闭 SessionFactory
//        sessionFactory.close();

        // Add annotated classes (your entities)
        //  configuration.addAnnotatedClass(User.class); // Replace User.class with your entity class

        // Build the ServiceRegistry from the Configuration
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .applySettings(configuration.getProperties())
//                .build();

        // Build the SessionFactory
//        sessionFactory = configuration.buildSessionFactory(serviceRegistry);

    }

    public static SessionFactory getSessionFactory(String jdbcUrl, List<Class> li) throws SQLException {

        System.out.println("fun get sess fatcy(jdbcurl=" + jdbcUrl + ",listClass=" + encodeJsonObj(li) + "");
        if (jdbcUrl.startsWith("jdbc:mysql")) {
            var db = getDatabaseFileName4mysql(jdbcUrl);
            crtDatabase(jdbcUrl, db);
        }


        // Hibernate 配置属性
        Properties properties = new Properties();
        String dvr = getDvr(jdbcUrl);
        System.out.println("dvr188=" + dvr);
        properties.put(Environment.DRIVER, dvr);
        properties.put(Environment.URL, "" + jdbcUrl);
        properties.put(Environment.USER, getUnameFromJdbcurl(jdbcUrl));
        properties.put(Environment.PASS, getPwdFromJdbcurl(jdbcUrl));
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

        if (isSqliteJdbcUrl(jdbcUrl)) {
            properties.put(Environment.DIALECT, "org.hibernate.dialect.SQLiteDialect");

        } else if (jdbcUrl.startsWith("jdbc:mysql")) {
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        }
        else if (jdbcUrl.startsWith("jdbc:p6spy:mysql")) {
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        }
        else if (jdbcUrl.startsWith("jdbc:h2")) {
            // H2Dialect
            properties.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
        }

        //    hibernate.dialect.storage_engine
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.FORMAT_SQL, "true");
        properties.put(Environment.STORAGE_ENGINE, "innodb");
        //HBM2DDL_CHARSET_NAME
        properties.put(Environment.HBM2DDL_AUTO, "update"); // 自动建表
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

        properties.put(Environment.UNIQUE_CONSTRAINT_SCHEMA_UPDATE_STRATEGY, "skip");
        properties.put("hibernate.schema_update.unique_constraint_strateg", "skip");
       // y=
        // 禁用插入和更新的顺序控制（按实际需要设置）
        properties.put(Environment.ORDER_INSERTS, "false");
        properties.put(Environment.ORDER_UPDATES, "false");

        // 设置 Hibernate 命名策略为物理命名策略
        properties.put("hibernate.physical_naming_strategy",
                CamelCaseToUnderscoresNamingStrategy.class.getName());
        System.out.println(org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl.class);
        // spring.jpa.hibernate.naming.physical-strategy=

        //-------统计查询cache的使用
        //   properties.put(Environment.USE_SECOND_LEVEL_CACHE, true);

        //   properties.put(Environment.USE_QUERY_CACHE, true);

        // 二级缓存

        // properties.put(AvailableSettings.CACHE_REGION_FACTORY, InfinispanRegionFactory.class.getName());

        // 不使用 infinispan.xml，而是使用默认或 programmatic 配置
        // 可以通过 Infinispan 自定义 CacheManager（下面是默认的）


        System.out.println("id13361 "+encodeJson(properties));
        // 创建 ServiceRegistry
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(properties)
                .build();

        // 添加实体类映射
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        for (Class cls : li) {
            metadataSources.addAnnotatedClasses(cls);
        }
        addAnnotatedClasses2025(metadataSources);

        // 创建 SessionFactory
        sessionFactory = metadataSources.buildMetadata().buildSessionFactory();

        return sessionFactory;
    }

    private static boolean isSqliteJdbcUrl(String jdbcUrl) {
        return jdbcUrl.startsWith("jdbc:sqlite:");
    }

    private static void addAnnotatedClasses2025(MetadataSources metadataSources) {


        // 递归扫描 .class 文件
        List<Class<?>> classList = getClassesList();
        //   scanClasses(classDir, classDir.getAbsolutePath(), classList);

        // 注册到 hbnt
        for (Class<?> clazz : classList) {
            try {
                if (clazz.getName().startsWith("entityx") || clazz.getName().startsWith("model.")) {

                    if (clazz.isAnnotationPresent(Entity.class)) {
                        metadataSources.addAnnotatedClasses(clazz);
                        System.out.println("hbnt已注册hbnt: " + clazz.getName());
                    }

                }

            } catch (Exception e) {
                System.err.println("hbnt注册失败: " + clazz.getName());
                System.err.println("hbnt注册失败msg: " + e.getMessage());
                //  System.err.println("注册失败: " + clazz.getName());
            }
        }

    }


//        // 获取 classes 目录
//        String classpath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
//        File classDir = new File(classpath);
//        if (!classDir.exists() || !classDir.isDirectory()) {
//            System.err.println("classes 目录不存在！");
//            return;
//        }


    //clazz是否包含某个注解AnnoClass
//    private static boolean isHavAnno(Class<?> clazz, Class<?> AnnoClass) {
//    }

    public static Object persist(Object var1) {
        return persist(var1, sessionFactory.getCurrentSession());
    }

    // @log
    public static Object persist(Object var1, Session session) {

        String mth = colorStr("persistByHbnt", YELLOW_bright);
        String prmurl = colorStr(encodeJsonObj(var1), GREEN);
        System.out.println("\r\n▶\uFE0Ffun " + mth + "(o=" + prmurl);

        System.out.println("persistByHbnt(" + var1.getClass().getName());
        session.persist(var1);
        session.flush();

        System.out.println("✅endfun persistByHbnt()");
        return var1;
    }


    //alias  of get rzt lst
    public static @NotNull List getListBySql(@NotBlank String sql, @NotNull Session session) throws Throwable {

        return getResultList(sql, session);
    }


    public static @NotNull List<?> getResultList(@NotBlank String sql, @NotNull Session session) throws Throwable {

        ifIsBlank(sql);
        String mthClr = colorStr("getListBySql", YELLOW_bright);
        System.out.println("\r\n▶\uFE0Ffun " + mthClr + "(" + (sql));
        NativeQuery nativeQuery = session.createNativeQuery(sql, Map.class);
        // setPrmts4sql(sqlprmMap, nativeQuery);

        //       .setParameter("age", 18);
        @NotNull
        List<?> list1 = nativeQuery.getResultList();
        System.out.println("✅endfun getListBySql.ret=list,listsize=" + list1.size());
        return list1;
    }


    public static @NotNull <T> List<T> getResultList(@NotBlank String sql, @NotNull Class<T> clz
    ) throws Throwable {

        ifIsBlank(sql);
        String mthClr = colorStr("getListBySqlLmt200", YELLOW_bright);
        System.out.println("\r\n▶\uFE0Ffun " + mthClr + "(" + (sql));

        Session session = sessionFactory.getCurrentSession();
        NativeQuery nativeQuery = session.createNativeQuery(sql, clz);
        // setPrmts4sql(sqlprmMap, nativeQuery);
        // 设置分页
        // nativeQuery.setFirstResult(0);
        // nativeQuery.setMaxResults(200);
        //       .setParameter("age", 18);
        @NotNull
        List<T> list1 = nativeQuery.getResultList();
        System.out.println("✅endfun getListBySqlLmt200.ret=list,listsize=" + list1.size());
        return list1;
    }

    @Deprecated
    public static @NotNull Object getListBySqlLmt200(@NotBlank String sql, @NotNull Session session) throws Throwable {

        ifIsBlank(sql);
        String mthClr = colorStr("getListBySqlLmt200", YELLOW_bright);
        System.out.println("\r\n▶\uFE0Ffun " + mthClr + "(" + (sql));

        NativeQuery nativeQuery = session.createNativeQuery(sql);
        // setPrmts4sql(sqlprmMap, nativeQuery);
        // 设置分页
        nativeQuery.setFirstResult(0);
        nativeQuery.setMaxResults(200);
        //       .setParameter("age", 18);
        @NotNull
        List<?> list1 = nativeQuery.getResultList();
        System.out.println("✅endfun getListBySqlLmt200.ret=list,listsize=" + list1.size());
        return list1;
    }

    /**
     * 可以通过 Session 来获取 EntityManager，因为 Session 实现了 EntityManager 接口。以下是如何通过 SessionFactory
     *
     * @param session
     * @return
     */
    public static @NotNull EntityManager toEttMngr(@NotNull Session session) {
        // 将 Session 转换为 EntityManager
        EntityManager entityManager = session.getEntityManagerFactory().createEntityManager();

        return entityManager;
    }

    public static SessionFactory sessionFactory;

    public static @NotNull @org.jetbrains.annotations.NotNull <T> T mergex(@NotNull T t) {

        return mergex(t, sessionFactory.getCurrentSession());
    }

    public static @NotNull @org.jetbrains.annotations.NotNull <T> T mergex(@NotNull T t, @NotNull Session session) {

        String mthClr = colorStr("mergex", YELLOW_bright);
        System.out.println("\r\n▶\uFE0Ffun " + mthClr + "(t=" + encodeJsonObj(t));
        System.out.println("mergex(" + t.getClass().getName());
        System.out.println(session);
        T rzt = session.merge(t);
        //   session.merge(objU);
        session.flush();
        System.out.println("✅endfun mergex.ret=" + encodeJson(rzt));
        return rzt;
    }

    public static <T> T findByHbntDep(Class<T> t, String id, Session session) {

        String mthClr = colorStr("findByHbnt", YELLOW_bright);
        System.out.println("\r\n▶\uFE0Ffun " + mthClr + "(class=" + t + ",id=" + id);
        //  System.out.println("findByHbnt("+ t.getClass().getName()+",id="+id);
        T rzt = session.find(t, id);
        System.out.println("✅endfun findByHbnt.ret=" + encodeJson(rzt));
        return rzt;

    }

    @Deprecated
    public static <T> T getSingleResultV2(String sql ,Class<T> enttClz) throws findByIdExptn_CantFindData {
        System.out.println("fun getSingleResult sql=" + sql);
        Session session = sessionFactory.getCurrentSession();
        Query<T> query = session.createNativeQuery(sql, enttClz);

        List<T> results = query.getResultList();

        if (results.isEmpty()) {
            throw new findByIdExptn_CantFindData("找不到数据");
        }

//        if (results.size() > 1) {
//            throw new IllegalStateException("返回了多个结果，数据异常：" + results.size() + " 条");
//        }

        return results.get(0);


    }


    public static <T> T getSingleResult(String sql ,Class<T> enttClz) throws findByIdExptn_CantFindData {
        System.out.println("fun getSingleResult sql=" + sql);
         Session   session = sessionFactory.getCurrentSession(); // 使用 SessionFactory 打开一个新的 Session
        Query<T> query = session.createNativeQuery(sql,enttClz); // 创建原生 SQL 查询
        try{
            T result = query.getSingleResult(); // 执行查询并获取唯一结果
            if (result == null)
                throw new findByIdExptn_CantFindData(sql);
            System.out.println("endfun getSingleResult.ret=" + encodeJsonObj(result));
            return result;
        }catch (jakarta.persistence.NoResultException e ){
            throw new findByIdExptn_CantFindData(sql);
        }


    }

    public static Object getSingleResult(String sql, Session session) throws findByIdExptn_CantFindData {

        // Session   session = sessionFactory.getCurrentSession(); // 使用 SessionFactory 打开一个新的 Session
        Query<?> query = session.createNativeQuery(sql); // 创建原生 SQL 查询
        Object result = query.getSingleResult(); // 执行查询并获取唯一结果
        if (result == null)
            throw new findByIdExptn_CantFindData(sql);

        return result;
    }


    /**
     * no uniRzt, dep...bcz no rzt ret null,,gsr no rzt ex,and gsr is jpa stdd,unirzt only hbnt api
     * 使用hibernate执行sql，返回一个字段值
     *
     * @param
     * @return
     */
    public static Object getSingleResult(String sql, Object dft, Session session) {

        // Session   session = sessionFactory.getCurrentSession(); // 使用 SessionFactory 打开一个新的 Session
        Query<?> query = session.createNativeQuery(sql); // 创建原生 SQL 查询
        Object result = query.getSingleResult(); // 执行查询并获取唯一结果

        return result;
    }

@Deprecated
    public static <T> T findByHbntDep(Class<T> t, String id, LockModeType lockModeType, Session session) {
        String mthClr = colorStr("findByHbnt", YELLOW_bright);
        System.out.println("\r\n▶\uFE0Ffun " + mthClr + "(class=" + t + ",id=" + id + ",LockModeType=" + lockModeType);
        //  System.out.println("findByHbnt("+ t+"。。。");
      //  T rzt = session.find(t, id, lockModeType);
    T rzt = session.find(t, id);
        System.out.println("✅endfun findByHbnt.ret=" + encodeJson(rzt));
        return rzt;
    }

    /**
     * Pageable pageable = PageRequest.of(2, 10, Sort.by("createdAt").descending());
     *
     * @param sql
     * @param sqlprmMap
     * @param pageable
     * @return
     * @throws SQLException
     */
    @Deprecated
    public static @NotNull Page<Map> getResultListWzPageByHbntRtLstmap(@NotBlank String sql, Map<String, Object> sqlprmMap, Pageable pageable) throws SQLException {

        System.out.println("fun getResultListWzPageByHbntRtLstmap(sql= " + sql);
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<?> nativeQuery = session.createNativeQuery(sql, Map.class);
        setPrmts4sql(sqlprmMap, nativeQuery);
        // 设置分页
        nativeQuery.setFirstResult(getstartPosition(pageable.getPageNumber(), pageable.getPageSize()));
        nativeQuery.setMaxResults(pageable.getPageSize());
        //       .setParameter("age", 18);
        List<Map> list1 = (List<Map>) nativeQuery.getResultList();


        //------------page
        long totalRecords = nativeQuery.getResultCount();

        //   Page<Usr> page;
        //    int totalPages = (int) Math.ceil((double) totalRecords / pageable.getPageSize());
        return new PageImpl<Map>(list1, pageable, totalRecords);
        // return new PageResult<>(list1, totalRecords, totalPages,pageable.getPageNumber(),pageable.getPageSize());
    }

    public static @NotNull <T> Page<T> getResultListWzPage(@NotBlank String sql, Map<String, Object> sqlprmMap, Pageable pageable,Class<T> clz) throws SQLException {

        System.out.println("fun getResultListWzPageByHbntRtLstmap(sql= " + sql);
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<T> nativeQuery = session.createNativeQuery(sql,clz);
        setPrmts4sql(sqlprmMap, nativeQuery);
        // 设置分页
        nativeQuery.setFirstResult(getstartPosition(pageable.getPageNumber(), pageable.getPageSize()));
        nativeQuery.setMaxResults(pageable.getPageSize());
        //       .setParameter("age", 18);
        List<T> list1 =   nativeQuery.getResultList();


        //------------page
        long totalRecords = nativeQuery.getResultCount();

        //   Page<Usr> page;
        //    int totalPages = (int) Math.ceil((double) totalRecords / pageable.getPageSize());
        return new PageImpl<T>(list1, pageable, totalRecords);
        // return new PageResult<>(list1, totalRecords, totalPages,pageable.getPageNumber(),pageable.getPageSize());
    }


    @Deprecated
    public static <T> T findByHerbinateLockForUpdt(Class<T> t, String id, Session session) {

        var lockModeType = LockModeType.PESSIMISTIC_WRITE;
        String mthClr = colorStr("findByHbnt", YELLOW_bright);
        System.out.println("\r\n▶\uFE0Ffun " + mthClr + "(class=" + t + ",id=" + id + ",LockModeType=" + lockModeType);
        //  System.out.println("findByHbnt("+ t+"。。。");
        T rzt = session.find(t, id);  //lockModeType
        System.out.println("✅endfun findByHbnt.ret=" + encodeJson(rzt));
        return rzt;
    }

    @NotNull
    public static <T> T findByHerbinateLockForUpdtV2(@NotNull Class<T> t, String id) throws findByIdExptn_CantFindData {
        return findByHerbinateLockForUpdtV2(t, id, sessionFactory.getCurrentSession());
    }

    @NotNull
    public static <T> T findByHerbinateLockForUpdtV2(@NotNull Class<T> t, String id, Session session) throws findByIdExptn_CantFindData {

        var lockModeType = LockModeType.PESSIMISTIC_WRITE;
        String mthClr = colorStr("findByHbnt", YELLOW_bright);
        System.out.println("\r\n▶\uFE0Ffun " + mthClr + "(class=" + t + ",id=" + id + ",LockModeType=" + lockModeType);
        //  System.out.println("findByHbnt("+ t+"。。。");
        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.lock.timeout", 2000); // 等待2秒
        //T rzt = session.find(t, id, lockModeType, props);
        T rzt = session.find(t, id);
        if (rzt == null)
            throw new findByIdExptn_CantFindData("cls=" + t + ",id=" + id);
        System.out.println("✅endfun findByHbnt.ret=" + encodeJson(rzt));
        return rzt;
    }


    @org.jetbrains.annotations.NotNull
    public static <T> T addModelIfNotExst(T obj, String id, Session session) {

        try {
            return (T) findById(obj.getClass(), id, session);
        } catch (findByIdExptn_CantFindData e) {

            return (T) persist(obj, session);
        }

    }

    public static int executeUpdate(String sql, Session session) {
        // 使用原生 SQL 执行 UPDATE 操作
        //String sql = "UPDATE my_entity SET name = :name WHERE id = :id";
        int updatedEntities = session.createNativeQuery(sql)
                // .setParameter("name", "New Name")
                //  .setParameter("id", 1L) // 假设更新 ID 为 1 的实体
                .executeUpdate();

        System.out.println("Number of entities updated: " + updatedEntities);
        return updatedEntities;
    }

    public static <T> T findById(Class<T> t, String id) throws findByIdExptn_CantFindData {
        return findById(t, id, sessionFactory.getCurrentSession());
    }

    //good bp  throw ex,,,more lubst
    public static <T> T findById(Class<T> t, String id, Session session) throws findByIdExptn_CantFindData {

        String mthClr = colorStr("findByHbnt", YELLOW_bright);
        System.out.println("\r\n▶\uFE0Ffun " + mthClr + "(class=" + t + ",id=" + id);
        //  System.out.println("findByHbnt("+ t.getClass().getName()+",id="+id);
        T rzt = session.find(t, id);
        if (rzt == null)
            throw new findByIdExptn_CantFindData("cls=" + t + ",id=" + id);
        System.out.println("✅endfun findByHbnt.ret=" + encodeJson(rzt));
        return rzt;
    }
}
