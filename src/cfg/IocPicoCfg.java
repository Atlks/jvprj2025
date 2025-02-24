package cfg;

import service.WltService;
import biz.BaseHdr;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static biz.BaseHdr.saveDirUsrs;
import static org.picocontainer.Characteristics.CACHE;
import static cfg.AopLogJavassist.getAClassAoped;
import static util.HbntUtil.getSessionFactory;
import static util.util2026.scanClasses;

//PicoContainer more easy thena guice lite,guice ,spring
public class IocPicoCfg {
    public static MutablePicoContainer PicoContainer888 = new DefaultPicoContainer();

    @NotNull
    public static MutablePicoContainer iniIocContainr() throws SQLException {
        BaseHdr.iniCfgFrmCfgfile();

//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        // **使用 Provider，每次获取都是新的 `Session`**
     //   container888.addAdapter(new SessionProvider());

        List<Class> li = List.of();
        BaseHdr.iniCfgFrmCfgfile();
        SessionFactory sessionFactory = getSessionFactory(saveDirUsrs, li);
       // container888.addAdapter(new SessionFactProvider());
        // 注册组件
        PicoContainer888.addComponent(sessionFactory);
    //    container888.addComponent(LoginHdr.class);
        scanAllClass();//  all add class  ...  mdfyed class btr
        injectAll();
        return PicoContainer888;
    }

    private static void injectAll() {
        Function<Class,Object> fun=new Function<Class, Object>() {
            @Override
            public Object apply(Class aClass) {
              Object ins=  PicoContainer888.getComponent(aClass);
                System.out.println(" ijt ins="+ins);
              traveProp(ins);
                Object ins2=  PicoContainer888.getComponent(aClass);
                return null;
            }
        };
        scanAllClass(fun);

    }

    //遍历对象属性，如果为null，则设置为PicoContainer.getComponent(属性类型.class)
    private static void traveProp(Object ins) {

        if (ins == null) return;

        Class<?> clazz = ins.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // 允许访问私有字段
            if(field.getType()== WltService.class)
                System.out.println("d951");
            System.out.println("trav prop="+field);
            try {
                if (field.get(ins) == null) { // 如果字段值为 null
                    Object component = PicoContainer888.getComponent(field.getType());
                    if (component != null) {
                        System.out.println("setprop as:"+component);
                        field.set(ins, component);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to inject property: " + field.getName(), e);
            }
        }
    }

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
                    if(clazz.getName().startsWith("entityx."))
                        continue;
                    if(clazz.getName().startsWith("test."))
                        continue;
                    Class<?> modifiedClass = getAClassAoped(clazz);
                    PicoContainer888.as(CACHE).addComponent(modifiedClass);
                    System.out.println("pico已注册: " + clazz.getName());
                } catch (Exception e) {
                    System.err.println("pico注册失败: " + clazz.getName());
                    System.err.println("pico注册失败msg: " + e.getMessage());
                  //  System.err.println("注册失败: " + clazz.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
