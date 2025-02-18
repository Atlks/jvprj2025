package util;

import jakarta.persistence.LockModeType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static util.ColorLogger.*;
import static util.StrUtil.getPwdFromJdbcurl;
import static util.StrUtil.getUnameFromJdbcurl;
import static util.Util2025.encodeJson;
import static util.Util2025.encodeJsonObj;
import static util.dbutil.*;

//ormUtilHbnt
public class HbntUtil {

    public static Session openSession(String jdbcUrl, List<Class> li) throws SQLException {
        String mthClr=colorStr("openSession",YELLOW_bright);
        String urlClr=colorStr(jdbcUrl,GREEN);
        String liClr=colorStr(encodeJsonObj(li),GREEN);
        System.out.println("▶\uFE0F fun "+mthClr+"(url="+urlClr+",listClass="+liClr);
        var db=getDatabaseFileName4mysql(jdbcUrl);
        crtDatabase(jdbcUrl,db);

        // Hibernate 配置属性
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, getDvr(jdbcUrl));
        properties.put(Environment.URL, ""+jdbcUrl);
        properties.put(Environment.USER, getUnameFromJdbcurl(jdbcUrl));
        properties.put(Environment.PASS, getPwdFromJdbcurl(jdbcUrl));
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        //    hibernate.dialect.storage_engine
        properties.put(Environment.SHOW_SQL, "true");

     //   properties.put(Environment.FORMAT_SQL, "true");
        properties.put(Environment.STORAGE_ENGINE, "innodb");
        //HBM2DDL_CHARSET_NAME
        properties.put(Environment.HBM2DDL_AUTO, "update"); // 自动建表
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

        properties.put( Environment.ORDER_INSERTS, "false");
        properties.put( Environment.ORDER_UPDATES, "false");

        // 创建 ServiceRegistry
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(properties)
                .build();

        // 添加实体类映射
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        for(Class cls : li)
        {
            metadataSources.addAnnotatedClasses(cls);
        }

        // 创建 SessionFactory
        SessionFactory sessionFactory = metadataSources.buildMetadata().buildSessionFactory();

        // 获取 Session
        Session session = sessionFactory.openSession();
        System.out.println("✅endfun openSession()");
        return  session;


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

    public static void persistByHbnt(Object var1, Session session) {
        System.out.println("\r\n▶\uFE0Ffun persistByHbnt(o="+encodeJsonObj(var1));

        System.out.println("persistByHbnt("+ var1.getClass().getName());
        session.persist(var1);
        session.flush();
        System.out.println("✅endfun persistByHbnt()");
    }

    public static <T> T mergeByHbnt(T  t, Session session) {
        System.out.println("\r\n▶\uFE0Ffun mergeByHbnt(t="+encodeJsonObj(t));
        System.out.println("mergeByHbnt("+ t.getClass().getName());
        T rzt = session.merge(t);
        //   session.merge(objU);
        session.flush();
        System.out.println("✅endfun mergeByHbnt.ret="+ encodeJson(rzt));
        return rzt;
    }

    public static  <T> T findByHbnt(Class<T> t, String id, Session session) {
        System.out.println("\r\n▶\uFE0Ffun findByHbnt(class="+t+",id="+id);
      //  System.out.println("findByHbnt("+ t.getClass().getName()+",id="+id);
        T rzt = session.find(t, id);
        System.out.println("✅endfun findByHbnt.ret="+ encodeJson(rzt));
        return rzt;

    }


    public static <T> T findByHbnt(Class<T> t, String id, LockModeType lockModeType, Session session) {

        System.out.println("\r\n▶\uFE0Ffun findByHbnt(class="+t+",id="+id+",LockModeType="+lockModeType);
      //  System.out.println("findByHbnt("+ t+"。。。");
        T rzt = session.find(t, id,lockModeType);
        System.out.println("✅endfun findByHbnt.ret="+ encodeJson(rzt));
        return rzt;
    }

}
