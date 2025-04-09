package cfg;


import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

import static biz.BaseHdr.saveDirUsrs;
import static cfg.AopLogJavassist.*;
//import static cfg.AopLogJavassist.printLn;
import static util.tx.HbntUtil.getSessionFactory;
import static util.tx.dbutil.setField;
import static util.misc.util2026.*;

//PicoContainer more easy thena guice lite,guice ,spring
public class IocAtiiocCfg {
    public static Map<String,Object> AtIoc_context=new HashMap<>();

    @NotNull
    public static Map<String,Object> iniIocContainr4at() throws SQLException, FileNotFoundException {
        MyCfg.iniContnr4cfgfile();

//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        // **使用 Provider，每次获取都是新的 `Session`**
     //   container888.addAdapter(new SessionProvider());

        List<Class> li = List.of();
        MyCfg.iniContnr4cfgfile();
        SessionFactory sessionFactory = getSessionFactory(saveDirUsrs, li);
        AtIoc_context.put(SessionFactory.class.getName(),sessionFactory);
       // container888.addAdapter(new SessionFactProvider());
        // 注册组件
     //   PicoContainer888.addComponent(sessionFactory);
    //    container888.addComponent(LoginHdr.class);
        scanAllClassRgClz();//  all add class  ...  mdfyed class btr
         injectAll();
        return AtIoc_context;
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
    public static void scanAllClassRgClz() {
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
                synchronized (lock) {
                    System.out.flush();  // 刷新输出缓冲区
                    System.err.flush();  // 刷新输出缓冲区
                    try {
                        if (clazz.getName().startsWith("entityx."))
                            continue;
                        if (clazz.getName().startsWith("test."))
                            continue;
                        if (clazz.getName().startsWith("cfg."))
                            continue;
                        if (clazz.getName().startsWith("util"))
                            continue;
                        if (clazz.getName().contains("ReChargeComplete"))
                             printLn("D1138");
                        System.out.flush();  // 刷新输出缓冲区
                        Class<?> modifiedClass = getAClassAoped(clazz);
                        //   context.register(modifiedClass);
                        if( modifiedClass.getName().contains("ReChargeComplete"))
                            System.out.println("d231");
                        Object instsWzSessfac = getInstsWzSessfac(modifiedClass);
                        AtIoc_context.put(modifiedClass.getName(), instsWzSessfac);

                        printLn("atIoc已注册: " + clazz.getName());
                        printLn("conetxt size:"+AtIoc_context.size());

                    } catch (Exception e) {
                        printLn("atIoc注册失败: " + clazz.getName());
                        System.err.flush();  // 刷新输出缓冲区
                        printLn("atIoc注册失败msg: " + e.getMessage());
                        System.err.flush();  // 刷新输出缓冲区
                        //  System.err.println("注册失败: " + clazz.getName());
                    }
                }
              //  sleepMs(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sleepMs(int i) throws InterruptedException {
   Thread.sleep(i);
    }

    private static void injectAll() {

        AtIoc_context.forEach((key, ins) -> {
            System.out.println("Key: " + key + ", Value: " + ins);
            Class<?> clazz = ins.getClass();
            if(clazz!=SessionFactory.class)
            {
                Field[] fields = clazz.getFields();

                for (Field field : fields) {
                    field.setAccessible(true); // 允许访问私有字段
//                    if(field.getType()== AddRchgOrdToWltService.class)
//                        System.out.println("d951");
                    System.out.println("trav prop="+field);
                    try {
                        if (field.get(ins) == null) { // 如果字段值为 null
                            Object component = getBean2025(field.getType());
                            if (component != null) {
                                System.out.println("setprop as:"+component);
                                field.set(ins, component);
                                // setFld(ins, field, component);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to inject property: " + field.getName(), e);
                    }
                }
            }


        });


//        Function<Class,Object> fun=new Function<Class, Object>() {
//            @Override
//            public Object apply(Class aClass) {
//                Object ins=  PicoContainer888.getComponent(aClass);
//                System.out.println(" ijt ins="+ins);
//                traveProp(ins);
//                Object ins2=  PicoContainer888.getComponent(aClass);
//                return null;
//            }
//        };
//        scanAllClass(fun);

    }

    private static void setFld(Object ins, Field field, Object component) throws NoSuchFieldException, IllegalAccessException {
        Class TargetClass= ins.getClass();
        VarHandle fieldHandle = MethodHandles.lookup()
                .in(TargetClass)
                .findVarHandle(TargetClass, field.getName(), field.getType());

        fieldHandle.set(ins, component);
    }

    private static void traveProp(Object ins) {

        if (ins == null) return;

        Class<?> clazz = ins.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // 允许访问私有字段
          //  if(field.getType()== AddRchgOrdToWltService.class)
                System.out.println("d951");
            System.out.println("trav prop="+field);
            try {
                if (field.get(ins) == null) { // 如果字段值为 null
//                    Object component = PicoContainer888.getComponent(field.getType());
//                    if (component != null) {
//                        System.out.println("setprop as:"+component);
//                        field.set(ins, component);
//                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to inject property: " + field.getName(), e);
            }
        }
    }

    public static <T> T getBean2025(Class<T> clazz) {
        return (T) AtIoc_context.get(clazz.getName()); // 获取时使用泛型返回指定类型
    }
    // 使用泛型明确声明每个类和对应类型
    public static <T> T getComponent(Class<T> clazz) {
        return (T) AtIoc_context.get(clazz.getName()); // 获取时使用泛型返回指定类型
    }
    private static Object getInstsWzSessfac(Class<?> modifiedClass) throws  Exception {

     //   Class<?> modifiedClass = getAClassExted(modifiedClass);
        Object instance ;
        SessionFactory sessionFactory=getComponent(SessionFactory.class);
        try{
            instance= modifiedClass.getConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            // 没有无参构造函数
            Constructor<?> constructor = modifiedClass.getConstructor(SessionFactory.class); // 指定参数类型
            instance = constructor.newInstance( sessionFactory);

            //other dync fore
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//            setField(instance,"session",new SessionProvider().provide());
        //new SessionProvider().provide()
        setField(instance, SessionFactory.class, sessionFactory );
        System.out.println("inst="+instance);
        System.out.println("insts.sessFctry="+getField(instance,"sessionFactory"));

        //这里根据参数，生成参数类型
//        Class<?>[] parameterTypes = Arrays.stream(prm).map(Object::getClass).toArray(Class<?>[]::new);
//        Method method = modifiedClass.getMethod(methodName, parameterTypes);
        setField(instance, "sessionFactory", sessionFactory );
        return instance;
    }


}
