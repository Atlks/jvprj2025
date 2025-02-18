package util;

import apiUsr.RegHandler;
import apis.BaseHdr;
import com.sun.net.httpserver.HttpExchange;
import javassist.*;
import test.MyClassLoader;

import java.io.IOException;
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

    public static Class<?> getAClassExted(Class<?> aClass) throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();

        if (pool.find("apiUsr.RegHandler") == null) {
            throw new RuntimeException("Class RegHandler not found in classpath.");
        }


        CtClass ctClass = pool.get(aClass.getName());

        // 使用自定义类加载器加载字节码
        MyClassLoader myClassLoader = new MyClassLoader();
        CtMethod[] ctMthdArr=   ctClass.getMethods();
        for(CtMethod ctMethod:ctMthdArr)
        {
            // ----------过滤掉继承的obj方法，只处理当前类的方法
            String methodName = ctMethod.getName();
            if( isObjectMethod(methodName))
                continue;;

            // 过滤 abstract 和 native 方法
            if ((ctMethod.getModifiers() & Modifier.ABSTRACT) != 0 ||
                    (ctMethod.getModifiers() & Modifier.NATIVE) != 0) {
                System.out.println("Skipping method: " + methodName);
                continue;
            }
            //------------- 在方法前后插入日志
//            ctMethod.insertBefore("System.out.println(\"!fun " + methodName + "()\");");

            // 在方法前后插入日志，调用 encodeJson 方法
            String mth="▶\uFE0F"+colorStr(methodName,YELLOW_bright);
            String logCode =
                    "{ " +
                            "  String jsonArgs = util.Util2025.encodeJsonV2($args); " + // 调用封装的 encodeJson 方法
                            "  System.out.println(" +
                            "\""+
                            "fun " + mth + "(), " +
                            "args=" +
                            "\" " +
                            "+ util.ColorLogger.colorStr(jsonArgs,util.ColorLogger.GREEN));" +
                            "}";

            ctMethod.insertBefore(logCode);

            //------------- 在方法后插入日志
           // System.out.println(encodeJson(true));
            // 方法返回值序列化日志
            String mth_end="✅"+methodName;
            String returnSerializationCode =
                    "{ " +
                            "  String jsonRet = util.Util2025.encodeJson4dbgShowVal($_); " +  // 调用自定义方法
                            "  System.out.println(\"endfunx " + mth_end + "(), return:\" + jsonRet);" +
                            "}";


            ctMethod.insertAfter(returnSerializationCode, true);  // `true` 让 `$_` 代表返回值

            // ctMethod.   setAccessible(true);



        }

        // 获取修改后的字节码
        byte[] bytecode = ctClass.toBytecode();
        // 手动释放 CtClass
        ctClass.detach();
        Class<?> modifiedClass = myClassLoader.defineClassFromByteArray( aClass.getName(),bytecode);
        return modifiedClass;
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
