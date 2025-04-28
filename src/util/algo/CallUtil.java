package util.algo;

import handler.ylwlt.dto.QueryDto;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import static util.algo.GetUti.getMethod;
import static util.proxy.AopUtil.ivk4log;

//run invoke process invk call run start
public class CallUtil {


    public static < RetType> RetType lambdaInvoke(Class<?> cls1, Object queryDto) throws Throwable {

//        var target=cls1.getConstructor().newInstance();
//        Method m = getMethod(target, "handleRequest");
//        var retobj = m.invoke(target, queryDto);


        //funName jst 4 lg
        String mthFullname = cls1.getName() + ".handleRequest";


        return (RetType)  ivk4log(mthFullname, queryDto, () -> {
            var target = cls1.getConstructor().newInstance();
            Method m = getMethod(target, "handleRequest");
            var retobj = m.invoke(target, queryDto);
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
