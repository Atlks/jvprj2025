package util.proxy;

import util.SupplierX;

import java.lang.reflect.Method;


import static util.log.ColorLogger.*;
import static util.ExptUtil.curFun4dbg;
import static util.ExptUtil.currFunPrms4dbg;
import static util.Util2025.*;

//cglib outtime,asm too lowlev ,bytebuddy cant log 3lev
//only jvvst is ok..
public class AopUtil {
    public static <T> T ivk4log(String funName,Object args, SupplierX<T> spl) throws Exception {
        // 在调用 Lambda 表达式之前或之后，你可以添加日志记录或其他操作
        //   System.out.println("开始执行 blk..."+funName);
        String mthClred = colorStr(funName, YELLOW_bright);
          String argsCled = colorStr(encodeJsonV2(args), GREEN);
        System.out.println("▶\uFE0Ffun " + mthClred + "(args=" + argsCled);
        T result = spl.get();

        //    System.out.println("endblk $funName 执行完毕，结果为: " + result);
        System.out.println("✅endfun " + funName + "().ret=" + encodeJsonObj(result));
        // 返回 Lambda 表达式的结果
        return result;
    }

    public static <T> T ivk4log(String funName, SupplierX<T> spl) throws Exception {
        // 在调用 Lambda 表达式之前或之后，你可以添加日志记录或其他操作
     //   System.out.println("开始执行 blk..."+funName);
        String mthClred = colorStr(funName, YELLOW_bright);
       // String prmurl = colorStr(encodeJsonV2(args), GREEN);
        System.out.println("▶\uFE0Ffun " + mthClred + "(args=" + "");
        T result = spl.get();

    //    System.out.println("endblk $funName 执行完毕，结果为: " + result);
        System.out.println("✅endfun " + funName + "().ret=" + encodeJsonObj(result));
        // 返回 Lambda 表达式的结果
        return result;
    }
    /**
     * Invoke a method on an object dynamically
     *
     * @param object The object on which the method is called
     * @param methodName The name of the method to invoke
     * @param objs The parameters to pass to the method
     */
    public static Object invokeMethod2025(Object object, String methodName, Object... objs) throws  Exception {
        try {
            System.out.println("▶\uFE0Ffun "+methodName+"("+encodeJson(objs));
            curFun4dbg.set(methodName);
            currFunPrms4dbg.set(objs);
            // Get the class type of the object
            Class<?> clazz = object.getClass();

            // Get the parameter types of the method (to match with the method signature)
            Class<?>[] paramTypes = new Class<?>[objs.length];
            for (int i = 0; i < objs.length; i++) {
                paramTypes[i] = objs[i] == null ? null : objs[i].getClass();
            }

            // Get the method by name and parameter types
            Method method = clazz.getMethod(methodName, paramTypes);

            // Set the method accessible if needed
            method.setAccessible(true);

            // Invoke the method on the object and pass the parameters
            Object result = method.invoke(object, objs);

            // Print the result (if any)
            System.out.println("✅endfun "+methodName+".Result: " +encodeJson(result) );
            return  result;
        } catch (Exception e) {
           // e.printStackTrace();  // Catch any exceptions for debugging
           throw e;
        }
     //   return null;
    }
}
