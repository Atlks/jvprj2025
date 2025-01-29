package test;

import apiOrdBet.OrdBet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateExample {
    public static void main(String[] args) throws Exception, Throwable, HibernateException {
        System.out.println("Class path: " + System.getProperty("java.class.path"));

        // 创建 SessionFactory
        // C:\Users\attil\IdeaProjects\jvprj2025\cfg\ordbet.hibernate.cfg.xml
        SessionFactory factory = new Configuration()
                .configure("yonjin/ordbet.hibernate.cfg.xml")
                .addAnnotatedClass(OrdBet.class)
                .buildSessionFactory();

        // 创建 Session
        Session session = factory.getCurrentSession();

        try {
            // 1. 保存数据
            OrdBet newUser = new OrdBet();
            newUser.uname="John Doe2";
            newUser.id=newUser.uname;
          //  newUser.setEmail("john.doe@example.com");

            // 开启事务
            session.beginTransaction();

            // 保存用户
            session.save(newUser);

            // 提交事务
            session.getTransaction().commit();

            // 2. 查询数据
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//
//            List<User> users = session.createQuery("from User", User.class).getResultList();
//
//            for (User user : users) {
//                System.out.println("User: " + user.getName() + ", Email: " + user.getEmail());
//            }
//
//            session.getTransaction().commit();
        } finally {
//            try {
//                factory.close();
//            } catch (Throwable | HibernateException e) {
//                throw new RuntimeException(e);
//            }
        }
    }
}
