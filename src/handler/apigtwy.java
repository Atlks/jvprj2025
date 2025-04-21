package handler;

import handler.agt.QryAgtsDto;
import util.algo.FunFaas;
import util.serverless.ApiGatewayResponse;

import java.lang.reflect.Method;

public class apigtwy {


    public static void main(String[] args) throws Throwable {


        //dtocls from   //Method("handleRequest
        Object dto = new QryAgtsDto();


        Object target = new XXHdl();

        //agtway just use itfs to covrt


       // Method[] ms=target.getClass().getDeclaredMethods();
//      Method m= target.getClass().getMethod("handleRequest");
        Method m=getMethod(target,"run");
       m.invoke(target,dto);

//
//        FunFaas<Object, ApiGatewayResponse> fas =(dto)->{
//            target::;
//        }
//        fas.apply(dto);

    }


    /**
     * 获取指定对象中名称为 methodName 的无参方法（支持 private、protected、public）
     *
     * @param obj        要查找方法的对象
     * @param methodName 方法名称
     * @return           反射得到的方法对象，找不到返回 null
     */
    private static Method getMethod(Object obj, String methodName) {
        if (obj == null || methodName == null || methodName.isEmpty()) return null;
        Class<?> clazz = obj.getClass();

      //   while (clazz != null) {
      //      try {
                Method[] ms=obj.getClass().getMethods();
                for(  Method method :ms)
                {
                      if(method.getName().equals(methodName))
                      {
                          method.setAccessible(true); // 支持 private/protected 方法
                          return method;
                      }

                }

//            } catch (NoSuchMethodException e) {
//                // 向上查找父类
//                clazz = clazz.getSuperclass();
//            }
//        }

        return null; // 未找到
    }
}
