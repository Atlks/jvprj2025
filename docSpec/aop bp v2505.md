




public class LambdaReflectDemo {


    public static void main(String[] args) throws Throwable {
        call(DelAdmHdr::m3, new handler.admin.DelAdmHdr.DeleteUserReqDto("66") );
    }

//一个函数参数的，俩个三个参数的实现。。以及supply模式
call函数以及可以读取funname ,dto 等。。。完美了，不需要使用faas模式麻烦。。。




@MethodInfo("注解描述m333")
public static Object m3(handler.admin.DelAdmHdr.DeleteUserReqDto dto ) {
System.out.println(" 执行，参数: " + dto + "  "   );
return null;
}
