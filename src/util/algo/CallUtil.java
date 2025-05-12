package util.algo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.function.Consumer;

import static util.algo.GetUti.getMethod;
import static util.ioc.SimpleContainer.getObj;
import static util.ioc.SimpleContainer.rgstMap4clz;
import static util.proxy.AopUtil.ivk4log;

//run invoke process invk call run start
public class CallUtil {

    public static void exec(String cmd) {
        try {
            ProcessBuilder builder = new ProcessBuilder();
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                builder.command("cmd.exe", "/c", cmd);
            } else {
                builder.command("sh", "-c", cmd);
            }

            builder.redirectErrorStream(true);
            Process process = builder.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static  void callTry(Runnablex o) {
        try {
            o.run();
        } catch (Throwable e) {
            System.out.println("---calltry().catch: " + e.getMessage() + "  ");
            e.printStackTrace();
            System.out.println("----end catlltry().catch()");
        }
    }


    public static < RetType> RetType lmdIvk
            (String regFunName, Object dto) throws Throwable {
       Class beanClz=rgstMap4clz.get(regFunName);

        return  lambdaInvoke(  beanClz,dto);
    }


    public static < RetType> RetType lmdIvk
            (Class<?> cls1, Object dto) throws Throwable {
        return  lambdaInvoke(cls1,dto);
    }

    public static < RetType> RetType lambdaInvoke(Class<?> cls1, Object dto) throws Throwable {

//        var target=cls1.getConstructor().newInstance();
//        Method m = getMethod(target, "handleRequest");
//        var retobj = m.invoke(target, dto);


        //funName jst 4 lg
        String mthFullname = cls1.getName() + ".handleRequest";


        return (RetType)  ivk4log(mthFullname, dto, () -> {
            var target = cls1.getConstructor().newInstance();
            Method m = getMethod(target, "handleRequest");
            var retobj = m.invoke(target, dto);
            return retobj;
        });


    }

//call_user_func

    /**
     * // 直接传递方法引用
     * callUserFunc(CallUserFunc::hello, "John");
     *
     * @param func
     * @param arg
     */
    public static void callUserFunc(Consumer<String> func, String arg) {
        func.accept(arg);
    }
}
