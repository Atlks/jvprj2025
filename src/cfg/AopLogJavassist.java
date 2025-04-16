package cfg;

import api.usr.RegHandler;

import com.sun.net.httpserver.HttpExchange;
import javassist.*;

import util.misc.HttpExchangeImp;
import util.proxy.MyClassLoader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static util.log.ColorLogger.*;
import static util.misc.util2026.*;

public class AopLogJavassist {
    public static void main(String[] args) throws Exception {
        //  System.out.println(RegHandler.class);
        Class<?> aClass = RegHandler.class;

        Class<?> modifiedClass = getAClassAoped(aClass);


        MyCfg.iniContnr4cfgfile();
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




    //----aop ex


    /**  aop  log
     * 这个确定可以修改了class
     *
     * @param aClass
     * @return
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Class<?> getAClassAoped(Class<?> aClass) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // synchronized (lock)
        {

            //   Class.toString() 只是返回类的字符串表示，格式类似于：这个方法 不会触发类的静态初始化，因为它只是访问 Class 对象的元信息。
            printLn("getClassExtd(cls=" + aClass.getName());

            ClassPool pool = ClassPool.getDefault();

//        if (pool.find("api.usr.RegHandler") == null) {
//            throw new RuntimeException("Class RegHandler not found in classpath.");
//        }


            CtClass ctClass = pool.get(aClass.getName());

            if (aClass.getName().contains("AddMoneyToWltService"))
                System.out.println("D909");
            CtMethod[] ctMthdArr = ctClass.getDeclaredMethods();
            for (CtMethod ctMethod : ctMthdArr) {
                // ----------过滤掉继承的obj方法，只处理当前类的方法
                String methodNameFullname =aClass.getName()+"."+ ctMethod.getName();
                String methodName=ctMethod.getName();
                if (isObjectMethod(methodName))
                    continue;
                ;

                if (isObjectMethodEx(methodName))
                    continue;
                ;


                // 过滤 abstract 和 native 方法
                if ((ctMethod.getModifiers() & Modifier.ABSTRACT) != 0 ||
                        (ctMethod.getModifiers() & Modifier.NATIVE) != 0) {
                    //  System.out.println("Skipping method: " + methodName);
                    continue;
                }

                printLn("ex mth=" + methodName);
                //------------- 在方法前后插入日志
//            ctMethod.insertBefore("System.out.println(\"!fun " + methodName + "()\");");

                // 在方法前后插入日志，调用 encodeJson 方法
                String mth = "▶\uFE0F" + colorStr(methodNameFullname, YELLOW_bright);
                String logCode =
                        "{ " +
                                "  String jsonArgs = util.Util2025.encodeJsonV2($args); " + // 调用封装的 encodeJson 方法
                                "  System.out.println(" +
                                "\"" +
                                "funm " + mth + "(), " +
                                "args=" +
                                "\" " +
                                "+ util.log.ColorLogger.colorStr(jsonArgs,util.log.ColorLogger.GREEN));" +
                                "}";

                ctMethod.insertBefore(logCode);

                //------------- 在方法后插入日志
                // System.out.println(encodeJson(true));
                // 方法返回值序列化日志
                String mth_end = "✅" + methodNameFullname;
                String returnSerializationCode =
                        "{ " +
                                "  String jsonRet = util.Util2025.encodeJson4dbgShowVal($_); " +  // 调用自定义方法
                                "  System.out.println(\"endfunx " + mth_end + "(), return:\" + jsonRet);" +
                                "}";


                ctMethod.insertAfter(returnSerializationCode, true);  // `true` 让 `$_` 代表返回值

                // ctMethod.   setAccessible(true);


            }

            if (aClass.getName().contains("TransHdr"))
                System.out.println("D909");


            // 判断是否已经实现了 Serializable
            //  srlzCtCls(ctClass, pool);

            //  ctClass.addInterface(pool.get("java.io.Serializable"));


            // =======================让它支持序列化            // 获取修改后的字节码
            byte[] bytecode = ctClass.toBytecode();
            // 手动释放 CtClass
            ctClass.detach();

            //     ---------使用自定义类加载器加载字节码
//            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            MyClassLoader myClassLoader = new MyClassLoader();
            //but here cls ldr also mycls ldr
            Class<?> modifiedClass = null;
            //= myClassLoader.defineClassFromByteArray(aClass.getName(), bytecode);

            //cfm mdfy class,,if now ret ,ivk by itfs,its ok..take effk

            //  Class<?> modifiedClass2 = getAClassInCurClasLdr(modifiedClass);
            return modifiedClass;


//
////        if(aClass.getName().contains("ReChargeComplete"))
////              System.exit(0);
//        return modifiedClass;
            // System.out.println(message);
        }
    }


//            ClassLoader classLoader1 = modifiedClass.getClassLoader();
//         //mycls ldr
//            System.out.println(classLoader1);//test.MyClassLoader@2bd7d4d1


//            Class<?> modifiedClass = classLoader.defineClass(aClass.getName(), bytecode);

    // return    modifiedClass;



//    @NotNull
//    private static Class<?> getAClassInCurClasLdr(Class<?> modifiedClass) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, ClassNotFoundException {
//        Object instance = modifiedClass.getConstructor().newInstance();
//        // 反序列化到当前 ClassLoader
//        byte[] serializeData = serialize(instance);
//
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        System.out.println(classLoader);
//
//
//        //为什么这里报错了  meiyh  srz id..
//        Object deserializedInstance = deserialize(serializeData, classLoader);
//        Class<?> modifiedClass2 = deserializedInstance.getClass();
//        return modifiedClass2;
//    }



    public static final Object lock = new Object();

    static boolean isObjectMethodEx(String name) {
        return name.equals("finalize") || name.equals("clone") || name.equals("setSessionFactory") ||
                name.equals("main") || name.equals("handle") || name.equals("handle2") || name.equals("handle3");
    }

    // 过滤 Object 类的方法
    public static boolean isObjectMethod(String name) {
        return name.equals("toString") || name.equals("equals") || name.equals("hashCode") ||
                name.equals("getClass") || name.equals("notify") || name.equals("notifyAll") || name.equals("wait");
    }
}

//class MyService {
//    public void doSomething(String message) {
//        System.out.println("Doing: " + message);
//    }
//}


//    private void aopEXhandler(HttpExchange exchange, Method method, Object[] args)   {
//        ExceptionBase ex = new ExceptionBase("");
//        try {
//            curUrl.set(String.valueOf((exchange.getRequestURI())));
//            //===================log chk
//       //     urlAuthChk(exchange);
//
//            //=========auth chk pass
//            Session session = sessionFactory.getCurrentSession();
//            session.beginTransaction();
//
//            Object target = null;
//            method.invoke(target, args); // 调用目标方法
//
//            commitTransaction(session);
//        } catch (InvocationTargetException e) {
//
//            ex = new ExceptionBase(e.getMessage());
//            ex.cause = e;
//            Throwable cause = e.getCause();
//
//            ex.errcode = cause.getClass().getName();
//            ex.errmsg = e.getCause().getMessage();
//
//
//            addInfo2ex(ex, e);
//
//            String responseTxt = encodeJson(ex);
//            System.out.println("\uD83D\uDED1 endfun handlex().ret=" + responseTxt);
//            wrtRespErrNoex(exchange, responseTxt);
//
//
//        } catch (Throwable e) {
//
//
//            System.out.println(
//                    "⚠\uFE0F e="
//                            + e.getMessage() + "\nStackTrace="
//                            + getStackTraceAsString(e)
//                            + "\n end stacktrace......................"
//            );
//
//
//            //my throw ex.incld funprm
//            if (e instanceof ExceptionBase) {
//                ex = (ExceptionBase) e;
//                ex.errcode = e.getClass().getName();
//
//
//            } else {
//                //nml err
//                ex = new ExceptionBase(e.getMessage());
//
//                //cvt to cstm ex
//                String message = e.getMessage();
//                ex = new ExceptionBase(message);
//                ex.cause = e;
//                ex.errcode = e.getClass().getName();
//
//            }
//
//            addInfo2ex(ex, e);
//
//            String responseTxt = encodeJson(ex);
//            System.out.println("\uD83D\uDED1 endfun handlex().ret=" + responseTxt);
//            wrtRespErrNoex(exchange, responseTxt);
//
//
//        }
//
//    }
