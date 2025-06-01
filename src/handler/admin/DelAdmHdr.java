package handler.admin;


import jakarta.annotation.security.PermitAll;
import lombok.Data;
import model.admin.Admin;
import org.hibernate.Session;
import util.annos.Paths;
import util.model.Context;
import util.serverless.RequestHandler;
import util.tx.SessionImpBase;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;

import static util.algo.CallUtil.lambdaInvoke;
import static util.tx.HbntUtil.*;

@PermitAll
@Paths({"/apiv1/admin/adm/del"})
public   class DelAdmHdr implements RequestHandler<DelAdmHdr.DeleteUserReqDto, Object> {

    @Override
    public Object handleRequest(DeleteUserReqDto param, Context context) throws Throwable {

      //  DeleteUserReqDto dt=new DeleteUserReqDto(param.userId());

        Class<Admin> aClass = Admin.class;
        String objid = param.uname;

        deletex(aClass, objid);
        return  crudRztThreadLocal.get();
    }


    public static void main2(String[] args) throws Throwable {
        lambdaInvoke(DelAdmHdr.class,new DeleteUserReqDto(""),null);
    }


    public record DeleteUserReqDto(String uname) {
    }


    public  static  void m1(String prm,int prm2){
        System.out.println(prm);
    }


    // 这里是可以实现类型校验的
//    public static <T, U> void call(BiConsumer<T, U> func, T arg1, U arg2) throws Throwable {
//        String methodName = getMethodName(func);
//        System.out.println(methodName+"  "+arg1+"  "+arg2);
//        func.accept(arg1, arg2);
//    }

    public static void main(String[] args) throws Throwable {

      //// 这里是可以实现类型校验的
       // call(DelAdmHdr::m1,"666",333);
    }
//    public static <T, U> String getMethodName(BiConsumer<T, U> func) throws Throwable {
//        MethodHandles.Lookup lookup = MethodHandles.lookup();
//
//        // 找到 writeReplace 方法签名：无参数，返回 Object
//        MethodType methodType = MethodType.methodType(Object.class);
//
//        //haish cant
//        // 通过 MethodHandles 找到 writeReplace
//        MethodHandle writeReplaceMH = lookup.findVirtual(func.getClass(), "writeReplace", methodType);
//
//        SerializedLambda serializedLambda = (SerializedLambda) writeReplaceMH.invoke(func);
//
//        return serializedLambda.getImplMethodName();
//    }
//
//    public static <T, U> String getMethodName3(BiConsumer<T, U> func) throws Exception {
//        Method writeReplaceMethod = null;
//
//        // 通过遍历DeclaredMethods找到writeReplace
//        for (Method m : func.getClass().getDeclaredMethods()) {
//            if (m.getName().equals("writeReplace")) {
//                writeReplaceMethod = m;
//                break;
//            }
//        }
//
//        if (writeReplaceMethod == null) {
//            throw new NoSuchMethodException("writeReplace method not found");
//        }
//
//        writeReplaceMethod.setAccessible(true);
//        SerializedLambda serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(func);
//        return serializedLambda.getImplMethodName();
//    }
//    public static <T, U> String getMethodName2(BiConsumer<T, U> func) throws Exception {
//        Method writeReplace = func.getClass().getDeclaredMethod("writeReplace");
//        writeReplace.setAccessible(true);
//        SerializedLambda serializedLambda = (SerializedLambda) writeReplace.invoke(func);
//        return serializedLambda.getImplMethodName();
//    }

}
