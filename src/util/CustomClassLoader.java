package util;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

// 自定义类加载器
public class CustomClassLoader extends ClassLoader {

    public CustomClassLoader(ClassLoader parent) {
        super(parent); // 继承当前 ClassLoader
    }
    public static Map<String, Class<?>> classMap = new HashMap<>();

    /**
     *  关键改动：
     *
     * 只有修改过的类才会用 defineClass 加载，其他类都交给 原 ClassLoader 加载。
     * 避免 ClassCastException，因为 只有 RegHandler 发生了字节码变更。
     * @param name
     * @param bytes
     * @return
     */
    public Class<?> defineClass(String name, byte[] bytes) {
        // **检查是否已加载**
//            try {
//                return loadClass(name);
//            } catch (ClassNotFoundException e) {
        // 只有找不到时，才真正定义
        try{
            Class<?> aClass = defineClass(name, bytes, 0, bytes.length);
            classMap.put(name,aClass);
            return aClass;
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
     * @param name
     *          The <a href="#binary-name">binary name</a> of the class
     *
     * @param resolve
     *          If {@code true} then resolve the class
     *
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // 让 JDK 类和第三方库都由系统加载
        if (name.startsWith("java.") || name.startsWith("jdk.")) {
            return super.loadClass(name, resolve);
        }
        if (name.startsWith("com.sun.") || name.startsWith("jdk.")) {
            return super.loadClass(name, resolve);
        }

// 如果是目标类，我们用自定义加载逻辑
    //    return  defineClass()
        return findClass(name);
    }

    @NotNull
    public Class<?> loadClassx(@NotNull String name) {
        return     classMap.get(name);
    }
//        @Override
//        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
//            // 只加载修改过的类，其他类全部交给父 `ClassLoader`
//            return super.loadClass(name, resolve);
//        }
}
