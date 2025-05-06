


# biz fun ,  vs sys fun(uti)

# long time fun,,vs  stf 


如果你需要跨函数/跨实例的共享状态或低延迟连接复用：

方案	说明
使用 Redis / Memcached	存储连接信息、缓存状态、用户会话等
使用数据库连接池服务（如 RDS Proxy）	云厂商提供的连接池，避免频繁建连
使用全局初始化组件 + 容器内缓存	构建如 SessionFactory 或 HttpClient 实例，并在函数内部静态持有


aws 可以使用静态变量，静态方法直接初始化sess fctry,后面faas直接使用fctr即可。。。



public class MyLambdaHandler implements RequestHandler<Map<String, Object>, String> {

    // 静态变量：容器生命周期内可复用，类似单例
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            // Cold start 时初始化失败直接抛出
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


