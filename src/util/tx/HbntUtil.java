package util.tx;

import entityx.Keyx;
import jakarta.persistence.Entity;
import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.query.NativeQuery;
import org.hibernate.service.ServiceRegistry;

import java.sql.SQLException;
import java.util.List;
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
        var db = getDatabaseFileName4mysql(jdbcUrl);
        crtDatabase(jdbcUrl, db);

        // Hibernate 配置属性
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, getDvr(jdbcUrl));
        properties.put(Environment.URL, "" + jdbcUrl);
        properties.put(Environment.USER, getUnameFromJdbcurl(jdbcUrl));
        properties.put(Environment.PASS, getPwdFromJdbcurl(jdbcUrl));
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        //    hibernate.dialect.storage_engine
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.FORMAT_SQL, "true");
        properties.put(Environment.STORAGE_ENGINE, "innodb");
        //HBM2DDL_CHARSET_NAME
        properties.put(Environment.HBM2DDL_AUTO, "update"); // 自动建表
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

        properties.put(Environment.ORDER_INSERTS, "false");
        properties.put(Environment.ORDER_UPDATES, "false");

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
        SessionFactory sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
        return sessionFactory;
    }

    private static void addAnnotatedClasses2025(MetadataSources metadataSources) {


        // 递归扫描 .class 文件
        List<Class<?>> classList = getClassesList();
        //   scanClasses(classDir, classDir.getAbsolutePath(), classList);

        // 注册到 hbnt
        for (Class<?> clazz : classList) {
            try {
                if (clazz.getName().startsWith("entityx")) {

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


    // @log
    public static Object persistByHibernate(Object var1, Session session) {

        String mth = colorStr("persistByHbnt", YELLOW_bright);
        String prmurl = colorStr(encodeJsonObj(var1), GREEN);
        System.out.println("\r\n▶\uFE0Ffun " + mth + "(o=" + prmurl);

        System.out.println("persistByHbnt(" + var1.getClass().getName());
        session.persist(var1);
        session.flush();

        System.out.println("✅endfun persistByHbnt()");
        return var1;
    }

    public static @NotNull Object getListBySql(@NotBlank String sql, @NotNull Session session) throws Throwable {

        ifIsBlank(sql);
        String mthClr = colorStr("getListBySql", YELLOW_bright);
        System.out.println("\r\n▶\uFE0Ffun " + mthClr + "(" + (sql));
        NativeQuery nativeQuery = session.createNativeQuery(sql);
        // setPrmts4sql(sqlprmMap, nativeQuery);

        //       .setParameter("age", 18);
        @NotNull
        List<?> list1 = nativeQuery.getResultList();
        System.out.println("✅endfun getListBySql.ret=list,listsize=" + list1.size());
        return list1;
    }

    public static @NotNull Object getListBySqlLmt200(@NotBlank String sql, @NotNull Session session) throws Throwable {

        ifIsBlank(sql);
        String mthClr = colorStr("getListBySqlLmt200", YELLOW_bright);
        System.out.println("\r\n▶\uFE0Ffun " + mthClr + "(" + (sql));
        // System.out.println("mergeByHbnt("+ t.getClass().getName());
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


    public static @NotNull @org.jetbrains.annotations.NotNull <T> T mergeByHbnt(@NotNull T t, @NotNull Session session) {
        String mthClr = colorStr("mergeByHbnt", YELLOW_bright);
        System.out.println("\r\n▶\uFE0Ffun " + mthClr + "(t=" + encodeJsonObj(t));
        System.out.println("mergeByHbnt(" + t.getClass().getName());
        T rzt = session.merge(t);
        //   session.merge(objU);
        session.flush();
        System.out.println("✅endfun mergeByHbnt.ret=" + encodeJson(rzt));
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

    public static <T> T findByHerbinateLockForUpdt(Class<T> t, String id, Session session) {

        var lockModeType = LockModeType.PESSIMISTIC_WRITE;
        String mthClr = colorStr("findByHbnt", YELLOW_bright);
        System.out.println("\r\n▶\uFE0Ffun " + mthClr + "(class=" + t + ",id=" + id + ",LockModeType=" + lockModeType);
        //  System.out.println("findByHbnt("+ t+"。。。");
        T rzt = session.find(t, id, lockModeType);
        System.out.println("✅endfun findByHbnt.ret=" + encodeJson(rzt));
        return rzt;
    }


    //good bp  throw ex,,,more lubst
    public static <T> T findByHerbinate(Class<T> t, String id, Session session) throws findByIdExptn_CantFindData {

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
