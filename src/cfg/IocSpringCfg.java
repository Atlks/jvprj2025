package cfg;

import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import util.proxy.AtProxy4Svs;
import util.proxy.AtProxy4api;
import util.Icall;
import util.StrUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

import static java.time.LocalTime.now;
//import static cfg.AopLogJavassist.printLn;
import static util.proxy.IocUtil.registerBean2map;
import static util.dbutil.setField;
import static util.util2026.*;

//PicoContainer more easy thena guice lite,guice ,spring
public class IocSpringCfg {
    public static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    @NotNull
    public static ApplicationContext iniIocContainr4spr() throws Exception {
        MyCfg.iniCfgFrmCfgfile();
        AppConfig.sessionFactory = new AppConfig().sessionFactory();
        //   context.scan("");

        Consumer<Class> csmr4log = clazz -> {
            //只针对api 和biz的开放注册修改class注入aop
            //clazz.getName() 只是获取类的全限定名（package.ClassName），不会触发类的静态初始化 或 类加载。

            if (!clazz.getName().startsWith("api") && !clazz.getName().startsWith("service")) {
                System.out.println("contine clz=" + clazz.getName());
                return;
            }
            printLn("\n开始注册" + clazz.getName());
            //-----------jdk dync pro xy
//                    if(clazz.getName().contains("RechargeHdr"))
//                        System.out.println("d306");
            Object obj1 = null;
            try {
                obj1 = clazz.getConstructor().newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            setField(obj1, SessionFactory.class, AppConfig.sessionFactory);
            // 目标对象
            Object proxyObj;  //def no prxy ,,
            //only prxy service obj
            if(clazz.getName().startsWith("service.") && isImpltInterface(clazz,Icall.class) )
            {
                proxyObj  = AtProxy4Svs.createProxy4log(obj1); // 创建代理
//                context.registerBean(clazz.getName(), (Class) proxyObj.getClass(), () -> proxyObj);
//                String beanName = StrUtil.lowerFirstChar(clazz.getSimpleName());
//                context.registerBean(beanName, (Class) Icall.class, () -> (Icall)proxyObj);
            } else if(clazz.getName().startsWith("api")) {
                proxyObj = new AtProxy4api(obj1);
            } else {
                proxyObj = obj1;
            }

            context.registerBean(clazz.getName(), (Class) proxyObj.getClass(), () -> proxyObj);
            String beanName = StrUtil.lowerFirstChar(clazz.getSimpleName());
            context.registerBean(beanName, (Class)proxyObj.getClass(), () -> proxyObj);
            context.registerBean(clazz.getSimpleName(), (Class)proxyObj.getClass(), () ->proxyObj);

            registerBean2map(clazz.getSimpleName(),proxyObj);

            //context.registerBean( clazz.getName(), proxy);
            printLn("spr已注册: " + beanName);
            printLn("spr已注册: " + clazz.getName());

        };
        scanAllClass(csmr4log);//  all add class  ...  mdfyed class btr
        //chkek reg bean ..must use beanmae to reg ,not bean.class,,bcz maybe reg custome class aop mdfyed...
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println("..已注册 Bean：" + beanName);
        }

        return context;
    }

    //判断此类是否实现了icall接口
    private static boolean isImpltInterface(Class clazz, Class<Icall> icallClass) {
        // 获取类实现的所有接口
        Class<?>[] interfaces = clazz.getInterfaces();

        // 遍历接口数组，检查是否有与 icallClass 匹配的接口
        for (Class<?> iface : interfaces) {
            if (iface==(icallClass)) {
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
