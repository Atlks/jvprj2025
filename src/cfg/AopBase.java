package cfg;

import org.hibernate.SessionFactory;




import javax.inject.Inject;
import java.io.Serializable;

import static util.tx.dbutil.setField;


public abstract class AopBase implements Serializable {
    // 实现 Serializable 接口
    private static final long serialVersionUID = 1L; // 推荐加 serialVersionUID
    public static boolean ovrtTEst = false;
//    
//    @Lazy
    @Inject
    public SessionFactory sessionFactory;

    //
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    // 统一对外的方法，做 AOP 代理
    public final void doAction() {
        // AOP 逻辑：前置增强（如日志、权限校验）
        System.out.println("【AOP】开始执行方法...");

        // 调用子类的实际业务逻辑
        doAction2();

        // AOP 逻辑：后置增强（如监控、性能分析）
        System.out.println("【AOP】方法执行完成！");
    }

    // 让子类实现具体的业务逻辑

    public abstract void doAction2();

    // 反射调用方法
//    public   void invk( @NotNull String methodName, @NotNull Object... prm) throws Exception {
//        Class<?> aClass =this.getClass();
//        Class<?> modifiedClass = getAClassExted(aClass);
//        Object instance ;
//        try{
//            instance= modifiedClass.getConstructor().newInstance();
//        } catch (NoSuchMethodException e) {
//            // 没有无参构造函数
//            Constructor<?> constructor = modifiedClass.getConstructor(SessionFactory.class); // 指定参数类型
//            instance = constructor.newInstance( sessionFactory);
//
//            //other dync fore
//        }
//
////            setField(instance,"session",new SessionProvider().provide());
//        //new SessionProvider().provide()
//        setField(instance, SessionFactory.class, sessionFactory );
//        System.out.println("inst="+instance);
//        System.out.println("insts.sessFctry="+getField(instance,"sessionFactory"));
//
//        //这里根据参数，生成参数类型
//        Class<?>[] parameterTypes = Arrays.stream(prm).map(Object::getClass).toArray(Class<?>[]::new);
//        Method method = modifiedClass.getMethod(methodName, parameterTypes);
//        setField(instance, "sessionFactory", sessionFactory );
//        copyProps(this,instance);
//        method.invoke(instance, prm);
//    }

}
