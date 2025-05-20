package ztest;

import util.annos.MethodInfo;

import static util.algo.CallUtil.call;

// 业务类，静态方法加注解
class DelAdmHdr {

    @MethodInfo("注解描述m333")
    public static Object m3(handler.admin.DelAdmHdr.DeleteUserReqDto dto ) {
        System.out.println(" 执行，参数: " + dto + "  "   );
        return null;
    }


}

public class LambdaReflectDemo {


    public static void main(String[] args) throws Throwable {
        call(DelAdmHdr::m3, new handler.admin.DelAdmHdr.DeleteUserReqDto("66") );
    }









}
