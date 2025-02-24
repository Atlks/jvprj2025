package util;

import java.lang.reflect.Method;


import static util.ExptUtil.curFun4dbg;
import static util.ExptUtil.currFunPrms4dbg;
import static util.Util2025.encodeJson;
//cglib outtime,asm too lowlev ,bytebuddy cant log 3lev
//only jvvst is ok..
public class AopUtil {


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
