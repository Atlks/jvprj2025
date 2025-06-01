package orgx;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import orgx.u.User;
import orgx.uti.context.ProcessContext;
import orgx.uti.orm.CustomInterceptor;

public class CfgSvs {

    /// jdbc:h2:file:C:\Users\Administrator\IdeaProjects/untitled\data\testdb;AUTO_SERVER=TRUE
    static SessionFactory getSessionFactory() {
        Configuration config = new Configuration();
        config.addAnnotatedClass(User.class); // **绑定实体类**
        config.setProperty("hibernate.connection.url", "jdbc:h2:file:./data/testdb;AUTO_SERVER=TRUE");
        config.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        config.setProperty("hibernate.connection.username", "sa");
        config.setProperty("hibernate.connection.password", "");
        config.setProperty("hibernate.hbm2ddl.auto", "update");
        config.setInterceptor(new CustomInterceptor()); // **注册拦截器**
// **创建 SessionFactory**
        SessionFactory sessionFactory = config.buildSessionFactory();

        ProcessContext.sessionFactory=sessionFactory;
        return sessionFactory;
    }
}
