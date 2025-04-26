package util.algo;

import handler.agt.getSuperiors;
import handler.ylwlt.dto.QueryDto;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import static util.algo.GetUti.getMethod;

//run invoke process invk call run start
public class CallUtil {



    public static Object lambdaInvoke(Class<getSuperiors> cls1, QueryDto queryDto) throws  Exception {
        var target=cls1.getConstructor().newInstance();
        Method m = getMethod(target, "handleRequest");
        var retobj = m.invoke(target, queryDto);
        return  retobj;

    }

//call_user_func

    /**
     *    // 直接传递方法引用
     *         callUserFunc(CallUserFunc::hello, "John");
     * @param func
     * @param arg
     */
    public static void callUserFunc(Consumer<String> func, String arg) {
    func.accept(arg);
}
}
