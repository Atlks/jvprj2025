package util;

import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import  java.lang.reflect.Constructor;


import static org.objectweb.asm.Opcodes.*;

public class AOPASM {
    public static void main(String[] args) throws Exception {
        // 目标类的全限定类名（原始类）
        Class<?> targetClassClass = TargetClass.class;
        String mthName = "sayHello";

        //invk(targetClassClass, mthName);
    }

    public static Object createProxy(Class<?> targetClassClass ) throws Exception {
        // **如果是接口，直接返回 null**
        if (targetClassClass.isInterface()) {
            return null;
        }

        Class<?> modifiedClass = getClassMdfed(targetClassClass);

        Constructor<?> constructor = modifiedClass.getDeclaredConstructor();
        constructor.setAccessible(true); // 允许访问私有或默认构造器

        Object instance = constructor.newInstance();
        return  instance;

        //        Object instance = modifiedClass.getDeclaredConstructor().newInstance();
    }
    public static  CustomClassLoader customClassLoader;
    public static Class<?> getClassMdfed(Class<?> targetClassClass) throws Exception {
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

//    @Deprecated
//    public static void invk(Class<?> targetClassClass, String mthName,Object... objs) throws Exception {
//        String className = targetClassClass.getName();
//        // 1. 读取字节码并修改
//        byte[] modifiedClassBytes = modifyClass(className);
//
//        // 2. 写入新字节码（可选，方便调试）
//        File file = new File("TargetClassModified.class");
//        try (FileOutputStream fos = new FileOutputStream(file)) {
//            fos.write(modifiedClassBytes);
//        }
//
//        // 3. 加载修改后的类
//        Class<?> modifiedClass = new CustomClassLoader().defineClass(className, modifiedClassBytes);
//
//        Constructor<?> constructor = modifiedClass.getDeclaredConstructor();
//        constructor.setAccessible(true); // 允许访问私有或默认构造器
////        Object instance = modifiedClass.getDeclaredConstructor().newInstance();
//        Object instance =constructor.newInstance();
//
//        // 4. 通过反射调用方法，验证是否打印日志
//        // Get the parameter types of the method (to match with the method signature)
//        Class<?>[] paramTypes = new Class<?>[objs.length];
//        for (int i = 0; i < objs.length; i++) {
//            paramTypes[i] = objs[i] == null ? null : objs[i].getClass();
//        }
//        Method method = modifiedClass.getMethod(mthName,paramTypes);
//        method.setAccessible(true);  // 显式允许访问
//        method.invoke(instance,objs);
//    }

    // 修改目标类的字节码，添加 AOP 日志
    public static byte[] modifyClass(String className) throws Exception {
        ClassReader cr = new ClassReader(className);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

                // 过滤掉构造方法和 Object 的方法
                if (!name.equals("<init>") && !name.equals("<clinit>") && !isObjectMethod(name)) {
                    return new MethodVisitor(ASM9, mv) {
                        @Override
                        public void visitCode() {
                            // 方法执行前：System.out.println("Method " + name + " start");
                            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                            mv.visitLdcInsn("!fun " + name + " ()");
                            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

                            super.visitCode();
                        }

                        @Override
                        public void visitInsn(int opcode) {
                            // 在方法返回之前插入日志（但不影响 return 语句）
                            if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
                                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                                mv.visitLdcInsn("!!endfun " + name + " ");
                                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                            }
                            super.visitInsn(opcode);
                        }
                    };
                }
                return mv;
            }
        };
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    // 过滤 Object 类的方法
    private static boolean isObjectMethod(String name) {
        return name.equals("toString") || name.equals("equals") || name.equals("hashCode") ||
                name.equals("getClass") || name.equals("notify") || name.equals("notifyAll") || name.equals("wait");
    }

    // 自定义类加载器
    static class CustomClassLoader extends ClassLoader {

        public CustomClassLoader(ClassLoader parent) {
            super(parent); // 继承当前 ClassLoader
        }

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
            try {
                return loadClass(name);
            } catch (ClassNotFoundException e) {
                // 只有找不到时，才真正定义
                try{
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (Throwable ex) {
                 //  e.printStackTrace();
                }

            }

//            if (bytes != null) {
//                return defineClass(name, bytes, 0, bytes.length);
//            } else {
//                try {
//                    return super.loadClass(name); // 复用原来的类
//                } catch (ClassNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//            }
            try {
                return loadClass(name);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
//            return defineClass(name, b, 0, b.length);
//        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            // 让 JDK 类和第三方库都由系统加载
            if (name.startsWith("java.") || name.startsWith("jdk.")) {
                return super.loadClass(name, resolve);
            }
            if (name.startsWith("com.sun.") || name.startsWith("jdk.")) {
                return super.loadClass(name, resolve);
            }


            return findClass(name);
        }
//        @Override
//        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
//            // 只加载修改过的类，其他类全部交给父 `ClassLoader`
//            return super.loadClass(name, resolve);
//        }
    }
}

// 目标类（用于测试）
  class TargetClass {
    public TargetClass() {}  // 让构造方法可访问
    public void sayHello() {
        System.out.println("Hello, ASM!");
    }
}

