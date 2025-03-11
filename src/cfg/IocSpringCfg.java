package cfg;

import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.auth.SAM;
import util.auth.SAM4chkLgnStatJwtMod;
import util.proxy.AtProxy4Svs;
import util.algo.Icall;
import util.oo.StrUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

import static api.usr.RegHandler.SAM4regLgn;
import static java.time.LocalTime.now;
//import static cfg.AopLogJavassist.printLn;
import static util.proxy.AtProxy4api.ChkLgnStatSam;
import static util.proxy.IocUtil.registerBeanAsClz2map;
import static util.proxy.IocUtil.registerBeanAsObj2map;
import static util.proxy.SprUtil.registerBean;
import static util.tx.dbutil.setField;
import static util.misc.util2026.*;

//PicoContainer more easy thena guice lite,guice ,spring
public class IocSpringCfg {
    public static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    @NotNull
    public static ApplicationContext iniIocContainr4spr() throws Exception {
        MyCfg.iniCfgFrmCfgfile();
        AppConfig.sessionFactory = new AppConfig().sessionFactory();
        //===================scan ini all
        Consumer<Class> csmr4log = clazz -> {

            if (!clazz.getName().startsWith("api") && !clazz.getName().startsWith("service")) {
                System.out.println("contine clz=" + clazz.getName());
                return;
            }
            printLn("\n开始注册" + clazz.getName());
            Object obj1 = getObject(clazz);
            setField(obj1, SessionFactory.class, AppConfig.sessionFactory);
            // 目标对象
            Object proxyObj;  //def no prxy ,,
            //only prxy service obj
            if (clazz.getName().startsWith("service.") && isImpltInterface(clazz, Icall.class)) {
                proxyObj = AtProxy4Svs.createProxy4log(obj1); // 创建代理
                registerBeanAsObj2sprNmap(clazz, proxyObj);
            } else {
                /** if (clazz.getName().startsWith("api"))
                 * 默认行为：
                 * 它使用默认的 bean 定义，这意味着：
                 * bean 将使用默认的构造函数创建。
                 * bean 的作用域将是单例（singleton）。
                 * bean 将不会有任何初始化或销毁回调。
                 * 适用场景：
                 * 适用于简单的 bean 注册，当你不需要对 bean 的创建和生命周期进行精细控制时。
                 */
                registerBean(clazz,context);

            }

        };
        scanAllClass(csmr4log);//  all add class  ...  mdfyed class btr


        //===============chkek reg bean ..must use beanmae to reg ,not bean.class,,bcz maybe reg custome class aop mdfyed...
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println("..已注册 Bean：" + beanName);
        }


        //---------------ini  custm
        //    obj1 = clazz.getConstructor().newInstance();
        registerBeanAsClz2sprNmap(ChkLgnStatSam, SAM4chkLgnStatJwtMod.class);
        registerBeanAsClz2sprNmap(SAM4regLgn, SAM.class);
        return context;
    }


    //只针对api 和biz的开放注册修改class注入aop
    //clazz.getName() 只是获取类的全限定名（package.ClassName），不会触发类的静态初始化 或 类加载。

    @NotNull
    private static Object getObject(Class clazz) {
        Object obj1 = null;
        try {
            obj1 = clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return obj1;
    }

    private static void registerBeanAsClz2sprNmap(String beanname, Class<?> clz) {
        context.registerBean(beanname, clz);
        registerBeanAsClz2map(beanname, clz);
//        context.registerBean(SAM4regLgn, SAM.class);
//        registerBean2map(SAM4regLgn, SAM.class);

    }

    private static void registerBeanAsObj2sprNmap(Class clazz4beanName, Object proxyObj) {
        registerBeanAsObj2spr(clazz4beanName, proxyObj);
        registerBeanAsObj2map(clazz4beanName.getSimpleName(), proxyObj);

    }

    private static void registerBeanAsObj2spr(Class clazz, Object proxyObj) {

//        context.registerBean(clazz.getName(), (Class) proxyObj.getClass(), () -> proxyObj);
        String beanName = StrUtil.lowerFirstChar(clazz.getSimpleName());
        context.registerBean(beanName, (Class) proxyObj.getClass(), () -> proxyObj);
        context.registerBean(clazz.getSimpleName(), (Class) proxyObj.getClass(), () -> proxyObj);
        printLn("spr已注册: " + beanName);
        printLn("spr已注册: " + clazz.getName());
    }


    //                context.registerBean(clazz.getName(), (Class) proxyObj.getClass(), () -> proxyObj);
//                String beanName = StrUtil.lowerFirstChar(clazz.getSimpleName());
//                context.registerBean(beanName, (Class) Icall.class, () -> (Icall)proxyObj);

    //判断此类是否实现了icall接口
    private static boolean isImpltInterface(Class clazz, Class<Icall> icallClass) {
        // 获取类实现的所有接口
        Class<?>[] interfaces = clazz.getInterfaces();

        // 遍历接口数组，检查是否有与 icallClass 匹配的接口
        for (Class<?> iface : interfaces) {
            if (iface == (icallClass)) {
                return true;  // 类实现了 icall 接口
            }
        }

        return false;  // 类没有实现 icall 接口

    }


    //        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
    // **使用 Provider，每次获取都是新的 `Session`**
    //   container888.addAdapter(new SessionProvider());

    //  List<Class> li = List.of();


    //  context.register(modifiedClass);
    //  context.registerBean(modifiedClass, modifiedClass.getName());
    //   context.refresh(); // 这一步必须有！


//    private static void injectAll() {
//        Function<Class,Object> fun=new Function<Class, Object>() {
//            @Override
//            public Object apply(Class aClass) {
//              Object ins=  PicoContainer888.getComponent(aClass);
//                System.out.println(" ijt ins="+ins);
//              traveProp(ins);
//                Object ins2=  PicoContainer888.getComponent(aClass);
//                return null;
//            }
//        };
//        scanAllClass(fun);
//
//    }

    //遍历对象属性，如果为null，则设置为PicoContainer.getComponent(属性类型.class)
//    private static void traveProp(Object ins) {
//
//        if (ins == null) return;
//
//        Class<?> clazz = ins.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//
//        for (Field field : fields) {
//            field.setAccessible(true); // 允许访问私有字段
//            if(field.getType()== WltService.class)
//                System.out.println("d951");
//            System.out.println("trav prop="+field);
//            try {
//                if (field.get(ins) == null) { // 如果字段值为 null
//                    Object component = PicoContainer888.getComponent(field.getType());
//                    if (component != null) {
//                        System.out.println("setprop as:"+component);
//                        field.set(ins, component);
//                    }
//                }
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException("Failed to inject property: " + field.getName(), e);
//            }
//        }
//    }


}
