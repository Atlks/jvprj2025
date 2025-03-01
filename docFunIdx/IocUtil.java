package util;

import apiUsr.LoginHdr;
import apiUsr.RegHandler;
import apis.BaseHdr;
import org.jetbrains.annotations.NotNull;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.injectors.ConstructorInjection;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
//PicoContainer more easy thena guice lite,guice ,spring
public class IocUtil {
    public static MutablePicoContainer container888 = new DefaultPicoContainer();

    @NotNull
    public static MutablePicoContainer iniIocContainr() {
        BaseHdr.iniCfgFrmCfgfile();

//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        // **使用 Provider，每次获取都是新的 `Session`**
        container888.addAdapter(new SessionProvider());
        // 注册组件
     //   container888.addComponent(RegHandler.class);
    //    container888.addComponent(LoginHdr.class);
        scanAllClass();
        return container888;
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
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void scanClasses(File dir, String basePath, List<Class<?>> classList) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanClasses(file, basePath, classList);
            } else if (file.getName().endsWith(".class")) {
                String className = file.getAbsolutePath()
                        .replace(basePath, "")
                        .replace(".class", "")
                        .replace(File.separator, ".");
                if (className.startsWith(".")) {
                    className = className.substring(1);
                }
                try {
                    Class<?> clazz = Class.forName(className);
                    classList.add(clazz);
                } catch (ClassNotFoundException e) {
                    System.err.println("加载失败: " + className);
                } catch (Throwable e) {
                    System.err.println("加载失败: " + className);
                }
            }
        }
    }

}
