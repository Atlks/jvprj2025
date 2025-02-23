package cfg;

import biz.BaseHdr;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.time.LocalTime.now;
import static util.AopLogJavassist.getAClassExted;
import static util.AopLogJavassist.printLn;
import static util.util2026.scanClasses;

//PicoContainer more easy thena guice lite,guice ,spring
public class IocSpringCfg {
    public static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    @NotNull
    public static ApplicationContext iniIocContainr4spr() throws SQLException {
        BaseHdr.iniCfgFrmCfgfile();

//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        // **使用 Provider，每次获取都是新的 `Session`**
     //   container888.addAdapter(new SessionProvider());

      //  List<Class> li = List.of();

    //    SessionFactory sessionFactory = getSessionFactory(saveDirUsrs, li);

      //   context.scan("");
        scanAllClass();//  all add class  ...  mdfyed class btr

      //  context.register(modifiedClass);
      //  context.registerBean(modifiedClass, modifiedClass.getName());
     //   context.refresh(); // 这一步必须有！


        //chkek reg bean ..must use beanmae to reg ,not bean.class,,bcz maybe reg custome class aop mdfyed...
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println("..已注册 Bean：" + beanName);
        }
        //  injectAll();
        return context;
    }

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

    /**
     * 扫描classes路径所有class，加入到容器 MutablePicoContainer
     */
    public static void scanAllClass(Function f) {
        try {
            // 获取 classes 目录
            String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            File classDir = new File(classpath);
            if (!classDir.exists() || !classDir.isDirectory()) {
                System.err.println("classes 目录不存在！");
                return;
            }

            // 递归扫描 .class 文件
            List<Class<?>> classList = new ArrayList<>();
            scanClasses(classDir, classDir.getAbsolutePath(), classList);

            // 注册到 PicoContainer
            for (Class<?> clazz : classList) {
                try {
                    f.apply(clazz);
                 //   container888.addComponent(clazz);
                    System.out.println("f.aply: " + clazz.getName());
                } catch (Exception e) {
                    System.err.println("apply失败: " + clazz.getName());
                    System.err.println("apply失败msg: " + e.getMessage());
                    //  System.err.println("注册失败: " + clazz.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 扫描classes路径所有class，加入到容器 MutablePicoContainer
     */
    public static void scanAllClass() {
        System.out.println( "scannAllcls at "+now());
        try {
            // 获取 classes 目录
            String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            File classDir = new File(classpath);
            if (!classDir.exists() || !classDir.isDirectory()) {
                System.err.println("classes 目录不存在！");
                return;
            }

            // 递归扫描 .class 文件
            List<Class<?>> classList = new ArrayList<>();
            scanClasses(classDir, classDir.getAbsolutePath(), classList);

            // 注册到 PicoContainer
            for (Class<?> clazz : classList) {
                try {
                    //只针对api 和biz的开放注册修改class注入aop
                    //clazz.getName() 只是获取类的全限定名（package.ClassName），不会触发类的静态初始化 或 类加载。

                    if(!clazz.getName().startsWith("api") && !clazz.getName().startsWith("service"))
                    {
                        System.out.println("contine clz="+clazz.getName());
                        continue;
                    }


                    printLn("\n开始注册"+clazz.getName());
                    Class<?> modifiedClass = getAClassExted(clazz);
                   //  context.register(modifiedClass);  jeig bhao,,beanname not classname
                    context.registerBean( modifiedClass.getName(), modifiedClass);
                 //   context. registerBean(modifiedClass,modifiedClass.getName());
                    printLn("modifiedClass.getClassLoader="+modifiedClass.getClassLoader());
                    printLn("spr已注册: " + clazz.getName());

                } catch (Exception e) {
                    printLn("spr注册失败: " + clazz.getName());
                    printLn("spr注册失败msg: " + e.getMessage());
                  //  System.err.println("注册失败: " + clazz.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
