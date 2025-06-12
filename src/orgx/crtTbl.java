package orgx;

import org.hibernate.SessionFactory;
import orgx.acc.Account;
import orgx.transaction.Balance;
import orgx.transaction.Transaction;
import orgx.u.Eml;
import orgx.u.User;

import java.util.List;
import java.util.UUID;

import static orgx.CfgSvs.*;
import static orgx.orm.HbntUti.getSessionFactoryMysql;
import static orgx.uti.orm.TxMng.*;
import static orgx.uti.orm.JpaUti.getResultList;
import static orgx.uti.orm.JpaUti.merge;

public class crtTbl {
    public static void main(String[] args) {
        // **编程式配置 EntityManagerFactory**
//        Map<String, Object> settings = new HashMap<>();
//        settings.put(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:h2:mem:testdb");
//        settings.put(AvailableSettings.JAKARTA_JDBC_DRIVER, "org.h2.Driver");
//        settings.put(AvailableSettings.JAKARTA_JDBC_USER, "sa");
//        settings.put(AvailableSettings.JAKARTA_JDBC_PASSWORD, "");
//        settings.put(AvailableSettings.DIALECT, "org.hibernate.dialect.H2Dialect");
//        settings.put(AvailableSettings.HBM2DDL_AUTO, "update"); // 自动更新数据库
//
//        // **创建 PersistenceConfiguration**
//        PersistenceConfiguration config =new  PersistenceConfiguration("cfg1")
//                .managedClass(User.class) // 绑定实体类
//                .properties(settings);
//        // **创建 EntityManagerFactory**
//        EntityManagerFactory emf =config.createEntityManagerFactory();
//  emf.getSchemaManager().create(true);
//        EntityManager em=emf.createEntityManager();


        SessionFactory sessionFactory = getSessionFactoryMysql(getDataSourceMysql(), new Class[]{User.class,Transaction.class, Account.class, Balance.class});
//        begin();
//        //    em.persist(new User("Alice"));
//        merge(new User("Bob1"));
//        //    em.persist(new User("Bob"));
//        commit();


        callInTransaction(em -> {
            Eml eml = new Eml();
            eml.emll = "xx@xx.com";
            eml.len = 88;
            User u = new User("bb66");
            u.email = eml;
            merge(u);
            Transaction tx=new Transaction();
            tx.setTransactionId(UUID.randomUUID().toString());
            tx.setAccountId(UUID.randomUUID().toString()); merge(tx);
            return null;
        });

        // **查询数据**
        String query1 = "SELECT u FROM User u";
        Class cls = User.class;
        List<User> li = getResultList(query1, cls);


        li.forEach(user -> System.out.println("User: " + user.getName()));


        //  emf.close();
    }


}
