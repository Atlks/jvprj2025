package util.algo;

import java.util.function.Consumer;

//run invoke process invk call run start
public class CallUtil {

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
