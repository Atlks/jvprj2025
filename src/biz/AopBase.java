package biz;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.Serializable;

import static util.dbutil.setField;

@Component
public class AopBase implements Serializable {
    // 实现 Serializable 接口
    private static final long serialVersionUID = 1L; // 推荐加 serialVersionUID
    public static boolean ovrtTEst = false;
    @Autowired
    @Inject
    public SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

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
