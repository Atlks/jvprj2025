package util;

import apiUsr.RegHandler;
import apis.BaseHdr;
import com.sun.net.httpserver.HttpExchange;
import javassist.*;
import org.jetbrains.annotations.NotNull;
import test.MyClassLoader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static util.ColorLogger.*;

public class AopLogJavassist {
    public static void main(String[] args) throws Exception {
        //  System.out.println(RegHandler.class);
        Class<?> aClass = RegHandler.class;

        Class<?> modifiedClass = getAClassExted(aClass);


        BaseHdr.iniCfgFrmCfgfile();
        //  StaticMethodAOP. enhanceClass(RegHandler::class.toString());
        HttpExchange he =
                new HttpExchangeImp("http://localhost:8889/reg?uname=qq1&pwd=ppp", "uname=0091", "output2025.txt");


        Object instance = modifiedClass.getDeclaredConstructor().newInstance();

        Method method = modifiedClass.getMethod("handle", HttpExchange.class);
        method.invoke(instance, he);

//cannot be cast to class ,dif cls lddr..
//        RegHandler service = (RegHandler) modifiedClass.getDeclaredConstructor().newInstance();
//            service.handle(he);


    }

    public static void printLn(String msg) {
        synchronized (lock) {
            PrintWriter writer = new PrintWriter(System.out, true);  // 自动刷新

            writer.println(msg);
            System.out.flush();  // 刷新输出缓冲区
            System.err.flush();  // 刷新输出缓冲区
        }
    }

    public static Class<?> getAClassExted(Class<?> aClass) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        synchronized (lock) {


            printLn("getClassExtd(cls=" + aClass);

            ClassPool pool = ClassPool.getDefault();

//        if (pool.find("apiUsr.RegHandler") == null) {
//            throw new RuntimeException("Class RegHandler not found in classpath.");
//        }


            CtClass ctClass = pool.get(aClass.getName());

           if(aClass.getName().contains("TransHdr"))
               System.out.println("D909");
            CtMethod[] ctMthdArr = ctClass.getMethods();
            for (CtMethod ctMethod : ctMthdArr) {
                // ----------过滤掉继承的obj方法，只处理当前类的方法
                String methodName = ctMethod.getName();

                if (isObjectMethod(methodName))
                    continue;
                ;

                if (isObjectMethodEx(methodName))
                    continue;
                ;
                printLn("ex mth=" + methodName);

                // 过滤 abstract 和 native 方法
                if ((ctMethod.getModifiers() & Modifier.ABSTRACT) != 0 ||
                        (ctMethod.getModifiers() & Modifier.NATIVE) != 0) {
                    //  System.out.println("Skipping method: " + methodName);
                    continue;
                }
                //------------- 在方法前后插入日志
//            ctMethod.insertBefore("System.out.println(\"!fun " + methodName + "()\");");

                // 在方法前后插入日志，调用 encodeJson 方法
                String mth = "▶\uFE0F" + colorStr(methodName, YELLOW_bright);
                String logCode =
                        "{ " +
                                "  String jsonArgs = util.Util2025.encodeJsonV2($args); " + // 调用封装的 encodeJson 方法
                                "  System.out.println(" +
                                "\"" +
                                "funm " + mth + "(), " +
                                "args=" +
                                "\" " +
                                "+ util.ColorLogger.colorStr(jsonArgs,util.ColorLogger.GREEN));" +
                                "}";

                ctMethod.insertBefore(logCode);

                //------------- 在方法后插入日志
                // System.out.println(encodeJson(true));
                // 方法返回值序列化日志
                String mth_end = "✅" + methodName;
                String returnSerializationCode =
                        "{ " +
                                "  String jsonRet = util.Util2025.encodeJson4dbgShowVal($_); " +  // 调用自定义方法
                                "  System.out.println(\"endfunx " + mth_end + "(), return:\" + jsonRet);" +
                                "}";


                ctMethod.insertAfter(returnSerializationCode, true);  // `true` 让 `$_` 代表返回值

                // ctMethod.   setAccessible(true);


            }

            if(aClass.getName().contains("TransHdr"))
                System.out.println("D909");


            // 判断是否已经实现了 Serializable
          //  srlzCtCls(ctClass, pool);

            //  ctClass.addInterface(pool.get("java.io.Serializable")); // 让它支持序列化            // 获取修改后的字节码
            byte[] bytecode = ctClass.toBytecode();
            // 手动释放 CtClass
            //  ctClass.detach();

            //     ---------使用自定义类加载器加载字节码
//            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
             MyClassLoader myClassLoader = new MyClassLoader();
             //but here cls ldr also mycls ldr
            Class<?> modifiedClass = myClassLoader.defineClassFromByteArray(aClass.getName(), bytecode);
//
//            ClassLoader classLoader1 = modifiedClass.getClassLoader();
//         //mycls ldr
//            System.out.println(classLoader1);//test.MyClassLoader@2bd7d4d1


//            Class<?> modifiedClass = classLoader.defineClass(aClass.getName(), bytecode);

           // return    modifiedClass;

            Class<?> modifiedClass2 = getAClassInCurClasLdr(modifiedClass);
            return modifiedClass2;


//
////        if(aClass.getName().contains("ReChargeComplete"))
////              System.exit(0);
//        return modifiedClass;
            // System.out.println(message);
        }
    }

    //  这里没有增加  声明 serialVersionUID
    private static void srlzCtCls(CtClass ctClass, ClassPool pool) throws NotFoundException, CannotCompileException {
        boolean isSerializable = false;
        for (CtClass iface : ctClass.getInterfaces()) {
            if (iface.getName().equals("java.io.Serializable")) {
                isSerializable = true;
                break;
            }
        }

        // 如果未实现，则添加 Serializable 接口
        if (!isSerializable) {
          //  ctClass.addInterface(serializable);
            ctClass.addInterface(pool.get("java.io.Serializable"));
            System.out.println(ctClass.getName() + " 已添加 Serializable 接口");
        } else {
            System.out.println(ctClass.getName() + " 已经实现 Serializable，无需添加");
        }


        // **保持旧的 serialVersionUID**
        long serialVersionUIDValue = 9164029442537620054L; // 这里使用旧的值
        try {
            CtField uidField = ctClass.getDeclaredField("serialVersionUID");
            System.out.println("已有 serialVersionUID: " + uidField);
        } catch (NotFoundException e) {
            CtField serialVersionUID = new CtField(CtClass.longType, "serialVersionUID", ctClass);
            serialVersionUID.setModifiers(Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL);
            ctClass.addField(serialVersionUID, CtField.Initializer.constant(serialVersionUIDValue)); // 设定固定值
            System.out.println(ctClass.getName() + " 已添加 serialVersionUID = " + serialVersionUIDValue);
        }
    }

    @NotNull
    private static Class<?> getAClassInCurClasLdr(Class<?> modifiedClass) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, ClassNotFoundException {
        Object instance = modifiedClass.getConstructor().newInstance();
        // 反序列化到当前 ClassLoader
        ByteArrayInputStream bis = new ByteArrayInputStream(serialize(instance));
        ObjectInputStream in = new ObjectInputStream(bis);

        //为什么这里报错了
        Object deserializedInstance = in.readObject();
        Class<?> modifiedClass2 = deserializedInstance.getClass();
        return modifiedClass2;
    }

    // **序列化对象**
    private static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        return bos.toByteArray();
    }

    // **反序列化对象**
    private static Object deserialize(byte[] data, ClassLoader classLoader) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream in = new ObjectInputStream(bis) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                return Class.forName(desc.getName(), true, classLoader);
            }
        };
        return in.readObject();
    }

    public static final Object lock = new Object();

    private static boolean isObjectMethodEx(String name) {
        return name.equals("finalize") || name.equals("clone") || name.equals("setSessionFactory") ||
                name.equals("main") || name.equals("handle") || name.equals("notifyAll") || name.equals("wait");
    }

    // 过滤 Object 类的方法
    public static boolean isObjectMethod(String name) {
        return name.equals("toString") || name.equals("equals") || name.equals("hashCode") ||
                name.equals("getClass") || name.equals("notify") || name.equals("notifyAll") || name.equals("wait");
    }
}

class MyService {
    public void doSomething(String message) {
        System.out.println("Doing: " + message);
    }
}
