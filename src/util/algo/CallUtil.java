package util.algo;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import static util.algo.GetUti.getMethod;
import static util.ioc.SimpleContainer.getObj;
import static util.ioc.SimpleContainer.rgstMap4clz;
import static util.proxy.AopUtil.ivk4log;

//run invoke process invk call run start
public class CallUtil {


    public static  void callTry(Runnablex o) {
        try {
            o.run();
        } catch (Throwable e) {
            e.printStackTrace();
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
