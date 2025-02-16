package util;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import static util.AOPASM.modifyClass;

// 自定义类加载器
public class CustomClassLoader extends ClassLoader {

    public CustomClassLoader(ClassLoader parent) {
        super(parent); // 继承当前 ClassLoader
    }

    public static Map<String, Class<?>> classMap = new HashMap<>();

    /**
     * 关键改动：
     * <p>
     * 只有修改过的类才会用 defineClass 加载，其他类都交给 原 ClassLoader 加载。
     * 避免 ClassCastException，因为 只有 RegHandler 发生了字节码变更。
     *
     * @param name
     * @param bytes
     * @return
     */
    public Class<?> defineClass(String name, byte[] bytes) {
        // **检查是否已加载**
//            try {
//                return loadClass(name);
//            } catch (ClassNotFoundException e) {

        try {
            // 只有找不到时，才真正定义
            var cls = classMap.get(name);
            if (cls == null) {
                Class<?> aClass = defineClass(name, bytes, 0, bytes.length);
                classMap.put(name, aClass);
                return aClass;
            } else
                return cls;

        } catch (Throwable ex) {
            //   e.printStackTrace();
        }

        //   }

//            if (bytes != null) {
//                return defineClass(name, bytes, 0, bytes.length);
//            } else {
//                try {
//                    return super.loadClass(name); // 复用原来的类
//                } catch (ClassNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            try {
//                return loadClass(name);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
        return null;
    }
//            return defineClass(name, b, 0, b.length);
//        }


    /**
     * // 如果是目标类，我们用自定义加载逻辑
     *
     * @param name    The <a href="#binary-name">binary name</a> of the class
     * @param resolve If {@code true} then resolve the class
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // 让 JDK 类和第三方库都由系统加载
        if (name.startsWith("java.") || name.startsWith("jdk.")) {
            return super.loadClass(name, resolve);
        }
//        if (name.startsWith("com.sun.") || name.startsWith("jdk.")) {
//            return super.loadClass(name, resolve);
//        }
        var cls = classMap.get(name);
        if (cls == null) {
            return super.loadClass(name, resolve);
        } else {
            return classMap.get(name);
        }
// 如果是目标类，我们用自定义加载逻辑
        //    return  defineClass()

    }

    public static  CustomClassLoader customClassLoader;
    public static Class<?> defineClassX(Class<?> targetClassClass) throws Exception {
        String className = targetClassClass.getName();
        System.out.println("fun loadclassx(clas="+targetClassClass);
        // 1. 读取字节码并修改
        byte[] modifiedClassBytes = modifyClass(className);

        // 2. 写入新字节码（可选，方便调试）
        File file = new File("TargetClassModified.class");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(modifiedClassBytes);
        }


        ClassLoader parentClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("targetClassClass currentThread Classlodr="+parentClassLoader);
        ClassLoader parentClassLoader2 = targetClassClass.getClassLoader(); // 用目标类的 ClassLoader
        System.out.println("targetClassClass   Classlodr="+parentClassLoader);
        if(customClassLoader==null){
            customClassLoader = new CustomClassLoader(parentClassLoader);
        }

        Class<?> modifiedClass = customClassLoader.defineClass(className, modifiedClassBytes);
        return modifiedClass;
    }


    @NotNull
    public Class<?> loadClassx(@NotNull String name) {
        return classMap.get(name);
    }
//        @Override
//        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
//            // 只加载修改过的类，其他类全部交给父 `ClassLoader`
//            return super.loadClass(name, resolve);
//        }
}
