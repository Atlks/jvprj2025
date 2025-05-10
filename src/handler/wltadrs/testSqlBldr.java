//package handler.wltadrs;
//
//import entityx.usr.Usr;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//import jakarta.persistence.TypedQuery;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import org.hibernate.Session;
//import org.hibernate.engine.spi.SessionImplementor;
//import org.hibernate.jpa.HibernatePersistenceProvider;
//import org.jooq.*;
//import org.jooq.conf.ParamType;
//import org.jooq.impl.DSL;
//
//import java.lang.Record;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static util.algo.GetUti.getTableName;
//
//public class testSqlBldr {
//
//
//    //完全通过 Java 配置来替代 persistence.xml
//    public static void main(String[] args) {
//        // 配置 JPA，等效于 persistence.xml
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        properties.put("hibernate.hbm2ddl.auto", "update");  // 数据库自动更新
//        properties.put("hibernate.show_sql", "true");
//        properties.put("hibernate.jdbc.driver", "com.mysql.cj.jdbc.Driver");  // MySQL 驱动
//        properties.put("hibernate.jdbc.url", "jdbc:mysql://localhost:3306/prjdb");  // MySQL 数据库 URL
//        properties.put("hibernate.jdbc.user", "root");  // 数据库用户名
//        properties.put("hibernate.jdbc.password", "pppppp");  // 数据库密码
//
//
//
//        // 实体类路径（例如：指定包扫描）
//        properties.put("hibernate.archive.autodetection", "class, hbm");  // 自动扫描实体类
//
//        // 将实体类添加到 jpaProperties 中
//         properties.put("jakarta.persistence.classnames", "entityx.usr.Usr"); // 替换为你的实体类全路径
//
//        // 使用 HibernatePersistenceProvider 来创建 EntityManagerFactory
//        HibernatePersistenceProvider persistenceProvider = new HibernatePersistenceProvider();
//
//        // 创建 EntityManagerFactory，不需要配置 PersistenceUnit 名称
//        EntityManagerFactory emf = persistenceProvider.createEntityManagerFactory(null, properties); // 传入 null，避免使用 PersistenceUnit 名称
//
//        System.out.println(emf);
//
//        // 创建 EntityManager
//        var em = emf.createEntityManager();
//
//        // 创建 CriteriaBuilder 和 CriteriaQuery
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Usr> query = cb.createQuery(Usr.class);
//
//      //  Root：表示查询的根实体，类似于 SQL 中的 FROM。
//        Root<Usr> root = query.from(Usr.class);
//
//        // 构造 uname = 'John' 条件
//        Predicate condition = cb.equal(root.get("uname"), "John");
//        query.select(root).where(condition);
//
//        // 执行查询
//        TypedQuery<Usr> typedQuery = em.createQuery(query);
//        List<Usr> result = typedQuery.getResultList();
//
//        // 输出 JPQL 查询语句
//        System.out.println("JPQL: " + query);
//
//        // 可选：打印 JPQL 或 SQL（依赖底层实现，如 Hibernate）
//        System.out.println("JPQL: " + typedQuery.unwrap(org.hibernate.query.Query.class).getQueryString());
//
//
//        // 获取实际的 SQL 查询（如果使用 Hibernate）
//        if (em.unwrap(Session.class) instanceof SessionImplementor) {
//            SessionImplementor sessionImplementor = (SessionImplementor) em.unwrap(Session.class);
//            org.hibernate.query.Query<?> hibernateQuery = sessionImplementor.createQuery(query);
//            System.out.println("SQL: " + hibernateQuery.getQueryString());
//        }
//
//        em.close();
//        emf.close();
//    }
//    //将此段代码翻译为  JPA Criteria API
//    public static void main2(String[] args) {
//
//        Table<?> USER = DSL.table(getTableName(Usr.class));
//        Field<String> uname = DSL.field("uname", String.class);
//
//        DSLContext create = DSL.using(SQLDialect.MYSQL);
//        SelectQuery<?> query = create.selectQuery();
//
//        query.addFrom(USER);
//        query.addConditions(uname.eq("John"));
//        String sql = query.getSQL(ParamType.INLINED);  // ✅ 直接拿到 SQL 字符串
//        System.out.println(sql);
//    }
//
//
//}
