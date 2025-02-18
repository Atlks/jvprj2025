package cfg;

import apis.BaseHdr;
import org.jetbrains.annotations.NotNull;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import util.SessionFactProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static util.util2026.scanClasses;

//PicoContainer more easy thena guice lite,guice ,spring
public class IocPicoCfg {
    public static MutablePicoContainer container888 = new DefaultPicoContainer();

    @NotNull
    public static MutablePicoContainer iniIocContainr() {
        BaseHdr.iniCfgFrmCfgfile();

//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        // **使用 Provider，每次获取都是新的 `Session`**
     //   container888.addAdapter(new SessionProvider());
        container888.addAdapter(new SessionFactProvider());
        // 注册组件
     //   container888.addComponent(RegHandler.class);
    //    container888.addComponent(LoginHdr.class);
        scanAllClass();
        return container888;
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
                    container888.addComponent(clazz);
                    System.out.println("已注册: " + clazz.getName());
                } catch (Exception e) {
                    System.err.println("注册失败: " + clazz.getName());
                    System.err.println("注册失败msg: " + e.getMessage());
                  //  System.err.println("注册失败: " + clazz.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
