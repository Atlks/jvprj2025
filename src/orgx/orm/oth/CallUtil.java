package orgx.orm.oth;

//import api.ylwlt.BizFun;
//import org.jetbrains.annotations.NotNull;
//import util.fp.SerializableBiConsumer;
//import util.fp.SerializableFun;
//import util.model.Context;
//import util.serverless.RequestHandler;
//import util.annos.MethodInfo;


import jakarta.validation.constraints.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

//import static util.algo.GetUti.getMethod;
//import static util.ioc.SimpleContainer.rgstMap4clz;
//import static util.misc.Util2025.encodeJson;
//import static util.proxy.AopUtil.ivk4log;

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

    public static void callTry(RunnableThrowing o) {
        try {
            o.run();
        } catch (Throwable e) {
            System.out.println("---calltry().catch: " + e.getMessage() + "  ");
            e.printStackTrace();
            System.out.println("----end catlltry().catch()");
        }
    }

//
//    public static <RetType> RetType lmdIvk
//            (String regFunName, Object dto) throws Throwable {
//        Class beanClz = rgstMap4clz.get(regFunName);
//
//        return lambdaInvoke(beanClz, dto);
//    }

//
//    public static <RetType> RetType lmdIvk
//            (Class<?> cls1, Object dto) throws Throwable {
//        return lambdaInvoke(cls1, dto);
//    }
//
//
//    public static <T, U> void call(SerializableBiConsumer<T, U> funcBiCsmr, T arg1, U arg2) throws Throwable {
//        // 自动解析目标类和方法名
//        Method method = extractMethod(funcBiCsmr,arg1,arg2);
//
//        // 获取注解内容
//        MethodInfo info = method.getAnnotation(MethodInfo.class);
//        String desc = info == null ? "无注解信息" : info.value();
//        System.out.println("调用方法：" + method.getName() + ", 注解描述：" + desc);
//
//        // 执行方法
//        funcBiCsmr.accept(arg1, arg2);
//    }
//
//
//    /**
//     * 注意这里 samename fun..must excpt
//     *
//     * @param func
//     * @param arg1_dto
//     * @param <T>
//     * @param <RtClz>
//     * @return
//     * @throws Throwable
//     */
//    public static <T, RtClz> RtClz call(SerializableFun<T, RtClz> func, T arg1_dto) throws Throwable {
//
//
//        // 自动解析目标类和方法名
//        Method method = extractMethod(func, arg1_dto);
//
//        // 获取注解内容
//        BizFun info = method.getAnnotation(BizFun.class);
//        String desc = info == null ? "none" : info.value();
//
//        String declaringClassName = getDeclaringClassName(method);
//
//        String methodName = declaringClassName + "::" + method.getName();
//        System.out.println("fun方法：" + methodName + ", 描述：" + desc + ",((" + encodeJson(arg1_dto));
//
//        // 执行方法
//        RtClz apply = func.apply(arg1_dto);
//        System.out.println("endfun " + methodName);
//        return apply;
//    }


    @NotNull
    private static String getDeclaringClassName(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        // System.out.println("类名：" + declaringClass.getName());      // 包含包名
        String declaringClassName = declaringClass.getName();
        return declaringClassName;
    }


    // 利用 SerializedLambda 反射拿目标方法
    private static <T> Method extractMethod(Serializable lambda, T arg1Dto) throws Exception {
        // 调用 writeReplace 方法，拿 SerializedLambda
        Method writeReplace = lambda.getClass().getDeclaredMethod("writeReplace");
        writeReplace.setAccessible(true);
        SerializedLambda serializedLambda = (SerializedLambda) writeReplace.invoke(lambda);

        // 类名是用 / 作为分隔符，需要替换成 .
        String className = serializedLambda.getImplClass().replace('/', '.');
        String methodName = serializedLambda.getImplMethodName();

        // 载入类
        Class<?> implClass = Class.forName(className);

        List<Method> methods = new ArrayList<>();
        // 这里简单尝试根据方法名找唯一匹配（你也可以结合参数类型完善）
        for (Method m : implClass.getDeclaredMethods()) {

            if (m.getName().equals(methodName)) {
                if(lambda instanceof SerializableFun) {
                    if (m.getParameterCount() == 1 && m.getParameterTypes()[0].equals(arg1Dto.getClass()))
                        return m;
                }


            }

        }
        throw new NoSuchMethodException("无法找到方法: " + methodName);
//        if (methods.size() == 0)
//
//        else
//            return methods.get(0);

    }

    private static <T,P2> Method extractMethod(Serializable lambda, T arg1Dto,P2 prm2) throws Exception {
        // 调用 writeReplace 方法，拿 SerializedLambda
        Method writeReplace = lambda.getClass().getDeclaredMethod("writeReplace");
        writeReplace.setAccessible(true);
        SerializedLambda serializedLambda = (SerializedLambda) writeReplace.invoke(lambda);

        // 类名是用 / 作为分隔符，需要替换成 .
        String className = serializedLambda.getImplClass().replace('/', '.');
        String methodName = serializedLambda.getImplMethodName();

        // 载入类
        Class<?> implClass = Class.forName(className);

        List<Method> methods = new ArrayList<>();
        // 这里简单尝试根据方法名找唯一匹配（你也可以结合参数类型完善）
        for (Method m : implClass.getDeclaredMethods()) {

            if (m.getName().equals(methodName)) {


                if(lambda instanceof SerializableBiConsumer)
                {
                    if (m.getParameterCount() == 2 && m.getParameterTypes()[0].equals(arg1Dto.getClass()) && m.getParameterTypes()[1].equals(prm2.getClass()))
                        return m;
                    else
                        methods.add(m);
                }

            }

        }

        if (methods.size() == 0)
            throw new NoSuchMethodException("无法找到方法: " + methodName);
        else
            return methods.get(0);

    }

//    public static <RetType, FunClz extends RequestHandler<DtoClz, RetType>, DtoClz> RetType lambdaInvoke(Class<FunClz> cls1, DtoClz dto, Context ctx) throws Throwable {
//
////        var target=cls1.getConstructor().newInstance();
////        Method m = getMethod(target, "handleRequest");
////        var retobj = m.invoke(target, dto);
//
//
//        //funName jst 4 lg
//        String mthFullname = cls1.getName() + ".handleRequest";
//
//
//        return ivk4log(mthFullname, dto, () -> {
//            FunClz target = cls1.getConstructor().newInstance();
//            RequestHandler<DtoClz, RetType> hdl = (RequestHandler<DtoClz, RetType>) target;
//
//            RetType retobj = hdl.handleRequest(dto, ctx);
//            return retobj;
//        });
//
//
//    }

//    public static <RetType> RetType lambdaInvoke(Class<?> cls1, Object dto) throws Throwable {
//
////        var target=cls1.getConstructor().newInstance();
////        Method m = getMethod(target, "handleRequest");
////        var retobj = m.invoke(target, dto);
//
//
//        //funName jst 4 lg
//        String mthFullname = cls1.getName() + ".handleRequest";
//
//
//        return (RetType) ivk4log(mthFullname, dto, () -> {
//            var target = cls1.getConstructor().newInstance();
//            Method m = getMethod(target, "handleRequest");
//            var retobj = m.invoke(target, dto);
//            return retobj;
//        });
//
//
//    }

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
