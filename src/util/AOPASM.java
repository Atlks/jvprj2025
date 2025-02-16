package util;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import  java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


import static org.objectweb.asm.Opcodes.*;
import static util.CustomClassLoader.defineClassX;

public class AOPASM {
    public static void main(String[] args) throws Exception {
        // 目标类的全限定类名（原始类）
//        Class<?> targetClassClass = TargetClass.class;
//        String mthName = "sayHello";

        //invk(targetClassClass, mthName);
    }

    public static Object createProxy(Class<?> targetClassClass) throws Exception {
        // **如果是接口，直接返回 null**
        if (targetClassClass.isInterface()) {
            return null;
        }

        Class<?> modifiedClass = defineClassX(targetClassClass);

        Object instance = getObject(modifiedClass);
        return instance;

        //        Object instance = modifiedClass.getDeclaredConstructor().newInstance();
    }

    @NotNull
    public static Object getObject(Class<?> modifiedClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> constructor = modifiedClass.getDeclaredConstructor();
        constructor.setAccessible(true); // 允许访问私有或默认构造器

        Object instance = constructor.newInstance();
        return instance;
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

    /// /        Object instance = modifiedClass.getDeclaredConstructor().newInstance();
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
        ClassVisitor classVisitor1 = new ClassVisitor(ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

                if (name.equals("reg4bz")) {
                    System.out.println("Dbg");
                    //  (LapiUsr/Usr;)Ljava/lang/String;
                    //说明 参数是usr,fanhuizhi str
                }
                MethodVisitor MethodVisitor_superVM = super.visitMethod(access, name, descriptor, signature, exceptions);

                if(name.equals("handle") ||name.equals("handle2")  )
                    return  MethodVisitor_superVM;

                // 过滤掉构造方法和 Object 的方法
                if (!name.equals("<init>") && !name.equals("<clinit>") && !isObjectMethod(name)) {
                    MethodVisitor methodVisitorAddLog = getMethodVisitor4addlog(access, name, descriptor, MethodVisitor_superVM);
                    return methodVisitorAddLog;
                }

                //else  ret ori mtod
                return MethodVisitor_superVM;
            }



        };
        cr.accept(classVisitor1, 0);
        return cw.toByteArray();
    }


    @NotNull
    public static MethodVisitor getMethodVisitor4addlog(int access, String name, String descriptor, MethodVisitor MethodVisitor8) {
        MethodVisitor methodVisitorAddLog = new MethodVisitor(ASM9, MethodVisitor8) {
            @Override
            public void visitCode() {

                System.out.println(" fun vist code()");
                System.out.println("name=" + name);
                System.out.println("emthd descriptor=" + descriptor);
                // 方法执行前：System.out.println("Method " + name + " start");
                MethodVisitor8.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                MethodVisitor8.visitLdcInsn("!fun " + name + " (");

                // 处理参数（拼接字符串） argumentTypes=usr is ok
                Type[] argumentTypes = Type.getArgumentTypes(descriptor);

                // 计算参数起始索引
                boolean isStatic = (access & Opcodes.ACC_STATIC) != 0;
                int paramIndex = isStatic ? 0 : 1;  // 非静态方法参数从1开始，静态方法从0开始

                for (int i = 0; i < argumentTypes.length; i++) {
                    Type type = argumentTypes[i];

                    if (type.getSort() >= Type.BOOLEAN && type.getSort() <= Type.DOUBLE) {
                        // 处理基本类型（int, boolean, long, double等）
                        boxPrimitive(type, paramIndex, MethodVisitor8);
                    } else {
                        // 处理对象类型
                        MethodVisitor8.visitVarInsn(ALOAD, paramIndex);
                    }

                    // 调用 toString() 转换为字符串
                //    MethodVisitor8.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "toString", "()Ljava/lang/String;", false);
                    // 调用 encodeJson() 代替 toString()
                    //  MethodVisitor8.visitMethodInsn(INVOKESTATIC, "你的类的全限定名",   "encodeJson"
                    MethodVisitor8.visitMethodInsn(INVOKESTATIC, "util/Util2025", "encodeJson", "(Ljava/lang/Object;)Ljava/lang/String;", false);

                    // 追加到日志字符串
                    MethodVisitor8.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "concat", "(Ljava/lang/String;)Ljava/lang/String;", false);

                    paramIndex += type.getSize(); // long 和 double 需要占2个槽
                }


                MethodVisitor8.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                System.out.println(" endfun vist code()");
                super.visitCode();
            }

            // 输出函数返回值 ，  返回值 用 encodeJson编码，方便输出对象
            //输出格式为  endfun methodName().ret=encodejson(returnVal)
            @Override

            public void visitInsn(int opcode) {
                // 在方法返回之前插入日志（但不影响 return 语句）
                if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
                    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

                    // 处理非 void 方法的返回值
                    if (opcode != RETURN) { // 说明方法有返回值
                        // 复制返回值到栈顶，防止被消费（因为 `encodeJson` 调用后，原值会丢失）
                        mv.visitInsn(DUP);

                        // 调用 encodeJson 进行序列化
                        mv.visitMethodInsn(INVOKESTATIC, "util/Util2025", "encodeJson", "(Ljava/lang/Object;)Ljava/lang/String;", false);
                    } else {
                        // void 方法，直接使用 "null" 作为返回值
                        mv.visitLdcInsn("null");
                    }

                    // 先加载日志前缀 `!!endfun 方法名().ret= `
                    mv.visitLdcInsn("!!endfun " + name + "().ret= ");

                    // 交换栈顶顺序，确保 `!!endfun 方法名().ret= ` 先拼接，返回值在后
                    mv.visitInsn(SWAP);

                    // 拼接字符串
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "concat", "(Ljava/lang/String;)Ljava/lang/String;", false);

                    // 输出日志
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                }
                super.visitInsn(opcode);
            }



        };
        return methodVisitorAddLog;
    }

    // 过滤 Object 类的方法
    public static boolean isObjectMethod(String name) {
        return name.equals("toString") || name.equals("equals") || name.equals("hashCode") ||
                name.equals("getClass") || name.equals("notify") || name.equals("notifyAll") || name.equals("wait");
    }

    /**
     * 处理基本类型的装箱 (Boxing primitive types to Object)
     */
    private static void boxPrimitive(Type type, int index, MethodVisitor mv) {
        switch (type.getSort()) {
            case Type.INT:
                mv.visitVarInsn(ILOAD, index);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                break;
            case Type.BOOLEAN:
                mv.visitVarInsn(ILOAD, index);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
                break;
            case Type.LONG:
                mv.visitVarInsn(LLOAD, index);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                break;
            case Type.DOUBLE:
                mv.visitVarInsn(DLOAD, index);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                break;
            case Type.FLOAT:
                mv.visitVarInsn(FLOAD, index);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
                break;
            case Type.CHAR:
                mv.visitVarInsn(ILOAD, index);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
                break;
            case Type.BYTE:
                mv.visitVarInsn(ILOAD, index);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
                break;
            case Type.SHORT:
                mv.visitVarInsn(ILOAD, index);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
                break;
        }

    }
}

// 目标类（用于测试）
//  class TargetClass {
//    public TargetClass() {}  // 让构造方法可访问
//    public void sayHello() {
//        System.out.println("Hello, ASM!");
//    }
//}

